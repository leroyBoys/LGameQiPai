/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.supers;

import com.logger.type.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author leroy
 */
public class SuperLog {

    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    private int level;

    public static void log(int level, Class cls, String info) {
        ((Logger) LoggerFactory.getLogger(cls)).info(info);
    }

    public static void out(int level, String info) {
        System.out.println(info);
    }

    public static void log(int level, String info) {
        System.out.println(info);
    }

    public static void error(int level, String info) {
        System.err.println(info);
    }

    public void out(int index, String loginfo, Logger logg) {
        //logg.debug(loginfo);
        System.out.println(loginfo);
        //区别谁要打印
        switch (index) {
            case 6://系统信息
                //case 1://Spice
                //System.out.println(loginfo);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void ErrorLog(Exception ex, LogType loginType, String desctag) {
        ex.printStackTrace();
    }

    public void SaveBlankSession(String loginfo) {
    }

    public void SaveLog(int logindex, String fileName, String loginfo) {

    }

    public void SaveErrorLog(int logindex, String loginfo) {
    }

    public void SaveExceptionLog(int logindex, Exception loginfo) {
    }

    public void outLogObject(String fileName, Object... logs) {
    }
}
