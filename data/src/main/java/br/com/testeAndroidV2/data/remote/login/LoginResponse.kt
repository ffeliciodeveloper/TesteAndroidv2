package br.com.testeAndroidV2.data.remote.login

import br.com.testeAndroidV2.data.remote.ErrorResponse
import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("userAccount") val userEntity: UserEntity,
    @SerializedName("error") val errorResponse: ErrorResponse
)
