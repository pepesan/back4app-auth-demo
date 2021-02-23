package com.back4app.kotlin.back4appexample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseException
import com.parse.ParseUser


class LoginActivity : AppCompatActivity() {

    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var login: Button? = null
    private var navigatesignup: Button? = null
    private var internal_layout: ConstraintLayout? = null
    private var progress_bar: ProgressBar? = null
    var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progress_bar = findViewById(R.id.progress_bar)
        internal_layout= findViewById(R.id.internal_layout)
        username = findViewById(R.id.username)
        username?.requestFocus()
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        navigatesignup = findViewById(R.id.navigatesignup)

        login?.setOnClickListener(View.OnClickListener {
            login(
                    username?.text.toString(),
                    password?.text.toString()
            )
        })

        navigatesignup?.setOnClickListener(View.OnClickListener {
            startActivity(
                    Intent(
                            this@LoginActivity,
                            SignUpActivity::class.java
                    )
            )
        })
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        val sharedSessionKey = sharedPref?.getString("session_key", "")
        Log.d("app", "OnCreate SharedSessionKey: $sharedSessionKey")
        if (sharedSessionKey!="") {
            ParseUser.becomeInBackground(sharedSessionKey) { user, e ->
                if (user != null) {
                    Log.d("app", "Valid Session Token: " + user.username)
                    val intent = Intent(this, LogoutActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    Log.d("app", "Invalid Session token: " + e.localizedMessage)
                }
            }
        }
    }

    fun login(username: String, password: String) {
        //progressDialog?.show()
        progress_bar?.visibility=View.VISIBLE
        internal_layout?.visibility=View.GONE

        ParseUser.logInInBackground(username, password) { parseUser: ParseUser?, parseException: ParseException? ->
            if (parseUser != null) {
                Log.d("app", "User session Token: " + parseUser.sessionToken)
                val sharedPref = getPreferences(Context.MODE_PRIVATE)
                sharedPref.edit().
                    putString("session_key", parseUser.sessionToken).apply()
                val sharedSessionKey = sharedPref.getString("session_key", "")
                Log.d("app", "Login: session key $sharedSessionKey")
                if (sharedSessionKey!=""){
                    ParseUser.becomeInBackground(sharedSessionKey) { parseUser, parseException ->
                        if(parseException!=null){
                            Log.d("app", "Invalid Session token: " + parseException.localizedMessage)
                        }else{
                            Log.d("app", "Valid Session Token: username: " + parseUser.username)
                        }
                    }
                }
                Log.d("app", "SharedSessionKey: $sharedSessionKey")
                //showAlert("Successful Login", "Welcome back " + username + " !")
                val snackbar = Snackbar.make(login!!, "Autenticating User",
                        Snackbar.LENGTH_LONG)
                snackbar.show()
                val intent = Intent(this, LogoutActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                ParseUser.logOut()
                if (parseException != null) {
                    Toast.makeText(this, parseException.message, Toast.LENGTH_LONG).show()
                }
                progress_bar?.visibility=View.GONE
                internal_layout?.visibility=View.VISIBLE
            }
        }
    }
}