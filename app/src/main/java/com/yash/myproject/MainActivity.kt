package com.yash.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.yash.myproject.databinding.ActivityMainBinding
import com.yash.myproject.homescreen.SuperHeroFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.navigationBack.setOnClickListener {
            onBackPressed()
        }
        binding.lifecycleOwner = this
        addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)!!.childFragmentManager.fragments[0]

        when (currentFragment::class.java) {

            SuperHeroFragment::class.java -> binding.navigationBack.visibility = View.GONE
            else -> binding.navigationBack.visibility = View.VISIBLE
        }
    }

    private fun addOnBackStackChangedListener(listener: FragmentManager.OnBackStackChangedListener) =
        supportFragmentManager
            .findFragmentById(R.id.navHostFragment)!!
            .childFragmentManager
            .addOnBackStackChangedListener(listener)
}