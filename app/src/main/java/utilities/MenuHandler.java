package utilities;

import activities.CompanyActivity;
import activities.MainActivity;
import activities.NewsActivity;
import activities.PricesActivity;
import activities.ProductActivity;
import activities.ServicesActivity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import com.example.inov_service.R;

public class MenuHandler {

  public static void OnItemClick(MenuItem item, Context context) throws Exception {
    String s = item.toString();
    if (context.getResources().getString(R.string.mainMenuStr).equals(s)) {
      if (!(context instanceof MainActivity)) {
        context.startActivity(new Intent(context.getApplicationContext(), MainActivity.class));
      }
    } else if (context.getResources().getString(R.string.catalogMenuStr).equals(s)) {
      if (!(context instanceof ProductActivity)) {
        Intent intent = new Intent(context.getApplicationContext(), ProductActivity.class);
        intent.putExtra("href", "/product/");
        intent.putExtra("title",
            context.getResources().getString(R.string.catalogBtnStr));
        context.startActivity(intent);
      }
    } else if (context.getResources().getString(R.string.serviceMenuStr).equals(s)) {
      if (!(context instanceof ServicesActivity)) {
        Intent intent = new Intent(context.getApplicationContext(), ServicesActivity.class);
        intent.putExtra("href", "/services/");
        intent.putExtra("title",
            context.getResources().getString(R.string.serviceMenuStr));
        context.startActivity(intent);
      }
    } else if (context.getResources().getString(R.string.pricesMenuStr).equals(s)) {
      if (!(context instanceof PricesActivity)) {
        Intent intent = new Intent(context.getApplicationContext(), PricesActivity.class);
        intent.putExtra("href", "/price/");
        intent.putExtra("title",
            context.getResources().getString(R.string.pricesMenuStr));
        context.startActivity(intent);
      }
    } else if (context.getResources().getString(R.string.aboutMenuStr).equals(s)) {
      if (!(context instanceof CompanyActivity)) {
        context.startActivity(new Intent(context.getApplicationContext(), CompanyActivity.class));
      }
    } else if (context.getResources().getString(R.string.newsMenuStr).equals(s)) {
      if (!(context instanceof NewsActivity)) {
        Intent intent = new Intent(context.getApplicationContext(), NewsActivity.class);
        intent.putExtra("href", "/info/news/");
        intent.putExtra("title",
            context.getResources().getString(R.string.newsMenuStr));
        context.startActivity(intent);
      }
    } else {
      throw new Exception("Unknown menu item!");
    }
  }
}
