package shahin.android.exam.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import shahin.android.exam.R;
import shahin.android.exam.data.Constants;
import shahin.android.exam.data.DeliverData;

public class DeliverLocationMapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng mLastLatLng;

    private RelativeLayout mDetailsView;
    private ImageView mDeliverImageView;
    private TextView mDeliverItemDesTv;

    private int mDeliverItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_location_map);

        mDeliverItemPosition = getIntent().getIntExtra(Constants.KEY_DELIVERABLES_POSITION, 0);

        initViews();
    }

    private void initViews(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDetailsView = (RelativeLayout) findViewById(R.id.info_cover);
        mDeliverImageView = (ImageView) findViewById(R.id.thumbnail_img);
        mDeliverItemDesTv = (TextView) findViewById(R.id.info_content);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getFocusedBuilding();

        setDeliveries();
    }

    private void setDeliveries() {
        ArrayList<DeliverData> deliverList = MainApplication.mDeliverList;
        DeliverData deliverData = deliverList.get(mDeliverItemPosition);

        mLastLatLng = deliverData.getLatLng();

        setMarkerOnDeliverPlace(deliverData);

        showDetails(deliverData);
    }

    private void setMarkerOnDeliverPlace(DeliverData deliverData) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLastLatLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_marker));
        markerOptions.title(deliverData.getAddress());
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mLastLatLng));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLastLatLng, 18));
    }

    private void showDetails(DeliverData deliverData){
        String description = deliverData.getDescription() + " at " + deliverData.getAddress();
        Picasso mPicasso = Picasso.get();
        mPicasso.load(deliverData.getThumbnil()).into(mDeliverImageView);
        mDeliverItemDesTv.setText(description);

        float dest = 1;
        ObjectAnimator viewAnimation = ObjectAnimator.ofFloat(mDetailsView,
                "alpha", dest);
        viewAnimation.setDuration(1000);
        viewAnimation.start();
    }
}
