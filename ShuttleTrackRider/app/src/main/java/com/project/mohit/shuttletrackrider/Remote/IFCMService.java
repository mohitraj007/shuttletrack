package com.project.mohit.shuttletrackrider.Remote;

import com.project.mohit.shuttletrackrider.Model.DataMessage;
import com.project.mohit.shuttletrackrider.Model.FCMResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Mohit on 12-03-2018.
 */

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAEnEg6DA:APA91bETz0iwWfdqKKOm5v27oPTZAMoWeu2Q9RzeYOVwon75GNVOVR1Ze5PMx8iG14Faqa1SU_XOZs7-rEAVMsyYmEGR-LwxrGiqFEzKdnAZXUyaWp83HdTxtMwqWRM412QWyoW1HZ9m"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body DataMessage body);
}
