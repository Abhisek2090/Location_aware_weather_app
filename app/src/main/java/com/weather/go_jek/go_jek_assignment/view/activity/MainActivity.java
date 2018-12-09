package com.weather.go_jek.go_jek_assignment.view.activity;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.weather.go_jek.go_jek_assignment.R;
import com.weather.go_jek.go_jek_assignment.adapters.WeatherDataAdapter;
import com.weather.go_jek.go_jek_assignment.application.GO_JEK_Weather_application;
import com.weather.go_jek.go_jek_assignment.databinding.ActivityMainBinding;
import com.weather.go_jek.go_jek_assignment.dependencyinjection.PresenterComponent;
import com.weather.go_jek.go_jek_assignment.model.ForecastDayResponse;
import com.weather.go_jek.go_jek_assignment.presenter.MainActivityPresenter;
import com.weather.go_jek.go_jek_assignment.receiver.ConnectivityReceiver;
import com.weather.go_jek.go_jek_assignment.utils.PermissionManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends PresentedActivity<MainActivityPresenter> implements ConnectivityReceiver.ConnectivityReceiverListener,
        MainActivityPresenter.IMainActivityView, PermissionManager.PermissionsResultCallback {


    private MainActivityPresenter presenter;
    private PermissionManager permissionsManager;
    private Location userLocation = null;
    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback mlocationCallback;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String userLocationAddress;
    private WeatherDataAdapter weatherDataAdapter;
    private ActivityMainBinding binding;
    LinearLayoutManager layoutManager ;
    Animation slideUp;
    private Snackbar snackbar;
    private ConnectivityReceiver connectivityReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPresenter(presenter);

        layoutManager = new LinearLayoutManager(this);
      //  binding.tempList.setLayoutManager(layoutManager);

        permissionsManager = new PermissionManager(this, this);

 /*       googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        googleApiClient.connect();*/


        binding.progressBar.setVisibility(View.VISIBLE);

        slideUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            permissionsManager.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            getCurrentPosition();
        }


        binding.retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideErrorScreen();
                showProgressBar();
                getCurrentPosition();
            }
        });

        String message = "Ooops ! No internet connection Found.";
        int color = Color.WHITE;
        snackbar = Snackbar
                .make(findViewById(R.id.mainactivity_container), message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        sbView.setBackgroundColor(color);
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.snackbar_text_color));

        connectivityReceiver = new ConnectivityReceiver();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(connectivityReceiver, intentFilter);

    }

    @Override
    protected MainActivityPresenter onCreatePresenter() {
        presenter = new MainActivityPresenter(this);
        return presenter;
    }

    @Override
    protected void injectPresenter(PresenterComponent component, MainActivityPresenter presenter) {
        component.inject(presenter);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {

        if (isConnected) {
            if(snackbar.isShown()) {
              snackbar.dismiss();

            }

        } else {
            snackbar.show();

        }
    }

    @Override
    public void onPermissionGranted(List<String> permissions) {
        Log.i(TAG, "onPermissionGranted");
        if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) &&
                permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getCurrentPosition();
        }
    }

    @Override
    public void onPermissionDenied(List<String> permissions) {
        Log.i(TAG, "onPermissionDenied");
        if (permissions.size() > 0) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onPermissionAvailable(List<String> permissions) {
        Log.i(TAG, "onPermissionAvailable");
        if (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION) &&
                permissions.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getCurrentPosition();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.requestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getCurrentPosition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    userLocation = location;
                    double lat = userLocation.getLatitude();
                    double lng = userLocation.getLongitude();

                    getAddress(lat, lng);
                }
            }
        });
    }

    /*public void getCurrentPosition() {
        Log.i(TAG,"getCurrentPosition");

        final LocationRequest locationRequest = LocationRequest.create()
                .setInterval(9000)
                .setFastestInterval(8000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {

            mlocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    if(locationResult != null) {

                        userLocation = locationResult.getLastLocation();

                        Log.i(TAG, String.valueOf(userLocation));
                        if(userLocation != null) {
                            double lat = userLocation.getLatitude();
                            double lng = userLocation.getLongitude();

                            getAddress(lat, lng);

                        }

                    }

                }

            };


            fusedLocationProviderClient.requestLocationUpdates(locationRequest,mlocationCallback, null);
           // fusedLocationProviderClient.removeLocationUpdates(mlocationCallback);


        } catch (SecurityException e) {

        }
    }*/

    private void getAddress(double latitude, double longitude) {
        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            if (addresses.size() > 0) {
                userLocationAddress = addresses.get(0).getLocality();
                System.out.println(addresses.get(0).getLocality());
                presenter.getWeatherData(userLocationAddress);
                presenter.getCurrentTemp(userLocationAddress);
            }
            else {
                // do your stuff
            }
        }
    }

    @Override
    public void updateTempForecast(List<ForecastDayResponse> forecastDayResponses) {

        if(weatherDataAdapter == null) {
            Log.i(TAG,"weatherDataAdapter == null");

            weatherDataAdapter = new WeatherDataAdapter(this, forecastDayResponses);
            binding.tempList.setLayoutManager(layoutManager);
            binding.tempList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            binding.tempList.setAdapter(weatherDataAdapter);
            binding.tempList.setVisibility(View.VISIBLE);
            binding.tempList.setAnimation(slideUp);
            weatherDataAdapter.notifyDataSetChanged();
        }
        else {

           weatherDataAdapter.updateWithNewList(forecastDayResponses);
        }

    }

    @Override
    public void showCurrentTemp(String temp) {
        binding.currentTempTextView.setText(temp);
        binding.locationTextView.setText(userLocationAddress);
    }

    @Override
    public void hideProgressBar() {
        if(binding.progressBar != null) {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorScreen() {
        binding.progressBar.setVisibility(View.GONE);
        binding.emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorScreen() {
        binding.emptyView.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        GO_JEK_Weather_application.getInstance().setConnectivityListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);


    }
}
