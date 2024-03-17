package com.yrun.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.yrun.data.databinding.FragmentSettingsBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.BundleWrapper
import com.yrun.presentation.core.BundleWrapper.Companion.FROM
import com.yrun.presentation.core.BundleWrapper.Companion.TO
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

        val fragmentInteraction = FragmentInteraction.Base()

        binding.saveButton.setOnClickListener {

            fragmentInteraction
                .source(this, fromAdapter.selectedChoice(), toAdapter.selectedChoice())

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
        viewModel.init(BundleWrapper.Base(savedInstanceState))
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        BundleWrapper.Base(outState).save(fromAdapter.selectedChoice(), FROM)
        BundleWrapper.Base(outState).save(toAdapter.selectedChoice(), TO)
    }


}

