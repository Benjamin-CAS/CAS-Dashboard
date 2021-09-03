package com.cas.casdashboard.util

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified VB : ViewBinding> bindView() =
    FragmentBindingDelegate(VB::class.java)

class FragmentBindingDelegate<VB : ViewBinding>(
    private val clazz: Class<VB>
) : ReadOnlyProperty<Fragment, VB> {
    private var isInitialized = false
    private var binding: VB ?=null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (!isInitialized) {
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    binding = null
                    isInitialized = false
                }
            })
            binding = clazz.getMethod("bind", View::class.java).invoke(null, thisRef.requireView()) as? VB
            isInitialized = true
        }

        return binding!!
    }
}

inline fun <reified VB : ViewBinding> Activity.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

inline fun <reified VB : ViewBinding> Dialog.inflate() = lazy {
    inflateBinding<VB>(layoutInflater).apply { setContentView(root) }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

