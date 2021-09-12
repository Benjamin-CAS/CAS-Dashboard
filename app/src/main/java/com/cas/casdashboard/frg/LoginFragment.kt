package com.cas.casdashboard.frg


import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.adapter.LoginEditTextRvAdapter
import com.cas.casdashboard.databinding.FragmentLoginBinding
import com.cas.casdashboard.util.BaseFragment
import com.cas.casdashboard.util.Constants.isLoginView
import com.cas.casdashboard.util.bindView
import com.google.android.material.snackbar.Snackbar
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel by viewModels<LoginFrgViewModel>()
    private val editTextRvAdapter = LoginEditTextRvAdapter()
    private val mk: MMKV = MMKV.defaultMMKV()
    private lateinit var textAnimation:Animation
    override val binding: FragmentLoginBinding by bindView()
    override fun initView() {
        isLoginView = true
        textAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.text_alpha_anim)
        loginViewModel.getSearchCompany().observe(viewLifecycleOwner){
            editTextRvAdapter.submitList(it)
        }
        with(binding){
            loginBox.maskLoading.visibility = View.GONE
            loginBox.progressBar.isVisible = false
            root.setOnClickListener {
                binding.loginBox.editTextRv.isVisible = false
            }
            loadingText.isVisible = false
            loginBox.apply {
                rememberCredentialsSwitch.isChecked = mk.decodeBool(IS_REMEMBER_CREDENTIALS)
                lockedModeSwitch.isChecked = mk.decodeBool(IS_LOCKED_MODE)
                editTextRv.apply {
                    layoutManager = GridLayoutManager(requireContext(),1)
                    adapter = editTextRvAdapter
                }
                companyNameSearch.doAfterTextChanged { editText ->
                    binding.loginBox.editTextRv.isVisible = editText.toString().isNotBlank()
                    loginViewModel.searchCompany(editText.toString())
                }
                rememberCredentialsSwitch.setOnCheckedChangeListener { button, isCheck ->
                    mk.encode(IS_REMEMBER_CREDENTIALS,isCheck)
                }
                lockedModeSwitch.setOnCheckedChangeListener { button, isCheck ->
                    mk.encode(IS_LOCKED_MODE,isCheck)
                }
                loginBtn.apply {
                    icon = null
                    setOnClickListener {
                        maskLoading.isVisible = true
                        loadingText.startAnimation(textAnimation)
                        Log.e(TAG, "initView: 点击了")
//                        setIconResource(R.drawable.loading_animated_vector)
//                        (icon as AnimatedVectorDrawable).start()
                        isClickable = false
                        loadingText.isVisible = true
                        progressBar.isVisible = true
                        Snackbar.make(binding.root,"Please wait for the server to respond......", Snackbar.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }

        }
        editTextRvAdapter.setOnTextViewOnClick {
            binding.loginBox.editTextRv.isVisible = false
            binding.loginBox.companyNameSearch.setText(it.companyAllName)
            Log.e(TAG, "initView: $it")
        }

        lifecycleScope.launch {
            while (isLoginView){
                binding.imageView.load(imageList.random()){
                    crossfade(true)
                }
                delay(10000)
            }
        }
    }
    companion object {
        private const val TAG = "LoginFragment"
        val imageList = listOf("https://tse1-mm.cn.bing.net/th/id/R-C.471ed6b716f5dc8dbd7fe1cb316b5d5f?rik=PDQSg018Hw%2bqpQ&riu=http%3a%2f%2fwww.ghostw7.com%2fuploadslxy%2fallimg%2f160125%2f1T91352B-4.jpg&ehk=JFbykxuI%2f1RKAOc9PAzFYGQkYdRyTQfKea6Zp9T1HDo%3d&risl=&pid=ImgRaw&r=0",
            "https://tse1-mm.cn.bing.net/th/id/R-C.3d65ceb916e50ab97ea198341fd6e18c?rik=m9nclAHZL4Vb4Q&riu=http%3a%2f%2fwww.ghostw7.com%2fuploadslxy%2fallimg%2f160125%2f1T9132T7-5.jpg&ehk=Dpnr0mb4j8x%2b1kHVSTDsdyJMDwntoS3rlUXla8uBexg%3d&risl=&pid=ImgRaw&r=0",
            "https://c.wallhere.com/photos/48/45/lake_sky-1594607.jpg!d")
        private const val IS_REMEMBER_CREDENTIALS = "IS_REMEMBER_CREDENTIALS"
        private const val IS_LOCKED_MODE = "IS_LOCKED_MODE"
    }
}