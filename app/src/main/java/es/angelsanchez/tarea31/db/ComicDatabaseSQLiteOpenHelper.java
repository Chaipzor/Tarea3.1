package es.angelsanchez.tarea31.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ComicDatabaseSQLiteOpenHelper extends SQLiteOpenHelper {


    public static final String TABLE_DATOS = "t_comics";

    public ComicDatabaseSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_DATOS + "(" +
                "id INTEGER PRIMARY KEY, " +
                "title TEXT NOT NULL, " +
                "day INTEGER NOT NULL, " +
                "month INTEGER NOT NULL, " +
                "year INTEGER NOT NULL, " +
                "img TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_DATOS);
        onCreate(db);
    }
}
