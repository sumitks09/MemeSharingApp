package com.abc.memesharingKS

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var mShare:Button



    private val url = "https://meme-api.herokuapp.com/gimme"
     private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mShare=findViewById(R.id.ShareButton)

        mShare.setOnClickListener{
            val myIntent= Intent(Intent.ACTION_SEND)
            myIntent.type="type/plain"
            val shareBody = "You are body"
            val shareSub = "you subject here"
            myIntent.putExtra(Intent.EXTRA_SUBJECT , shareBody)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
            startActivity(Intent.createChooser(myIntent , "Share Using "))
        }



        imageView=findViewById(R.id.my_imageView)

        loadImageFromApi();

    }

    private fun loadImageFromApi() {

        val queue = Volley.newRequestQueue(this);
        val request= JsonObjectRequest(Request.Method.GET,this.url,null,
            { response ->

        Log.d("Result",response.toString())
         Picasso.get().load(response.get("url").toString()).placeholder(R.drawable.loader).into(imageView);
            },
            {
           Log.e("error" ,it.toString())

                Toast.makeText(applicationContext,"Error in loading the meme from server",Toast.LENGTH_LONG).show()

            }
        )
        queue.add(request);

    }

    fun changeImage(view: View) {
        this.loadImageFromApi()
    }




}