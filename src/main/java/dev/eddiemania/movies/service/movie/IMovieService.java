package dev.eddiemania.movies.service.movie;

import dev.eddiemania.movies.entity.Movie;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IMovieService {
    public List<Movie> allMovies();
    public Optional<Movie> singleMovie(ObjectId movieId);
}
