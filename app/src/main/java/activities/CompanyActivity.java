package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.example.inov_service.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import java.io.IOException;
import java.util.List;

public class CompanyActivity extends AppCompatActivity implements OnMapReadyCallback,
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private GoogleMap map;
  private AdvanceDrawerLayout drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_company);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.goggleMap);

    Toolbar myToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(myToolbar);

    drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    if (mapFragment != null) {
      mapFragment.getMapAsync(this);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    menu.setGroupVisible(R.id.group_main, false);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    try {
      OnItemClick(menuItem, this);
      drawer.closeDrawer(GravityCompat.START);
      return true;
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
      return false;
    }
  }


  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;
    final float zoomLevel = (float) 18.0;
    final String address = this.getResources().getString(R.string.addressStr);

    try {
      List<Address> p1aces = new Geocoder(this).getFromLocationName(address, 1);
      if (p1aces != null) {
        Address location = p1aces.get(0);
        LatLng office = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(office).title(address));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(office, zoomLevel));
      }
    } catch (IOException e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
