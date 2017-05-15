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
            monitor.end(ct,"getUserInfo3");
        }
        return null;
    }

   
    public UserInfo insertUserInfo(UserInfo info) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_info(device_id,user_from_type,user_from_id,user_name,user_pwd,role,invite_code,user_status,status_endtime,create_date)"
                    + "			VALUES(?,?,?,?,?,?,?,?,?,?)",
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
            );
            info.setId((int) id);
            return info;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"insertUserInfo");
        }
        return null;
    }

   
    public UserDev insertDev(UserDev dev) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_device(device_info,device_name,os_id,device_mac,udid,create_date)VALUES(?,?,?,?,?,?)",
                    dev.getDeviceInfo(),
                    dev.getDeviceName(),
                    dev.getOsId(),
                    dev.getDeviceMac(),
                    dev.getUdid(),
                    dev.getCreateDate()
            );
            dev.setId((int) id);
            return dev;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"insertDev");
        }
        return null;
    }

   
    public int findDevId(String device_mc, String udid) {
        MethodCacheTime ct = monitor.start();
        try {
          Object obj = userPool.ExecuteQueryOnlyValue("SELECT * FROM user_device WHERE device_mac = ? AND udid = ?",
                  device_mc,udid);
          if(obj == null){
              return 0;
          }

          return (int) obj;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"findDevId");
        }
        return -1;
    }

   
    public UserFrom insertFrom(UserFrom from) {
        MethodCacheTime ct = monitor.start();
        try {
            long id = userPool.Insert("INSERT INTO user_from(user_src,serial_num,info,create_date)VALUES(?,?,?,?)",
                    from.getUserSrc(), from.getSerialNum(), from.getInfo(), from.getCreateDate());
            from.setId((int) id);
            return from;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"insertFrom");
        }
        return null;
    }

   
    public boolean updateUserInfoStatus(int uid, Status.UserStatus status, Date endTime) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("UPDATE user_info SET user_status = ?,status_endtime = ? WHERE id = ?;",status.getValue(), endTime,uid);
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"updateUserInfoStatus");
        }
        return false;
    }

   
    public boolean updateUserInfoLoginStatus(int uid, boolean isOnline, Date updateTime) {
        MethodCacheTime ct = monitor.start();
        try {
            String sql = isOnline?"UPDATE user_info SET is_online = ?,login_time = ? WHERE id = ?":"UPDATE user_info SET is_online = ?,login_off_time = ? WHERE id = ?";


            userPool.Execute(sql,isOnline, updateTime,uid);
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            monitor.end(ct,"updateUserInfoLoginStatus");
        }
        return false;
    }

    public boolean updateUserInfoLastDev(int uid, int devId) {
        MethodCacheTime ct = monitor.start();
        try {
            userPool.Execute("UPDATE user_info SET device_id = ?  WHERE id = ?", devId,uid);
            return true;
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("updateUserInfoLastDev");
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
            ct.dbTrace("createRoleInfo");
        }
        return -1;
    }

    public boolean updatepwd(int uid, String newpwd) {
        MethodCacheTime ct = monitor.start();
        try {
            return userPool.ExecuteUpdate("UPDATE user_info SET user_pwd = ? WHERE id = ?", new Object[]{
                    newpwd,uid
            });
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("updatepwd");
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
            ct.dbTrace("getCustom");
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
            ct.dbTrace("getRoleInfoByUid");
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
            ct.dbTrace("getUserSets");
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
            ct.dbTrace("setUserSet");
        }
        return false;
    }

   
    public RoleInfo getRoleInfoById(int id) {
        MethodCacheTime ct = monitor.start();
        try {
            return gamePool.ExecuteQueryOne(RoleInfo.instance,"SELECT * FROM role_info WHERE id = ?", id);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("getRoleInfoById");
        }
        return null;
    }

    public void updateCard(int roleId, int card) {
        MethodCacheTime ct = monitor.start();
        try {
            gamePool.Execute("CALL get_role_by_id(update user_info set card=? where id = ?)",card,roleId);
        } catch (Exception ex) {
            file.ErrorLog(ex, LogType.Error, "db");
        } finally {
            ct.dbTrace("updateCard");
        }
    }
}
