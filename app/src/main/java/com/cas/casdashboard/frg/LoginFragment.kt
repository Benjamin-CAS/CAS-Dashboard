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
import com.cas.casdashboard.model.room.entity.CompanyAllEntity
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.LogUtil
import com.cas.casdashboard.util.bindView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginFrgViewModel>(R.layout.fragment_login) {
    private var locationId: String = ""
    private lateinit var companyId: String
    private lateinit var textAnimation: Animation
    private lateinit var mUsername: String
    private lateinit var mPassword: String
    private val companyListRvAdapter = LoginEditTextRvAdapter { companyAllEntity:CompanyAllEntity ->
        binding.loginBox.companyNameSearch.setText(companyAllEntity.companyAllName)
        binding.loginBox.companyListRv.isVisible = false
        Constants.companyName = companyAllEntity.companyAllName
        companyId = companyAllEntity.companyAllId.toString()
        LogUtil.e(TAG, "$companyAllEntity")
        viewModel.getCompanyLocation(companyAllEntity.companyAllId.toString())
        viewModel.getCompanyLocationID.observe(viewLifecycleOwner,
            object : IStateObserver<CompanyLocationDecode>() {
                override fun onDataChange(data: CompanyLocationDecode) {
                    if (!data.isNullOrEmpty()) {
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

                }

                override fun onLoading() {

                }

            })
        if (viewModel.getIsRememberCredentialsValue()) {
            viewModel.getAdministrator(companyAllEntity.companyAllName) {admin ->
                binding.loginBox.apply {
                    companyNameSearch.setText(admin.companyName)
                    spinner.setText(admin.locationName)
                    username.setText(admin.username)
                    password.setText(admin.password)
                    locationRecyclerview.isVisible = false
                    companyListRv.isVisible = false
                }
                locationId = admin.locationId
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
    override val viewModel: LoginFrgViewModel by viewModels()
    /**
     * 变量初始化
     */
    override fun initView() {
        isLoginView = true
        textAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.text_alpha_anim)
        viewModel.getSearchCompany().observe(viewLifecycleOwner) { companyListRvAdapter.submitList(it) }
        viewModel.postIsRememberCredentialsValue()
        viewModel.postIsLockedModeValue()
        loginApiResult()
        viewBindingApply()
        loadingBgi()
    }
    private fun viewBindingApply() = with(binding) {
        root.setOnClickListener {
            binding.loginBox.apply {
                companyListRv.isVisible = false
                locationRecyclerview.isVisible = false
            }
        }
        viewModel.setLoadingObserver(false)
        loginBox.apply {
            viewModel.isRememberCredentials.observe(viewLifecycleOwner) { rememberCredentialsSwitch.isChecked = it }
            viewModel.isLockedMode.observe(viewLifecycleOwner) { lockedModeSwitch.isChecked = it }
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
                viewModel.searchCompany(editText.toString())
            }
            spinner.doAfterTextChanged { editText ->
                if (editText.toString().isBlank()) locationRecyclerview.isVisible = false
            }
            rememberCredentialsSwitch.setOnCheckedChangeListener { _, isCheck ->
                viewModel.encodeIsRememberCredentialsValue(isCheck)
            }
            lockedModeSwitch.setOnCheckedChangeListener { _, isCheck ->
                viewModel.encodeIsLockedModeValue(isCheck)
            }
            loginBtn.apply {
                icon = null
                setOnClickListener {
                    viewModel.deleteAllLoginResultItem()
                    viewModel.setLoadingObserver(true)
                    mUsername = username.text.toString()
                    mPassword = password.text.toString()
                    if (locationId.isNotBlank() && mUsername.isNotBlank() && mUsername.isNotBlank()) {
                        Log.e(TAG, "---viewBindingApply: $locationId $mUsername $mPassword")
                        viewModel.getLogin(locationId, mUsername, mPassword)
                    } else {
                        Snackbar.make(binding.root, "Username or password is null", Snackbar.LENGTH_SHORT).show()
                        viewModel.setLoadingObserver(false)
                    }
                }
            }
        }
    }

    private fun loginApiResult() {
        viewModel.loadingObserver.observe(viewLifecycleOwner){
            binding.apply {
                loginBox.maskLoading.isVisible = it
                loginBox.loginBtn.isClickable = !it
                loadingText.isVisible = it
                loginBox.progressBar.isVisible = it
                if (it)loadingText.startAnimation(textAnimation) else loadingText.clearAnimation()
            }
        }
        viewModel.loginResult.observe(viewLifecycleOwner, object : IStateObserver<List<LoginResultItem>>() {
                override fun onDataChange(data: List<LoginResultItem>) {
                    LogUtil.e(TAG, "onDataChange---: $data")
                    viewModel.setLoadingObserver(false)
                    binding.loginBox.apply {
                        viewModel.insertAdministrator(
                            companyNameSearch.text.toString(),
                            companyId,
                            spinner.text.toString(),
                            locationId,
                            mUsername,
                            mPassword
                        )
                    }
                    viewModel.insertLoginResultItem(data)
                    findNavController().navigate(R.id.homeFragment)
                }

                override fun onDataEmpty() {
                    LogUtil.e(TAG, "onDataEmpty: ")
                    Snackbar.make(binding.root, "No details of the company", Snackbar.LENGTH_SHORT).show()
                    viewModel.setLoadingObserver(false)
                }

                override fun onFailed(msg: String) {
                    LogUtil.e(TAG, "onFailed: $msg")
                    viewModel.setLoadingObserver(false)
                }

                override fun onError(error: Throwable) {
                    LogUtil.e(TAG, "onError: $error")
                    viewModel.setLoadingObserver(false)
                }

                override fun onLoading() {

                }

            })
    }
    private fun loadingBgi(){
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
    companion object {
        private const val TAG = "LoginFragment"
    }


}
