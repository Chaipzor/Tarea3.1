package es.angelsanchez.tarea31;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import es.angelsanchez.tarea31.db.ComicDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inicio extends AppCompatActivity {

    Button btnVer;
    Button btnRecord;
    final int MAXCOMICS = 2715;
    XkcdService xkcdService;
    private Executor executor;
    boolean flag;
    ComicCRUD listaComics = new ComicCRUD();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ComicDatabase db = new ComicDatabase(Inicio.this);
        listaComics.setListaComics(db.retrieveComics());

        btnRecord = findViewById(R.id.btn_Record);

        //Muestra el historial
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abrimos la nueva actividad y le pasamos el arrayList con todos los Comics.
                Intent listaCms = new Intent(Inicio.this, ComicsAdapter.class);
                //listaCms.putExtra("lista", listaComics);
                listaCms.putExtra("lista", listaComics.getListaComics());
                startActivity(listaCms);
            }
        });

        btnVer = findViewById(R.id.btn_Ver);
        //Muestra el comic elegido
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit = findViewById(R.id.Number);
                int num = Integer.parseInt(mEdit.getText().toString());

                //Max. 2715
                if (num < 0 || num > MAXCOMICS) {
                    Toast.makeText(Inicio.this, "Debe estar entre 0 y 2715", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        boolean existe = comprobarComic(num, db);
                        if (existe) {
                            Log.d("TAG", "Ya estaba en la bd");
                        } else {
                            //getPost(num, db);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent comicA = new Intent(Inicio.this, ComicActivity.class);
                    comicA.putExtra("id", num);
                    startActivity(comicA);
                }
            }
        });
    }

    //Lee el json de la web
    public void getPost(int num, ComicDatabase db) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        xkcdService = retrofit.create(XkcdService.class);

        Call<Comic> request = xkcdService.getComic(num);

        request.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {

                Comic resource = response.body();
                String title = (String) resource.getTitle();
                int day = (Integer) resource.getDay();
                int month = (Integer) resource.getMonth();
                int year = (Integer) resource.getYear();
                String img = (String) resource.getImg();
                db.createComic(num, img, title, day, month, year);

                Log.d("TAG", "titulo + " + title + "dia " + day);
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public boolean comprobarComic(int num, ComicDatabase db) throws InterruptedException {
        flag = false;
        executor = ((MyApplication) getApplication()).diskIOExecutor;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Añadimos a la BBDD los nuevos datos del comic seleccionado.
                ArrayList<Comic> listado = listaComics.getListaComics();
                for (Comic comic : listado) {
                    if (comic.getId() == num) {
                        flag = true;
                    }
                }
            }
        });
        return flag;
    }

}