package com.educharge.educharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView =(ImageView)  findViewById(R.id.imageView2);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        imageView.startAnimation(animation);

        /* try{
            Thread.sleep(3000);
        }catch(Exception e){
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this,"hello", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,LoginActivity.class));
        */

        imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));
           }
       });
    }

}
