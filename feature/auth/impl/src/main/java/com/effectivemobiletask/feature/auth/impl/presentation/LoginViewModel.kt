package com.effectivemobiletask.feature.auth.impl.presentation

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.effectivemobiletask.base.BaseViewModel
import com.effectivemobiletask.domain.repository.CoursesRepository
import com.effectivemobiletask.feature.auth.impl.model.AuthEvent
import com.effectivemobiletask.feature.auth.impl.model.AuthState
import com.effectivemobiletask.navigation.NavigationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    navigationProvider: NavigationProvider
) : BaseViewModel<AuthState, AuthEvent>(navigationProvider) {

    init {
        setState(AuthState())
    }

    fun onEmailChanged(email: String) {
        updateState { currentState ->
            currentState.copy(
                email = email,
                isLoginEnabled = validateForm(email, currentState.password)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        updateState { currentState ->
            currentState.copy(
                password = password,
                isLoginEnabled = validateForm(currentState.email, password)
            )
        }
    }

    fun onLoginClick() {
        val currentState = getCurrentState() ?: return

        if (!validateForm(currentState.email, currentState.password)) {
            emitEvent(AuthEvent.ShowError("Заполните все поля корректно"))
            return
        }

        setLoading(true)

        // Симуляция API запроса
        viewModelScope.launch {
            try {
                delay(1000) // Имитация сетевого запроса

                // Проверка email по маске (простая валидация)
                if (!isValidEmail(currentState.email)) {
                    emitEvent(AuthEvent.ShowError("Введите корректный email"))
                    return@launch
                }

                // Успешный вход
                emitEvent(AuthEvent.NavigateToMain)
                authNavigation.navigateToMainTabs()
            } finally {
                setLoading(false)
            }
        }
    }

    fun onVKClick() {
        emitEvent(AuthEvent.OpenVK)
    }

    fun onOKClick() {
        emitEvent(AuthEvent.OpenOK)
    }

    private fun validateForm(email: String, password: String): Boolean {
        return isValidEmail(email) && password.isNotBlank()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}