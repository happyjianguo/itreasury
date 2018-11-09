package  com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity;

/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 12:40:49)
 * @author：Administrator
 */
import java.sql.Timestamp;
import java.util.*;
public class ClientOtherShareInfo implements java.io.Serializable
{
    /**
     * ClientOtherShareInfo 构造子注解。
     */
    public ClientOtherShareInfo()
    {
        super();
    }

    private long ClientID=-1;
    private String ClientName="";
    private float Scale =0;
    private String CardNo="";
    private String Pwd="";
    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getCardNo()
    {
        return CardNo;
    }

    /**
     * @param 
     * function 得到/设置
     * return long
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param 
     * function 得到/设置
     * return String
     */
    public String getPwd()
    {
        return Pwd;
    }

    /**
     * @param 
     * function 得到/设置
     * return float
     */
    public float getScale()
    {
        return Scale;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setCardNo(String string)
    {
        CardNo = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientID(long l)
    {
        ClientID = l;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setClientName(String string)
    {
        ClientName = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setPwd(String string)
    {
        Pwd = string;
    }

    /**
     * @param 
     * function 得到/设置
     * return void
     */
    public void setScale(float f)
    {
        Scale = f;
    }

}
