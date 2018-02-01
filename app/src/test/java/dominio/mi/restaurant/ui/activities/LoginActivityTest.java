package dominio.mi.restaurant.ui.activities;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import dominio.mi.restaurant.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Dumevi Cruces on 29/01/18.
 */

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    private LoginActivity activity;
    private EditText password;
    private EditText email;
    private TextView loginButton;

    @Before

    public void init() {
        activity = Robolectric.setupActivity(LoginActivity.class);
        email = activity.findViewById(R.id.user_email);
        password = activity.findViewById(R.id.password);
        loginButton = activity.findViewById(R.id.login_button);
    }

    @Test
    public void clickNotAccount_shouldStartRegisterActivity() {
        activity.findViewById(R.id.not_account).performClick();

        Intent expectedIntent = new Intent(activity, RegisterActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();

        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void loginSuccess() {
        email.setText("email@gmail.com");
        password.setText("123456");

        loginButton.performClick();

        Intent started = ShadowApplication.getInstance().getNextStartedActivity();
        assertNotNull(started);
    }

    @Test
    public void loginFailure(){
        email.setText("invalidemail");
        password.setText("invpw");

        loginButton.performClick();

        Intent started = ShadowApplication.getInstance().getNextStartedActivity();

        assertNull(started);
    }
}
