package com.brianegan.bansa

import java.util.concurrent.atomic.AtomicBoolean

fun <S : State> createStore(initialState: S, reducer: AnyReducer) : Store<S>
        = AppStore(initialState, reducer)

class AppStore<S: State>: Store<S> {

    private var currentState: S
        set(state) {
            field = state
            subscriptions.forEach {
                it.first._onStateChanged(it.second?.filter(state) ?: state)
            }
        }

    private var reducer: AnyReducer

    private var subscriptions = arrayListOf<Pair<AnyStateSubscriber, StateFilter?>>()

    override val state: S
        get() = currentState

    override var dispatch: (Action) -> Action

    private var isDispatching = AtomicBoolean(false)

    constructor(initialState: S, initialReducer: AnyReducer) {
        currentState = initialState;
        reducer = initialReducer
        dispatch = { action ->
            if (isDispatching.get()) {
                throw IllegalStateException("Reducer is dispatching so it cannot dispatch action")
            }
            isDispatching.set(true)
            @Suppress("unchecked_cast")
            val newState = reducer._reduce(currentState, action) as S
            isDispatching.set(false)
            currentState = newState
            action
        }
    }

    private fun isNewSubscriber(subscriber: AnyStateSubscriber)
            = subscriptions.any { subscription -> subscription.first === subscriber }

    override fun subscribe(subscriber: AnyStateSubscriber): AnyStateSubscriber {
        if (isNewSubscriber(subscriber)) {
            subscriptions.add(Pair(subscriber, null))
            subscriber._onStateChanged(state)
        }
        return subscriber
    }

    fun subscribe(subscriber: AnyStateSubscriber, filter: StateFilter) {
        if (!isNewSubscriber(subscriber)) { return }

        subscriptions.add(Pair(subscriber, filter))
        subscriber._onStateChanged(filter.filter(state))
    }

    override fun unsubscribe(subscriber: AnyStateSubscriber) {
        val index = subscriptions.indexOfFirst { subscription -> subscription.first === subscriber }
        if (index >= 0) {
            subscriptions.removeAt(index)
        }
    }

}
