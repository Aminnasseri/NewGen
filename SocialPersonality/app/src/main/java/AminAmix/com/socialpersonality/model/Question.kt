package AminAmix.com.socialpersonality.model


data class Question(
    val answers: List<Answer>,
    val id: Int,
    val priority: Int,
    val tip: String,
    val title: String,
    val type: String
)