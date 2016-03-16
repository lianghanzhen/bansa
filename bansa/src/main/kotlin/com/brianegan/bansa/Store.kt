package com.brianegan.bansa

interface Store<S : State> {

    val state: S
    var dispatch: (Action) -> Action

    fun subscribe(subscriber: AnyStateSubscriber): AnyStateSubscriber
    fun unsubscribe(subscriber: AnyStateSubscriber)

}
