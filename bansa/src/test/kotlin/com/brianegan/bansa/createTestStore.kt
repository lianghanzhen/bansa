package com.brianegan.bansa

fun <S : State> createTestStore(initialState: S, reducer: AnyReducer): Store<S> {
    return createStore(initialState, reducer);
}
