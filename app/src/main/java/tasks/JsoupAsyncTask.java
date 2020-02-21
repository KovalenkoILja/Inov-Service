package tasks;

import activities.ItemActivity;
import activities.MainActivity;
import activities.NewsActivity;
import activities.PriceListActivity;
import activities.PricesActivity;
import activities.ProductActivity;
import activities.ServicesActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import activities.SinglePageActivity;
import java.io.IOException;
import java.lang.ref.WeakReference;
import models.PageContent;

public class JsoupAsyncTask extends AsyncTask<String, String, PageContent> {

  private String TAG = getClass().getSimpleName();

  private PageContent pageContent;

  private WeakReference<Context> context;

  private ProgressDialog dialog;

  public JsoupAsyncTask(Context activity) {
    this.context = new WeakReference<>(activity);
    this.dialog = new ProgressDialog(activity);
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    dialog.setTitle("Подождите...");
    dialog.show();
  }

  @Override
  protected PageContent doInBackground(String... strings) {
    try {
      Context cur_context = context.get();

      if (cur_context instanceof MainActivity) {
        this.pageContent = utilities.Jsoup.GetMainPageContent(this, strings);
      } else if (cur_context instanceof ProductActivity) {
        this.pageContent = utilities.Jsoup.GetCatalogContent(this, strings);
      } else if (cur_context instanceof ItemActivity) {
        this.pageContent = utilities.Jsoup.GetItemContent(this, strings);
      } else if (cur_context instanceof PricesActivity) {
        this.pageContent = utilities.Jsoup.GetPricesCatalogContent(this, strings);
      } else if (cur_context instanceof PriceListActivity) {
        this.pageContent = utilities.Jsoup.GetPriceListContent(this, strings);
      } else if (cur_context instanceof NewsActivity) {
        this.pageContent = utilities.Jsoup.GetNewsFeedContent(this, strings);
      } else if (cur_context instanceof SinglePageActivity) {
        this.pageContent = utilities.Jsoup.GetSinglePageContent(this, strings);
      } else if (cur_context instanceof ServicesActivity) {
        this.pageContent = utilities.Jsoup.GetServicesContent(this, strings);
      }
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    }

    //Log.d(TAG, pageContent.toString());
    return pageContent;
  }

  public void PublishProgress(String str) {
    this.publishProgress(str);
  }

  @Override
  protected void onProgressUpdate(String... values) {
    super.onProgressUpdate(values);
    dialog.setMessage(values[0]);
  }

  @Override
  protected void onPostExecute(PageContent result) {
    super.onPostExecute(result);

    Context cur_context = context.get();

    if (cur_context instanceof MainActivity) {
      MainActivity activity = (MainActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof ProductActivity) {
      ProductActivity activity = (ProductActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof ItemActivity) {
      ItemActivity activity = (ItemActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof PricesActivity) {
      PricesActivity activity = (PricesActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof PriceListActivity) {
      PriceListActivity activity = (PriceListActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof NewsActivity) {
      NewsActivity activity = (NewsActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof SinglePageActivity) {
      SinglePageActivity activity = (SinglePageActivity) cur_context;
      activity.SetupLayout(result);
    } else if (cur_context instanceof ServicesActivity) {
      ServicesActivity activity = (ServicesActivity) cur_context;
      activity.SetupLayout(result);
    }

    dialog.dismiss();
  }
}
