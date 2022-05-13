package by.bsuir.lab1mobilki;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog = null;
    private TableLayout tableLayout = null;
    Context context;
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                selectedFragment = new HomeFragment();
                tableLayout.removeAllViews();
                findAllNews();
                break;
            case R.id.explore:
                selectedFragment = new ExploreFragment();
                tableLayout.removeAllViews();
                break;
            case R.id.profile:
                selectedFragment = new ProfileFragment();
                tableLayout.removeAllViews();
                break;
        }

        assert selectedFragment != null;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, selectedFragment)
                .commit();
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        Toast toast = Toast.makeText(MainActivity.this,
                "internet", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS news (title TEXT,  created_at TEXT, UNIQUE (title))");

        tableLayout = findViewById(R.id.newsTable);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, new HomeFragment()).commit();

        new ProcessInBackground().execute();
    }

    public void onMyButtonClick(View view) {
        EditText myEditText = (EditText) findViewById(R.id.editTextTextPersonName3);
        String text = myEditText.getText().toString();
        searchNews(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_about_dev) {
            Intent intent = new Intent(this, AboutDeveloperActivity.class);
            System.out.println(1);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public void findAllNews() {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS news (title TEXT,  created_at TEXT, UNIQUE (title))");
        Cursor query = db.rawQuery("SELECT * FROM news limit 30;", null);
        if (query.getCount() > 0) {
            query.moveToFirst();
            do {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView textView = new TextView(this);
                textView.setText(query.getString(0));
                TextView textView1 = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView1.setText(query.getString(1));

                tableRow.addView(textView, 0);
                tableRow.addView(textView1, 1);
                runOnUiThread(() -> {
                    tableLayout.addView(tableRow, 0);
                    // Stuff that updates the UI
                });

            } while (query.moveToNext());
            query.close();
        }
    }

    public void searchNews(String word) {
        System.out.println(word);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS news (title TEXT,  created_at TEXT, UNIQUE (title))");
        Cursor query = db.rawQuery("SELECT * FROM news WHERE title LIKE ?  limit 30;", new String[]{"%" + word + "%"});
        if (query.getCount() > 0) {
            query.moveToFirst();
            do {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                TextView textView = new TextView(this);
                textView.setText(query.getString(0));
                TextView textView1 = new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView1.setText(query.getString(1));

                tableRow.addView(textView, 0);
                tableRow.addView(textView1, 1);
                runOnUiThread(() -> {
                    tableLayout.addView(tableRow, 0);
                    // Stuff that updates the UI
                });

            } while (query.moveToNext());
            query.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save(String title, String createdAt) {

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        try {
            db.execSQL("INSERT INTO news (title,created_at) " +
                    "VALUES (?,?);", new Object[]{title.substring(0, 25) + "...", createdAt});
        } catch (SQLiteConstraintException ignored) {
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            try {
                getInputStream(new URL("https://moxie.foxnews.com/feedburner/politics.xml"));

            }catch (Exception e){
                progressDialog.setMessage("Connection");
                progressDialog.show();
            }
            progressDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                // rss feed site here
                URL url = new URL("https://moxie.foxnews.com/feedburner/politics.xml");

                // factory new instance
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                InputStream inputStream = getInputStream(url);
                if (inputStream != null) {

                    xpp.setInput(inputStream, "UTF_8");

                    boolean insideItem = false;

                    int eventType = xpp.getEventType();
                    String title = null;
                    String pubDate = null;
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {

                            if (xpp.getName().equalsIgnoreCase("item")) {
                                insideItem = true;
                            } else if (xpp.getName().equalsIgnoreCase("title")) {
                                if (insideItem) {
                                    title = xpp.nextText();

                                }
                            } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                                if (insideItem) {
                                    pubDate = xpp.nextText();
                                }
                            }
                            if (title != null && pubDate != null) {
                                save(title, pubDate);
                            }
                        }

                        eventType = xpp.next();
                    }
                }
            } catch (XmlPullParserException | IOException e) {
                exception = e;
            }
            findAllNews();

            return null;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}

