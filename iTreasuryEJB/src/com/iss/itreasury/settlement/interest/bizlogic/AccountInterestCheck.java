package com.iss.itreasury.settlement.interest.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.interest.dao.Sett_AccountInterestCheckDAO;
import com.iss.itreasury.settlement.interest.dataentity.InterestQueryResultInfo;
import com.iss.itreasury.settlement.interest.dataentity.InterestSettmentInfo;
import com.iss.itreasury.settlement.interest.dataentity.UpLoadInterestCheckInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class AccountInterestCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public  AccountInterestCheck()
	{
		// TODO Auto-generated method stub
	}
	
	/**
	 * 方法说明：从excel文件中导入银行账户利息
	 *  Method importInterestInfo.
	 * @param SmartUpload ,long, long
	 * @return Collection
	 */
	public Collection importInterestInfo(com.jspsmart.upload.SmartUpload mySmartUpload,long lCurrencyID,long lOfficeID) throws Exception
	{
		Collection c_Return = null;
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			c_Return = dao.saveUploadFileToDateBase(mySmartUpload,lCurrencyID,lOfficeID);
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c_Return;
	}

	/**
	 * 方法说明：查找对照结算账户利息与银行账户利息
	 *  Method queryInterestCheckInfo.
	 * @param UpLoadInterestCheckInfo ,InterestSettmentInfo
	 * @return Collection
	 */
	public Collection queryInterestCheckInfo(UpLoadInterestCheckInfo info) throws Exception
	{
		Collection c_Return = null;
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			c_Return = dao.findByConditions(info);
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return c_Return;
	}
	
	/**
	 * 方法说明：根据用户选择，修正结算活期账户利息（校正值为银行利息）
	 *  Method correctInterestInfo.
	 * @param long[] ,InterestSettmentInfo
	 * @return Collection
	 */
	public Collection correctInterestInfo(String[] strTransInterestID,InterestSettmentInfo settmentInfo) throws Exception
	{
		Collection c_Return = null;
		Vector v_KeepAccount = new Vector();
		Vector v_NotKeepAccount = new Vector();
		Connection con = Database.getConnection();
		//事务维护
		con.setAutoCommit(false);
		try
		{
			Sett_AccountInterestCheckDAO dao = new Sett_AccountInterestCheckDAO();
			InterestSettlement interestSettlement= new InterestSettlement();
			//获取InterestSettmentInfo集
			Vector v_ISInfo = dao.findInterestSettInfo(con,strTransInterestID);
			//删除结息记录（当天操作）
			interestSettlement.delInterest(con,v_ISInfo,settmentInfo);
			//重新结算利息
			//组成两个Vector用于重新结算利息（已修正利息，分记账与不记账）
			
			for(int i = 0;i<v_ISInfo.size();i++)
			{
				InterestSettmentInfo iSInfo = (InterestSettmentInfo)v_ISInfo.elementAt(i);
				InterestQueryResultInfo iRInfo= dao.convertInterestSettmentInfo(con,iSInfo);
				if(iSInfo.getIsKeepAccount() == Constant.YesOrNo.YES)
				{
					v_KeepAccount.add(iRInfo);
				}
				else
				{
					v_NotKeepAccount.add(iRInfo);
				}
			}
			//结算利息
			settmentInfo.setIsKeepAccount(Constant.YesOrNo.YES);
			interestSettlement.balanceInterest(con,v_KeepAccount,settmentInfo);
			
			settmentInfo.setIsKeepAccount(Constant.YesOrNo.NO);
			interestSettlement.balanceInterest(con,v_NotKeepAccount,settmentInfo);
			
			//事务提交，完成逻辑
			con.commit();
			con.setAutoCommit(true);
			
		}
		catch(IException ie)
		{
			ie.printStackTrace();
			try
			{
				//****连接回滚******
				con.rollback();
				con.setAutoCommit(true);
			}
			catch (Exception eRollback)
			{
				throw new IException("事务回退异常");
			}
			throw ie;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				//****连接回滚******
				con.rollback();
				con.setAutoCommit(true);
			}
			catch (Exception eRollback)
			{
				throw new IException("事务回退异常");
			}
			throw new IException("Gen_E001");
		}
		finally
		{
			//不管是成功与否，如果该方法启动了事务，在最后应该进行连接资源的释放。
			try
			{
					//"****关闭连接******
					con.close();
					con = null;
			}
			catch (Exception eClose)
			{
				throw new IException("关闭连接失败！");
			}
		}
		
		return c_Return;
	}
}
