package br.com.testeAndroidV2.domain.interactor

import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.mocks.StatementsMock
import br.com.testeAndroidV2.domain.statements.interactor.StatementsInteractor
import br.com.testeAndroidV2.domain.statements.repository.StatementsRepository
import br.com.testeAndroidV2.domain.util.TestScheduler
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StatementsInteractorTest {

    @Mock
    lateinit var statementsRepository: StatementsRepository

    lateinit var scheduler: Schedulers

    lateinit var statementsInteractor: StatementsInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        scheduler = TestScheduler()
        statementsInteractor = StatementsInteractor(statementsRepository, scheduler)
    }

    @Test
    fun `When fetch statements is successful should be return a pair of list statements or error statement`() {

        val statementsList = StatementsMock.getListStatements()
        val errorResponse = mock(ErrorResponse::class.java)
        val pairResponse = Pair(errorResponse, statementsList)

        `when`(statementsRepository.fetchStatementsByUserId(anyInt())).thenReturn(Single.just(pairResponse))
        val result = statementsInteractor.execute(statementsInteractor.Request(152)).test()

        result
            .assertNoErrors()
            .assertValue(pairResponse)
            .assertComplete()

        verify(statementsRepository).fetchStatementsByUserId(152)
    }

    @Test
    fun `When fetch statements is unsuccessful should be return an exception`() {

        val exception = Exception()

        `when`(statementsRepository.fetchStatementsByUserId(anyInt())).thenReturn(Single.error(exception))
        val result = statementsInteractor.execute(statementsInteractor.Request(152)).test()

        result
            .assertNoValues()
            .assertError(exception)
            .assertNotComplete()

        verify(statementsRepository).fetchStatementsByUserId(152)
    }
}