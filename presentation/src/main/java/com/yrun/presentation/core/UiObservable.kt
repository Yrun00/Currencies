package com.yrun.presentation.core

import java.io.Serializable


interface UpdateObserver<UiState : Any> : Serializable {
    fun updateObserver(observer: UpdateUi<UiState>)
}

interface UpdateUi<T : Any> : Serializable {
    fun updateUi(uiState: T)

    class Empty<T : Any> : UpdateUi<T> {
        override fun updateUi(uiState: T) = Unit
    }
}

interface UiObservable<UiState : Any> : UpdateObserver<UiState>, UpdateUi<UiState> {

    abstract class Abstract<UiState : Any>(protected var cache: UiState) : UiObservable<UiState> {

        private var observer: UpdateUi<UiState> = UpdateUi.Empty()

        override fun updateObserver(observer: UpdateUi<UiState>) {
            this.observer = observer
            observer.updateUi(cache)
        }

        override fun updateUi(uiState: UiState) {
            cache = uiState
            observer.updateUi(cache)
        }
    }
}