package com.oziggyagent.oziggyagent.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrderRequest {
    private double total;
    private String description;
    private String intent;
    private String method;
    private String currency;


}