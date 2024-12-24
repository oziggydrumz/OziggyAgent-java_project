package com.oziggyagent.oziggyagent.model;

import lombok.Getter;

@Getter

public enum Location {
   LAGOS("lagos"),BENIN("benin"),IMO("imo"),ABUJA("abuja"),PORT_HARCOURT("portHarcourt");
    private final String value;
   Location(String value){
       this.value=value;
   }
}
