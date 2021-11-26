package edu.ycce.rssreader;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit.api.ApiService;
import retrofit.model.Category;
import retrofit.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FeedListFragment.Listener, SourceListFragment.Listener, MenuAdapter.MenuItemClickListener {
    private MenuAdapter menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        String url = "https://vnexpress.net/rss/tin-moi-nhat.rss";
        if (getIntent() != null && getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString("rssLink");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        Bundle bundle = new Bundle();
        bundle.putString( "rssLink", url );

        Fragment fragment = new FeedListFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add( R.id.content_frame, fragment );
        ft.commit();
        getNews();
    }


    private void setNavigationDrawerMenu(List<News> newsList){
        newsList.get( 0 ).setSelected( true ); // auto select first item
        menuAdapter = new MenuAdapter( newsList, this );
        RecyclerView navMenuDrawer = findViewById( R.id.main_nav_menu_recyclerview );
        navMenuDrawer.setAdapter( menuAdapter );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.isAutoMeasureEnabled();
        navMenuDrawer.setLayoutManager( new LinearLayoutManager( this ) );
        navMenuDrawer.setHasFixedSize( false );
        navMenuDrawer.setAdapter( menuAdapter );
    }

    // gọi API
    private void getNews(){
        ApiService.apiService.getNews().enqueue( new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response){
                List<News> body1 = new ArrayList<>();
                body1.addAll( response.body() );


                setNavigationDrawerMenu( body1 );
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable throwable){
                Toast.makeText( MainActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.nav_add_news:
                Intent intent = new Intent( this, AddNewsActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent );
                return true;
            case R.id.nav_add_categories:
                intent = new Intent( this, AddCategoriesActivity.class );
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity( intent );
                return true;
            default:
                return super.onOptionsItemSelected( item );

        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        Bundle bundle = new Bundle();

        switch (id) {
            //               case R.id.nav_all:
//            case R.id.nav_business:
//                bundle.putString("category", "Business");
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_entertainment:
//                bundle.putString("category", "Entertainment");
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_sport:
//                bundle.putString("category", "Sport");
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_technology:
//                bundle.putString("category", "Technology");
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_vn_express:
//         //       bundle.putString("category", "VNExpress");
//                bundle.putInt("newsId", 1);
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_tuoi_tre_online:
//         //       bundle.putString("category", "Tuổi trẻ Online");
//                bundle.putInt("newsId", 2 );
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_other:
//     //           bundle.putString("category", "Other");
//                bundle.putInt("newsId", 3 );
//
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
//            case R.id.nav_about:
//                fragment = new AboutFragment();
//                break;
            case R.id.nav_add_news:
                intent = new Intent( this, AddNewsActivity.class );
                break;
            case R.id.nav_add_categories:
                intent = new Intent( this, AddCategoriesActivity.class );
                break;
//            case R.id.nav_sources:
//                bundle.putString("category", "");
//                fragment = new SourceListFragment();
//                fragment.setArguments(bundle);
//                break;
            default:
                fragment = new FeedListFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace( R.id.content_frame, fragment );
            ft.commit();
        } else {
            startActivity( intent );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    @Override
    public void itemClicked(long id){
        Intent intent = new Intent( this, WebviewActivity.class );
        intent.putExtra( WebviewActivity.ID, (int) id );
        startActivity( intent );
    }

    @Override
    public void sourceClicked(long id){
        Bundle bundle = new Bundle();
        bundle.putLong( "id", (int) id );
        Fragment fragment = new FeedListFragment();
        fragment.setArguments( bundle );
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.content_frame, fragment );
        ft.commit();
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMenuItemClick(int newsId){
        if (newsId > 0) {
//            Fragment fragment = null;
//            Intent intent = null;
//            Bundle bundle = new Bundle();

//            bundle.putInt( "newsId", newsId );
//            fragment = new SourceListFragment();

//            fragment.setArguments( bundle );

            Intent intent = new Intent();
            intent = new Intent( this, CategoryListActivity.class );
            intent.putExtra( "newsId", newsId );
            startActivity( intent );

//
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace( R.id.content_frame, fragment );
//            ft.commit();

            DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
            drawer.closeDrawer( GravityCompat.START );
        }
//        if (newsId == 0) {
//            Fragment fragment = null;
//            Intent intent = null;
//            Bundle bundle = new Bundle();
//
//            bundle.putInt("newsId", newsId);
//            fragment = new SourceListFragment();
//            fragment.setArguments(bundle);
//
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, fragment);
//            ft.commit();
//            Intent intent = new Intent( this, AddNewsActivity.class );
//            startActivity( intent );
//
//            DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
//            drawer.closeDrawer( GravityCompat.START );
//        }
//        if (newsId == -1) {
//            Intent intent = new Intent( this, AddCategoriesActivity.class );
//            startActivity( intent );
//
//            DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
//            drawer.closeDrawer( GravityCompat.START );
//        }
    }

    @Override
    public void onMenuItemLongClick(int newsId){
        showDialogConfirm( newsId );
    }

    private void showDialogConfirm(int newsId){
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        ViewGroup viewGroup = findViewById( android.R.id.content );
        View dialogView = LayoutInflater.from( getApplicationContext() ).inflate( R.layout.dialog_confirm_delete, viewGroup, false );
        TextView tvCancel = dialogView.findViewById( R.id.tvCancel );
        TextView tvAccept = dialogView.findViewById( R.id.tvAccept );

        builder.setView( dialogView );
        AlertDialog alertDialog = builder.create();
        tvCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                alertDialog.dismiss();
            }
        } );

        tvAccept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view){
                handleDeleteNews( newsId );
                alertDialog.dismiss();
            }
        } );
        alertDialog.show();
    }

    private void handleDeleteNews(int newsId){
        ApiService.apiService.deleteNews( newsId ).enqueue( new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response){
                menuAdapter.removeItemWidthId( newsId );
                Toast.makeText( MainActivity.this, "Call API Success!", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t){
                Toast.makeText( MainActivity.this, "Call API Error!", Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}

