package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments;

import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.BaseView;
import java.util.List;

public class FilmsListContract {
    public interface View extends BaseView<Presenter> {
        void getFilmsSuccess(List<Film> result);
    }

    interface Presenter  {
        void getFilms(String page);
        void unsubscribe();
    }
}
