/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transspecial.dataentity;
import java.io.Serializable;
import java.sql.Timestamp;
/**
 * ���⽻�׵İ�״̬��ѯ����ʵ���ࣺ
 * 1�����б�����ΪPrivate,����ֻ����setXXX�������õ�ֻ����getXXX������
 * 2���������������͡�Ĭ��ֵ��˵��
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryByStatusConditionInfo implements Serializable
{
		private long[] StatusIDs=null;

        private long OfficeID = -1;//���´�ID
        private long CurrencyID = -1;//����ID
        private long UserID = -1;//�û�ID
        private long TypeID = -1;// ��ѯ���ͣ�0��������Ĳ��ң���1�������˵Ĳ��ң�
        //����״̬
        private Timestamp Date = null; //��ѯ����
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

}
