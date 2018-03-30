package dominio.mi.restaurant.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.ui.MyActivity;

public class LoginActivity extends MyActivity {
    private EditText password;
    private EditText userEmail;
    private CallbackManager callbackManager;
    private View loginOverlay;
    private TextView textLogin;
    private TextView notAccount;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binUi();

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmailForm = userEmail.getText().toString();
                String userPassword = password.getText().toString();

                if (formLogin(userEmailForm, userPassword)) {
                    startActivity(Utils.intentUserSharedPreferences(LoginActivity.this,
                            CategoriesActivity.class, true, false));
                }
            }
        });

        notAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginOverlay.setVisibility(View.VISIBLE);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                loginOverlay.setVisibility(View.GONE);
                toast(LoginActivity.this.getString(R.string.login_canceled));
            }

            @Override
            public void onError(FacebookException error) {
                loginOverlay.setVisibility(View.GONE);
                toast(ERROR_MESSAGE);
            }
        });


    }

    private void binUi() {
        TextView textLogo = findViewById(R.id.logo_text_resto);
        Typeface customAcme = Typeface.createFromAsset(getAssets(), "fonts/Acme-Regular.ttf");
        textLogo.setTypeface(customAcme);

        TextView textLogoSearch = findViewById(R.id.logo_text_search);
        Typeface customShadow = Typeface.createFromAsset(getAssets(), "fonts/ShadowsIntoLight.ttf");
        textLogoSearch.setTypeface(customShadow);

        textLogin = findViewById(R.id.login_button);
        notAccount = findViewById(R.id.not_account);

        userEmail = findViewById(R.id.user_email);
        password = findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        loginButton = findViewById(R.id.login_fb_button);
        loginOverlay = findViewById(R.id.overlay_view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean formLogin(String email, String password) {
        if (!Utils.isValidEmail(email)) {
            toast(this.getString(R.string.invalid_email));
            return false;
        } else if (!Utils.isValidPassword(password)) {
            toast(this.getString(R.string.short_password));
            return false;
        } else {
            return true;
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(Utils.intentUserSharedPreferences(LoginActivity.this,
                                    CategoriesActivity.class, true, true));
                        } else {
                            loginOverlay.setVisibility(View.GONE);
                            toast(LoginActivity.this.getString(R.string.error_trying_login_fb_account));
                        }
                    }
                });
    }
}
