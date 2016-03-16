package com.brianegan.bansa

interface AnyReducer {

    fun _reduce(state: State, action: Action): State

}

interface Reducer<ReducerState, A: Action>: AnyReducer {

    fun reduce(state: ReducerState, action: A): ReducerState

    @Suppress("unchecked_cast")
    override fun _reduce(state: State, action: Action): State {
        val reducerState = state as? ReducerState ?: return state
        val reducerAction = action as? A ?: return state
        return reduce(reducerState, reducerAction) as State
    }

}

fun <S, A: Action> createReducer(reduceFunction: (S, A) -> S): AnyReducer {
    return object : Reducer<S, A> {
        override fun reduce(state: S, action: A): S {
            return reduceFunction(state, action)
        }
    }
}
