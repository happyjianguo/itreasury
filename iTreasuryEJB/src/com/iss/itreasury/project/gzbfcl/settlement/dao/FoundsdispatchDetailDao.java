package com.iss.itreasury.project.gzbfcl.settlement.dao;

import java.util.Collection;
import java.util.Vector;


import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchDetailInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.QueryFoundsdispatchDetailInfo;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
public class FoundsdispatchDetailDao extends SettlementDAO
{
	
	public FoundsdispatchDetailDao()
	{
		super("Sett_FoundsdispatchDetail");
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	

	 public long saveFoundsdispatchDetailInfo(FoundsdispatchDetailInfo detailinfo) throws Exception
	 {
			long lResult = -1;
			String strTmpSQL = "";
			long lTmpID=-1;
			try
			{
				initDAO();

					if (detailinfo.getId() < 0)
					{
						//得到最大ID
						strTmpSQL = "select nvl(max(ID)+1,1) ID from Sett_FoundsdispatchDetail";
						transPS = prepareStatement(strTmpSQL);
						transRS = transPS.executeQuery();
						if (transRS != null && transRS.next())
							lTmpID = transRS.getLong("ID");
						transRS.close();
						transRS = null;
						transPS.close();
						transPS = null;
						//insert new record
						strTmpSQL = "insert into Sett_FoundsdispatchDetail(ID,FoundsDispatchID,FromOrganizationID,FromBankAccountID,ToOrganizationID,ToBankAccountID,Amount,FoundsAplication,OfficeID,CurrentID,inputUserID,inputTime,statusID) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
						transPS = prepareStatement(strTmpSQL);
						transPS.setLong(1, lTmpID);
						transPS.setLong(2, detailinfo.getFoundsDispatchID());
						transPS.setLong(3, detailinfo.getFromOrganizationID());
						transPS.setLong(4, detailinfo.getFromBankAccountID());
						transPS.setLong(5, detailinfo.getToOrganizationID());
						transPS.setLong(6, detailinfo.getToBankAccountID());	
						transPS.setDouble(7, detailinfo.getAmount());
						transPS.setString(8, detailinfo.getFoundsAplication());
						transPS.setLong(9, detailinfo.getOfficeID());
						transPS.setLong(10, detailinfo.getCurrentID());
						
						transPS.setLong(11, detailinfo.getInputUserID());
						transPS.setTimestamp(12, detailinfo.getInputTime());
						transPS.setLong(13, Constant.RecordStatus.VALID);
						lResult = transPS.executeUpdate();
						transPS.close();
						transPS = null;
					}
					else
					{ //update the record
						strTmpSQL = "update Sett_FoundsdispatchDetail set FoundsDispatchID=?,FromOrganizationID=?,FromBankAccountID=?,ToOrganizationID=?,ToBankAccountID=?,Amount=?,FoundsAplication=?,OfficeID=?,CurrentID=?,ModifyUserID=?,ModifyTime=?,statusID=? where ID=?";
						transPS = prepareStatement(strTmpSQL);
						transPS.setLong(1, detailinfo.getFoundsDispatchID());
						transPS.setLong(2, detailinfo.getFromOrganizationID());
						transPS.setLong(3, detailinfo.getFromBankAccountID());
						transPS.setLong(4, detailinfo.getToOrganizationID());
						transPS.setLong(5, detailinfo.getToBankAccountID());	
						transPS.setDouble(6, detailinfo.getAmount());
						transPS.setString(7, detailinfo.getFoundsAplication());
						transPS.setLong(8, detailinfo.getOfficeID());
						transPS.setLong(9, detailinfo.getCurrentID());
						
						transPS.setLong(10, detailinfo.getModifyUserID());
						transPS.setTimestamp(11, detailinfo.getModifyTime());
						transPS.setLong(12, Constant.RecordStatus.VALID);
						transPS.setLong(13, detailinfo.getId());
						lResult = transPS.executeUpdate();
						transPS.close();
						transPS = null;
					}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}

			return lResult;
		
 

				
	}
	 /*
	  * 
	  * 根据资金调度令id查找所有明细id
	  * 
	  * */
	 public Collection findQueryFoundsdispatchDetailInfobyFoundsdipatchID(long foundsdispatchID) throws Exception
	 {
			
			Vector v = new Vector();

			String strTmpSQL = null;
			try
			{
				Log.print("findFoundsdispatchDetailInfobyFoundsdipatchID");
				initDAO();
				//返回需求的结果集
				strTmpSQL = "select a.*,"
				          + " c.OrganizationName FromOrganizationName,"
				          + " c2.OrganizationName ToOrganizationName,"
				          + " d.BankAccountCode FromBankAccountNo,"
				          + " d2.BankAccountCode ToBankAccountNo,"
				          + " d.BankName FromBankName,"
				          + " d2.BankName ToBankName,"
				          + " e.sName inputUserName," 
				          + " f.sName officeName "
				      
				          + " from  Sett_FoundsDispatchDetail a, fd_organization c ,fd_organization c2,fd_bankaccount d,fd_bankaccount d2,userinfo e,office f"
				          + " where a.statusID=1"
				          + " and c.id = a.FromOrganizationID" 
				          + " and c2.id = a.ToOrganizationID "
				          + " and d.id = a.FromBankAccountID "
				          + " and d2.id = a.ToBankAccountID "
				          + " and e.id = a.inputUSerID "
				          + " and f.id = a.OfficeID"
				          + " and FoundsDispatchID = ? order by a.ID ";
				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, foundsdispatchID);
				transRS = executeQuery();
				while (transRS.next())
				{
					QueryFoundsdispatchDetailInfo Querydetailinfo = new QueryFoundsdispatchDetailInfo();
					Querydetailinfo.setId(transRS.getLong("ID"));
					Querydetailinfo.setFoundsDispatchID(transRS.getLong("FoundsDispatchID")) ;
					Querydetailinfo.setFromOrganizationID(transRS.getLong("FromOrganizationID"));
					Querydetailinfo.setFromOrganizationName(transRS.getString("fromOrganizationName"));
					Querydetailinfo.setFromBankAccountID(transRS.getLong("FromBankAccountID"));
					Querydetailinfo.setFromBankAccountNo(transRS.getString("fromBankAccountNo"));
					Querydetailinfo.setFromBankName(transRS.getString("fromBankName"));
					Querydetailinfo.setToOrganizationID(transRS.getLong("ToOrganizationID"));
					Querydetailinfo.setToOrganizationName(transRS.getString("toOrganizationName"));
					Querydetailinfo.setToBankAccountID(transRS.getLong("ToBankAccountID"));
					Querydetailinfo.setToBankAccountNo(transRS.getString("toBankAccountNo"));
					Querydetailinfo.setToBankName(transRS.getString("toBankName"));
					Querydetailinfo.setAmount(transRS.getDouble("Amount"));
					Querydetailinfo.setFoundsAplication(transRS.getString("FoundsAplication")) ;
					Querydetailinfo.setOfficeID(transRS.getLong("OfficeID"));
					Querydetailinfo.setCurrentID(transRS.getLong("currentID"));
					Querydetailinfo.setInputUserID(transRS.getLong("inputUserID"));
					Querydetailinfo.setInputTime(transRS.getTimestamp("inputTime"));
					Querydetailinfo.setModifyUserID(transRS.getLong("ModifyUserID"));
					Querydetailinfo.setModifyTime(transRS.getTimestamp("ModifyTime"));
					Querydetailinfo.setStatusID(transRS.getLong("statusID"));

					v.addElement(Querydetailinfo);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}
			return (v.size() > 0 ? v : null);

	 }
	 
	 public Collection findQueryFoundsdispatchDetailInfobyFoundsdipatchIDandConditions(QueryFoundsdispatchDetailInfo queryDetailInfo) throws Exception
	 {
			
			Vector v = new Vector();

			String strTmpSQL = null;
			try
			{
				Log.print("findFoundsdispatchDetailInfobyFoundsdipatchID");
				initDAO();
				//返回需求的结果集
				strTmpSQL = "select a.*,"
				          + " c.OrganizationName FromOrganizationName,"
				          + " c2.OrganizationName ToOrganizationName,"
				          + " d.BankAccountCode FromBankAccountNo,"
				          + " d2.BankAccountCode ToBankAccountNo,"
				          + " d.BankName FromBankName,"
				          + " d2.BankName ToBankName,"
				          + " e.sName inputUserName," 
				          + " f.sName officeName "
				      
				          + " from  Sett_FoundsDispatchDetail a, fd_organization c ,fd_organization c2,fd_bankaccount d,fd_bankaccount d2,userinfo e,office f"
				          + " where a.statusID=1"
				          + " and c.id = a.FromOrganizationID" 
				          + " and c2.id = a.ToOrganizationID "
				          + " and d.id = a.FromBankAccountID "
				          + " and d2.id = a.ToBankAccountID "
				          + " and e.id = a.inputUSerID "
				          + " and f.id = a.OfficeID";
				          

				
				if(queryDetailInfo.getFoundsDispatchID()>0)
				{
					strTmpSQL+=" and a.FoundsDispatchID= "+queryDetailInfo.getFoundsDispatchID();
				}

				if(queryDetailInfo.getFromOrganizationID()>0)
				{
					strTmpSQL+=" and a.FromOrganizationID = "+queryDetailInfo.getFromOrganizationID();
				}
				if(queryDetailInfo.getToOrganizationID()>0)
				{
					strTmpSQL+=" and a.ToOrganizationID = "+queryDetailInfo.getToOrganizationID();
				}
				strTmpSQL += " order by ID asc";
				transPS = prepareStatement(strTmpSQL);
				transRS = executeQuery();
				
				while (transRS.next())
				{
					QueryFoundsdispatchDetailInfo Querydetailinfo = new QueryFoundsdispatchDetailInfo();
					Querydetailinfo.setId(transRS.getLong("ID"));
					Querydetailinfo.setFoundsDispatchID(transRS.getLong("FoundsDispatchID")) ;
					Querydetailinfo.setFromOrganizationID(transRS.getLong("FromOrganizationID"));
					Querydetailinfo.setFromOrganizationName(transRS.getString("fromOrganizationName"));
					Querydetailinfo.setFromBankAccountID(transRS.getLong("FromBankAccountID"));
					Querydetailinfo.setFromBankAccountNo(transRS.getString("fromBankAccountNo"));
					Querydetailinfo.setFromBankName(transRS.getString("fromBankName"));
					Querydetailinfo.setToOrganizationID(transRS.getLong("ToOrganizationID"));
					Querydetailinfo.setToOrganizationName(transRS.getString("toOrganizationName"));
					Querydetailinfo.setToBankAccountID(transRS.getLong("ToBankAccountID"));
					Querydetailinfo.setToBankAccountNo(transRS.getString("toBankAccountNo"));
					Querydetailinfo.setToBankName(transRS.getString("toBankName"));
					Querydetailinfo.setAmount(transRS.getDouble("Amount"));
					Querydetailinfo.setFoundsAplication(transRS.getString("FoundsAplication")) ;
					Querydetailinfo.setOfficeID(transRS.getLong("OfficeID"));
					Querydetailinfo.setCurrentID(transRS.getLong("currentID"));
					Querydetailinfo.setInputUserID(transRS.getLong("inputUserID"));
					Querydetailinfo.setInputTime(transRS.getTimestamp("inputTime"));
					Querydetailinfo.setModifyUserID(transRS.getLong("ModifyUserID"));
					Querydetailinfo.setModifyTime(transRS.getTimestamp("ModifyTime"));
					Querydetailinfo.setStatusID(transRS.getLong("statusID"));

					v.addElement(Querydetailinfo);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}
			return (v.size() > 0 ? v : null);

	 }
	 /*
	  * 
	  * 通过资金调度令id找出其明细信息
	  * 
	  * */
	 public Collection findFoundsdispatchDetailInfoByFoundsdipatchID(long foundsdispatchID) throws Exception{
		 
			Vector v = new Vector();

			String strTmpSQL = null;
			try
			{
				Log.print("findFoundsdispatchDetailInfobyFoundsdipatchID ");
				initDAO();
				//返回需求的结果集
				strTmpSQL = "select * from  Sett_FoundsDispatchDetail where statusID=1 and FoundsDispatchID = ? order by ID";

				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, foundsdispatchID);
				transRS = executeQuery();
				while (transRS.next())
				{
					FoundsdispatchDetailInfo detailinfo = new FoundsdispatchDetailInfo();
					detailinfo.setId(transRS.getLong("ID"));
					detailinfo.setFoundsDispatchID(transRS.getLong("FoundsDispatchID")) ;
					detailinfo.setFromOrganizationID(transRS.getLong("FromOrganizationID"));
					detailinfo.setFromBankAccountID(transRS.getLong("FromBankAccountID"));
					detailinfo.setToOrganizationID(transRS.getLong("ToOrganizationID"));
					detailinfo.setToBankAccountID(transRS.getLong("ToBankAccountID"));
					detailinfo.setAmount(transRS.getDouble("Amount"));
					detailinfo.setFoundsAplication(transRS.getString("FoundsAplication")) ;
					detailinfo.setOfficeID(transRS.getLong("OfficeID"));
					detailinfo.setCurrentID(transRS.getLong("currentID"));
					detailinfo.setInputUserID(transRS.getLong("inputUserID"));
					detailinfo.setInputTime(transRS.getTimestamp("inputTime"));
					detailinfo.setModifyUserID(transRS.getLong("ModifyUserID"));
					detailinfo.setModifyTime(transRS.getTimestamp("ModifyTime"));
					detailinfo.setStatusID(transRS.getLong("statusID"));

					v.addElement(detailinfo);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}
			return (v.size() > 0 ? v : null);
	 
		 
		 
		 
		 
		 
	 }
	 /*
	  * 分页查询
	  * 
	  * */
		public String getQueryFoundsdispatchDetailInfo(QueryFoundsdispatchDetailInfo info)
		{
			String strSelect ="";
			String strFrom = "";
			
			strSelect = " (select a.*,"

				+ "b.FoundsdispatchCode ,b.FoundsDispatchReceive,"
				+ " c.OrganizationName FromOrganizationName,"
				+ " c2.OrganizationName ToOrganizationName,"
				+ " d.BankAccountCode FromBankAccountNo,"
				+ " d2.BankAccountCode ToBankAccountNo,"
				+ " d.BankName FromBankName,"
				+ " d2.BankName ToBankName,"
			    + " e.sName inputUserName," 
			    + " f.sName officeName" ;
			
			strFrom = " from  Sett_FoundsDispatchDetail a, Sett_FoundsDispatch  b,  fd_organization c ,fd_organization c2,fd_bankaccount d,fd_bankaccount d2,userinfo e,office f"
				+ " where 1 = 1 "
				+ " and b.id = a.FoundsdispatchID "
				+ " and c.id = a.FromOrganizationID "
				+ " and c2.id = a.ToOrganizationID "
				+ " and d.id = a.FromBankAccountID "
				+ " and d2.id = a.ToBankAccountID "
				+ " and e.id = a.inputUSerID "
				+ " and f.id = a.OfficeID "
				+ " and a.officeid = "
				+ info.getOfficeID()
				+ " and a.Currentid = "
				+ info.getCurrentID()
				+ " and a.StatusID = "
				+ Constant.RecordStatus.VALID;
	
			if(info.getFoundsDispatchID()>0)
			{
				strFrom+=" and a.FoundsDispatchID= "+info.getFoundsDispatchID();
			}
			else if(info.getFoundsDispatchCode()!=null && !(info.getFoundsDispatchCode().equals("")))
			{
				strFrom+=" and b.FoundsDispatchCode like '%"+info.getFoundsDispatchCode()+"%'";
			}
			if(info.getFromOrganizationID()>0)
			{
				strFrom+=" and a.FromOrganizationID = "+info.getFromOrganizationID();
			}
			else if(info.getFromOrganizationName()!=null && !(info.getFromOrganizationName().equals("")))
			{
				strFrom+=" and c.OrganizationName like '%"+info.getFromOrganizationName()+"%'";
			}
			if(info.getToOrganizationID()>0)
			{
				strFrom+=" and a.ToOrganizationID = "+info.getToOrganizationID();
			}
			else if(info.getToOrganizationName()!=null && !(info.getToOrganizationName().equals("")))
			{
				strFrom+=" and c2.OrganizationName like '%"+info.getToOrganizationName()+"%'";
			}

			
			return strSelect + strFrom+")";
		}
		
		/*
		 * 
		 * 
		 * (没用)
		 * 
		 * */
		public String getFoundsdispatchInfo(QueryFoundsdispatchDetailInfo info)
		{
			String strSelect ="";
			String strFrom = "";
			
			strSelect = " (select b.FoundsdispatchCode FoundsdispatchCode, max(b.ID) ID";
			
			strFrom = " from  Sett_FoundsDispatchDetail a, Sett_FoundsDispatch  b,  fd_organization c ,fd_organization c2,fd_bankaccount d,fd_bankaccount d2,userinfo e,office f"
				+ " where 1 = 1 "
				+ " and b.id = a.FoundsdispatchID "
				+ " and c.id = a.FromOrganizationID "
				+ " and c2.id = a.ToOrganizationID "
				+ " and d.id = a.FromBankAccountID "
				+ " and d2.id = a.ToBankAccountID "
				+ " and e.id = a.inputUSerID "
				+ " and f.id = a.OfficeID "
				+ " and a.officeid = "
				+ info.getOfficeID()
				+ " and a.Currentid = "
				+ info.getCurrentID()
				+ " and a.StatusID = "
				+ Constant.RecordStatus.VALID;
	
			if(info.getFoundsDispatchID()>0)
			{
				strFrom+=" and a.FoundsDispatchID= "+info.getFoundsDispatchID();
			}

			if(info.getFromOrganizationID()>0)
			{
				strFrom+=" and a.FromOrganizationID = "+info.getFromOrganizationID();
			}
			if(info.getToOrganizationID()>0)
			{
				strFrom+=" and a.ToOrganizationID = "+info.getToOrganizationID();
			}

			
			return strSelect + strFrom+" group by FoundsdispatchCode )";
		}
		
		/*
		 * 
		 * 判断单位名称是否已被资金调度令使用
		 * 
		 * */
		public long findFoundsDetailbyOrganizationID(long OrganizationID) throws Exception{
			
			long isUsed=0;

			String strTmpSQL = null;
			try
			{
				Log.print("findFoundsdispatchDetailInfobyFoundsdipatchID ");
				initDAO();
				//返回需求的结果集
				strTmpSQL = "select count(*) from  Sett_FoundsDispatchDetail where statusID=1 and ( FromOrganizationID =? or ToOrganizationID=? ) order by ID ";

				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, OrganizationID);
				transPS.setLong(2, OrganizationID);
				transRS = executeQuery();
				while (transRS.next())
				{

					isUsed=transRS.getLong("count(*)");

				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}
			return isUsed;
	 
			
		}
		
		/*
		 * 
		 * 判断开户行账户号是否已被资金调度令使用
		 * 
		 * */
		public long findFoundsDetailbyBankAccountID(long BankAccountID) throws Exception{
			
			long isUsed=0;

			String strTmpSQL = null;
			try
			{
				Log.print("findFoundsdispatchDetailInfobyFoundsdipatchID ");
				initDAO();
				//返回需求的结果集
				strTmpSQL = "select count(*) from  Sett_FoundsDispatchDetail where statusID=1 and ( FromBankAccountID =? or ToBankAccountID=? ) order by ID ";

				transPS = prepareStatement(strTmpSQL);
				transPS.setLong(1, BankAccountID);
				transPS.setLong(2, BankAccountID);
				transRS = executeQuery();
				while (transRS.next())
				{

					isUsed=transRS.getLong("count(*)");

				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new SettlementException();
			}
			finally
			{
				try
				{
					finalizeDAO();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					throw new SettlementException();
				}
			}
			return isUsed;
	 
			
		}
		
		
}

