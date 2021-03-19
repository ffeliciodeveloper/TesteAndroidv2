package br.com.testeAndroidV2.testeandroidv2.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.testeAndroidV2.testeandroidv2.R
import br.com.testeAndroidV2.testeandroidv2.databinding.ActivityLoginBinding
import br.com.testeAndroidV2.testeandroidv2.ui.statements.StatementsActivity
import br.com.testeAndroidV2.testeandroidv2.util.Validator
import br.com.testeAndroidV2.testeandroidv2.util.Validator.clearErrorText
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        fetchUserByLoginAndPassword()
        observerSaveUser()
        observerUser()
        observerLoading()
        observerErrorLogin()
    }

    override fun onResume() {
        super.onResume()

        getLastUserLogged()
    }

    private fun fetchUserByLoginAndPassword() {
        binding.buttonLogin.setOnClickListener {
            if (!validateLogin(binding.textInputLogin.text.toString())) {
                clearErrorText(binding.textInputLogin, binding.textInputLayoutLogin)
                return@setOnClickListener
            }

            if (!validatePassword(binding.textInputPassword.text.toString())) {
                clearErrorText(binding.textInputPassword, binding.textInputLayoutPassword)
                return@setOnClickListener
            }

            loginViewModel.fetchUserByLoginAndPassword(
                binding.textInputLogin.text.toString(),
                binding.textInputPassword.text.toString()
            )
        }
    }


    private fun observerUser() {
        loginViewModel.user.observe(this, {
            loginViewModel.saveUser(it)
        })
    }

    private fun observerSaveUser() {
        loginViewModel.userSaved.observe(this, { userSaved ->
            when (userSaved) {
                true -> {
                    loginViewModel.user.value?.let { user ->
                        binding.textInputLogin.setText("")
                        binding.textInputPassword.setText("")
                        StatementsActivity.start(this, user)
                    }
                }
                false -> {
                    Toast.makeText(
                        this,
                        getString(R.string.msg_error_save_user),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun observerLoading() {
        loginViewModel.loading.observe(this, {
            when (it) {
                true -> {
                    binding.buttonLogin.visibility = View.GONE
                    binding.progressBarLogin.visibility = View.VISIBLE
                }
                false -> {
                    binding.buttonLogin.visibility = View.VISIBLE
                    binding.progressBarLogin.visibility = View.GONE
                }
            }
        })
    }

    private fun observerErrorLogin() {
        loginViewModel.errorLogin.observe(this, {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        })
    }

    private fun getLastUserLogged() {
        loginViewModel.getLastLoggedUser()
    }

    private fun validateLogin(login: String): Boolean {
        if(login.trim().isEmpty()) {
            binding.textInputLayoutLogin.error = getString(R.string.msg_login_empty)
            return false
        }

        if(!Validator.isOnlyNumber(login)) {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login.trim()).matches()) {
                binding.textInputLayoutLogin.error = getString(R.string.msg_invalid_email)
                return false
            }
            return true;
        }

        if (!Validator.validateCpf(login)) {
            binding.textInputLayoutLogin.error = getString(R.string.msg_invalid_cpf)
            return false
        }
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (!Validator.validatePassword(password)) {
            binding.textInputLayoutPassword.error = getString(R.string.msg_invalid_password)
            return false
        }
        return true
    }
}
