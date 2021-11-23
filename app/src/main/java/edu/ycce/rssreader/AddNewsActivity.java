package edu.ycce.rssreader;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.api.ApiService;
import retrofit.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_news );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled( true );
    }

    public void addNews(View view){
        EditText name_input = (EditText) findViewById( R.id.name_input );
        EditText url_input = (EditText) findViewById( R.id.feed_url );
//        Spinner categories = (Spinner) findViewById( R.id.categories );
        String name = name_input.getText().toString();
        String url = url_input.getText().toString();
//        String category = String.valueOf( categories.getSelectedItem() );
        if (!(name.equals( "" )) && !(url.equals( "" ))) {
//            feedSourceModel = new FeedSourceModel();
//            feedSourceModel.setUrl( url );
//            feedSourceModel.setCategory( category );
//            feedSourceModel.setName( name );
//            try {
//                sQLiteHelper.insertRecord( feedSourceModel );
//                Toast toast = Toast.makeText( this, "Source Successfully Added", Toast.LENGTH_SHORT );
//                toast.show();
//                Intent intent = new Intent( this, MainActivity.class );
//                startActivity( intent );
//            } catch (SQLiteException e) {
//                Log.v( "exception", e.getMessage() );
//                Toast toast = Toast.makeText( this, "Database unavailable", Toast.LENGTH_SHORT );
//                toast.show();
//            }
//            sQLiteHelper.insertRecord( feedSourceModel );
            News obj = new News();
            obj.setNewsTitle( name );
            obj.setDescription( url );
            ApiService.apiService.addNews(obj).enqueue( new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                   response.body();

                }

                @Override
                public void onFailure(Call<News> call, Throwable throwable) {
                    Toast.makeText(AddNewsActivity.this, "Call API Error!", Toast.LENGTH_SHORT).show();
                }
            });
            Toast toast = Toast.makeText( this, "Source Successfully Added", Toast.LENGTH_SHORT );
            toast.show();
            Intent intent = new Intent( this, MainActivity.class );
            startActivity( intent );
        } else {
            Toast toast = Toast.makeText( this, "Please enter name and url", Toast.LENGTH_SHORT );
            toast.show();
        }
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