import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService underTest;

    @Captor
    private ArgumentCaptor<Movie> movieCaptor;

    @Captor
    private ArgumentCaptor<String> movieTitleCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll_noMoviesInDb() {
        when(movieRepository.findAll()).thenReturn(new ArrayList<>());

        String result = underTest.findAll();

        assertEquals("There are no movies at the moment", result);
    }

    @Test
    public void testFindAll_twoMoviesExistInDb() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setCategory("Category.ACTION");
        movie.setLength(123);
        movie.setTitle("test-movie");
        movie.setShows(new ArrayList<>());
        movie.setActors(new HashSet<>());

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setCategory("Category.COMEDY");
        movie2.setLength(90);
        movie2.setTitle("unkown movie");
        movie2.setShows(new ArrayList<>());
        movie2.setActors(new HashSet<>());

        List<Movie> movies = List.of(movie, movie2);

        when(movieRepository.findAll()).thenReturn(movies);

        String result = underTest.findAll();
        String expectedResult = movie.getTitle() + " (" + movie.getCategory() + ", " + movie.getLength() + " minutes)\n"
                + movie2.getTitle() + " (" + movie2.getCategory() + ", " + movie2.getLength() + " minutes)\n";

        assertEquals(expectedResult, result);
    }

    @Test
    public void testUpdate_movieNameDoesNotExist() {
        String movieName = "testmovie";
        String movieNewCategory = "Category.ACTION";
        int movieNewLength = 10;

        when(movieRepository.findByTitleIgnoreCase(eq(movieName))).thenReturn(Optional.empty());

        boolean result = underTest.update(movieName, movieNewCategory, movieNewLength);

        assertFalse(result);
    }

    @Test
    public void testUpdate_movieExists() {
        String movieName = "testmovie";
        String movieNewCategory = "Category.ACTION";
        int movieNewLength = 10;

        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle(movieName);
        movie.setCategory("Category.SCI_FI");
        movie.setLength(20);
        movie.setShows(new ArrayList<>());
        movie.setActors(new HashSet<>());

        Mockito.when(movieRepository.findByTitleIgnoreCase(eq(movieName)))
                .thenReturn(Optional.of(movie));

        boolean result = underTest.update(movieName, movieNewCategory, movieNewLength);

        Mockito.verify(movieRepository).save(movieCaptor.capture());
        Movie passedMovie = movieCaptor.getValue();

        assertTrue(result);
        assertEquals(movieNewCategory, passedMovie.getCategory());
        assertEquals(movieNewLength, passedMovie.getLength());
    }

    @Test
    public void testDeleteByTitle_noMovieWithThisTitleInDb() {
        String movieTitle = "notExisting movie name";
        int expectedResult = 0;

        when(movieRepository.deleteByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(new ArrayList<>());

        int result = underTest.deleteByTitle(movieTitle);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteByTitle_twoMoviesWithThisTitleInDb() {
        String movieTitle = "test movie";
        int expectedResult = 2;

        Movie movie = new Movie();
        Movie movie2 = new Movie();

        when(movieRepository.deleteByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(List.of(movie, movie2));

        int result = underTest.deleteByTitle(movieTitle);

        assertEquals(expectedResult, result);
    }
}
