package com.oziggyagent.oziggyagent.controller;

import com.oziggyagent.oziggyagent.configuration_properties.ConfigurationProperties;
import com.oziggyagent.oziggyagent.dto.requestdto.OrderRequest;
import com.oziggyagent.oziggyagent.model.User;
import com.oziggyagent.oziggyagent.repo.UserRepo;
import com.oziggyagent.oziggyagent.serviceImp.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/paypalController",produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class PaypalController {

@Autowired
    private  PayPalService payPalService;

      @Autowired
     private UserRepo userRepo;

      private final ConfigurationProperties configurationProperties;



 private static final   String cancelUrl = "https://localhost:8080/payment/cancel";
   private static final  String successUrl = "https://localhost:8080/payment/success";

    @PostMapping("/makePayment")
    public String makePayment(@RequestBody OrderRequest orderRequest,long id) {
        try {
            User myUser=userRepo.findAUserById(id);
            if (myUser==null){
                return String.format("Redirected to user registration %s/user/userRegister", configurationProperties.getBasePath());
            }
       //     String cancelUrl = "https://localhost:9090/payment/cancel";
       //     String successUrl = "https://localhost:9090/payment/success";
            com.paypal.api.payments.Payment payment = payPalService.createPayment(
                    orderRequest.getTotal(),
                    orderRequest.getCurrency(),
                    orderRequest.getMethod(),
                    orderRequest.getIntent(),
                    orderRequest.getDescription(),
                    cancelUrl,
                    successUrl

            );
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect"+links.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            log.error("error occurred::", e);

        }

        return "redirect"+("/payment/error");
    }
@GetMapping("/paymentSuccess")
    public String paymentSuccess(
            @RequestParam String paymentId,
            @RequestParam   String payerId
    ) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approval")) {
                return "paymentSuccess";
            }

        } catch (PayPalRESTException e) {
            log.error("error occurred::");

        }
        return "paymentSuccess";
    }
@GetMapping("/paymentCancel")
    public String paymentCancel() {
        return "paymentCancel";

    }
@GetMapping("/paymentError")
    public String paymentError() {
        return "paymentError";
    }




}
