package com.trunghoang.generalapp.ui.homespot

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.baseloadmore.BaseLoadMoreAdapter
import com.trunghoang.generalapp.data.model.HomeSpot

/**
 * Created by Hoang Trung on 18/07/2019
 */
class HomeSpotAdapter : BaseLoadMoreAdapter<HomeSpot>(homeSpotCallback) {

    override fun getItemLayoutRes(): Int {
        return R.layout.item_home_spot
    }

    class SwipeCallback(
        private val adapter: HomeSpotAdapter,
        private val onItemMove: (from: Int, to: Int) -> Unit
    ) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.adapterPosition
            val to = target.adapterPosition
            val item1 = adapter.getItem(from)
            val item2 = adapter.getItem(to)
            if (item1 != null && item2 != null) {

                // Notify database to swap items here
                onItemMove(from, to)

                return true
            }
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun isLongPressDragEnabled(): Boolean {

            // It has to be true
            return true
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder?.itemView?.alpha = 0.5f
            }
        }

        override fun clearView(recyclerView: RecyclerView,
                               viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            viewHolder.itemView.alpha = 1.0f

            // Don't need to do anything here
        }
    }

}

val homeSpotCallback = object : DiffUtil.ItemCallback<HomeSpot>() {
    override fun areItemsTheSame(oldItem: HomeSpot, newItem: HomeSpot): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeSpot, newItem: HomeSpot): Boolean {
        return oldItem == newItem
    }

}