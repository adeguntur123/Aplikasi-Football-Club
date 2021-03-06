package com.ade.kotlin.presenter

import com.ade.kotlin.api.API
import com.ade.kotlin.api.ApiObject
import com.ade.kotlin.model.PlayerDetailResponse
import com.google.gson.Gson
import com.ade.kotlin.CCProvider;
import com.ade.kotlin.view.PemainDetailInterface
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PemainDetailPresenter(private val anInterface: PemainDetailInterface,
                            private val apiRepository: API,
                            private val gson: Gson, private val context: CCProvider = CCProvider()) {

    fun getPlayerList(idTeam: String?) {
        anInterface.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(ApiObject.getPlayerDetail(idTeam)),
                        PlayerDetailResponse::class.java
                )
            }
            anInterface.showPlayerDetail(data.await().playerDetails)
            anInterface.hideLoading()
        }
    }
}