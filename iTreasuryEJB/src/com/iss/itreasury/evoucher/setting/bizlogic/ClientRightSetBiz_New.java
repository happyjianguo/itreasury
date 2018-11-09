package com.iss.itreasury.evoucher.setting.bizlogic;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.evoucher.setting.dao.ClientRightSetDao_New;
import com.iss.itreasury.evoucher.setting.dataentity.ClientRightEntity;
import com.iss.itreasury.evoucher.sigprintquery.bizlogic.SignaturePrintQueryBiz;
import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht;
import com.iss.itreasury.evoucher.util.VOUConstant.EleDocsSet;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class ClientRightSetBiz_New {
	
	ClientRightSetDao_New clientRightSetdao= new ClientRightSetDao_New();

	/**
	 * 客户授权设置 biz层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	
	public PagerInfo queryClientRightSetQuery(ClientRightEntity ce) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = clientRightSetdao.queryClientRightSetQuerySQL(ce);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(ClientRightSetBiz_New.class, "resultSetHandle");
		
		}	catch(Exception e)
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
		ClientRightEntity ce = new ClientRightEntity();
		
		long id = -1;
		long officeid = -1;
		String ClientCode = null;//客户编号
		String ClientName = null;//客户名称
		String Nofficename = null;//办事处
		String nissignature = null;//授权状态
		long flag = -1;
		
		try {
			if(rs!=null)
			{
			while(rs.next()){
				id = rs.getLong("nclientid");
				officeid = rs.getLong("nofficeid");
				ClientCode = rs.getString("clientCode");
				ClientName = rs.getString("clientName");
				Nofficename = rs.getString("nofficename");
				flag = rs.getLong("nissignature");
				
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,id+"__"+officeid);
					PagerTools.returnCellList(cellList,ClientCode+","+id);
					PagerTools.returnCellList(cellList,ClientName);
					PagerTools.returnCellList(cellList,Nofficename);
				    
				  if (flag == EleDocsSet.HASSETRIGHT) {
					  nissignature = "已授权";
					   PagerTools.returnCellList(cellList,nissignature);
					}else{
						
						nissignature = "未授权";
					      PagerTools.returnCellList(cellList,nissignature);

					}
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
