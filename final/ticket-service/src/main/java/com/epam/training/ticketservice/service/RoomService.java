package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.dto.RoomDto;
import com.epam.training.ticketservice.domain.entity.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public String createRoom(String name, int chairColumns, int chairRow) {
        Room room = new Room();
        room.setName(name);
        room.setRows(chairRow);
        room.setColumns(chairColumns);

        roomRepository.save(room);

        return "Room creation successful!";
    }

    public String updateRoom(String name, int chairColumns, int chairRow) {
        Optional<Room> room = roomRepository.findByNameIgnoreCase(name);

        if (room.isPresent()) {
            room.get().setColumns(chairColumns);
            room.get().setRows(chairRow);

            roomRepository.save(room.get());

            return "Successful update!";

        } else {
            return "There is no room with this name: " + name;
        }
    }

    public String deleteRoom(String name) {
        int countDeletedRows = roomRepository.deleteByName(name);

        if (countDeletedRows == 0) {
            return "Delete unsuccessful";
        } else {
            return countDeletedRows + " room(s) had been deleted.";
        }
    }

    public String listRooms() {
        List<Room> allRooms = roomRepository.findAll();

        if (allRooms.size() == 0) {
            return "There are no rooms at the moment";
        }

        List<RoomDto> roomDtos = new ArrayList<>();

        allRooms.forEach(room -> roomDtos.add(
                    RoomDto.builder()
                            .name(room.getName())
                            .rows(room.getRows())
                            .columns(room.getColumns())
                            .chairs(room.getColumns() * room.getRows())
                            .build()));

        String result = "";
        for (RoomDto room : roomDtos) {
            result += room.toString() + "\n";
        }

        return result;
    }
}
