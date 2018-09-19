package shahin.android.exam.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import shahin.android.exam.R;
import shahin.android.exam.data.Constants;
import shahin.android.exam.data.DeliverData;
import shahin.android.exam.util.ShareData;

public class SplashActivity extends Activity {
    private BroadcastReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(isNetworkConnected()) {
            getDeliveries();
        }else {
            checkInternetConnection();
            if(ShareData.getDeliverData()!=null) {
                initDeliveries(ShareData.getDeliverData());
            }else{
                checkInternetConnection();
            }
        }
    }

    /*
     Call API to get deliveries data
     **/
    private void getDeliveries(){
        final String apiUrl = Constants.DELIVERABLES_API;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String deliveriesResponseJson = response.body().string();
                //
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*
                        Save delivers result on Cache
                         */
                        ShareData.setDeliverData(deliveriesResponseJson);
                        //
                        /*
                        Initialization delivers result
                         */
                        initDeliveries(deliveriesResponseJson);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                /*
                Handle request failure
                */
            }
        });
    }

    private void initDeliveries(String jsonResult){
        try {
            JSONArray jsonDeliveriesArray = new JSONArray(jsonResult);

            int totalDeliveries = jsonDeliveriesArray.length();

            String deliverDescription;
            String deliverImageUrl;
            JSONObject jsonDeliveriesLocationObj;
            String deliverLocationLat;
            String deliverLocationLng;
            String deliverAddress;
            for (int i = 0; i < totalDeliveries; i++) {
                JSONObject jsonEachDeliveriesObj = jsonDeliveriesArray.getJSONObject(i);
                deliverDescription = jsonEachDeliveriesObj.getString(Constants.TAG_DELIVERABLES_DESCRIPTION);
                deliverImageUrl = jsonEachDeliveriesObj.getString(Constants.TAG_DELIVERABLES_IMAGE);
                jsonDeliveriesLocationObj = jsonEachDeliveriesObj.getJSONObject(Constants.TAG_DELIVERABLES_LOCATION);
                deliverLocationLat = jsonDeliveriesLocationObj.getString(Constants.TAG_DELIVERABLES_LATITUDE);
                deliverLocationLng = jsonDeliveriesLocationObj.getString(Constants.TAG_DELIVERABLES_LONGITUDE);
                deliverAddress = jsonDeliveriesLocationObj.getString(Constants.TAG_DELIVERABLES_ADDRESS);
                LatLng latLng = new LatLng(Double.parseDouble(deliverLocationLat), Double.parseDouble(deliverLocationLng));
                MainApplication.mDeliverList.add(new DeliverData(deliverImageUrl, deliverDescription, deliverAddress, latLng));
            }

            Intent mainIntent = new Intent(this, DeliverablesListActivity.class);
            startActivity(mainIntent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void checkInternetConnection() {
        if (mNetworkReceiver == null) {
            mNetworkReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");
                    NetworkInfo.State state = info.getState();
                    if (state == NetworkInfo.State.CONNECTED) {
                        getDeliveries();
                    }
                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver((BroadcastReceiver) mNetworkReceiver, intentFilter);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy (){
        super.onDestroy();
        if (mNetworkReceiver != null) {
            unregisterReceiver(mNetworkReceiver);
        }
    }
}
