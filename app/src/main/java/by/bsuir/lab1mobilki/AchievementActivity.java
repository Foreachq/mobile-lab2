package by.bsuir.lab1mobilki;

import android.os.Bundle;

import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AchievementActivity extends AppCompatActivity {
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Achievements");
    }
}