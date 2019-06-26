package com.hapoalim.ekaterinatemnogrudova.hapoalim.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "IMAGES_APP";
    public static final String FAVORITES = "Image_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Film> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Film image) {
        List<Film> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Film>();
        favorites.add(image);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Film image) {
        ArrayList<Film> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(image);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Film> getFavorites(Context context) {
        SharedPreferences settings;
        List<Film> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Film[] favoriteItems = gson.fromJson(jsonFavorites,
                    Film[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Film>(favorites);
        } else
            return null;

        return (ArrayList<Film>) favorites;
    }
}
