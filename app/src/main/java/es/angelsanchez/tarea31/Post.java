package es.angelsanchez.tarea31;

import android.util.Log;

import es.angelsanchez.tarea31.db.ComicDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Post {

    XkcdService xkcdService;

    public void getPostBD(int num, ComicDatabase db) {
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
                try {
                    String title = resource.getTitle();
                    int day = resource.getDay();
                    int month = resource.getMonth();
                    int year = resource.getYear();
                    String img = resource.getImg();
                    db.createComic(num, img, title, day, month, year);
                    Log.d("TAG", "titulo + " + title + "dia " + day);

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
    }



}
