package wtf.qase.appskeleton.example

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Response
import wtf.qase.appskeleton.example.main.settings.SettingsViewModel
import wtf.qase.appskeleton.example.repo.AppPreferences
import wtf.qase.appskeleton.example.repo.api.UserApi
import wtf.qase.appskeleton.example.repo.dto.LoginRequest
import wtf.qase.appskeleton.example.repo.dto.LoginResponse

@RunWith(AndroidJUnit4::class)
class SettingsTest : KoinTest {

    private val preferences: AppPreferences by inject()

    @Mock
    private lateinit var userApi: UserApi

    private lateinit var viewModel: SettingsViewModel

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }

        viewModel = SettingsViewModel(preferences, userApi)
    }

    @After
    fun clean() {
        RxJavaPlugins.reset()
    }

    @Test
    fun prefs() {
        assertEquals("true, 1234 as int", viewModel.prefs)
    }

    @Test
    fun login() {
        `when`(userApi.login(LoginRequest("a", "b")))
            .thenReturn(Single.just(Response.success(LoginResponse("a", 1))))

        val response = viewModel.login("a", "b").blockingGet()
        assertTrue(response.isSuccessful)
        assertEquals("a", response.body()?.accessToken)
        assertEquals(1L, response.body()?.valid)
    }

    @Test
    fun logout() {
        `when`(userApi.logout())
            .thenReturn(Single.just(Response.success(Unit)))

        viewModel.logout().test()
            .assertValue { it.isSuccessful }
            .assertComplete()
    }
}
