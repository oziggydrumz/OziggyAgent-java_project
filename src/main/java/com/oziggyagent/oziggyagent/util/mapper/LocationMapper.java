package com.oziggyagent.oziggyagent.util.mapper;

import com.oziggyagent.oziggyagent.dto.requestdto.LocationRequest;
import com.oziggyagent.oziggyagent.model.MyLocation;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public MyLocation map( LocationRequest locationRequest){

        MyLocation location =new MyLocation();

        location.setState(locationRequest.getState());

        return location;

    }
}
