package es.angelsanchez.tarea31;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.angelsanchez.tarea31.db.ComicDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Post extends AppCompatActivity {

    XkcdService xkcdService;

    //Este método crea el comic "id" en la base de datos
    public void getPostBD(int id, ComicDatabase db, Context context) {

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
                    String title = resource.getTitle();
                    int day = resource.getDay();
                    int month = resource.getMonth();
                    int year = resource.getYear();
                    String img = resource.getImg();
                    db.createComic(id, img, title, day, month, year);
                    Log.d("TAG", "titulo + " + title + "dia " + day);
                    Intent comicA = new Intent(context, ComicActivity.class);
                    comicA.putExtra("id", id);
                    comicA.putExtra("exist", false);
                    context.startActivity(comicA);

                } catch (NullPointerException e) {
                    //En caso de no existir ese comic, regresa a la pantalla de inicio
                    Log.d("TAG", "Ese nº de comic no existe, no guarda en bbdd");
                    Toast.makeText(context, "Ese número de comic no existe", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                call.cancel();
            }
        });
    }



}
