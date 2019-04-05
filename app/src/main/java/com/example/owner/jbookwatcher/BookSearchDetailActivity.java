package com.example.owner.jbookwatcher;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.owner.jbookwatcher.data.BookDbHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class BookSearchDetailActivity extends AppCompatActivity {

    public static final String BOOK_DETAIL_KEY = "book";
    ImageView ivBookCover;
    TextView tvBookTitle;
    TextView tvBookAuthor;
    TextView tvBookPages;
    TextView tvBookPubYear;
    TextView tvWeight;
    Button bAddToList;
    BookDbHelper dbHelper;
    Book b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivBookCover = findViewById(R.id.iv_book_detail);
        tvBookTitle = findViewById(R.id.tv_book_detail_title);
        tvBookAuthor = findViewById(R.id.tv_book_detail_author);
        tvBookPages = findViewById(R.id.tv_book_detail_pages);
        tvBookPubYear = findViewById(R.id.tv_book_detail_publish_year);
        tvWeight = findViewById(R.id.tv_book_detail_weight);
        bAddToList = findViewById(R.id.button_book_detail_add);

        b = (Book) getIntent().getSerializableExtra(BOOK_DETAIL_KEY);
        setBookDetails(b);

        //check if this book is a duplicate, set button/other view accordingly
        dbHelper = new BookDbHelper(this);

        setButtonListener();

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
                        tvBookPages.setText(getString(R.string.no_page_count_info));
                    }
                    if(response.has("weight")){
                        if(oneInFifty()){
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

    private boolean oneInFifty(){
        Random r = new Random();
        int low = 0;
        int high = 49;
        int result = r.nextInt(high-low) + low;

        return result == 10;
    }

    private void setButtonListener(){
        bAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.add_title)
                        .setSingleChoiceItems(new String[]{"Currently Reading", "Finished Reading", "To Read"}, 0, null)
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                                switch(selectedPosition) {
                                    case 0:
                                        b.setListIndicator(0);
                                        break;
                                    case 1:
                                        b.setListIndicator(1);
                                        break;
                                    case 2:
                                        b.setListIndicator(2);
                                }
                                if(!dbHelper.addBook(b)) {
                                    Toast.makeText(v.getContext(),"This book by " + b.getAuthor() + " is already in one of your lists", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    dbHelper.printDbToLog();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }
}
