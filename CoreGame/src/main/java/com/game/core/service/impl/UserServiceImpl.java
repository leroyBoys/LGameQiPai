package com.game.core.service.impl;

import com.game.core.dao.mysql.UserDao;
import com.game.core.dao.redis.UserRedis;
import com.game.core.service.UserService;
import com.game.manager.OnlineManager;
import com.logger.log.GameLog;
import com.logger.log.SystemLogger;
import com.logger.type.LogType;
import com.module.CustomKey;
import com.module.ItemData;
import com.module.Status;
import com.module.db.RoleInfo;
import com.module.db.UserDev;
import com.module.db.UserFrom;
import com.module.db.UserInfo;
import com.module.net.DB;

import java.util.Date;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/4.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserRedis userRedis;
    public UserServiceImpl(UserDao userDao,UserRedis userRedis){
        this.userDao = userDao;
        this.userRedis = userRedis;
    }

    @Override
    public DB.UK getUserKey(int uid) {
        return userRedis.getUserKey(uid);
    }

    @Override
    public void setUserKey(int uid,String ipPot,String key) {
        userRedis.setUserKey(uid,ipPot,key);
    }

    @Override
    public boolean isOnline(int uid) {
        return OnlineManager.getIntance().getRoleId(uid) != null;
    }

    @Override
    public UserInfo getUserInfo(String userName, String pwd) {
        UserInfo userInfo = userDao.getUserInfo(userName);
        if(userInfo == null || !pwd.equals(userInfo.getUserPwd())){
            return null;
        }
        return userInfo;
    }

    @Override
    public UserInfo getUserInfo(String userName) {
        return userDao.getUserInfo(userName);
    }

    @Override
    public UserInfo getUserInfoFromId(int fromId) {
        return userDao.getUserInfoFromId(fromId);
    }

    @Override
    public UserInfo getUserInfo(int uid) {
        return userDao.getUserInfo(uid);
    }

    @Override
    public boolean updateUserToken(int uid, String key, String ip) {
        userRedis.setUserKey(uid,ip,key);
        return false;
    }

    @Override
    public UserInfo insertUserInfo(UserInfo info) {
        return userDao.insertUserInfo(info);
    }

    @Override
    public UserDev insertDev(String device_info, String device_name, String device_mac, String udid, int os_id) {
        int devId = findDevId(device_mac, udid);
        UserDev dev = new UserDev();
        dev.setCreateDate(new Date());
        dev.setDeviceInfo(device_info);
        dev.setDeviceMac(device_mac);
        dev.setDeviceName(device_name);
        dev.setOsId(os_id);
        dev.setUdid(udid);
        dev.setId(devId);
        if (devId > 0) {
            return dev;
        }
        return userDao.insertDev(dev);
    }

    @Override
    public int findDevId(String device_mc, String udid) {
        return userDao.findDevId(device_mc, udid);
    }

    @Override
    public UserFrom insertFrom(UserFrom from) {
        return userDao.insertFrom(from);
    }

    @Override
    public boolean updateUserInfoStatus(int uid, Status.UserStatus status, Date endTime) {
        return userDao.updateUserInfoStatus(uid, status, endTime);
    }

    @Override
    public boolean updateUserInfoLastDev(int uid, int devId) {
        return userDao.updateUserInfoLastDev(uid, devId);
    }

    @Override
    public boolean updateUserInfoLoginStatus(int uid, boolean isOnline, Date updateTime) {
        return userDao.updateUserInfoLoginStatus(uid, isOnline, updateTime);
    }

    @Override
    public RoleInfo getRoleInfoByUid(int uid) {
        return userDao.getRoleInfoByUid(uid);
    }

    @Override
    public RoleInfo getRoleInfoById(int id) {
        return userDao.getRoleInfoById(id);
    }

    @Override
    public int createRoleInfo(int uid, String alise, String headImage, int sex, int iv, int exp, int vip) {
        return userDao.createRoleInfo(uid, alise, headImage, sex, iv, exp, vip);
    }

    @Override
    public void updateRoleInfo(RoleInfo info) {
    }

    @Override
    public boolean updatepwd(int uid, String newpwd) {
        return userDao.updatepwd(uid, newpwd);
    }

    @Override
    public String getCustom(int uid, CustomKey ck) {
        return userDao.getCustom(uid, ck);
    }

    @Override
    public boolean setCustom(int uid, CustomKey ck, String val) {
        return userDao.setCustom(uid, ck, val);
    }

    @Override
    public List<ItemData> getDailSignReward(String date) {
        return null;
    }

    @Override
    public List<ItemData> getContinuesSignReward(int rewardDaynum) {
        return null;
    }

    @Override
    public String getAutoName(int sex) {
        return "tome" + sex;
    }

    @Override
    public int getMoney(int uid) {
        return userDao.getCardCount(uid);
    }

    @Override
    public int addMoney(int uid, int needMoney, LogType logFrom, int logId) {
        int money =  getMoney(uid);
        money+=needMoney;
        if(money<0 ){
            updateMoney(uid,0,logFrom,logId);
        }else {
            updateMoney(uid,money,logFrom,logId);
        }

        GameLog.cardLog(needMoney,uid,0,new Date(),"");
        return money;
    }

    @Override
    public int updateMoney(int uid, int money, LogType logFrom, int logId) {
        userDao.updateCard(uid, money);
        return money;
    }

    @Override
    public void updateRoleInfoRoomid(int roleId, int id) {

    }

    @Override
    public boolean loginConfim(int uid) {
        return userDao.loginConfim(uid);
    }

    @Override
    public boolean offLine(int uid) {
        return userDao.offLine(uid);
    }
}
