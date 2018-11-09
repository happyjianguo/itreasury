package com.iss.itreasury.system.setting.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.setting.dao.FinancialInterfaceDao;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class FinancialInterfaceBiz {

	FinancialInterfaceDao financialDao = new FinancialInterfaceDao();
	
	/**
	 * 财务接口设置查询类
	 * add by liaiyi 2013-01-28
	 */
	public PagerInfo queryFinancialInterfaceInfo(GlSettingInfo glSettingInfo) throws Exception{
		   
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			sql = financialDao.queryFinancialInterfaceSQL(glSettingInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(FinancialInterfaceBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
		public ArrayList resultSetHandle(ResultSet rs) throws IException {
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		GlSettingInfo info = new GlSettingInfo();
		
		long id = -1;                  //编号
		long officeID = -1;
		long currencyID = -1;
		long officeName = -1;          //办事处
		long currencyName = -1;      //币种
		String glDBIP = null;          //数据库IP
		String glDBUserName = null;    //用户名
		String glDBPassWord = null;    //用户密码
		String glDBPort = null;        //库端口
		String glDBDatabaseName = null;//库名称
		String glResponseHTTP = null;  //EAI地址
		String glName = null;          //总账类型
		String glSender = null;        //用友远程登入注册号
		String glVoucherType = null;   //用友记账类型
		String glUserName = null;      //总账用户
		String glPassWord = null;      //总账用户密码
		long nImportType = -1;     //总账导入方式
		String branChcode = null;
		
		try {
			if(rs!=null)
			{
			while(rs.next()){
				id = rs.getLong("id");
				officeID = rs.getLong("officeid");
				officeName = rs.getLong("officeid");
				currencyID = rs.getLong("currencyid");
			    currencyName = rs.getLong("currencyid");
				glDBIP = rs.getString("glDBIP");//数据库ip
				glDBUserName = rs.getString("glDBUserName");//用户
				glDBPassWord = rs.getString("glDBPassWord");//用户密码
				glDBPort = rs.getString("glDBPort");//库端口
				glDBDatabaseName = rs.getString("glDBDatabaseName");//库名称
				glResponseHTTP = rs.getString("glResponseHTTP");//EAI地址
				
				glName =rs.getString("glName");//总账类型
				glSender = rs.getString("glSender");//远程登入注册号
				glVoucherType = rs.getString("glVoucherType");//记账类型
				glUserName = rs.getString("glUserName");//总账用户
				glPassWord = rs.getString("glPassWord");//总账用户密码
				
				//nImportType= DataFormat.formatNumber(info.getNImportType());//凭证汇总类型
				nImportType= rs.getLong("nImportType");//凭证汇总类型
				
				branChcode = info.getBranChcode();//账套编号
				
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,id+","+id);
					PagerTools.returnCellList(cellList,NameRef.getOfficeNameByID(officeName));
					PagerTools.returnCellList(cellList,Constant.CurrencyType.getName(currencyName));
					PagerTools.returnCellList(cellList,glDBIP);
					PagerTools.returnCellList(cellList,glDBUserName);
					PagerTools.returnCellList(cellList,glDBPassWord);
					PagerTools.returnCellList(cellList,glDBPort);
					PagerTools.returnCellList(cellList,glDBDatabaseName);
					PagerTools.returnCellList(cellList,glResponseHTTP);
					PagerTools.returnCellList(cellList,glName);
					PagerTools.returnCellList(cellList,glSender);
					PagerTools.returnCellList(cellList,glVoucherType);
					PagerTools.returnCellList(cellList,glVoucherType);
					PagerTools.returnCellList(cellList,glUserName);
					PagerTools.returnCellList(cellList,glPassWord);
					PagerTools.returnCellList(cellList,Constant.GLImportType.getName(nImportType));
					
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//返回数据
					resultList.add(rowInfo);
				
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
}
}
