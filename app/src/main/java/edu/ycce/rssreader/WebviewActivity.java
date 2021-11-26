package edu.ycce.rssreader;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {

    public static String EXTRA_URL = "url";
    private WebView webView;
    public static String[] links;
    public int id;
    private String url;
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        id = (int)getIntent().getExtras().get(ID);
//        Bundle bundle = getIntent().getExtras();
//        if(bundle == null){
//            return;
//        }
//        Category category = (Category) bundle.get( "object_categories" ) ;


        webView = (WebView)findViewById(R.id.webView);
//        url = category.getRssLink();

        url = links[id];

        webView.loadUrl(url);
    }
}
