package info.metadude.android.halfnarpclient.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.List;

import info.metadude.java.library.halfnarp.ApiModule;
import info.metadude.java.library.halfnarp.TalkPreferencesService;
import info.metadude.java.library.halfnarp.model.CreateTalkPreferencesSuccessResponse;
import info.metadude.java.library.halfnarp.model.GetTalksResponse;
import info.metadude.java.library.halfnarp.model.TalkIds;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTalks();
        createTalkPreferences();
    }

    private void getTalks() {
        TalkPreferencesService service = ApiModule.getTalkPreferencesService();
        service.getTalks(new Callback<List<GetTalksResponse>>() {
            @Override
            public void success(List<GetTalksResponse> getTalksResponses, Response response) {
                for (GetTalksResponse getTalkResponse : getTalksResponses) {
                    Log.d(getClass().getName(), getTalkResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(getClass().getName(), "Error = " + error);
            }
        });
    }


    private void createTalkPreferences() {
        TalkIds talkIds = new TalkIds();
        talkIds.add(5930);
        talkIds.add(5931);

        TalkPreferencesService service = ApiModule.getTalkPreferencesService();
        service.createTalkPreferences(
                talkIds,
                new Callback<CreateTalkPreferencesSuccessResponse>() {
                    @Override
                    public void success(
                            CreateTalkPreferencesSuccessResponse createTalkPreferencesResponse,
                            Response response) {
                        Log.d(getClass().getName(),
                                createTalkPreferencesResponse.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(getClass().getName(), "Error = " + error);
                    }
                });
    }

}
