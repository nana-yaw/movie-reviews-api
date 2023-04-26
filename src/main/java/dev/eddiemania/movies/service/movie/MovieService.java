package dev.eddiemania.movies.service.movie;

import dev.eddiemania.movies.entity.Movie;
import dev.eddiemania.movies.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(ObjectId movieId) {
        return movieRepository.findById(movieId);
    }
}
