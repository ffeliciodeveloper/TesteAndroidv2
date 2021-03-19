package br.com.testeAndroidV2.data.repositories

import br.com.testeAndroidV2.data.remote.api.TesteAndroidV2Service
import br.com.testeAndroidV2.data.remote.statements.StatementMapper
import br.com.testeAndroidV2.data.remote.statements.StatementsRepositoryImpl
import br.com.testeAndroidV2.data.remote.statements.StatementsResponse
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.statements.model.Statements
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StatementsRepositoryImplTest {
    @Mock
    lateinit var testeAndroidV2Service: TesteAndroidV2Service

    @Mock
    lateinit var statementMapper: StatementMapper

    lateinit var statementsRepositoryImpl: StatementsRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        statementsRepositoryImpl = StatementsRepositoryImpl(testeAndroidV2Service, statementMapper)
    }

    @Test
    @Throws(Exception::class)
    fun `Should return a list of statements from api when fetch is successful`() {
        val statementResponse = mock(StatementsResponse::class.java)

        val errorResponse = mock(ErrorResponse::class.java)
        val statements = mock(Statements::class.java)
        val list = listOf(statements)

        val pairResponse = Pair(errorResponse, list)


        `when`(testeAndroidV2Service.fetchStatementsByUserId(anyInt())).thenReturn(
            Single.just(
                statementResponse
            )
        )
        `when`(statementMapper.statementsEntityToStatements(statementResponse)).thenReturn(
            pairResponse
        )


        val result = statementsRepositoryImpl.fetchStatementsByUserId(152).test()

        result
            .assertNoErrors()
            .assertValue(pairResponse)
            .assertComplete()

        verify(testeAndroidV2Service).fetchStatementsByUserId(152)
    }

    @Test
    @Throws(Exception::class)
    fun `Should return a list of statements from api when fetch is unsuccessful`() {
        val exception = Exception()

        `when`(testeAndroidV2Service.fetchStatementsByUserId(anyInt())).thenReturn(
            Single.error(
                exception
            )
        )

        val result = statementsRepositoryImpl.fetchStatementsByUserId(152).test()

        result
            .assertNoValues()
            .assertError(exception)
            .assertNotComplete()

        verify(testeAndroidV2Service).fetchStatementsByUserId(152)
    }
}