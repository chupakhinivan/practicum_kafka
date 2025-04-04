package com.chupakhin.module_2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCensoredMessage {

    private String senderUser;
    private String recipientUser;
    private String message;
    private boolean hasViolation;
}
