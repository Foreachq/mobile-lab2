package by.bsuir.lab1mobilki;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;



import java.util.Objects;

public class AboutDeveloperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Developer information");
    }






}