package com.yrun.presentation.load

//import com.yrun.presentation.core.views.ChangeVisibility
//import com.yrun.presentation.core.views.ErrorText
//
//interface LoadUiState {
//
//    fun update(
//        progressBar: ChangeVisibility,
//        errorTextView: ErrorText,
//        retryButton: ChangeVisibility
//    )
//
//    data class Error(private val message: String) : LoadUiState {
//        override fun update(
//            progressBar: ChangeVisibility,
//            errorTextView: ErrorText,
//            retryButton: ChangeVisibility
//        ) {
//            errorTextView.show()
//            errorTextView.changeText(message)
//            progressBar.hide()
//            retryButton.show()
//        }
//    }
//
//    object Progress : LoadUiState {
//        override fun update(
//            progressBar: ChangeVisibility,
//            errorTextView: ErrorText,
//            retryButton: ChangeVisibility
//        ) {
//            errorTextView.hide()
//            retryButton.hide()
//            retryButton.hide()
//        }
//    }
//
//    object Empty : LoadUiState {
//        override fun update(
//            progressBar: ChangeVisibility,
//            errorTextView: ErrorText,
//            retryButton: ChangeVisibility
//        ) = Unit
//    }
//}