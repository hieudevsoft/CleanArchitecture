package hieubui.eup.base_clean_architecture.data.repositories

import android.util.Log
import hieubui.eup.base_clean_architecture.common.DataSource
import hieubui.eup.base_clean_architecture.common.EntitySource
import hieubui.eup.base_clean_architecture.common.toCatEntity
import hieubui.eup.base_clean_architecture.common.toFailure
import hieubui.eup.base_clean_architecture.data.datasources.local.CatLocalSource
import hieubui.eup.base_clean_architecture.data.datasources.remote.CatRemoteSource
import hieubui.eup.base_clean_architecture.data.models.ResponseCatApi
import hieubui.eup.base_clean_architecture.domain.entities.CatEntity
import hieubui.eup.base_clean_architecture.domain.failure.Failure
import hieubui.eup.base_clean_architecture.domain.repositories.CatRepository

class CatRepositoryImpl(
    //catLocalSource:CatLocalSource,
    private val remoteLocalSource:CatRemoteSource,
):CatRepository {
    override suspend fun getCatImage(limit: Int): EntitySource<List<CatEntity>> {
        val dataSource = remoteLocalSource.getCatImage(limit)
        if(dataSource is DataSource.Error){
            return EntitySource.Error(dataSource.exception.toFailure())
        }
        val data = (dataSource as DataSource.Success).data.map { it.toCatEntity() }
        return EntitySource.Success(data)
    }
}