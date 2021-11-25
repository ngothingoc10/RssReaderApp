package retrofit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.model.Category;
import retrofit.model.News;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.8:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    //Call API
    @GET("news/{newsId}/categories")
    Call<List<Category>> getCategories(@Path("newsId") int newsId);

    //Call API
    @GET("news")
    Call<List<News>> getNews();

    //Call API
    @POST("news")
    Call<News> addNews(@Body News news);

    @POST("news/{newsId}/categories")
    Call<Category> addCategories(@Path("newsId") int newsId,@Body Category category);
    @GET("{newsTitle}")
    Call<News> getNews2(@Path("newsTitle") String newsTitle);

    // api delete news
    @DELETE("news/{newsId}")
    Call<News> deleteNews(@Path("newsId") int newsId);
}
