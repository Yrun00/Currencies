package com.yrun.presentation.load

//import com.yrun.domain.load.LoadCurrenciesResult
//import com.yrun.presentation.dashboard.DashboardUiObservable
//import com.yrun.presentation.dashboard.DashboardUiState
//import com.yrun.presentation.main.Navigation
//import javax.inject.Inject
//
//class BaseLoadResultMapper @Inject constructor(
//    private val observable: DashboardUiObservable,
//    private val navigation: Navigation.Mutable,
//) : LoadCurrenciesResult.Mapper {
//
//    override fun mapError(message: String) {
//        observable.updateUi(DashboardUiState.Error(message))
//    }
//
//    override fun mapSuccess() {
//    }
//}