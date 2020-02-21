package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import adaptors.RecyclerViewAdaptor;
import adaptors.SliderAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inov_service.R;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import models.CardViewData;
import models.PageContent;
import tasks.JsoupAsyncTask;

public class MainActivity extends AppCompatActivity implements
    NavigationView.OnNavigationItemSelectedListener {

  private String TAG = getClass().getSimpleName();

  private SliderView sliderView;
  private RecyclerView recyclerView;
  private JsoupAsyncTask asyncGetMainPageContent;
  private AdvanceDrawerLayout drawer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    sliderView = findViewById(R.id.imageSlider);
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

    asyncGetMainPageContent = new JsoupAsyncTask(this);
    asyncGetMainPageContent.execute();
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
    if (asyncGetMainPageContent != null) {
      asyncGetMainPageContent.cancel(true);
    }
  }

  public void GoToProduct(View view) {
    Log.d(TAG, "GoToProductClick");

    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
    intent.putExtra("href", "/product/");
    intent.putExtra("title", getResources().getString(R.string.catalogBtnStr));
    startActivity(intent);
  }

  public void SetupLayout(PageContent result) {
    try {
      final SliderAdapter sliderAdapter = new SliderAdapter(
          result.getCardViewData(),
          (view, position) -> OnCardViewItemClick(result, view, position));

      this.sliderView.setSliderAdapter(sliderAdapter);
      this.sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
      this.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
      this.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
      this.sliderView.setIndicatorSelectedColor(Color.WHITE);
      this.sliderView.setIndicatorUnselectedColor(Color.GRAY);
      this.sliderView.startAutoCycle();

      this.sliderView.setOnIndicatorClickListener(
          position -> sliderView.setCurrentPagePosition(position));

      final RecyclerViewAdaptor adapter = new RecyclerViewAdaptor(
          result.getRecyclerViewData(),
          (view, position) -> {
          });

      this.recyclerView.setAdapter(adapter);
    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }

  public void OnCardViewItemClick(PageContent result, View view, int position) {
    Log.d(TAG, "OnCardViewItemClick");

    CardViewData data = result.getCardViewData().get(position);

    Intent intent = new Intent(view.getContext().getApplicationContext(),
        ProductActivity.class);
    intent.putExtra("href", "/" + data.getHref());
    intent.putExtra("title", data.getTitle());
    view.getContext().startActivity(intent);
  }
}

