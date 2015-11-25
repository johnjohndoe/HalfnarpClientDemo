package info.metadude.android.halfnarpclient.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

import info.metadude.java.library.halfnarp.ApiModule;
import info.metadude.java.library.halfnarp.TalkPreferencesService;
import info.metadude.java.library.halfnarp.model.CreateTalkPreferencesSuccessResponse;
import info.metadude.java.library.halfnarp.model.GetTalkPreferencesSuccessResponse;
import info.metadude.java.library.halfnarp.model.GetTalksResponse;
import info.metadude.java.library.halfnarp.model.TalkIds;
import info.metadude.java.library.halfnarp.model.UpdateTalkPreferencesSuccessResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {

    private TalkPreferencesService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTalkPreferencesService();
        getTalks();
        createTalkPreferences();
    }

    private void initTalkPreferencesService() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        List<Interceptor> interceptors = new ArrayList<>(1);
        if (BuildConfig.DEBUG) {
            interceptors.add(httpLoggingInterceptor);
        }
        mService = ApiModule.getTalkPreferencesService(interceptors);
    }

    private void getTalks() {
        Call<List<GetTalksResponse>> getTalksCall = mService.getTalks();
        getTalksCall.enqueue(new Callback<List<GetTalksResponse>>() {
            @Override
            public void onResponse(Response<List<GetTalksResponse>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    onGetTalksSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.e(getClass().getName(), "Error = " + t);
            }
        });
    }

    private void onGetTalksSuccess(List<GetTalksResponse> getTalksResponses) {
        for (GetTalksResponse getTalkResponse : getTalksResponses) {
            Log.d(getClass().getName(), getTalkResponse.toString());
        }
    }

    private void createTalkPreferences() {
        TalkIds talkIds = new TalkIds();
        talkIds.add(5930);
        talkIds.add(5931);

        Call<CreateTalkPreferencesSuccessResponse> createTalkPreferencesSuccessResponseCall =
                mService.createTalkPreferences(talkIds);
        createTalkPreferencesSuccessResponseCall.enqueue(
                new Callback<CreateTalkPreferencesSuccessResponse>() {
                    @Override
                    public void onResponse(Response<CreateTalkPreferencesSuccessResponse> response,
                                           Retrofit retrofit) {
                        if (response.isSuccess()) {
                            onCreateTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Log.e(getClass().getName(), "Error = " + t);
                    }
                });
    }

    private void onCreateTalkPreferencesSuccess(
            CreateTalkPreferencesSuccessResponse createTalkPreferencesResponse) {
        Log.d(getClass().getName(),
                createTalkPreferencesResponse.toString());
        String uniqueId = createTalkPreferencesResponse.getUid();
        updateTalkPreferences(uniqueId);
        getTalkPreferences(uniqueId);
    }

    private void updateTalkPreferences(String uniqueId) {
        TalkIds talkIds = new TalkIds();
        talkIds.add(1000);
        talkIds.add(7000);

        Call<UpdateTalkPreferencesSuccessResponse> updateTalkPreferencesSuccessResponseCall =
                mService.updateTalkPreferences(uniqueId, talkIds);
        updateTalkPreferencesSuccessResponseCall.enqueue(
                new Callback<UpdateTalkPreferencesSuccessResponse>() {
                    @Override
                    public void onResponse(Response<UpdateTalkPreferencesSuccessResponse> response,
                                           Retrofit retrofit) {
                        if (response.isSuccess()) {
                            onUpdateTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Log.e(getClass().getName(), "Error = " + t);
                    }
                });
    }

    private void onUpdateTalkPreferencesSuccess(
            UpdateTalkPreferencesSuccessResponse updateTalkPreferencesResponse) {
        Log.d(getClass().getName(), updateTalkPreferencesResponse.toString());
    }

    private void getTalkPreferences(String uniqueId) {
        Call<GetTalkPreferencesSuccessResponse> getTalkPreferencesSuccessResponseCall =
                mService.getTalkPreferences(uniqueId);
        getTalkPreferencesSuccessResponseCall.enqueue(
                new Callback<GetTalkPreferencesSuccessResponse>() {
                    @Override
                    public void onResponse(Response<GetTalkPreferencesSuccessResponse> response,
                                           Retrofit retrofit) {
                        if (response.isSuccess()) {
                            onGetTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                        Log.e(getClass().getName(), "Error = " + t);
                    }
                });
    }

    private void onGetTalkPreferencesSuccess(
            GetTalkPreferencesSuccessResponse getTalkPreferencesResponse) {
        Log.d(getClass().getName(), getTalkPreferencesResponse.toString());
    }

}
