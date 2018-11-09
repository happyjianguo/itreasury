package com.iss.itreasury.settlement.transspecial.dataentity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.iss.sysframe.base.dataentity.BaseDataEntity;

public class QueryBySubSpecialTypeAndStatusInfo extends BaseDataEntity implements Serializable
{
	
	private long Transactiontypeid=-1;//��������---SETTConstant.TransactionType.SPECIALOPERATION�����⽻�����ͣ�
	private  long SubTransactiontypeid=-1;//���⽻��������
	
	private long[] StatusIDs=null;

    private long OfficeID = -1;//���´�ID
    private long CurrencyID = -1;//����ID
    private long UserID = -1;//�û�ID
    private long TypeID = -1;// ��ѯ���ͣ�0��������Ĳ��ң���1�������˵Ĳ��ң�
    //����״̬
    private Timestamp Date = null; //��ѯ����
    
    public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	/**
     * @return
     */
    public long getCurrencyID()
    {
            return CurrencyID;
    }

    /**
     * @return
     */
    public Timestamp getDate()
    {
            return Date;
    }

    /**
     * @return
     */
    public long getOfficeID()
    {
            return OfficeID;
    }



    /**
     * @return
     */
    public long getTypeID()
    {
            return TypeID;
    }

    /**
     * @return
     */
    public long getUserID()
    {
            return UserID;
    }

    /**
     * @param l
     */
    public void setCurrencyID(long l)
    {
            CurrencyID = l;
    }

    /**
     * @param timestamp
     */
    public void setDate(Timestamp timestamp)
    {
            Date = timestamp;
    }

    /**
     * @param l
     */
    public void setOfficeID(long l)
    {
            OfficeID = l;
    }

 

    /**
     * @param l
     */
    public void setTypeID(long l)
    {
            TypeID = l;
    }

    /**
     * @param l
     */
    public void setUserID(long l)
    {
            UserID = l;
    }

/**
 * @return
 */
public long[] getStatusIDs() {
	return StatusIDs;
}

/**
 * @param i
 */
public void setStatusIDs(long[] i) {
	if(i!=null){
		StatusIDs=new long[i.length];
		int j=0;
		while(j<i.length){
			StatusIDs[j]=i[j];
			j++;
		}
	}		
}

/**
 * @return the transactiontypeid
 */
public long getTransactiontypeid() {
	return Transactiontypeid;
}

/**
 * @param transactiontypeid the transactiontypeid to set
 */
public void setTransactiontypeid(long transactiontypeid) {
	this.Transactiontypeid = transactiontypeid;
}

/**
 * @return the subTransactiontypeid
 */
public long getSubTransactiontypeid() {
	return SubTransactiontypeid;
}

/**
 * @param subTransactiontypeid the subTransactiontypeid to set
 */
public void setSubTransactiontypeid(long subTransactiontypeid) {
	this.SubTransactiontypeid = subTransactiontypeid;
}

}
