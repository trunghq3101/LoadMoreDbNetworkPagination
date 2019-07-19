package com.miller.loadmoredbnetwork

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Hoang Trung on 18/07/2019
 */

abstract class BaseLoadMoreAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>
) : PagedListAdapter<Item, RecyclerView.ViewHolder>(diffCallback) {

    abstract val itemBindingVariable: Int

    var networkState: NetworkState? = null
        set(value) {
            val prev = field
            val prevExtraRow = hasExtraRow()
            field = value
            val newExtraRow = hasExtraRow()
            if (prevExtraRow != newExtraRow) {
                if (prevExtraRow) {
                    notifyItemRemoved(itemCount)
                } else {
                    notifyItemInserted(itemCount)
                }
            } else if (newExtraRow && prev != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PROGRESS -> NetworkStateItemViewHolder(
                inflater.inflate(
                    R.layout.item_loadmore_network_state,
                    parent,
                    false
                )
            )
            TYPE_ITEM -> ItemViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    getItemLayoutRes(),
                    parent,
                    false
                )
            )
            else -> ItemViewHolder(
                DataBindingUtil.inflate(
                    inflater,
                    getItemLayoutRes(),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.binding.setVariable(itemBindingVariable, getItem(position))
        } else if (holder is NetworkStateItemViewHolder){
            holder.bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    @LayoutRes
    abstract fun getItemLayoutRes(): Int

    private fun hasExtraRow(): Boolean {
        return networkState != null && this.networkState !== NetworkState.LOADED
    }

    open class ItemViewHolder(
        val binding: ViewDataBinding
    ): RecyclerView.ViewHolder(binding.root)

    class NetworkStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val progressBarView = itemView.findViewById<ProgressBar>(R.id.progressLoadMore)
        private val errorView = itemView.findViewById<TextView>(R.id.textError)

        fun bind(networkState: NetworkState?) {
                var showProgressBar = false
                var showError = false
                if (networkState != null && networkState.status == NetworkState.Status.RUNNING) {
                    showProgressBar = true
                }
                progressBarView.visibility = if (showProgressBar) View.VISIBLE else View.GONE
                if (networkState != null && networkState.status == NetworkState.Status.FAILED) {
                    showError = true
                    errorView.text = networkState.msg
                }
                errorView.visibility = if (showError) View.VISIBLE else View.GONE
        }

    }

    companion object {
        const val TYPE_PROGRESS = 0
        const val TYPE_ITEM = 1
    }
}