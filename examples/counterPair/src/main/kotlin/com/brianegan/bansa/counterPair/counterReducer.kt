package com.brianegan.bansa.counterPair

import com.brianegan.bansa.createReducer
import java.util.*

val counterReducer = createReducer { state: ApplicationState, action: CounterAction ->
    when (action) {
        is INIT -> action.initialState
        is INCREMENT -> {
            val counters = LinkedHashMap(state.counters)
            counters[action.id] = counters[action.id]?.plus(1)
            state.copy(counters = counters)
        }
        is DECREMENT -> {
            val counters = LinkedHashMap(state.counters)
            counters[action.id] = counters[action.id]?.minus(1)
            state.copy(counters = counters)
        }
        else -> state
    }
}
