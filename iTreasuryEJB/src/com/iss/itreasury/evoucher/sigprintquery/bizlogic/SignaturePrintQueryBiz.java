package com.iss.itreasury.evoucher.sigprintquery.bizlogic;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientmanageBiz;
import com.iss.itreasury.clientmanage.util.CMConstant;
import com.iss.itreasury.evoucher.sigprintquery.dao.SignaturePrintQueryDao;
import com.iss.itreasury.evoucher.sigprintquery.dataentity.SigPrintEntity;
import com.iss.itreasury.evoucher.util.VOUConstant;
import com.iss.itreasury.evoucher.util.VOUConstant.EBankDocRiht;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class SignaturePrintQueryBiz {

	SignaturePrintQueryDao sigPrintDao = new SignaturePrintQueryDao();
	/**
	 * 电子签章打印查询 biz层
	 * add by liaiyi 2012-04-02
	 * @return
	 * @throws Exception
	 */
	public PagerInfo querySignaturePrintQuery(SigPrintEntity spe) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = sigPrintDao.querySignaturePrintQuerySQL(spe);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(SignaturePrintQueryBiz.class, "resultSetHandle");
//			ArrayList depictList = new ArrayList();
//			PagerDepictBaseInfo baseInfo = null;
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("clientcode");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("nbilltypeid");
//			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
//			baseInfo.setExtensionMothod(VOUConstant.EBankDocRiht.class, "getTypeDesc", new Class[]{long.class});
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("stransno");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("printusername");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("dtprintdate");
//			baseInfo.setDisplayType(PagerTypeConstant.DATETIME);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("NPRINTCOUNT");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			pagerInfo.setDepictList(depictList);
		
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
		
		String clientcode = null;
		long nbilltypeid = -1;;
		String stransno = null;
		String printusername = null;
		Timestamp dtprintdate = null;
		long nPrintCount = -1;
		
		
		try {
			if(rs!=null)
			{
			while(rs.next()){
				clientcode = rs.getString("clientcode");
				nbilltypeid = rs.getLong("nbilltypeid");
				stransno = rs.getString("stransno");
				printusername = rs.getString("printusername");
				dtprintdate = rs.getTimestamp("dtprintdate");
				nPrintCount = rs.getLong("nPrintCount");
				
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,clientcode);
					PagerTools.returnCellList(cellList,EBankDocRiht.getTypeDesc(nbilltypeid));
					PagerTools.returnCellList(cellList,stransno);
					PagerTools.returnCellList(cellList,printusername);
					PagerTools.returnCellList(cellList,DataFormat.getDateTimeString(dtprintdate) );
				  if(nPrintCount ==0){
					PagerTools.returnCellList(cellList,nPrintCount+1);
				  }else{
					PagerTools.returnCellList(cellList,nPrintCount);
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
