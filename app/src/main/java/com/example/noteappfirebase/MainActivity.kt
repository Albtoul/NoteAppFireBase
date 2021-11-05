package com.example.noteappfirebase

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var TAG = "MaineActivity"
    private lateinit var rc: RecyclerView
    private lateinit var ed: EditText
    private lateinit var notebutton: Button

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed=findViewById(R.id.editTextTextPersonName)
        notebutton=findViewById(R.id.button)
        rc=findViewById(R.id.RVv)
        retrive()
        notebutton.setOnClickListener {
            if (ed.text.isNotEmpty()) {
                val user = hashMapOf("note" to ed.text.toString())


// Add a new document with a generated ID
                db.collection("users").add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        retrive()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }


            } else
                Toast.makeText(applicationContext, "fill the blank ", Toast.LENGTH_SHORT).show()
                ed.text.clear()



        }
}
    fun retrive(){

        // retrive data

        db.collection("users").get().addOnSuccessListener { result ->
            var details = arrayListOf<List<Any>>()

            for (document in result) {

                document.data.map { (key, value) ->
                    details.add(listOf(document.id, value))
                }
            }
            rc.adapter = RV(this, details)
            rc.layoutManager = LinearLayoutManager(this)

        }
            .addOnFailureListener { exception -> Log.w(TAG, "error", exception) }


    }
fun delete(id:String){

    db.collection("users").document(id).delete()
    retrive()

}



    fun dilogfun(id:String) {

        val build = AlertDialog.Builder(this)
        val update = EditText(this)
        update.hint = " enter new note for update "
        build.setCancelable(false)
            .setPositiveButton("save", DialogInterface.OnClickListener() { _, _ ->
                if (update != null) {
                    val user = hashMapOf("note" to update.text.toString())
                    db.collection("users").document(id).set(update, SetOptions.merge())
                }
            })
            .setNegativeButton("deny", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })
        val alert = build.create()
        alert.setTitle("Update Note")
        alert.setView(update)
        alert.show()

    }
}