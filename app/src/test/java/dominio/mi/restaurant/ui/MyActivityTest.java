package dominio.mi.restaurant.ui;

import android.content.Intent;
import android.view.MenuItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.fakes.RoboMenuItem;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;

import dominio.mi.restaurant.R;
import dominio.mi.restaurant.ui.activities.LoginActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Dumevi Cruces on 28/01/18.
 */

@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {
    private MyActivity activity;

    @Before
    public void init() {
        activity = Robolectric.setupActivity(MyActivity.class);
    }

    @Test
    public void menuItemSelected_shouldStartLoginActivity() {
        MenuItem menuItem = new RoboMenuItem(R.id.action_logout);
        activity.menuCaseLogin(menuItem);

        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();

        Assert.assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void menuItemSelected_shouldCloseActivity() {
        MenuItem menuItem = new RoboMenuItem(android.R.id.home);
        activity.menuCaseLogin(menuItem);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        assertTrue(shadowActivity.isFinishing());
    }
}
