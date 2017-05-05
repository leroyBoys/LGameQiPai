/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;


import com.module.core.ResponseCode;

/**
 *
 * @author leroy
 */
public class ReturnStatus {

    private  ResponseCode.Error statusCode = ResponseCode.Error.succ;
    private int[] codeParatrs;//错误码附加值
    
    public ResponseCode.Error getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ResponseCode.Error statusCode) {
        this.statusCode = statusCode;
    }

    public int[] getCodeParatrs() {
        return codeParatrs;
    }

    public void setCodeParatrs(int[] codeParatrs) {
        this.codeParatrs = codeParatrs;
    }
    
    
}
