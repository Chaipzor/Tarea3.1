package es.angelsanchez.tarea31;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ViewHolder>  {

    private List<Comic> listaComics;
    private final OnUserClickListener listener;

    // Pasamos el array de comics al constructor
    public ComicsAdapter(OnUserClickListener listener){
        this.listaComics = new ArrayList<>();
        this.listener = listener;
    }

    public void updateComics(List<Comic> listaComics){
        this.listaComics = listaComics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comic_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    // Rellena la lista a través de los holders.
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the data model based on position
        Comic comic = listaComics.get(position);
        String title = comic.getTitle();
        String id = String.valueOf(comic.getId());
        String date = (comic.getDay()) + "/" + (comic.getMonth()) +
                "/" + (comic.getYear());

        holder.tituloTextView.setText(title);
        holder.idTextView.setText(id);
        holder.fechaTextView.setText(date);
        holder.tituloTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listener.onUserClick(comic);
            }
        });
    }

    // Devuelve el nº total de elementos de la lista
    @Override
    public int getItemCount() {
        return listaComics.size();
    }

    public interface OnUserClickListener{
        void onUserClick(Comic userClicked);
    }



}