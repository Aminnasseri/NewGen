package AminAmix.com.socialpersonality.model


import com.google.gson.annotations.SerializedName

data class PType(
    val id: Int,
    @SerializedName("image_female")
    val imageFemale: String,
    @SerializedName("image_male")
    val imageMale: String,
    val shortDesc: String,
    val title: String
)