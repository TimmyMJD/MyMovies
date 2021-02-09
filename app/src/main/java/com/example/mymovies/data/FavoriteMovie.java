package com.example.mymovies.data;

import androidx.room.Entity;

@Entity(tableName = "favorite_movies")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backdropPath, double voteAverage, String releaseDate) {
        super(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
    }
}
