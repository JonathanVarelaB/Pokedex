package com.jvarela.pokdex.manager

import android.view.View
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoaderManager {

    fun showLoader(loader: CircularProgressIndicator, actionView: View? = null){
        if(actionView != null)
            actionView.isEnabled = false
        loader.visibility = View.VISIBLE
    }

    fun hideLoader(loader: CircularProgressIndicator, actionView: View? = null){
        if(actionView != null)
            actionView.isEnabled = true
        loader.visibility = View.GONE
    }

}