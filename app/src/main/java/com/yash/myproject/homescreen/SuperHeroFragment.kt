package com.yash.myproject.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yash.myproject.R
import com.yash.myproject.databinding.FragmentSuperheroBinding
import com.yash.myproject.homescreen.viewmodel.SuperHeroViewModel
import com.yash.myproject.homescreen.viewmodel.ViewModelStates
import com.yash.myproject.model.Character
import com.yash.myproject.model.SuperHeroResponse
import com.yash.myproject.utils.ClickListener
import com.yash.myproject.utils.PaginationScrollListener
import com.yash.myproject.utils.viewModelCreator
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein


class SuperHeroFragment : Fragment(), KodeinAware, ClickListener {

    override val kodein: Kodein by kodein()
    private val viewModel: SuperHeroViewModel by viewModelCreator()
    private lateinit var binding: FragmentSuperheroBinding
    private var superHeroAdapter = SuperheroAdapter(this)
    private var isLoading = false
    private var currentPageNumber = 1
    private var totalSuperHeros = 0

    private val superHeroObserver = Observer<SuperHeroResponse> {
        isLoading = false
        currentPageNumber = it.data.offset
        totalSuperHeros = it.data.total
        superHeroAdapter.updateUI(it.data.results,currentPageNumber)
    }

    private val statusObserver = Observer<ViewModelStates> {
        Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchSuperHeros(1)
        viewModel.superHeros.observe(this, superHeroObserver)
        viewModel.states.observe(this, statusObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_superhero, container, false)
        binding.lifecycleOwner = this@SuperHeroFragment
        binding.viewModel = viewModel
        with(binding.superHeroRecyclerView)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = superHeroAdapter
            addOnScrollListener(PagingCallback(layoutManager as LinearLayoutManager))
        }

        return binding.root
    }

    override fun onSuperHeroClick(props: Character) {
        super.onSuperHeroClick(props)
        findNavController().navigate(
            SuperHeroFragmentDirections.actionSuperHeroFragmentToDetailFragment(
                props
            )
        )
    }

    inner class PagingCallback(private val layoutManager: LinearLayoutManager) :
        PaginationScrollListener(layoutManager) {

        override fun loadMoreItems() {
            isLoading = true
            viewModel.fetchSuperHeros(++currentPageNumber)
        }

        override fun isLastPage(): Boolean {
            return layoutManager.itemCount == totalSuperHeros
        }

        override fun isLoading(): Boolean {
            return isLoading
        }
    }
}