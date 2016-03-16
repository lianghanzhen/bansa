package com.brianegan.bansa

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StoreTest {
    data class MyState(val state: String = "initial state") : State
    data class MyAction(val type: String = "unknown") : Action

    @Test
    fun `when an action is fired, the corresponding reducer should be called and update the state of the application`() {
        val reducer = createReducer { state: MyState, action: MyAction ->
            when (action.type) {
                "to reduce" -> MyState("reduced")
                else -> state
            }
        }
        val store = createTestStore(MyState(), reducer)

        store.dispatch(MyAction(type = "to reduce"))

        assertThat(store.state.state).isEqualTo("reduced")
    }

    @Test
    fun `when two reducers are combined, and a series of actions are fired, the correct reducer should be called`() {
        val helloReducer1 = "helloReducer1"
        val helloReducer2 = "helloReducer2"

        val reducer1 = createReducer { state: MyState, action: MyAction ->
            when (action.type) {
                helloReducer1 -> MyState("oh hai")
                else -> state
            }
        }

        val reducer2 = createReducer { state: MyState, action: MyAction ->
            when (action.type) {
                helloReducer2 -> MyState("mark")
                else -> state
            }
        }

        val store = createTestStore(MyState(), CombineReducer(reducer1, reducer2))

        store.dispatch(MyAction(type = helloReducer1))
        assertThat(store.state.state).isEqualTo("oh hai")
        store.dispatch(MyAction(type = helloReducer2))
        assertThat(store.state.state).isEqualTo("mark")
    }

    @Test
    fun `subscribers should be notified when the state changes`() {
        val store = createTestStore(MyState(), createReducer { state: MyState, action: MyAction -> MyState() })
        val subscriber1 = createSubscriber<MyState> { state -> assertThat(state.state === "initial state") }
        val subscriber2 = createSubscriber<MyState> { state -> assertThat(state.state === "initial state") }

        store.subscribe(subscriber1)
        store.subscribe(subscriber2)

        store.dispatch(MyAction())
    }

    @Test
    fun `the store should not notify unsubscribed objects`() {
        val store = createTestStore(MyState(), createReducer { state: MyState, action: MyAction -> MyState() })
        val subscriber1 = createSubscriber<MyState> { state -> assertThat(state.state === "initial state") }
        val subscriber2 = createSubscriber<MyState> { state -> assertThat(state.state === "initial state") }

        store.subscribe(subscriber1)
        store.subscribe(subscriber2)
        store.unsubscribe(subscriber1)

        store.dispatch(MyAction())
    }
}

