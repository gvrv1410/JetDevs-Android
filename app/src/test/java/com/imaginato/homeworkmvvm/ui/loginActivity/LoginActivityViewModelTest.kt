package com.imaginato.homeworkmvvm.ui.loginActivity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.imaginato.homeworkmvvm.data.local.RoomDb.User
import com.imaginato.homeworkmvvm.data.local.RoomDb.UserDao
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.Data
import com.imaginato.homeworkmvvm.ui.loginActivity.EntityLogin.LoginMainEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class LoginActivityViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var loginRepository: LoginRepository

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var loginLiveDataObserver: Observer<LoginMainEntity>

    @Mock
    private lateinit var exceptionLiveDataObserver: Observer<Throwable>

    private lateinit var viewModel: LoginActivityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = LoginActivityViewModel(loginRepository, userDao)
        viewModel.loginLiveData.observeForever(loginLiveDataObserver)
        viewModel.exceptionLiveData.observeForever(exceptionLiveDataObserver)
    }

    @Test
    fun `check loginUserRepo`() = runTest {
        // Arrange
        val params = mapOf("username" to "testUser", "password" to "password123")
        val loginMainEntity = LoginMainEntity(
            data = Data(isDeleted = true,userId = "1", userName = "testUser"),
            errorMessage = "Success",
            errorCode = "00"
        )

        `when`(loginRepository.loginUser(params)).thenReturn(loginMainEntity)

        // Act
        viewModel.userLogin(params)

        // Assert
        verify(loginRepository).loginUser(params)
    }


    @Test
    fun `check for no exeption`() = runTest {
        // Arrange
        val params = mapOf("username" to "testUser", "password" to "password123")
        val loginMainEntity = LoginMainEntity(
            data = Data(isDeleted = true,userId = "1", userName = "testUser"),
            errorMessage = "Success",
            errorCode = "00"
        )
        `when`(loginRepository.loginUser(params)).thenReturn(loginMainEntity)

        // Act
        viewModel.userLogin(params)

        // Assert
        verifyNoInteractions(exceptionLiveDataObserver)
    }



}

@ExperimentalCoroutinesApi
class CoroutineTestRule(val dispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
        cleanupTestCoroutines()
    }
}

//@ExperimentalCoroutinesApi
//class CoroutineTestRule : TestRule {
//
//    private val testDispatcher = TestCoroutineDispatcher()
//    val testScope = TestCoroutineScope(testDispatcher)
//
//    override fun apply(base: Statement, description: Description): Statement {
//        return object : Statement() {
//            @Throws(Throwable::class)
//            override fun evaluate() {
//                Dispatchers.setMain(testDispatcher) // Set the main dispatcher to the test dispatcher
//                try {
//                    base.evaluate()
//                } finally {
//                    Dispatchers.resetMain() // Reset the main dispatcher after the test is done
//                    testDispatcher.cleanupTestCoroutines()
//                }
//            }
//        }
//    }
//}