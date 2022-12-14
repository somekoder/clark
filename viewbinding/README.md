### Simplified ViewBindings

[![](https://jitpack.io/v/SomeKoder/clark.svg)](https://jitpack.io/#SomeKoder/clark)

This library was created to simplify ViewBindings in android. It takes care of the binding and unbinding, eliminating the need to handle cleanup when views are destroyed.

    class HomeFragment() : Fragment() {  
  
	    private val binding by viewBinding(FragmentHome::inflate)

        override fun onCreateView(
            inflater: LayoutInflater, 
            container: ViewGroup?, 
            savedInstanceState: Bundle?
        ): View {
            return binding.root
        }
	    
    }