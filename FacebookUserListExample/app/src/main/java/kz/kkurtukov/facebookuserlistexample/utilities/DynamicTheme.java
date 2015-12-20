package kz.kkurtukov.facebookuserlistexample.utilities;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by kkurtukov on 18.12.2015.
 */
public class DynamicTheme {

    private int currentTheme;

    public void onCreate(Activity activity) {
        currentTheme = getSelectedTheme(activity);
        activity.setTheme(currentTheme);
    }

    public void onResume(Activity activity) {
        if (currentTheme != getSelectedTheme(activity)) {
            Intent intent = activity.getIntent();
            activity.finish();
            OverridePendingTransition.invoke(activity);
            activity.startActivity(intent);
            OverridePendingTransition.invoke(activity);
        }
    }

    protected int getSelectedTheme(Activity activity) {
        /*String theme = <ClassPreferences>.getTheme(activity);

        if (theme.equals("{name_of_theme}")) return R.style.{first_theme_name};

        return R.style.{second_theme_name};*/
        return 1;
    }

    private static final class OverridePendingTransition {
        static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }
}
