package com.oziggyagent.oziggyagent.serviceImp;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service


public class PayPalService {
@Autowired
    private  APIContext apiContext;

    public com.paypal.api.payments.Payment createPayment(double total,
                                                         String currency,
                                                         String method,
                                                         String intent,
                                                         String description,
                                                         String cancelUrl,
                                                         String successUrl) throws PayPalRESTException {
        Amount amount=new Amount();
        amount.setCurrency(currency);
        total=new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f",total));

        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);

        List<Transaction>transactions=new ArrayList<>();
        transactions.add(transaction);

        Payer payer=new Payer();
        payer.setPaymentMethod(method.toString());

        com.paypal.api.payments.Payment payment=new com.paypal.api.payments.Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls=new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);





        return payment.create(apiContext);
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        com.paypal.api.payments.Payment payment=new com.paypal.api.payments.Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution=new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext,paymentExecution);

    }




}