import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.domain.entity.Room;
import com.epam.training.ticketservice.domain.entity.Show;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ShowRepository;
import com.epam.training.ticketservice.service.ShowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ShowService underTest;

    @Captor
    private ArgumentCaptor<Show> showArgumentCaptor;

    @Captor
    private ArgumentCaptor<Room> roomArgumentCaptor;

    @Captor
    private ArgumentCaptor<Movie> movieArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShow_withIncorrectDate() {
        String expectedResult = "Nem megfelelo datum formatum! (pl.: 2011-01-01 14:44)";

        String result = underTest.createShow("testMovie", "Blue room", "01/02/1990");

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateShow_withIncorrectMovieTitle() {
        String expectedResult = "Room name or movie title is not correct!";
        String movieTitle = "testMovie";
        String roomName = "testRoom";
        String creationDate = "2022-11-26 08:30";

        Room room = new Room();
        room.setName(roomName);

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.empty());

        String result = underTest.createShow(movieTitle, roomName, creationDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateShow_withIncorrectRoomName() {
        String expectedResult = "Room name or movie title is not correct!";
        String movieTitle = "testMovie";
        String roomName = "testRoom";
        String creationDate = "2022-11-26 08:30";

        Movie movie = new Movie();
        movie.setTitle(movieTitle);

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.empty());
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));

        String result = underTest.createShow(movieTitle, roomName, creationDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateShow_withOverlappingMovieDate() throws ParseException {
        String expectedResult = "There is an overlapping screening";
        String movieTitle = "testMovie";
        String roomName = "testRoom";
        String creationDate = "2022-11-26 08:30";

        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setLength(60);

        Room room = new Room();
        room.setName(roomName);

        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(creationDate);
        } catch (ParseException e) {
             throw new ParseException("Sikertelen datum parse", 0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movieStartDate);
        calendar.add(calendar.MINUTE, -10);
        Date movieStartDateWithBreak = calendar.getTime();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDate), eq(room)))
                .thenReturn(List.of(new Show()));
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDateWithBreak), eq(room)))
                .thenReturn(List.of());

        String result = underTest.createShow(movieTitle, roomName, creationDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateShow_withOverlappingBreakPeriod() throws ParseException {
        String expectedResult = "This would start in the break period after another screening in this room";
        String movieTitle = "testMovie";
        String roomName = "testRoom";
        String creationDate = "2022-11-26 08:30";

        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setLength(60);

        Room room = new Room();
        room.setName(roomName);

        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(creationDate);
        } catch (ParseException e) {
            throw new ParseException("Sikertelen datum parse", 0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movieStartDate);
        calendar.add(calendar.MINUTE, -10);
        Date movieStartDateWithBreak = calendar.getTime();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDate), eq(room)))
                .thenReturn(List.of());
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDateWithBreak), eq(room)))
                .thenReturn(List.of(new Show()));

        String result = underTest.createShow(movieTitle, roomName, creationDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testCreateShow_withNoOverlappingShows() throws ParseException {
        String expectedResult = "Screening creation is successful";
        String movieTitle = "testMovie";
        String roomName = "testRoom";
        String creationDate = "2022-11-26 08:30";

        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setLength(60);

        Room room = new Room();
        room.setName(roomName);

        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(creationDate);
        } catch (ParseException e) {
            throw new ParseException("Sikertelen datum parse", 0);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(movieStartDate);
        calendar.add(calendar.MINUTE, -10);
        Date movieStartDateWithBreak = calendar.getTime();

        calendar.add(Calendar.MINUTE, movie.getLength() + 10);
        Date movieEndDate = calendar.getTime();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDate), eq(room)))
                .thenReturn(List.of());
        when(showRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanEqualAndRoomEquals(eq(movieStartDate), eq(movieStartDateWithBreak), eq(room)))
                .thenReturn(List.of());

        String result = underTest.createShow(movieTitle, roomName, creationDate);
        verify(showRepository).save(showArgumentCaptor.capture());
        Show showToSave = showArgumentCaptor.getValue();

        assertEquals(expectedResult, result);
        assertEquals(movie, showToSave.getMovie());
        assertEquals(room, showToSave.getRoom());
        assertEquals(movieStartDate, showToSave.getStartDate());
        assertEquals(movieEndDate, showToSave.getEndDate());
    }

    @Test
    public void testDeleteShow_withIncorrectDate() {
        String expectedResult = "Nem megfelelo datum formatum! (pl.: 2011-01-01 14:44)";
        String movieTitle = "test-movie";
        String roomName = "test-room";
        String movieDate = "02/01/1992 18:22";

        String result = underTest.deleteShow(movieTitle, roomName, movieDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteShow_withIncorrectMovieTitle() {
        String expectedResult = "There is no room or movie with this name.";
        String movieTitle = "test-movie";
        String roomName = "test-room";
        String movieDate = "1992-08-01 18:22";

        Room room = new Room();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.empty());

        String result = underTest.deleteShow(movieTitle, roomName, movieDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteShow_withIncorrectRoomName() {
        String expectedResult = "There is no room or movie with this name.";
        String movieTitle = "test-movie";
        String roomName = "test-room";
        String movieDate = "1992-08-01 18:22";

        Movie movie = new Movie();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.empty());
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));

        String result = underTest.deleteShow(movieTitle, roomName, movieDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteShow_noShowInTheRoomForTheGivenMovie() throws ParseException {
        String expectedResult = "There was no screening at this time!";
        String movieTitle = "test-movie";
        String roomName = "test-room";
        String movieDate = "1992-08-01 18:22";

        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(movieDate);
        } catch (ParseException e) {
            throw new ParseException("Sikertelen datum parse", 0);
        }

        Movie movie = new Movie();
        Room room = new Room();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));
        when(showRepository.deleteByStartDateEqualsAndMovieEqualsAndRoomEquals(eq(movieStartDate), eq(movie), eq(room)))
                .thenReturn(0);

        String result = underTest.deleteShow(movieTitle, roomName, movieDate);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteShow_withAllCorrectValues() throws ParseException {
        String expectedResult = "Screening delete successful!";
        String movieTitle = "test-movie";
        String roomName = "test-room";
        String movieDate = "1992-08-01 18:22";

        Date movieStartDate = null;
        try {
            movieStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(movieDate);
        } catch (ParseException e) {
            throw new ParseException("Sikertelen datum parse", 0);
        }

        Movie movie = new Movie();
        Room room = new Room();

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));
        when(movieRepository.findByTitleIgnoreCase(eq(movieTitle)))
                .thenReturn(Optional.of(movie));
        when(showRepository.deleteByStartDateEqualsAndMovieEqualsAndRoomEquals(eq(movieStartDate), eq(movie), eq(room)))
                .thenReturn(1);

        String result = underTest.deleteShow(movieTitle, roomName, movieDate);

        assertEquals(expectedResult, result);
    }

}
