package com.game.core.service.impl;

import com.game.core.dao.mysql.UserDao;
import com.game.core.dao.redis.UserRedis;
import com.game.core.service.UserService;
import com.game.manager.OnlineKeyManager;
import com.game.manager.OnlineManager;
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
    public DB.UK getUserKey(int uid, boolean isFromDb) {
        DB.UK uk;
        if(!isFromDb){
            uk = OnlineKeyManager.getIntance().getUserById(uid);
            if(uk != null){
                return uk;
            }
        }

        uk = userRedis.getUserKey(uid);
        if(uk == null){
            OnlineKeyManager.getIntance().clearKey(uid);
            return null;
        }
        OnlineKeyManager.getIntance().putKey(uid,uk);
        return uk;
    }

    @Override
    public boolean isOnline(int uid) {
        return OnlineManager.getIntance().getUserById(uid) != null;
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
    public boolean updateUserInfoStatus(int uid, String userName, String pwd, String invite_code) {
        return userDao.updateUserInfoStatus(uid, userName, pwd, invite_code);
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
    public int createRoleInfo(int uid, String alise, String headImage, byte[] head, int sex, int iv, int exp, int vip) {
        return userDao.createRoleInfo(uid, alise, headImage, head, sex, iv, exp, vip);
    }

    @Override
    public void updateRoleInfo(RoleInfo info) {
    }

    @Override
    public boolean updatepwd(int uid, String oldPwd, String newpwd) {
        return userDao.updatepwd(uid, oldPwd, newpwd);
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
        return 0;
    }

    @Override
    public int addMoney(int uid, int needMoney, LogType logFrom, int logId) {
        return getMoney(uid);
    }
}
