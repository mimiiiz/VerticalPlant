package com.example.mark.verticalplant;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    CardView cardView;
    ImageButton imgv1;
    ContentResolver cr;
    Bitmap bitmap;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Vertical Plant");
        setSupportActionBar(myToolbar);

        sharedPreferences = getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);

        int level = sharedPreferences.getInt("waterLevel", 0);
        String status = sharedPreferences.getString("waterStatus", "Please watering this plant");

        cardView = (CardView) findViewById(R.id.new_card_view);

        //set new Card visible if it exist
        if (sharedPreferences.getBoolean("newCard", false)){
            cardView.setVisibility(View.VISIBLE);
        }else {
            cardView.setVisibility(View.GONE);
        }

        //set water status
        TextView waterStatus = (TextView) findViewById(R.id.water_status);
        TextView waterPercent = (TextView) findViewById(R.id.water_percent);
        ProgressBar waterProgressBar = (ProgressBar) findViewById(R.id.water_progress);
        waterStatus.setText(status);
        waterPercent.setText(level + "%");
        waterProgressBar.setProgress(level);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == AddPlantActivity.RESULT_ADD_PLANT) {
                this.uri = data.getParcelableExtra("imageUri");
                imgv1 = (ImageButton) findViewById(R.id.imgV1);
                cardView = (CardView) findViewById(R.id.new_card_view);
                cardView.setVisibility(View.VISIBLE);
                cr = getContentResolver();
                try {
                    this.bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                    imgv1.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), uri.getPath(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(">>>> log ", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                addNewPlant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addNewPlant() {
        Intent toAddPlantActivity = new Intent(this, AddPlantActivity.class);
        startActivityForResult(toAddPlantActivity, 1);
    }

    public void waterPlant(View view) {
        startActivity(new Intent(this, WateringActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //set new Card visible if it exist
        if (sharedPreferences.getBoolean("newCard", false)){
            cardView = (CardView) findViewById(R.id.new_card_view);
            cardView.setVisibility(View.VISIBLE);
        }

        int level = sharedPreferences.getInt("waterLevel", 0);
        String status = sharedPreferences.getString("waterStatus",null);

        //set water status
        TextView waterStatus = (TextView) findViewById(R.id.water_status);
        TextView waterPercent = (TextView) findViewById(R.id.water_percent);
        ProgressBar waterProgressBar = (ProgressBar) findViewById(R.id.water_progress);
        waterStatus.setText(status);
        waterPercent.setText(level + "%");
        waterProgressBar.setProgress(level);


    }
}
