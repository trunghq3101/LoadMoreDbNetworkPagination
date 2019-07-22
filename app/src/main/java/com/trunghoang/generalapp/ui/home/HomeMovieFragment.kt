package com.trunghoang.generalapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Hoang Trung on 18/07/2019
 */

class HomeMovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModel()
    private val adapter: MovieAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData()
        observeField()
    }

    fun initData() {

        recyclerHomeSpot.adapter = adapter
        recyclerHomeSpot.addItemDecoration(SpacesItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_8)))

        ItemTouchHelper(MovieAdapter.SwipeCallback(adapter) { from, to ->
            viewModel.swapItems(from, to)
        }).apply {
            attachToRecyclerView(recyclerHomeSpot)
        }

        viewModel.loadData()
    }

    fun observeField() {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            viewModel.syncDataToNetwork(it.snapshot())
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.networkState = it
        })
    }

    companion object {
        fun newInstance() = HomeMovieFragment()
    }
}