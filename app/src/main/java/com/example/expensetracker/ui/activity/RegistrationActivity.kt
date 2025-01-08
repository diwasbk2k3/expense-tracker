package com.example.expensetracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expensetracker.R
import com.example.expensetracker.databinding.ActivityRegistrationBinding
import com.example.expensetracker.model.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding

    lateinit var auth : FirebaseAuth

    // database instance
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // table's instance (table creation)
    var ref: DatabaseReference = database.reference.child("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.signUp.setOnClickListener {
            // Get input data
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val firstName = binding.registerFname.text.toString()
            val lastName = binding.registerLname.text.toString()
            val address = binding.registerAddress.text.toString()
            val phone = binding.registerContact.text.toString()

            // Validate the form fields
            if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                // Show error if any field is empty
                Toast.makeText(this@RegistrationActivity, "Please fill in all fields", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            // Proceed with Firebase registration if all validations pass
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val userModel = userModel(userId.toString(), firstName, lastName, address, phone, email)
                    ref.child(userId.toString()).setValue(userModel).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Show success toast
                            Toast.makeText(this@RegistrationActivity, "Registration success", Toast.LENGTH_LONG).show()

                            // Redirect to Login Activity
                            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                            startActivity(intent)

                            // Optionally finish the RegistrationActivity to prevent going back to it
                            finish()

                        } else {
                            Toast.makeText(this@RegistrationActivity, it.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this@RegistrationActivity, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}