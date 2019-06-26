package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.R;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.databinding.FragmentFilmBinding;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.activities.FilmsActivity;
import jp.wasabeef.glide.transformations.CropTransformation;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.BASE_IMAGE_URL;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.SCREEN.FRAGMENT_FILM;

public class FilmFragment extends Fragment {
    protected FragmentFilmBinding mBinder;
    private Film mFilm;

    public static FilmFragment newInstance() {
        return new FilmFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_film, container, false);
        View view = mBinder.getRoot();
        if (getArguments() != null) {
            final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
            final String json = getArguments().getString( FRAGMENT_FILM.name(), null);
            mFilm = gson.fromJson(json, Film.class);
            ((FilmsActivity) getActivity()).addBackToToolBar();
            ((FilmsActivity) getActivity()).mBinder.toolBar.setTitle(mFilm.getTitle());
            String date = String.format( getActivity().getResources().getString(R.string.fragment_film_date), mFilm.getReleaseDate());
            mBinder.date.setText(date);
            String rating = String.format( getActivity().getResources().getString(R.string.fragment_film_rating), String.valueOf(mFilm.getPopularity()));
            mBinder.popularity.setText(rating);
            mBinder.description.setText(mFilm.getOverview());
            Glide.with(mBinder.image.getContext())
                    .load(BASE_IMAGE_URL+ mFilm.getPosterPath()).bitmapTransform(new CropTransformation(mBinder.image.getContext()))
                    .into(mBinder.image);
        }
        return view;
    }
}
