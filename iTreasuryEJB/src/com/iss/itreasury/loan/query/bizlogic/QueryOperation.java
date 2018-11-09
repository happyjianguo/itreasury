/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.query.bizlogic;

import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.util.*;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import com.iss.itreasury.loan.query.dataentity.*;
import com.iss.itreasury.loan.query.dao.*;
import com.iss.itreasury.loan.recognizancenotice.dataentity.RecognizanceNoticeInfo;
import com.iss.system.dao.PageLoader;
import com.iss.itreasury.loan.attornmentapply.dataentity.*;
import com.iss.itreasury.loan.credit.dao.*;
import com.iss.itreasury.loan.credit.dataentity.*;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdPayNoticeInfo;
import com.iss.itreasury.loan.leasehold.dataentity.LeaseholdRepayNoticeInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryOperation
{
	private static Log4j log4j = null;

	public PageLoader queryPayNotice(QuestPayNoticeInfo payNInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPayNotice(payNInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	/**
	 * ��ѯ���Ŵ������֪ͨ��
	 * @param drawInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryDrawNotice(QuestLoanDrawNoticeInfo drawInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryDrawNotice(drawInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public String getDiscountCredenceOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getDiscountCredenceOrder(orderParam, desc);
		return orderSQL;
	}
	public PageLoader queryDiscountCredence(QuestCredenceInfo cInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryDiscountCredence(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public PageLoader queryTransDiscountCredence(long lID,long nTypeID) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryTransDiscountCredence(lID,nTypeID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	public String getPayNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getPayNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	public String getDrawNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getDrawNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * �����տ�֪ͨ����ѯ���� ���Ϻ������� added by zntan 2004-12-9
	 * @param orderParam
	 * @param desc
	 * @return
	 */
	public String getAssureChargeNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getAssureChargeNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * �����տ�֪ͨ����ѯ ���Ϻ������� added by zntan 2004-12-9
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAssureChargeNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryAssureChargeNotice(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}
	
	/**
	 * ����������֪ͨ����ѯ���� ���Ϻ������� added by zntan 2004-12-9
	 * @param orderParam
	 * @param desc
	 * @return
	 */
	public String getAssureManagementNoticeOrder(long orderParam, long desc)
	{
		QueryDao dao = new QueryDao();
		String orderSQL = dao.getAssureManagementNoticeOrder(orderParam, desc);
		return orderSQL;
	}
	
	/**
	 * ����������֪ͨ����ѯ ���Ϻ������� added by zntan 2004-12-9
	 * @param lID
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryAssureManagementNotice(QueryAssureNoticeInfo qInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryAssureManagementNotice(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	/**
	 * ִ�к�ִͬ�������ѯ
	 * @param lID ��ͬ��ʾ
	 * @param lParam �����־
	 * @param lDesc �������־
	 * @return 
	 * @throws Exception
	 */
	public Collection queryPerform(long lID, long lParam, long lDesc) throws Exception
	{
		Collection c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPerform(lID, lParam, lDesc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return c;
	}

	/**
	 * ��ѯ��ִͬ������Ļ�����Ϣ
	 * @param lID ��ͬID
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryPerformSum(long lID) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryPerformSum(lID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}

		return sumInfo;
	}

	public PageLoader queryAttornment(AttornmentQueryInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryAttornment(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	public String getAttornmentOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();
		return dao.getAttornmentOrderSQL(lOrder, lDesc);
	}

	/**
	 * ִ�д��������ѯ�Ļ�����Ϣ��ѯ
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryAttornmentSum(AttornmentQueryInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryAttornmentSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}

	/**
	 * ִ�д��������ѯ��ʹ��PageLoader��ʽ��
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryLoanApply(QueryLoanApplyInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryLoanApply(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	public String getLoanOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();
		return dao.getLoanOrderSQL(lOrder, lDesc);
	}

	/**
	 * ִ�д��������ѯ�Ļ�����Ϣ��ѯ
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryLoanApplySum(QueryLoanApplyInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryLoanApplySum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}

	/**
	 * ִ�к�ͬ��ѯ����ʹ��PageLoader��ʽ��
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContract(QuestContractInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryContract(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}
	/**
	 * ִ�к�ͬ���ʲ�ѯ����ʹ��PageLoader��ʽ��
	 * @param cInfo
	 * @return
	 * @throws Exception
	 */
	public PageLoader queryContractRate(QueryBatchWillContractInfo cInfo) throws Exception
	{
		PageLoader loader = null;
		QueryBatchWillContractDao dao = new QueryBatchWillContractDao();

		try
		{
			loader = dao.queryContractRate(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}
	
	/**
	 * ִ�к�ͬ���ʵ�����ѯ����ʹ��PageLoader��ʽ��
	 * @author ���ָ�
	 * @param adjustQueryInfo
	 * @return PageLoader
	 * @throws Exception
	 */
	public PageLoader queryContractAdjustInfo(ContractInfoAdjustQuery adjustQueryInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryContractAdjustInfo(adjustQueryInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	/**
	 * 
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getContractOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getContractOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * ����������򷽷����ſ�֪ͨ����
	 * @author ���ָ�
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getPayFormOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getPayFormOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * ����������򷽷���չ�����룩
	 * @param lOrder
	 * @param lDesc
	 * @return
	 */
	public String getExtendOrderSQL(long lOrder, long lDesc)
	{
		QueryDao dao = new QueryDao();

		return (dao.getExtendOrderSQL(lOrder, lDesc));
	}
	
	/**
	 * ִ�к�ͬ��ѯ�Ļ�����Ϣ��ѯ
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryContractSum(QuestContractInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.QueryContractSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}
	
	/**
	 * ִ�к�ͬƾ֤��ѯ�Ļ�����Ϣ��ѯ
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public QuerySumInfo queryDiscountCredenceSum(QuestCredenceInfo cInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryDiscountCredenceSum(cInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;

	}
	
	

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QueryDao
	 * function
	 */
	public PageLoader queryContractPlan(QuestContractPlanInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryQuestContractPlanInfo(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	/**
	 * ִ�к�ִͬ�мƻ��Ĳ�ѯ���˷���ֻ�ں�ͬ��ϸ��Ϣ�鿴ִ�мƻ�ʱʹ�á�
	 * @param qInfo
	 * @return
	 * @throws Exception
	 */
	public Collection queryContractPlanResult(QuestContractPlanInfo qInfo) throws Exception
	{
		QueryDao dao = new QueryDao();
		Collection c = null;

		try
		{
			c = dao.queryContractPlanResult(qInfo);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public PageLoader queryExtend(QuestExtendInfo qInfo) throws Exception
	{
		PageLoader loader = null;
		QueryDao dao = new QueryDao();

		try
		{
			loader = dao.queryQuestExtendInfo(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return loader;
	}

	
	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public QuerySumInfo queryExtendSum(QuestExtendInfo qInfo) throws Exception
	{
		QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.QueryExtendSum(qInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	}

	/*
	 * 
	 * @author haoning
	 * @time 2003-11-20
	 * @param QuestExtendInfo
	 * function
	 */
	public QueryMyJobCountInfo queryMyJobCount(long lUserID, long lOfficeID, long lCurrencyID) throws Exception
	{
		long lStatusID = -1;
		QueryMyJobCountInfo CountInfo = new QueryMyJobCountInfo();
		QueryDao dao = new QueryDao();

		try
		{
			//=====================================����״̬===========================//
			Log.print("//============================����״̬==========================//");
			//===================׫д==================//
			Log.print("//-----------------------------׫д----------------------");
			lStatusID = 0;
			{
				//��������
				CountInfo.setLoanApplySaveCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF))
				{
					//��������
					CountInfo.setDiscountApplySaveCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					CountInfo.setTransDiscountApplySaveCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//========ninh 2004-06-25======//        
					//��˴���ת�����롪��׫д
					CountInfo.setAttornmentApplySaveCount(
					        dao.QueryAttornmentApplyCount(lUserID,lStatusID,lOfficeID,lCurrencyID)
					        );
				}
				//*/
				//�ô������������� ���� ��Ŀ�ж� ninh 2004-11-08
					//��������
					CountInfo.setDiscountApplySaveCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					CountInfo.setTransDiscountApplySaveCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));

			}
			//===================���ύ================//
			Log.print("//------------------------------���ύ--------------------------//");
			lStatusID = 1;
			{
				//�������롪�����ύ
				CountInfo.setLoanApplySubmitCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//�ſ�֪ͨ���������ύ
				CountInfo.setPaySubmitCount(dao.QueryPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//���ڴ��������ύ
				CountInfo.setOverDueSubmitCount(dao.QueryOverDueCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//��ִͬ�мƻ��������ύ
				CountInfo.setContractPlanSubmitCount(dao.QueryContractPlanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//���ʵ����������ύ
				CountInfo.setRateAdjustSubmitCount(dao.QueryRateAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//��ǰ��������ύ
				CountInfo.setAheadRePaySubmitCount(dao.QueryAheadRePayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //����û�У������е�
				{
					//չ�����롪�����ύ
					CountInfo.setExtendApplySubmitCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�⻹���롪�����ύ
					CountInfo.setFreeApplySubmitCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������롪�����ύ
					CountInfo.setDiscountApplySubmitCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//����ƾ֤�������ύ
					CountInfo.setDiscountCredenceSubmitCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת�������롪�����ύ
					CountInfo.setTransDiscountApplySubmitCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//ת����ƾ֤���롪�����ύ
					CountInfo.setTransDiscountCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//�ع�ƾ֤���롪�����ύ
					CountInfo.setRepurchaseCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//����״̬����������ύ
					CountInfo.setRiskStatusAdjustSubmitCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//��ͬ״̬����������ύ
					CountInfo.setContractStatusAdjustSubmitCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ŷſ�֪ͨ���������ύ
					CountInfo.setYTPaySubmitCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������֪ͨ���������ύ
					CountInfo.setYTDrawNoticeSubmitCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//========ninh 2004-06-25======//        
					//��˴���ת�����롪�����ύ
					CountInfo.setAttornmentApplySubmitCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				}//*/
				//�ô������������� ���� ��Ŀ�ж� ninh 2004-11-08
					//չ�����롪�����ύ
					CountInfo.setExtendApplySubmitCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//�⻹���롪�����ύ
					CountInfo.setFreeApplySubmitCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//�������롪�����ύ
					CountInfo.setDiscountApplySubmitCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//����ƾ֤�������ύ
					CountInfo.setDiscountCredenceSubmitCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//ת�������롪�����ύ
					CountInfo.setTransDiscountApplySubmitCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//ת����ƾ֤���롪�����ύ
					CountInfo.setTransDiscountCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//�ع�ƾ֤���롪�����ύ
					CountInfo.setRepurchaseCredenceSubmitCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//����״̬����������ύ
					CountInfo.setRiskStatusAdjustSubmitCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//��ͬ״̬����������ύ
					CountInfo.setContractStatusAdjustSubmitCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ŷſ�֪ͨ���������ύ
					CountInfo.setYTPaySubmitCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������֪ͨ���������ύ
					CountInfo.setYTDrawNoticeSubmitCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//��˴���ת�����롪�����ύ
					CountInfo.setAttornmentApplySubmitCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�����տ�֪ͨ�����������ύ
					CountInfo.setAssureChargeNoticeSubmitCount(dao.queryAssureChargeNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//������֪ͨ��--���ύ
					CountInfo.setAssureManagementNoticeSubmitCount(dao.queryAssureManagementNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

			}
			//=====================�����=====================//
			Log.print("//-----------------------------�����--------------------------");
			lStatusID = 2;
			{
				//�������롪�������
				CountInfo.setLoanApplyForCheckCount(dao.QueryLoanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//�ſ�֪ͨ�����������
				CountInfo.setPayForCheckCount(dao.QueryPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//���ڴ����������
				CountInfo.setOverDueForCheckCount(dao.QueryOverDueCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//��ִͬ�мƻ����������
				CountInfo.setContractPlanForCheckCount(dao.QueryContractPlanCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//���ʵ������������
				CountInfo.setRateAdjustForCheckCount(dao.QueryRateAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				//��ǰ����������
				CountInfo.setAheadRePayForCheckCount(dao.QueryAheadRePayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //����û�У������е�
				{
					//չ�����롪�������        
					CountInfo.setExtendApplyForCheckCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�⻹���롪�������        
					CountInfo.setFreeApplyForCheckCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������롪�������
					CountInfo.setDiscountApplyForCheckCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//����ƾ֤���������
					CountInfo.setDiscountCredenceForCheckCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת�������롪�������
					CountInfo.setTransDiscountApplyForCheckCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//ת����ƾ֤���롪�������
					CountInfo.setTransDiscountCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//�ع�ƾ֤���롪�������
					CountInfo.setRepurchaseCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//����״̬������������
					CountInfo.setRiskStatusAdjustForCheckCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//��ͬ״̬������������
					CountInfo.setContractStatusAdjustForCheckCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ŷſ�֪ͨ�����������
					CountInfo.setYTPayForCheckCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������֪ͨ�����������
					CountInfo.setYTDrawNoticeForCheckCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

					//=====ninh 2004-06-25======//        
					//��˴���ת�����롪�������
					CountInfo.setAttornmentApplyForCheckCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				}
				//*/
				//�ô������������� ���� ��Ŀ�ж� ninh 2004-11-08
					//չ�����롪�������        
					CountInfo.setExtendApplyForCheckCount(dao.QueryExtendApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�⻹���롪�������        
					CountInfo.setFreeApplyForCheckCount(dao.QueryFreeApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������롪�������
					CountInfo.setDiscountApplyForCheckCount(dao.QueryDiscountApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//����ƾ֤���������
					CountInfo.setDiscountCredenceForCheckCount(dao.QueryDiscountCredenceCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת�������롪�������
					CountInfo.setTransDiscountApplyForCheckCount(dao.QueryLoanApplyCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
					//ת����ƾ֤���롪�������
					CountInfo.setTransDiscountCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.TRANSDISCOUNTCREDENCE));					
					//�ع�ƾ֤���롪�������
					CountInfo.setRepurchaseCredenceForCheckCount(dao.QueryCredenceCountByCredenceType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.CredenceType.REPURCHASECREDENCE));					
					//����״̬������������
					CountInfo.setRiskStatusAdjustForCheckCount(dao.QueryRiskStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//��ͬ״̬������������
					CountInfo.setContractStatusAdjustForCheckCount(dao.QueryContractStatusAdjustCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ŷſ�֪ͨ�����������
					CountInfo.setYTPayForCheckCount(dao.QueryYTPayCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�������֪ͨ�����������
					CountInfo.setYTDrawNoticeForCheckCount(dao.QueryDrawNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//��˴���ת�����롪�������
					CountInfo.setAttornmentApplyForCheckCount(dao.QueryAttornmentApplyCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//�����տ�֪ͨ��--�����
					CountInfo.setAssureChargeNoticeForCheckCount(dao.queryAssureChargeNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//������֪ͨ��--�����
					CountInfo.setAssureManagementNoticeForCheckCount(dao.queryAssureManagementNoticeCount(lUserID, lStatusID, lOfficeID, lCurrencyID));

			}
			//=================================��ͬ״̬===============================//
			Log.print("//==============================��ͬ״̬===========================//");
			//====================׫д===================//
			Log.print("//-------------------------׫д----------------------//");
			lStatusID = 0;
			{
				//�����ͬ����׫д-����
				CountInfo.setContractApplySaveCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //����û�У������е�
				{
					//չ�ں�ͬ����׫д-����
					CountInfo.setExtendContractSaveCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ����׫д-����
					CountInfo.setDiscountContractSaveCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ����׫д-����
					CountInfo.setTransDiscountContractSaveCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
				}
				//*/
					//չ�ں�ͬ����׫д-����
					CountInfo.setExtendContractSaveCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ����׫д-����
					CountInfo.setDiscountContractSaveCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ����׫д-����
					CountInfo.setTransDiscountContractSaveCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));
			}
			//===================���ύ==================//
			Log.print("//---------------------------���ύ---------------------//");
			lStatusID = 1;
			{
				//�����ͬ�������ύ-����
				CountInfo.setContractApplySubmitCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //����û�У������е�
				{
					//չ�ں�ͬ�������ύ-����
					CountInfo.setExtendContractSubmitCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ�������ύ-����
					CountInfo.setDiscountContractSubmitCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ�������ύ-����
					CountInfo.setTransDiscountContractSubmitCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));					
				}
				//*/
					//չ�ں�ͬ�������ύ-����
					CountInfo.setExtendContractSubmitCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ�������ύ-����
					CountInfo.setDiscountContractSubmitCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ�������ύ-����
					CountInfo.setTransDiscountContractSubmitCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));					
			}
			//===================�����==================//
			Log.print("//------------------------�����-----------------------//");
			lStatusID = 2;
			{
				//�����ͬ���������-����
				CountInfo.setContractApplyForCheckCount(dao.QueryContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
				/*
				if (Env.getProjectName().equals(Constant.ProjectName.HN) || Env.getProjectName().equals(Constant.ProjectName.CNMEF)) //����û�У������е�
				{
					//չ�ں�ͬ���������-����
					CountInfo.setExtendContractForCheckCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ���������-����
					CountInfo.setDiscountContractForCheckCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ���������-����
					CountInfo.setTransDiscountContractForCheckCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));										
				}
				//*/
					//չ�ں�ͬ���������-����
					CountInfo.setExtendContractForCheckCount(dao.QueryExtendContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//���ֺ�ͬ���������-����
					CountInfo.setDiscountContractForCheckCount(dao.QueryDiscountContractCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
					//ת���ֺ�ͬ���������-����
					CountInfo.setTransDiscountContractForCheckCount(dao.QueryContractCountByLoanType(lUserID, lStatusID, lOfficeID, lCurrencyID,Constant.ApprovalLoanType.ZTX,LOANConstant.LoanType.ZTX));										
			}
			//===================������==================//
			Log.print("//---------------------------������---------------------//");
			lStatusID = 3;
			{
				//�����ͬ����������-����
				CountInfo.setContractApplyForActiveCount(dao.QueryContractApplyForActiveCount(lUserID, lStatusID, lOfficeID, lCurrencyID));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return CountInfo;
	}

	public Collection queryCreditLimitInfo(CreditLimitQueryInfo qInfo) throws Exception
	{
		CreditLimitDAO dao = new CreditLimitDAO();
		return dao.findByMultiOption( qInfo );	
	} 
	
	public double getTheRealAmountByContractID(long lContractID) throws Exception
	{
		double dReturn = 0.0;
		QueryDao dao = new QueryDao();
		
		try
		{
			dReturn = dao.getTheRealAmountByContractID(lContractID);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return dReturn;
	}
	
        /**
         * ���������տ�֪ͨ����ѯ����
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLeaseholdPayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLeaseholdPayNoticeOrder(orderParam, desc);
                return orderSQL;
        }

        /**
         * ���������տ�֪ͨ����ѯ
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryLeaseholdPayNotice(LeaseholdPayNoticeInfo qInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryLeaseholdPayNotice(qInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }
        
        /**
         * �������ޱ�֤�𱣺����ѯ
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryRecognizanceNotice(RecognizanceNoticeInfo rInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryRecognizanceNotice(rInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }

        /**
         * ���������տ�֪ͨ����ѯ����
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLeaseholdRepayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLeaseholdRepayNoticeOrder(orderParam, desc);
                return orderSQL;
        }

        /**
         * ���������տ�֪ͨ����ѯ
         * @param lID
         * @return
         * @throws Exception
         */
        public PageLoader queryLeaseholdRepayNotice(LeaseholdRepayNoticeInfo qInfo) throws Exception
        {
                PageLoader c = null;
                QueryDao dao = new QueryDao();

                try
                {
                        c = dao.queryLeaseholdRepayNotice(qInfo);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        throw e;
                }
                return c;
        }
        
        //Modify by leiyang date 2007/06/27
    	/**
    	* ��ѯ����֪ͨ���ı���״̬����ȫ��״̬
    	* @param ContractQueryInfo ��ѯ����
    	* @return Collection
    	 * @throws Exception 
    	 * @throws IException 
    	* @exception Exception
    	*/
    	/*public Collection queryRepayNoticeSaveOrAll(QueryRepayNoticeInfo qInfo, boolean bool) throws Exception
    	{
    		Collection c = null;
    		try
    		{
    			QueryDao dao = new QueryDao();
    			c = dao.queryRepayNoticeSaveOrAll(qInfo,bool);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			throw e;
    		}
    		return c;
    	}
    	*/
        //Modify by leiyang date 2007/06/27
    	/**
    	* ��ѯ����֪ͨ���ķǱ���״̬
    	* @param ContractQueryInfo ��ѯ����
    	* @return Collection
    	 * @throws Exception 
    	 * @throws IException 
    	* @exception Exception
    	*/
    	/*public Collection queryRepayNoticeNotSave(QueryRepayNoticeInfo qInfo) throws Exception
    	{
    		Collection c = null;
    		try
    		{
    			QueryDao dao = new QueryDao();
    			c = dao.queryRepayNoticeNotSave(qInfo);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    			throw e;
    		}
    		return c;
    	}*/
       
      
        /**
         * ��ѯ����֪ͨ�� 
         * @param repayInfo
         * @return
         * @throws Exception
         */
        public PageLoader queryRepayNotice(QuestRepayNoticeInfo repayInfo) throws Exception{
        	PageLoader c = null;
        	
        	QueryDao dao = new QueryDao();

            try
            {
                    c = dao.queryRepayNotice(repayInfo);
            }
            catch (Exception e)
            {
                    e.printStackTrace();
                    throw e;
            }
        	
        	return c;
        }
        
        
        /**
         * ����֪ͨ�����ݱ�ͷ����
         * @param orderParam
         * @param desc
         * @return
         */
        public String getLoanRepayNoticeOrder(long orderParam, long desc)
        {
                QueryDao dao = new QueryDao();
                String orderSQL = dao.getLoanRepayNoticeOrder(orderParam, desc);
                return orderSQL;
        }
       
        
        /**
         * ��ѯ����֪ͨ�������ܶ�
         * @param 
         */
      
        public QuerySumInfo queryRepayNoticeSum(QuestRepayNoticeInfo repayInfo)throws Exception{
     	   
     	   QuerySumInfo sumInfo = null;
     		QueryDao dao = new QueryDao();

     		try
     		{
     			sumInfo = dao.queryRepayNoticeSum(repayInfo);
     		}
     		catch (Exception e)
     		{
     			e.printStackTrace();
     			throw e;
     		}
     		return sumInfo;
     	   
        }  
        
        
        
  /**
   *
   * ��ѯ�ſ�֪ͨ����ͬ�ܶ�ͷſ��ܶ�
   */
   public QuerySumInfo queryLoanPayNoticeSum (QuestPayNoticeInfo payNoticeInfo) throws Exception {
	   
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryLoanPayNoticeSum(payNoticeInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   } 
   /**
    * ��ѯ���Ŵ����ܶ�
    * @param drawInfo
    * @return
    * @throws Exception
    */
   public QuerySumInfo queryYTDrawNoticeSum(QuestLoanDrawNoticeInfo drawInfo)throws Exception{
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryYTDrawNoticeSum(drawInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   }  
   
   /**
    * ҵ����ܲ�ѯ 
    * @param repayInfo
    * @return
    * @throws Exception
    */
   public PageLoader queryAllBusinesses(QuestContractInfo allInfo) throws Exception{
   	PageLoader c = null;
   	
   	QueryDao dao = new QueryDao();

       try
       {
               c = dao.queryAllBusinesses(allInfo);
       }
       catch (Exception e)
       {
               e.printStackTrace();
               throw e;
       }
   	
   	return c;
   }
   
   
   /**
    * ҵ����ܸ��ݱ�ͷ����
    * @param orderParam
    * @param desc
    * @return
    */
   public String getAllBusinessesOrder(long orderParam, long desc)
   {
           QueryDao dao = new QueryDao();
           String orderSQL = dao.getAllBusinessesOrder(orderParam, desc);
           return orderSQL;
   }
  
   
  
   /**
    * ҵ����ܸ����ܶ�
    * @param allInfo
    * @return
    * @throws Exception
    */
   public QuerySumInfo queryAllBusinessesSum(QuestContractInfo allInfo )throws Exception{
	   
	   QuerySumInfo sumInfo = null;
		QueryDao dao = new QueryDao();

		try
		{
			sumInfo = dao.queryAllBusinessesSum(allInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return sumInfo;
	   
   }  
   
   
   
   
   
   
   
	public static void main(String[] args)
	{
		try
		{
			QueryOperation op = new QueryOperation();
			QueryMyJobCountInfo info = new QueryMyJobCountInfo();
			info = op.queryMyJobCount(76, 1, 1);
			Log.print("---------------kkk = "+info.getExtendApplySubmitCount().length);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
//	�ſ����ת�����ѯ
	public PageLoader queryPayform_Overdue(QuestPayNoticeInfo payNInfo) throws Exception
	{
		PageLoader c = null;
		QueryDao dao = new QueryDao();

		try
		{
			c = dao.queryPayform_Overdue(payNInfo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
}
//ת���⡢��������	
	 public long doOffBalance(long ID,long UserID,String strAction) throws RemoteException, IRollbackException,Exception
	    {
	        long lReturn = -1;
	        try{
	        	QueryDao dao = new QueryDao();
		        if(strAction.equals("doOffbanlance"))
                { 	
		        	lReturn=dao.updateStatus(ID, Constant.offBalance.YES, UserID);
                }
                else
                {
                	lReturn=dao.updateStatus(ID,Constant.offBalance.NO, UserID);
                }
	        }
	    	catch (IException ie)
			{
				throw ie;
			}
			catch (Exception e)
			{
				throw new IException("Gen_E001");
			}
	        return lReturn;

	    }
	//���˼�¼��ѯ,����������浽SETT_PAYFORM_OVERDUE����
	 public void findOffbalanceInfo(long office,long currency,String tsInterestStart) throws Exception
		{
		    QueryDao dao = new QueryDao();
		    QueryPayformOverdue info =null;
			Collection vReturn = new ArrayList();
			try {
				vReturn=dao.queryOffBalanceInfo(office,currency,tsInterestStart);
				if (vReturn != null && vReturn.size() > 0) 
				{
					info = new QueryPayformOverdue();
					Iterator it=vReturn.iterator();
					
					while(it.hasNext())
					{
						info=(QueryPayformOverdue)it.next();
						if(dao.isRepeat(info))
						{
							dao.updateOffBalance(info);
						}
						else
						{
							dao.addOffbalance(info);
						}
					}
				}
			} catch (Exception e) {
				throw new IException("���뻵��ʧ��,"+e.getMessage());
				
			}
			
		}

}