package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.R;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.databinding.ActivityFilmsBinding;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments.FilmsListFragment;

import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.SCREEN.FRAGMENT_FILMS;


public class FilmsActivity extends AppCompatActivity {
    public ActivityFilmsBinding mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_films);
        mBinder.toolBar.setTitle(getString(R.string.activity_films_tool_bar_title));
        setSupportActionBar(mBinder.toolBar);
        if (savedInstanceState == null) {
            FilmsListFragment filmsListFragment = FilmsListFragment.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, filmsListFragment, FRAGMENT_FILMS.name());
            fragmentTransaction.addToBackStack(FRAGMENT_FILMS.name());
            fragmentTransaction.commitAllowingStateLoss();
        }
        mBinder.ibFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FilmsActivity.this,
                        FavouriteFilmsActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void removeIconFromToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mBinder.ibFavourites.setVisibility(View.VISIBLE);

        }
    }

    public void addBackToToolBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
            mBinder.ibFavourites.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            if (getFragmentManager().findFragmentById(R.id.fragment_container) ==
                    getFragmentManager().findFragmentByTag(FRAGMENT_FILMS.name())) {
                finish();
            }
            else{
                removeIconFromToolBar();
                mBinder.toolBar.setTitle(getString(R.string.activity_films_tool_bar_title));
            }
        } else {
            super.onBackPressed();
        }
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
}
