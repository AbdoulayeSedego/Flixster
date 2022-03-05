package com.example.flixster

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.youtube.player.internal.r
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {

//    private val ivPoster = findViewById<ImageView>(R.id.ivPoster)
//    private val tvTitle = findViewById<TextView>(R.id.tvTitle)
//    private val tvOverview = findViewById<TextView>(R.id.tvOverview)

    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)
        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onFailure( statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?)
            {
                Log.e(TAG, "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON $json")
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "onSuccess: Movie list $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encounter exception $e")
                }

            }

        })

    }
//    Implementing Shared element animation

//    ivPoster.setOnclickListener {
//        val share = Intent(this, DetailActivity::class.java)
//        val options = ActivityOptions.makeSceneTransitionAnimation(this,
//            Pair.create(ivPoster, "profile"),
//            Pair.create(tvTitle, "TransitionTitle"),
//            Pair.create(tvOverview, "TransitionOverview"),
//        )
//    }

}