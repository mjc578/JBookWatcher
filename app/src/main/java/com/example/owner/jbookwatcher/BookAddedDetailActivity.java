package com.example.owner.jbookwatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BookAddedDetailActivity extends AppCompatActivity {

    ImageView star1, star2, star3, star4, star5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.added_book_detail);

        star1 = findViewById(R.id.star_1); setStarListener(star1);
        star2 = findViewById(R.id.star_2); setStarListener(star2);
        star3 = findViewById(R.id.star_3); setStarListener(star3);
        star4 = findViewById(R.id.star_4); setStarListener(star4);
        star5 = findViewById(R.id.star_5); setStarListener(star5);

    }

    private void setStarListener(final ImageView star){
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), star.getDrawable().toString(), Toast.LENGTH_LONG).show();
                if(star.getDrawable().equals(getDrawable(R.drawable.star_outline))){
                    star.setImageResource(R.drawable.star_filled);
                }
            }
        });
    }


}
