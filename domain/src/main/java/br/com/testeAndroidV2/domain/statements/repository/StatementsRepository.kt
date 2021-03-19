package br.com.testeAndroidV2.domain.statements.repository

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.statements.model.Statements
import io.reactivex.rxjava3.core.Single

interface StatementsRepository {
    fun fetchStatementsByUserId(userId: Int): Single<Pair<ErrorResponse, List<Statements>>>
}