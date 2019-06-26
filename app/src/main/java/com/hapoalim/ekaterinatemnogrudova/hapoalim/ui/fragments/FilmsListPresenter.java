package com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments;

import android.util.Log;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.FilmsResponse;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.TMDBApi.TMDBService;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.IScheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.API_KEY;

public class FilmsListPresenter implements FilmsListContract.Presenter {
    private FilmsListContract.View mView;
    private Disposable mDisposable;
    private IScheduler mScheduler;
    private TMDBService mTMDBService;

    public FilmsListPresenter(FilmsListContract.View view, TMDBService TMDBService, IScheduler scheduler) {
        mView = view;
        mView.setPresenter(this);
        mScheduler = scheduler;
        mTMDBService = TMDBService;
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void getFilms(String page) {
        mDisposable = mTMDBService.getFilms(API_KEY, page)
                    .subscribeOn(mScheduler.io())
                    .observeOn(mScheduler.ui()).subscribeWith(new DisposableObserver<FilmsResponse>() {
            @Override
            public void onNext(FilmsResponse response) {
                mView.getFilmsSuccess(response.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ERROR", "!!!!");
            }

            @Override
            public void onComplete() {
            }

        });
    }
}
