package wtf.qase.appskeleton.example.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import wtf.qase.appskeleton.example.BuildConfig
import wtf.qase.appskeleton.example.repository.user.dto.api.UserApi

val networkModule = module {
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideLoginApi(get()) }
}

fun provideGson(): Gson = GsonBuilder()
    .excludeFieldsWithoutExposeAnnotation()
    .create()

fun provideOkHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor {
            val request = it.request()
            val requestBuilder = request.newBuilder()
            if (request.header("Authorization") == null) {
                requestBuilder.addHeader("Authorization", "Bearer accessToken")
            }

            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("Content-Type", "application/json")
            return@addInterceptor it.proceed(requestBuilder.build())
        }
        .build()
}

fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.SERVER_BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

fun provideLoginApi(retrofit: Retrofit): UserApi = retrofit.create(
    UserApi::class.java)
