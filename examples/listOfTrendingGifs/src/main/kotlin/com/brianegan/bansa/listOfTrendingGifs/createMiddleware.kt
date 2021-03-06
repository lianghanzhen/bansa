package com.brianegan.bansa.listOfTrendingGifs

import com.brianegan.bansa.Action
import com.brianegan.bansa.State
import com.brianegan.bansa.Store

fun <S : State, A : Action>createMiddleware(middleware: (Store<S, A>, (A) -> A, A) -> A)
        : (Store<S, A>) -> ((A) -> A) -> (A) -> A {
    return { store: Store<S, A> ->
        { next: (A) -> A ->
            { action: A ->
                middleware(store, next, action);
            }
        }
    }
}
