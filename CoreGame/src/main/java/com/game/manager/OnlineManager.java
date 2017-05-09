package com.game.manager;

import com.game.socket.module.UserVistor;
import com.lgame.util.exception.AppException;
import com.logger.log.SystemLogger;
import com.lsocket.manager.AddressManager;
import com.lsocket.module.Visitor;
import com.module.core.ResponseCode;
import com.module.supers.SuperLog;
import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by leroy:656515489@qq.com
 * 2017/4/6.
 */
public class OnlineManager{
    private static final OnlineManager serverManager = new OnlineManager();

    private OnlineManager(){}
    public static OnlineManager getIntance(){
        return serverManager;
    }

    public UserVistor getUserById(int uid){
        return roleId_session.get(uid);
    }

    protected final int maxOnlineCount = 10000;
    protected ConcurrentHashMap<Integer, UserVistor> roleId_session = new ConcurrentHashMap();//userId-session

    private ConcurrentHashMap<String, AtomicInteger> ip_links = new ConcurrentHashMap();
    protected final int ip_maxLinks = 20;//单客户端连接最大数

    protected final ConcurrentHashMap<String, IoSession> reply_is_map = new ConcurrentHashMap();

    /**
     * 添加到session列表返回之前的session，如果之前没有则返回null
     * @param roleId
     * @param userVistor
     * @return
     */
    public ResponseCode.Error putOnlineList(int roleId,int uid,UserVistor userVistor){
        if (userVistor == null || roleId <= 0) {
            return ResponseCode.Error.succ;
        }
        userVistor.setUid(uid);
        userVistor.setRoleId(roleId);

        String ip = userVistor.getIp().getIp();
        AtomicInteger atomicInteger = ip_links.get(ip);
        if(atomicInteger != null){
            if (atomicInteger.get() >= ip_maxLinks) {
                SystemLogger.warn(OnlineManager.class,"已超出但客户端最大连接数" + ip_maxLinks);
                return ResponseCode.Error.one_server_too_connect;
            }
        }else {
            synchronized (ip_links) {
                atomicInteger = ip_links.get(ip);
                if(atomicInteger == null){
                    atomicInteger = new AtomicInteger(0);
                    ip_links.put(ip,atomicInteger);
                }
            }
        }
        atomicInteger.incrementAndGet();

        int onlineCount = getCurrentOnlineCount();
        if (onlineCount >= maxOnlineCount) {
            SystemLogger.warn(OnlineManager.class,"服务器人数超载，请选择其他服务器登陆，当前在线人数：" + onlineCount);
            return ResponseCode.Error.server_too_busy;
        }

        roleId_session.put(roleId,userVistor);
        return ResponseCode.Error.succ;
    }

    /**
     * 移除session返回之前的session，如果之前没有则返回null
     *
     * @return
     */
    public void removeFromOnlineList(int roleId) {
        UserVistor userVistor = roleId_session.remove(roleId);

        if (userVistor == null) {
            return;
        }

        String ip = userVistor.getIp().getIp();
        AtomicInteger atomicInteger = ip_links.get(ip);
        if(atomicInteger == null){
            return;
        }

        atomicInteger.decrementAndGet();
    }

    /**
     * 是否在本服务器
     * @param roleId
     * @return
     */
    public boolean isOnlineHere(int roleId) {
        if(roleId == 0){
            return  false;
        }
        return roleId_session.containsKey(roleId);
    }

    public void doErrorResponse(UserVistor userVistor, String info) {
        if (userVistor.getConnectErrorCount() < 10) {
            userVistor.setConnectErrorCount(userVistor.getConnectErrorCount()+1);
            SuperLog.out(0, info);
        } else {
            //加入黑名单
            AddressManager.getIntstance().addBlack(userVistor, info);
        }
    }

    public int getCurrentOnlineCount() {
        return roleId_session.size();
    }

    public IoSession getReply_is_map(String ip) {
        return reply_is_map.get(ip);
    }

    public ConcurrentHashMap<String, IoSession> getReply_is_map() {
        return reply_is_map;
    }
}
