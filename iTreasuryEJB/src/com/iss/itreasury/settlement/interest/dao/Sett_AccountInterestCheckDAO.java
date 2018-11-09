package com.iss.itreasury.settlement.interest.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.dataentity.UpLoanReturnInfo;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.settlement.interest.bizlogic.InterestBatch;
import com.iss.itreasury.settlement.interest.bizlogic.InterestOperation;
import com.iss.itreasury.settlement.interest.bizlogic.InterestSettlement;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.UpLoadInterestCheckInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

public class Sett_AccountInterestCheckDAO extends SettlementDAO 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * constructor
	 * @param conn
	 */
	public Sett_AccountInterestCheckDAO()
	{
		super();
	}
	
	/**
	 * constructor
	 * @param conn
	 */
	public Sett_AccountInterestCheckDAO(Connection conn)
	{
		super(conn);
	}

	
	/**
	*saveUploadFileToDateBase  �����ϴ��ĵ�����Ϣ�����ݿ�  ��
	*��������ϴ��ĵ�����Ϣ�����ݿ�
	*@mySmartUpload  
	*@return long 
	*ADD  BY  yanliu��
	*/
	public Vector saveUploadFileToDateBase (
							com.jspsmart.upload.SmartUpload mySmartUpload,
							long lCurrencyID,
							long lOfficeID) throws Exception
	{
		long lResult = 0;
		String strResult = "";

		String strFileContent="";
		String sbExecuteSQL= "";
		String strSQLGetOtherInfo = "";
		Vector returnVector = new Vector ();
		Vector conditionVector = new Vector ();
		String strAdd ="";//ÿ�δ��ϴ��ļ��ж�����һ����Ԫ��
		int index=0;
		int toIndex= 0;
		long lNumberRow = 0;
		int fromIndex = 0;

		boolean  bIsValid = true; //��ǰ���Ƿ���Ч

		String strAccuntID ="";
		String strClientID = "";
		String strGetUpClientID = "";
		String strClientCode="";
		String strTempClientCode ="";
		long  lUpClientID=0;

		long  lIsertRowSum = 0;

		long lMaxID = 0;
		long lMaxNO =0 ;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int iTableKeyPositionFive=0;
		boolean bIsEnd =true;
		// Retreive the current file
		com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);
		myFile.saveAs(Env.UPLOAD_PATH+myFile.getFileName());

		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Env.UPLOAD_PATH+ myFile.getFileName()));
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			HSSFRow row =  null;
			HSSFCell cell = null;
			if (fs!=null)
			{
				wb = new HSSFWorkbook(fs);
			}
			if (wb!=null)
			{
				sheet = wb.getSheetAt(0);
			}
			if (sheet==null)
			{
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
				returnVector.addElement (upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(true);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("���ܵ���յ�Excel�ļ���");
				Log.print("����3");
			}

			row = sheet.getRow(0); 
			for (int i=0;row!=null;i++,row =sheet.getRow(i))
			{
				Log.print("���ѭ��****"+i);
				//i=0��ʱ������Ǳ����У�Ӧ������
				if(i==0){
				    continue;
				}
				bIsValid=true;
				UpLoadInterestCheckInfo upLoadInterestCheckInfo= new UpLoadInterestCheckInfo();				

				for  (index = 1; index <= 11; index++)
				{
					Log.print("�ڲ�ѭ��****"+index);
					strAdd="";
					Log.print ("for  ѭ����ʼ"+index);
					cell = row.getCell((short) (index-1));
					if (cell!=null)
					{
						//��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							strAdd=cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							strAdd=String.valueOf(cell.getNumericCellValue());
						}
					}
					if (strAdd==null)
					{
						strAdd="";
					}
									
					Log.print ("��ȡ����strAdd=="+strAdd+"==");	
					
					//1-�˻���
					if (index==1)
					{
						strAdd = strAdd.trim ();
						if (strAdd.indexOf(".")>0)
							strAdd = strAdd.substring(0,strAdd.indexOf("."));
						Log.print ("ȡ��1������ = "+strAdd);
						if (strAdd.equals(""))
						{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							returnVector.addElement (upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
						}else
						{
							upLoadInterestCheckInfo.setAccountNo(strAdd);
							conditionVector.addElement(upLoadInterestCheckInfo);
						}
					}
									
					//2-��Ϣ��
					if (index==2)
					{
						strAdd = strAdd.trim ();
						if (strAdd.indexOf(".")>0)
							strAdd = strAdd.substring(0,strAdd.indexOf("."));
						Log.print ("ȡ��2������ = "+strAdd);
						try
						{
							upLoadInterestCheckInfo.setInterestStartDate(DataFormat.getDateTime(strAdd));
						}
						catch(Exception e)
						{
							Log.print ("ȡ��2������!��ʽ������!");
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							returnVector.addElement (upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���ڣ�::"+strAdd);
						}
					}
									
					//3-��Ϣ��
					if (index==3)
					{
						strAdd = strAdd.trim ();
						Log.print ("ȡ��3������ = "+strAdd);
						try
						{
							upLoadInterestCheckInfo.setInterestClearDate(DataFormat.getDateTime(strAdd));
						}
						catch(Exception e)
						{
							Log.print ("ȡ��3������!��ʽ������!");
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							returnVector.addElement (upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���ڣ�::"+strAdd);
						}
					}
								
					//4-ժҪ
					if (index==4)
					{
						strAdd = strAdd.trim ();
						Log.print ("ȡ��4������ = "+strAdd);
						if (strAdd.equals(""))
						{
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							returnVector.addElement (upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
						}
						else
						{
							upLoadInterestCheckInfo.setAbstract(strAdd);
						}
					}					
									
					//5-���
					if (index==5)
					{
						strAdd = strAdd.trim ();
						Log.print ("ȡ��5������ = "+strAdd);
						try
						{
							upLoadInterestCheckInfo.setInterestAmount(Double.parseDouble(strAdd));
						}
						catch(Exception e)
						{
							Log.print ("ȡ��5������!��ʽ������!");
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
							returnVector.addElement (upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���֣�::"+strAdd);
						}
					}
									
				}//
				if(!upLoadInterestCheckInfo.getAccountNo().equals(""))
				{
						//conditionVector.addElement(billInfo);
				}
			}
								
			if (returnVector.size () < 1 )
			{
				Iterator it = conditionVector.iterator ();

				Log.print("��ʼ��������");

//				Log.print("ɾ����ǰ���������");
//				con = Database.getConnection();
//
//				ps = con.prepareStatement (" delete from SETT_AccountInterestCheck where AccountNo = " + lDiscountApplyID
//												+ " and InterestStartDate = " +
//												+ " and InterestClearDate = "
//											);
//				
//				ps.executeQuery ();
//				Log.print("�ɹ�ɾ����ǰ���������");
//				if (ps!=null)
//				{
//					ps.close();
//					ps=null;
//				}

				lIsertRowSum = 0;

				while (it.hasNext ())
				{
					Log.print("aa");

					UpLoadInterestCheckInfo upLoadInterestCheckInfo = (UpLoadInterestCheckInfo)it.next();

					lIsertRowSum++;

					strResult = this.save(upLoadInterestCheckInfo);

					if (strResult!=null && strResult.length()>0)
					{
						Log.print("�ɹ������" + lIsertRowSum + "����¼��");
					}
					else
					{
						UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
						returnVector.addElement (upLoanReturnInfo);
						upLoanReturnInfo.setIsOk(false);
					
						upLoanReturnInfo.setPositionRow(lIsertRowSum);
						upLoanReturnInfo.setPositionCol(0);
							 
						upLoanReturnInfo.setReason("����д�����ݿⷢ���쳣����ȫ�����µ��룡");
					}
					if (ps!=null)
					{
						ps.close ();
						ps = null;
					}
									
				}
				
				Log.print ("===�ɹ���������===");
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			
			Log.print ("===�ɹ��ر�����===");
								
		}
		catch(SQLException  sqle)
		{
			Log.print (sqle.toString ());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
			
			UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
			returnVector.addElement (upLoanReturnInfo);
			upLoanReturnInfo.setIsOk(false);
			upLoanReturnInfo.setPositionRow(lIsertRowSum);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason("д����߶�ȡ���ݿⷢ���쳣����ȫ�����µ��룡");
		}
		catch(Exception e)
		{
			Log.print (e.toString ());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
							
			UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
			returnVector.addElement (upLoanReturnInfo);
			upLoanReturnInfo.setIsOk(false);
			upLoanReturnInfo.setPositionRow(lIsertRowSum);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason(e.toString()+"��");
		}						
		finally
		{
			Log.print ("===finally===");
			try
			{
				if (myFile != null)
					myFile = null;
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch(Exception e)
			{
				Log.print (e.toString ());
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
				returnVector.addElement (upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(false);
				upLoanReturnInfo.setPositionRow(lIsertRowSum);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason(e.toString()+"��");
			}
		}
		
		Log.print ("===finally=ok==");
		return (returnVector.size () > 0 ? returnVector : null);
	}
	
	/**
	 * ���������˻���Ϣ��Ϣ��
	 * �߼�˵����
	 * 
	 * @param info, UpLoadInterestCheckInfo, ��Ϣ����ʵ����
	 * @return String, �����ɼ�¼���˻���
	 * @throws IException
	 */
	public String save( UpLoadInterestCheckInfo info) throws Exception
	{
		String strReturn = "";
		long lCount = -1;
		int index = 1;
		Connection con = null;

		con = getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append(" select count(*) from SETT_AccountInterestCheck ");
			buffer.append(" where AccountNo like '" + info.getAccountNo() + "'");
			buffer.append(" and InterestStartDate = ? " );
			buffer.append(" and InterestClearDate = ? " );
			
			index = 1;
			ps = con.prepareStatement(buffer.toString());
			ps.setTimestamp(index++, info.getInterestStartDate());
			ps.setTimestamp(index++, info.getInterestClearDate());
			rs = ps.executeQuery();
			if(rs!=null && rs.next())
			{
				lCount = rs.getLong(1);
			}
			cleanup(rs);
			cleanup(ps);			
			
			if(lCount>0)
			{
				buffer = new StringBuffer();
				buffer.append(" update SETT_AccountInterestCheck set ");
				buffer.append(" InterestAmount=? ,Abstract=? ,StatusID=? ");
				buffer.append(" where AccountNo=? and InterestStartDate=? and InterestClearDate=? ");

				ps = con.prepareStatement(buffer.toString());
				index = 1;
				ps.setDouble(index++, info.getInterestAmount());
				ps.setString(index++, info.getAbstract());
				ps.setLong(index++, info.getStatusID()); 
				ps.setString(index++, info.getAccountNo());
				ps.setTimestamp(index++, info.getInterestStartDate());
				ps.setTimestamp(index++, info.getInterestClearDate());
				
				ps.executeUpdate();
				strReturn = info.getAccountNo();
			}
			else
			{
				buffer = new StringBuffer();
				buffer.append(" INSERT INTO SETT_AccountInterestCheck \n");
				buffer.append(" (AccountNo,InterestStartDate,InterestClearDate, \n");
				buffer.append(" InterestAmount,Abstract,StatusID) \n");
				buffer.append(" VALUES \n");
				buffer.append(" (?,?,?,?,?,?) \n");

				ps = con.prepareStatement(buffer.toString());
				
				index = 1;
				ps.setString(index++, info.getAccountNo());
				ps.setTimestamp(index++, info.getInterestStartDate());
				ps.setTimestamp(index++, info.getInterestClearDate());
				ps.setDouble(index++, info.getInterestAmount());
				ps.setString(index++, info.getAbstract());
				ps.setLong(index++, info.getStatusID());			

				int i = ps.executeUpdate();
				if (i > 0)
				{
					strReturn = info.getAccountNo();
				}
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return strReturn;
	}
	
	/**
	 * ����˵�������ݲ�ѯ������ϣ���ѯ�����ϵļ�¼
	 *  Method findByConditions.
	 * @param UpLoadInterestCheckInfo
	 * @param orderBySequence 
	 * @return Collection
	 */
	public Collection findByConditions(UpLoadInterestCheckInfo conditionInfo)
		throws Exception
	{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector v_Result = new Vector();
		int index = 1;
		try
		{
			con = getConnection();

			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(" select * from ");
			strSQLBuffer.append(" (select a.sbankaccountno,a.sbankaccountname ,a.nwithinaccountid ,nvl(b.minterest,-1) settInterest , \n");
			strSQLBuffer.append(" b.dtintereststart settDTStart ,b.dtinterestend settDTEnd ,b.dtInterestSettlement settDTInterest ,nvl(b.id,-1) transInterestID ");
			strSQLBuffer.append(" from sett_bankaccountoffiliale a ,sett_transinterestsettlement b \n");
			strSQLBuffer.append(" where a.nupbankaccountid>0 and b.nstatusid>0 and a.nwithinaccountid = b.naccountid(+) and a.nbnktype = ? \n");
			strSQLBuffer.append(" ) aa,sett_accountinterestcheck bb \n");
			strSQLBuffer.append(" where aa.sbankaccountno = bb.accountno(+) \n");
			strSQLBuffer.append(" and aa.settDTStart = bb.intereststartdate(+) and aa.settDTInterest = bb.interestcleardate(+) ");
			
			if (conditionInfo.getInterestStartDate() != null)
			{
				strSQLBuffer.append(" AND aa.settDTStart = ? \n");
			}

			if (conditionInfo.getInterestClearDate() != null)
			{
				strSQLBuffer.append(" AND aa.settDTInterest = ? \n");
			}

			log.info(strSQLBuffer.toString());
			ps = con.prepareStatement(strSQLBuffer.toString());
			ps.setLong(index++, SETTConstant.BankType.ICBC);
			if (conditionInfo.getInterestStartDate() != null)
			{
				ps.setTimestamp(index++, conditionInfo.getInterestStartDate());
			}
			if (conditionInfo.getInterestClearDate() != null)
			{
				ps.setTimestamp(index++, conditionInfo.getInterestClearDate());
			}
			rs = ps.executeQuery();

			while (rs.next())
			{
				UpLoadInterestCheckInfo info = new UpLoadInterestCheckInfo();
				info.setTransInterestID(rs.getLong("transInterestID"));
				info.setSettBankAccountNo(rs.getString("sbankaccountno"));//���ݽ������ò��ҵĻ����˻���Ӧ�����˻�
				info.setAccountNo(rs.getString("accountno"));//excel���е�����˻�
				info.setInterestStartDate(rs.getTimestamp("settDTStart"));
				info.setInterestClearDate(rs.getTimestamp("settDTInterest"));
				//Log.print("--------------time = "+ info.getInterestStartDate());
				info.setInterestAmount(rs.getDouble("InterestAmount"));
				info.setAccountName(rs.getString("sbankaccountname"));
				info.setSettAccountID(rs.getLong("nwithinaccountid"));
				info.setSettInterestAmount(rs.getDouble("settInterest"));
				v_Result.add(info);
			}

			v_Result = (v_Result.size() > 0) ? v_Result : null;
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			cleanup(con);
		}
		return v_Result;
	}
	
	/**
	 * ����˵��������Ϣ����ʱ���˷���������Ϣ����ɾ��ʱ�Ĳ�����
	 *  Method findInterestSettInfo.
	 * @param Connection ,long[] 
	 * @return Vector
	 */
	public Vector findInterestSettInfo(Connection con,String[] strTransInterestID) throws Exception
	{
		Vector v_Return = new Vector();
		//InterestSettmentInfo info = new InterestSettmentInfo();
		String strID = null;
		if(strTransInterestID!=null && strTransInterestID.length>0)
		{
			strID = "(";
			for(int i = 0;i<strTransInterestID.length;i++)
			{
				strID = strID + strTransInterestID[i] + ",";
			}
			if(strID.length()>1)
			{
				strID = strID.substring(0,strID.length()-1);
				strID = strID+")";
			}
			else
			{
				strID = null;
			}
		}
		
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{

			StringBuffer strSQL = new StringBuffer();
						
			strSQL.append(" select account.ID AS AccountID, \n");  
			strSQL.append(" account.sAccountNo AS AccountNo, \n");                        //�˻���
			strSQL.append(" account.nAccountTypeID AS AccountTypeID, \n");                 //�˻�����
			strSQL.append(" subaccount.ID AS SubAccountID, \n");
			strSQL.append(" subaccount.AF_sDepositNo AS fixedDepositNo, "); //���ڵ��ݺ�
			strSQL.append(" subaccount.AL_mPreDrawInterest AS loanPreDrawInterest, "); //���������Ϣ
			strSQL.append(" contractform.sContractCode AS contractNo, \n");                //��ͬ��                             //���˻� ID
			strSQL.append(" payform.sCode AS payFormNo, \n");  		
			strSQL.append(" interest.* \n");  					
			strSQL.append(" from sett_Account account, sett_SubAccount subaccount, loan_PayForm payform, SETT_TRANSINTERESTSETTLEMENT interest,loan_ContractForm contractform, loan_LoanForm loanform \n");
			// where 			
			strSQL.append(" where account.ID = interest.nAccountID ");
			strSQL.append(" AND ");
			strSQL.append(" interest.nSubAccountID = subaccount.ID  ");
			strSQL.append(" AND ");
			strSQL.append(" subaccount.AL_nLoanNoteID = payform.ID(+)  ");
			strSQL.append(" AND ");
			strSQL.append(" payform.nContractID = contractform.ID(+)  ");
			strSQL.append(" AND ");
			strSQL.append(" contractform.nLoanID = loanform.ID(+)  ");		
			
			if(strID!=null)
			{
				strSQL.append(" and interest.id in " + strID);	
			}

			log.info(strSQL.toString());
			ps = conInternal.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				InterestSettmentInfo info = new InterestSettmentInfo();
				info.setID(rs.getLong("ID"));
				info.setAccountID(rs.getLong("AccountID"));
				info.setAccountNo(rs.getString("AccountNo"));
				info.setAccountTypeID(rs.getLong("AccountTypeID"));
				info.setSubAccountID(rs.getLong("SubAccountID"));
				info.setContractNo(rs.getString("contractNo"));
				info.setPayFormNo(rs.getString("payFormNo"));
				info.setFixedDepositNo(rs.getString("fixedDepositNo"));
				info.setLoanPreDrawInterest(rs.getDouble("loanPreDrawInterest"));
				info.setInterestStartDate(rs.getTimestamp("DTINTERESTSTART"));
				info.setInterestEndDate(rs.getTimestamp("DTINTERESTSETTLEMENT"));
				info.setInterestDays(rs.getLong("nInterestDays"));
				info.setInterest(rs.getDouble("mInterest"));
				info.setRate(rs.getDouble("mRate"));
				info.setNegotiateInterest(rs.getDouble("mNegotiateInterest"));
				info.setBaseBalance(rs.getDouble("mBaseBalance"));
				info.setInterestTax(rs.getDouble("mInterestTax"));
				info.setTransNo(rs.getString("sTransNo"));
				info.setInputUserID(rs.getLong("nInputUserID"));
				info.setExecuteDate(rs.getTimestamp("dtExecute"));
				info.setInterestType(rs.getLong("nInterestType"));
				info.setOperationType(rs.getLong("nOperationType"));
				info.setPayInterestAccountID(rs.getLong("NPAYINTERESTACCOUNTID"));
				info.setReceiveInterestAccountID(rs.getLong("NRECEIVEINTERESTACCOUNTID"));
				info.setIsKeepAccount(rs.getLong("NISKEEPACCOUNT"));
				info.setOfficeID(rs.getLong("NOFFICEID"));
				info.setCurrencyID(rs.getLong("NCURRENCYID"));
				v_Return.add(info);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				cleanup(rs);
				cleanup(ps);
				if(con==null)
				{
					cleanup(conInternal);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return v_Return.size()>0?v_Return:null;
	}

	/**
	 * ����˵�����޸��˻���Ϣ��¼
	 *  Method correctInterestInfo.
	 * @param InterestSettmentInfo
	 * @return InterestQueryResultInfo
	 */
	public InterestQueryResultInfo convertInterestSettmentInfo(Connection con,InterestSettmentInfo info)
		throws Exception
	{
		InterestQueryResultInfo returnInfo = new InterestQueryResultInfo();
		Connection conInternal = null;
		if (con == null)
		{
			conInternal = getConnection();
		}
		else
		{
			conInternal = con;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Collection result = null;
		int index = 1;
				
		try
		{
			returnInfo.setAccountNo(info.getAccountNo());
			returnInfo.setAccountTypeID(info.getAccountTypeID());
			returnInfo.setAccountID(info.getAccountID());
			returnInfo.setSubAccountID(info.getSubAccountID());
			returnInfo.setFixedDepositNo(info.getFixedDepositNo());
			returnInfo.setContractNo(info.getContractNo());
			returnInfo.setPayFormNo(info.getPayFormNo());
			returnInfo.setLoanPreDrawInterest(info.getLoanPreDrawInterest());
			returnInfo.setInterestType(SETTConstant.InterestFeeType.INTEREST);
			
			sett_TransAccountDetailDAO transDetailDAO = new sett_TransAccountDetailDAO(conInternal);
			InterestOperation io = new InterestOperation(conInternal);
			InterestBatch ib = new InterestBatch(conInternal);
			InterestSettlement interestSettlement= new InterestSettlement();
			Log.print("--------------info.getInterestEndDate() = "+info.getInterestEndDate());
			Log.print("--------------ExcuteDate() = "+Env.getSystemDate(conInternal,info.getOfficeID(),info.getCurrencyID()));
			Log.print("--------------ExcuteDate() = "+info.getExecuteDate());
			
			returnInfo = interestSettlement.getInterestInfo(
					returnInfo.getFixedDepositNo(),
					returnInfo.getLoanPreDrawInterest(),
					returnInfo.getContractNo(),
					returnInfo.getPayFormNo(),
					returnInfo.getSubAccountID(),
					returnInfo.getAccountID(),
					returnInfo.getAccountTypeID(),
					info.getOfficeID(),
					info.getCurrencyID(),
					info.getInterestEndDate(),
					Env.getSystemDate(conInternal,info.getOfficeID(),info.getCurrencyID()),
					SETTConstant.InterestFeeType.INTEREST,
					io,
					ib,
					transDetailDAO
					);						
			
			//�滻��ȷ����Ϣֵ
			StringBuffer strSQLBuffer = new StringBuffer();
			strSQLBuffer.append(" select a.interestamount from sett_accountinterestcheck a ,sett_transinterestsettlement b,sett_bankaccountoffiliale c ");
			strSQLBuffer.append(" where b.naccountid = c.nwithinaccountid and a.accountno = c.sbankaccountno ");
			strSQLBuffer.append(" and b.naccountid = " + returnInfo.getAccountID() );
			strSQLBuffer.append(" and a.interestcleardate = ? ");

			log.info(strSQLBuffer.toString());
			ps = conInternal.prepareStatement(strSQLBuffer.toString());
			ps.setTimestamp(1,info.getInterestEndDate());
			rs = ps.executeQuery();

			if (rs.next())
			{				
				returnInfo.setInterest(rs.getDouble("interestamount"));
			}
		}
		finally
		{
			cleanup(rs);
			cleanup(ps);
			if(con==null)
			{
				cleanup(conInternal);
			}
		}
		return returnInfo;
	}
	
}
