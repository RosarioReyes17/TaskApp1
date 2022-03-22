package com.ar.projectfb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.ar.projectfb.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    var dbReference : DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database?.reference!!.child("User")
        loadProfile()


        super.onCreate(savedInstanceState)

    }

    private fun loadProfile(){
        title = "Profile"
        val user = auth.currentUser
        // val dbReference = database.reference.child("User")
        val userref = dbReference?.child(user?.uid!!)
        binding.txtProfileEmail.text = user?.email


        userref?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot){
                binding.txtProfileName.text = snapshot.child("Name").value.toString()
                binding.txtProfileLastName.text = snapshot.child("LastName").value.toString()
                binding.txtProfilePhone.text = snapshot.child("Phone").value.toString()
                binding.txtProfileGender.text = snapshot.child("Gender").value.toString()
                binding.txtProfileDate.text = snapshot.child("DateOfBirth").value.toString()
                binding.txtProfileCountry.text = snapshot.child("Country").value.toString()
                binding.txtProfileAddress.text = snapshot.child("Address").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity2::class.java))
            finish()
        }
    }


}