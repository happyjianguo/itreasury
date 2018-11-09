package com.iss.itreasury.ebank.obdiscountapply.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.*;
import com.iss.itreasury.util.*;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.util.*;
import com.iss.itreasury.loan.util.*;
import com.iss.itreasury.ebank.obdiscountapply.dataentity.*;
/**
 * @author gqzhang
 *��������DAO To change this generated comment edit the template variable
 *"typecomment": Window>Preferences>Java>Templates. To enable and disable the
 *creation of type comments go to Window>Preferences>Java>Code Generation.
 */
public class OBDiscountApplyDao extends SettlementDAO
{
	private Log4j log4j = new Log4j(Constant.ModuleType.EBANK, this); //
	public OBDiscountApplyDao()
	{
		super();
	}
	/**
	 * Method saveDiscount1.
	 * ��������������Ϣ����һ�������뵥λ����Ϣ��
	 *�����״̬Ϊ��׫д
	 *������OB_Loan
	 *���أ���������ID
	 * @param info
	 * @return long
	 */
	public long saveDiscount1(DiscountMainInfo info) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sbSQL = null;
		long lMaxID = -1;
		long lTypeID = OBConstant.LoanInstrType.DISCOUNT;
		long lModuleID = OBConstant.SubModuleType.LOAN;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��saveDiscount1~~~~~~~~~~");
			conn = getConnection();
			//��һ���ݴ�
			if (info.getID() <= 0)
			{
				//��ȡ����ID
				sbSQL = new StringBuffer();
				sbSQL = sbSQL.append("select nvl(max(ID)+1,1) from OB_LOAN");
				ps = conn.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if (rs.next())
				{
					lMaxID = rs.getLong(1);
					info.setID(lMaxID);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				Log.print("lMaxID:" + lMaxID);
				//ָ����
				String strInstructionNo = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
				//String strInstructionNo = "1111";
				sbSQL = new StringBuffer();
				sbSQL.append(" insert into OB_Loan \n");
				sbSQL.append(" ( \n");
				sbSQL.append(" ID,NTYPEID,NCURRENCYID,NOFFICEID,SINSTRUCTIONNO,NBORROWCLIENTID,NINPUTUSERID,DTINPUTDATE,NSTATUSID");
				sbSQL.append(" ) \n");
				sbSQL.append(" values(?,?,?,?,?,?,?,?,?) \n");
				Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
				Log.print(sbSQL.toString());
				Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
				ps = conn.prepareStatement(sbSQL.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, info.getID());
				ps.setLong(nIndex++, lTypeID);
				ps.setLong(nIndex++, info.getCurrencyID());
				ps.setLong(nIndex++, info.getOfficeID());
				ps.setString(nIndex++, strInstructionNo);
				ps.setLong(nIndex++, info.getClientID());
				ps.setLong(nIndex++, info.getUserID());
				ps.setTimestamp(nIndex++, info.getDate());
				ps.setLong(nIndex++, info.getStatusID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
			{
				sbSQL = new StringBuffer();
				sbSQL.append(" update OB_Loan set NTYPEID=?,NCURRENCYID=?,NOFFICEID=?,NBORROWCLIENTID=?,NINPUTUSERID=?,DTINPUTDATE=?,NSTATUSID=? where id=?");
				Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
				Log.print(sbSQL.toString());
				Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
				ps = conn.prepareStatement(sbSQL.toString());
				int nIndex = 1;
				ps.setLong(nIndex++, lTypeID);
				ps.setLong(nIndex++, info.getCurrencyID());
				ps.setLong(nIndex++, info.getOfficeID());
				ps.setLong(nIndex++, info.getClientID());
				ps.setLong(nIndex++, info.getUserID());
				ps.setTimestamp(nIndex++, info.getDate());
				ps.setLong(nIndex++, info.getStatusID());
				ps.setLong(nIndex++, info.getID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}
	/**
	 * Method findDiscountByID.
	 * �������������ID��ѯ�����������Ϣ������Ʊ�ݵ���Ϣ
	 * @param lDiscountID
	 * @return DiscountInfo
	 * @throws Exception
	 */
	public DiscountInfo findDiscountByID(long lDiscountID) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountInfo lai = new DiscountInfo();
		try
		{
			Log.print("~~~~~~~~~~~���뷽��findDiscountByID~~~~~~~~~~");
			con = Database.getConnection();
			strSQL = " select a.*, ";
			strSQL += " c.sName sClientName, c.sAccount, d.sName sInputUserName, nvl(e.sContractCode,'') sContractCode ";
			strSQL += " from OB_Loan a,Client c,OB_User d,Loan_ContractForm e ";
			strSQL += " where a.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and a.ID=e.nLoanID(+) and a.ID=? ";
			Log.print("======================");
			Log.print(strSQL);
			Log.print("======================");
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setID(lDiscountID); //����ID��ʶ
				lai.setDiscountCode(rs.getString("SINSTRUCTIONNO")); //���ֱ��
				lai.setContractCode(rs.getString("sContractCode")); //���ֺ�ͬ���
				lai.setStatusID(rs.getLong("nStatusID")); //״̬
				//���뵥λ
				lai.setApplyClientID(rs.getLong("nBorrowClientID")); //���뵥λ���
				lai.setApplyClientName(rs.getString("sClientName")); //���뵥λ����
				lai.setApplyLOfficeAccount(rs.getString("sAccount")); //���뵥λ�������´��˺�
				lai.setApplyDiscountAmount(rs.getDouble("mLoanAmount")); //�������ֽ��
				lai.setExamineAmount(rs.getDouble("mExamineAmount")); //��׼���
				lai.setCheckAmount(rs.getDouble("mCheckAmount")); //�˶����
				lai.setDiscountRate(rs.getDouble("mDiscountRate")); //��������
				lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //������Ϣ
				lai.setDiscountPurpose(rs.getString("sLoanPurpose")); //������;
				lai.setDiscountReason(rs.getString("sLoanReason")); //����ԭ��
				lai.setDiscountDate(rs.getTimestamp("dtDiscountDate")); //���ּ�Ϣ��
				lai.setDiscountStartDate(rs.getTimestamp("dtDiscountDate")); //������
				lai.setDiscountEndDate(rs.getTimestamp("dtEndDate")); //���ֵ�����
				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				lai.setDocumentType(rs.getString("SDOCUMENTTYPE"));
				lai.setIsPurchaserInterest(rs.getLong("ISPURCHASERINTEREST"));//�Ƿ��򷽸�Ϣ
				lai.setDiscountClientID(rs.getLong("DISCOUNTCLIENTID"));//��Ʊ��ID
				lai.setDiscountClientName(rs.getString("DISCOUNTCLIENTNAME"));//��Ʊ������ 
				lai.setSubTypeId(rs.getLong("nsubtypeid"));//������ID
				lai.setTypeID(rs.getLong("ntypeid"));//����ID
				if (lai.getStatusID() == OBConstant.LoanInstrStatus.SUBMIT && lai.getNextCheckUserID() != lai.getInputUserID())
				{
					lai.setIsCheck(Constant.YesOrNo.YES); //����˹�
				}
				else
				{
					lai.setIsCheck(Constant.YesOrNo.NO); //δ��˹�
				}
				//����Ʊ��
				lai.setBankAccepPO(rs.getLong("nBankAcceptPO")); //���гжһ�Ʊ��������
				lai.setBizAcceptPO(rs.getLong("nBizAcceptPO")); //��ҵ�жһ�Ʊ��������
				lai.setApplyDiscountPO(lai.getBankAccepPO() + lai.getBizAcceptPO()); //�������ֻ�Ʊ��������
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			strSQL =
				" select sum(nvl(BankAmount,0))+sum(nvl(BizAmount,0)) BillAmount,sum(BankCount) BankCount,sum(BizCount) BizCount from ( "
					+ " select count(*) BankCount,sum(nvl(mAmount,0)) BankAmount,0 BizCount,0 BizAmount from OB_DiscountBill where nLoanID=? and nStatusID=? " 
                    + " and nAcceptPoType=? " 
                    //+ " and to_char(dtEnd,'yyyy-mm-dd') >=? and  to_char(DTCREATE,'yyyy-mm-dd') <=? "
					+ " union all "
					+ " select 0 BankCount,0 BankAmount,count(*) BizCount,sum(nvl(mAmount,0)) BizAmount from OB_DiscountBill where nLoanID=? and nStatusID=? and nAcceptPoType=? " 
                    //+ " and to_char(dtEnd,'yyyy-mm-dd') >=? and  to_char(DTCREATE,'yyyy-mm-dd') <=? "
					+ " ) ";
			Log.print(strSQL);
			Log.print("��������:" + lai.getDiscountDate());
			ps = con.prepareStatement(strSQL);
			int nIndex = 1;
			ps.setLong(nIndex++, lDiscountID);
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			ps.setLong(nIndex++, OBConstant.DraftType.BANK);
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			ps.setLong(nIndex++, lDiscountID);
			ps.setLong(nIndex++, Constant.RecordStatus.VALID);
			ps.setLong(nIndex++, OBConstant.DraftType.BIZ);
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			//ps.setString(nIndex++, DataFormat.getDateString(lai.getDiscountDate()));
			rs = ps.executeQuery();
			if (rs.next())
			{
				lai.setBillAmount(rs.getDouble("BillAmount")); //�������ֻ�Ʊ�ܽ��
				lai.setBankCount(rs.getLong("BankCount")); //���гжһ�Ʊ��������
				lai.setBizCount(rs.getLong("BizCount")); //��ҵ�жһ�Ʊ��������
				lai.setBillCount(rs.getLong("BankCount") + rs.getLong("BizCount")); //�������ֻ�Ʊ��������
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return lai;
	}
	/**
	 * Method saveDiscount2.
	 * ��������������Ϣ���ڶ���������Ʊ�ݵ���Ϣ��
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscount2(DiscountBillStatInfo info) throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��saveDiscount2~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update OB_LOAN set NINPUTUSERID=?,DTINPUTDATE=?,nBankAcceptPO=?,nBizAcceptPO=?,mLoanAmount=?,sLoanReason=?,sLoanPurpose=?,DTDISCOUNTDATE=?,DTSTARTDATE=?,NSUBTYPEID=?,ISPURCHASERINTEREST=?,DISCOUNTCLIENTID=?,DISCOUNTCLIENTNAME=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			int nIndex = 1;
			ps.setLong(nIndex++, info.getInputUserID());
			ps.setTimestamp(nIndex++, info.getDate());
			ps.setLong(nIndex++, info.getBankAcceptPO());
			ps.setLong(nIndex++, info.getBizAcceptPO());
			ps.setDouble(nIndex++, info.getAmount());
			ps.setString(nIndex++, info.getReason());
			ps.setString(nIndex++, info.getPurpose());
			ps.setTimestamp(nIndex++, info.getDiscountStartDate());
            ps.setTimestamp(nIndex++, info.getDiscountStartDate());
            ps.setLong(nIndex++, info.getSubTypeId());//������
            ps.setLong(nIndex++, info.getIsPurchaserInterest());//�Ƿ��򷽸�Ϣ
            ps.setLong(nIndex++, info.getDiscountClientID());//��Ʊ��ID
            ps.setString(nIndex++, info.getDiscountClientName());//��Ʊ������
			ps.setLong(nIndex++, info.getID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getID();
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * �������������¼״̬
	 * @see com.iss.itreasury.dao.SettlementDAO#updateStatus(long, long)
	 */
	public long updateStatus(long lDiscountID, long lStatusID) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��updateStatus~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update OB_LOAN set nStatusID=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lDiscountID);
			if (ps.executeUpdate() > 0)
			{
				lResult = 1;
			}
			else
			{
				lResult = 0;
			}
			ps.close();
			ps = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	/**
	 * Method deleteDiscountBill
	 * ����ɾ������Ʊ����Ϣ���߼�ɾ����.
	 * @param lDiscountBillID
	 * @return long 1-ɾ���ɹ� 0ɾ��ʧ��
	 * @throws Exception
	 */
	public long deleteDiscountBill(long lDiscountBillID[]) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		int i = 0;
		long lBillID = -1;
		long lDiscountApplyID = -1;
		long nStatusID = -1;
		long nSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��deleteDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			for (i = 0; i < lDiscountBillID.length; i++)
			{
				if (lDiscountBillID[i] > 0)
				{
					lBillID = lDiscountBillID[i];
				}
			}
			//���������ʾ
			strSQL = " select nLoanID from OB_DiscountBill where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lBillID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lDiscountApplyID = rs.getLong("nLoanID");
			}
			Log.print(lDiscountApplyID);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			for (i = 0; i < lDiscountBillID.length; i++)
			{
				if (lDiscountBillID[i] > 0)
				{
					//���¼�¼
					strSQL = " update OB_DiscountBill set nStatusID=? where ID=? ";
					Log.print(strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, Constant.RecordStatus.INVALID);
					ps.setLong(2, lDiscountBillID[i]);
					ps.executeUpdate();
					ps.close();
					ps = null;
					//�������к�
					strSQL = " select nSerialNo from OB_DiscountBill where ID=? ";
					Log.print(strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setLong(1, lDiscountBillID[i]);
					rs = ps.executeQuery();
					if (rs.next())
					{
						nSerialNo = rs.getLong("nSerialNo");
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					if (lDiscountApplyID > 0 && nSerialNo > 0)
					{
						strSQL = " update OB_DiscountBill set nSerialNo=nSerialNo-1 where nSerialNo>? and nLoanID=? ";
						Log.print("�������к�");
						Log.print(strSQL);
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, nSerialNo);
						ps.setLong(2, lDiscountApplyID);
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
				}
			}
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lDiscountBillID.length);
	}
	/**
	 * Method findDiscountBillByDiscountID.
	 * �������������ʶ��ѯ����Ʊ��
	 * ��ѯ��OB_DiscountBill ���أ���DiscountBillInfo��ɵ� Collection
	 * @param discountBillQueryInfo
	 * @return Collection
	 */
	public Vector findDiscountBillByDiscountID(DiscountBillQueryInfo discountBillQueryInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strUpdate = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		Timestamp tsEnd = null; //��������
		String strEnd = ""; //��������
		int nDays = 0; //ʵ����������
		double dAmount = 0; //Ʊ�ݽ��
		double dAccrual = 0; //������Ϣ
		double dRealAmount = 0; //ʵ�����ֽ��
		double dTotalAmount = 0; //��Ʊ�ݽ��
		double dTotalAccrual = 0; //��Ʊ����Ϣ
		double dTotalRealAmount = 0; //��Ʊ��ʵ�����
		try
		{
			Log.print("~~~~~~~~~~~���뷽��findDiscountBillByDiscountID~~~~~~~~~~");
			con = Database.getConnection();
			Log.print("���ֱ�ʶ��" + discountBillQueryInfo.getDiscountID());
			Log.print("������  ��" + discountBillQueryInfo.getDate());
			Log.print("======��ʼУ�������գ�������Ӧ��С������Ʊ�ݵ�����======");
			//����������У�飬Ӧ�ô�������Ʊ�ݵ�����
			strSQL = " select count(*) from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("==============Ʊ��������==============" + lRecordCount);
				//Log.print("==============Ʊ���ܽ��==============" + dTotalAmount);
			}
			//�����¼��С�ڵ���0
			if (lRecordCount <= 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				return null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("==============������У�����==============");
			Log.print("==============��ʼ��ѯƱ���������ܽ��=============");
			//�����¼����
			strSelect = " select count(*),sum(nvl(mAmount,0)) ";
			strSQL = " from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nLoanID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============Ʊ��������==============" + lRecordCount);
			Log.print("==============Ʊ���ܽ��==============" + dTotalAmount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("=============������ѯƱ���������ܽ��=========");
			lPageCount = lRecordCount / discountBillQueryInfo.getPageLineCount();
			if ((lRecordCount % discountBillQueryInfo.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			//������
			switch ((int) discountBillQueryInfo.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo";
					break;
				case 2 :
					strSQL += " order by sUserName";
					break;
				case 3 :
					strSQL += " order by sBank";
					break;
				case 4 :
					strSQL += " order by nIsLocal";
					break;
				case 5 :
					strSQL += " order by dtCreate";
					break;
				case 6 :
					strSQL += " order by dtEnd";
					break;
				case 7 :
					strSQL += " order by sCode";
					break;
				case 8 :
					strSQL += " order by mAmount";
					break;
				case 9 :
					strSQL += " order by nAddDays";
					break;
				case 10 :
					strSQL += " order by nAcceptPOType";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (discountBillQueryInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			//��������Ľ����
			lRowNumStart = (discountBillQueryInfo.getPageNo() - 1) * discountBillQueryInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + discountBillQueryInfo.getPageLineCount() - 1;
			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print("��ҳ��ѯ��ʼ");
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountApplyID(discountBillQueryInfo.getDiscountID()); //���ֱ�ʾ
				dbill.setDiscountBillID(rs.getLong("ID")); //Ʊ�ݱ�ʾ
				dbill.setSerialNo(rs.getLong("nSerialNo")); //���к�
				dbill.setDiscountCredenceID(rs.getLong("NDISCOUNTCREDENCEID")); //����ƾ֤��ʶ
				dbill.setUser(rs.getString("sUserName")); //ԭʼ��Ʊ��
				dbill.setBank(rs.getString("sBank")); //�ж�����
				dbill.setIsLocal(rs.getLong("nIsLocal")); //�ж��������ڵأ��Ƿ��ڱ��أ�
				dbill.setCreate(rs.getTimestamp("dtCreate")); //��Ʊ��
				dbill.setEnd(rs.getTimestamp("dtEnd")); //������
				dbill.setCode(rs.getString("sCode")); //��Ʊ����
				dbill.setAmount(rs.getDouble("mAmount")); //��Ʊ���
				dbill.setAddDay(rs.getLong("nAddDays")); //�ڼ������Ӽ�Ϣ����
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOType")); //��Ʊ����
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //���ֵ�λֱ��ǰ��
				dbill.setNLoanID(rs.getLong("nLoanID"));//��������ָ��ID
				//Ʊ��ʵ�������Ҫ������
				v.add(dbill);
			}
			Log.print("��ҳ��ѯ����");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		Log.print("======�˳����ּ�Ϣ(findDiscountBillByDiscountID)����======");
		return (v.size() > 0 ? v : null);
	}
	
	
	/**
	 * Method findDiscountBillByDiscountID.
	 * �������������ʶ��ѯ����Ʊ��
	 * ��ѯ��OB_DiscountBill ���أ���DiscountBillInfo��ɵ� Collection
	 * @param discountBillQueryInfo
	 * @return Collection
	 */
	public Vector findByID(DiscountBillQueryInfo discountBillQueryInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		String strUpdate = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		Timestamp tsEnd = null; //��������
		String strEnd = ""; //��������
		int nDays = 0; //ʵ����������
		double dAmount = 0; //Ʊ�ݽ��
		double dAccrual = 0; //������Ϣ
		double dRealAmount = 0; //ʵ�����ֽ��
		double dTotalAmount = 0; //��Ʊ�ݽ��
		double dTotalAccrual = 0; //��Ʊ����Ϣ
		double dTotalRealAmount = 0; //��Ʊ��ʵ�����
		try
		{
			Log.print("~~~~~~~~~~~���뷽��findDiscountBillByDiscountID~~~~~~~~~~");
			con = Database.getConnection();
			Log.print("���ֱ�ʶ��" + discountBillQueryInfo.getDiscountID());
			Log.print("������  ��" + discountBillQueryInfo.getDate());
			Log.print("======��ʼУ�������գ�������Ӧ��С������Ʊ�ݵ�����======");
			//����������У�飬Ӧ�ô�������Ʊ�ݵ�����
			strSQL = " select count(*) from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				Log.print("==============Ʊ��������==============" + lRecordCount);
				//Log.print("==============Ʊ���ܽ��==============" + dTotalAmount);
			}
			//�����¼��С�ڵ���0
			if (lRecordCount <= 0)
			{
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				con.close();
				con = null;
				return null;
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("==============������У�����==============");
			Log.print("==============��ʼ��ѯƱ���������ܽ��=============");
			//�����¼����
			strSelect = " select count(*),sum(nvl(mAmount,0)) ";
			strSQL = " from OB_DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID=" + discountBillQueryInfo.getDiscountID();
			//strSQL = strSQL + " and to_char(dtEnd,'yyyy-mm-dd') >= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			//strSQL = strSQL + " and to_char(DTCREATE,'yyyy-mm-dd') <= '" + DataFormat.getDateString(discountBillQueryInfo.getDate()) + "'";
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
			}
			Log.print("==============Ʊ��������==============" + lRecordCount);
			Log.print("==============Ʊ���ܽ��==============" + dTotalAmount);
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("=============������ѯƱ���������ܽ��=========");
			lPageCount = lRecordCount / discountBillQueryInfo.getPageLineCount();
			if ((lRecordCount % discountBillQueryInfo.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			//������
			switch ((int) discountBillQueryInfo.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo";
					break;
				case 2 :
					strSQL += " order by sUserName";
					break;
				case 3 :
					strSQL += " order by sBank";
					break;
				case 4 :
					strSQL += " order by nIsLocal";
					break;
				case 5 :
					strSQL += " order by dtCreate";
					break;
				case 6 :
					strSQL += " order by dtEnd";
					break;
				case 7 :
					strSQL += " order by sCode";
					break;
				case 8 :
					strSQL += " order by mAmount";
					break;
				case 9 :
					strSQL += " order by nAddDays";
					break;
				case 10 :
					strSQL += " order by nAcceptPOType";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (discountBillQueryInfo.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			//��������Ľ����
			lRowNumStart = (discountBillQueryInfo.getPageNo() - 1) * discountBillQueryInfo.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + discountBillQueryInfo.getPageLineCount() - 1;
//			strSQL = "select * " + strSQL;
//			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
//			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			strSQL = " select * " + strSQL ;
			Log.print("��ҳ��ѯ��ʼ");
			System.out.println(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountApplyID(discountBillQueryInfo.getDiscountID()); //���ֱ�ʾ
				dbill.setDiscountBillID(rs.getLong("ID")); //Ʊ�ݱ�ʾ
				dbill.setSerialNo(rs.getLong("nSerialNo")); //���к�
				dbill.setDiscountCredenceID(rs.getLong("NDISCOUNTCREDENCEID")); //����ƾ֤��ʶ
				dbill.setUser(rs.getString("sUserName")); //ԭʼ��Ʊ��
				dbill.setBank(rs.getString("sBank")); //�ж�����
				dbill.setIsLocal(rs.getLong("nIsLocal")); //�ж��������ڵأ��Ƿ��ڱ��أ�
				dbill.setCreate(rs.getTimestamp("dtCreate")); //��Ʊ��
				dbill.setEnd(rs.getTimestamp("dtEnd")); //������
				dbill.setCode(rs.getString("sCode")); //��Ʊ����
				dbill.setAmount(rs.getDouble("mAmount")); //��Ʊ���
				dbill.setAddDay(rs.getLong("nAddDays")); //�ڼ������Ӽ�Ϣ����
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOType")); //��Ʊ����
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //���ֵ�λֱ��ǰ��
				dbill.setNLoanID(rs.getLong("nLoanID"));//��������ָ��ID
				//Ʊ��ʵ�������Ҫ������
				v.add(dbill);
			}
			Log.print("��ҳ��ѯ����");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		Log.print("======�˳����ּ�Ϣ(findByID)����======");
		return (v.size() > 0 ? v : null);
	}
	/**
	 * Method updateDiscount.
	 * �޸�������Ϣ
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscount(DiscountInfo info) throws Exception
	{
		long lID = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		int nIndex = 1;
		try
		{
			Log.print("==========updateDiscount start=============");
			conn = Database.getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append("UPDATE OB_Loan SET  ");
			if (info.getDocumentType() != null && info.getDocumentType().length() > 0)
			{
				//������������
				sbSQL.append("SDOCUMENTTYPE=?, ");
			}
			sbSQL.append("ninputuserid=ninputuserid ");
			sbSQL.append("where id=? AND  nCurrencyID = ? AND ninputuserid = ? ");
			Log.print("\n==========SQL==========");
			Log.print(sbSQL.toString());
			Log.print("==========SQL==========\n");
			ps = conn.prepareStatement(sbSQL.toString());
			if (info.getDocumentType() != null && info.getDocumentType().length() > 0)
			{
				ps.setString(nIndex++, info.getDocumentType());
			}
			ps.setLong(nIndex++, info.getID());
			ps.setLong(nIndex++, info.getCurrencyID());
			ps.setLong(nIndex++, info.getInputUserID());
			if (ps.executeUpdate() > 0)
			{
				Log.print("updateDiscount success��");
			}
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			lID = info.getID();
			Log.print("==========updateDiscount end=============");
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * Method saveDiscountBill.
	 * ��������Ʊ����Ϣ
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscountBill(DiscountBillInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		long lMaxID = -1;
		int nMaxSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��saveDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//�õ�����ID
			strSQL = " select nvl(max(ID)+1,1) from OB_DiscountBill ";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//��ȡ���к�
			strSQL = " select nvl(max(nSerialNo)+1,1) from OB_DiscountBill " + " where nLoanID=" + info.getDiscountApplyID() + " and nStatusID = " + Constant.RecordStatus.VALID;
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				nMaxSerialNo = rs.getInt(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//�洢��¼
			strSQL =
				" Insert into OB_DiscountBill(ID,nLoanID,nSerialNo,sUserName,sBank,nIsLocal,dtCreate,dtEnd,sCode,mAmount,nStatusID,nAddDays,nAcceptPOType,sFormerOwner)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lMaxID);
			ps.setLong(2, info.getDiscountApplyID());
			ps.setLong(3, nMaxSerialNo);
			ps.setString(4, info.getUser());
			ps.setString(5, info.getBank());
			ps.setLong(6, info.getIsLocal());
			ps.setTimestamp(7, info.getCreate());
			ps.setTimestamp(8, info.getEnd());
			ps.setString(9, info.getCode());
			ps.setDouble(10, info.getAmount());
			ps.setLong(11, Constant.RecordStatus.VALID);
			ps.setLong(12, info.getAddDay());
			ps.setLong(13, info.getAcceptPOTypeID());
			ps.setString(14, info.getFormerOwner());
			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
			Timestamp tsDiscountEndDate = null;
			strSQL = " select dtEndDate from OB_LOAN where ID=? ";
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, info.getDiscountApplyID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				tsDiscountEndDate = rs.getTimestamp("dtEndDate");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (tsDiscountEndDate == null && info.getEnd() != null)
			{
				//���¼�¼
				strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, info.getEnd());
				ps.setLong(2, info.getDiscountApplyID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
				if (tsDiscountEndDate != null && info.getEnd() != null && tsDiscountEndDate.before(info.getEnd()))
				{
					//���¼�¼
					strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
					//Log.print (strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, info.getEnd());
					ps.setLong(2, info.getDiscountApplyID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				}
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			//throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}
	/**
	 * Method findDiscountBillByID.
	 * ���ݱ�ʶ��ѯһ������Ʊ�ݵ���Ϣ
	 * @param lDiscountBillID
	 * @return DiscountBillInfo
	 * @throws Exception
	 */
	public DiscountBillInfo findDiscountBillByID(long lDiscountBillID) throws Exception
	{
		DiscountBillInfo dbi = new DiscountBillInfo();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��findDiscountBillByID~~~~~~~~~~");
			con = Database.getConnection();
			strSQL = " select * from Loan_DiscountContractBill where ID=? and nStatusID=?";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lDiscountBillID);
			ps.setLong(2, Constant.RecordStatus.VALID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				
				dbi.setDiscountBillID(lDiscountBillID); //Ʊ�ݱ�ʾ
				//dbi.setDiscountApplyID(rs.getLong("nLoanID")); //���ֱ�ʶ
				dbi.setSerialNo(rs.getLong("nSerialNo")); //���к�
				dbi.setUser(rs.getString("sUserName")); //ԭʼ��Ʊ��
				dbi.setBank(rs.getString("sBank")); //�ж�����
				dbi.setIsLocal(rs.getLong("nIsLocal")); //�ж��������ڵ�
				dbi.setCreate(rs.getTimestamp("dtCreate")); //��Ʊ��
				dbi.setEnd(rs.getTimestamp("dtEnd")); //������
				dbi.setCode(rs.getString("sCode")); //��Ʊ����
				dbi.setAmount(rs.getDouble("mAmount")); //��Ʊ���
				dbi.setAddDay(rs.getLong("nAddDays")); //�ڼ������Ӽ�Ϣ����
				dbi.setAcceptPOTypeID(rs.getLong("NACCEPTPOTYPEID")); //��Ʊ����
				dbi.setFormerOwner(rs.getString("sFormerOwner")); //���ֵ�λֱ��ǰ��
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return dbi;
	}
	/**
	 * Method updateDiscountBill.
	 * ��������Ʊ����ϸ��Ϣ
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscountBill(DiscountBillInfo info) throws Exception
	{
		long lID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��updateDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//���¼�¼
			strSQL = " update OB_DiscountBill set sUserName=?,sBank=?,nIsLocal=?,dtCreate=?,dtEnd=?,sCode=?,mAmount=?,nAddDays=?,nAcceptPOType=?,sFormerOwner=? where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			int nIndex = 1;
			ps.setString(nIndex++, info.getUser());
			ps.setString(nIndex++, info.getBank());
			ps.setLong(nIndex++, info.getIsLocal());
			ps.setTimestamp(nIndex++, info.getCreate());
			ps.setTimestamp(nIndex++, info.getEnd());
			ps.setString(nIndex++, info.getCode());
			ps.setDouble(nIndex++, info.getAmount());
			ps.setLong(nIndex++, info.getAddDay());
			ps.setLong(nIndex++, info.getAcceptPOTypeID());
			ps.setString(nIndex++, info.getFormerOwner());
			ps.setLong(nIndex++, info.getDiscountBillID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getDiscountBillID();
			conn.close();
			conn = null;
			//			+++++++++++++++++++++++++++++
			Timestamp tsDiscountEndDate = null;
			strSQL = " select dtEndDate from OB_LOAN where ID=? ";
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, info.getDiscountApplyID());
			rs = ps.executeQuery();
			if (rs.next())
			{
				tsDiscountEndDate = rs.getTimestamp("dtEndDate");
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			if (tsDiscountEndDate == null && info.getEnd() != null)
			{
				//���¼�¼
				strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setTimestamp(1, info.getEnd());
				ps.setLong(2, info.getDiscountApplyID());
				ps.executeUpdate();
				ps.close();
				ps = null;
			}
			else
				if (tsDiscountEndDate != null && info.getEnd() != null && tsDiscountEndDate.before(info.getEnd()))
				{
					//���¼�¼
					strSQL = " update OB_LOAN set dtEndDate=? where ID=? ";
					//Log.print (strSQL);
					ps = conn.prepareStatement(strSQL);
					ps.setTimestamp(1, info.getEnd());
					ps.setLong(2, info.getDiscountApplyID());
					ps.executeUpdate();
					ps.close();
					ps = null;
				}
			conn.close();
			conn = null;
			//+++++++++++++++++++++++++++++
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//�ڴ˴�������������Ӧ�ó���Ĵ��롣
		try
		{
			OBDiscountApplyDao dao = new OBDiscountApplyDao();
			/*DiscountMainInfo info = new DiscountMainInfo();
			info.setID(1);
			info.setCurrencyID(1);
			info.setOfficeID(1);
			dao.saveDiscount1(info);
			*/
			/*
				DiscountBillStatInfo info = new DiscountBillStatInfo();
				info.setAmount(100);
				info.setBankAcceptPO(2);
				info.setBizAcceptPO(4);
				info.setInputUserID(3);
				info.setDiscountStartDate(DataFormat.getDateTime("2005-01-06"));
				info.setReason("reson");
				info.setPurpose("purpose1");
				info.setDate(DataFormat.getDateTime("2003-01-05"));
				info.setID(1);
				dao.saveDiscount2(info);
			*/
			/*dao.updateStatus(1,3);
			 */
			/*DiscountBillInfo info = new DiscountBillInfo();
			info.setDiscountBillID(3);
			info.setDiscountApplyID(1);
			info.setDiscountCredenceID(2);
			info.setUser("user3");
			info.setBank("bank3");
			info.setIsLocal(1);
			info.setCreate(DataFormat.getDateTime("2006-4-1"));
			info.setEnd(DataFormat.getDateTime("2008-8-8"));
			info.setCode("code3");
			info.setAmount(200);
			info.setAddDay(3);
			info.setAcceptPOTypeID(2);
			info.setFormerOwner("FormerOwner3");
			// dao.saveDiscountBill(info);
			dao.updateDiscountBill(info);
			*/
			/*long[] lID = {0,1};
			dao.deleteDiscountBill(lID);
			*/
			DiscountInfo info = new DiscountInfo();
			info = dao.findDiscountByID(112);
			Log.print("bank:" + info.getApplyBank());
			Log.print("amount:" + info.getApplyDiscountAmount());
			//Log.print("documenttype:" + info.getDocumentType());
			/*DiscountBillInfo info = null;
			info = dao.findDiscountBillByID(2);
			Log.print("user��"+info.getUser());
			Log.print("lSerialNo��"+info.getSerialNo());
			Log.print("addday��"+info.getAddDay());
			Log.print("strBank��"+info.getBank());
			*/
			/*	Vector vctInfo = null;
				DiscountBillQueryInfo info = new DiscountBillQueryInfo();
				info.setDiscountID(1);
				info.setPageLineCount(3);
				info.setPageNo(2);
				info.setDate(DataFormat.getDateTime("2006-01-05"));
				vctInfo = dao.findDiscountBillByDiscountID(info);
				if (vctInfo != null)
				{
					Log.print("not null");
				}
				else
				{
					Log.print("null");
				}
				*/
			/*private long lDiscountID = -1;
			private long lContractID = -1;
			private long lPageLineCount= -1;
			private long lPageNo = -1;
			private long lOrderParam = -1;
			private long lDesc = -1;
			private double dRate = 0.0;
			private Timestamp tsDate = null;
			*/
			/*	DiscountInfo info = new DiscountInfo();
				info.setID(1);
				info.setInputUserID(1);
				info.setCurrencyID(1);
				info.setDocumentType("1111111111");
				dao.updateDiscount(info);
				*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public Collection findDiscountBillByContractID(DiscountBillQueryInfo info) throws Exception, IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dDiscountRate = 0; //����
		Timestamp tsDiscountDate = null; //��Ϣ��
		double dExamineAmount = 0; //��׼���
		double dDiscountAccrual = 0; //��Ϣ
		double dCheckAmount = 0; //ʵ�����
		Timestamp tsEnd = null; //��������
		String strEnd = ""; //��������
		int nDays = 0; //ʵ����������
		double dAmount = 0; //Ʊ�ݽ��
		double dAccrual = 0; //������Ϣ
		double dRealAmount = 0; //ʵ�����ֽ��
		double dTotalAmount = 0; //��Ʊ�ݽ��
		double dTotalAccrual = 0; //��Ʊ����Ϣ
		double dTotalRealAmount = 0; //��Ʊ��ʵ�����
		try
		{
			con = Database.getConnection();
			Log.print("======�������ּ�Ϣ(findBillInterestByID)����======");
			Log.print("��ͬ��ʾ��" + info.getContractID());
			Log.print("ƾ֤��ʾ��" + info.getDiscountCredenceID());
			if (info.getContractID() > 0)
			{
				strSQL = " select a.* from Loan_ContractForm a where a.ID=? ";
				ps = con.prepareStatement(strSQL);
				ps.setLong(1, info.getContractID());
				rs = ps.executeQuery();
				if (rs.next())
				{
					dExamineAmount = rs.getDouble("mExamineAmount"); //��׼���
					dRealAmount = rs.getDouble("mCheckAmount"); //�˶����
					dAccrual = dExamineAmount - dRealAmount; //������Ϣ
					dDiscountRate = rs.getDouble("mDiscountRate"); //��������
					tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //���ּ�Ϣ��
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nContractID=" + info.getContractID();
			}
			else
				if (info.getDiscountCredenceID() > 0)
				{
					//strSQL = " select a.* from Loan_ContractForm a, Loan_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";
					strSQL = " select a.* from Loan_ContractForm a, OB_DiscountCredence b where a.ID=b.nContractID and b.ID=? ";
					ps = con.prepareStatement(strSQL);
					ps.setLong(1, info.getDiscountCredenceID());
					rs = ps.executeQuery();
					if (rs.next())
					{
						dExamineAmount = rs.getDouble("mExamineAmount"); //��׼���
						dRealAmount = rs.getDouble("mCheckAmount"); //�˶����
						dAccrual = dExamineAmount - dRealAmount; //������Ϣ
						dDiscountRate = rs.getDouble("mDiscountRate"); //��������
						tsDiscountDate = rs.getTimestamp("dtDiscountDate"); //���ּ�Ϣ��
					}
					rs.close();
					rs = null;
					ps.close();
					ps = null;
					strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountCredenceID=" + info.getDiscountCredenceID();
				}
			Log.print("======��ʼ��ѯƱ���������ܽ��======");
			//�����¼����
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			//strSQL = " from DiscountBill where nStatusID=" + Constant.RecordStatus.VALID + " and nDiscountApplyID=" + lDiscountApplyID;
			Log.print(strSelect + strSQL);
			ps = con.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
				dTotalAccrual = dTotalAmount - dTotalRealAmount;
			}
			Log.print("==============");
			Log.print("Ʊ��������=" + lRecordCount);
			Log.print("Ʊ���ܽ��=" + dTotalAmount);
			Log.print("Ʊ������Ϣ=" + dTotalAccrual);
			Log.print("��ʵ�����=" + dTotalRealAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("======������ѯƱ���������ܽ��======");
			lPageCount = lRecordCount / info.getPageLineCount();
			if ((lRecordCount % info.getPageLineCount()) != 0)
			{
				lPageCount++;
			}
			////////////////////////////������//////////////////////////////////////////////////////////////////////
			switch ((int) info.getOrderParam())
			{
				case 1 :
					strSQL += " order by nSerialNo ";
					break;
				case 2 :
					strSQL += " order by sUserName ";
					break;
				case 3 :
					strSQL += " order by sBank ";
					break;
				case 4 :
					strSQL += " order by nIsLocal ";
					break;
				case 5 :
					strSQL += " order by dtCreate ";
					break;
				case 6 :
					strSQL += " order by dtEnd ";
					break;
				case 7 :
					strSQL += " order by nAddDays ";
					break;
				case 8 :
					strSQL += " order by sCode ";
					break;
				case 9 :
					strSQL += " order by mAmount ";
					break;
				case 10 :
					strSQL += " order by nAcceptPOTypeID";
					break;
				case 11 :
					strSQL += " order by sFormerOwner";
					break;
				default :
					strSQL += " order by nSerialNo ";
			}
			if (info.getDesc() == Constant.PageControl.CODE_ASCORDESC_DESC)
			{
				strSQL += " desc";
			}
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//��������Ľ����
			lRowNumStart = (info.getPageNo() - 1) * info.getPageLineCount() + 1;
			lRowNumEnd = lRowNumStart + info.getPageLineCount() - 1;
			strSQL = "select * " + strSQL;
			strSQL = " select a.*, rownum r from " + " ( " + strSQL + " ) a ";
			strSQL = " select * from ( " + strSQL + " ) b  where b.r between " + lRowNumStart + " and " + lRowNumEnd;
			Log.print("��ҳ��ѯ��ʼ");
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				DiscountBillInfo dbill = new DiscountBillInfo();
				dbill.setDiscountContractID(info.getContractID()); //���ֺ�ͬ��ʾ
				dbill.setDiscountDate(tsDiscountDate); //��Ϣ��
				dbill.setDiscountRate(dDiscountRate); //��Ϣ����
				dbill.setDiscountCredenceID(rs.getLong("nDiscountCredenceID")); //ƾ֤��ʾ
				//obʹ�ñ�ʶ
				dbill.setOBDiscountCredenceID(rs.getLong("ob_nDiscountCredenceID")); //
				dbill.setID(rs.getLong("ID")); //Ʊ�ݱ�ʾ
				dbill.setSerialNo(rs.getLong("nSerialNo")); //���к�
				dbill.setUser(rs.getString("sUserName")); //ԭʼ��Ʊ��
				dbill.setBank(rs.getString("sBank")); //�ж�����
				dbill.setIsLocal(rs.getLong("nIsLocal")); //�ж��������ڵأ��Ƿ��ڱ��أ�
				dbill.setCreate(rs.getTimestamp("dtCreate")); //��Ʊ��
				dbill.setEnd(rs.getTimestamp("dtEnd")); //������
				dbill.setCode(rs.getString("sCode")); //��Ʊ����
				dbill.setAmount(rs.getDouble("mAmount")); //��Ʊ���
				dbill.setAddDay(rs.getLong("nAddDays")); //�ڼ������Ӽ�Ϣ����
				dbill.setAcceptPOTypeID(rs.getLong("nAcceptPOTypeID")); //��Ʊ����
				dbill.setFormerOwner(rs.getString("sFormerOwner")); //���ֵ�λֱ��ǰ��
				//////////////////////////////////////////////////////////
				//dAmount = rs.getDouble("mAmount"); //��Ʊ���
				tsEnd = rs.getTimestamp("dtEnd");
				nDays = 0;
				if (tsEnd != null && tsDiscountDate != null)
				{
					strEnd = tsEnd.toString();
					tsEnd =
						new java.sql.Timestamp(
							new Integer(strEnd.substring(0, 4)).intValue() - 1900,
							new Integer(strEnd.substring(5, 7)).intValue() - 1,
							new Integer(strEnd.substring(8, 10)).intValue(),
							0,
							0,
							0,
							0);
					nDays = (int) java.lang.Math.ceil((tsEnd.getTime() - tsDiscountDate.getTime()) / 86400000) + rs.getInt("nAddDays"); //���Ͻڼ������Ӽ�Ϣ����
				}
				if (nDays >= 0)
				{
					if (rs.getLong("nIsLocal") == LOANConstant.YesOrNo.NO)
					{
						nDays = nDays + 3;
					}
					//dAccrual = dAmount * (dDiscountRate / 360) * 0.01 * nDays;
					//dAccrual = DataFormat.formatDouble(dAccrual, 2);
					//dRealAmount = dAmount - dAccrual;
				}
				/*
				Log.print("========================");
				Log.print("��������=" + nDays);
				Log.print("��Ʊ���=" + dAmount);
				Log.print("��Ʊ��Ϣ=" + dAccrual);
				Log.print("ʵ�����=" + dRealAmount);
				Log.print("========================");
				dTotalAccrual = DataFormat.formatDouble(dTotalAccrual, 2) + DataFormat.formatDouble(dAccrual, 2);
				dTotalRealAmount = DataFormat.formatDouble(dTotalRealAmount, 2) + DataFormat.formatDouble(dRealAmount, 2);
				*/
				dbill.setDays(nDays); //ʵ����������
				dbill.setCheckAmount(rs.getDouble("mCheckAmount")); //ʵ�����ֽ��
				dbill.setInterest(rs.getDouble("mAmount") - rs.getDouble("mCheckAmount")); //������Ϣ
				//////////////////////////////////////////////////////////
				dbill.setCount(lRecordCount);
				//dbill.setTotalAmount(DataFormat.formatDouble(dTotalAmount, 2));
				//dbill.setTotalAccrual(DataFormat.formatDouble(dTotalAccrual, 2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalRealAmount,2));
				//dbill.setTotalRealAmount(DataFormat.formatDouble(dTotalAmount, 2) - DataFormat.formatDouble(dTotalAccrual, 2));
				dbill.setTotalAmount(dTotalAmount);
				dbill.setTotalInterest(dTotalAccrual);
				dbill.setTotalAmount(dTotalRealAmount);
				v.add(dbill);
			}
			Log.print("��ҳ��ѯ����");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		Log.print("======�˳����ּ�Ϣ(findBillInterestByID)����======");
		return (v.size() > 0 ? v : null);
	}
	/**
	* ����һ��������Ϣ���������ݿ��DiscountApply��
	* @param lDiscountID ���ֱ�ʶ
	*/
	public DiscountCredenceInfo findDiscountCredenceByID(long lDiscountCredenceID) throws Exception, IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		int nBillCount = 0;
		DiscountCredenceInfo lai = new DiscountCredenceInfo();
		try
		{
			con = Database.getConnection();
			strSQL = " select a.*, ";
			strSQL += " b.ID nContractID,b.sContractCode,b.nBorrowClientID,b.mExamineAmount,b.mCheckAmount,b.mDiscountRate,c.sName sClientName, ";
			strSQL += " d.sName sInputUserName,e.sAccountno sGrantCurrentAccount,e.sName sGrantName,f.sName AccName,f.sCode AccCode";
			strSQL += " from OB_DiscountCredence a, Loan_ContractForm b, Client c, ob_User d, Sett_Account e, Sett_Branch f ";
			strSQL += " where a.nContractID=b.ID and b.nBorrowClientID=c.ID(+) and a.nInputUserID=d.ID and b.nTypeID=? and a.ID=? ";
			strSQL += " and a.nGrantCurrentAccountID = e.ID(+) and a.nAccountBankID = f.ID(+)";
			Log.print(strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, LOANConstant.LoanType.TX);
			ps.setLong(2, lDiscountCredenceID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				//��������
				lai.setDiscountContractID(rs.getLong("nContractID")); //����ID��ʶ
				lai.setDiscountContractCode(rs.getString("sContractCode")); //���ֱ��
				//���뵥λ
				lai.setApplyClientID(rs.getLong("nBorrowClientID")); //���뵥λ���
				lai.setApplyClientName(rs.getString("sClientName")); //���뵥λ����
				lai.setApplyAccount(rs.getString("sApplyAccount")); //���뵥λ�˺�
				lai.setApplyBank(rs.getString("sApplyBank")); //���뵥λ��������
				lai.setExamineAmount(rs.getDouble("mExamineAmount")); //��׼���
				lai.setCheckAmount(rs.getDouble("mCheckAmount")); //�˶����
				//lai.setInterest(rs.getDouble("mExamineAmount") - rs.getDouble("mCheckAmount")); //������Ϣ
				lai.setDiscountRate(rs.getDouble("mDiscountRate")); //��������
				//����ƾ֤
				lai.setID(lDiscountCredenceID); //����ƾ֤��ʶ
				//lai.setCode(rs.getString("sCode")); //����ƾ֤���
				lai.setCode(rs.getString("sInstructionNo")); //����ƾ֤���
				lai.setFillDate(rs.getTimestamp("dtFillDate"));
				lai.setDraftTypeID(rs.getLong("nDraftTypeID")); //���ֻ�Ʊ�����ʾ
				lai.setDraftCode(rs.getString("sDraftCode")); //���ֻ�Ʊ����
				lai.setPublicDate(rs.getTimestamp("dtPublicDate")); //��Ʊ��
				lai.setAtTerm(rs.getTimestamp("dtAtTerm")); //������
				lai.setAcceptClientName(rs.getString("sAcceptClient")); //�жҵ�λ����
				lai.setAcceptAccount(rs.getString("sAcceptAccount")); //�жҵ�λ�˺�
				lai.setAcceptBank(rs.getString("sAcceptBank")); //�жҵ�λ����
				lai.setStatusID(rs.getLong("nStatusID")); //����ƾ֤�Ƿ�ɾ��
				lai.setBillAmount(rs.getDouble("mAmount")); //����ƾ֤���
				lai.setBillInterest(rs.getDouble("mInterest")); //����ƾ֤��Ϣ
				lai.setBillCheckAmount(rs.getDouble("mAmount") - rs.getDouble("mInterest")); //����ƾ֤�˶����
				lai.setInputUserID(rs.getLong("nInputUserID"));
				lai.setInputUserName(rs.getString("sInputUserName"));
				lai.setInputDate(rs.getTimestamp("dtInputDate"));
				//lai.setNextCheckUserID(rs.getLong("nNextCheckUserID")); //��һ������˱�ʾ
				lai.setGrantTypeID(rs.getLong("nGrantTypeID"));
				lai.setAccountBankID(rs.getLong("nAccountBankID"));
				lai.setAccountBankCode(rs.getString("AccCode"));
				lai.setAccountBankName(rs.getString("AccName"));
				lai.setReceiveClientCode(rs.getString("sReceiveAccount"));
				lai.setReceiveClientName(rs.getString("sReceiveClientName"));
				lai.setRemitBank(rs.getString("sRemitBank"));
				lai.setRemitInProvince(rs.getString("sRemitInProvince"));
				lai.setRemitInCity(rs.getString("sRemitInCity"));
				lai.setGrantCurrentAccountID(rs.getLong("nGrantCurrentAccountID"));
				lai.setGrantCurrentAccountCode(rs.getString("sGrantCurrentAccount"));
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
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
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lai;
	}
	/**
	 * Method saveDiscountCredence.
	 * ��������Ʊ����Ϣ
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long saveDiscountCredence(DiscountCredenceInfo info) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		String strCredenceCode = null;
		long lMaxID = -1;
		int nMaxSerialNo = -1;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��saveDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//��һ���ݴ�
			//�õ�����ID
			strSQL = "select nvl(max(ID)+1,1) from OB_discountcredence";
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxID = rs.getLong(1);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			//strCredenceCode = createCredenceCode(info.getDiscountContractID());
			strCredenceCode = OBOperation.createInstrCode(OBConstant.SubModuleType.LOAN);
			//�洢��¼
			strSQL =
				" Insert into Ob_DiscountCredence(ID,nContractID,dtFillDate,nDraftTypeID,sDraftCode, "
					+ " dtPublicDate,dtAtTerm,sApplyClient,sApplyAccount,sApplyBank,sAcceptClient,sAcceptAccount,sAcceptBank, "
					+ " mAmount,mRate,mInterest,nStatusID,nInputUserID,dtInputDate,sCode, "
					+ " nGrantTypeID,nAccountBankID,sReceiveAccount,sReceiveClientName,sRemitBank,sRemitInProvince,sRemitInCity,nGrantCurrentAccountID,SINSTRUCTIONNO) "
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?) ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lMaxID);
			ps.setLong(2, info.getDiscountContractID());
			ps.setTimestamp(3, info.getFillDate());
			ps.setLong(4, info.getDraftTypeID());
			ps.setString(5, info.getDraftCode());
			ps.setTimestamp(6, info.getPublicDate());
			ps.setTimestamp(7, info.getAtTerm());
			ps.setString(8, info.getApplyClientName());
			ps.setString(9, info.getApplyAccount());
			ps.setString(10, info.getApplyBank());
			ps.setString(11, info.getAcceptClientName());
			ps.setString(12, info.getAcceptAccount());
			ps.setString(13, info.getAcceptBank());
			ps.setDouble(14, info.getBillAmount());
			ps.setDouble(15, info.getDiscountRate());
			ps.setDouble(16, info.getBillInterest());
			//ps.setLong(17, LOANConstant.DiscountCredenceStatus.SUBMIT);
			//��ʼΪ׫д״̬
			ps.setLong(17, OBConstant.LoanInstrStatus.SAVE);
			ps.setLong(18, info.getInputUserID());
			ps.setString(19, strCredenceCode);
			ps.setLong(20, info.getGrantTypeID());
			ps.setLong(21, info.getAccountBankID());
			ps.setString(22, info.getReceiveClientCode());
			ps.setString(23, info.getReceiveClientName());
			ps.setString(24, info.getRemitBank());
			ps.setString(25, info.getRemitInProvince());
			ps.setString(26, info.getRemitInCity());
			ps.setLong(27, info.getGrantCurrentAccountID());
			ps.setString(28, strCredenceCode);
			ps.executeUpdate();
			ps.close();
			ps = null;
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return (lMaxID);
	}

    /**
     * ninh 2004-3-10
     * Method cancelDiscountCredenceByID.
     * ȡ������Ʊ����ϸ��Ϣ
     * @param lDiscountCredenceID
     * @return long
     * @throws Exception
     */
    public long cancelDiscountCredenceByID(long lDiscountCredenceID) throws Exception
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        String strSQL = null;
        long lResult = 0;

        try
        {
            con = Database.getConnection();

            strSQL = " update ob_DiscountCredence set nStatusID = ? where ID = ? ";
            ps = con.prepareStatement(strSQL);

            ps.setLong(1, Constant.RecordStatus.INVALID);
            ps.setLong(2, lDiscountCredenceID);

            lResult = ps.executeUpdate();
            

            ps.close();
            ps = null;
            
            strSQL = " update Loan_DiscountContractBill set ob_nDiscountCredenceID = null where ob_nDiscountCredenceID = ? ";
            ps = con.prepareStatement(strSQL);

            ps.setLong(1, lDiscountCredenceID);

            lResult = ps.executeUpdate();

            ps.close();
            ps = null;
            con.close();
            con = null;
        }
        catch (Exception ex)
        {
            log4j.error(ex.toString());
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
            catch (Exception ex)
            {
                log4j.error(ex.toString());
                throw new IException("Gen_E001");
            }
        }
        return lResult;
    }



	/**
	 * Method updateDiscountCredence.
	 * ��������Ʊ����ϸ��Ϣ
	 * @param info
	 * @return long
	 * @throws Exception
	 */
	public long updateDiscountCredence(DiscountCredenceInfo info) throws Exception
	{
		long lID = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��updateDiscountBill~~~~~~~~~~");
			conn = Database.getConnection();
			//���¼�¼
			//���¼�¼
			strSQL =
				" update OB_DiscountCredence set sApplyAccount=?,sApplyBank=?,sAcceptClient=?,sAcceptAccount=?,sAcceptBank=?, "
					+ " nGrantTypeID=?,nAccountBankID=?,sReceiveAccount=?,sReceiveClientName=?,sRemitBank=?,sRemitInProvince=?,sRemitInCity=?,nGrantCurrentAccountID=? "
					+ " where ID=? ";
			Log.print(strSQL);
			ps = conn.prepareStatement(strSQL);
			//ps.setLong(1, lDraftTypeID);
			ps.setString(1, info.getApplyAccount());
			ps.setString(2, info.getApplyBank());
			ps.setString(3, info.getAcceptClientName());
			ps.setString(4, info.getAcceptAccount());
			ps.setString(5, info.getAcceptBank());
			ps.setLong(6, info.getGrantTypeID());
			ps.setLong(7, info.getAccountBankID());
			ps.setString(8, info.getReceiveClientCode());
			ps.setString(9, info.getReceiveClientName());
			ps.setString(10, info.getRemitBank());
			ps.setString(11, info.getRemitInProvince());
			ps.setString(12, info.getRemitInCity());
			ps.setLong(13, info.getGrantCurrentAccountID());
			ps.setLong(14, info.getID());
			Log.print("lGrantTypeID=" + info.getGrantTypeID());
			Log.print("lAccountBankID=" + info.getAccountBankID());
			Log.print("strReceiveClientCode=" + info.getReceiveClientCode());
			Log.print("strReceiveClientName=" + info.getReceiveClientName());
			Log.print("strRemitBank=" + info.getRemitBank());
			Log.print("strRemitInProvince=" + info.getRemitInProvince());
			Log.print("strRemitInCity=" + info.getRemitInCity());
			Log.print("lGrantCurrentAccountID=" + info.getGrantCurrentAccountID());
			ps.executeUpdate();
			ps.close();
			ps = null;
			lID = info.getID();
			//lID = info.getDiscountBillID();
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(conn);
		}
		return lID;
	}
	/**
	 * ��������ƾ֤��¼״̬
	 * @
	 */
	public long updateCredenceStatus(long lCredenceID, long lStatusID) throws Exception
	{
		long lResult = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			Log.print("~~~~~~~~~~~���뷽��updateCredenceStatus~~~~~~~~~~");
			conn = getConnection();
			sbSQL = new StringBuffer();
			sbSQL.append(" update ob_discountcredence set nStatusID=? where ID=? ");
			Log.print("~~~~~~~~~~~strSQL Start~~~~~~~~~~~~~~");
			Log.print(sbSQL.toString());
			Log.print("~~~~~~~~~~~strSQL End~~~~~~~~~~~~~~");
			ps = conn.prepareStatement(sbSQL.toString());
			ps.setLong(1, lStatusID);
			ps.setLong(2, lCredenceID);
			if (ps.executeUpdate() > 0)
			{
				lResult = 1;
			}
			else
			{
				lResult = 0;
			}
			ps.close();
			ps = null;
			String strSQL = "";
			System.out.println("lID!!!!!!!!!!!!!===(*******)" + lResult + "pppppp" + lCredenceID);
			//�����ȡ������Ҫ����Ӧ��Ʊ���Ϳ�
			System.out.println("lStattuuuuuuuuuuuuuuuuuuuuuuuuusID====" + lStatusID);
			if (lStatusID == OBConstant.LoanInstrStatus.CANCEL)
			{
				System.out.println("inininininininininininininininini!@");
				strSQL = " update Loan_DiscountContractBill set nDiscountCredenceID = null,ob_nDiscountCredenceID = null where ob_nDiscountCredenceID = ? ";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lCredenceID);
				lResult = ps.executeUpdate();
				System.out.println("lID!!!!!!!!!!!!!===(*******)" + lResult);
				ps.close();
				ps = null;
			}
			conn.close();
			conn = null;
		}
		catch (Exception ex)
		{
			log4j.error(ex.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		return lResult;
	}
	/**
	 * �������������Ʊ����Ϣ������DiscountBill��
	 * @param lContractID ���ֺ�ͬ��ʶ
	 * @param strBillID ����Ʊ�ݱ�ʶ�ַ��������ŷָ�
	 * @return ��������Ʊ�ݵ��б�
	 */
	public DiscountBillInfo findDiscountBillByBillsID(long lContractID, String strBillID) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSelect = null;
		String strSQL = null;
		String strOrder = null;
		Vector v = new Vector();
		long lRecordCount = -1;
		long lPageCount = -1;
		long lRowNumStart = -1;
		long lRowNumEnd = -1;
		double dDiscountRate = 0; //����
		Timestamp tsDiscountDate = null; //��Ϣ��
		double dExamineAmount = 0; //��׼���
		double dDiscountAccrual = 0; //��Ϣ
		double dCheckAmount = 0; //ʵ�����
		long lDiscountBillID = -1;
		long lBankCount = 0;
		Timestamp tsEnd = null; //��������
		String strEnd = ""; //��������
		int nDays = 0; //ʵ����������
		double dAmount = 0; //Ʊ�ݽ��
		double dAccrual = 0; //������Ϣ
		double dRealAmount = 0; //ʵ�����ֽ��
		double dTotalAmount = 0; //��Ʊ�ݽ��
		double dTotalAccrual = 0; //��Ʊ����Ϣ
		double dTotalRealAmount = 0; //��Ʊ��ʵ�����
		DiscountBillInfo dbi = new DiscountBillInfo();
		try
		{
			conn = Database.getConnection();
			Log.print("======��ʼ��ѯƱ���������ܽ��======");
			//�����¼����
			strSelect = " select count(*),sum(nvl(mAmount,0)),sum(nvl(mCheckAmount,0)) ";
			strSQL = " from Loan_DiscountContractBill where nStatusID=" + Constant.RecordStatus.VALID + " and ID in ( " + strBillID + " ) ";
			Log.print(strSelect + strSQL);
			ps = conn.prepareStatement(strSelect + strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				lRecordCount = rs.getLong(1);
				dTotalAmount = rs.getDouble(2);
				dTotalRealAmount = rs.getDouble(3);
			}
			Log.print("==============");
			Log.print("Ʊ��������=" + lRecordCount);
			Log.print("Ʊ���ܽ��=" + dTotalAmount);
			Log.print("==============");
			rs.close();
			rs = null;
			ps.close();
			ps = null;
			Log.print("======������ѯƱ���������ܽ��======");
			if (strBillID.indexOf(",") == -1)
			{
				if (Long.parseLong(strBillID.trim()) > 0)
				{
					dbi = findDiscountBillByID(Long.parseLong(strBillID.trim()));
				}
			}
			else
			{
				strSelect = " select count(*) ";
				strSQL =
					" from Loan_DiscountContractBill where nStatusID="
						+ Constant.RecordStatus.VALID
						+ " and ID in ( "
						+ strBillID
						+ " ) and nAcceptPOTypeID = "
						+ LOANConstant.DraftType.BANK;
				Log.print(strSelect + strSQL);
				ps = conn.prepareStatement(strSelect + strSQL);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					lBankCount = rs.getLong(1);
				}
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				if (lBankCount == lRecordCount)
				{
					dbi.setAcceptPOTypeID(LOANConstant.DraftType.BANK);
				}
				else
					if (lBankCount == 0)
					{
						dbi.setAcceptPOTypeID(LOANConstant.DraftType.BIZ);
					}
					else
					{
						dbi.setAcceptPOTypeID(-1);
					}
				dbi.setCode("�����ϸ��");
				dbi.setCreate(null);
				dbi.setEnd(null);
				dbi.setUser("�����ϸ��");
				dbi.setBank("�����ϸ��");
			}
			dbi.setTotalAmount(dTotalAmount);
			dbi.setTotalInterest(dTotalAmount - dTotalRealAmount);
			dbi.setTotalAmount(dTotalRealAmount);
			conn.close();
			conn = null;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return (dbi);
	}
	/**
	 *
	 * ��������ƾ֤���
	 *
	 */
	private String createCredenceCode(long lContractID) throws IException
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = "";
		String strCode = "000";
		long lCode = 0;
		try
		{
			con = Database.getConnection();
			strSQL = " select nvl(max(sCode),0) sCode from Ob_DiscountCredence where nContractID = ? ";
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, lContractID);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				strCode = rs.getString(1);
				lCode = Long.parseLong(strCode) + 1;
				strCode = DataFormat.formatInt(lCode, 3, true);
			}
			rs.close();
			rs = null;
			ps.close();
			ps = null;
		}
		catch (Exception e)
		{
			Log.print(e.toString());
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
		Log.print(":::::::::: ::::strcode::::::" + strCode);
		return strCode;
	}
	/**
	 * ������ƾ֤��Ӧ������Ʊ�ݴ��̣�����DiscountCredence��
	 * @param lDiscountCredenceID ����ƾ֤��ʶ�����<=0���㣬������㲢�޸�
	 * @param lDiscountContractID ���ֺ�ͬ��ʶ
	 * @return ��������ƾ֤��ʶ
	 */
	public long saveDiscountCredenceBill(long lDiscountCredenceID, long lDiscountContractID, long lDiscountBillID[]) throws IException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String strSelect = null;
		String strSQL = null;
		long nStatusID = -1;
		int i = 0;
		long lRecordCount = -1;
		double dDiscountRate = 0; //����
		Timestamp tsDiscountDate = null; //��Ϣ��
		double dExamineAmount = 0; //��׼���
		double dDiscountAccrual = 0; //��Ϣ
		double dCheckAmount = 0; //ʵ�����
		Timestamp tsEnd = null; //��������
		String strEnd = ""; //��������
		long nDays = 0; //ʵ����������
		double dAmount = 0; //Ʊ�ݽ��
		double dAccrual = 0; //������Ϣ
		double dRealAmount = 0; //ʵ�����ֽ��
		double dTotalAmount = 0; //��Ʊ�ݽ��
		double dTotalAccrual = 0; //��Ʊ����Ϣ
		double dTotalRealAmount = 0; //��Ʊ��ʵ�����
		long lCredenceStatusID = -1; //����ƾ֤״̬
		try
		{
			conn = Database.getConnection();
			if (lDiscountCredenceID > 0)
			{
				//���¼�¼
				strSQL = " update Loan_DiscountContractBill set OB_nDiscountCredenceID=? where nDiscountCredenceID=? ";
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, -1);
				ps.setLong(2, lDiscountCredenceID);
				ps.executeUpdate();
				ps.close();
				ps = null;
				//���¼�¼
				for (i = 0; i < lDiscountBillID.length; i++)
				{
					if (lDiscountBillID[i] > 0)
					{
						//���¼�¼
						strSQL = " update Loan_DiscountContractBill set OB_nDiscountCredenceID=? where ID=? ";
						Log.print(strSQL);
						ps = conn.prepareStatement(strSQL);
						ps.setLong(1, lDiscountCredenceID);
						ps.setLong(2, lDiscountBillID[i]);
						ps.executeUpdate();
						ps.close();
						ps = null;
					}
				}
				//strSQL = " select sum(nvl(mAmount,0)) mTotalAmount,sum(nvl(mCheckAmount,0)) mTotalCheckAmount from Loan_DiscountContractBill a where a.nDiscountCredenceID=? ";
				strSQL = " select sum(nvl(mAmount,0)) mTotalAmount,sum(nvl(mCheckAmount,0)) mTotalCheckAmount from Loan_DiscountContractBill a where a.ob_nDiscountCredenceID=? ";
				ps = conn.prepareStatement(strSQL);
				ps.setLong(1, lDiscountCredenceID);
				rs = ps.executeQuery();
				if (rs.next())
				{
					//dTotalAmount = Data.formatDouble(rs.getDouble(1),2);
					//dTotalRealAmount = DataFormat.formatDouble(rs.getDouble(2),2);
					//dTotalAccrual = DataFormat.formatDouble(dTotalAmount,2) - DataFormat.formatDouble(dTotalRealAmount,2);
					dTotalAmount = rs.getDouble(1);
					dTotalRealAmount = rs.getDouble(2);
					dTotalAccrual = dTotalAmount - dTotalRealAmount;
				}
				Log.print("==============");
				Log.print("��Ʊ���=" + dTotalAmount);
				Log.print("��Ʊ��Ϣ=" + dTotalAccrual);
				Log.print("ʵ�����=" + dTotalRealAmount);
				Log.print("==============");
				rs.close();
				rs = null;
				ps.close();
				ps = null;
				strSQL = " update ob_DiscountCredence set mAmount=?, mInterest=? where ID=? ";
				Log.print(strSQL);
				ps = conn.prepareStatement(strSQL);
				ps.setDouble(1, dTotalAmount);
				ps.setDouble(2, dTotalAccrual);
				ps.setLong(3, lDiscountCredenceID);
				ps.executeUpdate();
				ps.close();
				ps = null;
				conn.close();
				conn = null;
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
				if (conn != null)
				{
					conn.close();
					conn = null;
				}
			}
			catch (Exception ex)
			{
				log4j.error(ex.toString());
				throw new IException("Gen_E001");
			}
		}
		return lDiscountCredenceID;
	}
}