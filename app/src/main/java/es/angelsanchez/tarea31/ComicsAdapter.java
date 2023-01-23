package es.angelsanchez.tarea31;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView idTextView;
        public TextView tituloTextView;
        public TextView fechaTextView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            idTextView = (TextView) itemView.findViewById(R.id.id);
            messageButton = (Button) itemView.findViewById(R.id.btn_Display);
            tituloTextView = (TextView) itemView.findViewById(R.id.titulo);
            fechaTextView = (TextView) itemView.findViewById(R.id.fecha);
        }
    }

    // Store a member variable for the contacts
    private List<Comic> listaComics;



    // Pass in the contact array into the constructor
    public ComicsAdapter(List<Comic> comics) {
        listaComics = comics;
    }

    @Override
    public ComicsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.comic_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ComicsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Comic comic = listaComics.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.idTextView;
        textView.setText(comic.getId());
        //Button button = holder.messageButton;
        //button.setText(comic.isOnline() ? "Message" : "Offline");
        //button.setEnabled(comic.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listaComics.size();
    }
}