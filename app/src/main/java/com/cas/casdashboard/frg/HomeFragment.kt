package com.cas.casdashboard.frg


import android.annotation.SuppressLint
import android.util.Log
import android.view.Gravity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.SideBarAdapter
import com.cas.casdashboard.databinding.FragmentHomeBinding
import com.cas.casdashboard.dialog.ChartDialog
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.bindView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeFrgViewModel by viewModels<HomeFrgViewModel>()
    private val sideBarAdapter = SideBarAdapter{

    }
    override val binding: FragmentHomeBinding by bindView()
    override fun initView() {
        isLoginView = false
        viewBindingApply()
        homeFrgViewModel.getLoginResultItem().observe(viewLifecycleOwner){
            sideBarAdapter.apply {
                submitList(it){
                    if (it.isNotEmpty()) setPosition()
                }
            }
        }
    }
    @SuppressLint("WrongConstant")
    private fun viewBindingApply(){
        binding.apply {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            menuBtn.setOnClickListener {
                binding.drawerLayout.openDrawer(Gravity.START)
            }
            button2.setOnClickListener {
//                ChartDialog().show(childFragmentManager,"Chart")
                childFragmentManager.beginTransaction().replace(R.id.home_nav_host,TestFragment()).commit()
            }
            sideRecyclerview.apply {
                layoutManager = GridLayoutManager(requireContext(),1)
                adapter = sideBarAdapter
            }
        }

        Constants.isLockedMode.observe(viewLifecycleOwner){
            binding.menuBtn.isClickable = !it
        }
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}