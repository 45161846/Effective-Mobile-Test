package com.effectivemobiletask.feature.auth.impl.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthState(
    val email: String = "",
    val password: String = "",
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false
)