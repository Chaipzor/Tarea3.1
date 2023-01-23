package es.angelsanchez.tarea31;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//POJO
public class Comic {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("img")
    @Expose
    private String img;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("day")
    @Expose
    private int day;

    @SerializedName("month")
    @Expose
    private int month;

    @SerializedName("year")
    @Expose
    private int year;

    public Comic(int id, String img, String title, int day, int month, int year) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Comic() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
