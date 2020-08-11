package com.project.mohit.shuttletrackrider.Common;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.project.mohit.shuttletrackrider.Home;
import com.project.mohit.shuttletrackrider.Model.DataMessage;
import com.project.mohit.shuttletrackrider.Model.FCMResponse;
import com.project.mohit.shuttletrackrider.Model.Rider;
import com.project.mohit.shuttletrackrider.Model.Token;
import com.project.mohit.shuttletrackrider.Remote.FCMClient;
import com.project.mohit.shuttletrackrider.Remote.GoogleMapAPI;
import com.project.mohit.shuttletrackrider.Remote.IFCMService;
import com.project.mohit.shuttletrackrider.Remote.IGoogleAPI;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohit on 10-03-2018.
 */

public class Common {

    public static final int PICK_IMAGE_REQUEST = 9999;

    public static boolean isDriverFound = false;
    public static String driverId = "";

    public static Location mLastLocation;

    public static Rider currentUser = new Rider();

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";
    public static final String token_tbl = "Tokens";

    public static final String user_field = "rider_usr";
    public static final String pwd_field = "rider_pwd";

    public static final String fcmURL = "https://fcm.googleapis.com";
    public static final String googleAPIUrl = "https://maps.googleapis.com";

    private static double base_fare = 10.0;
    private static double time_rate = 0.0;
    private static double distance_rate = 0.0;

    public static double getPrice(double km,int min) {
        return (base_fare+(time_rate*min)+(distance_rate*km));
    }

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }

    public static IGoogleAPI getGoogleService()
    {
        return GoogleMapAPI.getClient(googleAPIUrl).create(IGoogleAPI.class);
    }

    public static void sendRequestToDriver(String driverId, final IFCMService mService, final Context context, final Location currentLocation) {
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference(Common.token_tbl);

        tokens.orderByKey().equalTo(driverId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Token token = postSnapshot.getValue(Token.class);//Get token object from database with key

                            //make raw payload - convert latlng to json
                            String riderToken = FirebaseInstanceId.getInstance().getToken();
                            /*Notification data = new Notification(riderToken, json_lat_lng);//send to driver app
                            Sender content = new Sender(token.getToken(), data);//send this data to token*/

                            Map<String ,String > content = new HashMap<>();
                            content.put("customer",riderToken);
                            content.put("lat",String.valueOf(currentLocation.getLatitude()));
                            content.put("lng",String.valueOf(currentLocation.getLongitude()));
                            DataMessage dataMessage = new DataMessage(token.getToken(),content);

                            mService.sendMessage(dataMessage)
                                    .enqueue(new Callback<FCMResponse>() {
                                        @Override
                                        public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                                            if (response.body().success == 1)
                                                Toast.makeText(context, "Request sent!", Toast.LENGTH_SHORT).show();
                                            else
                                                Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(Call<FCMResponse> call, Throwable t) {
                                            Log.e("ERROR", t.getMessage());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
