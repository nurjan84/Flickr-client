package com.nurzhan.flickr.dagger.modules

import android.content.Context
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.nurzhan.flickr.BuildConfig
import com.nurzhan.flickr.api.CacheProviders
import com.nurzhan.flickr.dagger.scopes.AppScope
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.*
import okhttp3.internal.platform.Platform
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {

    @AppScope
    @Provides
    fun rxCache(context: Context): CacheProviders {
        return RxCache.Builder()
            .setMaxMBPersistenceCache(5)
            .persistence(context.cacheDir, GsonSpeaker())
            .using(CacheProviders::class.java)
    }

    @AppScope
    @Provides
    fun interceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            requestBuilder.addHeader("Accept", "application/json;version=v1.0;charset=utf-8")
            val response = chain.proceed(requestBuilder.build())

            response
        }
    }


    @AppScope
    @Provides
    fun loggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .loggable(BuildConfig.SHOW_LOG)
            .setLevel(Level.BODY)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()
    }

    @AppScope
    @Provides
    fun specs(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }


    @AppScope
    @Provides
    fun okHttpClient(spec: ConnectionSpec, interceptor: Interceptor, httpLoggingInterceptor: LoggingInterceptor, context: Context): OkHttpClient {
        /* if (android.os.Build.VERSION.SDK_INT < 21) {
            try {
                ProviderInstaller.installIfNeeded(context)
                val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
                sslContext.init(null, null, null)
                sslContext.createSSLEngine()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
        return OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(interceptor)
            //.connectionSpecs(Collections.singletonList(spec))
            .build()
    }


}