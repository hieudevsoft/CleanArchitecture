package hieubui.eup.base_clean_architecture.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import hieubui.eup.base_clean_architecture.R
import hieubui.eup.base_clean_architecture.data.datasources.remote.CatRemoteSourceImpl
import hieubui.eup.base_clean_architecture.databinding.ActivityMainBinding
import hieubui.eup.base_clean_architecture.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    private val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        observerData()
    }

    private fun setOnClick(){
        binding.apply {
            btnLoad.setOnClickListener {
                mainViewModel.getCatImages(10)
            }
        }
    }

    private fun observerData(){
        lifecycle.coroutineScope.launch {
            mainViewModel.catImages
                .collectLatest {
                    if(it.isLoading){
                        binding.loading.visibility = View.VISIBLE
                        binding.tvData.visibility = View.GONE
                    }
                    if(!it.images.isNullOrEmpty()&&!it.isLoading){
                        binding.loading.visibility = View.GONE
                        binding.tvData.visibility = View.VISIBLE
                        binding.tvData.text = it.images.joinToString("\n")
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}