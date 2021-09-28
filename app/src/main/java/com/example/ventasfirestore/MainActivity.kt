package com.example.ventasfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    lateinit var btnList: Button
    lateinit var btnAdd: Button
    lateinit var tvList: TextView
    lateinit var etName: EditText
    lateinit var btnEdit: Button
    lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        btnList = findViewById(R.id.btnList)
        tvList = findViewById(R.id.tvList)
        btnAdd = findViewById(R.id.btnAdd)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)


        btnList.setOnClickListener {
            var dates = " "
            db.collection("Categoria")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        dates += "${document.data}\n"
                    }
                    tvList.text = dates
                }
                .addOnFailureListener { exception ->
                    tvList.text = "Not found"

                }
        }
        btnAdd.setOnClickListener {
            saveDates(db)
            }
        btnEdit.setOnClickListener {
            saveDates(db)

        }
        btnDelete.setOnClickListener {
            if (etName.text.isNotBlank()) {
                db.collection("Categoria")
                    .document(etName.text.toString())
                    .delete()
                    .addOnSuccessListener { _ ->
                        tvList.text = "Delete succes"
                    }
                    .addOnFailureListener { _ ->
                        tvList.text = "Not found"
                    }
        }
    }}
    private fun saveDates(dv:FirebaseFirestore){
        if (etName.text.isNotBlank()) {
            val date = hashMapOf(
                "name" to etName.text.toString()
            )
            db.collection("Categoria")
                .document()
                .set(date)
                .addOnSuccessListener { _ ->
                    tvList.text = "Succes"
                }
                .addOnFailureListener { _ ->
                    tvList.text = "Not found"
                }

    }
}}