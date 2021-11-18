package com.cas.casdashboard.frg


import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.LocationAdapter
import com.cas.casdashboard.adapter.LoginEditTextRvAdapter
import com.cas.casdashboard.databinding.FragmentLoginBinding
import com.cas.casdashboard.https.response.decode.CompanyLocationDecode
import com.cas.casdashboard.https.response.decode.LoginResultItem
import com.cas.casdashboard.https.util.IStateObserver
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.bindView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private lateinit var locationId: String
    private lateinit var companyId: String
    private lateinit var textAnimation: Animation
    private lateinit var mUsername: String
    private lateinit var mPassword: String
    private val loginViewModel by viewModels<LoginFrgViewModel>()
    private val companyListRvAdapter = LoginEditTextRvAdapter { companyAllEntity ->
        binding.loginBox.companyNameSearch.setText(companyAllEntity.companyAllName)
        binding.loginBox.companyListRv.isVisible = false
        Constants.companyName = companyAllEntity.companyAllName
        companyId = companyAllEntity.companyAllId.toString()
        Log.e(TAG, "initView: $companyAllEntity")
        loginViewModel.getCompanyLocation(companyAllEntity.companyAllId.toString())
        loginViewModel.getCompanyLocationID.observe(viewLifecycleOwner,
            object : IStateObserver<CompanyLocationDecode>() {
                override fun onDataChange(data: CompanyLocationDecode) {
                    if (!data.isNullOrEmpty()) {
                        Log.e(TAG, "onDataChange CompanyLocationDecode: $data")
                        locationAdapter.submitList(data)
                        binding.loginBox.locationRecyclerview.isVisible = true
                    } else {
                        Snackbar.make(binding.root, "Server exception!", Snackbar.LENGTH_SHORT).show()
                    }
                }

                override fun onDataEmpty() {

                }

                override fun onFailed(msg: String) {

                }

                override fun onError(error: Throwable) {
                    requireActivity().getExternalFilesDir(null)
                }

                override fun onLoading() {

                }

            })
        if (loginViewModel.getIsRememberCredentialsValue()) {
            loginViewModel.getAdministrator(companyAllEntity.companyAllName)
                .observe(viewLifecycleOwner) { administrator ->
                    if (administrator != null) {
                        binding.loginBox.apply {
                            companyNameSearch.setText(administrator.companyName)
                            spinner.setText(administrator.locationName)
                            username.setText(administrator.username)
                            password.setText(administrator.password)
                            locationRecyclerview.isVisible = false
                            companyListRv.isVisible = false
                        }
                        locationId = administrator.locationId
                    }
                }
        }
    }
    private val locationAdapter = LocationAdapter {
        binding.loginBox.spinner.setText(it.name_en)
        binding.loginBox.locationRecyclerview.isVisible = false
        locationId = it.location_id
    }

    /**
     * 获取ViewBinding
     */
    override val binding: FragmentLoginBinding by bindView()

    /**
     * 变量初始化
     */
    override fun initView() {
        isLoginView = true
        textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_alpha_anim)
        loginViewModel.getSearchCompany().observe(viewLifecycleOwner) {
            companyListRvAdapter.submitList(it)
        }
        loginViewModel.postIsRememberCredentialsValue()
        loginViewModel.postIsLockedModeValue()
        loginApiResult()
        viewBindingApply()
        val imageListDraw = listOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.one),
            ContextCompat.getDrawable(requireContext(), R.drawable.two),
            ContextCompat.getDrawable(requireContext(), R.drawable.three),
            ContextCompat.getDrawable(requireContext(), R.drawable.four),
            ContextCompat.getDrawable(requireContext(), R.drawable.five),
            ContextCompat.getDrawable(requireContext(), R.drawable.six),
            ContextCompat.getDrawable(requireContext(), R.drawable.seven),
            ContextCompat.getDrawable(requireContext(), R.drawable.eight),
            ContextCompat.getDrawable(requireContext(), R.drawable.nine),
            ContextCompat.getDrawable(requireContext(), R.drawable.ten),
            ContextCompat.getDrawable(requireContext(), R.drawable.eleven),
            ContextCompat.getDrawable(requireContext(), R.drawable.twelve),
            ContextCompat.getDrawable(requireContext(), R.drawable.thirteen),
            ContextCompat.getDrawable(requireContext(), R.drawable.fourteen),
            ContextCompat.getDrawable(requireContext(), R.drawable.fifteen),
        )
        lifecycleScope.launch {
            while (isLoginView) {
                binding.imageView.load(imageListDraw.random()) {
                    crossfade(true)
                }
                delay(10000)
            }
        }
    }
    private fun viewBindingApply() = with(binding) {
        root.setOnClickListener {
            binding.loginBox.apply {
                companyListRv.isVisible = false
                locationRecyclerview.isVisible = false
            }
        }
        loadingText.isVisible = false
        loginBox.apply {
            maskLoading.isVisible = false
            progressBar.isVisible = false
            loginViewModel.isRememberCredentials.observe(viewLifecycleOwner) {
                rememberCredentialsSwitch.isChecked = it
            }

            loginViewModel.isLockedMode.observe(viewLifecycleOwner) {
                lockedModeSwitch.isChecked = it
            }
            companyListRv.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = companyListRvAdapter
            }
            locationRecyclerview.apply {
                layoutManager = GridLayoutManager(requireContext(), 1)
                adapter = locationAdapter
            }
            companyNameSearch.doAfterTextChanged { editText ->
                if (editText.toString().isBlank()) locationRecyclerview.isVisible = false
                binding.loginBox.companyListRv.isVisible = editText.toString().isNotBlank()
                loginViewModel.searchCompany(editText.toString())
            }
            spinner.doAfterTextChanged { editText ->
                if (editText.toString().isBlank()) locationRecyclerview.isVisible = false
            }
            rememberCredentialsSwitch.setOnCheckedChangeListener { button, isCheck ->
                loginViewModel.encodeIsRememberCredentialsValue(isCheck)
            }
            lockedModeSwitch.setOnCheckedChangeListener { button, isCheck ->
                loginViewModel.encodeIsLockedModeValue(isCheck)
            }
            loginBtn.apply {
                icon = null
                setOnClickListener {
                    maskLoading.isVisible = true
                    loadingText.startAnimation(textAnimation)
                    isClickable = false
                    loadingText.isVisible = true
                    progressBar.isVisible = true
                    Snackbar.make(
                        binding.root,
                        "Please wait for the server to respond......",
                        Snackbar.LENGTH_SHORT
                    ).show()
//                        loginViewModel.getLoginResult()
                    mUsername = username.text.toString()
                    mPassword = password.text.toString()
                    if (locationId.isNotBlank() && mUsername.isNotBlank() && mUsername.isNotBlank()) {
                        loginViewModel.getLogin(locationId, mUsername, mPassword)
                    } else
                        Snackbar.make(
                            binding.root,
                            "Username or password is null",
                            Snackbar.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    private fun loginApiResult() {
        loginViewModel.loginResult.observe(viewLifecycleOwner, object : IStateObserver<List<LoginResultItem>>() {
                override fun onDataChange(data: List<LoginResultItem>) {
                    Log.e(TAG, "onDataChange: $data")
                    binding.apply {
                        loadingText.isVisible = false
                        loginBox.apply {
                            progressBar.isVisible = false
                            maskLoading.isVisible = false
                            loginViewModel.insertAdministrator(
                                companyNameSearch.text.toString(),
                                companyId,
                                spinner.text.toString(),
                                locationId,
                                mUsername,
                                mPassword
                            )
                            loginViewModel.insertLoginResultItem(data)
                            findNavController().navigate(R.id.homeFragment)
                        }
                    }
                }

                override fun onDataEmpty() {
                    Log.e(TAG, "onDataEmpty: ")
                    Snackbar.make(
                        binding.root,
                        "No details of the company",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    binding.loadingText.isVisible = false
                    binding.loginBox.apply {
                        progressBar.isVisible = false
                        maskLoading.isVisible = false
                    }
                }

                override fun onFailed(msg: String) {
                    Log.e(TAG, "onFailed: $msg")
                }

                override fun onError(error: Throwable) {
                    Log.e(TAG, "onError: $error")
                }

                override fun onLoading() {}

            })
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
