package com.ruth.articleapplication.network.retrofit;


import android.app.Application;

import androidx.arch.core.internal.SafeIterableMap;

import com.ruth.articleapplication.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ruth.articleapplication.utils.Constants.CACHE_CONTROL_HEADER;
import static com.ruth.articleapplication.utils.Constants.CACHE_CONTROL_NO_CACHE;
import static com.ruth.articleapplication.utils.Constants.CACHE_SIZE;


public class NetworkClient {
    public final static String BASE_URL = BuildConfig.SERVER_URL;
    private volatile static ApiService retrofit = null;
    private NetworkClient() {
    }

    public static ApiService getInstance(Application application) {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
                okHttpClient.addInterceptor(interceptor);
                okHttpClient.addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build();
                    Response originalResponse = chain.proceed(request);
                    boolean shouldUseCache = request.header(CACHE_CONTROL_HEADER)!= null  && !request.header(CACHE_CONTROL_HEADER).equals(CACHE_CONTROL_NO_CACHE);
                    if (!shouldUseCache) {
                        return originalResponse;
                    }

                    return originalResponse.newBuilder()
                            .addHeader(CACHE_CONTROL_HEADER, "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                            .build();
                });
                okHttpClient.cache(new Cache(application.getCacheDir(),CACHE_SIZE));
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .client(okHttpClient.build())
                        .build().create(ApiService.class);
            }
        }
        return retrofit;
    }


}
