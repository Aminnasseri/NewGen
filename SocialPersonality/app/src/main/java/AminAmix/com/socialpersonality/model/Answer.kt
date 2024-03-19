package AminAmix.com.socialpersonality.model


import com.google.gson.annotations.SerializedName

data class Answer(
    val id: Int,
    val image: String,
    @SerializedName("question_id")
    val questionId: Int,
    val title: String,
    val type: String,
    val value: Int
)