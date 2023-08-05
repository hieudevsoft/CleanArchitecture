package hieubui.eup.base_clean_architecture.data.datasources.remote

import android.util.Log
import com.google.gson.Gson
import hieubui.eup.base_clean_architecture.common.Constants
import hieubui.eup.base_clean_architecture.common.DataSource
import hieubui.eup.base_clean_architecture.common.readBody
import hieubui.eup.base_clean_architecture.data.exceptions.GeneralException
import hieubui.eup.base_clean_architecture.data.exceptions.NoInternetException
import hieubui.eup.base_clean_architecture.data.exceptions.ParserException
import hieubui.eup.base_clean_architecture.data.exceptions.ServerException
import hieubui.eup.base_clean_architecture.data.models.ResponseCatApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException
import java.util.Locale

interface CatRemoteSource {
    suspend fun getCatImage(limit: Int): DataSource<ResponseCatApi>
}

class CatRemoteSourceImpl : CatRemoteSource {
    override suspend fun getCatImage(limit: Int): DataSource<ResponseCatApi> {
        val getUrl = String.format(Locale.ENGLISH, Constants.URL.API_GET_CAT, limit)
        val url = URL(getUrl)
        return withContext(Dispatchers.IO) {
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "GET"
                connection.connect()
                val responseCode = connection.responseCode
                if (responseCode != 200) {
                    DataSource.Error<ResponseCatApi>(ServerException())
                }
                val body = connection.inputStream.readBody()
                try {
                    val responseCat = Gson().fromJson(body, ResponseCatApi::class.java)
                    DataSource.Success(responseCat)
                } catch (e: Exception) {
                    DataSource.Error(ParserException())
                }
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    DataSource.Error(NoInternetException())
                } else {
                    DataSource.Error(GeneralException())
                }
            } finally {
                connection.disconnect()
            }
        }
    }

}