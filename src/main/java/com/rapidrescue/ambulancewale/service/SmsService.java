package com.rapidrescue.ambulancewale.service;

import com.rapidrescue.ambulancewale.models.entity.Booking;
import com.rapidrescue.ambulancewale.models.enums.SmsStatus;
import com.rapidrescue.ambulancewale.models.response.BookingRecivedSmsResponse;
import com.rapidrescue.ambulancewale.security.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsService {
//    curl 'https://api.twilio.com/2010-04-01/Accounts/AC988e3d8b8e53f552c8b3d07b3fda1dbf/Messages.json' -X POST \
//            --data-urlencode 'To=+917769066177' \
//            --data-urlencode 'MessagingServiceSid=MG1f6b9983b60e535644c9fcf35493cf0f' \
//            --data-urlencode 'Body=Ahoy 👋' \
//            -u AC988e3d8b8e53f552c8b3d07b3fda1dbf:[AuthToken]

    @Autowired
    private TwilioConfig twilioConfig;


    public BookingRecivedSmsResponse sendSms(String msg , String number) {

        try {


            log.info("=================== sendSms =======================");
            PhoneNumber to = new PhoneNumber("+91"+number);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrailNumber());
            log.info("send sms => "+ from+ " and to "+ to+" ");
            Message message = Message.creator(to, from,msg).create();

            log.info("=================== sendSms Response ======================="+ message.getStatus() + " " + message.getErrorMessage());
            return  new BookingRecivedSmsResponse(SmsStatus.DELIVERED,msg);
        }catch (Exception e){
            log.info("========================Exception in sendSms ==================="+e.getMessage());
            return new BookingRecivedSmsResponse(SmsStatus.FAILED,e.getMessage());
        }

    }
}
