package es.angelsanchez.tarea31;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import es.angelsanchez.tarea31.db.ComicDatabase;

public class ComicActivity extends AppCompatActivity {

    private Executor executor;
    Button btn_Siguiente;
    Button btn_Anterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        cargarComic();
    }

    public void cargarComic(){
        TextView tvTitulo = (TextView) findViewById(R.id.titulo);
        TextView tvfecha = (TextView) findViewById(R.id.fecha);
        int id = getIntent().getIntExtra("id",0);
        ComicDatabase db = new ComicDatabase(ComicActivity.this);
        executor = ((MyApplication)getApplication()).diskIOExecutor;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Leemos de la BD
                ArrayList<Comic> listaComics = db.retrieveComics();
                String titulo = listaComics.get(id).getTitle();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTitulo.setText(titulo);
                    }
                });


            }
        });
    }
}