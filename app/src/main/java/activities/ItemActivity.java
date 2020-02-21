package activities;

import static utilities.DialogHandler.ShowDialog;
import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import adaptors.SliderAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.Map;
import models.PageContent;
import tasks.JsoupAsyncTask;

public class ItemActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private TextView titleTextView;
  private TextView priceTextView;
  private SliderView sliderView;
  private TableLayout tableLayout;
  private JsoupAsyncTask asyncGetItemContent;
  private AdvanceDrawerLayout drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item);

    titleTextView = findViewById(R.id.titleTextView);
    priceTextView = findViewById(R.id.priceTextView);
    sliderView = findViewById(R.id.imageSlider);
    tableLayout = findViewById(R.id.tableLayout);

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

    asyncGetItemContent = new JsoupAsyncTask(this);

    asyncGetItemContent.execute(href);
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
    if (asyncGetItemContent != null) {
      asyncGetItemContent.cancel(true);
    }
  }

  public void SetupLayout(PageContent result) {
    try {
      final SliderAdapter sliderAdapter = new SliderAdapter(
          result.getCardViewData(),
          (view, position) -> ShowDialog(this, result.getTitleStr(), result.getDescStr()));

      this.sliderView.setSliderAdapter(sliderAdapter);
      this.sliderView.setIndicatorAnimation(
          IndicatorAnimations.WORM);
      this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
      this.sliderView.setAutoCycleDirection(
          SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
      this.sliderView.setIndicatorSelectedColor(Color.WHITE);
      this.sliderView.setIndicatorUnselectedColor(Color.GRAY);
      this.sliderView.startAutoCycle();

      this.sliderView.setOnIndicatorClickListener(position -> {
      });

      this.priceTextView.setText(result.getPriceStr());

      for (Map.Entry<String, String> item : result.getTableRows().entrySet()) {
        final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.table_row,
            null);
        TextView textView;

        textView = tableRow.findViewById(R.id.tableCell1);
        textView.setText(item.getKey());

        textView = tableRow.findViewById(R.id.tableCell2);
        textView.setText(item.getValue());

        tableLayout.addView(tableRow);
      }
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
