package com.iss.itreasury.clientcenter.client.bizlogic;
//
import java.rmi.RemoteException;
//
import java.util.*;
import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.clientcenter.client.dao.ClientDao;
import com.iss.itreasury.clientcenter.client.dataentity.*;
import com.iss.itreasury.settlement.account.dao.Sett_ClientDAO;
/**
 * Created 2003-8-15 14:50:59
 * Code generated by the Forte for Java EJB Module
 * @author yfan
 */
public class ClientOperation
{
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this); //
	/**
	 * No argument constructor required by container.
	 */
	public ClientOperation()
	{
	}
	/**
	 * findClient �������пͻ�
	 * ���ݿͻ���Ų������пͻ������ؿͻ���ϸ����
	 * ����Client���ݱ�
	 * ��ѯ��¼
	 * haoning
	 * @param lClientID String  �ͻ���ŵ�ID
	 * @return ClientInfo  ��ϸ�Ŀͻ���Ϣ
	 * @throws RemoteException`
	 */
	public ClientInfo findClientByID(long lClientID) 
    throws Exception
	{ 
        ClientDao dao = new ClientDao();
		
		return dao.findClientByID(lClientID);
		
	}
	/**
	 * �������޸ģ��ͻ���ϸ����
	 * saveClientInfo  �������޸ģ��ͻ�����ϸ����
	 * ����Client���ݱ�
	 * ������Ӧ�ֶ�
	 * lID=0,����  lID>0,�޸�
	 * Լ����long�Ͳ���=-1��string�Ͳ���=����,Ϊδʹ����������������޸�
	 *
	 * @param clientinfo �ͻ���Ϣ
	 * ��Ӧ�ֶΣ���������clientinfo���У�
	 * @param lID ��ʶ
	 * @param strClientName   ��˾����
	 * @param strClientNo,    �ͻ����
	 * @param strLicence,     Ӫҵִ��
	 * @param lOfficeID,      ���´�
	 * @param strAccount,     ����˾�˺�
	 * @param strBank,        ��������
	 * @param strAccount      ���������˺�
	 * @param strBank1,       ��������1
	 * @param strAccount1,    �˺�1
	 * @param strBank2,       ��������2
	 * @param strAccount2,    �˺�2
	 * @param strBank3,       ��������3
	 * @param strAccount3,    �˺�3
	 * @param strProvince,    ʡ
	 * @param strCity,        ��
	 * @param strAddress1,    ��ַ1
	 * @param strAddress2     ��ַ2
	 * @param strZipCode,     �ʱ�
	 * @param strDeputy,      ���˴���
	 * @param strTel,         �绰
	 * @param strFax,         ����
	 * @param strMailAddr,    ����
	 * @param strContact,     ��ϵ��
	 * @param strEconomic,    ��������
	 * @param lGovernmentID,  ���ܲ��ű�ʾ
	 * @param isShareHolder,  �Ƿ�ɷ�
	 * @param lClientTypeID,  �ͻ�����
	 * @param lCreditLevel,   ���õȼ�
	 * @param lVentureLevel   ��������
	 * @param strCapital      ע���ʱ�
	 *
	 * @return long �ɹ�����ID��Ϣ��ʧ�ܷ���0
	 * @throws RemoteException
	 */
	public long saveClientInfo(ClientInfo clientinfo) 
    throws Exception,IException
	{
        ClientDao Dao = new ClientDao();
		return Dao.saveClientInfo(clientinfo);
	}
    
    /**
     * ɾ���ͻ�
     *a.. У��ÿͻ��Ƿ��ڿͻ���Ϣ����ģ�������κ��ۺϻ���������Ϣ��
      b.. У��ÿͻ��Ƿ��ڽ���ģ�������κν��׻����˻���Ϣ��
      c.. У��ÿͻ��Ƿ��ڴ���ģ�������κδ���������ߺ�ͬ��
     * @param lClientID
     * @return >0 ɾ���ɹ� <0 ɾ��ʧ��
     * @throws Exception
     */
    public long deleteClientByID(long lClientID) throws Exception
    {
        ClientDao Dao = new ClientDao();
        return Dao.deleteClientByID(lClientID);
    }
    
	public static void main(String args[])
	{
        ClientOperation lcs = new ClientOperation();
		ClientInfo cInfo = null;
		cInfo = new ClientInfo();
		cInfo.setClientID(0);
		cInfo.setName("qqtest");
		cInfo.setLicenceCode("nlicencecode");
		cInfo.setOfficeID(Long.valueOf("01").longValue());
		try
		{
			lcs.saveClientInfo(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}