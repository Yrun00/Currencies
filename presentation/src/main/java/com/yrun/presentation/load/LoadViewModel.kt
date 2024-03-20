package com.yrun.presentation.load

//import com.yrun.domain.load.LoadCurrenciesRepository
//import com.yrun.domain.load.LoadCurrenciesResult
//import com.yrun.presentation.core.UpdateUi
//import com.yrun.presentation.main.BaseViewModel
//import com.yrun.presentation.main.Navigation
//import com.yrun.presentation.main.RunAsync
//import dagger.hilt.android.lifecycle.HiltViewModel
//import javax.inject.Inject
//
//@HiltViewModel
//class LoadViewModel @Inject constructor(
//    private val repository: LoadCurrenciesRepository,
//    private val uiObservable: LoadUiObservable,
//    private val navigation: Navigation.Mutable,
//    runAsync: RunAsync,
//    private val mapper: LoadCurrenciesResult.Mapper = BaseLoadResultMapper(
//        uiObservable,
//        navigation
//    )
//) : BaseViewModel(runAsync) {
//
//    fun init(isFirstRun: Boolean) {
//        if (isFirstRun) load()
//    }
//
//    fun load() {
//        uiObservable.updateUi(LoadUiState.Progress)
//        runAsync({
//            repository.loadCurrencies()
//        }) {
//            it.map(mapper)
//        }
//    }
//
//    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {
//        uiObservable.updateObserver(observer)
//    }
//
//    fun stopGettingUpdates() {
//        uiObservable.updateObserver(UpdateUi.Empty())
//    }
//}