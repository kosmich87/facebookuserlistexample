package kz.kkurtukov.facebookuserlistexample.ui;

import android.os.Bundle;

import kz.kkurtukov.facebookuserlistexample.R;
import kz.kkurtukov.facebookuserlistexample.utilities.DynamicLanguage;
import kz.kkurtukov.facebookuserlistexample.utilities.DynamicTheme;

/**
 * Created by kkurtukov on 18.12.2015.
 */
public class FriendsListActivity extends BaseActivity {

    private static String TAG = FriendsListActivity.class.getSimpleName();

    private final DynamicTheme dynamicTheme = new DynamicTheme();
    private final DynamicLanguage dynamicLanguage = new DynamicLanguage();

    private FriendsListFragment fragment;

    @Override
    protected void onPreCreate() {
        dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        fragment = initFragment(R.id.container, new FriendsListFragment(), dynamicLanguage.getCurrentLocale());
    }
}
