package com.yash.myproject.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yash.myproject.R
import com.yash.myproject.databinding.FragmentDetailBinding
import com.yash.myproject.utils.load


class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        val props = DetailFragmentArgs.fromBundle(requireArguments()).props
        binding.imgDescription.load("${props.thumbnail.path}/standard_medium.${props.thumbnail.extension}")
        binding.txvDescription.text = if (props.description.isNotEmpty()) {
            props.description
        } else "No Data Available"
        return binding.root
    }

}