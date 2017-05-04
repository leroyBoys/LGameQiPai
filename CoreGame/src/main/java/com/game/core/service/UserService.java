package com.game.core.service;

import com.logger.type.LogType;
import com.module.CustomKey;
import com.module.ItemData;
import com.module.Status;
import com.module.db.*;
import com.module.net.DB;

import java.util.Date;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/4.
 */
public interface UserService {

    public DB.UK getUserKey(int uid, boolean isFromDb);

    public boolean isOnline(int uid);

    public UserInfo getUserInfo(String userName, String pwd);

    public UserInfo getUserInfo(String userName);

    public UserInfo getUserInfoFromId(int fromId);

    public UserInfo getUserInfo(int uid);

    public boolean updateUserToken(int uid, String key, String ip);

    public UserInfo insertUserInfo(UserInfo info);

    public UserDev insertDev(String device_info, String device_name, String device_mac, String udid, int os_id);

    public int findDevId(String device_mc, String udid);

    public UserFrom insertFrom(UserFrom from);

    public boolean updateUserInfoStatus(int uid, Status.UserStatus status, Date endTime);

    public boolean updateUserInfoStatus(int uid, String userName, String pwd, String invite_code);

    public boolean updateUserInfoLastDev(int uid, int devId);

    public boolean updateUserInfoLoginStatus(int uid, boolean isOnline, Date updateTime);


    public RoleInfo getRoleInfoByUid(int uid);

    public RoleInfo getRoleInfoById(int id);

    public int createRoleInfo(int uid, String alise, String headImage, byte[] head, int sex, int iv, int exp, int vip);

    public void updateRoleInfo(RoleInfo info);

    public boolean updatepwd(int uid, String oldPwd, String newpwd);

    public String getCustom(int uid, CustomKey ck);

    public boolean setCustom(int uid, CustomKey ck, String val);

    public List<ItemData> getDailSignReward(String date);

    public List<ItemData> getContinuesSignReward(int rewardDaynum);

    public String getAutoName(int sex);

    public int getMoney(int uid);

    public int getGold(int uid);

    public int addMoney(int uid, int needMoney, LogType logFrom,int logId);

    public int addGold(int uid, int needGold, LogType logFrom,int logId);

    public void setSkillPoint(int roleId, int skillPoint);

}
