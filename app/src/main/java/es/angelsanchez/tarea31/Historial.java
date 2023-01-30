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

        rvlistaComics = findViewById(R.id.recyclerlistadoComics);
        rvlistaComics.setLayoutManager(new LinearLayoutManager(this));

        // Lookup the recyclerview in activity layout
        //RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerlistadoComics);
        // Set layout manager to position the items
        //rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // Create adapter passing in the sample user data
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
        // Attach the adapter to the recyclerview to populate items
        rvlistaComics.setAdapter(adapter);

        // That's all!

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