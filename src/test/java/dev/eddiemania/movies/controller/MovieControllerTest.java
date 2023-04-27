package dev.eddiemania.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.eddiemania.movies.entity.Movie;
import dev.eddiemania.movies.service.movie.MovieService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MovieControllerTest.class)
class MovieControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    MovieController movieController;

    @MockBean
   private MovieService movieService;

    @Test
    void getAllMovies() {
        Movie lupin = new Movie();
        ObjectId lupinId = new ObjectId();
        lupin.setId(lupinId);
        lupin.setTitle("Lupin");

        Movie deadPool = new Movie();
        ObjectId deadPoolId = new ObjectId();
        deadPool.setId(deadPoolId);
        deadPool.setTitle("Deadpool");

        List<Movie> series = new ArrayList<>();
        series.add(lupin);
        series.add(deadPool);

        when(movieService.allMovies()).thenReturn(series);
        List<Movie> cinema = movieService.allMovies();

        assertThat(Objects.requireNonNull(cinema)).hasSize(2);
        assertThat(Objects.requireNonNull(cinema.get(0).getTitle())).isEqualTo("Lupin");
        assertThat(Objects.requireNonNull(cinema.get(1).getTitle())).isEqualTo("Deadpool");
    }

    @Test
    void getSingleMovieById() throws Exception {
        Movie aladdin = Movie.builder()
                .id(new ObjectId())
                .title("Aladdin")
                .build();
        String id = aladdin.getId().toString();
        ObjectId aladdinId = aladdin.getId();

        given(movieService.singleMovie(aladdinId))
                .willReturn(Optional.of(aladdin));

        ResultActions response = mvc.perform(get("/api/v1/movies/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andDo(print());

    }
}