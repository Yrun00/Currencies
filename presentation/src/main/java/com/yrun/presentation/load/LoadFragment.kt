package com.yrun.presentation.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yrun.data.databinding.FragmentProgressBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.UpdateUi


class LoadFragment : BaseFragment<FragmentProgressBinding, LoadViewModel>() {

    private lateinit var updateUi: UpdateUi<LoadUiState>

    override val viewModelClass: Class<LoadViewModel> = LoadViewModel::class.java

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProgressBinding =
        FragmentProgressBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    progressBar = binding.progressBar,
                    errorTextView = binding.errorTextView,
                    retryButton = binding.retryButton
                )
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}