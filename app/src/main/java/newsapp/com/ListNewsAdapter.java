package newsapp.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


/**
 * Created by SHAJIB-PC on 10/23/2017.
 */

class ListNewsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemModel> data;

    public ListNewsAdapter(Context a, ArrayList<ItemModel> d) {
        context = a;
        data = d;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    public ItemModel getItemByPosition(int position){
        return data.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.list_row, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.title.setId(position);
        holder.sdetails.setId(position);


            holder.title.setText(data.get(position).title);
            holder.sdetails.setText(data.get(position).description);

            Picasso.with(context)
                    .load(data.get(position).image)
                    .resize(300, 200)
                    .into(holder.galleryImage);


        return convertView;
    }
}

class ListNewsViewHolder {
    ImageView galleryImage;
    TextView title, sdetails;
}