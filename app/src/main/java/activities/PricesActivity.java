package activities;

import static utilities.DialogHandler.ShowExceptionDialog;
import static utilities.MenuHandler.OnItemClick;

import adaptors.ListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.inov_service.R;
import models.PageContent;
import tasks.JsoupAsyncTask;

public class PricesActivity extends AppCompatActivity {

  private String TAG = getClass().getSimpleName();

  private ListView listView;
  private TextView titleTextView;
  private JsoupAsyncTask asyncGetContent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_prices);

    titleTextView = findViewById(R.id.titleTextView);

    listView = findViewById(R.id.listView);
    Toolbar myToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(myToolbar);

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
    menu.findItem(R.id.action_prices).setVisible(false);
    return super.onCreateOptionsMenu(menu);
  }

  public void onItemClick(MenuItem item) throws Exception {
    OnItemClick(item, this);
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
      titleTextView.setText(result.getTitleStr());

      final ListAdapter adapter = new ListAdapter(
          R.layout.list_row, this, result.getRecyclerViewData(),
          (view, position) -> {
            Intent intent = new Intent(this, PriceListActivity.class);
            intent.putExtra("href", result.getRecyclerViewData().get(position).getHref());
            intent.putExtra("title",
                result.getRecyclerViewData().get(position).getTitle());
            this.startActivity(intent);
          }
      );
      listView.setAdapter(adapter);

    } catch (Exception e) {
      Log.e(TAG, e.toString());
      ShowExceptionDialog(this, e.toString());
    }
  }
}
