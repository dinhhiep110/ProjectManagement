package com.ntq.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Respond<T> {
    private String message;
    private T data;
}
