package edu.ycce.rssreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomSimpleAdapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater = null;

    public CustomSimpleAdapter(Context context,
                               List<? extends Map<String, ?>> data, int resource, String[] from,
                               int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_layout, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
        TextView txtDescription = vi.findViewById(R.id.text_description);
        txtDescription.setText((String) data.get("descriptions"));


        TextView txtTitle = vi.findViewById(R.id.text_title);
        txtTitle.setText((String) data.get("title"));

        TextView textPubDate = vi.findViewById(R.id.text_date);
        textPubDate.setText((String) data.get("date"));

        new DownloadTask((ImageView) vi.findViewById(R.id.image))
                .execute((String) data.get("imgUrl"));

        return vi;
    }
}
