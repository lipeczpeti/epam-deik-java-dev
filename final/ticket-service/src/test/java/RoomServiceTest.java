import com.epam.training.ticketservice.domain.entity.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService underTest;

    @Captor
    private ArgumentCaptor<Room> roomCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRoom_passedCorrectObject() {
        String newRoomName = "new york room";
        int newRoomRows = 10;
        int newRoomColumns = 15;

        String result = underTest.createRoom(newRoomName, newRoomColumns, newRoomRows);

        verify(roomRepository).save(roomCaptor.capture());
        Room room = roomCaptor.getValue();

        assertEquals("Room creation successful!", result);
        assertEquals(newRoomName, room.getName());
        assertEquals(newRoomColumns, room.getColumns());
        assertEquals(newRoomRows, room.getRows());
    }

    @Test
    public void testUpdateRoom_passingNonExistingRoomName() {
        String roomName = "room";
        int roomRows = 10;
        int roomColumns = 15;
        String expectedResult = "There is no room with this name: " + roomName;

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.empty());

        String result = underTest.updateRoom(roomName, roomColumns, roomRows);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testUpdateRoom_passingExistingRoomName() {
        String roomName = "room";
        int roomRows = 10;
        int roomColumns = 15;
        String expectedResult = "Successful update!";

        Room room = new Room();
        room.setId(1);
        room.setName(roomName);
        room.setColumns(20);
        room.setRows(15);

        when(roomRepository.findByNameIgnoreCase(eq(roomName)))
                .thenReturn(Optional.of(room));

        String result = underTest.updateRoom(roomName, roomColumns, roomRows);

        verify(roomRepository).save(roomCaptor.capture());
        Room roomPassedToSave = roomCaptor.getValue();

        assertEquals(expectedResult, result);
        assertEquals(roomName, roomPassedToSave.getName());
        assertEquals(roomColumns, roomPassedToSave.getColumns());
        assertEquals(roomRows, roomPassedToSave.getRows());
    }

    @Test
    public void testDeleteRoom_nonExistingRoomNamePassed() {
        String roomName = "xyz";
        String expectedResult = "Delete unsuccessful";

        when(roomRepository.deleteByName(eq(roomName)))
                .thenReturn(0);

        String result = underTest.deleteRoom(roomName);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteRoom_existingRoomNamePassed() {
        String roomName = "xyz";
        String expectedResult = "1 room(s) had been deleted.";

        when(roomRepository.deleteByName(eq(roomName)))
                .thenReturn(1);

        String result = underTest.deleteRoom(roomName);

        assertEquals(expectedResult, result);
    }

    @Test
    public void testListRooms_noRoomsInDatabase() {
        String expectedResult = "There are no rooms at the moment";

        when(roomRepository.findAll())
                .thenReturn(new ArrayList<>());

        String result = underTest.listRooms();

        assertEquals(expectedResult, result);
    }

    @Test
    public void testListRooms_twoRoomsInDatabase() {
        Room room1 = new Room();
        room1.setId(1);
        room1.setColumns(10);
        room1.setRows(11);
        room1.setName("xyz");

        Room room2 = new Room();
        room2.setId(2);
        room2.setColumns(20);
        room2.setRows(21);
        room2.setName("abc");

        String expectedResult = "Room " + room1.getName() + " with " + (room1.getColumns() * room1.getRows()) + " seats, " + room1.getRows() + " rows and " + room1.getColumns() + " columns\n"
                + "Room " + room2.getName() + " with " + (room2.getColumns() * room2.getRows()) + " seats, " + room2.getRows() + " rows and " + room2.getColumns() + " columns\n";

        when(roomRepository.findAll())
                .thenReturn(List.of(room1, room2));

        String result = underTest.listRooms();

        assertEquals(expectedResult, result);
    }
}
