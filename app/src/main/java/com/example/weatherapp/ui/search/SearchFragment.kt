package com.example.weatherapp.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.transition.TransitionManager
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.AppExecutors
import com.example.weatherapp.R
import com.example.weatherapp.binding.FragmentDataBindingComponent
import com.example.weatherapp.databinding.SearchFragmentBinding
import com.example.weatherapp.di.Injectable
import com.example.weatherapp.ui.common.CityListAdapter
import com.example.weatherapp.ui.common.SwipeToDeleteCallback
import com.example.weatherapp.util.autoCleared
import com.example.weatherapp.vo.City
import kotlinx.android.synthetic.main.search_fragment.*
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SearchFragmentBinding>()

    var adapter by autoCleared<CityListAdapter>()

    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    var set = false
    private val constraint1 = ConstraintSet()
    private val constraint2 = ConstraintSet()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val rvAdapter = CityListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors,
            showFullName = true
        )
        constraint1.clone(binding.root)
        constraint2.clone(context, R.layout.search_fragment_alt)

        binding.query = searchViewModel.query
        binding.repoList.adapter = rvAdapter
        adapter = rvAdapter

        initSearchInputListener()

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val city: City = adapter.currentList[viewHolder.adapterPosition]
                searchViewModel.delete(city)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.repoList)
    }

    private fun addAnimationOperations() {
        TransitionManager.beginDelayedTransition(binding.root)
        val constraint = constraint2
        constraint.applyTo(root)
    }

    private fun backAnimationOperations() {
        TransitionManager.beginDelayedTransition(binding.root)
        val constraint = constraint1
        constraint.applyTo(root)
    }


    private fun initSearchInputListener() {
        binding.textInputLayout3.setEndIconOnClickListener {
            backAnimationOperations()
            binding.input.text!!.clear()
        }
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        val pattern =
            "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)\$".toRegex()
        if (pattern.matches(query)) {
            dismissKeyboard(v.windowToken)
            searchViewModel.setQuery(query)
            addAnimationOperations()
        } else {
            Toast.makeText(
                context, getString(R.string.coord_valid), LENGTH_SHORT
            ).show()
        }
    }

    private fun initRecyclerView() {
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })
        binding.searchResult = searchViewModel.results
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}
