package com.yrun.presentation.load

//
//@AndroidEntryPoint
//class LoadFragment : BaseFragment<FragmentProgressBinding, LoadViewModel>() {
//
//    private lateinit var updateUi: UpdateUi<LoadUiState>
//
//    override val viewModel: LoadViewModel by viewModels()
//
//
//    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProgressBinding =
//        FragmentProgressBinding.inflate(inflater, container, false)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        updateUi = object : UpdateUi<LoadUiState> {
//            override fun updateUi(uiState: LoadUiState) {
//                uiState.update(
//                    progressBar = binding.fragmentProgressBar,
//                    errorTextView = binding.fragmentErrorTextView,
//                    retryButton = binding.fragmentRetryButton
//                )
//            }
//        }
//
//        binding.fragmentRetryButton.setOnClickListener {
//            viewModel.load()
//        }
//
//        viewModel.init(savedInstanceState == null)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        viewModel.startGettingUpdates(updateUi)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        viewModel.stopGettingUpdates()
//    }
//}