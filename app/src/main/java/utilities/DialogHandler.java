package utilities;

import android.app.Activity;
import android.app.AlertDialog;

public class DialogHandler {

  public static void ShowExitDialog(final Activity activity) {
    new AlertDialog.Builder(activity)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("Выйти")
        .setMessage("Вы уверены что хотите выйти?")
        .setPositiveButton("Да", (dialog, which) ->
        {
          activity.finish();
          System.exit(0);
        })
        .setNegativeButton("Нет", null)
        .show();
  }

  public static void ShowExceptionDialog(final Activity activity, String message) {
    new AlertDialog.Builder(activity)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("Error!")
        .setMessage(message)
        .setPositiveButton("OK", null)
        .show();
  }

  public static void ShowDialog(final Activity activity, String title, String message) {
    new AlertDialog.Builder(activity)
        .setIconAttribute(android.R.attr.searchIcon)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("OK", null)
        .show();
  }
}