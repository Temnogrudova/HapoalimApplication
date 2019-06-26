package com.hapoalim.ekaterinatemnogrudova.hapoalim.models;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

public class Film {
    @SerializedName("id")
    private int id;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Film other = (Film) obj;
        if (id !=other.id)
            return false;
        return true;
    }
}
