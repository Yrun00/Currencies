package com.yrun.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yrun.data.R
import com.yrun.data.databinding.ActivityMainBinding
import com.yrun.presentation.core.UpdateUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navigationObserver: UpdateUi<Screen>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        R.id.errorTextView


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
}