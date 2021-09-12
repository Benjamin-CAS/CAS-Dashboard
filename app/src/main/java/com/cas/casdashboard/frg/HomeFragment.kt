package com.cas.casdashboard.frg


import android.annotation.SuppressLint
import android.view.Gravity
import androidx.drawerlayout.widget.DrawerLayout
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.FragmentHomeBinding
import com.cas.casdashboard.frg.LoginFragment.Companion.imageList
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.bindView


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val binding: FragmentHomeBinding by bindView()
    @SuppressLint("WrongConstant")
    override fun initView() {
        isLoginView = false
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        binding.menuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.START)
        }
        binding.backgroundImage.load(imageList.random()){
            crossfade(true)
        }
        binding.button2.setOnClickListener {
            binding.indoorProgressBar.setNumTextWithProgressSweepAngle("100")
        }
    }
    companion object {
        const val TAG = "HomeFragment"
    }
}