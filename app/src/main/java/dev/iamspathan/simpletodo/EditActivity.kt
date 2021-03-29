package dev.iamspathan.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditActivity : AppCompatActivity() {

    lateinit var etEditText: EditText
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.title = "Edit Item"
        etEditText = findViewById(R.id.etEditItem)
        btnSave = findViewById(R.id.btnEdit)
        etEditText.setText(intent.getStringExtra(MainActivity.KEY_ITEM_TEXT))

        val position = intent.getIntExtra(MainActivity.KEY_ITEM_POSITION, -1)
        Toast.makeText(applicationContext, "$position", Toast.LENGTH_SHORT).show()
        btnSave.setOnClickListener {
            //Create Intent
            val intent = Intent()
            intent.putExtra(MainActivity.KEY_ITEM_TEXT, etEditText.text.toString())
            intent.putExtra(MainActivity.KEY_ITEM_POSITION, position)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}