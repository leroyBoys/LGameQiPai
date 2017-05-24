/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.room;

/**
 *
 * @author lxh
 */
public enum MJCardType {

    /**
     * 万
     */
    wan(1),
    /**
     * 同
     */
    tong(2),
    tiao(3),
    dong(41),
    xi(42),
    nan(43),
    bei(44),
    zhong(45),
    fa(46),
    bai(47);
    private final int value;

    private MJCardType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MJCardType valueOf(int value) {
        MJCardType[] all = MJCardType.values();
        for (MJCardType cur : all) {
            if (cur.getValue() == value) {
                return cur;
            }
        }
        return null;
    }

}
