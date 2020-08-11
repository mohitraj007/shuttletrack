package com.project.mohit.shuttletrack.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mohit on 07-03-2018.
 */

public interface IGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);
}
