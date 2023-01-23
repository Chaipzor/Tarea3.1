package es.angelsanchez.tarea31;

import java.util.ArrayList;

public class ComicCRUD {
    static ArrayList<Comic> listaComics = new ArrayList<>();

    public void cargarBBDD(){

    }

    public void actualizarBBDD(){

    }

    public void anyadir(Comic comic){
        this.listaComics.add(comic);
    }

    public Comic leer(int id){
        for(int i = 0; i<listaComics.size(); i++){
            if(listaComics.get(i).getId()==id){
                return listaComics.get(i);
            }
        }
        return null;
    }

    public ComicCRUD() {
    }

    public ArrayList<Comic> getListaComics() {
        return listaComics;
    }

    public static void setListaComics(ArrayList<Comic> listaComics) {
        ComicCRUD.listaComics = listaComics;
    }
}
