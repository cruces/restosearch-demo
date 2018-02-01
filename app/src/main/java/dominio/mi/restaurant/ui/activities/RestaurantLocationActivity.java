package dominio.mi.restaurant.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.models.RestaurantLocationModel;
import dominio.mi.restaurant.models.RestaurantModel;
import dominio.mi.restaurant.network.managers.RestaurantManager;
import dominio.mi.restaurant.network.managers.RestaurantManagerCallbacks;
import dominio.mi.restaurant.ui.MyActivity;
import dominio.mi.restaurant.ui.ProgressBarUtil;

public class RestaurantLocationActivity extends MyActivity implements OnMapReadyCallback {
    public static final String RESTAURANT_ID = "restaurantId";
    private int idRestaurant;
    private GoogleMap restaurantMap;
    private double latitude;
    private double longitude;
    private boolean mapReady = false;
    private boolean requestReady = false;
    private ProgressBarUtil utilProgressBar;
    private RestaurantManager restaurantManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_location);

        getToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        restaurantManager = new RestaurantManager();

        idRestaurant = getIntent().getIntExtra(RESTAURANT_ID, 0);
        getRestaurantData();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        View overlay = findViewById(R.id.overlay_view);

        utilProgressBar = new ProgressBarUtil(progressBar, overlay);

    }

    private void getRestaurantData() {
        restaurantManager.getRestaurant(idRestaurant, new RestaurantManagerCallbacks.OnGetRestaurant() {
            @Override
            public void onGetRestaurantSuccess(RestaurantModel restaurantModel) {
                RestaurantLocationModel location = restaurantModel.getLocation();
                latitude = Double.parseDouble(location.getLatitude());
                longitude = Double.parseDouble(location.getLongitude());

                requestReady = true;
                String name = restaurantModel.getName();

                if (mapReady && requestReady) {
                    moveMapCamera();
                    utilProgressBar.hideProgressBar();
                }

                if (name != null && !name.isEmpty()) {
                    getSupportActionBar().setTitle(name);
                }

            }

            @Override
            public void onGetRestaurantError(Exception e) {
                Toast.makeText(RestaurantLocationActivity.this, ERROR_MESSAGE,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        restaurantMap = googleMap;
        mapReady = true;

        if (requestReady) {
            moveMapCamera();
            utilProgressBar.hideProgressBar();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return Utils.menu(this, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuCaseLogin(item);
    }

    private void moveMapCamera() {
        LatLng position = new LatLng(latitude, longitude);
        restaurantMap.addMarker(new MarkerOptions().position(position));
        restaurantMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        restaurantMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
    }

}
