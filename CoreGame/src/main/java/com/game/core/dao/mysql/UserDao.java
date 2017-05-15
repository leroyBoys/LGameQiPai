package com.game.core.dao.mysql;

import com.game.manager.CoreServiceManager;
import com.lgame.util.MethodCacheTime;
import com.lgame.util.StatisticsMonitor;
import com.logger.type.LogType;
import com.module.CustomKey;
import com.module.SetKey;
import com.module.Status;
import com.module.db.*;
import com.module.supers.Base;
import com.mysql.impl.SqlPool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/4.
 */
public class UserDao implements Base {
    private SqlPool userPool;
    private SqlPool gamePool;
    private StatisticsMonitor monitor;
    public UserDao(SqlPool userPool,SqlPool gamePool){
        this.userPool = userPool;
        this.gamePool = gamePool;
        monitor = CoreServiceManager.getIntance().monitor;
    }

    public UserInfo getUserInfo(String userName) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteQueryOne(UserInfo.instance,"SELECT * FROM user_info WHERE user_name =?", userName);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"getUserInfo1");
        }
        return null;
    }

   
    public UserInfo getUserInfoFromId(int fromId) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteQueryOne(UserInfo.instance,"SELECT * FROM user_info WHERE user_from_id = ?", fromId);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"getUserInfoFromId");
        }
        return null;
    }

   
    public UserInfo getUserInfoDevId(int devId) {
        return getUserInfo(0, 0, devId);
    }

    private UserInfo getUserInfo(int uid, int fromId, int devId) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteQueryOne(UserInfo.instance,"SELECT * FROM user_info WHERE id= ? AND user_from_id = ? AND device_id = ?",uid, fromId,devId);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"getUserInfo2");
        }
        return null;
    }


    public UserInfo getUserInfo(int uid) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteQueryOne(UserInfo.instance,"SELECT * FROM user_info WHERE id = ?", uid);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("GET_USERINFO_ID");
        }
        return null;
    }

   
    public UserInfo insertUserInfo(UserInfo info) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_info(device_id,user_from_type,user_from_id,user_name,user_pwd,role,invite_code,user_status,status_endtime,create_date)"
                    + "			VALUES(?,?,?,?,?,?,?,?,?,?)", new Object[]{
                    info.getDeviceId(),
                    info.getUserFromType(),
                    info.getUserFromId(),
                    info.getUserName(),
                    info.getUserPwd(),
                    info.getRole(),
                    info.getInviteCode(),
                    info.getUserStatus(),
                    info.getStatusEndTime(),
                    info.getCreateDate()
            });
            info.setId((int) id);
            return info;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("IN_USERINFO");
        }
        return null;
    }

   
    public UserDev insertDev(UserDev dev) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_device(device_info,device_name,os_id,device_mac,udid,create_date)VALUES(?,?,?,?,?,?)", new Object[]{
                    dev.getDeviceInfo(),
                    dev.getDeviceName(),
                    dev.getOsId(),
                    dev.getDeviceMac(),
                    dev.getUdid(),
                    dev.getCreateDate()
            });
            dev.setId((int) id);
            return dev;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("IN_DEVICE");
        }
        return null;
    }

   
    public int findDevId(String device_mc, String udid) {
        MethodCacheTime ct = monitor.start();
        try {
          Object obj = userPool.ExecuteQueryOnlyValue("CALL GET_DEV(?,?)", new Object[]{
                  device_mc,
                  udid});
          if(obj == null){
              return 0;
          }

          return (int) obj;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("GET_DEV");
        }
        return -1;
    }

   
    public UserFrom insertFrom(UserFrom from) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_from(user_src,serial_num,info,create_date)VALUES(?,?,?,?)", new Object[]{from.getUserSrc(), from.getSerialNum(), from.getInfo(), from.getCreateDate()});
            from.setId((int) id);
            return from;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("IN_USERFROM");
        }
        return null;
    }

   
    public boolean updateUserInfoStatus(int uid, Status.UserStatus status, Date endTime) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("CALL SET_USERINFO_STATUS(?,?,?)", new Object[]{
                    uid, status.getValue(), endTime
            });
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("SET_USERINFO_STATUS");
        }
        return false;
    }

   
    public boolean updateUserInfoLoginStatus(int uid, boolean isOnline, Date updateTime) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("CALL SET_USERINFO_ONLINE(?,?,?)", new Object[]{
                    uid, isOnline, updateTime
            });
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("SET_USERINFO_ONLINE");
        }
        return false;
    }

   
    public boolean updateUserInfoStatus(int uid, String userName, String pwd, String invite_code) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("CALL SET_USERINFO_AGAIN(?,?,?,?)", new Object[]{
                    uid, userName, pwd, invite_code
            });
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("SET_USERINFO_AGAIN");
        }
        return false;
    }

   
    public boolean updateUserInfoLastDev(int uid, int devId) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("CALL SET_USERINFO_DEV(?,?)", new Object[]{
                    uid, devId
            });
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("SET_USERINFO_DEV");
        }
        return false;
    }


    public int createRoleInfo(int uid, String alise, String headImage,int sex, int iv, int exp, int vip) {
        MethodCacheTime ct = monitor.start();
        try {
            return (int) gamePool.Insert("INSERT INTO role_info" +
                            "(user_alise,uid,head_image,user_lv,user_exp,vip_level,vip_end_time,user_sex,create_date,user_status)" +
                            "VALUES(?,?,?,?,?,?,?,?,?,?)",
                    alise, uid, headImage, iv, exp, 0, null, sex, new Date(), Status.UserStatus.normal.getValue());
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("IN_ROLE_INFO");
        }
        return -1;
    }

   
    public boolean updatepwd(int uid, String oldPwd, String newpwd) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteUpdate("CALL SET_USERINFO_PWD(?,?,?)", new Object[]{
                    uid, oldPwd, newpwd
            });
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("SET_USERINFO_PWD");
        }
        return true;
    }

   
    public String getCustom(int uid, CustomKey ck) {
        MethodCacheTime ct = monitor.start();
        try {
            return (String) gamePool.ExecuteQueryOnlyValue("CALL get_user_custom(?,?)", new Object[]{uid, ck.getValue()});
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("get_user_custom");
        }
        return null;
    }

   
    public boolean setCustom(int uid, CustomKey ck, String val) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteUpdate("CALL set_user_custom(?,?,?)", new Object[]{
                    uid, ck.getValue(), val
            });
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("set_user_custom");
        }
        return true;
    }

   
    public RoleInfo getRoleInfoByUid(int uid) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteQueryOne(RoleInfo.instance,"SELECT * FROM role_info WHERE uid = 1?", uid);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("get_role_by_uid");
        }
        return null;
    }

    /**
     *
     * @param uid
     * @param ck
     * @return cid子id val状态
     */
   
    public Map<Integer, String> getUserSetByKey(int uid, SetKey ck) {
        MethodCacheTime ct = monitor.start();
        try {
            return (Map<Integer, String>) gamePool.ExecuteQueryOnlyValue("CALL get_sets_uid_pid(?,?)", new Object[]{uid, ck.getValue()});
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("get_sets_uid_pid");
        }
        return null;
    }

   
    public List<UserSet> getUserSets(int uid) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteQuery(UserSet.instance,"CALL get_sets_uid(?)", new Object[]{uid});
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("get_sets_uid");
        }
        return null;
    }

   
    public boolean setUserSet(int uid, SetKey ck, int cid, String val) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteUpdate("CALL set_user_set(?,?,?,?)", new Object[]{uid, ck.getValue(), cid, val});
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("set_user_set");
        }
        return false;
    }

   
    public RoleInfo getRoleInfoById(int id) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteQueryOne(RoleInfo.instance,"CALL get_role_by_id(?)", new Object[]{id});
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("get_role_by_id");
        }
        return null;
    }

    public void updateCard(int roleId, int card) {
        MethodCacheTime ct = monitor.start();
        try {
            gamePool.Execute("CALL get_role_by_id(update user_info set card=? where id = ?)",roleId,card);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("updateCard");
        }
    }
}
