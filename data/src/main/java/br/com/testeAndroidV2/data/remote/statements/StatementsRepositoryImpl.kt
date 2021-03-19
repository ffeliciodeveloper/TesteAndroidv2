package br.com.testeAndroidV2.data.remote.statements

import br.com.testeAndroidV2.data.remote.api.TesteAndroidV2Service
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.statements.model.Statements
import br.com.testeAndroidV2.domain.statements.repository.StatementsRepository
import io.reactivex.rxjava3.core.Single

class StatementsRepositoryImpl(
    private val testeAndroidv2Service: TesteAndroidV2Service,
    private val statementMapper: StatementMapper
) : StatementsRepository {
    override fun fetchStatementsByUserId(userId: Int): Single<Pair<ErrorResponse, List<Statements>>> {
        return testeAndroidv2Service.fetchStatementsByUserId(userId)
            .map { statementResponse ->
                statementMapper.statementsEntityToStatements(statementResponse)
            }

    }
}