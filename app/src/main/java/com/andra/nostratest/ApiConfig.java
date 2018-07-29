package com.andra.nostratest;

import android.support.annotation.MainThread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Andra on 7/28/2018.
 */

public class ApiConfig {
    public static final String BASE_URL = "http://dev.nostratech.com:10093";

    private Map<String, List<Subscription>> subscriptionMap;
    private ApiInterface apiService;
    private static ApiConfig apiConfig;

    public static synchronized ApiConfig instance() {
        if (apiConfig == null) {
            apiConfig = new ApiConfig();
        }
        return apiConfig;
    }

    private ApiConfig() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiInterface.class);
    }

    public void close(String key) {
        List<Subscription> lss = subscriptionMap.get(key);
        if (lss != null) {
            for (Subscription subs : lss) {
                if (!subs.isUnsubscribed()) subs.unsubscribe();
            }
        }
        subscriptionMap.remove(key);
    }

    public void getContacts(final ApiCallback<List<Contact>> callback) {
        Subscription subs = apiService.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    try {
                        if (response.getData() != null && response.getData().size() > 0) {
                            callback.success(response.getData(), "");
                        }

                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                }, callback::onFailure);

    }

    public void deleteContact(String id, final ApiCallback<JsonObject> callback) {
        Subscription subs = apiService.deleteContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callback.success(response, ""), callback::onFailure);
    }

    public void updateProfile(String accountID, JsonObject data, final ApiCallback<JsonObject> callback) {
        Subscription subs = apiService.updateProfile(accountID, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> callback.success(jsonObject, ""), callback::onFailure);
    }

    public void addContact(JsonObject data, final ApiCallback<JsonObject> callback) {
        Subscription subs = apiService.addContact( data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonObject -> callback.success(jsonObject, ""), callback::onFailure);
    }

    public interface ApiCallback<T> {
        String getKey();

        void success(T t, String messages);

        void onFailure(Throwable t);
    }
}
