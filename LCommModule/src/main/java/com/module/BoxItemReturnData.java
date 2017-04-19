/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leroy
 */
public class BoxItemReturnData extends ReturnStatus {
    private List<VirtualItemData> items;
    private List<String> returnMsg;//返回内容

    public BoxItemReturnData() {
    }

    public BoxItemReturnData(ResponseCode.Error statuscode,int... paraters) {
        super.setStatusCode(statuscode);
        super.setCodeParatrs(paraters);
    }

    public List<VirtualItemData> getItems() {
        return items;
    }

    public void setItems(List<VirtualItemData> items) {
        this.items = items;
    }
    
    
    public List<String> getReturnMsg() {
        return returnMsg;
    }
    
    public void setReturnMsg(List<String> returnMsg) {
        this.returnMsg = returnMsg;
    }
    
    public void addItems(List<VirtualItemData> items) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);
    }
    
    public void addItem(VirtualItemData item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }
    
    public void addMsg(String msg) {
        if (this.returnMsg == null) {
            this.returnMsg = new ArrayList<>();
        }
        this.returnMsg.add(msg);
    }
    
}
