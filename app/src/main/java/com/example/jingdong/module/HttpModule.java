package com.example.jingdong.module;

import com.example.jingdong.net.AdApi;
import com.example.jingdong.net.AdApiService;
import com.example.jingdong.net.Api;
import com.example.jingdong.net.CatagoryApi;
import com.example.jingdong.net.CatagoryApiService;
import com.example.jingdong.net.ChildCatagoryApi;
import com.example.jingdong.net.ChildCatagoryApiService;
import com.example.jingdong.net.MyInterceptor;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.net.NetApiService;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(new MyInterceptor());
    }

    @Provides
    AdApi provideAdApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        AdApiService adApiService = retrofit.create(AdApiService.class);
        return AdApi.getAdApi(adApiService);
    }

    @Provides
    CatagoryApi provideCatagoryApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        CatagoryApiService catagoryApiService = retrofit.create(CatagoryApiService.class);
        return CatagoryApi.getCatagoryApi(catagoryApiService);
    }

    @Provides
    ChildCatagoryApi provideChildCatagoryApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        ChildCatagoryApiService childCatagoryApiService = retrofit.create(ChildCatagoryApiService.class);
        return ChildCatagoryApi.getChildCatagoryApi(childCatagoryApiService);
    }

    @Provides
    NetApi provideNetApi(OkHttpClient.Builder builder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
        NetApiService netApiService = retrofit.create(NetApiService.class);
        return NetApi.getNetApi(netApiService);
    }


}
