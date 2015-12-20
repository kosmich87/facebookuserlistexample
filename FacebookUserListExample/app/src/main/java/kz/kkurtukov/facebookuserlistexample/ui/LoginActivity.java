package kz.kkurtukov.facebookuserlistexample.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.util.Arrays;

import kz.kkurtukov.facebookuserlistexample.R;
import kz.kkurtukov.facebookuserlistexample.model.FBUserManager;
import kz.kkurtukov.facebookuserlistexample.utilities.DynamicLanguage;
import kz.kkurtukov.facebookuserlistexample.utilities.DynamicTheme;

public class LoginActivity extends BaseActivity {

    private static String TAG = LoginActivity.class.getSimpleName();

    private final DynamicTheme dynamicTheme = new DynamicTheme();
    private final DynamicLanguage dynamicLanguage = new DynamicLanguage();

    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    private LoginButton loginButton;
    private Button mGoButton;

    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackManager;

    @Override
    protected void onPreCreate() {
        dynamicTheme.onCreate(this);
        dynamicLanguage.onCreate(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get keyhash
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }*/

        // Set up the login form.
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    changeGoButtonState();
                }
            }
        };

        mGoButton = (Button) findViewById(R.id.go_button);

        changeGoButtonState();

        mGoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                getFriendList();
            }
        });

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "user_location",
                "user_birthday",
                "user_friends",
                "public_profile",
                "user_education_history",
                "user_hometown"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                changeGoButtonState();
                Log.v(TAG, "fblogin onSuccess");

            }

            @Override
            public void onCancel() {
                Log.v(TAG, "fblogin onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.v(TAG, "fblogin onError: " + exception.toString());
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        if (isFacebookLoggedIn()){
            showFriendsListActivity();
        }
    }

    private void changeGoButtonState() {
        if (isFacebookLoggedIn()) {
            mGoButton.setText(R.string.action_go);
            mGoButton.setEnabled(true);
        } else {
            mGoButton.setText(R.string.action_not_go);
            mGoButton.setEnabled(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public boolean isFacebookLoggedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    private void getFriendList() {
        /*GraphRequest request = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(
                            JSONArray object,
                            GraphResponse response) {
                        Log.d(TAG, "response: " + response);
                        try {
                            FBUserManager.parseFbUserList(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showProgress(false);
                    }
                });
        request.executeAsync();*/

        GraphRequest request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/taggable_friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "response: " + response);
                        try {
                            FBUserManager.parseFbUserList(response.getJSONObject());
                            showFriendsListActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showProgress(false);
                    }
                });
        request.executeAsync();

        /*request = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d(TAG, "response: " + response);
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields",
                "id," +
                "name," +
                "age_range," +
                "gender," +
                "location," +
                "education," +
                "last_name");
        request.setParameters(parameters);
        request.executeAsync();*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showFriendsListActivity(){
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);
        finish();
    }
}

