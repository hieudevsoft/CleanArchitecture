package hieubui.eup.base_clean_architecture.presentation.states

data class CatsState (
    val isLoading:Boolean = false,
    val images:List<String>?=null,
    val error:String?=null
){
    companion object{
        fun initialize() = CatsState()
    }
}