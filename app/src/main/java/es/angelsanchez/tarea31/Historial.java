package es.angelsanchez.tarea31;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Historial extends AppCompatActivity {

    RecyclerView rvlistaComics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        rvlistaComics = findViewById(R.id.recyclerlistadoComics);
        rvlistaComics.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Comic> listado = (ArrayList<Comic>) getIntent().getSerializableExtra("lista");

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerlistadoComics);

        // Create adapter passing in the sample user data
        ComicsAdapter adapter = new ComicsAdapter(listado);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }
}