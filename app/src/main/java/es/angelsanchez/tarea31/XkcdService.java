package es.angelsanchez.tarea31;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {

    @GET("{idComic}/info.0.json")
    Call<Comic> getComic(@Path("idComic") int idComic);
}
