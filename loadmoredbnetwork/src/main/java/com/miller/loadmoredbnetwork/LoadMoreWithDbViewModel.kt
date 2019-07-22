package com.miller.loadmoredbnetwork

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Created by Hoang Trung on 18/07/2019
 */

abstract class BaseLoadMoreWithDbViewModel<Item: BaseLoadMoreEntity>: ViewModel() {
    private val initialLoad = MutableLiveData<Boolean>()
    private val repoResult = Transformations.map(initialLoad) {
        getInitData()
    }
    val data = Transformations.switchMap(repoResult) {
        it.pagedList
    }
    val networkState = Transformations.switchMap(repoResult) {
        it.networkState
    }

    protected abstract fun getInitData(): Listing<Item>

    abstract fun syncDataToNetwork(item: Item)

    abstract fun syncDataToNetwork(items: List<Item>)

    fun loadData() { initialLoad.value = true }
}