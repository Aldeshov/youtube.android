package com.aldeshov.youtube.service

import com.aldeshov.youtube.BASE_URL
import com.aldeshov.youtube.YouTube
import com.aldeshov.youtube.service.database.Database
import com.aldeshov.youtube.service.network.APIService
import com.aldeshov.youtube.service.network.AuthInterceptor
import com.aldeshov.youtube.service.repositories.*
import com.aldeshov.youtube.ui.activity.main.MainViewModel
import com.aldeshov.youtube.ui.activity.main.account.AccountViewModel
import com.aldeshov.youtube.ui.activity.authenticate.login.LoginViewModel
import com.aldeshov.youtube.ui.activity.authenticate.signup.SignupViewModel
import com.aldeshov.youtube.ui.activity.main.account.ProfileViewModel
import com.aldeshov.youtube.ui.activity.main.contents.ListViewModel
import com.aldeshov.youtube.ui.activity.main.content.ContentViewModel
import com.aldeshov.youtube.ui.activity.main.content.CommentsViewModel
import com.aldeshov.youtube.ui.activity.main.drawer.DrawerViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    fun apiProvider(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    factory { apiProvider(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<ContentRepository> { ContentRepositoryImpl(get()) }
    single<ChannelRepository> { ChannelRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
    viewModel { DrawerViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ListViewModel(get(), get()) }
    viewModel { ContentViewModel(get(), get()) }
    viewModel { CommentsViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

val dbModule = module {
    fun dbProvider(): Database? {
        return YouTube.instance.getDatabase()
    }

    single { dbProvider() }
}

val apiModule = module {
    fun loggingInterceptorProvider(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return loggingInterceptor
    }

    fun httpClientProvider(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    single { loggingInterceptorProvider() }
    factory { AuthInterceptor(get()) }
    factory { httpClientProvider(get(), get()) }
    single { retrofitProvider(get()) }
}