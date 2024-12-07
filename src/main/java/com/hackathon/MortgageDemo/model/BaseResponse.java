package com.hackathon.MortgageDemo.model;

import constants.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

    protected String referenceId;
    private Status status;
    private T data;
}
