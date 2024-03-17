package com.yrun.presentation.premium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.yrun.data.databinding.FragmentPremiumBinding
import com.yrun.presentation.core.BaseFragment
import com.yrun.presentation.settings.FragmentInteraction

class PremiumFragment : BaseFragment<FragmentPremiumBinding, PremiumViewModel>() {

    override val viewModelClass: Class<PremiumViewModel> = PremiumViewModel::class.java

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentPremiumBinding =
        FragmentPremiumBinding.inflate(inflater, container, false)


    private var pair: Pair<String, String> = Pair("", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.backToSettings()
                }
            }
        )
        binding.backToSettingsButton.setOnClickListener {
            viewModel.backToSettings()
        }

        binding.buyPremiumButton.setOnClickListener {

            FragmentInteraction.Base().target(this) {
                pair = it
            }
            viewModel.buyPremiumAndGoToDashboard(pair)
        }
    }
}