package com.yrun.presentation.main

import com.yrun.presentation.core.UiObservable

interface Navigation : com.yrun.presentation.core.UiObservable<Screen> {

    class Base : UiObservable.Abstract<Screen>(Screen.Empty), Navigation
}