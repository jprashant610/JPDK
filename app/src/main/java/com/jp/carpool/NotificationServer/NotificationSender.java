package com.jp.carpool.NotificationServer;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dk on 3/19/17.
 */

public class NotificationSender {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    final String LegacyServerKey = "AIzaSyANutjkF1rhRu9cuVW5g74VZSzzq_BM1QA";
    String token = null;
    public NotificationSender()
    {
        MyFirebaseInstanceIDService fcmTokenService = new MyFirebaseInstanceIDService();
        token = fcmTokenService.getToken();
    }

    public void sendNotification() {
        Log.d("NotificationSender","Going to send notification :"+LegacyServerKey+" - "+token);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject json=new JSONObject();
                    JSONObject dataJson=new JSONObject();
                    dataJson.put("body","Hi this is sent from device to device");
                    dataJson.put("title","dummy title");
                    json.put("notification",dataJson);
                    json.put("to",token);
                    RequestBody body = RequestBody.create(JSON, json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization","key="+LegacyServerKey)
                            .url("https://fcm.googleapis.com/fcm/send")
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String finalResponse = response.body().string();
                    Log.d("FCM responce","Responce body : "+ finalResponse);
                }catch (Exception e){
                    //Log.d(TAG,e+"");
                }
                return null;
            }
        }.execute();

    }
}
