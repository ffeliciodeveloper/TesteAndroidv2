package br.com.testeAndroidV2.data.remote.api

import br.com.testeAndroidV2.data.remote.login.LoginResponse
import br.com.testeAndroidV2.data.remote.statements.StatementsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*


interface TesteAndroidV2Service {

    @FormUrlEncoded
    @POST("login")
    fun fetchUserByLoginAndPassword(
        @Field("user") login: String,
        @Field("password") password: String
    ): Single<LoginResponse>

    @GET("statements/{userId}")
    fun fetchStatementsByUserId(
        @Path("userId") userId: Int,
    ): Single<StatementsResponse>
}