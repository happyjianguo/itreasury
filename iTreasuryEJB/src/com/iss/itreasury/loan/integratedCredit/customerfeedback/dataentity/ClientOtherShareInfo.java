package  com.iss.itreasury.loan.integratedCredit.customerfeedback.dataentity;

/**
 * �˴���������˵����
 * �������ڣ�(2002-1-15 12:40:49)
 * @author��Administrator
 */
import java.sql.Timestamp;
import java.util.*;
public class ClientOtherShareInfo implements java.io.Serializable
{
    /**
     * ClientOtherShareInfo ������ע�⡣
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
     * function �õ�/����
     * return String
     */
    public String getCardNo()
    {
        return CardNo;
    }

    /**
     * @param 
     * function �õ�/����
     * return long
     */
    public long getClientID()
    {
        return ClientID;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getClientName()
    {
        return ClientName;
    }

    /**
     * @param 
     * function �õ�/����
     * return String
     */
    public String getPwd()
    {
        return Pwd;
    }

    /**
     * @param 
     * function �õ�/����
     * return float
     */
    public float getScale()
    {
        return Scale;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setCardNo(String string)
    {
        CardNo = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setClientID(long l)
    {
        ClientID = l;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setClientName(String string)
    {
        ClientName = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setPwd(String string)
    {
        Pwd = string;
    }

    /**
     * @param 
     * function �õ�/����
     * return void
     */
    public void setScale(float f)
    {
        Scale = f;
    }

}
