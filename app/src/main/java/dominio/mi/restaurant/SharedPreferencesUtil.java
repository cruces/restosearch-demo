package dominio.mi.restaurant;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Dumevi Cruces on 17/09/17.
 */

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("restaurant", Context.MODE_PRIVATE);
    }

    public void setUserRegistered(boolean userRegistered, boolean isFb) {
        sharedPreferences.edit().putBoolean("userRegistered", userRegistered).apply();
        if (isFb) {
            sharedPreferences.edit().putBoolean("userRegisteredFb", true).apply();
        } else {
            sharedPreferences.edit().putBoolean("userRegisteredFb", false).apply();
        }
    }

    public boolean getUserRegistered() {
        return sharedPreferences.getBoolean("userRegistered", false);
    }

    public boolean getUserIsRegisteredFb() {
        return sharedPreferences.getBoolean("userRegisteredFb", false);
    }

    public void cleanSharedPreferences() {
        sharedPreferences.edit().clear().apply();
    }
}
