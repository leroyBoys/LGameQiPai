/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.util;

import com.lgame.util.comm.StringTool;

public class DbTraceData {

    public String Name = null;
    public long Count = 0;
    public long TotalConsume = 0;
    public long AvgConsume = 0;
    public long MaxConsume = 0;
    
    
    public DbTraceData(String name) {
        this.Name = name;
    }

    public void trace(long t) {
        this.Count += 1;
        this.TotalConsume += t;
        if (t > this.MaxConsume) {
            this.MaxConsume = t;
        }
        this.AvgConsume = (this.TotalConsume / this.Count);
    }

    public String getSumDes() {
        return getDesc(this.TotalConsume);
    }

    public String getAvgDes() {
        return getDesc(this.AvgConsume);
    }

    public String getMaxDes() {
        return getDesc(this.MaxConsume);
    }

    public String getDesc(long t) {
        if (t < 1000L) {
            return StringTool.Format("{0}毫秒", new Object[]{t});
        }
        if (t < 1000000L) {
            return StringTool.Format("{0}秒", new Object[]{t / 1000L});
        }

        return StringTool.Format("{0}k秒", new Object[]{t / 1000000L});
    }
}
