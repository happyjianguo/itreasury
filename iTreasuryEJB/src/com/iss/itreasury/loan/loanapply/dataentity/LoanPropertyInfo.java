package com.iss.itreasury.loan.loanapply.dataentity;

/**
 * <p>Title: iTreasury </p> 
 * <p>Description: ����������Ϣ</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iSoftstone</p>
 * @Author: gump
 * @version 1.0
 * @Date: 2003-09-25
 * �������ֶα���Ϊ��������Ϣ
 * isCircle         long        �Ƿ�ѭ��
 * isSaleBuy        long        �Ƿ���������
 * isTechnical      long        �Ƿ񼼸Ĵ���
 * isCredit         long        �Ƿ����ñ�֤
 * isAssure         long        �Ƿ񵣱�
 * isImpawn         long        �Ƿ���Ѻ
 * isPledge         long        �Ƿ��Ѻ
 */

import java.io.Serializable;

public class LoanPropertyInfo implements Serializable
{
    private long        loanID=-1;          //��ˮ��
    private long        loanType=-1;        //��������
    private long        isCircle=0;         //�Ƿ�ѭ��
    private long        isSaleBuy=-1;       //�Ƿ���������
    private long        isTechnical=0;      //�Ƿ񼼸Ĵ���
    private long        isCredit=0;         //�Ƿ����ñ�֤
    private long        isAssure=0;         //�Ƿ񵣱�
    private long        isImpawn=0;         //�Ƿ���Ѻ
    private long        isPledge=0;         //�Ƿ��Ѻ
    private long        isRecognizance=0;	//�Ƿ�֤��
	private long        isRepurchase=0;	    //�Ƿ�ع�
    
    /**
     * ���ô����������ˮ��
     * @param loanID long,�����������ˮ��
     */
    public void setLoanID(long loanID)
    {
        this.loanID=loanID;
    }
    /**
     * ��ȡ�����������ˮ��
     * @return long �����������ˮ��
     */
    public long getLoanID()
    {
        return loanID;
    }
    public void setLoanType(long loanType)
    {   
        this.loanType=loanType;
    }
    public long getLoanType()
    {
        return this.loanType;
    }
    /**
     * �����Ƿ�ѭ��
     * @param isCircle long 
     */
    public void setIsCircle(long isCircle)
    {
        this.isCircle=isCircle;   
    }
    
    /**
     * ��ȡ�Ƿ�ѭ��
     * @return long
     */
    public long getIsCircle()
    {
        return this.isCircle;
    }
    
    /**
     * �����Ƿ���������
     * @param isSaleBuy long 
     */
    public void setIsSaleBuy(long isSaleBuy)
    {
        this.isSaleBuy=isSaleBuy;
    }
    
    /**
     * ��ȡ�Ƿ���������
     * @return long
     */
    public long getIsSaleBuy()
    {
        return this.isSaleBuy;
    }
    
    /**
     * �����Ƿ񼼸Ĵ���
     * @param isTechnical long
     */
    public void setIsTechnical(long isTechnical)
    {
        this.isTechnical=isTechnical;
    }
    
    /**
     * ��ȡ�Ƿ񼼸Ĵ���
     * @return long
     */
    public long getIsTechnical()
    {
        return this.isTechnical;
    }
    

    /**
     * �����Ƿ����ñ�֤
     * @param isCredit long
     */
    public void setIsCredit(long isCredit)
    {
        this.isCredit=isCredit;
    }
    
    /**
     * ��ȡ�Ƿ����ñ�֤
     * @return long
     */
    public long getIsCredit()
    {
        return this.isCredit;
    }
    
    /**
     * �����Ƿ񵣱�
     * @param isAssure long
     */
    public void setIsAssure(long isAssure)
    {
        this.isAssure=isAssure;
    }
    
    /**
     * ��ȡ�Ƿ񵣱�
     * @return long
     */
    public long getIsAssure()
    {
        return this.isAssure;
    }
    
    /**
     * �����Ƿ���Ѻ
     * @param isImpawn long
     */
    public void setIsImpawn(long isImpawn)
    {
        this.isImpawn=isImpawn;
    }
    
    /**
     * ��ȡ�Ƿ���Ѻ
     * @return long
     */
    public long getIsImpawn()
    {
        return this.isImpawn;
    }
    
    /**
     * �����Ƿ��Ѻ
     * @param isPledge long
     */
    public void setIsPledge(long isPledge)
    {
        this.isPledge=isPledge;
    }
    
    /**
     * ��ȡ�Ƿ��Ѻ
     * @return long
     */
    public long getIsPledge()
    {
        return this.isPledge;
    }
    
    /**
     * @return Returns the isRecognizance.
     */
    public long getIsRecognizance()
    {
        return isRecognizance;
    }
    
    /**
     * @param isRecognizance The isRecognizance to set.
     */
    public void setIsRecognizance(long isRecognizance)
    {
        this.isRecognizance = isRecognizance;
    }
    
	public static void main(String[] args)
	{
	}
	/**
	 * @return
	 */
	public long getIsRepurchase() {
		return isRepurchase;
	}

	/**
	 * @param l
	 */
	public void setIsRepurchase(long l) {
		isRepurchase = l;
	}

}
