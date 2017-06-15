package com.game.load.xml;

import com.lgame.util.load.demo.xml.Root;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by leroy:656515489@qq.com
 * 2017/6/15.
 */
@XmlRootElement(name = "root")
public class ErrorCode {
    private List<ErrorCode> errorCode;
    private int code;
    private String message;

    @XmlElement(name = "code")
    public List<ErrorCode> getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(List<ErrorCode> errorCode) {
        this.errorCode = errorCode;
    }

    @XmlAttribute(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @XmlAttribute(name = "tip")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
