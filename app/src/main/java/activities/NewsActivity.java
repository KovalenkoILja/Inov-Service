package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import adaptors.NewsRecyclerViewAdaptor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inov_service.R;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import models.PageContent;
import tasks.JsoupAsyncTask;

public class NewsActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private RecyclerView recyclerView;
  private TextView titleTextView;
  private JsoupAsyncTask asyncGetCatalogContent;
  private AdvanceDrawerLayout drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_news);

    titleTextView = findViewById(R.id.titleTextView);

    recyclerView = findViewById(R.id.recyclerView);
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
      final NewsRecyclerViewAdaptor adapter = new NewsRecyclerViewAdaptor(
          result.getNewsData(),
          (view, position) -> {
            Intent intent = new Intent(this, SinglePageActivity.class);
            intent.putExtra("href", result.getNewsData().get(position).getHref());
            intent.putExtra("title", result.getNewsData().get(position).getTitle());
            this.startActivity(intent);
          });

      this.recyclerView.setAdapter(adapter);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
