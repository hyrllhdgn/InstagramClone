package com.hyrllhdgn.instagram.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hyrllhdgn.instagram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth= Firebase.auth

        //eğer kullanıcı bir kere giriş yapmış ise sürekli şifre istememek için çalışan kodlar:
        val currentUser = auth.currentUser
        if (currentUser!=null){
            val intent = Intent(applicationContext, FeedActivity::class.java)
            startActivity(intent)
            finish()

        }



    }
    //Giriş ekranında SINGIN e basıldığında çalışacak komut:
    fun singInClicked(view: View){
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()
        }else {
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }






    }

    //Giriş ekranında SINGUP a basıldığında çalışacak komut:
    fun singUpClicked(view: View){

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        //bu bilgilerin boş olup olmadıklarının kontrolunu yapmak gerek:
        if (email.equals("") || password.equals("")) {
            Toast.makeText(this,"Enter email and password!",Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //başarılı bir giriş olduğunda:
                val intent = Intent(this@MainActivity, FeedActivity::class.java)
                startActivity(intent)
                finish()
                //kullanıcının geri dönmesini istemediğimiz için finish metodunu çağırdık.


            }.addOnFailureListener {
                //hatalı bir giriş olduğunda:
                Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }



}