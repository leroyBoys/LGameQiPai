package com.game.core.config;

import com.lgame.util.comm.StringTool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leroy:656515489@qq.com
 * 2017/5/19.
 */
@XmlRootElement(name="root")
public class GameSwitch {
    private List<GameSwitch> switchList;
    private int switchId;
    private String switchName;
    private boolean isOpen;

    public GameSwitch(){}
    public GameSwitch(String str){
        String[] strArray = str.split(StringTool.SIGN3);
        switchId = Integer.valueOf(strArray[0]);
        isOpen = strArray.length <2||strArray.equals("0")?false:true;
    }

    @XmlElement(name = "switch")
    public List<GameSwitch> getSwitchList() {
        return switchList;
    }

    public void setSwitchList(List<GameSwitch> switchList) {
        this.switchList = switchList;
    }

    @XmlAttribute(name = "id")
    public int getSwitchId() {
        return switchId;
    }

    public void setSwitchId(int switchId) {
        this.switchId = switchId;
    }

    @XmlAttribute(name = "name")
    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    @XmlAttribute(name = "open")
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public enum Type{
        Admin(0),
        Test(1);
        private final int val;
        Type(int val){
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public static final Map<Integer,Type> types = new HashMap<>();

        static {
            for(Type type:Type.values()){
                types.put(type.getVal(),type);
            }
        }

        public static Type getType(int val){
            return types.get(val);
        }
    }
}
