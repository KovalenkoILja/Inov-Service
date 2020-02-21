package activities;

import static com.example.inov_service.MyAppGlideModule.LoadImageToView;
import static models.Urls.URL;
import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.example.inov_service.R;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import models.PageContent;
import tasks.JsoupAsyncTask;

public class SinglePageActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private TextView titleTextView;
  private TextView dateTextView;
  private TextView descriptionTextView;
  private TextView contentTextView;
  private ImageView thumbImageView;
  private AdvanceDrawerLayout drawer;

  private JsoupAsyncTask asyncGetCatalogContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_single_page);

    titleTextView = findViewById(R.id.titleTextView);
    dateTextView = findViewById(R.id.dateTextView);
    descriptionTextView = findViewById(R.id.descriptionTextView);
    contentTextView = findViewById(R.id.contentTextView);
    thumbImageView = findViewById(R.id.thumbImageView);

    Toolbar myToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(myToolbar);

    drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    Intent intent = getIntent();
    String href = intent.getStringExtra("href");
    String title = intent.getStringExtra("title");

    titleTextView.setText(title);

    asyncGetCatalogContent = new JsoupAsyncTask(this);

    asyncGetCatalogContent.execute(href);
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
  protected void onDestroy() {
    super.onDestroy();
    if (asyncGetCatalogContent != null) {
      asyncGetCatalogContent.cancel(true);
    }
  }

  public void SetupLayout(PageContent result) {
    try {
      if (result.getImageUrl() != null && !result.getImageUrl().isEmpty()) {
        LoadImageToView(URL + "/" + result.getImageUrl(), thumbImageView);
      }

      dateTextView.setText(result.getPeriod());
      descriptionTextView.setText(result.getTitleStr());
      contentTextView.setText(result.getDescStr());

    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
