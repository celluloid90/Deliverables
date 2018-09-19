package shahin.android.exam.activity;

import android.app.Application;

import java.util.ArrayList;

import shahin.android.exam.data.DeliverData;

public class MainApplication  extends Application {
    public static ArrayList<DeliverData> mDeliverList;
    public static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mDeliverList = new ArrayList<DeliverData>();
        mInstance = this;
    }
}
