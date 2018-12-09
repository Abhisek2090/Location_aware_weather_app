package com.weather.go_jek.go_jek_assignment.presenter;

import android.text.format.DateFormat;
import android.util.Log;

import com.weather.go_jek.go_jek_assignment.constants.Constants;
import com.weather.go_jek.go_jek_assignment.eventbus.Event;
import com.weather.go_jek.go_jek_assignment.eventbus.EventBus;
import com.weather.go_jek.go_jek_assignment.eventbus.EventSubscriber;
import com.weather.go_jek.go_jek_assignment.model.AvgTempResponseData;
import com.weather.go_jek.go_jek_assignment.model.CurrentResponseData;
import com.weather.go_jek.go_jek_assignment.model.DayObject;
import com.weather.go_jek.go_jek_assignment.model.ForecastData;
import com.weather.go_jek.go_jek_assignment.model.ForecastDayResponse;
import com.weather.go_jek.go_jek_assignment.model.ForecastObjectResponse;
import com.weather.go_jek.go_jek_assignment.restservice.RestService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Abhisek on 25-11-2018.
 */

public class MainActivityPresenter extends PresenterStub implements EventSubscriber {

    private static final String TAG = MainActivityPresenter.class.getSimpleName();


    @Inject
    EventBus eventBus;

    @Inject
    RestService restService;

    private IMainActivityView iMainActivityView;




    public MainActivityPresenter(IMainActivityView iMainActivityView) {
        this.iMainActivityView =iMainActivityView;
    }

    @Override
    public void onCreate() {
        eventBus.subscribe(this);
    }
    @Override
    public void onDestroy() {
        eventBus.unSubscribe();
    }


    public void getWeatherData( String locality) {
        String key = "7ba34d4c637a42e6b5d80417182511";
       // String location = "Paris";

        restService.getForecastTemperature(key, locality, Constants.GET_WEATHER_DATA);
    }
    public void getCurrentTemp( String locality) {
        String key = "7ba34d4c637a42e6b5d80417182511";
        // String location = "Paris";

        restService.getCurrentTemperature(key, locality, Constants.GET_CURRENT_TEMP);
    }
    @Override
    public void onEvent(Event event) {
        switch (event.getType()) {
            case Event.TYPE_SUCCESS:
                switch (event.getRequestCode()) {
                    case Constants.GET_WEATHER_DATA:
                        if (event.getStatusCode() == 200 || event.getStatusCode() == 201) {
                            Log.i(TAG, "getStatusCode");
                            ForecastData forecastResponseDataServerResponse = event.getResult();
                            if (forecastResponseDataServerResponse != null) {
                                List<ForecastDayResponse> forecastDayResponses = forecastResponseDataServerResponse.getForecast().getForecastday();
                                if(forecastDayResponses != null) {
                                    Log.i(TAG, String.valueOf(forecastDayResponses.size()));
                                    iMainActivityView.updateTempForecast(forecastDayResponses);
                                }

                            }

                        }
                        break;

                        case Constants.GET_CURRENT_TEMP:
                            if (event.getStatusCode() == 200 || event.getStatusCode() == 201) {
                                CurrentResponseData serverResponse = event.getResult();
                                if(serverResponse != null) {
                                    String currentTemp = String.valueOf(serverResponse.getCurrent().getTemp_c());
                                    iMainActivityView.showCurrentTemp(currentTemp);

                                }

                            }


                }
                break;

            case Event.TYPE_COMPLETION:
                iMainActivityView.hideProgressBar();


                break;

            case Event.TYPE_ERROR:
                iMainActivityView.hideProgressBar();
                iMainActivityView.showErrorScreen();
                break;


        }
    }

    public interface IMainActivityView {

        void updateTempForecast(List<ForecastDayResponse> forecastDayResponses);

        void showCurrentTemp(String temp);

        void hideProgressBar();

        void showErrorScreen();

        void hideErrorScreen();

    }
}
