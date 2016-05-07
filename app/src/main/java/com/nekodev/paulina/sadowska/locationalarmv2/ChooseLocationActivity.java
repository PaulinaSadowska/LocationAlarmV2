package com.nekodev.paulina.sadowska.locationalarmv2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final float STROKE_WIDTH = (float)1.5;
    private static final int ZOOM = 13;
    private static final int FILL_COLOR = Color.argb(60, 0, 0, 0);
    private static final int STROKE_COLOR = Color.argb(200, 0, 0, 0);
    private GoogleMap mMap;
    private PlaceAutocompleteFragment autocompleteFragment;

    private LatLng placeLatLng;
    private int radius = 1000;

    @Bind(R.id.choose_location_radius_label)
    TextView radiusLabel;
    @Bind(R.id.choose_location_radius_seekbar)
    RangeBar radiusSeekbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        ButterKnife.bind(this);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.choose_location_map);
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.choose_location_place_autocomplete_fragment);

        mapFragment.getMapAsync(this);
        configureAutocompleteFragment();
        radiusLabel.setText(getString(R.string.radius_label));
        radiusSeekbar.setRangePinsByValue(0, radius);
        radiusSeekbar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
                radius = Integer.parseInt(rightPinValue);
                updateMap(false);
            }
        });
        radiusSeekbar.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                return s + getString(R.string.radius_unit_label);
            }
        });
    }

    private void configureAutocompleteFragment() {
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            private final static String TAG = "PLACE_PICKER";

            @Override
            public void onPlaceSelected(Place place) {
                placeLatLng = place.getLatLng();
                updateMap(true);
            }

            @Override
            public void onError(Status status) {
                Log.e(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        placeLatLng = new LatLng(-34, 151);
        updateMap(true);
    }

    private void updateMap(boolean animate){
        if(mMap==null){
            return;
        }
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(placeLatLng));
        if(radius>0)
            addMarkerWithCircle();
        if(animate){
            moveCameraToPosition();
        }
    }

    private void moveCameraToPosition() {
        CameraPosition c = CameraPosition.builder()
                .target(placeLatLng)
                .zoom(ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(c), 3000, null);
    }

    private void addMarkerWithCircle() {
        if(radius>0){
            mMap.addCircle(new CircleOptions()
                    .center(placeLatLng)
                    .radius(radius)
                    .fillColor(FILL_COLOR)
                    .strokeColor(STROKE_COLOR)
                    .strokeWidth(STROKE_WIDTH));
        }
    }
}
