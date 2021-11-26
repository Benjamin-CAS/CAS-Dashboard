package com.cas.casdashboard.frg


import android.annotation.SuppressLint
import android.view.Gravity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.SideBarAdapter
import com.cas.casdashboard.databinding.FragmentHomeBinding
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.bindView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeFrgViewModel by viewModels<HomeFrgViewModel>()
    private val sideBarAdapter = SideBarAdapter { loginResultItem ->
        replaceFrg(MonitoringFragment(loginResultItem.id))
    }
    override val binding: FragmentHomeBinding by bindView()
    override fun initView() {
        isLoginView = false
        viewBindingApply()
        homeFrgViewModel.getLoginResultItem().observe(viewLifecycleOwner){
            sideBarAdapter.apply {
                submitList(it)
            }
        }
    }
    @SuppressLint("WrongConstant")
    private fun viewBindingApply() = with(binding){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        menuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(Gravity.START)
        }
        sideRecyclerview.apply {
            layoutManager = GridLayoutManager(requireContext(),1)
            adapter = sideBarAdapter
        }
        airQuality.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        Constants.isLockedMode.observe(viewLifecycleOwner){
            menuBtn.isClickable = !it
        }
    }
    private fun replaceFrg(frg:Fragment) = childFragmentManager.beginTransaction().replace(R.id.home_nav_host,frg).commit()
    companion object {
        const val TAG = "HomeFragment"

    }
}