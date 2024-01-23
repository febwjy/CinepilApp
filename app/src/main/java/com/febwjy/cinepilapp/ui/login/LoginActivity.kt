package com.febwjy.cinepilapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.febwjy.cinepilapp.databinding.ActivityLoginBinding
import com.febwjy.cinepilapp.ui.MainActivity
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Febby Wijaya on 23/01/2024.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var passwordShow = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Hawk.contains("Login")) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            binding.txtShowPassword.setOnClickListener {
                if (!passwordShow) {
                    binding.textInputPassword.transformationMethod = null
                    passwordShow = true
                    binding.txtShowPassword.text = "hide password"
                } else {
                    binding.textInputPassword.transformationMethod = PasswordTransformationMethod()
                    passwordShow = false
                    binding.txtShowPassword.text = "show password"
                }
            }

            binding.btnLogin.setOnClickListener {
                Hawk.put("Login", true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}