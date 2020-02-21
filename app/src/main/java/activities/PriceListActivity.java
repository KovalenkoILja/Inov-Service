package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import com.example.inov_service.R;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import java.util.List;
import java.util.Map.Entry;
import models.PageContent;
import models.PriceListData;
import tasks.JsoupAsyncTask;

public class PriceListActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private TextView titleTextView;
  private ScrollView priceListScrollView;
  private AdvanceDrawerLayout drawer;

  private JsoupAsyncTask asyncGetContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_price_list);

    titleTextView = findViewById(R.id.titleTextView);
    priceListScrollView = findViewById(R.id.priceListScrollView);

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

    asyncGetContent = new JsoupAsyncTask(this);

    asyncGetContent.execute(href);
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
    if (asyncGetContent != null) {
      asyncGetContent.cancel(true);
    }
  }

  public void SetupLayout(PageContent result) {
    try {
      TableLayout tableLayout = new TableLayout(this);

      for (Entry<String, List<PriceListData>> item : result.getPriceList().entrySet()) {

        TextView textView = new TextView(this);
        textView.setText(item.getKey());
        textView.setGravity(Gravity.CENTER);

        tableLayout.addView(textView);
        TableRow header = (TableRow) getLayoutInflater().inflate(R.layout.price_list_row,
            null);

        header.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        tableLayout.addView(header);

        for (PriceListData data : item.getValue()) {

          TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.price_list_row,
              null);

          TextView number = tableRow.findViewById(R.id.tableCellNumber);
          TextView name = tableRow.findViewById(R.id.tableCellName);
          TextView light = tableRow.findViewById(R.id.tableCellLight);
          TextView price = tableRow.findViewById(R.id.tableCellPrice);

          number.setText(data.getNumber());
          name.setText(data.getTitle());
          light.setText(data.getLight());
          price.setText(data.getPrice());

          tableLayout.addView(tableRow);
        }
      }
      priceListScrollView.addView(tableLayout);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
