package com.miller.loadmoredbnetwork

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

/**
 * Created by Hoang Trung on 15/07/2019
 */

data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>
)