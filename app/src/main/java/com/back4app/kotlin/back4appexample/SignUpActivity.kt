package com.back4app.kotlin.back4appexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseUser
import com.parse.SignUpCallback


class SignUpActivity : AppCompatActivity() {
    var username: TextInputEditText? = null
    var password: TextInputEditText? = null
    var passwordagain: TextInputEditText? = null
    var signup: Button? = null
    private var internal_layout: ConstraintLayout? = null
    private var progress_bar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        progress_bar = findViewById(R.id.progress_bar)
        internal_layout= findViewById(R.id.internal_layout)
        username =findViewById(R.id.username)
        password =findViewById(R.id.password)
        passwordagain =findViewById(R.id.passwordagain)
        signup = findViewById(R.id.signup)
        signup?.setOnClickListener{
            internal_layout?.visibility= View.GONE
            progress_bar?.visibility=View.VISIBLE
            if(validateUser()) {
                val user = ParseUser();
                // Set the user's username and password, which can be obtained by a forms
                user.username = username?.text.toString();
                user.setPassword(password?.text.toString());
                user.signUpInBackground(SignUpCallback() {
                    if (it == null) {
                        val snackbar = Snackbar.make(signup!!, "SignIn done, User Authenticated",
                                Snackbar.LENGTH_LONG)
                        snackbar.show()
                        val intent = Intent(this, LogoutActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(this,"Wrong user of password", Toast.LENGTH_LONG).show();
            }
        }

    }

    private fun validateUser(): Boolean {
        if( username?.text.toString()!=null
            && password?.text.toString()!=null
            && passwordagain?.text.toString()!=null
            && password?.text.toString().equals(passwordagain?.text.toString())){
            return true
        }else{
            return false
        }
    }

}