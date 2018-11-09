package com.iss.itreasury.project.gzbfcl.settlement.dao;

import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchDetailInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchInfo;
import com.iss.itreasury.project.gzbfcl.util.CreateCode;
import com.iss.itreasury.settlement.base.SettlementDAO;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.DataFormat;
public class FoundsdispatchDao extends SettlementDAO
{
	
	public FoundsdispatchDao()
	{
		super("Sett_FoundsDispatch");
		super.setUseMaxID();
		// TODO Auto-generated constructor stub
	}
	 /*
	  * 资金调度令保存（没用）
	  * 
	  * 
	  * */
	 public long saveFoundsdispatchInfo(FoundsdispatchInfo info) throws Exception
	 {
			long lResult = -1;
			long lTmpID = info.getId();
			String lTmpCode="";
			String strTmpSQL = "";
			try
			{
				initDAO();

				if (info.getId() < 0)
				{	
					//产生资金调度令编码
					CreateCode createcode=new CreateCode();
					lTmpCode=createcode.createFoundsDispatatchCode(Long.valueOf((DataFormat.getDateString(info.getInputTime())).substring(0, 4)).longValue(),info.getCurrentID(),info.getOfficeID());
					//得到最大ID
					strTmpSQL = "select nvl(max(ID)+1,1) id from Sett_FoundsDispatch";
					transPS = prepareStatement(strTmpSQL);
					transRS = transPS.executeQuery();
					if (transRS != null && transRS.next())
						lTmpID = transRS.getLong("ID");
					transRS.close();
					transRS = null;
					transPS.close();
					transPS = null;
					
					
					//insert new record
					strTmpSQL = "insert into Sett_FoundsDispatch(ID,FoundsDispatchCode,FoundsDispatchReceive,OfficeID,CurrentID,inputUserID,inputTime,statusID) values(?,?,?,?,?,?,?,?)";
					transPS = prepareStatement(strTmpSQL);
					transPS.setLong(1, lTmpID);
					transPS.setString(2, lTmpCode);
					transPS.setString(3, info.getFoundsDispatchReceive());
					transPS.setLong(4, info.getOfficeID());
					transPS.setLong(5, info.getCurrentID());
					transPS.setLong(6, info.getInputUserID());
					transPS.setTimestamp(7, info.getInputTime());
					transPS.setLong(8, Constant.RecordStatus.VALID);
					lResult = transPS.executeUpdate();
					transPS.close();
					transPS = null;
				}
				else
				{ //update the record
					strTmpSQL = "update Sett_FoundsDispatch set FoundsDispatchCode=?,FoundsDispatchReceive=?,OfficeID=?,CurrentID=?,ModifyUserID=?,ModifyTime=?,statusID=? where ID=?";
					transPS = prepareStatement(strTmpSQL);
					transPS.setString(1, info.getFoundsDispatchCode());
					transPS.setString(2, info.getFoundsDispatchReceive());
					transPS.setLong(3, info.getOfficeID());
					transPS.setLong(4, info.getCurrentID());
					transPS.setLong(5, info.getModifyUserID());
					transPS.setTimestamp(6, info.getModifyTime());
					transPS.setLong(7, Constant.RecordStatus.VALID);
					transPS.setLong(8,1);
					transPS.setLong(9, info.getId());
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

}
