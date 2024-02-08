package com.assessment.assessment.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDetails {

    private LocalDateTime timeStamp;
    private String message;
    private String path;
    private String errorCode;
}
