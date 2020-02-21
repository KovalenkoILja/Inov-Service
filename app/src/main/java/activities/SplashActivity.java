package activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inov_service.R;
import utilities.MyConnectivityChecker;


public class SplashActivity extends AppCompatActivity {

  /**
   * Duration of wait
   **/
  private final int SPLASH_DISPLAY_LENGTH = 1000;
  String TAG = getClass().getSimpleName();

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.activity_splash);

    if (MyConnectivityChecker.isConnected(this)) {
            /* connectivity available
              New Handler to start the Menu-Activity
              and close this Splash-Screen after some seconds.
              */

      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          /* Create an Intent that will start the Menu-Activity. */
          Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
          SplashActivity.this.startActivity(mainIntent);
          SplashActivity.this.finish();
        }
      }, SPLASH_DISPLAY_LENGTH);
    } else {
      /* no connectivity */
      showAlertDialog(SplashActivity.this, "Internet Connection",
          "You don't have internet connection");
    }
  }

  public void showAlertDialog(Context context, String title, String message) {
    AlertDialog alertDialog = new AlertDialog.Builder(context).create();

    // Setting Dialog Title
    alertDialog.setTitle(title);

    // Setting Dialog Message
    alertDialog.setMessage(message);

    // Setting alert dialog icon
    alertDialog.setIconAttribute(android.R.attr.alertDialogIcon);

    // Setting OK Button
    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {

        finish();
        System.exit(0);
      }
    });

    // Showing Alert Message
    alertDialog.show();
  }
}
