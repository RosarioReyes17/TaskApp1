package com.ar.projectfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.core.util.PatternsCompat
import androidx.databinding.DataBindingUtil
import com.ar.projectfb.databinding.ActivityLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity2 : AppCompatActivity() {

    private lateinit var binding : ActivityLogin2Binding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login2)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        loginUser()
    }


    fun register(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            validate()
            val user: String = binding.editTextUser.text.toString()
            val password: String = binding.editTextPassword.text.toString()

            if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.editTextUser.text.toString(),
                    binding.editTextPassword.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showProfile(
                                it.result?.user?.uid ?: "",
                                it.result?.user?.email ?: ""
                            )
                            finish()
                        } else {
                            errorMessage()
                        }
                    }
            }
        }
        }

    private fun validate(){
        val result = arrayOf(validateEmail(), validatePassword())

        if(false in result)
        {
            return
        }

    }
    private fun validateEmail() : Boolean{
        val email = binding.editTextUser.editableText.toString()
        return if(email.isEmpty()){
            binding.editTextUser.error = "Field can not be empty"
            false
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editTextUser.error = "Please enter a valid email address"
            false
        }else{
            binding.editTextUser.error = null
            true
        }

    }
    private fun validatePassword() : Boolean{
        val password = binding.editTextPassword.editableText.toString()

        return if(password.isEmpty()){
            binding.editTextPassword.error = "Field can not be empty"
            false
        }else{
            binding.editTextPassword.error = null
            true
        }
    }

    private fun showProfile(userId:String, email:String)
    {
        val profileIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("UserId", userId)
            putExtra("email", email)
        }
        startActivity(profileIntent)

    }

    private fun errorMessage(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("The user could not be authenticated")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
