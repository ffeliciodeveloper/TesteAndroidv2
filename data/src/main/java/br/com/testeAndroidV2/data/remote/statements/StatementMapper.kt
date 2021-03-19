package br.com.testeAndroidV2.data.remote.statements

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.statements.model.Statements

class StatementMapper {
    fun statementsEntityToStatements(statementsResponse: StatementsResponse): Pair<ErrorResponse, List<Statements>> {

        val statementsList = mutableListOf<Statements>()
        statementsResponse.statements.forEach { statementsEntity ->
                statementsList.add(
                Statements(
                    statementsEntity.title,
                    statementsEntity.desc,
                    statementsEntity.date,
                    statementsEntity.value
                )
            )
        }

        val error = ErrorResponse(
            statementsResponse.errorResponse.code,
            statementsResponse.errorResponse.message
        )

        return Pair(error, statementsList)
    }
}