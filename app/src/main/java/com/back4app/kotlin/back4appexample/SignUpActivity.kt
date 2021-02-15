package com.back4app.kotlin.back4appexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseUser
import com.parse.SignUpCallback


class SignUpActivity : AppCompatActivity() {
    var username: TextInputEditText? = null
    var password: TextInputEditText? = null
    var passwordagain: TextInputEditText? = null
    var signup: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        username =findViewById(R.id.username)
        password =findViewById(R.id.password)
        passwordagain =findViewById(R.id.passwordagain)
        signup = findViewById(R.id.signup)
        signup?.setOnClickListener{
            if(validateUser()) {
                val user = ParseUser();
                // Set the user's username and password, which can be obtained by a forms
                user.username = username?.text.toString();
                user.setPassword(password?.text.toString());
                user.signUpInBackground(SignUpCallback() {
                    if (it == null) {
                        showAlert("Successful Sign Up!", "Welcome" + user.username + "!");
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
        if(username?.text.toString()!=null && password?.text.toString()!=null
            && passwordagain?.text.toString()!=null
            && password?.text.toString().equals(passwordagain?.text.toString())){
            return true
        }else{
            return false
        }
    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                // don't forget to change the line below with the names of your Activities
                val intent = Intent(this, LogoutActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val ok = builder.create()
        ok.show()
    }
}