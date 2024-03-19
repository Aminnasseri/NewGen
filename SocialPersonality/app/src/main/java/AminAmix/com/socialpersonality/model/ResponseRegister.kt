package AminAmix.com.socialpersonality.model


data class ResponseRegister(
    val emailStatus: String,
    val token: String,
    val message: String,
    val code: String,
    val type: String

)

data class ResponseRegister2(
    val emailStatus: String,
    val token: String,
    val message: String,
    val code: String,
    val type: String
)

