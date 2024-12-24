package com.oziggyagent.oziggyagent.service;

import com.oziggyagent.oziggyagent.dto.requestdto.LocationRequest;
import com.oziggyagent.oziggyagent.model.MyLocation;
import com.oziggyagent.oziggyagent.service.exception.LocationAlreadyExist;
import org.springframework.stereotype.Service;

@Service
public interface LocationService {
    MyLocation addLocation(LocationRequest locationRequest) throws LocationAlreadyExist;

}
