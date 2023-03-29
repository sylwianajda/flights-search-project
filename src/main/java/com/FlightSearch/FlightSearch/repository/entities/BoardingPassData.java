package com.FlightSearch.FlightSearch.repository.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="boarding_passes")
public class BoardingPassData {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private long boardingPassId;
        private String firstName;
        private String lastName;
//        @Embedded
//        private FlightDto flightDto = new FlightDto(flightflight.getId());
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER )
        @Cascade(MERGE)
        @JoinColumn(name = "flight_id")
        private FlightData flightData;

        public BoardingPassData(String firstName, String lastName, FlightData flightData) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.flightData = flightData;
        }

        public BoardingPassData(BoardingPass boardingPass) {
                //this.boardingPassId = boardingPass.getBoardingPassId();
                this.firstName = boardingPass.getFirstName();
                this.lastName = boardingPass.getLastName();
                this.flightData = new FlightData(boardingPass.getFlight());
        }

        public FlightData getFlightData() {
                return flightData;
        }

        public void setFlightData(FlightData flightData) {
                this.flightData = flightData;
        }
}

//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "id", nullable = false)
//        private long boardingPassId;
//        private long flightNumber;
//        //private int seatNumber;
//        private String firstName;
//        private String lastName;
//        private LocalDateTime departDate;
//        private Time gateClosedTime;
//        //private LocalDateTime bookingTime;
//        private FlightDto flight;
//
//}
