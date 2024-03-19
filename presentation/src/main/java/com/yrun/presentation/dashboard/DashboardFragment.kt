package com.yrun.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.yrun.data.R
import com.yrun.data.databinding.FragmentDashboardBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.core.UpdateUi
import com.yrun.presentation.dashboard.adapter.DashboardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    ClickActions {

    private lateinit var updateUi: UpdateUi<DashboardUiState>
    private lateinit var adapter: DashboardAdapter
    private lateinit var snackBar: Snackbar

    override val viewModel: DashboardViewModel by viewModels()

    override fun inflate(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentDashboardBinding =
        FragmentDashboardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DashboardAdapter(this)

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

    override fun deletePair(pairUi: String) {
        snackBar = Snackbar.make(
            binding.root,
            getString(R.string.deleteQuestion, pairUi),
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.delete) {
            viewModel.deletePair(pairUi)
            snackBar.dismiss()
        }
        snackBar.show()

        binding.settingButton.setOnClickListener {
            viewModel.goToSettings()
            snackBar.dismiss()
        }

        binding.dashboardRecyclerView.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                snackBar.dismiss()
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
        })

        binding.root.setOnClickListener {
            snackBar.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = updateUi)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun retry() = viewModel.retry()

}