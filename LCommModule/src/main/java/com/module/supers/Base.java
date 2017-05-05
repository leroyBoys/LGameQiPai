/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.module.supers;


import com.logger.log.GameLog;

/**
 *
 * @author leroy_boy
 */
public interface Base {

    public SuperLog file = new SuperLog();
    public GameLog mongo = new GameLog();
}
