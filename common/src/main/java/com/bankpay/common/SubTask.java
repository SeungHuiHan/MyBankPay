package com.bankpay.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//각 서비스에 특정 membershipId & Validation를 하기위한 Task
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTask {
    private String membershipID;
    private String subTaskName;
    private String taskType; // "banking", "membership"
    private String status; // "success", "fail", "ready"
}
