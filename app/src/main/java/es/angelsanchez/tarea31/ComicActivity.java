package es.angelsanchez.tarea31;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;

import es.angelsanchez.tarea31.db.ComicDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComicActivity extends AppCompatActivity {

    private Executor executor;
    Button btn_Siguiente;
    Button btn_Anterior;
    int id;
    ComicDatabase db = new ComicDatabase(ComicActivity.this);
    XkcdService xkcdService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);

        //Recibe el id introducido por el usuario
        id = getIntent().getIntExtra("id", 0);

        //Recibe si el comic existía (true) o no (false) en la bbdd
        boolean exist = getIntent().getExtras().getBoolean("exist");
        if(exist){

            //Carga los datos de la bbdd en la actividad según su id
            cargarComicBD(id);
        } else{

            //Carga los datos de la web en la actividad según su id ya que no puede mirar en la bbdd
            //por el tiempo de carga que lleva esta.
            cargarComicJson(id);
        }
        btn_Anterior = findViewById(R.id.btn_Anterior);
        btn_Siguiente = findViewById(R.id.btn_Siguiente);
        btn_Anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar datos a mostrar al comic anterior
                id--;
                mostrarComic(id);
            }
        });
        btn_Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambiar datos a mostrar al comic siguiente
                id++;
                mostrarComic(id);
            }
        });

    }

    public void mostrarComic(int id){
        Comic comic = new Comic();
        comic = db.existe(id);
        Post post = new Post();
        if (comic != null) {
            Log.d("TAG", "Ya estaba en la bd: " + id);
            cargarComicBD(id);
        } else {
            //Además de leerse el json nuevamente y mostrarse en pantalla.
            comic = cargarComicJson(id);
            //Si no existía se leerá el json y se grabará en bbdd.
            if(comic!=null){
                db.createComic(comic);
            } else{
                Log.d("TAG", "Ese nº de comic no existe: " + id);
            }
        }
    }

    public void cargarComicBD(int id) {
        TextView tvTitulo = findViewById(R.id.titulo);
        TextView tvFecha = findViewById(R.id.fecha);
        ImageView imagen = findViewById(R.id.imagen);
        ComicDatabase db = new ComicDatabase(ComicActivity.this);
        executor = ((MyApplication) getApplication()).diskIOExecutor;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Leemos de la BDD
                try {
                    Comic comic = db.retrieveComic(id);
                    String titulo = comic.getTitle();
                    int dia = comic.getDay();
                    int mes = comic.getMonth();
                    int anyo = comic.getYear();
                    String img = comic.getImg();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTitulo.setText("Título:" + titulo);
                            tvFecha.setText("Fecha de publicación: " + dia + "/" + mes + "/" + anyo);
                            Picasso.get().load(img)
                                    .resize(300, 200)
                                    .centerCrop()
                                    .placeholder(R.drawable.progress_animation)
                                    .error(R.drawable.comic_missing)
                                    .noFade()
                                    .into(imagen);

                        }
                    });
                } catch (NullPointerException e) {
                    Log.d("TAG", "Ese nº de comic no existe, no existe todavía en bbdd");
                    //Toast.makeText(ComicActivity.this, "Ese número de comic no existe", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Comic cargarComicJson(int id) {
        Comic comic = new Comic();
        TextView tvTitulo = findViewById(R.id.titulo);
        TextView tvFecha = findViewById(R.id.fecha);
        ImageView imagen = findViewById(R.id.imagen);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        xkcdService = retrofit.create(XkcdService.class);

        Call<Comic> request = xkcdService.getComic(id);

        request.enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {

                Comic resource = response.body();
                try {
                    String titulo =  resource.getTitle();
                    int dia = resource.getDay();
                    int mes = resource.getMonth();
                    int anyo = resource.getYear();
                    String img = resource.getImg();
                    comic.setId(id);
                    comic.setTitle(titulo);
                    comic.setDay(dia);
                    comic.setMonth(mes);
                    comic.setYear(anyo);
                    Log.d("TAG", "titulo + " + titulo + "dia " + dia);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTitulo.setText("Título:" + titulo);
                            tvFecha.setText("Fecha de publicación: " + dia + "/" + mes + "/" + anyo);
                            Picasso.get().load(img)
                                    .resize(300, 200)
                                    .centerCrop()
                                    .placeholder(R.drawable.progress_animation)
                                    .error(R.drawable.comic_missing)
                                    .noFade()
                                    .into(imagen);
                        }
                    });

                } catch (NullPointerException e) {
                    Log.d("TAG", "Ese nº de comic no existe, no guarda en bbdd");
                    //Toast.makeText(Inicio.this, "Ese número de comic no existe", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                call.cancel();
            }
        });
        return comic;
    }

}