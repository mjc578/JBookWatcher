package com.example.owner.jbookwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK_DETAIL_KEY = "book";
    ImageView ivBookCover;
    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookPages;
    TextView tvBookPubYear;
    TextView tvAlreadyInList;
    TextView tvWeight;
    Button bAddToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivBookCover = findViewById(R.id.iv_book_detail);
        tvBookTitle = findViewById(R.id.tv_book_detail_title);
        tvBookAuthor = findViewById(R.id.tv_book_detail_author);
        tvBookPages = findViewById(R.id.tv_book_detail_pages);
        tvBookPubYear = findViewById(R.id.tv_book_detail_publish_year);
        tvAlreadyInList = findViewById(R.id.tv_book_detail_already_in_list);
        tvWeight = findViewById(R.id.tv_book_detail_weight);
        bAddToList = findViewById(R.id.button_book_detail_add);

        Book b = (Book) getIntent().getSerializableExtra(BOOK_DETAIL_KEY);
        setBookDetails(b);
    }

    public void setBookDetails(Book b){
        //set information we already have
        Glide.with(this).load(b.getLargeCoverUrl()).error(R.drawable.no_cover_avail).into(ivBookCover);
        tvBookTitle.setText(b.getBookTitle());
        //TODO: if author not present, set "No author information"
        tvBookAuthor.setText(b.getAuthor());

        //get other info from JSON response from api
        BookClient bc = new BookClient();
        bc.getBookDetails(b.getOLID(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    if(response.has("publish_date")){
                        String publishDate = "Published on " + response.getString("publish_date");
                        tvBookPubYear.setText(publishDate);
                    } else {
                        tvBookPubYear.setText(getString(R.string.no_pub_year_info));
                    }
                    if(response.has("number_of_pages")){
                        String numPages = response.getString("number_of_pages") + " pages";
                        tvBookPages.setText(numPages);
                    } else {
                        tvBookPages.setText(getString(R.string.no_page_couunt_info));
                    }
                    if(response.has("weight")){
                        if(oneInTwenty()){
                            String weight = "...In case you were wondering, this book weighs " +
                                    response.getString("weight") + "!";
                            tvWeight.setText(weight);
                            tvWeight.setVisibility(View.VISIBLE);
                        }
                    }

                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean oneInTwenty(){
        Random r = new Random();
        int low = 0;
        int high = 49;
        int result = r.nextInt(high-low) + low;

        return result == 10;
    }

    //TODO: hide button to add tp list if book by author is already in list despite edition
    private boolean isBookInDB(Book b){
        return false;
    }
}
