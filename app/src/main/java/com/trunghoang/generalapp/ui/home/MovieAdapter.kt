package com.trunghoang.generalapp.ui.home

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.miller.loadmoredbnetwork.BaseLoadMoreAdapter
import com.trunghoang.generalapp.BR
import com.trunghoang.generalapp.R
import com.trunghoang.generalapp.data.model.Movie

/**
 * Created by Hoang Trung on 18/07/2019
 */
class MovieAdapter : BaseLoadMoreAdapter<Movie>(homeSpotCallback) {

    override val itemBindingVariable: Int = BR.item

    override fun getItemLayoutRes(): Int {
        return R.layout.item_movie
    }

    class SwipeCallback(
        private val adapter: MovieAdapter,
        private val onItemMove: (from: Movie, to: Movie) -> Unit
    ) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {

        private var from: Int = 0
        private var to: Int = 0

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            from = viewHolder.adapterPosition
            to = target.adapterPosition
            val item1 = adapter.getItem(from)
            val item2 = adapter.getItem(to)
            if (item1 != null && item2 != null) {
                adapter.notifyItemMoved(from, to)
                return true
            }
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun isLongPressDragEnabled(): Boolean {
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

            val item1 = adapter.getItem(from)
            val item2 = adapter.getItem(to)
            if (item1 != null && item2 != null) {
                onItemMove(item1, item2)
            }
        }
    }

}

val homeSpotCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}
