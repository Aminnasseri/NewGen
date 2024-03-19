package AminAmix.com.socialpersonality.model


data class Users(
    val emailAddress: String,
    val emailStatus: String,
    val fullName: String,
    val code: String,
    val id: Int,
    val message: String,
    val lastSeenAt: Long,
    val personalityType: String,
    val profileImage: String,
    val sex: String,
    val source: String,
    val token: String,
    val resId: Int
)