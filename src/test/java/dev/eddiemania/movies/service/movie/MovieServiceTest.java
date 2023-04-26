package dev.eddiemania.movies.service.movie;

import dev.eddiemania.movies.entity.Movie;
import dev.eddiemania.movies.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    private ObjectId firstMovieId;

    @BeforeEach
    void setUp() {
        this.firstMovieId = new ObjectId();
        ObjectId secondMovieId = new ObjectId();

        Movie firstMovie = new Movie();
        firstMovie.setTitle("Lupin");
        firstMovie.setId(firstMovieId);

        Movie secondMovie = new Movie();
        secondMovie.setTitle("Deadpool");
        secondMovie.setId(secondMovieId);

        Mockito.when(movieRepository.findById(this.firstMovieId)).thenReturn(Optional.of(firstMovie));
        Mockito.when(movieRepository.findById(secondMovieId)).thenReturn(Optional.of(secondMovie));

        List<Movie> movies = new ArrayList<>();
        movies.add(firstMovie);
        movies.add(secondMovie);

        Mockito.when(movieRepository.findAll()).thenReturn(movies);
    }

    @Test
    void allMovies() {
        List<Movie> movies = movieService.allMovies();
        assertEquals(2, movies.size());
    }

    @Test
    void singleMovie() {
        String movieTitle = "Lupin";
        Optional<Movie> movieRecord = movieService.singleMovie(this.firstMovieId);
        movieRecord.ifPresent(movie -> assertEquals(movieTitle, movie.getTitle()));
    }
}