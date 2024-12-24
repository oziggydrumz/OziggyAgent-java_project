package com.oziggyagent.oziggyagent.service.exception;

import com.oziggyagent.oziggyagent.dto.requestdto.LocationRequest;
import com.oziggyagent.oziggyagent.model.MyLocation;
import com.oziggyagent.oziggyagent.repo.LocationRepo;
import com.oziggyagent.oziggyagent.service.LocationService;
import com.oziggyagent.oziggyagent.util.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service

public class LocationServiceImp implements LocationService {
@Autowired
    private final LocationRepo locationRepo;

    private final LocationMapper locationMapper;


    public LocationServiceImp(LocationRepo locationRepo, LocationMapper locationMapper) {
        this.locationRepo = locationRepo;
        this.locationMapper = locationMapper;
    }

    @Override

    public MyLocation addLocation( LocationRequest locationRequest) throws LocationAlreadyExist {

        MyLocation addLocation=locationRepo.findByState(locationRequest.getState());
        if(addLocation!=null){
            throw new LocationAlreadyExist("location already exist", HttpStatus.ALREADY_REPORTED);
        }
       MyLocation location=locationMapper.map( locationRequest);

        locationRepo.save(location);
        return location;
    }
}
