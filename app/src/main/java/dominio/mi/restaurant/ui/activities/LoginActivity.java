package dominio.mi.restaurant.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.ui.MyActivity;

public class LoginActivity extends MyActivity {
    private EditText password;
    private EditText userEmail;

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
                    CategoriesActivity.class, true));
        }
    }
}
