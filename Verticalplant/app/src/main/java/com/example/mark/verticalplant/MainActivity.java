package com.example.mark.verticalplant;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    protected void toAddPlantActivity(View view){
        Intent toAddPlantActivity = new Intent(this, AddPlantActivity.class);
        startActivityForResult(toAddPlantActivity, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == AddPlantActivity.RESULT_ADD_PLANT){
                Uri uri = data.getParcelableExtra("imageUri");
                ImageView imgv1 = (ImageView) findViewById(R.id.imgV1);
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                    imgv1.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), uri.getPath(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Log.d(">>>> log ", e.toString());
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
