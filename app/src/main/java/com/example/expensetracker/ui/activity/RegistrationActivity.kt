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
import com.example.expensetracker.model.UserModel
import com.example.expensetracker.repository.UserRepositoryImpl
import com.example.expensetracker.utils.LoadingUtils
import com.example.expensetracker.viewmodel.UserViewModel

class RegistrationActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding

    lateinit var userViewModel: UserViewModel

    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)


        binding.signUp.setOnClickListener {
            loadingUtils.show()
            var email = binding.registerEmail.text.toString()
            var password = binding.registerPassword.text.toString()
            var firstName = binding.registerFname.text.toString()
            var lastName = binding.registerLname.text.toString()
            var address = binding.registerAddress.text.toString()
            var phone = binding.registerContact.text.toString()



            userViewModel.signup(email, password) {
                    success, message, userId ->
                if (success) {
                    var userModel = UserModel(
                        userId.toString(), firstName,
                        lastName, address,
                        phone, email
                    )
                    userViewModel.addUserToDatabase(userId,userModel){
                            success,message->
                        if(success){
                            loadingUtils.dismiss()
                            Toast.makeText(
                                this@RegistrationActivity,
                                message,
                                Toast.LENGTH_LONG
                            ).show()
                        }else{
                            loadingUtils.dismiss()
                            Toast.makeText(
                                this@RegistrationActivity,
                                message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(
                        this@RegistrationActivity,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener {
//                    if (it.isSuccessful) {
//
//                        var userId = auth.currentUser?.uid
//
//                        var userModel = UserModel(
//                            userId.toString(), firstName,
//                            lastName, address,
//                            phone, email
//                        )
//
//                        ref.child(userId.toString()).setValue(userModel)
//                            .addOnCompleteListener {
//                            if(it.isSuccessful){
//                                Toast.makeText(
//                                    this@RegistrationActivity,
//                                    "Registration success", Toast.LENGTH_LONG
//                                ).show()
//                            }else{
//                                Toast.makeText(
//                                    this@RegistrationActivity,
//                                    it.exception?.message, Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//
//
//                    } else {
//                        Toast.makeText(
//                            this@RegistrationActivity,
//                            it.exception?.message, Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}