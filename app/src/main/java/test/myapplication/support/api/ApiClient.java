package test.myapplication.support.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private HttpLoggingInterceptor log = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final String BASE_URL = "https://api.jsonbin.io";
    private static final String SECRET_KEY = "$2a$10$bCBGl.oBMJ8Rn048md5OaOvZkPVPaOefqepDlcfGwXHyrqBAGgrC.";

    public ApiInterface mainClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("secret-key", SECRET_KEY)
                        .build();

                return chain.proceed(request);
            }
        };

        return createClient(ApiInterface.class, interceptor);
    }

    private <T> T createClient(Class<T> clientApi, Interceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(log)
                .addInterceptor(interceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        Retrofit r = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client.build())
                .build();

        return r.create(clientApi);
    }
}
