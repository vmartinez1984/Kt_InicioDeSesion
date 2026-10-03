package xyz.vmartinez.iniciodesesion.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import xyz.vmartinez.iniciodesesion.models.InicioDeSesionModel
import xyz.vmartinez.iniciodesesion.models.TokenModel

class InicioDeSesionService {

    var BASE_URL = "https://35gzj4wm-3000.usw3.devtunnels.ms/"
    private val apiService: ApiService

    //contructor
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun iniciarSesion(inicioDeSesionModel: InicioDeSesionModel, callback: ApiCallback<TokenModel>){

        val call = apiService.iniciarSesion(inicioDeSesionModel)
        call.enqueue(object : Callback<TokenModel?> {
            override fun onResponse(call: Call<TokenModel?>, response: Response<TokenModel?>) {
                val esCorrecta = response.isSuccessful()
                var tokenModel =  TokenModel("")
                if(esCorrecta){
                    //
                    tokenModel = response.body() as TokenModel
                }else{
                    //Token vacio, credenciales incorrecta
                }
                callback.onSuccess(tokenModel)
            }

            override fun onFailure(call: Call<TokenModel?>, t: Throwable) {
                callback.onError(t.message?: "Error")
            }
        })
    }

    private interface  ApiService{
        @POST("InicioDeSesion")
        fun iniciarSesion(@Body inicioDeSesionModel: InicioDeSesionModel): Call<TokenModel>
    }
}