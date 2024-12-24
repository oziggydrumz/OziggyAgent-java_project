package com.oziggyagent.oziggyagent.dto.requestdto.responesedto;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class PaymentGateWayResponse {
    private BigDecimal amount;
    private String emailAddress;
    private String ipAddress;
    private String paidAt;
    private String reference;
    private String channel;
    private String currency;
}
