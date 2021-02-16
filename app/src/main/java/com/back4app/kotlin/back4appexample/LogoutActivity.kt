package com.back4app.kotlin.back4appexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.parse.ParseException
import com.parse.ParseUser

class LogoutActivity : AppCompatActivity() {
    var logout: Button? = null
    private var internal_layout: ConstraintLayout? = null
    private var progress_bar: ProgressBar? = null
    private var list_button:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        progress_bar = findViewById(R.id.progress_bar)
        internal_layout= findViewById(R.id.internal_layout)
        list_button= findViewById(R.id.list_button)
        logout = findViewById(R.id.logout)
        list_button?.setOnClickListener{
            val intent = Intent(this,ListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        logout?.setOnClickListener {
            progress_bar?.visibility= View.VISIBLE
            internal_layout?.visibility= View.GONE

            // logging out of Parse
            ParseUser.logOutInBackground { e: ParseException? ->
                if (e == null){
                    val snackbar = Snackbar.make(logout!!, "Logging out User",
                            Snackbar.LENGTH_LONG)
                    snackbar.show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}
