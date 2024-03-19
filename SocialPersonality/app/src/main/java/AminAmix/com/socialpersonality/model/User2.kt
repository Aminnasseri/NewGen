package AminAmix.com.socialpersonality.model


data class User2(
    val emailAddress: String,
    val emailStatus: String,
    val fullName: String,
    val id: Int,
    val lastSeenAt: Long,
    val pType: PType,
    val profileImage: String,
    val sex: String,
    val source: String
)