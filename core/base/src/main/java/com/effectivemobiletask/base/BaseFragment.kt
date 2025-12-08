package com.effectivemobiletask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch

abstract class BaseFragment<
        State : Any,
        Event : Any,
        VM : BaseViewModel<State, Event>,
        Binding : ViewBinding
        > : Fragment() {

    abstract val viewModel: VM

    protected lateinit var binding: Binding

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun createBinding(view: View): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutRes(), container, false)
        binding = createBinding(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = createBinding(view)
        setupUI()
        observeState()
        observeEvents()
        observeLoading()
    }

    abstract fun setupUI()

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state?.let { renderState(it) }
                }
            }
        }
    }

    private fun observeEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun observeLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect { isLoading ->
                    handleLoading(isLoading)
                }
            }
        }
    }

    abstract fun renderState(state: State)
    abstract fun handleEvent(event: Event)
    open fun handleLoading(isLoading: Boolean) {}
}