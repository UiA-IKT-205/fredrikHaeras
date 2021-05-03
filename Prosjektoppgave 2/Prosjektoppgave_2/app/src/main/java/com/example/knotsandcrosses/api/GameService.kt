package com.example.knotsandcrosses.api

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.knotsandcrosses.App
import com.example.knotsandcrosses.R
import com.example.knotsandcrosses.api.data.Game
import com.example.knotsandcrosses.api.data.GameState
import com.google.gson.Gson
import org.json.JSONObject

typealias GameServiceCallback = (state: Game?, errorCode:Int?) -> Unit

object GameService {
    private val context = App.context

    private val requestQueue:RequestQueue = Volley.newRequestQueue(context)

    private enum class APIEndpoints(val url:String){
        CREATE_GAME("%1s%2s%3s".format(context.getString(R.string.protocol), context.getString(R.string.domain), context.getString(R.string.base_path)))
    }

    fun createGame(playerId:String, state:GameState, callback:GameServiceCallback){
        val url = APIEndpoints.CREATE_GAME.url

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state", state)

        val request = object:JsonObjectRequest(Request.Method.POST, url, requestData,
            {
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                callback(game, null)
            }, {
                callback(null, it.networkResponse.statusCode)
            } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }

        requestQueue.add(request)
    }

    fun joinGame(playerId:String, gameId:String, callback:GameServiceCallback){

    }

    fun updateGame(gameId:String, gameState:GameState, callback: GameServiceCallback){

    }

    fun pollGame(gameId:String, callback:GameServiceCallback){

    }
}