package com.epam.training.ticketservice.domain.dto;

import com.epam.training.ticketservice.domain.entity.Movie;
import com.epam.training.ticketservice.domain.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Builder
public class ShowDto {

    private int id;
    private Date startDate;
    private String room;
    private String movie;
    private String movieCategory;
    private int movieLength;

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return movie + " (" + movieCategory + ", " + movieLength + " minutes), screened in room " + room + ", at "
                + formatter.format(startDate);
    }
}
