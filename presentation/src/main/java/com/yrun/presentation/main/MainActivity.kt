package com.yrun.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yrun.data.databinding.ActivityMainBinding
import com.yrun.presentation.core.ProvideViewModel
import com.yrun.presentation.core.UpdateUi

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var navigationObserver: UpdateUi<Screen>
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)

        navigationObserver = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.show(binding.container.id, supportFragmentManager)
            }
        }
        viewModel.init(isFirsRun = savedInstanceState == null)

    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigationObserver)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun <T : CustomViewModel> viewModel(clazz: Class<T>) =
        (application as ProvideViewModel).viewModel(clazz)

}