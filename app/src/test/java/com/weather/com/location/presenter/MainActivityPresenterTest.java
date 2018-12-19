package com.weather.com.location.presenter;

import com.weather.com.location.model.ForecastData;

import org.junit.runner.RunWith;

/**
 * Created by Abhisek on 28-11-2018.
 */


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    private MainActivityPresenter presenter;

    @Mock
    private ForecastData forecastData;

    @Mock
    private MainActivityPresenter.IMainActivityView iMainActivityView;

    @Before
    public void setUpTesting() throws Exception{
        MockitoAnnotations.initMocks(this);
        presenter = Mockito.spy(new MainActivityPresenter(iMainActivityView));

     }

     @Test
     public void testEmptyWeatherData() throws Exception{
        //Trigger
        presenter.getCurrentTemp("");

        //Validation
         Mockito.verify(iMainActivityView, Mockito.never()).showErrorScreen();
        //verify(iMainActivityView).showErrorScreen();
     }

     @Test
     public void testWeatherData() throws Exception{
        String query = "Bengaluru";

        //Trigger
        presenter.getCurrentTemp(query);

        //validation

         Mockito.verify(iMainActivityView, Mockito.times(1)).showCurrentTemp(query);


     }
}