package com.example.owner.jbookwatcher.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.owner.jbookwatcher.Book;
import com.example.owner.jbookwatcher.BookClient;
import com.example.owner.jbookwatcher.BookDetailActivity;
import com.example.owner.jbookwatcher.R;
import com.example.owner.jbookwatcher.adapters.BookSearchAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String BOOK_DETAIL_KEY = "book";
    private ListView lvBooks;
    private BookSearchAdapter bookAdapter;
    private BookClient client;
    private ProgressBar progressBar;
    private TextView tvStartSearch;
    private TextView tvNoSearchRes;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View sfView = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        tvStartSearch = sfView.findViewById(R.id.search_to_get_started);
        tvNoSearchRes = sfView.findViewById(R.id.no_search_results);
        progressBar = sfView.findViewById(R.id.progress);
        lvBooks = sfView.findViewById(R.id.book_search_list);
        ArrayList<Book> aBooks = new ArrayList<>();
        bookAdapter = new BookSearchAdapter(getContext(), aBooks);
        lvBooks.setAdapter(bookAdapter);

        setListListener();

        return sfView;
    }

    private void fetchBooks(String query) {
        progressBar.setVisibility(View.VISIBLE);
        lvBooks.setVisibility(View.GONE);
        tvStartSearch.setVisibility(View.GONE);
        tvNoSearchRes.setVisibility(View.GONE);
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                progressBar.setVisibility(View.GONE);
                lvBooks.setVisibility(View.VISIBLE);
                try {
                    JSONArray docs;
                    if(response != null) {
                        // Get the docs json array
                        docs = response.getJSONArray("docs");
                        // Parse json array into array of model objects
                        final ArrayList<Book> books = Book.fromJson(docs);
                        //no search results
                        if(books.isEmpty()){
                            tvNoSearchRes.setVisibility(View.VISIBLE);
                        }
                        // Remove all books from the adapter
                        bookAdapter.clear();
                        // Load model objects into the adapter
                        for (Book book : books) {
                            bookAdapter.add(book); // add book through the adapter
                        }
                        bookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater mi) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mi.inflate(R.menu.book_search_bar, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void setListListener(){
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity(), BookDetailActivity.class);
                i.putExtra(BOOK_DETAIL_KEY, bookAdapter.getItem(position));
                startActivity(i);
            }
        });
    }
}
