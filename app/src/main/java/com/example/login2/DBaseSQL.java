package com.example.login2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBaseSQL extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 2; // Versión de la base de datos
    private static final String TABLE_NAME = "users"; // Nombre de la tabla
    private static final String COL_ID = "id"; // Columna ID
    private static final String COL_NAME = "name"; // Columna nombre
    private static final String COL_AGE = "age"; // Columna edad
    private static final String COL_EMAIL = "email"; // Nueva columna email

    // Constructor de la clase
    public DBaseSQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "users"
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_AGE + " INTEGER," +
                COL_EMAIL + " TEXT)"; // Añadimos la nueva columna
        db.execSQL(createTable); // Ejecutar la sentencia SQL para crear la tabla
        Log.d("DBaseSQL", "Tabla creada: " + TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Agregar la nueva columna "email" si la versión es inferior a 2
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_EMAIL + " TEXT");
            Log.d("DBaseSQL", "Tabla actualizada a la versión 2: columna email añadida.");
        }
    }

    // Método para insertar un usuario
    public boolean insertUser(String name, int age, String email) {
        SQLiteDatabase db = this.getWritableDatabase(); // Obtener la base de datos en modo escritura
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_AGE, age);
        contentValues.put(COL_EMAIL, email);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.d("DBaseSQL", "Usuario insertado: " + name + ", " + age);
        return result != -1; // Devolver true si la inserción fue exitosa
    }

    // Método para actualizar un usuario (sin cambios)
    public boolean updateUser(int id, String name, int age, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_AGE, age);
        contentValues.put(COL_EMAIL, email); // Actualizar el valor del email
        int result = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{String.valueOf(id)});
        Log.d("DBaseSQL", "Usuario actualizado ID: " + id);
        return result > 0; // Devolver true si la actualización fue exitosa
    }

    // Método para eliminar un usuario (sin cambios)
    public boolean deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        Log.d("DBaseSQL", "Usuario eliminado ID: " + id);
        return result > 0; // Devolver true si la eliminación fue exitosa
    }

    // Método para obtener todos los usuarios
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase(); // Obtener la base de datos en modo lectura
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null); // Devolver el cursor con todos los registros
    }
}
