package com.example.moiz.indoorts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

import static com.example.moiz.indoorts.SourceMapActivity.IMAGE_ID1;

public class NextMapActivity extends AppCompatActivity {

    public PhotoView nextmapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_next_map);

        nextmapView = (PhotoView) findViewById(R.id.nextmapview);
        nextmapView.setImageResource(getIntent().getIntExtra(IMAGE_ID1, 0));
    }
}
