package hieubui.eup.base_clean_architecture.domain.useceses

import hieubui.eup.base_clean_architecture.common.EntitySource
import hieubui.eup.base_clean_architecture.domain.entities.CatEntity
import hieubui.eup.base_clean_architecture.domain.repositories.CatRepository

class GetCatUseCase(
    private val catRepository:CatRepository
) {
    suspend fun getCatImages(limit:Int):List<CatEntity>{
        val entitySource = catRepository.getCatImage(limit)
        if(entitySource is EntitySource.Success){
            return entitySource.data
        }
        return emptyList()
    }
}