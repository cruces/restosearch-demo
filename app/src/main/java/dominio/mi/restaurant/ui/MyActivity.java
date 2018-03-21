package dominio.mi.restaurant.ui;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.SharedPreferencesUtil;
import dominio.mi.restaurant.Utils;
import dominio.mi.restaurant.ui.activities.CategoriesActivity;
import dominio.mi.restaurant.ui.activities.LoginActivity;

/**
 * Created by Dumevi Cruces on 01/10/17.
 */

public class MyActivity extends AppCompatActivity {
    public static String ERROR_MESSAGE = "Sorry, an error occurred";
    private FirebaseAuth firebaseAuth;
    private LoginManager loginManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ERROR_MESSAGE = this.getString(R.string.sorry_an_error_occurred);
        firebaseAuth = FirebaseAuth.getInstance();
        loginManager = LoginManager.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryText),
                        PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

    public boolean menuCaseLogin(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_logout:
                SharedPreferencesUtil userData = new SharedPreferencesUtil(this);
                if (userData.getUserIsRegisteredFb()) {
                    startActivity(Utils.intentUserSharedPreferences(this, LoginActivity.class, false, true));
                    firebaseAuth.signOut();
                    loginManager.logOut();

                } else {
                    startActivity(Utils.intentUserSharedPreferences(this, LoginActivity.class, false, false));
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void getTitleApp() {
        TextView textLogoBar = findViewById(R.id.logo_text_restobar);
        textLogoBar.setTypeface(Utils.getFontsAcme(getAssets()));

        TextView textSearchBar = findViewById(R.id.logo_text_searchbar);
        textSearchBar.setTypeface(Utils.getFontShadow(getAssets()));
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
