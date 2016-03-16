package com.brianegan.bansa

class CombineReducer : AnyReducer {

    private val reducers: List<AnyReducer>

    constructor(vararg reducers: AnyReducer) {
        this.reducers = reducers.toList()
    }

    override fun _reduce(state: State, action: Action): State {
        return this.reducers.fold(state, {state, reducer -> reducer._reduce(state, action)})
    }

}
