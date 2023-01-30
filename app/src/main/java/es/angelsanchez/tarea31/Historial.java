package es.angelsanchez.tarea31;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import es.angelsanchez.tarea31.db.ComicDatabase;

public class Historial extends AppCompatActivity {

    RecyclerView rvlistaComics;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        // Creamos el recyclerview
        rvlistaComics = findViewById(R.id.recyclerlistadoComics);
        rvlistaComics.setLayoutManager(new LinearLayoutManager(this));

        ComicsAdapter adapter = new ComicsAdapter(new ComicsAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(Comic comicClicked) {
                Intent comicA = new Intent(Historial.this, ComicActivity.class);
                int id = comicClicked.getId();
                comicA.putExtra("id", id);
                comicA.putExtra("exist", true);
                startActivity(comicA);
            }
        });
        // Vinculamos el recycleview con el adaptador para pasarle los items.
        rvlistaComics.setAdapter(adapter);

        executor = ((MyApplication)getApplication()).diskIOExecutor;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ComicDatabase db = new ComicDatabase(Historial.this);
                ArrayList<Comic> listaComics = db.retrieveComics();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTitle("Comics almacenados: " + listaComics.size());
                        adapter.updateComics(listaComics);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
}