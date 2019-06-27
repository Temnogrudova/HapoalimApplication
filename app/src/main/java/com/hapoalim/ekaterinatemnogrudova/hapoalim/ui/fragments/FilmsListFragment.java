package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.R;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.databinding.FragmentListFilmsBinding;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.TMDBApi.TMDBService;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.SchedulerProvider;
import java.util.ArrayList;
import java.util.List;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.SCREEN.FRAGMENT_FILM;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.SCREEN.FRAGMENT_FILMS;

public class FilmsListFragment extends Fragment  implements FilmsListContract.View, FilmsListAdapter.IFilmClicked {
    public FragmentListFilmsBinding mBinder;
    private FilmsListContract.Presenter mPresenter;
    private List<Film> mFilms = new ArrayList<>();
    private FilmsListAdapter mAdapter;
    private boolean isLoading = false;
    private int page = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public static FilmsListFragment newInstance() {
        return new FilmsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_list_films, container, false);
        View view = mBinder.getRoot();
        mPresenter = new FilmsListPresenter(this, new TMDBService(), new SchedulerProvider());
        mBinder.filmsList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new FilmsListAdapter(mFilms, getActivity(), this, FRAGMENT_FILMS);
        mBinder.filmsList.setAdapter(mAdapter);
        initImagesListScrollListener();
        if (savedInstanceState ==null) {
            getFilms();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onFilmClick(Film currentFilm) {
        FilmFragment filmFragment = FilmFragment.newInstance();
        Bundle filmItem = new Bundle();
        final Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
        filmItem.putString( FRAGMENT_FILM.name(), gson.toJson(currentFilm));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        if (filmItem != null) {
            filmFragment.setArguments(filmItem);
        }
        fragmentTransaction.add(R.id.fragment_container, filmFragment, FRAGMENT_FILM.name());
        fragmentTransaction.addToBackStack(FRAGMENT_FILM.name());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void setPresenter(FilmsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getFilmsSuccess(List<Film> result) {
        mBinder.networkProgress.setVisibility(View.GONE);
        isLoading = false;
        mFilms.addAll(result);
        mAdapter.notifyDataSetChanged();
    }

    private void initImagesListScrollListener() {
        mBinder.filmsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading) {
                    if (!recyclerView.canScrollVertically(1)) {
                        page++;
                        getFilms();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void getFilms() {
        mPresenter.getFilms(String.valueOf(page));
        mBinder.networkProgress.setVisibility(View.VISIBLE);
    }
}
