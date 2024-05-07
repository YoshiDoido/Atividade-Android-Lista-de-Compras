package com.example.app_listadecompras

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val produtosAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val listViewProduto = findViewById<ListView>(R.id.listViewProdutos)
        val btnInserir = findViewById<Button>(R.id.btnInserir)
        val txtProduto = findViewById<EditText>(R.id.txtProduto)

        listViewProduto.adapter = produtosAdapter

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            if(produto.isNotEmpty()){
                produtosAdapter.add(produto)
                txtProduto.text.clear()
            } else{
                txtProduto.error = "Coloque um Produto"
            }

        }

        listViewProduto.setOnItemClickListener { adapterView, view, position, id ->
            val item = produtosAdapter.getItem(position)
            produtosAdapter.remove(item)
        }

        listViewProduto.setOnItemLongClickListener { adapterView, view, position, id ->
            val item = produtosAdapter.getItem(position)

            // Create an EditText and pre-fill it with the current item's text
            val editText = EditText(this)
            editText.setText(item)

            // Create an AlertDialog to get the new text
            AlertDialog.Builder(this)
                .setTitle("Edit Item")
                .setView(editText)
                .setPositiveButton("OK") { dialog, whichButton ->
                    val newText = editText.text.toString()
                    if(newText.isNotEmpty()){
                        // Update the item in the adapter
                        produtosAdapter.remove(item)
                        produtosAdapter.insert(newText, position)
                    } else{
                        editText.error = "Item cannot be empty"
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()

            true // Return true to indicate that the long click was handled
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}