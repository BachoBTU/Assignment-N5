package com.example.assignmentn5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity(){

    private lateinit var noteEditText: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonDelete: Button
    private lateinit var noteTextVieW: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        val db = NoteDbHelper(this)

        val resultLst = db.selectAllNotes(1)

        noteTextVieW.text = resultLst.toString()

        buttonAdd.setOnClickListener{
            val info = noteEditText.text.toString()

            if(info.isEmpty()){
                Toast.makeText(this, "you didnt write anything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = noteTextVieW.text.toString()

            val result = note + "\n" + info


            db.insertNote(result)
            noteTextVieW.text = result
            noteEditText.text.clear()
        }

        buttonDelete.setOnClickListener{
            noteTextVieW.text = ""
            db.deleteAll()
        }

    }

    private fun init() {
        noteEditText = findViewById(R.id.noteEditText)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonDelete = findViewById(R.id.buttonDelete)
        noteTextVieW = findViewById(R.id.noteTextView)
    }
}
