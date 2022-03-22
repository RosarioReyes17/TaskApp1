package com.ar.projectfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ar.projectfb.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("User")
        register()
    }

    private fun register() {

        binding.btnRegister.setOnClickListener {
            val name: String = binding.txtName.text.toString()
            val lastName: String = binding.txtLastName.text.toString()
            val phone: String = binding.txtPhone.text.toString()
            val email: String = binding.txtEmail.text.toString()
            val password: String = binding.txtPassword.text.toString()
            val gender: String = binding.txtGender.text.toString()
            val dateOfBirthday: String = binding.editDate.text.toString()
            val country: String = binding.txtCountry.text.toString()
            val address: String = binding.txtAddress.text.toString()
            if (TextUtils.isEmpty(name)) {
                binding.txtName.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(lastName)) {
                binding.txtLastName.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(phone)) {
                binding.txtPhone.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(gender)) {
                binding.txtGender.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(dateOfBirthday)) {
                binding.editDate.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(country)) {
                binding.txtCountry.error = "Field can not be empty"
            } else if (TextUtils.isEmpty(address)) {
                binding.txtAddress.error = "Field can not be empty"
            } else
            {
              auth.createUserWithEmailAndPassword(
                    email.toString(),
                    password.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            val userDB = dbReference.child((user?.uid!!))

                            verifyEmail(user)

                            userDB.child("Name").setValue(name)
                            userDB.child("LastName").setValue(lastName)
                            userDB.child("Phone").setValue(phone)
                            userDB.child("Email").setValue(email)
                            userDB.child("Gender").setValue(gender)
                            userDB.child("DateOfBirth").setValue(dateOfBirthday)
                            userDB.child("Country").setValue(country)
                            userDB.child("Address").setValue(address)
                            finish()

                        }
                    }
            }

        }
    }
    private fun verifyEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    Toast.makeText(this,"Email sent", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this,"ERROR in sending the email", Toast.LENGTH_LONG).show()
                }
            }
    }
}