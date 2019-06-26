package com.hapoalim.ekaterinatemnogrudova.hapoalim;

import com.hapoalim.ekaterinatemnogrudova.hapoalim.TMDBApi.TMDBService;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.Film;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.models.FilmsResponse;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments.FilmsListContract;
import com.hapoalim.ekaterinatemnogrudova.hapoalim.ui.fragments.FilmsListPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import static com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.Constants.API_KEY;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FilmsPresenterTest {
    private FilmsListPresenter presenter;
    private TestSchedulerProvider testSchedulerProvider;
    private TestScheduler testScheduler;
    @Mock
    private FilmsListContract.View mView;
    @Mock
    private TMDBService pixabayService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);// required for the "@Mock" annotations
        testScheduler = new TestScheduler();
        testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        presenter  = new FilmsListPresenter(mView, pixabayService, testSchedulerProvider);
    }

    @Test
    public void handleGetFilmsResponse_Success(){
        FilmsResponse mockedResponse = new FilmsResponse();
        List<Film> films = new ArrayList<>();
        Film film = new Film();
        films.add(film);
        mockedResponse.setResults(films);
        Mockito.when(pixabayService.getFilms(API_KEY, ""))
                .thenReturn(Observable.just(mockedResponse));
        presenter.getFilms("");
        testScheduler.triggerActions();
        verify(mView).getFilmsSuccess(mockedResponse.getResults());
    }
}