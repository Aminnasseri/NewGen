package AminAmix.com.socialpersonality.WebService

import AminAmix.com.socialpersonality.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface APIInterface {
    @FormUrlEncoded
    @POST("user/entrance")
    fun registerUser(@Header("secureKey") secureKey:String,@Field("emailAddress") email: String): retrofit2.Call<ResponseRegister>

    @FormUrlEncoded
    @POST("user/entrance/oauth")
    fun registerUserOauth(
        @Header("secureKey") secureKey:String,
        @Field("emailAddress") email: String,
        @Field("fullName") fullName: String,
        @Field("profileImage") profileImage: String,
        @Field("sex") sex: String,
        @Field("source") source: String
    ): retrofit2.Call<Users>


    @FormUrlEncoded
    @POST("user/confirm")
    fun confirmUser(@Header("authorization") token:String, @Field("amount") amount: String): retrofit2.Call<ResponseRegister2>


    @GET("user/profile")
    fun getUser(@Header("authorization") token:String): retrofit2.Call<User2>

    @GET("question/list")
    fun getQuestions(@Header("authorization") token:String): retrofit2.Call<QuestionList>

    @FormUrlEncoded
    @POST("user/personal-info")
    fun postInfo(@Header("authorization") token:String, @Field("fullName") fullName: String,@Field("sex") sex: String ): retrofit2.Call<Users>

    @FormUrlEncoded
    @POST("user/send-otp")
    fun reSend(@Header("authorization") token:String, @Field("emailAddress") emailAddress: String): retrofit2.Call<ResponseRegister2>

    @FormUrlEncoded
    @POST("quiz/take")
    fun getResult(@Header("authorization") token:String, @Field("data") data: String): retrofit2.Call<TestResultResponse>




    @Multipart
    @POST("upload")
    fun updateProfile(
        @Header("authorization") token: String?,
        @Part("type") type: RequestBody,
        @Part image: MultipartBody.Part?,
    ): retrofit2.Call<ImagePath>


    @GET("personality")
    fun personalityDes(@Header("authorization") token:String,@Query("id") id: String): retrofit2.Call<MbtiResult>





}