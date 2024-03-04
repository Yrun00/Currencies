package com.yrun.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yrun.data.databinding.FragmentDashboardBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.UpdateUi

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    private lateinit var updateUi: UpdateUi<DashboardUiState>
    private lateinit var adapter: DashboardAdapter

    override val viewModelClass: Class<DashboardViewModel> = DashboardViewModel::class.java

    override fun inflate(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DashboardAdapter(viewModel)

        binding.dashboardRecyclerView.adapter = adapter

        binding.settingButton.setOnClickListener {
            viewModel.goToSettings()
        }

        updateUi = object : UpdateUi<DashboardUiState> {
            override fun updateUi(uiState: DashboardUiState) {
                uiState.update(showList = adapter)
            }
        }
        viewModel.load()
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
