package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.R;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.databinding.ActivityFavouriteFilmsBinding;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments.FilmsListAdapter;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFilmsActivity extends AppCompatActivity implements FilmsListAdapter.IFilmClicked {
    public ActivityFavouriteFilmsBinding mBinder;
    private List<Film> mFilms = new ArrayList<>();
    private SharedPreference sharedPreference;
    private FilmsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_favourite_films);
        mBinder.toolBar.setTitle(getString(R.string.activity_favourite_films_tool_bar_title));
        setSupportActionBar(mBinder.toolBar);
        sharedPreference = new SharedPreference();
        addBackToToolBar();
        if (sharedPreference.getFavorites(this)!=null) {
            mFilms.addAll(sharedPreference.getFavorites(this));
        }
        mBinder.favouriteFilmsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new FilmsListAdapter(mFilms, this, this, Constants.SCREEN.ACTIVITY_FAVOURITE_FILMS);
        mBinder.favouriteFilmsList.setAdapter(mAdapter);
    }


    public void addBackToToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, null);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFilmClick(Film currentFilm) {

    }
}
