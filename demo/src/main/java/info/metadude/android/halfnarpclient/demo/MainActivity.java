package info.metadude.android.halfnarpclient.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.List;

import info.metadude.java.library.halfnarp.ApiModule;
import info.metadude.java.library.halfnarp.TalkPreferencesService;
import info.metadude.java.library.halfnarp.model.PostSuccessResponse;
import info.metadude.java.library.halfnarp.model.TalkIds;
import info.metadude.java.library.halfnarp.model.TalkPreferencesResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        sendTalkPreferences();
    }

    private void loadData() {
        TalkPreferencesService service = ApiModule.getTalkPreferencesService();
        service.getTalkPreferencesResponse(new Callback<List<TalkPreferencesResponse>>() {
            @Override
            public void success(
                    List<TalkPreferencesResponse> talkPreferencesResponses,
                    Response response) {
                for (TalkPreferencesResponse talkPreferencesResponse : talkPreferencesResponses) {
                    Log.d(getClass().getName(), talkPreferencesResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(getClass().getName(), "Error = " + error);
            }
        });
    }


    private void sendTalkPreferences() {
        TalkIds talkIds = new TalkIds();
        talkIds.add(5930);
        talkIds.add(5931);

        TalkPreferencesService service = ApiModule.getTalkPreferencesService();
        service.postTalkPreferences(talkIds, new Callback<PostSuccessResponse>() {

            @Override
            public void success(PostSuccessResponse postResponse, Response response) {
                Log.d(getClass().getName(), postResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(getClass().getName(), "Error = " + error);
            }
        });
    }

}
