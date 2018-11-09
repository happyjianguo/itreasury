/* Generated by Together */

package com.iss.itreasury.bill.bizdelegation;

import java.util.Collection;

import com.iss.itreasury.bill.billtype.bizlogic.BillTypeBean;
import com.iss.itreasury.bill.billtype.dataentity.BillTypeInfo;
import com.iss.itreasury.bill.billtype.dataentity.BillTypeQueryInfo;
import com.iss.itreasury.bill.util.BillException;

public class BillTypeDelegation {

	private BillTypeBean facade = null;
    
	public BillTypeDelegation()
	{
		facade = new BillTypeBean();
	}
	
    /**
     * ��������������Ϣ������������ ����BillTypeBean����Ӧ����
     * @param abstractTypeID Ʊ������id
     * @return Collection�еĶ���ΪBillTypeInfo
     * @throws BillException
     */
    public Collection findByAbstractType(long abstractTypeID) throws BillException {
       
		return facade.findByAbstractType(abstractTypeID);
    }

    /**
     *���ݲ�ѯ������ѯƱ������ ����BillTypeBean����Ӧ����
     * @param qInfo ��ѯ������Ϣ
     * @return Collection�еĶ���ΪBillTypeInfo
     * @throws BillException
     */
    public Collection findByMultiOption(BillTypeQueryInfo qInfo) throws BillException {
		
		return facade.findByMultiOption(qInfo);
    }

    /**
     * ����Ʊ��������Ϣ ����BillTypeBean����Ӧ����
     * @param BillTypeInfo	������Ϣ
     * @return long ������������Ϣ��ID
     * @throws BillException
     */
    public long add(BillTypeInfo info) throws BillException {
    	
    	long id = -1;
    	
		id = facade.add(info);
		
		return id;
    }

    /**
     * �޸�Ʊ��������Ϣ ����BillTypeBean����Ӧ����
     * @param BillTypeinfo	������Ϣ
     * @return void
     * @throws BillException
     */
    public long update(BillTypeInfo info) throws BillException {
    	
		long id = -1;

		id = facade.update(info);
		
		return id;
    }

    /**
     * ����idɾ��������Ϣ ����BillTypeBean����Ӧ����
     * @param id	����id
     * @return void
     * @throws BillException
     */
    public void delete(long id) throws BillException {
    	
		facade.delete(id);
    }

    /**
     * ����id����������Ϣ ����BillTypeBean����Ӧ����
     * @param id	����id
     * @return BillTypeInfo
     * @throws BillException
     */
    public BillTypeInfo findByID(long id) throws BillException {
    	
		BillTypeInfo info = new BillTypeInfo();
		
		info = facade.findByID(id);
			
		return info;
    }


	/**
	 * ����id����������Ϣ ����BillTypeBean����Ӧ����
	 * @param id	����id
	 * @return BillTypeInfo
	 * @throws BillException
	 */
	public long findMaxBillTypeCode() throws BillException {
		
    	long code = 0;
    	
		code=facade.findMaxBillTypeCode(); 	
			
		return code;
	}    
    
    
}