/*
 * Created on 2006-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.transfixeddeposit.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.settlement.query.bizlogic.QTransAccountBiz;
import com.iss.itreasury.settlement.transfixeddeposit.dao.NotifyDepositDao;
import com.iss.itreasury.settlement.transfixeddeposit.dao.Sett_NotifyDepositInformDAO;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.NotifyDepositInformInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.system.dao.PageLoader;

/**
 * @author caryzeng
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TransNotifyDepositBiz {
	
//	新增通知信息，将info里的信息insert到表sett_notifyDepositInform里
	public long add(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//修改通知信息，将info里的信息update到表sett_notifyDepositInform里
	public long modify(NotifyDepositInformInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//添加交易号
	public long insertTransNo(String transno , long id) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.insertTransNo(transno,id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//查询通知信息，按info里的信息查询表
	//sett_notifyDepositInform,sett_account,sett_transopenfixeddeposit,client
	//info里只需传入模块标识和办事处标识
	//返回由NotifyDepositInformInfo组成的集合
	public Collection findByCondition(NotifyDepositInformInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			vret = dao.findByCondition(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
//	查询通知信息，按id查询表sett_notifyDepositInform
	//返回NotifyDepositInformInfo
	public NotifyDepositInformInfo findByID(long id) throws Exception
	{
		NotifyDepositInformInfo info = null;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			info = dao.findByID(id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return info;
	}
	//根据id号查找某条指令的状态
	public long getStatus(long id) throws Exception
	{
		long lret = -1;
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			lret = dao.getStatus(id);
			//System.out.println("**********hybiz****************************lret="+lret);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	取消通知，循环调用dao的modify方法
	public long cancel(long[] id) throws Exception
	{
		long lret = -1;
		if (id==null)
		{
			return lret;
		}
		
		if (id.length<=0)
		{
			return lret;
		}
		
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			NotifyDepositInformInfo info = new NotifyDepositInformInfo();
			
			for(int i=0;i<id.length;i++)
			{
				info.setID(id[i]);
				info.setStatusID(SETTConstant.NotifyInformStatus.CANCEL);//这里将状态置为 取消
				lret = dao.modify(info);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	//分页链接查找
	public PageLoader findWithPage (NotifyDepositInformInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			Sett_NotifyDepositInformDAO dao = new Sett_NotifyDepositInformDAO();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return loader;
	}
	
	/**
	 * 通知存款支取通知查询
	 */
	public PagerInfo queryNotifyDeposit(NotifyDepositInformInfo qInfo) throws Exception{
			// TODO Auto-generated method stub
			NotifyDepositDao notifyDepositDao = new NotifyDepositDao();
			PagerInfo pagerInfo = null;
			String sql = null;
			try
			{
				pagerInfo = new PagerInfo();
				//得到查询SQL
				sql = notifyDepositDao.queryNotifyDepositSQL(qInfo);
				pagerInfo.setSqlString(sql);
				
				pagerInfo.setExtensionMothod(TransNotifyDepositBiz.class, "resultSetHandle");
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("====>查询异常", e);
			}
			return pagerInfo;
	}
	
	public ArrayList resultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		NotifyDepositInformInfo info = null;
		Vector v = null;
		
		long statusID = -1;
		long moduleID = -1;
		long ID = -1;
		String saccountno = "";
		String clientName = "";
		String depositNo = "";
		double mAmount = 0.0;
		double amount = 0.0;
		long userID = -1;
		String notifyDate1 = "";
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					statusID = rs.getLong("statusID");
					moduleID = rs.getLong("moduleID");
					ID = rs.getLong("ID");
					saccountno = rs.getString("saccountno");
					clientName = rs.getString("clientName");
					depositNo = rs.getString("DepositNo");
					mAmount = rs.getDouble("mAmount");
					amount = rs.getDouble("amount");
					userID = rs.getLong("userID");
					notifyDate1 = rs.getString("notifyDate1");
					
					//存储列数据
					cellList = new ArrayList();
					
					if(statusID == SETTConstant.NotifyInformStatus.SAVE && moduleID==Constant.ModuleType.SETTLEMENT){
						PagerTools.returnCellList(cellList,ID+"");
					}else{
						PagerTools.returnCellList(cellList,"");
					}
					
					if(statusID == SETTConstant.NotifyInformStatus.SAVE){
						PagerTools.returnCellList(cellList,saccountno+","+ID);
					}else{
						PagerTools.returnCellList(cellList,saccountno+", ");
					}
					
					PagerTools.returnCellList(cellList,clientName);
					
					PagerTools.returnCellList(cellList,depositNo);
					
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mAmount));
					
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(amount));
					
					if (moduleID==Constant.ModuleType.EBANK)
				 	{
						PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getUserNameByID(userID));
				 	}else if (moduleID==Constant.ModuleType.SETTLEMENT)
				 	{
				 		PagerTools.returnCellList(cellList,com.iss.itreasury.settlement.util.NameRef.getUserNameByID(userID));
				 	}
				 	
					if (moduleID==Constant.ModuleType.EBANK){
				 		PagerTools.returnCellList(cellList,"网银");
			 		}else if (moduleID==Constant.ModuleType.SETTLEMENT){
			 	   		PagerTools.returnCellList(cellList,"结算");
			 	   	}
			 	   	
					if(notifyDate1!=null){
			 	   		PagerTools.returnCellList(cellList,notifyDate1.toString().substring(0,10));
			 	   	}else{
			 	   		PagerTools.returnCellList(cellList,"");
			 	   	}
					
					PagerTools.returnCellList(cellList,SETTConstant.NotifyInformStatus.getName(statusID));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
	/**
	 * 网银通知存款支取通知查询
	 */
	public PagerInfo queryNotifyDeposit4ebank(NotifyDepositInformInfo qInfo) throws Exception{
		// TODO Auto-generated method stub
		NotifyDepositDao notifyDepositDao = new NotifyDepositDao();
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = notifyDepositDao.queryNotifyDepositSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(TransNotifyDepositBiz.class, "ebankResultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	
	public ArrayList ebankResultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		NotifyDepositInformInfo info = null;
		Vector v = null;
		
		long statusID = -1;
		long moduleID = -1;
		long ID = -1;
		String saccountno = "";
		String clientName = "";
		String depositNo = "";
		double mAmount = 0.0;
		double amount = 0.0;
		long userID = -1;
		String notifyDate1 = "";
		
		try{
			
			if(rs!=null)
			{
				while(rs.next())
				{
					statusID = rs.getLong("statusID");
					moduleID = rs.getLong("moduleID");
					ID = rs.getLong("ID");
					saccountno = rs.getString("saccountno");
					clientName = rs.getString("clientName");
					depositNo = rs.getString("DepositNo");
					mAmount = rs.getDouble("mAmount");
					amount = rs.getDouble("amount");
					userID = rs.getLong("userID");
					notifyDate1 = rs.getString("notifyDate1");
					
					//存储列数据
					cellList = new ArrayList();
					
					//if(statusID == SETTConstant.NotifyInformStatus.SAVE && moduleID==Constant.ModuleType.SETTLEMENT){
						PagerTools.returnCellList(cellList,ID+"");
					//}else{
					//	PagerTools.returnCellList(cellList,"");
					//}
					
					if(statusID == SETTConstant.NotifyInformStatus.SAVE){
						PagerTools.returnCellList(cellList,saccountno+","+ID);
					}else{
						PagerTools.returnCellList(cellList,saccountno+", ");
					}
					
					PagerTools.returnCellList(cellList,clientName);
					
					PagerTools.returnCellList(cellList,depositNo);
					
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mAmount));
					
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(amount));
					
					if (moduleID==Constant.ModuleType.EBANK)
				 	{
						PagerTools.returnCellList(cellList,com.iss.itreasury.ebank.util.NameRef.getUserNameByID(userID));
				 	}else if (moduleID==Constant.ModuleType.SETTLEMENT)
				 	{
				 		PagerTools.returnCellList(cellList,com.iss.itreasury.settlement.util.NameRef.getUserNameByID(userID));
				 	}
				 	
					if(notifyDate1!=null){
			 	   		PagerTools.returnCellList(cellList,notifyDate1.toString().substring(0,10));
			 	   	}else{
			 	   		PagerTools.returnCellList(cellList,"");
			 	   	}
					
					PagerTools.returnCellList(cellList,SETTConstant.NotifyInformStatus.getName(statusID));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
		
	}
	
}
