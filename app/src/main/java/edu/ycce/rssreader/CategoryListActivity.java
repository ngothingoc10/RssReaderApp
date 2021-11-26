package edu.ycce.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.api.ApiService;
import retrofit.model.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        final List<Category> categoriesList = new ArrayList<>();
        Intent intent = getIntent();
        int newsId = intent.getIntExtra("newsId", 0);

        super.onCreate( savedInstanceState );
        setContentView( R.layout.category_list );





        // Call API
        ApiService.apiService.getCategories(newsId).enqueue( new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response){
                Toast.makeText(CategoryListActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
                categoriesList.addAll(response.body());
                String[] name = new String[categoriesList.size()];

                String[] link = new String[categoriesList.size()];
                for (int i = 0; i< categoriesList.size(); i++) { // lấy ra all các link rss của các chủ đề tương ứng
                    name[i] = categoriesList.get( i ).getCategoriesTitle();
                    link[i] = categoriesList.get( i ).getRssLink();
                }
                FeedListFragment.urls = link;
                CategoryAdapter adapter= new CategoryAdapter( categoriesList, new IClickItemCategoryListener() {
                    @Override
                    public void onClickItemCategory(String url){
                        onClickGotToDeTail(url);
                    }
                } );
                RecyclerView recyclerView = findViewById( R.id.rcv_category );
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager( CategoryListActivity.this );
                recyclerView.setLayoutManager( linearLayoutManager );
                recyclerView.setAdapter( adapter );
                adapter.notifyDataSetChanged();
                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration( CategoryListActivity.this , DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration( itemDecoration );







            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable){
                Toast.makeText( CategoryListActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();

            }
        });



    }

    private List<Category> getListCategories(){
        List<Category> list = new ArrayList<>();
      return list;
    }
    private void onClickGotToDeTail(String url){
        //     Intent intent = new Intent(mContext, FeedListFragment.class );
        ListFragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString( "rssLink", url );
        fragment = new FeedListFragment();
        fragment.setArguments(bundle);



    }

}