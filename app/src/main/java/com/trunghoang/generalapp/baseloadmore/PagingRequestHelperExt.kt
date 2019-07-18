package com.trunghoang.generalapp.baseloadmore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by Hoang Trung on 15/07/2019
 */

private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}

fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener(object: PagingRequestHelper.Listener {
        override fun onStatusChange(report: PagingRequestHelper.StatusReport) {
            when {
                report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
                report.hasError() -> liveData.postValue(
                    NetworkState.error(
                        getErrorMessage(
                            report
                        )
                    )
                )
                else -> liveData.postValue(NetworkState.LOADED)
            }
        }
    })
    return liveData
}