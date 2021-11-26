package edu.ycce.rssreader;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class FeedListFragment extends ListFragment{

    public static String[] urls;
    SimpleDateFormat dt;
//    RegEx regEx;
    public static String[] img;




    static interface Listener {
        void itemClicked(long id);
    }

    ;

    private Listener listener;

    public FeedListFragment(){
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        Bundle arg = getArguments();
        String url;
        if (arg != null) {
//            Category category = (Category) arg.get( "object_categories" ) ;
 //           long id = arg.getLong( String.valueOf( getId() ) );
//            url = urls[(int) id];
//            url = category.getRssLink();
                url = arg.getString( "rssLink" );
        } else {
            url = "https://vnexpress.net/rss/tin-moi-nhat.rss";
        }
        Parser parser = new Parser();
        parser.execute( url ); //convert XML thành JSON
        parser.onFinish( new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(final ArrayList<Article> list){ //lấy ra các bài báo trong 1 chủ đề(item)
                String[] titles = new String[list.size()];
                String[] link = new String[list.size()];
                String[] pubDate = new String[list.size()];
                String[] description = new String[list.size()];
//                regEx = new RegEx();
                for (int i = 0; i < list.size(); i++) {
                    titles[i] = list.get( i ).getTitle();
                    link[i] = list.get( i ).getLink();
                    pubDate[i] = list.get( i ).getPubDate().toString();
                    description[i] = list.get( i ).getDescription();
                }

                ArrayList<HashMap<String, String>> lists = new ArrayList<>();// List chứa các HanhMap(key, value)
                HashMap<String, String> item;
                for (int i = 0; i < list.size(); i++) {
                    item = new HashMap<String, String>();
                    item.put( "title", list.get( i ).getTitle() );
                    String dd = new SimpleDateFormat( "MMM d yyyy, hh:mm" ).format( list.get( i ).getPubDate() );
                    item.put( "date", dd );
                    String str = RegEx.replaceMatches(list.get( i ).getDescription()).substring( 5 );
                    item.put( "descriptions", str );
                    String imgUrl = RegEx.findImage(list.get( i ).getDescription());
                    item.put("imgUrl", imgUrl);
                    lists.add( item );
                }

                CustomSimpleAdapter adapter = new CustomSimpleAdapter( getLayoutInflater().getContext(),
                                                lists,
                                                R.layout.listview_layout,
                                                new String[]{"title", "date", "descriptions", "imgUrl"},
                                                new int[]{R.id.text_title, R.id.text_date, R.id.text_description, R.id.image} );
                setListAdapter( adapter );
                WebviewActivity.links = link;
            }

            @Override
            public void onError(){
                Log.v( "RSSLog", "something went wrong" );
            }
        } );

        return super.onCreateView( inflater, container, savedInstanceState );
    }

    @Override
    public void onAttach(Context context){
        super.onAttach( context );
        this.listener = (Listener) context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){
        if (listener != null) listener.itemClicked( id );
    }

}
