package dominio.mi.restaurant.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuth;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.ui.MyActivity;

public class LoginActivity extends MyActivity {
    private EditText password;
    private EditText userEmail;
    private CallbackManager callbackManager;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textLogo = findViewById(R.id.logo_text_resto);
        Typeface customAcme = Typeface.createFromAsset(getAssets(), "fonts/Acme-Regular.ttf");
        textLogo.setTypeface(customAcme);

        TextView textLogoSearch = findViewById(R.id.logo_text_search);
        Typeface customShadow = Typeface.createFromAsset(getAssets(), "fonts/ShadowsIntoLight.ttf");
        textLogoSearch.setTypeface(customShadow);

        userEmail = findViewById(R.id.user_email);
        password = findViewById(R.id.password);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());

        TextView textLogin = findViewById(R.id.login_button);
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formLogin();
            }
        });

        TextView notAccount = findViewById(R.id.not_account);
        notAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        auth = FirebaseAuth.getInstance();

        final LoginButton loginButton = findViewById(R.id.login_fb_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Utils.log("se cancelo ");
            }

            @Override
            public void onError(FacebookException error) {
                Utils.log("fb error " + error);
            }
        });


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        auth.signOut();
//        FirebaseUser currentUser = auth.getCurrentUser();
//        Utils.log("currentUser" + currentUser);
////        updateUI(currentUser);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void formLogin() {
        String userEmailForm = userEmail.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userEmailForm)) {
            toast(this.getString(R.string.empty_email));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmailForm).matches()) {
            toast(this.getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(userPassword)) {
            toast(this.getString(R.string.empty_password));
        } else if (userPassword.length() < 4) {
            toast(this.getString(R.string.short_password));
        } else {
            startActivity(Utils.intentUserSharedPreferences(this,
                    CategoriesActivity.class, true, false));
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(Utils.intentUserSharedPreferences(LoginActivity.this,
                                    CategoriesActivity.class, true, true));
                        } else {
                            toast("Sorry, error trying login with your facebook account");
                        }
                    }
                });
    }
}
