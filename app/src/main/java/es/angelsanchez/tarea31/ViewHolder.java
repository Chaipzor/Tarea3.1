package es.angelsanchez.tarea31;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView idTextView;
        public TextView tituloTextView;
        public TextView fechaTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            idTextView = itemView.findViewById(R.id.id);
            tituloTextView =  itemView.findViewById(R.id.titulo);
            fechaTextView = itemView.findViewById(R.id.fecha);
        }
    }
