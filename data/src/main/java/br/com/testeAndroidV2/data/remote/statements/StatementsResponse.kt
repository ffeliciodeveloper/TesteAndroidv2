package br.com.testeAndroidV2.data.remote.statements

import br.com.testeAndroidV2.data.remote.ErrorResponse
import com.google.gson.annotations.SerializedName

class StatementsResponse(
    @SerializedName("error") val errorResponse: ErrorResponse,
    @SerializedName("statementList" ) val statements: List<StatementsEntity>
)
