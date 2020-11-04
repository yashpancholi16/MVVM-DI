package com.yash.myproject.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yash.myproject.R
import com.yash.myproject.databinding.FragmentSuperheroBinding
import com.yash.myproject.homescreen.viewmodel.ISuperHeroViewModel
import com.yash.myproject.homescreen.viewmodel.SuperHeroViewModel
import com.yash.myproject.homescreen.viewmodel.ViewModelStates
import com.yash.myproject.model.SuperHeroResponse
import com.yash.myproject.utils.viewModelCreator
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SuperHeroListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by kodein()
    private val viewModel: SuperHeroViewModel by viewModelCreator()
    private lateinit var binding: FragmentSuperheroBinding
    private var superHeroAdapter = SuperheroAdapter()

    private val superHeroObserver = Observer<SuperHeroResponse> {
        superHeroAdapter.updateUI(it.data.results)
    }

    private val statusObserver = Observer<ViewModelStates> {
        Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchSuperHeros()
        viewModel.superHeros.observe(this, superHeroObserver)
        viewModel.states.observe(this, statusObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_superhero, container, false)
        binding.lifecycleOwner = this@SuperHeroListFragment
        binding.viewModel = viewModel
        with(binding.superHeroRecyclerView)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = superHeroAdapter
        }

        return binding.root
    }
}