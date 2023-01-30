package es.angelsanchez.tarea31.db;

import static es.angelsanchez.tarea31.db.ComicDatabaseSQLiteOpenHelper.TABLE_DATOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import es.angelsanchez.tarea31.Comic;


public class ComicDatabase {
    private ComicDatabaseSQLiteOpenHelper helper;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "historial.db";

    public ComicDatabase(Context context) {
        helper = new ComicDatabaseSQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    public void createComic(int id, String img, String title, int day, int month, int year) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("img", img);
        values.put("title", title);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);

        db.insert(TABLE_DATOS, null, values);
        db.close();
    }

    public void createComic(Comic comic) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", comic.getId());
        values.put("img", comic.getImg());
        values.put("title", comic.getTitle());
        values.put("day", comic.getDay());
        values.put("month", comic.getMonth());
        values.put("year", comic.getYear());

        db.insert(TABLE_DATOS, null, values);
        db.close();
    }

    //Devuelve null si no existe el comic en BBDD, si existe devuelve el comic.
    public Comic existe(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("select * from " +  TABLE_DATOS + " where id = ?",args);
        Comic comic = null;
        if(cursor.moveToFirst()){
            comic = new Comic(id, cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        }
        db.close();
        return comic;
    }

    public void updateComic(int id, String img, String title, int day, int month, int year){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String[] args = {String.valueOf(id)};
        values.put("id", id);
        values.put("img", img);
        values.put("title", title);
        values.put("day", day);
        values.put("month", month);
        values.put("year", year);
        db.update(TABLE_DATOS, values,"identifier = ?",args);
        db.close();
    }

    //Devuelve un único comic
    public Comic retrieveComic(int id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Comic comic = null;
        Cursor cursorComics = null;

        cursorComics = db.rawQuery("SELECT * FROM " + TABLE_DATOS + " Where id = " + id, null);
        if (cursorComics.moveToFirst()) {
            comic = new Comic();
            comic.setId(cursorComics.getInt(0));
            comic.setTitle(cursorComics.getString(1));
            comic.setDay(cursorComics.getInt(2));
            comic.setMonth(cursorComics.getInt(3));
            comic.setYear(cursorComics.getInt(4));
            comic.setImg(cursorComics.getString(5));
        }

        cursorComics.close();

        return comic;
    }

    //Devuelve un array de comics
    public ArrayList<Comic> retrieveComics(){
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<Comic> listaComics = new ArrayList<>();
        Cursor cursorComics = null;

        cursorComics = db.rawQuery("SELECT * FROM " + TABLE_DATOS, null);
        if (cursorComics.moveToFirst()) {
            do{
                Comic comic = new Comic();
                comic.setId(cursorComics.getInt(0));
                comic.setTitle(cursorComics.getString(1));
                comic.setDay(cursorComics.getInt(2));
                comic.setMonth(cursorComics.getInt(3));
                comic.setYear(cursorComics.getInt(4));
                comic.setImg(cursorComics.getString(5));
                listaComics.add(comic);
            } while(cursorComics.moveToNext());
        }

        cursorComics.close();
        db.close();

        return listaComics;
    }

}
