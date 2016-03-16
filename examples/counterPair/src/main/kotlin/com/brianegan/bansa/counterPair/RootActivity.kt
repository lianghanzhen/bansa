package com.brianegan.bansa.counterPair

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.brianegan.bansa.Action
import com.brianegan.bansa.Store
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.fullType
import uy.kohesive.injekt.api.get

open class RootActivity : AppCompatActivity() {
    val store: Store<ApplicationState> = Injekt.get(fullType<Store<ApplicationState>>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(RootView(this, store))
    }
}
