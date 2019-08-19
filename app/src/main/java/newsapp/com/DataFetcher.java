package newsapp.com;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DataFetcher extends AsyncTask<Void, Void, ArrayList<ItemModel>> {
    @Override
    protected ArrayList<ItemModel> doInBackground(Void... params) {
        ArrayList<ItemModel> itemsArrayList = new ArrayList<ItemModel>();
        Document doc;
        try{
            doc = Jsoup.connect("http://www.adme.ru/").get();
            Elements fetchedItem = doc.getElementsByAttributeValueMatching("class", "article-list-block js-article-list-item");
            itemsArrayList.clear();
            for (Element fetchedItems: fetchedItem){

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
        } catch (IOException e){
            e.printStackTrace();
        }
        return itemsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemModel> result) {
        //show data in listview
        //homeListView.setAdapter(itemAdapter);
    }
}