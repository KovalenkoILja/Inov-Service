package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import adaptors.RecyclerViewAdaptor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import models.RecyclerViewData;
import tasks.JsoupAsyncTask;

public class ServicesActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private RecyclerView recyclerView;
  private TextView titleTextView;
  private AdvanceDrawerLayout drawer;
  private JsoupAsyncTask asyncGetCatalogContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services);

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
      final RecyclerViewAdaptor adapter = new RecyclerViewAdaptor(
          result.getRecyclerViewData(),
          (view, position) -> OnRecycleViewItemClick(view, position, result));
      this.recyclerView.setAdapter(adapter);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }

  public void OnRecycleViewItemClick(View view, int position, PageContent result) {

    Log.d(TAG, "OnRecycleViewItemClick");

    RecyclerViewData data = result.getRecyclerViewData().get(position);

    switch (data.getType()) {
      case CATALOG:
        Intent intent = new Intent(view.getContext().getApplicationContext(),
            view.getContext().getClass());
        intent.putExtra("href", data.getHref());
        intent.putExtra("title", data.getTitle());
        view.getContext().startActivity(intent);
        break;
      case ITEM:
        intent = new Intent(view.getContext().getApplicationContext(),
            SinglePageActivity.class);
        intent.putExtra("href", data.getHref());
        intent.putExtra("title", data.getTitle());
        view.getContext().startActivity(intent);
        break;
      case NONE:
        break;
    }
  }
}
