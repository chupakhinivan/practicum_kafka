package com.chupakhin.module_2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserViolation {

    private String senderUser;
    private String recipientUser;
    private short violationCount;

}
