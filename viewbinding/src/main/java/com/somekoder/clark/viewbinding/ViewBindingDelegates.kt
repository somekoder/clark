package com.somekoder.clark.viewbinding

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** Activity binding delegate, may be used since onCreate up to onDestroy (inclusive) */
fun <T : ViewBinding> AppCompatActivity.viewBinding(factory: (LayoutInflater) -> T): ReadOnlyProperty<AppCompatActivity, T> = object : ReadOnlyProperty<AppCompatActivity, T>, DefaultLifecycleObserver {

    private var binding: T? = null

    override fun onDestroy(owner: LifecycleOwner) {
        (binding as? ViewDataBinding)?.unbind()
        binding = null
    }

    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (binding == null) {
            binding = factory(layoutInflater)
            lifecycle.addObserver(this)
        }
        return binding!!
    }
}

/** Fragment binding delegate, may be used since onViewCreated up to onDestroyView (inclusive) */
fun <T : ViewBinding> Fragment.viewBinding(factory: (LayoutInflater) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
            binding ?: factory(layoutInflater).also {
                // if binding is accessed after Lifecycle is DESTROYED, create new instance, but don't cache it
                if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                    viewLifecycleOwner.lifecycle.addObserver(this)
                    binding = it
                }
            }

        override fun onDestroy(owner: LifecycleOwner) {
            (binding as? ViewDataBinding)?.unbind()
            binding = null
        }
    }