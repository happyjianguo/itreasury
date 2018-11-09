/*
 * Created on 2003-9-26
 * 
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.transcompatibility.dataentity.QueryByStatusConditionInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityDetailInfo;
import com.iss.itreasury.settlement.transcompatibility.dataentity.TransCompatibilityInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;
/**
 * <p>Title:���ݲ�����</p><p>Description:���ڽ����ݲ������ݱ���</p><p>Copyright:
 * Copyright (c) 2003</p><p>Company:isoftstone</p>
 * 
 * @author gqzhang
 * @version 1.0
 */
public class Sett_TransCompatibilityDAO extends SettlementDAO
{
	//----------------------------------------------------------
	public final static int ORDERBY_TRANSACTIONNOID = 0; //���׺�
	//----------------------------------------------------------
	/*
	 * public final static int ORDERBY_ACCOUNT0 = 1; //�跽һ�˻��� public final
	 * static int ORDERBY_BANK0 = 2; //������ public final static int
	 * ORDERBY_GENERALLEDGER0 = 3; //���������� public final static int
	 * ORDERBY_AMOUNT0 = 4; //��� public final static int ORDERBY_RANSDIRECTION0 =
	 * 5; //������� //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT1 = 6; //�跽һ�˻��� public final
	 * static int ORDERBY_BANK1 = 7; //������ public final static int
	 * ORDERBY_GENERALLEDGER1 = 8; //���������� public final static int
	 * ORDERBY_AMOUNT1 = 9; //��� public final static int ORDERBY_RANSDIRECTION1 =
	 * 10; //������� //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT2 = 11; //�跽һ�˻��� public final
	 * static int ORDERBY_BANK2 = 12; //������ public final static int
	 * ORDERBY_GENERALLEDGER2 = 13; //���������� public final static int
	 * ORDERBY_AMOUNT2 = 14; //��� public final static int ORDERBY_RANSDIRECTION2 =
	 * 15; //������� //----------------------------------------------------------
	 * public final static int ORDERBY_ACCOUNT3 = 16; //�跽һ�˻��� public final
	 * static int ORDERBY_BANK3 = 17; //������ public final static int
	 * ORDERBY_GENERALLEDGER3 = 18; //���������� public final static int
	 * ORDERBY_AMOUNT3 = 19; //��� public final static int ORDERBY_RANSDIRECTION3 =
	 * 20; //�������
	 */
	//----------------------------------------------------------
	protected Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public Sett_TransCompatibilityDAO()
	{
		super("Sett_TransCompatibility");
	}
	/**
	 * Method findByID. ���ݼ���ҵ��ļ�¼id���Ҽ���ҵ�����Ϣ������ϸ
	 * 
	 * @param ltransID
	 * @return TransCompatibilityInfo
	 * @throws Exception
	 */
	public TransCompatibilityInfo findByID(long ltransID) throws SettlementDAOException
	{
		ResultSet rs = null;
		TransCompatibilityInfo resultInfo = null;
		try
		{
			log.print("======Sett_TransCompatibilityDAO��������Ҽ���ҵ����Ϣ======");
			log.print("======ltransID======:" + ltransID);
			try
			{
				initDAO();
			}
			catch (ITreasuryDAOException e)
			{
				throw new SettlementDAOException(e);
			}
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT * FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID =" + ltransID);
			strSQLBuffer.append("\n");
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			Vector vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				resultInfo = (TransCompatibilityInfo) vctReturn.elementAt(0);
				if (resultInfo != null)
				{
					log.print("===resultInfo:" + resultInfo);
				}
			}
			rs.close();
			log.print("======Sett_TransCompatibilityDAO���������Ҽ���ҵ����Ϣ======");
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("���Ҽ���ҵ����Ϣ��������", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("���Ҽ���ҵ����Ϣ��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return resultInfo;
	}
	/**
	 * ����˵��������ID�����޸�ʱ��
	 * 
	 * @param transID
	 * @return Timestamp
	 * @throws IException
	 */
	public Timestamp findTouchDate(long transID) throws SettlementDAOException
	{
		ResultSet rs = null;
		Timestamp res = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("SELECT ModifyDate FROM \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append("WHERE ID = " + transID);
			log.info(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			if (rs.next())
			{
				res = rs.getTimestamp(1);
				log.debug("====�޸�ʱ�䣺" + res);
			}
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("���Ҽ���ҵ���޸�ʱ���������", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("���Ҽ���ҵ���޸�ʱ���������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return res;
	}
	/**
		 * ���ݽ��׺Ų��ҽ���ID�ķ�����
		 */
	public long getIDByTransNo(String strTransNo) throws SettlementDAOException
	{
		ResultSet rs = null;
		long lID = -1;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			prepareStatement("select ID from \n" + strTableName + " where TransNo=" + strTransNo);
			rs = executeQuery();
			if (rs != null && rs.next())
			{
				lID = rs.getLong("ID");
			}
			rs.close();
		}
		catch (Exception e)
		{
			throw new SettlementDAOException("���ݼ��ݽ��ױ�Ų��ҽ���id��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return lID;
	}
	/**
	 * Method findCompatibilityByQueryCondition. ������ϲ�ѯ�������Ҽ��ݼ�¼
	 * 
	 * @param info
	 * @param strOrderByName
	 * @param isDesc
	 * @return Vector
	 * @throws SettlementDAOException
	 */
	public Vector findCompatibilityByQueryCondition(QueryByStatusConditionInfo info) throws SettlementDAOException
	{
		Vector vctReturn = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where ID>0 ");
			//��������
			if (info.getTransTypeID() > 0)
			{
				strSQLBuffer.append(" and TransactionTypeID=" + info.getTransTypeID());
				strSQLBuffer.append("\n");
			}
			//���ݽ�������
			if (info.getOperationTypeID() > 0)
			{
				strSQLBuffer.append(" and OperationTypeID=" + info.getOperationTypeID());
				strSQLBuffer.append("\n");
			}
			//¼����
			if (info.getInputUserID() > 0)
			{
				strSQLBuffer.append(" and InputUserID=" + info.getInputUserID());
				strSQLBuffer.append("\n");
			}
			//������
			if (info.getCheckUserID() > 0)
			{
				strSQLBuffer.append(" and CheckUserID=" + info.getCheckUserID());
				strSQLBuffer.append("\n");
			}
			if (info.getStatusIDs() != null && info.getStatusIDs().length > 0)
			{
				long lTemp = info.getStatusIDs().length;
				boolean isStart = true;
				for (int i = 0; i < lTemp; i++)
				{
					if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.TEMPSAVE)
					{ //�ݴ�û��ʱ������
						if (isStart)
						{
							strSQLBuffer.append(" and (");
							isStart = !isStart;
						}
						else
						{
							strSQLBuffer.append(" or ");
						}
						strSQLBuffer.append("(StatusID = " + SETTConstant.TransactionStatus.TEMPSAVE + ") ");
					}
					else
						if (info.getStatusIDs()[i] == SETTConstant.TransactionStatus.SAVE || info.getStatusIDs()[i] == SETTConstant.TransactionStatus.CHECK)
						{ //������߸���Ҫ�鵱���
							if (isStart)
							{
								strSQLBuffer.append(" and (");
								isStart = !isStart;
							}
							else
							{
								strSQLBuffer.append("or ");
							}
							strSQLBuffer.append("( StatusID = " + info.getStatusIDs()[i]);
							strSQLBuffer.append(" and ExecuteDate= TO_DATE('" + DataFormat.getDateString(info.getExecuteDate()) + "','YYYY/MM/DD HH24:MI:SS') \n");
							strSQLBuffer.append("\n");
							strSQLBuffer.append(" )");
						}
						else
						{
							strSQLBuffer.append(" and ");
							strSQLBuffer.append(" StatusID = " + info.getStatusIDs()[i]); //�հ׵�ʱ��
						}
				}
				strSQLBuffer.append(")");
				strSQLBuffer.append("\n");
			}
			strSQLBuffer.append(" and OfficeID=" + info.getOfficeID());
			strSQLBuffer.append("\n");
			strSQLBuffer.append(" and CurrencyID=" + info.getCurrencyID());
			strSQLBuffer.append("\n");
			switch ((int) info.getOrderByID())
			{
				case (int) ORDERBY_TRANSACTIONNOID :
					strSQLBuffer.append(" order by TransNo ");
					break;
				default :
					strSQLBuffer.append(" order by TransNo ");
			}
			strSQLBuffer.append("\n");
			if (info.isDesc())
			{
				strSQLBuffer.append(" desc \n");
			}
			log.debug(strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			if (vctReturn != null && vctReturn.size() > 0)
			{
				log.debug("===��������Ϣ��Ϊ��===");
				log.debug("===��������Ϣ��¼��===" + vctReturn.size());
				Vector vctTemp = new Vector();
				Vector vctDetail = null;
				TransCompatibilityInfo mainInfo = null;
				TransCompatibilityDetailInfo detailInfo = null;
				Sett_TransCompatibilityDetailDAO detailDao = new Sett_TransCompatibilityDetailDAO();
				for (int i = 0; i < vctReturn.size(); i++)
				{
					mainInfo = (TransCompatibilityInfo) vctReturn.elementAt(i);
					if (mainInfo != null)
					{
						log.debug("===��ѯ����Ϣ��Ӧ������Ϣ" + i);
						vctDetail = detailDao.findByMainID(mainInfo.getId());
						if (vctDetail != null && vctDetail.size() > 0)
						{
							mainInfo.setTransCompatibilityDetailInfo(vctDetail);
						}
						vctTemp.add(mainInfo);
					}
				}
				vctReturn = vctTemp;
			}
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("������ϲ�ѯ�������Ҽ��ݼ�¼��������", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("������ϲ�ѯ�������Ҽ��ݼ�¼��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		if (vctReturn == null)
		{
			vctReturn = new Vector();
		}
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	/**
	 * Method match.
	 * 
	 * @param info
	 * @return TransCompatibilityInfo
	 * @throws SettlementDAOException
	 */
	public Vector match(TransCompatibilityInfo info) throws SettlementDAOException
	{
		Vector vctReturn = null;
		ResultSet rs = null;
		try
		{
			initDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		try
		{
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append("select * from \n");
			strSQLBuffer.append(strTableName + " \n");
			strSQLBuffer.append(" where \n");
			strSQLBuffer.append(getMatchWhereSQL(info));
			//log.debug("=====ƥ��sql:" + strSQLBuffer.toString());
			prepareStatement(strSQLBuffer.toString());
			rs = executeQuery();
			vctReturn = getCompatibilityMainInfoFromResultSet(rs);
			rs.close();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException("����ƥ����ݼ�¼��������", e);
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("����ƥ����ݼ�¼��������", e);
		}
		try
		{
			finalizeDAO();
		}
		catch (ITreasuryDAOException e)
		{
			throw new SettlementDAOException(e);
		}
		return vctReturn;
	}
	/**
	 * Method getWhereSQL.
	 * 
	 * @param info
	 * @return String
	 * @throws SettlementDAOException
	 */
	private String getMatchWhereSQL(TransCompatibilityInfo info) throws SettlementDAOException
	{
		StringBuffer strSQLBuffer = new StringBuffer();
		//id
		long lId = info.getId();
		strSQLBuffer.append(" 1=1 ");
		if (lId > 0)
		{
			strSQLBuffer.append(" AND ID = '" + lId + "' \n");
		}
		strSQLBuffer.append(" AND TransactionNOID = " + info.getTransactionNOID() + " \n");
		//Number �������� 
		strSQLBuffer.append("AND  TransactionTypeID = " + info.getTransactionTypeID() + " \n");
		//����ҵ��������
		strSQLBuffer.append("AND  OperationTypeID = " + info.getOperationTypeID() + " \n");
		//Number ���´�
		strSQLBuffer.append("AND  OfficeID = " + info.getOfficeID() + " \n");
		//Number ����
		strSQLBuffer.append("AND  CurrencyID = " + info.getCurrencyID() + " \n");
		//DateTime ��Ϣ��
		Timestamp tsInterestStartDate = info.getInterestStartDate();
		if (tsInterestStartDate == null)
		{
			strSQLBuffer.append(" AND InterestStartDate IS NULL ");
		}
		else
		{
			String strTime = tsInterestStartDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InterestStartDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//DateTime ִ����
		Timestamp tsExecuteDate = info.getExecuteDate();
		if (tsExecuteDate == null)
		{
			strSQLBuffer.append(" AND ExecuteDate IS NULL ");
		}
		else
		{
			String strTime = tsExecuteDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ExecuteDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//ժҪ
		/*String strAbstract = info.getAbstract();
		if (strAbstract.compareToIgnoreCase("") == 0)
		{
			strSQLBuffer.append(" AND Abstract IS NULL \n");
		}
		else
		{
			strSQLBuffer.append(" AND  Abstract = '" + strAbstract + "' \n");
		}
		*/
		//״̬
		strSQLBuffer.append(" AND StatusID = " + SETTConstant.TransactionStatus.SAVE);
		//ȷ�ϰ��´�
		long lAffrimOfficeID = info.getAffrimOfficeID();
		if (lAffrimOfficeID > 0)
		{
			strSQLBuffer.append(" AND AffrimOfficeID = " + lAffrimOfficeID);
		}
		//¼����,��ǰ�����û����ܵ��ڽ�����Ϣ¼���û�
		strSQLBuffer.append(" AND InputUserID != " + info.getInputUserID() + " \n");
		//������
		long lCheckUserID = info.getCheckUserID();
		if (lCheckUserID > 0)
		{
			strSQLBuffer.append(" AND CheckUserID != " + lCheckUserID + " \n");
		}
		//ǩ����
		long lSignUserID = info.getSignUserID();
		if (lSignUserID > 0)
		{
			strSQLBuffer.append(" AND SignUserID != " + lSignUserID + " \n");
		}
		//ȷ����
		long lAffrimUserID = info.getAffrimUserID();
		if (lAffrimUserID > 0)
		{
			strSQLBuffer.append(" AND AffrimUserID != " + lAffrimUserID + " \n");
		}
		//¼������
		/*Timestamp tsInputDate = info.getInputDate();
		if (tsInputDate == null)
		{
			strSQLBuffer.append(" AND InputDate IS NULL ");
		}
		else
		{
			String strTime = tsInputDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND InputDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		*/
		//�޸�����
		Timestamp tsModifyDate = info.getModifyDate();
		if (tsModifyDate != null)
		{
			String strTime = tsModifyDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND ModifyDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
			;
		}
		//��������
		Timestamp tsCheckDate = info.getCheckDate();
		if (tsCheckDate != null)
		{
			String strTime = tsCheckDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND CheckDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
			;
		}
		//ǩ������
		Timestamp tsSignDate = info.getSignDate();
		if (tsSignDate != null)
		{
			String strTime = tsSignDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND SignDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		//ȷ������
		Timestamp tsAffirmDate = info.getAffirmDate();
		if (tsAffirmDate != null)
		{
			String strTime = tsAffirmDate.toString();
			strTime = strTime.substring(0, 10);
			strSQLBuffer.append(" AND AffirmDate = to_date('" + strTime + "','yyyy-mm-dd') \n");
		}
		return strSQLBuffer.toString();
	}
	/**
	 * Method getCompatibilityMainInfoFromResultSet.
	 * 
	 * @param rs
	 * @return Vector
	 * @throws Exception
	 */
	private Vector getCompatibilityMainInfoFromResultSet(ResultSet rs) throws SettlementDAOException
	{
		Vector vctReturn = new Vector();
		TransCompatibilityInfo info = null;
		try
		{
			while (rs.next())
			{
				info = new TransCompatibilityInfo();
				info.setId(rs.getLong("ID")); //Number ID
				info.setTransNo(rs.getString("TransNo")); //Code ���׺�
				info.setTransactionNOID(rs.getLong("TransactionNOID")); //Number����ID���������TransactionNo
				info.setTransactionTypeID(rs.getLong("TransactionTypeID")); //	Number��������
				info.setOfficeID(rs.getLong("OfficeID")); //	Number ���´�
				info.setCurrencyID(rs.getLong("CurrencyID")); //	Number ����
				info.setInterestStartDate(rs.getTimestamp("InterestStartDate")); //	DateTime��Ϣ��
				info.setExecuteDate(rs.getTimestamp("ExecuteDate")); //	DateTimeִ����
				info.setAbstract(rs.getString("Abstract")); //Abstract ժҪ
				info.setStatusID(rs.getLong("StatusID")); //	Number ״̬ȡֵ�ڳ������壺�ݴ桢���桢���ˡ�δǩ�ϡ�ǩ�ϡ�ȷ��
				info.setAffrimOfficeID(rs.getLong("AffrimOfficeID")); //	Numberȷ�ϰ��´�
				info.setInputUserID(rs.getLong("InputUserID")); //	Number ¼����
				info.setCheckUserID(rs.getLong("CheckUserID")); //	Number ������
				info.setSignUserID(rs.getLong("SignUserID")); //	Number ǩ����
				info.setAffrimUserID(rs.getLong("AffrimUserID")); //	Number ȷ����
				info.setInputDate(rs.getTimestamp("InputDate")); //DateTime ¼������
				info.setModifyDate(rs.getTimestamp("ModifyDate")); //	DateTime�޸�����ʱ���ʱ���֡����datatime����
				info.setCheckDate(rs.getTimestamp("CheckDate")); //	DateTime��������
				info.setSignDate(rs.getTimestamp("SignDate")); //	DateTime ǩ������
				info.setAffirmDate(rs.getTimestamp("AffirmDate")); //DateTimeȷ������
				info.setCheckAbstract(rs.getString("CheckAbstract")); //���˱�ע
				info.setOperationTypeID(rs.getLong("OperationTypeID")); //���ݽ�������
				vctReturn.add(info);
			}
		}
		catch (SQLException e)
		{
			throw new SettlementDAOException("���Ҽ���ҵ����Ϣ��������", e);
		}
		log.print("====vctReturn.size():" + vctReturn.size());
		return vctReturn.size() > 0 ? vctReturn : null;
	}
	/**
	 * Method main.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(java.lang.String[] args) throws Exception
	{
		//�ڴ˴�������������Ӧ�ó���Ĵ��롣
		try
		{
			Sett_TransCompatibilityDAO dao = new Sett_TransCompatibilityDAO();
			TransCompatibilityInfo info = dao.findByID(1);
			/*
			 * if (info != null) { System.out.print("==============" +
			 * info.getId()); System.out.print("==============" +
			 * info.getOfficeID()); Vector vctDetail =
			 * info.getTransCompatibilityDetailInfo(); if (vctDetail != null) {
			 * for (int i = 0; i < vctDetail.size(); i++) {
			 * TransCompatibilityDetailInfo detailInfo =
			 * (TransCompatibilityDetailInfo) vctDetail.elementAt(i);
			 * System.out.print("==============detail" + detailInfo.getId()); } } }
			 */
			System.out.print("==============" + dao.findTouchDate(1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}