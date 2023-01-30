package es.angelsanchez.tarea31;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.angelsanchez.tarea31.db.ComicDatabase;

public class Inicio extends AppCompatActivity {

    Button btnVer;
    Button btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ComicDatabase db = new ComicDatabase(Inicio.this);

        // Al pulsar el botón "Record" nos lleva a la actividad "Historial"
        btnRecord = findViewById(R.id.btn_Record);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inicio.this, Historial.class);
                startActivity(i);
            }
        });

        //Al pulsar el botón "Ver" guardará el número introducido en el textbox y abrirá ComicActivity
        //Además guardará si el número existía en BBDD o no y también lo pasa a la nueva actividad
        btnVer = findViewById(R.id.btn_Ver);
        //Muestra el comic elegido
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit = findViewById(R.id.Number);
                int id = Integer.parseInt(mEdit.getText().toString());
                boolean exist = false;

                //En caso de introducir un número negativo aparece un aviso
                if (id < 0) {
                    Toast.makeText(Inicio.this, "Debe ser un número mayor a 0", Toast.LENGTH_LONG).show();
                } else {
                    Comic comic = new Comic();

                    //Se comprueba si existe el id en la BBDD
                    comic = db.existe(id);

                    if (comic != null) {
                        Log.d("TAG", "Ya estaba en la bd");
                        exist = true;
                    } else {

                        //En caso de no existir en la BBDD se lee el JSON y se graba en BBDD
                        //Este proceso tarda unos 40 segundos en hacerse...
                        Post post = new Post();
                        post.getPostBD(id, db);
                    }
                    //Pasamos a la actividad ComicActivity el id y si existía en bbdd o no.
                    Intent comicA = new Intent(Inicio.this, ComicActivity.class);
                    comicA.putExtra("id", id);
                    comicA.putExtra("exist", exist);
                    startActivity(comicA);
                }
            }
        });
    }

}