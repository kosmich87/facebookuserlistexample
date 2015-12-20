package kz.kkurtukov.facebookuserlistexample.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by kkurtukov on 18.12.2015.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String LOCALE_EXTRA = "locale_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onPreCreate();
        super.onCreate(savedInstanceState);
    }

    protected void onPreCreate() {}

    protected <T extends Fragment> T initFragment(@IdRes int target,
                                                  @NonNull T fragment)
    {
        return initFragment(target, fragment, null);
    }

    protected <T extends Fragment> T initFragment(@IdRes int target,
                                                  @NonNull T fragment,
                                                  @Nullable Locale locale)
    {
        return initFragment(target, fragment, locale, null);
    }

    protected <T extends Fragment> T initFragment(@IdRes int target,
                                                  @NonNull T fragment,
                                                  @Nullable Locale locale,
                                                  @Nullable Bundle extras)
    {
        Bundle args = new Bundle();
        args.putSerializable(LOCALE_EXTRA, locale);

        if (extras != null) {
            args.putAll(extras);
        }

        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(target, fragment)
                .commit();
        return fragment;
    }
}
