package br.com.testeAndroidV2.domain.statements.interactor

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.InteractorCompletable
import br.com.testeAndroidV2.domain.InteractorSingle
import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.statements.model.Statements
import br.com.testeAndroidV2.domain.statements.repository.StatementsRepository
import io.reactivex.rxjava3.core.Single

class StatementsInteractor
    (private val statementsRepository: StatementsRepository, schedulers: Schedulers) :
    InteractorSingle<Pair<ErrorResponse, List<Statements>>, StatementsInteractor.Request>(schedulers) {


    override fun create(request: Request): Single<Pair<ErrorResponse, List<Statements>>> {
        return statementsRepository.fetchStatementsByUserId(request.getUserId())
    }

    inner class Request(private val userId: Int) : InteractorCompletable.Request() {
        fun getUserId() = userId
    }
}