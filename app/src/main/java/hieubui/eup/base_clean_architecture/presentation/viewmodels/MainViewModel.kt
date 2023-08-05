package hieubui.eup.base_clean_architecture.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hieubui.eup.base_clean_architecture.data.datasources.local.CatLocalSource
import hieubui.eup.base_clean_architecture.data.datasources.remote.CatRemoteSourceImpl
import hieubui.eup.base_clean_architecture.data.repositories.CatRepositoryImpl
import hieubui.eup.base_clean_architecture.domain.entities.CatEntity
import hieubui.eup.base_clean_architecture.domain.repositories.CatRepository
import hieubui.eup.base_clean_architecture.domain.useceses.GetCatUseCase
import hieubui.eup.base_clean_architecture.presentation.states.CatsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    application:Application,
):AndroidViewModel(application = application) {
    private val getCatUserCase = GetCatUseCase(
        catRepository = CatRepositoryImpl(
            remoteLocalSource = CatRemoteSourceImpl()
        )
    )
    private val _catImages = MutableStateFlow(CatsState.initialize())
    val catImages = _catImages.asStateFlow()

    fun getCatImages(limit:Int = 10){
        viewModelScope.launch(Dispatchers.IO){
            _catImages.update { it.copy(isLoading = true) }
            val entities = getCatUserCase.getCatImages(limit)
            _catImages.update { it.copy(isLoading = false,images = entities.map { it.url }) }
        }
    }
}