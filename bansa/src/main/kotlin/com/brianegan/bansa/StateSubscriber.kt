package com.brianegan.bansa

interface AnyStateSubscriber {

    fun _onStateChanged(state: Any)

}

interface StateSubscriber<StateType>: AnyStateSubscriber {

    fun onStateChanged(state: StateType)

    override fun _onStateChanged(state: Any) {
        @Suppress("unchecked_cast")
        val stateType = state as? StateType ?: return
        onStateChanged(stateType)
    }

}

interface StateFilter {
    fun filter(state: State): Any
}

fun <S> createSubscriber(subscribeFunction: (S) -> Unit): AnyStateSubscriber {
    return object : StateSubscriber<S> {
        override fun onStateChanged(state: S) {
            subscribeFunction(state)
        }
    }
}

fun createStateFilter(filterFunction: (State) -> Any): StateFilter {
    return object : StateFilter {
        override fun filter(state: State): Any {
            return filterFunction(state)
        }
    }
}
