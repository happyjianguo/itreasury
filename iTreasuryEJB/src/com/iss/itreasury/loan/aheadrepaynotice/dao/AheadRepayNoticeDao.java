/*
 * Created on 2003-12-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.aheadrepaynotice.dao;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.*;

import com.iss.itreasury.loan.contract.dataentity.*;
import com.iss.itreasury.loan.contract.dao.*;
//import com.iss.itreasury.system.bizlogic.ApprovalBiz;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.system.bizdelegation.*;
import com.iss.itreasury.loan.setting.dao.LoanTypeRelationDao;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.loan.aheadrepaynotice.dataentity.*;
import com.iss.itreasury.loan.loanpaynotice.dao.*;
import com.iss.itreasury.loan.loanpaynotice.dataentity.LoanPayNoticeInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AheadRepayNoticeDao
{

	private static Log4j log4j = null;

	public AheadRepayNoticeDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	 * ����������ѯ(�޸�)
	 * Create Date: 2003-10-15
	 * @param AheadRepayNoticeQueryInfo ��ѯ���� 
	 * @return Collection
	 * @exception Exception
	 */
	public Collection queryForUpdate(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Vector v = new Vector();

		//��ҳ����
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		int lIndex = 1;

		String strSQL = "";
		String strSQL_pre = "";
		String strSQL_con = "";
		String strSQL_order = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();

			//�����¼����
			strSQL_pre = " SELECT COUNT(*) ";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b";

			//��ѯ����
			strSQL_con = " WHERE b.id = a.nContractID";
			strSQL_con += " AND a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
			//strSQL_con += " AND a.nNextCheckUserID =? "; 
			strSQL_con += " and a.nNextCheckLevel = 1 ";
			strSQL_con += " AND a.nInputUserID=?";
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				strSQL_con += " AND b.nBorrowClientID = ?";
			}

			//lContractIDTo ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				strSQL_con += " AND b.ID = ?";
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				strSQL_con += " AND a.mAmount >= ?";
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				strSQL_con += " AND a.mAmount <= ?";
			}

			//¼��������
			if (qInfo.getInputDateFrom() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			//¼������ֹ
			if (qInfo.getInputDateTo() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);

			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			//¼��������
			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			//¼������ֹ
			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			//������ҳ��
			if (lRecordCount > qInfo.getPageLineCount())
			{
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0)
				{
					lPageCount++;
				}
			}
			else if (lRecordCount > 0 && lRecordCount <= qInfo.getPageLineCount())
			{
				lPageCount = 1;
			}
			else
			{
				lPageCount = 0;
			}

			//��������Ľ����

			//��ҳ��ʾ����ʾ��һҳ
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam())
			{
				case 1 : //����ͬ�������
					strSQL_order += " ORDER BY b.sContractCode";
					break;
				case 2 : //����λ����
					strSQL_order += " ORDER BY c.sName";
					break;
				case 3 : //���ſ�֪ͨ������
					strSQL_order += " ORDER BY d.sCode";
					break;
				case 4 : //���ſ�������
					strSQL_order += " ORDER BY d.mAmount";
					break;
				case 5 : //����ǰ����������
					strSQL_order += " ORDER BY a.mAmount";
					break;
				case 6 : //��¼����������
					strSQL_order += " ORDER BY a.dtInputDate";
					break;
				case 7 : //��֪ͨ��״̬����
					strSQL_order += " ORDER BY a.nStatusID";
					break;
				case 8 : //���Ƿ���ǰ����
					strSQL_order += " ORDER BY a.nIsAhead";
					break;
				default :
					strSQL_order += " ORDER BY a.dtInputDate DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL_order += " DESC";
			}

			//got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " b.sContractCode,c.sName,";
			strSQL_pre += " d.sCode as PayCode,d.mAmount as PayAmount";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,";
			strSQL_pre += " Client c,loan_PayForm d";

			strSQL_con += " AND b.nBorrowClientID=c.ID(+)";
			strSQL_con += " AND a.nLoanPayNoticeID=d.ID";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;
			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			ps.setLong(lIndex++, lRowNumStart); //������ʼ�к�
			ps.setLong(lIndex++, lRowNumEnd); //��������к�

			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("id")); //��ǰ����֪ͨ��ID
				info.setContractCode(rs.getString("sContractCode")); //��ͬ���
				info.setClientName(rs.getString("sName")); //���λ
				info.setLetoutNoticeCode(rs.getString("PayCode")); //�ſ�֪ͨ�����
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //�ſ���
				info.setAmount(rs.getDouble("mAmount")); //��ǰ������
				info.setInputDate(rs.getTimestamp("dtInputDate")); //¼������
				info.setStatus(LOANConstant.AheadRepayStatus.getName(rs.getLong("nStatusID")));
				//֪ͨ��״̬
				info.setIsAhead(rs.getLong("nIsAhead"));//�Ƿ���ǰ����
				info.setPageCount(lPageCount); //��¼�ܵ�ҳ����
				v.addElement(info);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}

		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* ����������ѯ��ͬ��Ϣ(���)
	* Create Date: 2003-10-15
	* @param ContractQueryInfo ��ѯ����
	* @return Collection
	* @exception Exception
	*/
	public Collection queryForExamine(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Vector v = new Vector();

		//��ҳ����
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		int lIndex = 1;

		String strSQL = "";
		String strSQL_pre = "";
		String strSQL_con = "";
		String strSQL_order = "";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			//������������������¼���ˣ���ʵ�򴫸���������ˣ���
			ApprovalDelegation appBiz = new ApprovalDelegation();
			long lModuleID = Constant.ModuleType.LOAN; //ģ������
			long lActionID = Constant.ApprovalAction.AHEADREPAY_NOTICE; //��ǰ����֪ͨ�����
			//String strUser = appBiz.findTheVeryUser(lModuleID, Constant.ApprovalLoanType.OTHER, lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(), qInfo.getInputUserID());

			con = Database.getConnection();

			//�����¼����
			strSQL_pre = " SELECT COUNT(*) ";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b";

			//��ѯ����
			strSQL_con = " WHERE b.id = a.nContractID";
            /*
			strSQL_con += " AND (a.nNextCheckUserID IN " + strUser;
			strSQL_con += " OR (a.nNextCheckUserID=-2";
			strSQL_con += " AND a.nInputUserID=?))";
			strSQL_con += " AND (a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
			strSQL_con += " OR a.nStatusID =" + LOANConstant.AheadRepayStatus.CHECK + ")";
            //*/
			//û�� �����֡���������������ת���֡������������ޡ��������š���������
			LoanTypeRelationDao loanTypeDao = new LoanTypeRelationDao();
			long[] loanTypeId = {LOANConstant.LoanType.ZY,LOANConstant.LoanType.WT,
					LOANConstant.LoanType.ZGXE,LOANConstant.LoanType.MFXD,
					LOANConstant.LoanType.OTHER
			}; 
			String strUser = null;
			long[] a_SubLoanType = loanTypeDao.getAllSetSubLoanTypeID(
					qInfo.getOfficeID(),qInfo.getCurrencyID(), loanTypeId );
			if (a_SubLoanType != null && a_SubLoanType.length > 0) {
				strSQL_con += " AND ((( ";
				for (int i = 0; i < a_SubLoanType.length; i++) {
					strUser  =	 appBiz.findTheVeryUser(lModuleID,
						a_SubLoanType[i], lActionID,qInfo.getOfficeID(),qInfo.getCurrencyID(),qInfo.getInputUserID());
					strSQL_con += " a.nNextCheckUserID IN " + strUser ;
					if (i < a_SubLoanType.length - 1)
						strSQL_con += " or ";
					else
						strSQL_con += " ) ";
					}					
			}else{
				return null;
			}
			
           // strSQL_con += " AND ((a.nNextCheckUserID IN " + strUser ;
            strSQL_con += "   AND a.nStatusID =" + LOANConstant.AheadRepayStatus.SUBMIT;
            strSQL_con += "     )";
            strSQL_con += " OR (a.nNextCheckUserID=-2 ";
            strSQL_con += " AND (a.nStatusID =" + LOANConstant.AheadRepayStatus.CHECK ;
            strSQL_con += " OR a.nStatusID =" + LOANConstant.AheadRepayStatus.USED + ")";
            strSQL_con += "     ))";
            
			strSQL_con += " AND a.nCurrencyID=?";
			strSQL_con += " AND a.nOfficeID=?";

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				strSQL_con += " AND b.nBorrowClientID = ?";
			}

			//lContractIDTo ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				strSQL_con += " AND b.ID = ?";
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				strSQL_con += " AND a.mAmount >= ?";
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				strSQL_con += " AND a.mAmount <= ?";
			}

			//¼��������
			if (qInfo.getInputDateFrom() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') >= TO_CHAR(?,'yyyy-mm-dd')";
			}

			//¼������ֹ
			if (qInfo.getInputDateTo() != null)
			{
				strSQL_con += " AND TO_CHAR(a.dtInputDate,'yyyy-mm-dd') <=TO_CHAR(?,'yyyy-mm-dd')";
			}

			//״̬
			if (qInfo.getStatusID() > 0)
			{
				strSQL_con += " AND a.nStatusID = ?";
			}

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);

			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			//¼��������
			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			//¼������ֹ
			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			//״̬
			if (qInfo.getStatusID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			rs = ps.executeQuery();

			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
			}
			log4j.info("lRecordCount:" + lRecordCount);

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}

			//������ҳ��
			if (lRecordCount > qInfo.getPageLineCount())
			{
				lPageCount = lRecordCount / qInfo.getPageLineCount();
				if ((lRecordCount % qInfo.getPageLineCount()) != 0)
				{
					lPageCount++;
				}
			}
			else if (lRecordCount > 0 && lRecordCount <= qInfo.getPageLineCount())
			{
				lPageCount = 1;
			}
			else
			{
				lPageCount = 0;
			}

			//��������Ľ����

			//��ҳ��ʾ����ʾ��һҳ
			lRowNumStart = (qInfo.getPageNo() - 1) * qInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + qInfo.getPageLineCount() - 1;

			switch ((int) qInfo.getOrderParam())
			{
				case 1 : //����ͬ�������
					strSQL_order += " ORDER BY b.sContractCode";
					break;
				case 2 : //����λ����
					strSQL_order += " ORDER BY c.sName";
					break;
				case 3 : //���ſ�֪ͨ������
					strSQL_order += " ORDER BY d.sCode";
					break;
				case 4 : //���ſ�������
					strSQL_order += " ORDER BY d.mAmount";
					break;
				case 5 : //����ǰ����������
					strSQL_order += " ORDER BY a.mAmount";
					break;
				case 6 : //��¼����������
					strSQL_order += " ORDER BY a.dtInputDate";
					break;
				case 7 : //��֪ͨ��״̬����
					strSQL_order += " ORDER BY a.nStatusID";
					break;
				case 8 : //���Ƿ���ǰ����
					strSQL_order += " ORDER BY a.nIsAhead";
					break;
				default :
					strSQL_order += " ORDER BY a.dtInputDate DESC";
			}

			if (qInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL_order += " DESC";
			}

			//got the sql sentence
			strSQL_pre = "SELECT * FROM ";
			strSQL_pre += " ( SELECT all_record.*,ROWNUM num FROM";
			strSQL_pre += " ( SELECT a.*,";
			strSQL_pre += " b.sContractCode,c.sName,";
			strSQL_pre += " d.sCode as PayCode,d.mAmount as PayAmount";
			strSQL_pre += " FROM loan_AheadRepayForm a,loan_ContractForm b,";
			strSQL_pre += " Client c,loan_PayForm d";

			strSQL_con += " AND b.nBorrowClientID=c.ID(+)";
			strSQL_con += " AND a.nLoanPayNoticeID=d.ID";
			strSQL_con += strSQL_order;
			strSQL_con += ")all_record ";
			strSQL_con += ") WHERE num BETWEEN ? AND ?";

			strSQL = strSQL_pre + strSQL_con;
			log4j.info(strSQL);
			ps = con.prepareStatement(strSQL);
			lIndex = 1;
			//ps.setLong(lIndex++, qInfo.getInputUserID());
			ps.setLong(lIndex++, qInfo.getCurrencyID());
			ps.setLong(lIndex++, qInfo.getOfficeID());

			//lClientID��λID
			if (qInfo.getClientID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getClientID());
			}

			// ��ͬ���
			if (qInfo.getContractID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getContractID());
			}

			//dAmountFrom�����
			if (qInfo.getAmountFrom() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountFrom());
			}

			//dAmountTo���ֹ
			if (qInfo.getAmountTo() > 0)
			{
				ps.setDouble(lIndex++, qInfo.getAmountTo());
			}

			if (qInfo.getInputDateFrom() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateFrom());
			}

			if (qInfo.getInputDateTo() != null)
			{
				ps.setTimestamp(lIndex++, qInfo.getInputDateTo());
			}

			//״̬
			if (qInfo.getStatusID() > 0)
			{
				ps.setLong(lIndex++, qInfo.getStatusID());
			}

			ps.setLong(lIndex++, lRowNumStart); //������ʼ�к�
			ps.setLong(lIndex++, lRowNumEnd); //��������к�

			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("id")); //��ǰ����֪ͨ��ID
				info.setContractCode(rs.getString("sContractCode")); //��ͬ���
				info.setClientName(rs.getString("sName")); //���λ
				info.setLetoutNoticeCode(rs.getString("PayCode")); //�ſ�֪ͨ�����
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //�ſ���
				info.setAmount(rs.getDouble("mAmount")); //��ǰ������
				info.setInputDate(rs.getTimestamp("dtInputDate")); //¼������
				info.setStatus(LOANConstant.AheadRepayStatus.getName(rs.getLong("nStatusID")));
				//֪ͨ��״̬
				info.setIsAhead(rs.getLong("nIsAhead"));//�Ƿ���ǰ����
				info.setPageCount(lPageCount); //��¼�ܵ�ҳ����
				v.addElement(info);
			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}

		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return (v.size() > 0 ? v : null);
	}

	/**
	* �õ��ſ�֪ͨ����ǰ���
	* Create Date: 2003-10-15
	* @param lID �ſ�֪ͨ��ID
	* @return double
	* @exception Exception
	*/
	public double getLateAmount(long lID) throws Exception
	{
		double dBalance = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();

			if (lID > 0)
			{
				sbSQL.append(" SELECT SUM(a.mBalance) Balance");
				sbSQL.append(" FROM sett_subAccount a,loan_payform b");
				sbSQL.append(" WHERE a.AL_nLoanNoteID = b.ID ");
				sbSQL.append(" AND b.id = ? ");

				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, lID);
				rs = ps.executeQuery();

				if (rs.next())
				{
					dBalance = rs.getDouble("Balance");
				}
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return dBalance;
	}

	/**
	* �õ���ǰ����֪ͨ����ϸ��Ϣ
	* Create Date: 2003-10-15
	* @param lID ��ǰ����֪ͨ��ID
	* @return AheadRepayNoticeInfo ��ǰ����֪ͨ����ϸ��Ϣ
	* @exception Exception
	*/
	public AheadRepayNoticeInfo findByID(long lID) throws Exception
	{		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.id as ContractID,b.sContractCode,");
			sbSQL.append(" b.nIntervalNum,b.mExamineAmount,b.mAdjustRate,b.ntypeid,b.nsubtypeid,");
			sbSQL.append(" c.sCode as PayCode,c.mInterestRate,");
			sbSQL.append(" c.mAmount as PayAmount,c.id as PayID,");
			sbSQL.append(" d.sName as clientName,e.sName as userName");
            
            sbSQL.append(" ,pp.id as PlanID ");//2004-01-15 ninh
            
			sbSQL.append(" FROM loan_AheadRepayForm a,loan_ContractForm b,");
			sbSQL.append(" loan_PayForm c,Client d,userInfo e");
            
            sbSQL.append(" ,loan_loancontractplan pp ");//2004-01-15 ninh
            
			sbSQL.append(" WHERE b.id = a.nContractID");
			sbSQL.append(" AND c.ID=a.nLoanPayNoticeID");
			sbSQL.append(" AND b.nBorrowClientID=d.ID(+)");
			sbSQL.append(" AND a.nInputUserID = e.id(+)");

            sbSQL.append(" and b.id=pp.nContractID(+) ");//2004-01-15 ninh
            sbSQL.append(" and pp.nStatusID(+) = "+Constant.RecordStatus.VALID);//2004-01-15 ninh
            
			sbSQL.append(" AND a.id = ?");

            sbSQL.append(" order by pp.id desc ");//2004-01-15 ninh
            
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
             
			rs = ps.executeQuery();
			
			ContractDao dao = new ContractDao();
			LoanPayNoticeDao payDao = new LoanPayNoticeDao();

			if (rs.next())
			{
				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("sCode")); //֪ͨ�����
				info.setClientName(rs.getString("clientName")); //��λ
				info.setContractCode(rs.getString("sContractCode")); //��ͬ���
				info.setContractID(rs.getLong("ContractID")); //��ͬID
				info.setIntervalNum(rs.getLong("nIntervalNum")); //��������
				info.setContractAmount(rs.getDouble("mExamineAmount")); //��ͬ���
				info.setLoanType(rs.getLong("ntypeid"));
				info.setLoanSubType(rs.getLong("nsubtypeid"));
				info.setPBackDate(rs.getTimestamp("payDate"));
				info.setBalanceAmount(rs.getDouble("interestAmount"));
               
                info.setPlanID(rs.getLong("PlanID"));//�ƻ���ʶ
                Log.print("PlanID:"+rs.getLong("PlanID")); 
                
				ContractAmountInfo cInfo = dao.getLateAmount(info.getContractID());
				info.setContractBalance(cInfo.getBalanceAmount()); //��ͬ���
				info.setLetoutNoticeCode(rs.getString("PayCode")); //�ſ�֪ͨ�����
				
				info.setLetoutNoticeRate(payDao.getLatelyRate(rs.getLong("PayID"),null));//�ſ�֪ͨ��ִ������
				
				//�ſ�֪ͨ������,��ǰ��������
				info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //�ſ�֪ͨ�����
				info.setLetoutNoticeID(rs.getLong("PayID")); //�ſ�֪ͨ��ID
				LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(rs.getLong("PayID"));
				info.setLetoutNoticeBalance(pinfo.getBalance()); //�ſ�֪ͨ�����
				
				info.setAmount(rs.getDouble("mAmount")); //��ǰ������
				info.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //�����
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));	//��һ����˼���
				info.setInputUserID(rs.getLong("nInputUserID")); //¼����ID
				info.setInputUserName(rs.getString("userName")); //¼����
				info.setInputDate(rs.getTimestamp("dtInputDate")); //¼��ʱ��
				info.setIsAhead(rs.getLong("nIsAhead"));//�Ƿ���ǰ����
				info.setStatusID(rs.getLong("nStatusID")); //״̬
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				//״̬ 

			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}

	/**
	 * ������ǰ����֪ͨ����
	 * @param AheadRepayNoticeInfo
	 * @return long
	 * @throws Exception
	 */
	public long insert(AheadRepayNoticeInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		long lCode = 0;
		String sCode="";

		try
		{
			if (info != null)
			{
				conn = Database.getConnection();
				/*���Ȼ��loan_AheadRepayForm �� MAXID */
				strSQL = " SELECT NVL(MAX(ID)+1,1) ID";
				strSQL += " FROM loan_AheadRepayForm ";

				ps = conn.prepareStatement(strSQL);
				log4j.info(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lID = rs.getLong("ID");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
		
				strSQL = " SELECT ";
				strSQL += "  max(l.scode) ";
				strSQL += " FROM loan_AheadRepayForm l ";
				strSQL += " WHERE l.nContractID = ? ";
				strSQL += " and l.nstatusid != 6 ";
				ps = conn.prepareStatement(strSQL);
				
				ps.setLong(1, info.getContractID());
				log4j.info(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					sCode = rs.getString(1);
					if(sCode==null)
					{
						lCode = 1;
					}else
					{
						lCode = Long.parseLong(sCode.substring(2))+1;
					}
					
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				
				if (lCode < 10)
				{
					sCode = "TQ00" + lCode;
				}
				else if (lCode < 100 && lCode >= 10)
				{
					sCode = "TQ0" + lCode;
				}
				if (lCode >= 100)
				{
					sCode = "TQ" + lCode;
				}

				log4j.info("������ǰ����֪ͨ����" + sCode);

				/* ������Ϣ��loan_loanForm ��loan_contractForm */
				strSQL = "INSERT INTO loan_AheadRepayForm ";
				strSQL += " (ID,nOfficeID,nCurrencyID,nContractID,";
				strSQL += " nLoanPayNoticeID,mAmount,sCode,nInputUserID,";
				strSQL += " dtInputDate,nNextCheckUserID,nStatusID,nNextCheckLevel,nIsAhead,INTERESTAMOUNT,PAYDATE,BATCHID)";
				strSQL += " values (?,?,?,?,?,?,?,?,sysdate,?,?,1,?,?,?,?)";

				log4j.info(strSQL);
				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, lID);
				ps.setLong(n++, info.getOfficeID());
				ps.setLong(n++, info.getCurrencyID());
				ps.setLong(n++, info.getContractID());
				ps.setLong(n++, info.getLetoutNoticeID());
				ps.setDouble(n++, info.getAmount());
				ps.setString(n++, sCode);
				ps.setLong(n++, info.getInputUserID());
				ps.setLong(n++, info.getInputUserID());
				ps.setLong(n++, LOANConstant.AheadRepayStatus.SUBMIT);
				ps.setLong(n++, info.getIsAhead());
				
				ps.setDouble(n++, info.getBalanceAmount());
				ps.setTimestamp(n++, info.getPBackDate());
				ps.setLong(n++, info.getBatchID());

				ps.executeUpdate();
				ps.close();
				ps = null;

			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lID;
	}

	/**
	* �޸���ǰ����֪ͨ����Ϣ
	* Create Date: 2003-10-15
	* @param AheadRepayNoticeInfo
	* @return long �����0��ʾ�ɹ���С�ڵ���0��ʾʧ��
	* @exception Exception
	*/
	public long update(AheadRepayNoticeInfo info) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_AheadRepayForm ");
			sbSQL.append(" SET nStatusID = ? "); //״̬

			if (info.getAmount() > 0)
			{
				sbSQL.append(" ,mAmount = ?");
			}

			if (info.getNextCheckUserID() > 0 || info.getNextCheckUserID() < -1)
			{
				sbSQL.append(" ,nNextCheckUserID = ?"); //��һ�����
			}
			if(info.getPBackDate()!=null)
			{
				sbSQL.append(" ,PAYDATE = to_date('"+DataFormat.formatDate(info.getPBackDate())+"','yyyy-mm-dd')"); //��������
			}
			if(info.getBalanceAmount()>0)
			{
				sbSQL.append(" ,INTERESTAMOUNT = "+info.getBalanceAmount()); //������Ϣ
			}
			//�Ƿ���ǰ����
			if (info.getIsAhead() > 0)
			{
				sbSQL.append(" ,nIsAhead = " + info.getIsAhead()); //�Ƿ���ǰ����
			}
			
			//modify by zwxiao 2010-07-21 �������ж��ظ����ᵼ��ϵͳ�쳣
			//�ǻ�����Ϣ
//			if (info.getBalanceAmount() > 0)
//			{
//				sbSQL.append(" ,interestamount = " + info.getBalanceAmount()); //�Ƿ���ǰ����
//			}
			
			/**2007/03/20 mzh_fu  begin*/
			//������¼�����˷����һ����
			if (info.getNextCheckUserID() > 0)
			{
			    sbSQL.append(" ,nNextCheckLevel = nNextCheckLevel + 1"); //��һ����˼���
			}
			/* ����Ƿ����޸�ʱ,����Ҫ�ı���һ����˼���*/
			/** 2007/03/20 mzh_fu
			//�����޸�
			if (info.getNextCheckLevel() > 0)
			{
				sbSQL.append(" ,nNextCheckLevel = " + info.getNextCheckLevel()); //��һ����˼���
			}
			*/
			/**2007/03/20 mzh_fu  end*/
			
			sbSQL.append(" WHERE id = ? "); //��ǰ����֪ͨ��ID

			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			int index = 1;
			ps.setLong(index++, info.getStatusID());

			if (info.getAmount() > 0)
			{
				ps.setDouble(index++, info.getAmount());
			}

			if (info.getNextCheckUserID() > 0 || info.getNextCheckUserID() < -1)
			{
				ps.setLong(index++, info.getNextCheckUserID());
			}

			ps.setLong(index++, info.getID());

			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;

	}
	
	/**
	 * Modify by leiyang date 2007/07/27
	 * 
	 * @param id
	 * @param statusId
	 * @return
	 * @throws Exception
	 */
	public long updateStatusAndCheckStatus(long id, long statusId) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();

			String sbSQL =  "UPDATE loan_AheadRepayForm SET nStatusID = ? where id = ? and nStatusID = ?";
			
			ps = con.prepareStatement(sbSQL);
			ps.setLong(1, statusId);
			ps.setLong(2, id);
			ps.setLong(3, LOANConstant.AheadRepayStatus.CHECK);

			lResult = ps.executeUpdate();

			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lResult;

	}
	
	/**
	 * @param payID �ſ�֪ͨ��ID
	 * @param rePayID ����֪ͨ��ID
	 * @return ��ѯδ������
	 * @throws RemoteException
	 * @throws IException
	 */
	public double findUnRePayAmountByID(long payID,long batchID,long rePayID) throws Exception {
		double unRePayAmount = 0.0;
		double rePayAmount = 0.0;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try 
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			
			if (payID > 0) 
			{
				sbSQL.append(" select sum(lep.mamount) RepayAmount ");
				sbSQL.append(" from loan_AheadRepayForm lep ");
				sbSQL.append(" where lep.NLOANPAYNOTICEID = ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != ? and lep.nstatusid != 0 ");
				if(batchID>0){
					sbSQL.append(" and  lep.batchid <> "+batchID);
				}
				if(rePayID>0)
				{
					sbSQL.append(" and  lep.ID<>? ");
				}
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1, payID);
				ps.setLong(2, LOANConstant.AheadRepayStatus.REFUSE);
				ps.setLong(3, LOANConstant.AheadRepayStatus.CANCEL);
				ps.setLong(4, LOANConstant.AheadRepayStatus.USED);
				if(rePayID>0)
				{
					ps.setLong(5, rePayID);
				}
				rs = ps.executeQuery();

				if (rs.next()) 
				{
					rePayAmount = rs.getDouble("RepayAmount");
				}
				
				ps.close();
				ps = null;
				LoanPayNoticeDao payDao = new LoanPayNoticeDao();
				LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(payID);
				
				//�ſ����ȥ�����ɻ�����
				unRePayAmount = UtilOperation.Arith.round(UtilOperation.Arith.sub(pinfo.getBalance(), rePayAmount), 2);
			}

			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} 
		catch (Exception e) 
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		} 
		finally 
		{
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (con != null) {
					con.close();
					con = null;
				}
			} 
			catch (Exception e) {
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		
		return unRePayAmount;
	}
	
	/**
	 * ���Һ�ͬ�µ����зſ�֪ͨ��
	 * @param 
	 */
	public Collection queryForRepayNotice(AheadRepayNoticeQueryInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Timestamp tsDate = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 ���ſ�ı������
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setContractID(rs.getLong("nContractID"));
				info.setContractCode(rs.getString("sContractCode"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//�ѷ��Ž��info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	public static void main(String args[])
	{
		try
		{
			;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * ������ǰ����֪ͨ����
	 * @param AheadRepayNoticeInfo
	 * @return long
	 * @throws Exception
	 */
	public long insertBatch(BatchRepayNoticeInfo bInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = "";

		long lID = -1;
		
		long lCode = 0;
		String sCode="";

		try
		{
			if (bInfo != null)
			{
				conn = Database.getConnection();
				/*���Ȼ��LOAN_BATCHREPAYFORM �� MAXID */
				strSQL = " SELECT NVL(MAX(ID)+1,1) ID";
				strSQL += " FROM LOAN_BATCHREPAYFORM ";

				ps = conn.prepareStatement(strSQL);
				rs = ps.executeQuery();
				if (rs.next())
				{
					lID = rs.getLong("ID");
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}

				strSQL = " SELECT ";
				strSQL += " count(id)";
				strSQL += " FROM LOAN_BATCHREPAYFORM where NCONTRACTID=?";
			//	strSQL += " WHERE ID = ?";
				

				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, bInfo.getContractID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lCode = rs.getLong(1)+1;
				}
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				
				if (lCode < 10)
				{
					sCode = "PL00" + lCode;
				}
				else if (lCode < 100 && lCode >= 10)
				{
					sCode = "PL0" + lCode;
				}
				if (lCode >= 100)
				{
					sCode = "PL" + lCode;
				}
				

				System.out.println("������������֪ͨ����" + sCode);

				strSQL = "INSERT INTO LOAN_BATCHREPAYFORM ";
				strSQL += " (ID,nOfficeID,nCurrencyID,nContractID,";
				strSQL += " mAmount,sCode,nInputUserID,";
				strSQL += " dtInputDate,nStatusID,nIsAhead,PAYDATE,REPAYID,nisrepayinterest,minterest)";
				strSQL += " values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?)";

				log4j.info(strSQL);
				ps = conn.prepareStatement(strSQL);
				int n = 1;
				ps.setLong(n++, lID);
				ps.setLong(n++, bInfo.getOfficeID());
				ps.setLong(n++, bInfo.getCurrencyID());
				ps.setLong(n++, bInfo.getContractID());
				ps.setDouble(n++, bInfo.getAmount());
				ps.setString(n++, sCode);
				ps.setLong(n++, bInfo.getInputUserID());
				ps.setLong(n++, LOANConstant.AheadRepayStatus.SUBMIT);
				ps.setLong(n++, bInfo.getIsAhead());
				ps.setTimestamp(n++, bInfo.getPBackDate());
				ps.setString(n++, bInfo.getRePayID());
				ps.setLong(n++, bInfo.getNisrepayinterest());
				ps.setDouble(n++, bInfo.getMinterest());
				ps.executeUpdate();
				ps.close();
				ps = null;

			}
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception e)
		{
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return lID;
	}

	public long updateBatch(BatchRepayNoticeInfo bInfo)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer strSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			strSQL.append(" update LOAN_BATCHREPAYFORM ");
			strSQL.append(" set nStatusID = ? "); //״̬

			if (bInfo.getAmount() > 0)
			{
				strSQL.append(" ,mAmount = " + bInfo.getAmount());
			}
			strSQL.append("  ,paydate = ? "); 
			strSQL.append("  ,minterest = ? "); 
			strSQL.append(" WHERE id = ? "); //��ǰ����֪ͨ��ID
			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			int index = 1;
			ps.setLong(index++, bInfo.getStatusID());
			ps.setTimestamp(index++, bInfo.getPBackDate());
			ps.setDouble(index++, bInfo.getMinterest());
			ps.setLong(index++, bInfo.getID());
			lResult = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult < 0) {
				Log.print(" update loan LOAN_BATCHREPAYFORM error : "
						+ lResult);
				return -1;
			} else {
				return bInfo.getID();
			}
		} catch (Exception e) {
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				throw new IException("Gen_E001");
			}
		}
		
	}
	
	/**
	 * ȡ���������
	 * @author ���ָ� 2008-11-26
	 * @param bInfo
	 * @return
	 * @throws IException
	 */
	public long cancelBatchRepayNotice(BatchRepayNoticeInfo bInfo)throws IException{
		PreparedStatement ps = null;
		Connection conn = null;
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strRpSQL = new StringBuffer();
		long lResult = -1;

		try {
			conn = Database.getConnection();
			//ȡ���������
			strSQL.append(" update LOAN_BATCHREPAYFORM ");
			strSQL.append(" set nStatusID = "+LOANConstant.AheadRepayStatus.CANCEL); //��Ϊ��ȡ��״̬
			strSQL.append(" WHERE id = "+bInfo.getID()); //��������֪ͨ��ID
			log4j.info(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			long lResult1 = ps.executeUpdate();
			//ȡ����������µ����л��
			strRpSQL.append(" update loan_aheadrepayform ");
			strRpSQL.append(" set nStatusID = "+LOANConstant.AheadRepayStatus.CANCEL); //��Ϊ��ȡ��״̬
			strRpSQL.append(" WHERE batchid = "+bInfo.getID()); //��������֪ͨ��ID
			log4j.info(strRpSQL.toString());
			ps = conn.prepareStatement(strRpSQL.toString());
			long lResult2 = ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;

			if (lResult1 < 0 ||lResult2 < 0) {
				Log.print(" update loan LOAN_BATCHREPAYFORM error : "
						+ lResult1+","+ lResult2);
			} else {
				lResult = bInfo.getID();
			}
		} catch (Exception e) {
			throw new IException("Gen_E001");
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				throw new IException("Gen_E001");
			}
		}
		return lResult;
	}
	
	/**
	* �õ���������֪ͨ����ϸ��Ϣ
	* Create Date: 2008-10-7
	* @param lID ��������֪ͨ��ID
	* @return BatchRepayNoticeInfo ��ǰ����֪ͨ����ϸ��Ϣ
	* @exception Exception
	*/
	public BatchRepayNoticeInfo findBatchAheadRepayByID(long lID) throws Exception
	{		BatchRepayNoticeInfo info = new BatchRepayNoticeInfo();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.*,");
			sbSQL.append(" b.id as ContractID,b.sContractCode,");
			sbSQL.append(" b.nIntervalNum,b.mExamineAmount,b.mAdjustRate,b.ntypeid,b.nsubtypeid,");
		    //sbSQL.append(" c.sCode as PayCode,c.mInterestRate,");
			//sbSQL.append(" c.mAmount as PayAmount,");
			sbSQL.append(" d.sName as clientName,e.sName as userName");          
			sbSQL.append(" FROM loan_batchrepayform a,loan_ContractForm b,");
			sbSQL.append(" Client d,userInfo e");
                       
			sbSQL.append(" WHERE b.id = a.nContractID");
			sbSQL.append(" AND b.nBorrowClientID=d.ID(+)");
			sbSQL.append(" AND a.nInputUserID = e.id(+)");
			sbSQL.append(" AND a.id = ?");

            sbSQL.append(" order by a.id desc ");//2004-01-15 ninh
            
			System.out.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
             
			rs = ps.executeQuery();
			
			ContractDao dao = new ContractDao();
			LoanPayNoticeDao payDao = new LoanPayNoticeDao();

			if (rs.next())
			{
				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("sCode")); //֪ͨ�����
				info.setClientName(rs.getString("clientName")); //��λ
				info.setContractCode(rs.getString("sContractCode")); //��ͬ���
				info.setContractID(rs.getLong("ContractID")); //��ͬID
				info.setIntervalNum(rs.getLong("nIntervalNum")); //��������
				info.setContractAmount(rs.getDouble("mExamineAmount")); //��ͬ���
				info.setLoanType(rs.getLong("ntypeid"));
				info.setLoanSubType(rs.getLong("nsubtypeid"));
				info.setPBackDate(rs.getTimestamp("payDate"));
			//	info.setBalanceAmount(rs.getDouble("interestAmount"));
               
                
				ContractAmountInfo cInfo = dao.getLateAmount(info.getContractID());
				info.setContractBalance(cInfo.getBalanceAmount()); //��ͬ���
			//	info.setLetoutNoticeCode(rs.getString("PayCode")); //�ſ�֪ͨ�����
				
			//	info.setLetoutNoticeRate(payDao.getLatelyRate(rs.getLong("PayID"),null));//�ſ�֪ͨ��ִ������
				
				//�ſ�֪ͨ������,��ǰ��������
			//	info.setLetoutNoticeAmount(rs.getDouble("PayAmount")); //�ſ�֪ͨ�����
			//	info.setLetoutNoticeID(rs.getLong("PayID")); //�ſ�֪ͨ��ID
			//	LoanPayNoticeInfo pinfo = payDao.findLoanPayNoticeByID(rs.getLong("PayID"));
			//	info.setLetoutNoticeBalance(pinfo.getBalance()); //�ſ�֪ͨ�����
				
				info.setAmount(rs.getDouble("mAmount")); //��ǰ������
				info.setInputUserID(rs.getLong("nInputUserID")); //¼����ID
				info.setInputUserName(rs.getString("userName")); //¼����
				info.setInputDate(rs.getTimestamp("dtInputDate")); //¼��ʱ��
				info.setIsAhead(rs.getLong("nIsAhead"));//�Ƿ���ǰ����
				info.setStatusID(rs.getLong("nStatusID")); //״̬
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				info.setNisrepayinterest(rs.getLong("nisrepayinterest"));//�Ƿ�黹��Ϣ
				info.setMinterest(rs.getDouble("minterest")); //������������Ϣ
				//״̬ 

			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}
	/**
	 * ������������µ����зſ�֪ͨ��
	 * @param 
	 */
	public Collection querySaveForRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 ���ſ�ı������
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//�ѷ��Ž��info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	* �õ�����֪ͨ����ϸ��Ϣ
	* Create Date: 2008-10-7
	* @param lID �ſ�֪ͨ��ID
	* @return AheadRepayNoticeInfo ��ǰ����֪ͨ����ϸ��Ϣ
	* @exception Exception
	*/
	public AheadRepayNoticeInfo findAheaeInfoByPayID(LoanPayNoticeInfo lInfo) throws Exception
	{		
		AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.* ");        
			sbSQL.append(" FROM loan_aheadrepayform a,loan_payform b,loan_batchrepayform c ");
			sbSQL.append(" WHERE a.nloanpaynoticeid = b.id ");
			sbSQL.append(" and a.batchid = c.id ");
			sbSQL.append(" and b.id = ?");
			sbSQL.append(" and c.id = ?");
			sbSQL.append(" and a.nstatusid not in( "+LOANConstant.AheadRepayStatus.CANCEL+")");
			sbSQL.append(" and a.batchid is not null ");
            sbSQL.append(" order by a.id desc ");            
			System.out.print(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lInfo.getID());  
			ps.setLong(2, lInfo.getActionID());
			rs = ps.executeQuery();
			if (rs.next())
			{				
				info.setID(rs.getLong("id"));
				info.setCode(rs.getString("SCode")); 
				info.setPBackDate(rs.getTimestamp("payDate"));  //�������� 		
				info.setAmount(rs.getDouble("mAmount")); //��ǰ������
				info.setIsAhead(rs.getLong("nIsAhead"));//�Ƿ���ǰ����
				info.setStatusID(rs.getLong("nStatusID")); //״̬
				info.setStatus(LOANConstant.AheadRepayStatus.getName(info.getStatusID())); 
				info.setBalanceAmount(rs.getDouble("interestamount")); //��Ϣ  2010,6,9 fhx
				
				//״̬ 
			}

			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				log4j.error(e.toString());
				throw new IException("Gen_E001");
			}
		}
		return info;

	}
	/**
	 * ������������µ����зſ�֪ͨ��
	 * @param 
	 */
	public Collection findAheadInfoByBatchID(long id) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.* from loan_aheadrepayform a where a.batchid = ? ");
			sb.append(" and a.nstatusid not in(  "+LOANConstant.AheadRepayStatus.CANCEL+")" );		
			System.out.println(sb.toString());
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1,id);   
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("ID"));			
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
 			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	 * ������������µ����зſ�֪ͨ��
	 * @param 
	 */
	public Collection queryAllRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,b.ID nContractID,b.sContractCode,b.NOFFICEID, ");
			sb.append(" round(c.mRate,6) as mrate,");
			sb.append(" d.sname as sInputName,e.saccountno as sGrantCurrentAccount,e.sname as sGrantName,");
			sb.append(" f.sname as sBorrowClientName,");
			sb.append(" b.mloanamount,b.NINTERVALNUM,g.mrate as mContractRate,b.SLOANPURPOSE,");
			sb.append(" f.SZIPCODE,f.sPhone,f.sAddress,b.ntypeid,h.sname as sConsignClientname, ");
			sb.append(" i.mInterest as subInterest,i.mBalance as subBalance,j.sname as accname,j.sCode as acccode");
			sb.append(" from loan_payform a,loan_contractform b,loan_interestrate c,userinfo d,");
			sb.append("  sett_account e,client f,loan_interestrate g,client h ");
			sb.append("  ,sett_subaccount i ,sett_Branch j");
			sb.append("  where a.ncontractid = b.id(+) ");
			sb.append("  and a.nBankInterestID = c.id(+) ");
			sb.append("  and a.ninputuserid = d.id(+) ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and b.nborrowclientid = f.id(+) ");
			sb.append("  and b.NBANKINTERESTID = g.id(+) ");
			sb.append("  and b.nconsignclientid = h.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and a.nAccountBankID = j.id(+)");
			//update by xfma 2008-11-25 ���ſ�ı������
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setDrawNoticeID(rs.getLong("NDRAWNOTICEID"));
				info.setLoanClientName(rs.getString("sBorrowClientName"));
				info.setLoanAmount(rs.getDouble("mLoanAmount"));
				info.setIntervalNum(rs.getLong("NINTERVALNUM"));
				//�ѷ��Ž��info.set
				info.setContractRate(rs.getDouble("mContractRate"));
				info.setLoanPurpose(rs.getString("sLoanPurpose"));
				info.setOutDate(rs.getTimestamp("dtoutdate"));
				info.setCode(rs.getString("sCode"));
				info.setLoanZipCode(rs.getString("sZipCode"));
				info.setLoanPhone(rs.getString("sPhone"));
				info.setLoanAddress(rs.getString("sAddress"));
				info.setLoanTypeID(rs.getLong("nTypeID"));
				info.setGrantTypeID(rs.getLong("nGrantTypeID"));
				info.setGrantCurrentAccount(rs.getString("sGrantCurrentAccount"));
				info.setGrantCurrentName(rs.getString("sGrantName"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setConsignClientName(rs.getString("sConsignClientName"));
				info.setConsignAccount(rs.getString("sConsignAccount"));
				//info.setInterestRate(rs.getDouble("mRate"));
				info.setCommissionRate(rs.getDouble("Mcommissionrate"));
				info.setSuretyFeeRate(rs.getDouble("mSuretyFeeRate"));
				info.setStart(rs.getTimestamp("dtStart"));
				info.setInputDate(rs.getTimestamp("DTINPUTDATE"));
				info.setEnd(rs.getTimestamp("dtEnd"));
				info.setCompanyLeader(rs.getString("sCompanyLeader"));
				info.setDepartmentLeader(rs.getString("sDepartmentLeader"));
				info.setCheckPerson(rs.getString("sCheckPerson"));
				info.setHandlingPerson(rs.getString("sHandlingPerson"));
				info.setInterest(rs.getDouble("subInterest"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setAccountBankID(rs.getLong("nAccountBankID"));
				info.setAccountBankCode(rs.getString("acccode"));
				info.setAccountBankName(rs.getString("accname"));
				info.setGrantCurrentAccountID(rs.getLong("NGRANTCURRENTACCOUNTID"));
				info.setReceiveClientCode(rs.getString("sReceiveAccount"));
				info.setReceiveClientName(rs.getString("sReceiveClientName"));
				info.setRemitinProvince(rs.getString("sRemitinProvince"));
				info.setRemitinCity(rs.getString("sRemitinCity"));
				info.setRemitBank(rs.getString("sRemitBank"));
				info.setBankInterestID(rs.getLong("nBankInterestID"));
				info.setLoanAccount(rs.getString("sloanaccount"));
				info.setInterestRate(dao.getLatelyRate(info.getID(), null));
				info.setStatusID(rs.getLong("nStatusID"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setInputUserName(rs.getString("sInputName"));
				info.setWTInterestRate(rs.getDouble("minterestrate"));
				info.setReceiveAccount(rs.getString("sReceiveAccount"));
				//System.out.println("%%%%%%%% info.getOutDate()="+info.getOutDate());
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setMbalance(rs.getDouble("subBalance"));
				info.setNextCheckLevel(rs.getLong("nNextCheckLevel"));
				info.setPreAdjustRate(rs.getDouble("MADJUSTRATE"));
				info.setPreStaidAdjustRate(rs.getDouble("MSTAIDADJUSTRATE"));
				info.setPreBasicInterestRate(rs.getDouble("mRate"));
				info.setInterestTypeID(rs.getLong("nInterestTypeID"));
				info.setLiborRateID(rs.getLong("nLiborRateID"));
				info.setIntervalNoticeNum(rs.getDouble("nIntervalNoticeNum"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	/**
	 * ������������µ����зſ�֪ͨ��
	 * @param 
	 */
	public Collection queryDoApprovalRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Timestamp tsDate = null;
		LoanPayNoticeDao dao = new LoanPayNoticeDao();
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			tsDate = DataFormat.getDateTime(con);
			StringBuffer sb = new StringBuffer();
			sb.append(" select a.*,m.BATCHID, ");
			sb.append(" round(a.minterestrate,6) as mrate ,i.mBalance as subBalance ");
			sb.append(" from loan_payform a,loan_aheadrepayform m,loan_batchrepayform n,");
			sb.append(" sett_account e, ");
			sb.append(" sett_subaccount i ");
			sb.append("  where  m.batchid = n.id ");
			sb.append("  and m.ncontractid = n.ncontractid ");
			sb.append("  and m.nloanpaynoticeid = a.id ");
			sb.append("  and n.ncontractid = a.ncontractid ");
			sb.append("  and a.Ngrantcurrentaccountid = e.id(+) ");
			sb.append("  and i.AL_nLoanNoteID(+) = a.ID and i.nStatusID(+) = ? ");
			sb.append("  and a.nstatusid = "+LOANConstant.LoanPayNoticeStatus.USED);
			sb.append("  and a.ncontractid = ? and  n.id = ?");
			sb.append("  and m.nStatusID != "+LOANConstant.AheadRepayStatus.CANCEL);
			sb.append("  and n.nStatusID != "+LOANConstant.AheadRepayStatus.CANCEL);
			//update by xfma 2008-11-26 ���ſ�ı������
			sb.append("  order by a.SCODE");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, Constant.RecordStatus.VALID);
			ps.setLong(2, qInfo.getContractID());
			ps.setLong(3, qInfo.getID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				LoanPayNoticeInfo info = new LoanPayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setCode(rs.getString("sCode"));
				info.setInterestRate(rs.getDouble("mrate"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setBalance(rs.getDouble("subBalance"));
				info.setActionID(rs.getLong("batchID"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
	
	/**
	 * ������������µ�������ʹ�û���֪ͨ��
	 * @author ���ָ� 2008-11-16
	 * @param BatchRepayNoticeInfo
	 */
	public Collection queryUsedRepayNotice(BatchRepayNoticeInfo qInfo) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v = new Vector();
		try
		{
			con = Database.getConnection();
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from loan_aheadrepayform m ");
			sb.append("  where m.batchid = ? ");
			sb.append("  and m.nstatusid = "+LOANConstant.AheadRepayStatus.USED);
			sb.append("  order by m.sCode");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, qInfo.getID());
			System.out.println(sb.toString());
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				AheadRepayNoticeInfo info = new AheadRepayNoticeInfo();
				info.setID(rs.getLong("ID"));
				info.setCode(rs.getString("sCode"));
				info.setLetoutNoticeID(rs.getLong("NLOANPAYNOTICEID"));
				info.setContractID(rs.getLong("NCONTRACTID"));
				info.setAmount(rs.getDouble("mAmount"));
				info.setBatchID(rs.getLong("batchID"));
				v.addElement(info);
			}			
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new Exception("remote exception : " + e.toString());
		}
		finally
		{
			try
			{
				if (rs != null)
				{
					rs.close();
					rs = null;
				}
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (con != null)
				{
					con.close();
					con = null;
				}
			}
			catch (Exception e)
			{
				throw new Exception("remote exception : " + e.toString());
			}
		}
		return v;
	}
}
