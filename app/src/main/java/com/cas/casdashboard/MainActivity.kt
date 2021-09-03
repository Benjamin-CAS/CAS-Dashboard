package com.cas.casdashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.core.view.*
import com.cas.casdashboard.databinding.ActivityMainBinding
import com.cas.casdashboard.util.Constants.hideKeyboard
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        hideSystemUI()
        setContentView(binding.root)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN -> {
                hideKeyboard(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,window.decorView).hide(WindowInsetsCompat.Type.systemBars())
        WindowInsetsControllerCompat(window,window.decorView).systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}