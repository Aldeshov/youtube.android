package com.aldeshov.youtube.ui.activity.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.repositories.UserRepository
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.get
import kotlin.test.assertEquals

//val dataTestModule = module {
//    fun loggingInterceptorProvider(): HttpLoggingInterceptor {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
//        return loggingInterceptor
//    }
//
//    fun httpClientProvider(
//        loggingInterceptor: HttpLoggingInterceptor,
//        authInterceptor: AuthInterceptor
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor(authInterceptor)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .build()
//    }
//
//    fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }
//
//    single { loggingInterceptorProvider() }
//    factory { AuthInterceptor(get()) }
//    factory { httpClientProvider(get(), get()) }
//    single { retrofitProvider(get()) }
//
//    fun dbProvider(): Database? {
//        return YouTube.instance.getDatabase()
//    }
//
//    single { dbProvider() }
//
//    single<UserRepository> { UserRepositoryImpl(get(), get()) }
//}

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        printLogger()
//        modules(dataTestModule)
//    }

    @Test
    fun loadDataTest() {
        val mainViewModel = MainViewModel(get(clazz = UserRepository::class.java))
        assertEquals(LiveStatus.NOTHING, mainViewModel.status)
        mainViewModel.loadData()
        assertEquals(LiveStatus.LOADING, mainViewModel.status)
    }
}