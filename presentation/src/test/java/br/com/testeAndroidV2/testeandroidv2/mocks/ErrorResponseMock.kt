package br.com.testeAndroidV2.testeandroidv2.br.com.testeAndroidV2.testeandroidv2.mocks

import br.com.testeAndroidV2.domain.ErrorResponse

class ErrorResponseMock {
    companion object {
        fun getErrorResponseIsEmpty() = ErrorResponse(0,"")
        fun getErrorResponseIsNotEmpty() = ErrorResponse(0,"Teste")
    }
}