package com.yrun.presentation.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.yrun.data.databinding.FragmentProgressBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.main.log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadFragment : BaseFragment<FragmentProgressBinding, LoadViewModel>() {

    private lateinit var updateUi: UpdateUi<LoadUiState>

    override val viewModel: LoadViewModel by viewModels()


    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProgressBinding =
        FragmentProgressBinding.inflate(inflater, container, false)

    override fun onDestroyView() {
        log("LoadFragment destroyed")
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("LoadFragment onViewCreated")

        updateUi = object : UpdateUi<LoadUiState> {
            override fun updateUi(uiState: LoadUiState) {
                uiState.update(
                    progressBar = binding.fragmentProgressBar,
                    errorTextView = binding.fragmentErrorTextView,
                    retryButton = binding.fragmentRetryButton
                )
            }
        }

        binding.fragmentRetryButton.setOnClickListener {
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