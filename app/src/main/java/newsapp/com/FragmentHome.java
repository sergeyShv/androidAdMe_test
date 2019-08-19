package newsapp.com;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentHome extends Fragment {
    ListNewsAdapter adapter;
    ListView listNews;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listNews = (ListView) getView().findViewById(R.id.listNews);
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("url", adapter.getItemByPosition(i).url);
                startActivity(intent);
            }
        });
        DownloadNews newsTask = new DownloadNews();
        newsTask.execute();
    }

    class DownloadNews extends AsyncTask<Void, Void, ArrayList<ItemModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ItemModel> doInBackground(Void... args) {
            ArrayList<ItemModel> itemsArrayList = new ArrayList<ItemModel>();
            Document doc;
            try {
                doc = Jsoup.connect("http://www.adme.ru/").get();
                Elements fetchedItem = doc.getElementsByAttributeValueMatching("class", "article-list-block js-article-list-item");
                itemsArrayList.clear();
                for (Element fetchedItems : fetchedItem) {
                    ItemModel mItemModel = new ItemModel();
                    //Parsing pictures
                    Elements pictures = fetchedItems.select(".al-pic");
                    String srcValue = pictures.attr("src");
                    //Parsing item urls
                    Elements urls = fetchedItems.select("a");
                    String urlValue = urls.attr("abs:href");
                    //Passing parsed items to model
                    mItemModel.setTitle(fetchedItems.select(".al-title").text());
                    mItemModel.setDescription(fetchedItems.select(".al-descr").text());
                    mItemModel.setUrl(urlValue);
                    mItemModel.setImage(srcValue);
//                mItemModel.setItemId(itemId);

                    //Adding model to array list
                    itemsArrayList.add(mItemModel);
                }

                //adding data to adapter
                //itemAdapter = new ItemAdapter(HomeActivity.this, itemsArrayList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return itemsArrayList;
        }


        @Override
        protected void onPostExecute(ArrayList<ItemModel> result) {
            super.onPostExecute(result);
            adapter = new ListNewsAdapter(getContext(), result);
            listNews.setAdapter(adapter);
            //show data in listview
            //homeListView.setAdapter(itemAdapter);
        }
    }


}
