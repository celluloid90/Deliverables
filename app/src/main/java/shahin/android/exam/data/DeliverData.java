package shahin.android.exam.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DeliverData extends ArrayList {
    private String mThumb;
    private String mDescription;
    private LatLng mLatLong;
    private String mAddress;

    public DeliverData(String thumbUrl, String description, String address, LatLng latLng) {
        super();

        this.mThumb = thumbUrl;
        this.mDescription = description;
        this.mAddress = address;
        this.mLatLong = latLng;
    }

    public String getThumbnil() {
        return mThumb;
    }

    public String getDescription() {
        return mDescription;
    }

    public LatLng getLatLng() {
        return mLatLong;
    }

    public String getAddress() {
        return mAddress;
    }
}

