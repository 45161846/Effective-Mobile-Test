package com.effectivemobiletask.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.effectivemobiletask.navigation.AuthNavigation
import com.effectivemobiletask.navigation.CoursesNavigation
import com.effectivemobiletask.navigation.FavoritesNavigation
import com.effectivemobiletask.navigation.NavigationProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : Any, Event : Any>(
    private val navigationProvider: NavigationProvider
) : ViewModel() {

    protected val authNavigation: AuthNavigation get() = navigationProvider.getAuthNavigation()
    protected val coursesNavigation: CoursesNavigation get() = navigationProvider.getCoursesNavigation()
    protected val favoritesNavigation: FavoritesNavigation get() = navigationProvider.getFavoritesNavigation()

    private val _state = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> = _state.asStateFlow()

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 10)
    val events = _events

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    protected fun setState(state: State) {
        _state.value = state
    }

    protected fun updateState(update: (State) -> State) {
        val currentState = _state.value ?: return
        _state.value = update(currentState)
    }

    protected fun emitEvent(event: Event) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }

    protected fun setLoading(isLoading: Boolean) {
        _loading.value = isLoading
    }

    fun getCurrentState(): State? = _state.value
}