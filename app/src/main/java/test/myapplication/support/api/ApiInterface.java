package test.myapplication.support.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("/b/{data}")
    Call<String> service(@Path("data") String data);
}
