package hk.hku.cs.comp3330

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomnav = this.findViewById<BottomNavigationView>(R.id.bottom_menu)
        val home_fragment = HomeFragment()
        val calendar_fragment = CalendarFragment()
        val moodle_fragement = MoodleFragment()
        val portal_fragment = PortalFragment()
        val chatGpt_Fragment = ChatGptFragment()

        // Test
        //val cacheFile: File = File(cacheDir, "password")
        //if (!cacheFile.exists()) {
        //    File.createTempFile("password", null, cacheDir)
        //    val cacheFile: File = File(cacheDir, "password")
        //    cacheFile.writeText("")
        //}

        bottomnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_button -> {
                    val ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameFragment, home_fragment)
                    ft.commit()
                }
                R.id.calendar_button -> {
                    val ft = getSupportFragmentManager().beginTransaction()
                    ft.replace(R.id.frameFragment, calendar_fragment)
                    ft.commit()
                }
                R.id.moodle_button -> {
                    val ft = getSupportFragmentManager().beginTransaction()
                    ft.replace(R.id.frameFragment, moodle_fragement)
                    ft.commit()
                }
                R.id.portal_button -> {
                    val ft = getSupportFragmentManager().beginTransaction()
                    ft.replace(R.id.frameFragment, portal_fragment)
                    ft.commit()
                }
                R.id.chatgpt_button -> {
                    val ft = getSupportFragmentManager().beginTransaction()
                    ft.replace(R.id.frameFragment, chatGpt_Fragment)
                    ft.commit()
                }
            }
            true
        }
    }
}