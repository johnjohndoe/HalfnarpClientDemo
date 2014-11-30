package info.metadude.android.halfnarpclient.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.List;

import info.metadude.java.library.halfnarp.ApiModule;
import info.metadude.java.library.halfnarp.TalkPreferencesService;
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

}
