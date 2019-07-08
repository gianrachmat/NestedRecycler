package test.myapplication.module;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.co.nlab.nframework.base.Module;
import id.co.nlab.nframework.base.ViewState;
import retrofit2.Response;
import test.myapplication.support.api.ApiClient;
import test.myapplication.support.api.ApiInterface;

public class OneModule {
    private Context context;
    private ViewState vs;
    public static final String TAG = "OneModule";

    public OneModule(Context context, ViewState vs) {
        this.context = context;
        this.vs = vs;
    }

    public void doRequest(final String dataName, String tag) {
        final ApiInterface api = new ApiClient().mainClient();
        final Module module = new Module(context, vs);
        module.getNetwork().networkConfiguration(tag);
        module.getNetwork().loading(true, "Loading...");

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<String> service = api.service(dataName).execute();
                        module.getNetwork().loading(false, "");
                        if (service.isSuccessful()) {
                            JSONObject response = new JSONObject(service.body());
                            String message = response.getString("message");
                            if (response.getString("code").equals("00")) {
                                module.getNetwork().success(response, message);
                            } else {
                                module.getNetwork().failure(response, "ERROR");
                            }
                        } else {
                            JSONObject error = new JSONObject(service.errorBody() != null ? service.errorBody().string() : null);
                            module.getNetwork().failure(error, "ERROR");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> toObjectList(JSONArray jsonArray, Class<T> tClass) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
            try {
                list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), tClass));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return list;
    }
}
