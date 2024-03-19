package AminAmix.com.socialpersonality.WebService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class APIClient {
    companion object {
        const val BASE_URL = "http://185.97.117.195:1337/api/v1/"
    }
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Companion.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
        return retrofit
    }



}