package com.google.android.gms.samples.vision.ocrreader.openfda;

import android.support.annotation.NonNull;

import com.google.android.gms.samples.vision.ocrreader.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import timber.log.Timber;

public class OpenFDA {

    interface OpenFDAInterface {
        @GET("drug/label.json")
        Call<OpenFDALabelSearchResponse> label(@Query(value = "search", encoded = true) String query);
    }

    OpenFDAInterface mOpenFDA;

    public OpenFDA() {
        buildRetrofitService();
    }

    public Observable<OpenFDADrugLabel> queryLabel(@NonNull String drugName) {
        String query = "generic_name:" + drugName + "+brand_name:" + drugName;

        return Observable.create(subscriber -> {
            OpenFDADrugLabel label = null;
            try {
                Response<OpenFDALabelSearchResponse> response = mOpenFDA.label(query).execute();


                Timber.d(response.message());
                Timber.d(Integer.valueOf(response.code()).toString());
                if (response.errorBody() != null) {
                    Timber.w(response.errorBody().string());
                }
                Timber.d(response.body().toString());

                if (!response.isSuccessful()) {
                    subscriber.onError(new Throwable(String.valueOf(response.errorBody())));
                }

                List<OpenFDADrugLabel> labels = response.body().getDrugLabels();
                if (labels != null && labels.size() > 0) {
                    label = labels.get(0);
                    subscriber.onNext(label);
                    subscriber.onCompleted();
                }
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }

    private void buildRetrofitService() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }

        Interceptor requestInterceptor = chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl;

            Request request = original.newBuilder()
                    .url(url)
                    .method(original.method(), original.body())
                    .build();

            try {
                return chain.proceed(request);
            } catch (IOException e) {
                Timber.w("failed to create interceptor: " + e.getLocalizedMessage());
                throw e;
            }
        };
        clientBuilder.addInterceptor(requestInterceptor);

        Gson gson = new GsonBuilder()
                .create();

        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.fda.gov/")
                .client(client)
                .build();
        mOpenFDA = retrofit.create(OpenFDAInterface.class);
    }
}
