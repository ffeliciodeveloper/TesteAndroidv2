package br.com.testeAndroidV2.testeandroidv2.br.com.testeAndroidV2.testeandroidv2.mocks

import br.com.testeAndroidV2.domain.user.model.User

class UserMock {
    companion object {
        fun getUserMock() = User(1, "Jose da Silva Teste", "2050", "012314564", 3.3445)
    }
}