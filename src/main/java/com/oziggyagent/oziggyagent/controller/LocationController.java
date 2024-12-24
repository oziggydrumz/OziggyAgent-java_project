package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.dto.requestdto.LocationRequest;
import com.oziggyagent.oziggyagent.service.LocationService;
import com.oziggyagent.oziggyagent.service.exception.LocationAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/MyLocation",produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(value = "/addLocation")
    public ResponseEntity<?>addLocation(@RequestBody LocationRequest locationRequest) throws LocationAlreadyExist {
        return new ResponseEntity<>(locationService.addLocation(locationRequest), HttpStatus.CREATED);
    }


}
