package com.travel.toy3.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travel.toy3.exception.CustomErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    // HttpStatus
    private Integer resultCode;
    private String resultMessage;

    // CustomErrorCode의 message
    private String errorMessage;

    // 실제 data
    private T data;
}
