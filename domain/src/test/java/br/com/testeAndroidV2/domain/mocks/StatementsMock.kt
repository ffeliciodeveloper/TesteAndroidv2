package br.com.testeAndroidV2.domain.mocks

import br.com.testeAndroidV2.domain.statements.model.Statements

class StatementsMock {
    companion object {
        fun getListStatements() =
            listOf(Statements("Pagamento", "Conta de Luz", "2018-08-15", 745.03))
    }
}