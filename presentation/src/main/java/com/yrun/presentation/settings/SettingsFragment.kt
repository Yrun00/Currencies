package com.yrun.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.yrun.data.databinding.FragmentSettingsBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.settings.adapter.SettingsAdapter

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    private lateinit var updateUi: UpdateUi<SettingsUiState>
    private lateinit var fromAdapter: SettingsAdapter
    private lateinit var toAdapter: SettingsAdapter

    override val viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java

    override fun inflate(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            viewModel.backToDashboard()
        }

        fromAdapter = SettingsAdapter(clickListener = viewModel::chooseFromCurrency)

        toAdapter = SettingsAdapter(clickListener = {
            viewModel.chooseToCurrency(fromCurrency = fromAdapter.selectedChoice(), toCurrency = it)
        })

        binding.fromRecyclerView.adapter = fromAdapter
        binding.toRecyclerView.adapter = toAdapter

        binding.saveButton.setOnClickListener {
            viewModel.saveSettings(
                fromCurrency = fromAdapter.selectedChoice(),
                toCurrency = toAdapter.selectedChoice()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.backToDashboard()
                }
            }
        )

        updateUi = object : UpdateUi<SettingsUiState> {
            override fun updateUi(uiState: SettingsUiState) {
                uiState.show(toAdapter = toAdapter, fromAdapter = fromAdapter)
                uiState.show(saveButton = binding.saveButton)
            }
        }
        viewModel.init()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }


}