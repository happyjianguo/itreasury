/*
 * Created on 2005-8-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.loan.credit.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author weihuang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckReportQueryInfo extends ITreasuryBaseDataEntity{
    private long id = -1;
    private long startClientID = -1; //�ͻ��ſ�ʼ
    private long endClientID = -1; //�ͻ��Ž���
    private String startClientCode = ""; //�ͻ��ſ�ʼ
    private String endClientCode = ""; //�ͻ��Ž���
    private double startAmount = 0; //�����ʼ
    private double endAmount = 0; //���������
    private String startReportCode="";//��鱨���ſ�ʼ
    private String endReportCode="";//��鱨���Ž���
    private long statusID = -1; //״̬
    private String startInputDate = null; //¼�����ڿ�ʼ
    private String endInputDate = null; //¼�����ڽ���
    private long clientID = -1;

    private String orderParamString = "";
    private long desc = -1;
    
    private long officeId=-1; //added by mzh_fu 2007/03/22 �����ѯʱδ���ְ��´�������
   
    /**
     * @return
     */
    public long getId() {
      return id;
    }

    /**
     * @param l
     */
    public void setId(long l) {
      id = l;
    }

   
    /**
     * @return
     */
    public long getDesc() {
      return desc;
    }

    /**
     * @return
     */
    public String getOrderParamString() {
      return orderParamString;
    }

    /**
     * @param l
     */
    public void setDesc(long l) {
      desc = l;
    }

    /**
     * @param string
     */
    public void setOrderParamString(String string) {
      orderParamString = string;
    }

    /**
     * @return
     */
    public long getEndClientID() {
      return endClientID;
    }

    /**
     * @return
     */
    public double getEndAmount() {
      return endAmount;
    }

    /**
     * @return
     */
    public long getStartClientID() {
      return startClientID;
    }

    /**
     * @return
     */
    public double getStartAmount() {
      return startAmount;
    }

    /**
     * @param l
     */
    public void setEndClientID(long l) {
      endClientID = l;
    }

    /**
     * @param d
     */
    public void setEndAmount(double d) {
      endAmount = d;
    }

    /**
     * @param l
     */
    public void setStartClientID(long l) {
      startClientID = l;
    }

    /**
     * @param d
     */
    public void setStartAmount(double d) {
      startAmount = d;
    }



////////////////////////////////////////////////////////  //
    /**
     * @return
     */
    public long getStatusID() {
      return statusID;
    }

    /**
     * @param l
     */
    public void setStatusID(long l) {
      statusID = l;
    }

    /**
     * @return
     */
    public String getEndInputDate() {
      return endInputDate;
    }

    /**
     * @return
     */
    public String getStartInputDate() {
      return startInputDate;
    }

    /**
     * @param string
     */
    public void setEndInputDate(String string) {
      endInputDate = string;
    }

    /**
     * @param string
     */
    public void setStartInputDate(String string) {
      startInputDate = string;
    }

    /**
     * @return
     */
    public long getClientID() {
      return clientID;
    }

    /**
     * @param l
     */
    public void setClientID(long l) {
      clientID = l;
    }

    

    /**
     * @return Returns the endCode.
     */
    public String getEndReportCode() {
        return endReportCode;
    }
    /**
     * @param endCode The endCode to set.
     */
    public void setEndReportCode(String endCode) {
        this.endReportCode = endCode;
    }
    /**
     * @return Returns the startCode.
     */
    public String getStartReportCode() {
        return startReportCode;
    }
    /**
     * @param startCode The startCode to set.
     */
    public void setStartReportCode(String startCode) {
        this.startReportCode = startCode;
    }
    /**
     * @return Returns the endClientCode.
     */
    public String getEndClientCode() {
        return endClientCode;
    }
    /**
     * @param endClientCode The endClientCode to set.
     */
    public void setEndClientCode(String endClientCode) {
        this.endClientCode = endClientCode;
    }
    /**
     * @return Returns the startClientCode.
     */
    public String getStartClientCode() {
        return startClientCode;
    }
    /**
     * @param startClientCode The startClientCode to set.
     */
    public void setStartClientCode(String startClientCode) {
        this.startClientCode = startClientCode;
    }

	public long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
  }