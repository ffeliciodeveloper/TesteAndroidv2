package br.com.testeAndroidV2.domain.interactor

import br.com.testeAndroidV2.domain.Schedulers
import br.com.testeAndroidV2.domain.mocks.UserMock
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.interactor.LoginInteractor
import br.com.testeAndroidV2.domain.user.repository.LoginRepository
import br.com.testeAndroidV2.domain.util.TestScheduler
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginInteractorTest {
    @Mock
    lateinit var loginRepository: LoginRepository
    lateinit var testScheduler: Schedulers

    lateinit var loginInteractor: LoginInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        loginInteractor = LoginInteractor(loginRepository, testScheduler)
    }

    @Test
    fun `When call to repository and fetch is successful should be return an user`() {
        val user = UserMock.getUserMock()
        val loginError = mock(ErrorResponse::class.java)

        val pairResult = Pair(user, loginError)
        `when`(loginRepository.fetchUserByLoginAndPassword(anyString(), anyString())).thenReturn(Single.just(pairResult))
        val result = loginInteractor
            .execute(loginInteractor.Request("Teste", "1234"))
            .test()

        result
            .assertComplete()
            .assertNoErrors()
            .assertValue(pairResult)

        verify(loginRepository).fetchUserByLoginAndPassword("Teste", "1234")
    }

    @Test
    fun `When call to repository and fetch is fail should be return an user`() {
        val exception = Exception()
        `when`(loginRepository.fetchUserByLoginAndPassword(anyString(), anyString())).thenReturn(
            Single.error(
                exception
            )
        )
        val result = loginInteractor
            .execute(loginInteractor.Request("Teste", "1234"))
            .test()

        result
            .assertNotComplete()
            .assertNoValues()
            .assertError(exception)

        verify(loginRepository).fetchUserByLoginAndPassword("Teste", "1234")
    }
}