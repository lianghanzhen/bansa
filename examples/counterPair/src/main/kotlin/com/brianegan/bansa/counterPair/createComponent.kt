package com.brianegan.bansa.counterPair

import com.brianegan.bansa.State
import com.brianegan.bansa.Store

fun <S : State, VM> connect(
        mapStoreToViewModel: (Store<S>) -> VM)
        : ((VM) -> Unit) -> (Store<S>) -> Unit {

    return { view -> { store ->
            view(mapStoreToViewModel(store))
        }
    }


}
