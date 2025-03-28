package com.rapidrescue.ambulancewale.models.response;

import com.rapidrescue.ambulancewale.models.enums.SmsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRecivedSmsResponse {
    private SmsStatus smsStatus;
    private String smsMessage;
}
