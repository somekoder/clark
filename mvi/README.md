### Simplified MVI

[![](https://jitpack.io/v/SomeKoder/clark.svg)](https://jitpack.io/#SomeKoder/clark)

This library was created to simplify MVI in android.

##### ClarkViewModel<Intent, State, Effect>

    class MainViewModel(  
        // Dependencies
    ) : ClarkViewModel<MainActivityIntent, MainActivityState, MainActivityEffect>() {  
  
	    override fun createInitialState(): MainActivityState {  
                return MainActivityState(isLoading = false)  
	    }  
  
	    override fun handleIntent(intent: MainActivityIntent) {  
	        when (intent) {  
	            MainActivityIntent.ToggleProgressBar -> toggleLoading()  
	            MainActivityIntent.DisplayMessage -> displayMessage()  
	        }  
	    }  
	    ...
    }

##### Update UI state with state reducer
    private fun toggleLoading() = setState { copy(isLoading = true) }

##### Triggering an intent
	viewModel.setIntent(MainActivityIntent.DisplayMessage)

##### Observing in view
    class MainActivity : AppCompatActivity() {  
            ...
	    override fun onCreate(savedInstanceState: Bundle?) {  
	        ...
	        // Collect UI state
	        collect(viewModel.uiState) { state ->  
	    	    binding.progressBar.isVisible = state.isLoading  
	        } 
	    }
    }

##### Getting current UI state
Current UI state can be fetched with `viewModel.currentState`
   
