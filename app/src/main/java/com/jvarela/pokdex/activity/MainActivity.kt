package com.jvarela.pokdex.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jvarela.pokdex.R
import com.jvarela.pokdex.fragment.BaseFragment


class MainActivity : AppCompatActivity() {

    var currentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if(currentFragment != null)
            currentFragment?.onBackPressed()
        else
            super.onBackPressed()
    }
}