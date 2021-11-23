package edu.ycce.rssreader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.api.ApiService;
import retrofit.model.Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit.model.Category;

public class SourceListFragment extends ListFragment {

    static interface Listener {
        void sourceClicked(long id);
    };

    private Listener listener;

    public SourceListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        SQLiteHelper sQLiteHelper;
        final List<Category> feedSourceModel = new ArrayList<>();
        Bundle arg = this.getArguments();
        int newId = 0;
        if(arg != null) {
            newId = arg.getInt( "newsId" );
        }
//        else {
//            newId = 1;
//        }
  //      sQLiteHelper = new SQLiteHelper(getLayoutInflater().getContext());
  //      feedSourceModel = sQLiteHelper.getAllRecords(category); // chọn ra tất cả chủ đề thuộc loại báo đó
      //   Lấy API về

        ApiService.apiService.getCategories(newId).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response){
                Toast.makeText(SourceListFragment.this.getContext(), "Call API success", Toast.LENGTH_SHORT).show();
                feedSourceModel.addAll(response.body());
                String[] name = new String[feedSourceModel.size()];

                String[] link = new String[feedSourceModel.size()];
                for (int i = 0; i< feedSourceModel.size(); i++) { // lấy ra all các link rss của các chủ đề tương ứng
                    name[i] = feedSourceModel.get(i).getCategoriesTitle();
                    link[i] = feedSourceModel.get(i).getRssLink();
                }

                FeedListFragment.urls = link;

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, name);
                setListAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable){
                Toast.makeText( SourceListFragment.this.getContext(), "Call API Error!", Toast.LENGTH_SHORT ).show();

            }
        });





//        String[] name = new String[feedSourceModel.size()];
//
//        String[] link = new String[feedSourceModel.size()];
//        for (int i = 0; i< feedSourceModel.size(); i++) { // lấy ra all các link rss của các chủ đề tương ứng
//            name[i] = feedSourceModel.get(i).getCategoriesTitle();
//            link[i] = feedSourceModel.get(i).getRssLink();
//        }
//
//        FeedListFragment.urls = link;
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getLayoutInflater().getContext(), android.R.layout.simple_list_item_1, name);
//        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (Listener)context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if(listener!=null)
            listener.sourceClicked(id);
    }


}
