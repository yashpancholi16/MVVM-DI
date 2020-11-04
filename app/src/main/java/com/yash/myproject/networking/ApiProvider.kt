package com.yash.myproject.networking

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.yash.myproject.utils.md5
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ApiProvider : IApiProvider, Interceptor {

    companion object {
        private const val BASE_URL = "http://gateway.marvel.com/v1/public/"
        private const val TIMEOUT_DELAY_SECONDS = 30L
        private const val PRIVATE_KEY = "ab754eb8f5f5a3a4d634a17730b5e59299635b87"
        private const val API_KEY = "aec5a521a0e390daaa47e59714c6f5b0"
    }


    override val getApi: ISuperHerosApi = getRetrofit().create(ISuperHerosApi::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()

        val url = original.url.newBuilder()
            .addQueryParameter("apikey", API_KEY)
            .addQueryParameter("ts", ts)
            .addQueryParameter("hash", "$ts$PRIVATE_KEY$API_KEY".md5())
            .build()
        val request = original.newBuilder()
            .method(original.method, original.body)
            .url(url)
            .build()

        return chain.proceed(request)
    }

    private fun getLoggingClient(): OkHttpClient =
        getUnsafeOkHttpClient().addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }).addInterceptor(this)
            .connectTimeout(TIMEOUT_DELAY_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_DELAY_SECONDS, TimeUnit.SECONDS)
            .build()


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getLoggingClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Suppress("TooGenericExceptionCaught")
    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) = Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) = Unit

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.retryOnConnectionFailure(true)
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
            return builder
        } catch (e: Exception) {
            // TODO add handling
            return OkHttpClient.Builder()
        }
    }
}