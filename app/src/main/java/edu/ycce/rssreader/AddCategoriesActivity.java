package edu.ycce.rssreader;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.api.ApiService;
import retrofit.model.Category;
import retrofit.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoriesActivity extends AppCompatActivity {
    TextView textSelection;
    Spinner categories;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_categories );
        Spinner categories = (Spinner) findViewById( R.id.spinner_news );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
        getNewsAPI();



    }



    public void addCategories(View view){
        EditText name_input = (EditText) findViewById( R.id.name_input );
        EditText url_input = (EditText) findViewById( R.id.feed_url );

        Spinner categories = (Spinner) findViewById( R.id.spinner_news );
        String name = name_input.getText().toString();
        String url = url_input.getText().toString();
        String title = String.valueOf(categories.getSelectedItem()).trim();

        ApiService.apiService.getNews2( title).enqueue( new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response){
                News news = response.body();
                int newsId = news.getNewsId();
 //               String newsId = String.valueOf( tmp );
                if (!(name.equals( "" )) && !(url.equals( "" ))) {

                    Category obj = new Category();
                    obj.setCategoriesTitle( name);
                    obj.setRssLink( url );
                    obj.setNewsId( newsId );
                    ApiService.apiService.addCategories( obj.getNewsId(), obj).enqueue( new Callback<Category>() {
                        @Override
                        public void onResponse(Call<Category> call, Response<Category> response){
                            response.body();

                        }

                        @Override
                        public void onFailure(Call<Category> call, Throwable throwable){
                            Toast.makeText( AddCategoriesActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                    Toast toast = Toast.makeText( AddCategoriesActivity.this, "Source Successfully Added", Toast.LENGTH_SHORT );
                    toast.show();
                    Intent intent = new Intent( AddCategoriesActivity.this, MainActivity.class );
                    startActivity( intent );
                } else {
                    Toast toast = Toast.makeText( AddCategoriesActivity.this, "Please enter name and url", Toast.LENGTH_SHORT );
                    toast.show();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable throwable){
                Toast.makeText( AddCategoriesActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();
            }
        } );


    }

    List<News> getNewsAPI(){
        final List<News> newsList =  new ArrayList<>();
        ApiService.apiService.getNews().enqueue( new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response){
                newsList.addAll( response.body() );
                //Lấy đối tượng Spinner ra
                Spinner spin = (Spinner) findViewById( R.id.spinner_news );
                //Gán Data source (newsTileList) vào Adapter
                String[] newsTileList = new String[newsList.size()];
                for(int i=0; i< newsList.size(); i++){
                    newsTileList[i] = newsList.get( i ).getNewsTitle() ;

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        ( AddCategoriesActivity.this, android.R.layout.simple_spinner_item, newsTileList );
                //phải gọi lệnh này để hiển thị danh sách cho Spinner
                adapter.setDropDownViewResource
                        ( android.R.layout.simple_spinner_dropdown_item  );
                //Thiết lập adapter cho Spinner
                spin.setAdapter( adapter );
                //thiết lập sự kiện chọn phần tử cho Spinner
                spin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){

//
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView){
                        textSelection.setText("");
                    }
                } );




            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable throwable){
                Toast.makeText( AddCategoriesActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();
            }
        } );
        return newsList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}
