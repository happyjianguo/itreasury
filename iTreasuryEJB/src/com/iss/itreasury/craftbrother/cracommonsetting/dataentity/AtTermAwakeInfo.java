/*
 * atTermAwakeInfo.java
 *
 * Created on 2002��3��14��, ����9:14
 */

package com.iss.itreasury.craftbrother.cracommonsetting.dataentity;

import java.beans.*;

/**
 *
 * @author  yzhang
 * @version
 */
public class AtTermAwakeInfo implements java.io.Serializable {


    /** Creates new atTermAwakeInfo */
    public AtTermAwakeInfo() {
        super();
    }

    private long OfficeID;               //���´�ID

    private long AwakeTypeID;            //��������ID

    private long AheadDays=0;            //��ǰ��������

    private long AwakeDays=0;            //���Ѷ�����

    public long getAheadDays()
    {
      return AheadDays;
    }

    public long getAwakeDays()
    {
      return AwakeDays;
    }

    public long getAwakeTypeID()
    {
      return AwakeTypeID;
    }

    public long getOfficeID()
    {
      return OfficeID;
    }

    public void setAheadDays(long AheadDays)
    {
      this.AheadDays = AheadDays;
    }

    public void setAwakeDays(long AwakeDays)
    {
      this.AwakeDays = AwakeDays;
    }

    public void setAwakeTypeID(long AwakeTypeID)
    {
      this.AwakeTypeID = AwakeTypeID;
    }

    public void setOfficeID(long OfficeID)
    {
      this.OfficeID = OfficeID;
    }


    /**private long zydqAheadDays;                //��Ӫ���ڴ�����ǰ��������
    private long zydqAwakeDays;                  //��Ӫ���ڴ������Ѽ���

    private long zyzcqAheadDays;                 //��Ӫ�г�����ǰ��������
    private long zyzcqAwakeDays;                 //��Ӫ�г��ڴ������Ѽ���

    private long wtAheadDays;                    //ί�д�����ǰ��������
    private long wtAwakeDays;                    //ί�д������Ѽ���

    private long wttjthAheadDays;                //ί��ͳ��ͳ��
    private long wttjthAwakeDays;

    private long zgxedqAheadDays;                //����޶����
    private long zgxedqAwakeDays;

    private long zgxezcqAheadDays;               //����޶��г���
    private long zgxezcqAwakeDays;

    private long ytAheadDays;                    //����
    private long ytAwakeDays;

    private long dklvtzAheadDays;                //�������ʵ���
    private long dklvtzAwakeDays;*/







}
