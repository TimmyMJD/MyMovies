package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.FavoriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewAddToFavorite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;

    private int id;
    private Movie movie;
    private FavoriteMovie favoriteMovie;

    private MainViewModel viewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavorite:
                Intent intentToFavorite = new Intent(this, FavoriteActivity.class);
                startActivity(intentToFavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseData);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavorite = findViewById(R.id.imageViewAddToFavorite);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        }else {
            finish();
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        movie = viewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOriginalTitle.setText(movie.getOriginalTitle());
        textViewOverview.setText(movie.getOverview());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(Double.toString(movie.getVoteAverage()));
        setFavoriteMovie();
    }

    public void onClickChangeFavorite(View view) {
        if(favoriteMovie == null) {
             viewModel.insertFavoriteMovies(new FavoriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favorite, Toast.LENGTH_SHORT).show();
        }else{
            viewModel.deleteFavoriteMovies(favoriteMovie);
            Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
        }
        setFavoriteMovie();
    }

    private void setFavoriteMovie() {
        favoriteMovie = viewModel.getFavoriteMovieById(id);
        if(favoriteMovie == null) {
            imageViewAddToFavorite.setImageResource(R.drawable.white_star);
        }else{
            imageViewAddToFavorite.setImageResource(R.drawable.yellow_star);
        }
    }
}