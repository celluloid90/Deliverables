package shahin.android.exam.util;

import android.content.Context;
import android.content.SharedPreferences;

import shahin.android.exam.activity.MainApplication;

public class ShareData {
    private Context context;
    //
    /*
    Share Preferences Name
     */
    public static final String ALL_VERSION = "all_version";
    public static final String DELIVER_DATA = "deliver_data";

    public ShareData(Context context) {
        this.context = context;
    }

    public static String getDeliverData() {
        SharedPreferences appSharedPrefs = MainApplication.mInstance.getSharedPreferences(ALL_VERSION,
                Context.MODE_PRIVATE);
        String DeliverData = appSharedPrefs.getString(DELIVER_DATA, "");
        //
        return DeliverData;
    }

    public static void setDeliverData(String data) {
        /*
        Storing object in preferences
         */
        SharedPreferences appSharedPrefs = MainApplication.mInstance.getSharedPreferences(ALL_VERSION,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.putString(DELIVER_DATA, data);
        prefsEditor.commit();
    }
}
