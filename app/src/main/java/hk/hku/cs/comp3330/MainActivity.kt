package hk.hku.cs.comp3330

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomnav = this.findViewById<BottomNavigationView>(R.id.bottom_menu)
        val moodle_fragement = MoodleFragment()

        bottomnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_button -> {}
                R.id.calendar_button -> {}
                R.id.moodle_button -> {
                    val ft = getSupportFragmentManager().beginTransaction()
                    ft.replace(R.id.frameFragment, MoodleFragment())
                    ft.commit()
                }
                R.id.portal_button -> {}

            }
            true
        }
    }
}