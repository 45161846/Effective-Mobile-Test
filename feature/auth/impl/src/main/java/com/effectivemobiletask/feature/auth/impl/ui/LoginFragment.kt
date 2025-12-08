package com.effectivemobiletask.feature.auth.impl.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.effectivemobiletask.base.BaseFragment
import com.effectivemobiletask.feature.auth.impl.R
import com.effectivemobiletask.feature.auth.impl.databinding.FragmentLoginBinding
import com.effectivemobiletask.feature.auth.impl.model.AuthEvent
import com.effectivemobiletask.feature.auth.impl.model.AuthState
import com.effectivemobiletask.feature.auth.impl.presentation.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<AuthState, AuthEvent, LoginViewModel, FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_login

    override fun createBinding(view: View): FragmentLoginBinding =
        FragmentLoginBinding.bind(view)

    override fun setupUI() {
        with(binding) {
            emailEditText.hint = "example@gmail.com"

            emailEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onEmailChanged(text?.toString() ?: "")
            }

            passwordEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onPasswordChanged(text?.toString() ?: "")
            }

            loginButton.setOnClickListener {
                viewModel.onLoginClick()
            }

            registrationText.isEnabled = false
            forgotPasswordText.isEnabled = false

             loginVk.setOnClickListener { viewModel.onVKClick() }
             loginOk.setOnClickListener { viewModel.onOKClick() }
        }
    }

    override fun renderState(state: AuthState) {
        with(binding) {
            loginButton.isEnabled = state.isLoginEnabled && !state.isLoading
        }
    }

    override fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.NavigateToMain -> {

            }
            is AuthEvent.ShowError -> {
                Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
            }
            is AuthEvent.OpenVK -> {
                openUrl("https://vk.com/")
            }
            is AuthEvent.OpenOK -> {
                openUrl("https://ok.ru/")
            }
        }
    }

    override fun handleLoading(isLoading: Boolean) {
        binding.loginButton.text = if (isLoading) "Загрузка..." else "Вход"
        binding.loginButton.isEnabled = !isLoading
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}