package AminAmix.com.socialpersonality.model


import com.google.gson.annotations.SerializedName

data class MbtiResult(
    val id: Int,
    @SerializedName("image_female")
    val imageFemale: String,
    @SerializedName("image_male")
    val imageMale: String,
    val shortDesc: String,
    val career: String,
    val strengths: List<Strenght>,
    val title: String,
    val weaknesses: List<WeaknesseX>
)