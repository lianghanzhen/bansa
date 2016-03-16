package com.brianegan.bansa

fun <S : State> applyMiddleware(
        vararg middleware: (Store<S>) -> ((Action) -> Action) -> (Action) -> Action)
        : (Store<S>) -> Store<S> {
    return { store ->
        store.dispatch = compose(middleware.map { it(store) })(store.dispatch)
        store
    }
}

fun <T> compose(
        chain: List<((T) -> T) -> (T) -> T>)
        : ((T) -> T) -> (T) -> T {
    return { dispatch -> chain.foldRight(dispatch, { f, composed -> f(composed) }) }
}
