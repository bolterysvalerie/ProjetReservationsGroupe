package be.icc.Pid_Reservations_2024.api.controller;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Services.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is the API controller for managing shows.
 * It handles HTTP requests related to shows, such as retrieving, creating, updating, and deleting shows.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LocationApiController {

    private final LocationService locationService;

    public LocationApiController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAll();
        return ResponseEntity.ok(locations);
    }
}
