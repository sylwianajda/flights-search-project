package com.FlightSearch.FlightSearch.controller;

import com.FlightSearch.FlightSearch.controller.exceptions.IllegalExceptionProcessing;
import com.FlightSearch.FlightSearch.repository.sqlRepository.AirportDataRepository;
import com.FlightSearch.FlightSearch.model.AirportRequest;
import com.FlightSearch.FlightSearch.model.AirportResponse;
import com.FlightSearch.FlightSearch.service.AirportReader;
import com.FlightSearch.FlightSearch.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@IllegalExceptionProcessing
@RequestMapping("/airport")
public class AirportController {
    private final AirportService airportService;
    private final AirportReader airportReader;

    public AirportController(AirportService airportService, AirportReader airportReader, AirportDataRepository airportDataRepository) {
        this.airportService = airportService;
        this.airportReader = airportReader;
    }

    @GetMapping("searchByIataCode")
    public boolean checkAirportExistsFromIataCode(@RequestParam(required = true)@NotEmpty String iataCode) {
        return airportService.findExistingAirportByIataCode(iataCode);
    }

    @GetMapping("searchByLocation")
    public List<AirportResponse> searchAirportByLocation(@RequestParam(required = true) String location) {
        return airportService.findAirportByDepartureFrom(location);
    }

    @GetMapping("searchById/{id}")
    public ResponseEntity<AirportResponse> searchAirportById(@PathVariable Integer id) {
//        if (!airportRepository.existsById(airportId)) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(airportService.findAirportById(id));
    }

    @GetMapping("/addAirports")
    public String addAirports() {
        airportReader.saveAirportsDataFromList();
        return "Airports have been added";
    }

    @Transactional
    @PutMapping("/updateAirport/{id}")
    public ResponseEntity<AirportResponse> updateAirport(@PathVariable int id, @Valid @RequestBody AirportRequest source) {
        AirportResponse airportUpdated = airportService.executeAirportUpdate(id, source);
        if (airportUpdated != null) {
            return ResponseEntity.ok(airportUpdated);
        } else {
            return ResponseEntity.notFound().build();
        }
//               if (!airportRepository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        } else {
//            airportServices.updaterAirport(id, source);
//        }
//            return ResponseEntity.noContent().build();
    }

}
