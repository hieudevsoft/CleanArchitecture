package hieubui.eup.base_clean_architecture.common

import hieubui.eup.base_clean_architecture.data.exceptions.NoInternetException
import hieubui.eup.base_clean_architecture.data.exceptions.ParserException
import hieubui.eup.base_clean_architecture.data.exceptions.ServerException
import hieubui.eup.base_clean_architecture.data.models.ResponseCatApiItem
import hieubui.eup.base_clean_architecture.domain.entities.CatEntity
import hieubui.eup.base_clean_architecture.domain.failure.Failure
import hieubui.eup.base_clean_architecture.domain.failure.GeneralFailure
import hieubui.eup.base_clean_architecture.domain.failure.InternetFailure
import hieubui.eup.base_clean_architecture.domain.failure.ParserFailure
import hieubui.eup.base_clean_architecture.domain.failure.ServerFailure
import java.io.InputStream
import kotlin.Exception

sealed class DataSource<out T>(data:T?=null, exception:Exception?=null){
    data class Success<T>(val data:T):DataSource<T>(data, exception = null)
    data class Error<T>(val exception: Exception):DataSource<T>(null,exception)
}

sealed class EntitySource<out T>(data:T?=null,failure:Failure?=null){
    data class Success<T>(val data:T):EntitySource<T>(data, failure = null)
    data class Error<T>(val failure: Failure):EntitySource<T>(null,failure)
}

suspend fun InputStream.readBody(): String {
    return try {
        bufferedReader().use{
            val content = it.readText()
            it.close()
            content
        }
    }catch (e:Exception){
        ""
    }
}

fun ResponseCatApiItem.toCatEntity():CatEntity{
    return CatEntity(
        id = id,
        url = url?:"",
    )
}

fun Exception.toFailure():Failure{
    return when(this){
        is ServerException -> ServerFailure("Loi server")
        is NoInternetException -> InternetFailure("Khong co mang")
        is ParserException -> ParserFailure("Loi parser data")
        else -> GeneralFailure(localizedMessage)
    }
}