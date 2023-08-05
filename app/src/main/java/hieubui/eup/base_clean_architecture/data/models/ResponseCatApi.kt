package hieubui.eup.base_clean_architecture.data.models


import com.google.gson.annotations.SerializedName

class ResponseCatApi : ArrayList<ResponseCatApiItem>()

data class ResponseCatApiItem(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("width")
    val width: Int? = null
)