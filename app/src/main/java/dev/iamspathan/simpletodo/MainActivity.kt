package dev.iamspathan.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(), onItemLongClick, onItemClick {

    lateinit var etTaskName: EditText
    lateinit var addButton: Button
    lateinit var rvItems: RecyclerView
    lateinit var itemAdapter: ItemsAdapter

    companion object {

        const val KEY_ITEM_TEXT = "KEY_ITEM_TEXT"
        const val KEY_ITEM_POSITION = "KEY_ITEM_POSITION"
        const val REQUEST_CODE = 101
    }

    var list: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etTaskName = findViewById(R.id.taskNameEt)
        addButton = findViewById(R.id.btnAdd)
        rvItems = findViewById(R.id.rvItems)

        loadItems()

        itemAdapter = ItemsAdapter(list, this, this)
        rvItems.apply {
            adapter = itemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        addButton.setOnClickListener {
            addItem(etTaskName.text.toString())
            itemAdapter.notifyItemInserted(list.size - 1)
            etTaskName.text.clear()
            Toast.makeText(applicationContext, "Item was added", Toast.LENGTH_SHORT).show()
            writeToFile()
        }
    }

    fun addItem(itemName: String) {

        if (etTaskName.text.isNotBlank()) {
            list.add(itemName)
        } else {
            Toast.makeText(applicationContext, "Items cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(item: Int) {
        list.removeAt(item)
        itemAdapter.notifyItemRemoved(item)
        Toast.makeText(applicationContext, "Item was removed", Toast.LENGTH_SHORT).show()
        writeToFile()
    }

    fun getFile(): File {
        return File(filesDir, "data.txt")
    }

    fun loadItems() {
        try {
            list = ArrayList(FileUtils.readLines(getFile(), Charset.defaultCharset()))
        } catch (e: IOException) {
            Log.e("MainActivity", "Error reading Items", e)
            list = ArrayList()
        }
    }

    fun writeToFile() {
        try {
            FileUtils.writeLines(getFile(), list)
        } catch (e: IOException) {
            Log.e("MainActivity", "Error Writing Items", e)
        }
    }

    override fun onClick(item: Int) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(KEY_ITEM_TEXT, list[item])
        intent.putExtra(KEY_ITEM_POSITION, item)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            val value = data?.getStringExtra(KEY_ITEM_TEXT)
            val position = data?.getIntExtra(KEY_ITEM_POSITION, -1)
            list[position!!] = value!!
            itemAdapter.notifyItemChanged(position)
            writeToFile()
            Toast.makeText(applicationContext, "$position", Toast.LENGTH_SHORT).show()
        }
    }
}