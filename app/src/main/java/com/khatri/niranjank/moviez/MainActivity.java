package com.khatri.niranjank.moviez;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //TextView tv,tv2;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        tv = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        */
        listView = (ListView) findViewById(R.id.listview1);

        listView.setOnItemClickListener(this);
        new CheckConnectionStatus().execute("");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this, MovieDetailActivity.class);
        i.putExtra("MOVIE_DETAILS", (MovieDetails)parent.getItemAtPosition(position));
        /*here i am passing details from one activity to another so we must implement serizable. */
        startActivity(i);
    }


    class CheckConnectionStatus extends AsyncTask<String, Void, String >{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // tv2.setText("The Response code is:");
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;

            try {
                url = new URL("https://api.themoviedb.org/3/movie/popular?api_key=8476a7ab80ad76f0936744df0430e67c&langauge=eb-US&page=1");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

                // wer are writing code for the post or request part
                /*
                httpconn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username","admin").
                        appendQueryParameter("password","admin");
                OutputStream os = httpconn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                bw.write(builder.build().getEncodedQuery()); // this build will construct URI with current attributes, while getencodedquer deals
                 //about the query seperator ?
                bw.flush();
                bw.close();
                os.close();
                httpconn.connect();
                */

                // now we write code for the response part
                /* for jason part we just use the code of response part */
                InputStream is = httpconn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = br.readLine();  // here we read the firs response line
                br.close();

                return s;// here we return the value to the method ..
            } catch (IOException e) {
                Log.e("Error:",e.getMessage(),e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject jobj = null;
            try {
                jobj = new JSONObject(s);

                /*
            Map<String, Integer> companiesMap = new HashMap<>();
            */
            /*we use this map to store the Json array values. key = name, value = id*/

                //tv.setText(s);
                //   tv.setText(jobj.getString("original_title"));

            /*to read the array we create JSONArray object */

                // we create ArrayList of MoveDetails
                ArrayList<MovieDetails> movieList = new ArrayList<>();

                JSONArray jsonArray = jobj.getJSONArray("results");

                for (int i=0; i< jsonArray.length();i++){
                    JSONObject obj = jsonArray.getJSONObject(i);

                    MovieDetails movieDetails = new MovieDetails();
                    movieDetails.setOriginal_title(obj.getString("original_title"));
                    movieDetails.setVote_average(obj.getDouble("vote_average"));
                    movieDetails.setOverview(obj.getString("overview"));
                    movieDetails.setPoster_path(obj.getString("poster_path"));
                    movieDetails.setRelease_date(obj.getString("release_date"));

                    // we add the movieDetails object in movieList
                    movieList.add(movieDetails);
                    //companiesMap.put(obj.getString("name"),obj.getInt("id"));
                }

                // we check from the text view
            /*
            tv.setText(String.valueOf(companiesMap.get("Taurus Film")));
            */
               // Log.i("List:", movieList.get(2).getOriginal_title());

                MovieArrayAdapter movieArrayAdapter = new MovieArrayAdapter(MainActivity.this,R.layout.movie_list, movieList);
                listView.setAdapter(movieArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
