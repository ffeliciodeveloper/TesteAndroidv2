package br.com.testeAndroidV2.data.repositories

import br.com.testeAndroidV2.data.remote.api.TesteAndroidV2Service
import br.com.testeAndroidV2.data.remote.login.LoginRepositoryImpl
import br.com.testeAndroidV2.data.remote.login.LoginResponse
import br.com.testeAndroidV2.data.remote.login.UserMapper
import br.com.testeAndroidV2.domain.ErrorResponse
import br.com.testeAndroidV2.domain.user.model.User
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {
    @Mock
    lateinit var testeAndroidV2Service: TesteAndroidV2Service

    @Mock
    lateinit var userMapper: UserMapper

    lateinit var loginRepositoryImpl: LoginRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginRepositoryImpl =
            LoginRepositoryImpl(testeAndroidV2Service, userMapper)
    }

    @Test
    fun `Should return an user from api when login is successful`() {
        val loginResponse = mock(LoginResponse::class.java)
        val user = mock(User::class.java)
        val errorResponse = mock(ErrorResponse::class.java)

        val pairResponse = Pair(user, errorResponse)


        `when`(testeAndroidV2Service.fetchUserByLoginAndPassword(anyString(), anyString())).thenReturn(
            Single.just(
                loginResponse
            )
        )

        `when`(userMapper.userEntityToUser(loginResponse)).thenReturn(pairResponse)

        val result = loginRepositoryImpl.fetchUserByLoginAndPassword("test_user", "Test@1").test()

        result
            .assertComplete()
            .assertNoErrors()
            .assertValue(pairResponse)

        verify(testeAndroidV2Service).fetchUserByLoginAndPassword("test_user", "Test@1")
    }

    @Test
    fun `Should return an exception from api when login is unsuccessful`() {
        val exception = Exception()
        `when`(testeAndroidV2Service.fetchUserByLoginAndPassword(anyString(), anyString())).thenReturn(
            Single.error(
                exception
            )
        )
        val result = loginRepositoryImpl.fetchUserByLoginAndPassword("test_user", "Test@1").test()

        result
            .assertNotComplete()
            .assertNoValues()
            .assertError(exception)

        verify(testeAndroidV2Service).fetchUserByLoginAndPassword("test_user", "Test@1")
    }
}