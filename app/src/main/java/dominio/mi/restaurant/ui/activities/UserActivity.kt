package dominio.mi.restaurant.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import dominio.mi.restaurant.R
import dominio.mi.restaurant.Utils
import dominio.mi.restaurant.ui.MyActivity

/**
 * Created by Dumevi Cruces on 07/02/18.
 */

class UserActivity : MyActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        getToolbar()
        val action = supportActionBar
        action!!.setDisplayShowTitleEnabled(false)
        action.setDisplayHomeAsUpEnabled(false)

        getTitleApp()

        val background = findViewById<ImageView>(R.id.background_profile)
        val imgProfile = findViewById<CircleImageView>(R.id.image_profile)

        val url = "http://vida20.com/wp-content/uploads/2014/05/portadas-nuevo-twitter-" +
                "1500x500-4.jpg"
        val urlProfile = "http://img.huffingtonpost.com/asset/,scalefit_950_800_noupscale/" +
                "57c4a6ce180000dd10bcde68.jpeg"

        Glide.with(this).load(url).into(background)
        Glide.with(this).load(urlProfile).into(imgProfile)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return Utils.menu(this, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return menuCaseLogin(item)
    }
}