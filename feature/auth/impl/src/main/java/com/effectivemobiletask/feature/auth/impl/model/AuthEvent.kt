package com.effectivemobiletask.feature.auth.impl.model

sealed interface AuthEvent {
    object NavigateToMain : AuthEvent
    data class ShowError(val message: String) : AuthEvent
    object OpenVK : AuthEvent
    object OpenOK : AuthEvent
}