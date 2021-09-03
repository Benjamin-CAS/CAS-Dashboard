package com.cas.casdashboard.frg


import android.util.Log
import androidx.lifecycle.lifecycleScope
import coil.load
import com.cas.casdashboard.R
import com.cas.casdashboard.databinding.FragmentLoginBinding
import com.cas.casdashboard.util.bindView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val binding: FragmentLoginBinding by bindView()
    override fun initView() {
        Log.e(TAG, "initView: 执行了")
        lifecycleScope.launch {
            while (true){
                binding.imageView.load(imageList.random()){
                    crossfade(true)
                }
                delay(10000)
            }
        }
    }
    companion object {
        const val TAG = "LoginFragment"
        val imageList = listOf("https://tse1-mm.cn.bing.net/th/id/R-C.471ed6b716f5dc8dbd7fe1cb316b5d5f?rik=PDQSg018Hw%2bqpQ&riu=http%3a%2f%2fwww.ghostw7.com%2fuploadslxy%2fallimg%2f160125%2f1T91352B-4.jpg&ehk=JFbykxuI%2f1RKAOc9PAzFYGQkYdRyTQfKea6Zp9T1HDo%3d&risl=&pid=ImgRaw&r=0",
            "https://tse1-mm.cn.bing.net/th/id/R-C.3d65ceb916e50ab97ea198341fd6e18c?rik=m9nclAHZL4Vb4Q&riu=http%3a%2f%2fwww.ghostw7.com%2fuploadslxy%2fallimg%2f160125%2f1T9132T7-5.jpg&ehk=Dpnr0mb4j8x%2b1kHVSTDsdyJMDwntoS3rlUXla8uBexg%3d&risl=&pid=ImgRaw&r=0",
            "https://c.wallhere.com/photos/48/45/lake_sky-1594607.jpg!d")
    }
}