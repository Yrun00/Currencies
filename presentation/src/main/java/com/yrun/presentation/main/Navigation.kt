package com.yrun.presentation.main

import com.yrun.presentation.core.UiObservable
import com.yrun.presentation.core.UpdateObserver
import com.yrun.presentation.core.UpdateUi
import javax.inject.Inject


interface Navigation {

    interface Navigate : UpdateUi<Screen>

    interface ObserveNavigation : UpdateObserver<Screen>

    interface Mutable : Navigate, ObserveNavigation

    class Base @Inject constructor() : UiObservable.Abstract<Screen>(Screen.Empty), Mutable


}