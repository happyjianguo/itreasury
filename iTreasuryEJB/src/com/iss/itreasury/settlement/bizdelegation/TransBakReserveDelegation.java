package com.iss.itreasury.settlement.bizdelegation;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;

import com.iss.itreasury.settlement.transbakreserve.bizlogic.TransBakReserve;
import com.iss.itreasury.settlement.transbakreserve.bizlogic.TransBakReserveHome;
import com.iss.itreasury.settlement.transbakreserve.dataentity.TransBakReserveInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.system.approval.dataentity.InutParameterInfo;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IRollbackException;

public class TransBakReserveDelegation {
	
	private TransBakReserve transBakReserveFacade;

	public TransBakReserveDelegation() throws RemoteException, IRollbackException
	{
		try
		{

			TransBakReserveHome home =
				(TransBakReserveHome) EJBHomeFactory.getFactory().lookUpHome(TransBakReserveHome.class);
			transBakReserveFacade = (TransBakReserve) home.create();
		}
		catch (RemoteException e)
		{
			throw e;
		}
		catch (IException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
		catch (CreateException e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}

	}
	
	/**
	 * Method preSave.
	 * @param TransBakReserveInfo
	 * @return int 0: ���� 1: ί��ƾ֤������--��ʾ���� 2: Ʊ�ݲ����� 3: �ظ����׼�¼ 4: �˻�͸֧
	 * @throws RemoteException, IRollbackException
	 */
	public long preSave(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
	    return transBakReserveFacade.preSave(info);

	}
	/**
	 * Method tempSave.
	 * @param TransBakReserveInfo
	 * @return long
	 * @throws  RemoteException, IRollbackException
	 */
	public long tempSave(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.tempSave(info);
	}
	
	/**
	 * Method findByConditions.
	 * @param1 TransBakReserveInfo   
	 * @param2 isMatch �Ƿ���ƥ�����
	 * @return Collection ������������
	 * @throws  RemoteException, IRollbackException
	 */
	public Collection findByConditions(TransBakReserveInfo info, int orderByType, boolean isDesc) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.findByConditions(info, orderByType, isDesc);

	}
	/**
	 * Method saveUpreceive.�ܲ�-����������-����
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long ����ļ�¼ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.saveUpreceive(info,pInfo);

	}
	/**
	 * Method saveDownReturn.�ܲ�-���������-����
	 * @param1 TransBakReserveInfo  
	 * @param2 InutParameterInfo  
	 * @return long ����ļ�¼ID
	 * @throws RemoteException,IRollbackException
	 */
	public long saveDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.saveDownReturn(info);

	}
	/**
	 * Method deleteUpreceive.�ܲ�-����������-ɾ��
	 * @param TransBakReserveInfo
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.deleteUpreceive(info);

	}
	
	/**
	 * Method deleteDownReturn.�ܲ�-���������-ɾ��
	 * @param TransBakReserveInfo
	 * @return long ɾ���ļ�¼ID
	 * @throws RemoteException,IRollbackException
	 */
	public long deleteDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.deleteDownReturn(info);

	}
	
	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  matchUpreceive.�ܲ�-����������-����ƥ��
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/
	public TransBakReserveInfo matchUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.matchUpreceive(info);

	}	
	/**
	* ����˵�������ݲ�ѯ����ƥ��
	*  Method  matchUpreceive.�ܲ�-���������-����ƥ��
	* @param TransBakReserveInfo info
	* @return TransBakReserveInfo
	* @throws RemoteException,IRollbackException
	*/
	public TransBakReserveInfo matchDownReturn(TransBakReserveInfo info)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.matchDownReturn(info);

	}	
	/**
	  * ����˵�������ݼ�¼ID���и��ˣ��ܲ�-����������-���ˣ�
	 * @param TransBakReserveInfo
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.checkUpreceive(info);
		return res;
	}
	/**
	  * ����˵�������ݼ�¼ID���и��ˣ��ܲ�-���������-���ˣ�
	 * @param TransBakReserveInfo
	 * @return long ���˵ļ�¼ID
	 * @throws RemoteException, IRollbackException
	 */
	public long checkDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.checkDownReturn(info);
		return res;
	}
	/**
	 * ����˵�������ݼ�¼ID����ȡ�����ˣ��ܲ�-����������-ȡ�����ˣ�
	 * @param TransBakReserveInfo
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckUpreceive(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.cancelCheckUpreceive(info);
		return res;
	}

	/**
	 * ����˵�������ݼ�¼ID����ȡ�����ˣ��ܲ�-���������-ȡ�����ˣ�
	 * @param TransBakReserveInfo
	 * @return long ȡ�����˵ļ�¼ID
	 * @throws RemoteException, IRollbackException
	 */
	public long cancelCheckDownReturn(TransBakReserveInfo info) throws RemoteException, IRollbackException
	{
		long res = transBakReserveFacade.cancelCheckDownReturn(info);
		return res;
	}

	/**
	 * ����˵���������˻�ID���õ��˻���Ϣ
	 * @param transID
	 * @return TransBakReserveInfo
	 * @throws RemoteException,IRollbackException
	 */
	public TransBakReserveInfo findByID(long transID) throws RemoteException, IRollbackException
	{
		return transBakReserveFacade.findByID(transID);

	}

	
	/**
	* ����˵���� �ܲ�-����������-������û��ʵ�֣������ݲ�ֱ��������
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return ������¼id
	* @throws RemoteException, IRollbackException
	*/
	public long doApprovalUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo) throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.doApproval(info);

	}	
	
	/**
	* ����˵���� �ܲ�-���������-������û��ʵ�֣������ݲ�ֱ��������
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return ������¼id
	* @throws RemoteException, IRollbackException
	*/
	public long doApprovalDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.doApproval(info);

	}
	/**
	* ����˵���� �ܲ�-����������-ȡ��������û��ʵ�֣������ݲ�ֱ��������
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return ������¼id
	* @throws RemoteException, IRollbackException
	*/
	public long cancelApprovalUpreceive(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.cancelApproval(info);

	}

	/**
	* ����˵���� �ܲ�-���������-ȡ��������û��ʵ�֣������ݲ�ֱ��������
	*  Method  doApprovalUpreceive.
	* @param TransBakReserveInfo info InutParameterInfo pInfo
	* @return ������¼id
	* @throws RemoteException, IRollbackException
	*/
	public long cancelApprovalDownReturn(TransBakReserveInfo info,InutParameterInfo pInfo)
		throws RemoteException, IRollbackException
	{

		return transBakReserveFacade.cancelApproval(info);

	}
}
