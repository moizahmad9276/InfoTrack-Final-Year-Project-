package com.example.moiz.indoorts;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

import static com.example.moiz.indoorts.FindLocationActivity.IMAGE_RES_ID;


public class SourceMapActivity extends AppCompatActivity {

    public PhotoView mapView;
   // private Button next;
    public static final String IMAGE_ID1 = "IMAGE_ID1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_map);

        mapView = (PhotoView) findViewById(R.id.mapview);
       // next = (Button) findViewById(R.id.btnnext);

        mapView.setImageResource(getIntent().getIntExtra(IMAGE_RES_ID, 0));

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), NextMapActivity.class);
//                intent.putExtra(IMAGE_ID1, R.drawable.five);
//                startActivity(intent);
//            }
//        });


    }
}
