package com.jvarela.pokdex.fragment

import androidx.fragment.app.Fragment
import com.jvarela.pokdex.activity.MainActivity

abstract class BaseFragment(idFragment: Int): Fragment(idFragment) {

    abstract fun onBackPressed()

    fun setCurrentFragment(){
        (activity as MainActivity).currentFragment = this
    }

    fun cleanCurrentFragment(){
        (activity as MainActivity).currentFragment = null
    }

}