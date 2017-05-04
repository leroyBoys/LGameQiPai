package com.game.core.service.impl;

import com.game.core.dao.mysql.UserDao;
import com.game.core.dao.redis.UserRedis;
import com.game.core.service.UserService;
import com.game.manager.OnlineKeyManager;
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
        return false;
    }

    @Override
    public UserInfo getUserInfo(String userName, String pwd) {
        return null;
    }

    @Override
    public UserInfo getUserInfo(String userName) {
        return null;
    }

    @Override
    public UserInfo getUserInfoFromId(int fromId) {
        return null;
    }

    @Override
    public UserInfo getUserInfo(int uid) {
        return null;
    }

    @Override
    public boolean updateUserToken(int uid, String key, String ip) {
        return false;
    }

    @Override
    public UserInfo insertUserInfo(UserInfo info) {
        return null;
    }

    @Override
    public UserDev insertDev(String device_info, String device_name, String device_mac, String udid, int os_id) {
        return null;
    }

    @Override
    public int findDevId(String device_mc, String udid) {
        return 0;
    }

    @Override
    public UserFrom insertFrom(UserFrom from) {
        return null;
    }

    @Override
    public boolean updateUserInfoStatus(int uid, Status.UserStatus status, Date endTime) {
        return false;
    }

    @Override
    public boolean updateUserInfoStatus(int uid, String userName, String pwd, String invite_code) {
        return false;
    }

    @Override
    public boolean updateUserInfoLastDev(int uid, int devId) {
        return false;
    }

    @Override
    public boolean updateUserInfoLoginStatus(int uid, boolean isOnline, Date updateTime) {
        return false;
    }

    @Override
    public RoleInfo getRoleInfoByUid(int uid) {
        return null;
    }

    @Override
    public RoleInfo getRoleInfoById(int id) {
        return null;
    }

    @Override
    public int createRoleInfo(int uid, String alise, String headImage, byte[] head, int sex, int iv, int exp, int vip) {
        return 0;
    }

    @Override
    public void updateRoleInfo(RoleInfo info) {

    }

    @Override
    public boolean updatepwd(int uid, String oldPwd, String newpwd) {
        return false;
    }

    @Override
    public String getCustom(int uid, CustomKey ck) {
        return null;
    }

    @Override
    public boolean setCustom(int uid, CustomKey ck, String val) {
        return false;
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
        return null;
    }

    @Override
    public int getMoney(int uid) {
        return 0;
    }

    @Override
    public int getGold(int uid) {
        return 0;
    }

    @Override
    public int addMoney(int uid, int needMoney, LogType logFrom, int logId) {
        return 0;
    }

    @Override
    public int addGold(int uid, int needGold, LogType logFrom, int logId) {
        return 0;
    }

    @Override
    public void setSkillPoint(int roleId, int skillPoint) {

    }
}
