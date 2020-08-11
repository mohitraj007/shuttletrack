package com.project.mohit.shuttletrack.Common;

import android.location.Location;

import com.project.mohit.shuttletrack.Model.User;
import com.project.mohit.shuttletrack.Remote.FCMClient;
import com.project.mohit.shuttletrack.Remote.IFCMService;
import com.project.mohit.shuttletrack.Remote.IGoogleAPI;
import com.project.mohit.shuttletrack.Remote.RetrofitClient;

/**
 * Created by Mohit on 07-03-2018.
 */

public class Common {

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInformation";
    public static final String user_rider_tbl = "RidersInformation";
    public static final String pickup_request_tbl = "PickupRequest";
    public static final String token_tbl = "Tokens";


    public static final int PICK_IMAGE_REQUEST = 9999;

    public static User currentUser;

    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com";
    public static final String user_field = "usr";
    public static final String pwd_field = "pwd";

    public static int count = 0;

    public static Location mLastLocation = null;

    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}
