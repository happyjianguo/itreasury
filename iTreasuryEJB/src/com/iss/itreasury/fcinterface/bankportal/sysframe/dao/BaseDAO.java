/*
 * Created on 2005-5-10
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.fcinterface.bankportal.sysframe.dao;

import java.util.Collection;

import com.iss.itreasury.fcinterface.bankportal.sysframe.dataentity.BaseDataEntity;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;

/**
 * @author mxzhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface BaseDAO
{
	public long geSequenceID() throws SystemException;
	public long getMaxID() throws SystemException;

	/**
	 * ���ݿ�����������������ID����������set����ȷ����ֵ
	 * 
	 * @param dataEntity
	 *            ��Ҫ���������ݿ���Ӧ��Data Entity��ʵ��
	 * @param
	 * @return �²�����ID
	 * @throws ITreasuryDAOException
	 */
	public long add(BaseDataEntity dataEntity) throws SystemException;

	/**
	 * ���ݿ���²���
	 * 
	 * @param dataEntity ��Ҫ�����µ����ݿ���Ӧ��Data Entity��ʵ��
	 * @param
	 * @return @throws ITreasuryDAOException
	 */
	public void update(BaseDataEntity dataEntity) throws SystemException;
	
	/**
	 * ���ݿ���Ҳ���
	 * @param id������
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public BaseDataEntity findByID(long id, Class className) throws SystemException;

	/**
	 * ���ݿ���Ҳ���
	 * @param conditionInfo ��ѯ�����������set��ֵ������Ϊ���ֶ�Ϊ��ѯ��������ƥ�䡡����
	 * @param className ��Ҫ���ҵ����ݿ���Ӧ��Data Entity������
	 * @param orderByString order by SQL��䣬�ֹ����
	 * @return
	 * @throws ITreasuryDAOException
	 */			
	public Collection findByCondition(BaseDataEntity conditionInfo, String orderByString) throws SystemException;
	
	/**
	 * �������ݿ��е����м�¼
	 * @param orderByString
	 * @return
	 * @throws SystemException
	 */
	public Collection findAll(Class baseDataEntityClass, String orderByString) throws SystemException;
	
	/**
	 * �����ݿ��и���id����ɾ��һ����¼
	 * ʹ��ʱ��ȷ�����߼�ɾ����������ɾ������
	 * */
	public void deletePhysically(long id) throws SystemException;
	
	/**
	 * ɾ�����ݿ������еļ�¼
	 * @throws SystemException
	 */
	public void deleteAll() throws SystemException;
}
