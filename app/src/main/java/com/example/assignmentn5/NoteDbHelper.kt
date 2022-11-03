package com.example.assignmentn5

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class NoteDbHelper(context: Context) : SQLiteOpenHelper(context, DbConfig.DATABASE_NAME,  null, DbConfig.VERSION) {
    companion object{
        private const val SQL_CREATE_TABLE = "CREATE TABLE ${Note.TABLE_NAME} (" +
                "${Note.ID} INTEGER PRIMARY KEY, " +
                "${Note.TEXT} TEXT)"


        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${Note.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun insertNote(noteText: String){

        val value = ContentValues().apply {
            put(Note.TEXT, noteText)
        }

        writableDatabase.insert(Note.TABLE_NAME, null, value)
    }


    fun deleteAll() {
        writableDatabase.delete(Note.TABLE_NAME, null, null)
    }

    fun selectAllNotes(length: Int): ArrayList<String> {
        val projection = arrayOf(
            Note.TEXT,
            Note.ID
        )

        val where = "${Note.TEXT} > ?"
        val whereArgs = arrayOf(length.toString())
        val myArrayList = ArrayList<String>()

        val cursor =
            readableDatabase.query(Note.TABLE_NAME, projection, where, whereArgs, null, null, null)

        while (cursor.moveToNext()) {
            val notesText = cursor.getString(cursor.getColumnIndexOrThrow(Note.TEXT))

            Log.d("Notes","$notesText" )
            myArrayList.add(notesText)
        }
        cursor.close()

        return myArrayList
    }
}