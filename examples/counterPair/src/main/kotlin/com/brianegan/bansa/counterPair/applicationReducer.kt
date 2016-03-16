package com.brianegan.bansa.counterPair

import com.brianegan.bansa.Action
import com.brianegan.bansa.createReducer

val applicationReducer = createReducer { state: ApplicationState, action: Action ->
    when (action) {
        is CounterAction -> counterReducer._reduce(state, action)
        else -> state
    }
}
