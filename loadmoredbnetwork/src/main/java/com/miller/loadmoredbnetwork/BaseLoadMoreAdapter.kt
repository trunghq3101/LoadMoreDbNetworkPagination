package com.miller.loadmoredbnetwork

import android.view.LayoutInflater
import android.view.ViewGroup
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
                DataBindingUtil.inflate(
                    inflater,
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
            holder.binding.setVariable(BR.item, getItem(position))
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
    ): RecyclerView.ViewHolder(binding.root) {
    }

    class NetworkStateItemViewHolder(
        val binding: ItemLoadmoreNetworkStateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(networkState: NetworkState?) {
            with(binding) {
                var showProgressBar = false
                var showError = false
                if (networkState != null && networkState.status == NetworkState.Status.RUNNING) {
                    showProgressBar = true
                }
                setVariable(BR.showProgressBar, showProgressBar)
                if (networkState != null && networkState.status == NetworkState.Status.FAILED) {
                    showError = true
                    setVariable(BR.msgError, networkState.msg)
                }
                setVariable(BR.showError, showError)
            }
        }

    }

    companion object {
        const val TYPE_PROGRESS = 0
        const val TYPE_ITEM = 1
    }
}