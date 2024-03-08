package com.yrun.presentation.main

import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateObserver
import com.yrun.presentation.core.UpdateUi


interface Navigation {

    interface Navigate : UpdateUi<Screen>

    interface ObserveNavigation : UpdateObserver<Screen>

    interface Mutable : Navigate, ObserveNavigation

    object Base : UiObservable.Abstract<Screen>(Screen.Empty), Mutable
}