package com.andra.nostratest;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Andra on 7/28/2018.
 */

public interface ApiInterface {
    @GET("api/v1/person")
    Observable<GeneralList<Contact>> getContacts();

    @DELETE("api/v1/person/{secure_id}")
    Observable<JsonObject> deleteContact(@Path("secure_id") String id);

    @PUT("api/v1/person/{secure_id}")
    Observable<JsonObject> updateProfile(@Path("secure_id") String id, @Body JsonObject data);

    @POST("api/v1/person")
    Observable<JsonObject> addContact(@Body JsonObject data);
}
