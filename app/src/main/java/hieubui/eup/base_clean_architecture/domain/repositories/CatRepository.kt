package hieubui.eup.base_clean_architecture.domain.repositories

import hieubui.eup.base_clean_architecture.common.DataSource
import hieubui.eup.base_clean_architecture.common.EntitySource
import hieubui.eup.base_clean_architecture.data.models.ResponseCatApi
import hieubui.eup.base_clean_architecture.domain.entities.CatEntity

interface CatRepository {
    suspend fun getCatImage(limit:Int):EntitySource<List<CatEntity>>
}