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
      //  setContentView(R.layout.news_fragment);
       // tableLayout = findViewById(R.id.achievementTable);
     //   findAllUsers();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Achievements");

    }


//
//    public List<User> findAllNews() {
//        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS news (title TEXT,  created_at TEXT)");
//
//        @SuppressLint("Recycle")
//        Cursor query = db.rawQuery("SELECT * FROM NEWS limit 10;", null);
//        if (query.getCount() > 0) {
//            query.moveToFirst();
//            do {
//                TableRow tableRow = new TableRow(this);
//                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                        TableRow.LayoutParams.WRAP_CONTENT));
//                TextView textView = new TextView(this);
//                textView.setText(query.getString(0));
//                TextView textView1 = new TextView(this);
//                textView.setGravity(Gravity.CENTER);
//                textView1.setText("Score: " + query.getString(1));
//                TextView textView2 = new TextView(this);
//                textView2.setText(query.getString(2));
//                if (tableRow != null) {
//                    tableRow.addView(textView, 0);
//                    tableRow.addView(textView1, 1);
//                    tableRow.addView(textView2, 2);
//                    tableLayout.addView(tableRow, 0);
//                }
//            } while (query.moveToNext());
//            query.close();
//        }
//
//
//        return null;
//    }
}