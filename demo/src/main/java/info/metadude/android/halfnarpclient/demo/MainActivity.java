package info.metadude.android.halfnarpclient.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import info.metadude.java.library.halfnarp.ApiModule;
import info.metadude.java.library.halfnarp.TalkPreferencesService;
import info.metadude.java.library.halfnarp.model.CreateTalkPreferencesSuccessResponse;
import info.metadude.java.library.halfnarp.model.GetTalkPreferencesSuccessResponse;
import info.metadude.java.library.halfnarp.model.GetTalksResponse;
import info.metadude.java.library.halfnarp.model.TalkIds;
import info.metadude.java.library.halfnarp.model.UpdateTalkPreferencesSuccessResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(httpLoggingInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        mService = ApiModule.getTalkPreferencesService(okHttpClient);
    }

    private void getTalks() {
        Call<List<GetTalksResponse>> getTalksCall = mService.getTalks();
        getTalksCall.enqueue(new Callback<List<GetTalksResponse>>() {
            @Override
            public void onResponse(Call<List<GetTalksResponse>> call,
                                   Response<List<GetTalksResponse>> response) {
                if (response.isSuccessful()) {
                    onGetTalksSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<GetTalksResponse>> call, Throwable t) {
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
                    public void onResponse(Call<CreateTalkPreferencesSuccessResponse> call,
                                           Response<CreateTalkPreferencesSuccessResponse> response) {
                        if (response.isSuccessful()) {
                            onCreateTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateTalkPreferencesSuccessResponse> call,
                                          Throwable t) {
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
                    public void onResponse(Call<UpdateTalkPreferencesSuccessResponse> call,
                                           Response<UpdateTalkPreferencesSuccessResponse> response) {
                        if (response.isSuccessful()) {
                            onUpdateTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateTalkPreferencesSuccessResponse> call,
                                          Throwable t) {
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
                    public void onResponse(Call<GetTalkPreferencesSuccessResponse> call,
                                           Response<GetTalkPreferencesSuccessResponse> response) {
                        if (response.isSuccessful()) {
                            onGetTalkPreferencesSuccess(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GetTalkPreferencesSuccessResponse> call, Throwable t) {
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
