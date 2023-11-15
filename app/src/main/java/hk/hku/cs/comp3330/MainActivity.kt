package hk.hku.cs.comp3330

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
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
        val cacheFile: File = File(cacheDir, "password")
        if (!cacheFile.exists()) {
            val dialog_builder = AlertDialog.Builder(this).setMessage("Please input your HKU credentials below")
            val dialog_layout = LinearLayout(this)
            dialog_layout.orientation = LinearLayout.VERTICAL
            val user_name = EditText(this)
            val password = EditText(this)
            user_name.hint = "Username"
            password.hint = "Password"
            password.transformationMethod = PasswordTransformationMethod()
            dialog_layout.addView(user_name)
            dialog_layout.addView(password)
            dialog_builder.setView(dialog_layout)
            dialog_builder.setPositiveButton("OK", {dialog: DialogInterface, which: Int ->
                File.createTempFile("username", null, cacheDir)
                File.createTempFile("password", null, cacheDir)
                var cacheFile: File = File(cacheDir, "username")
                cacheFile.writeText(user_name.text.toString())
                cacheFile = File(cacheDir, "password")
                cacheFile.writeText(password.text.toString())
            })
            dialog_builder.show()

            //File.createTempFile("password", null, cacheDir)
            //val cacheFile: File = File(cacheDir, "password")
            //cacheFile.writeText("")
        }

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