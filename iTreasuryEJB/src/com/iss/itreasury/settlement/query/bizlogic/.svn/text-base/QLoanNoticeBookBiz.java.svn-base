package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.settlement.query.Dao.QLoanNoticeBookDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryLoanNoticeInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;


/**
 * 贷款通知书查询数据层
 * @author songwenxiao
 *
 */
public class QLoanNoticeBookBiz {
	
	QLoanNoticeBookDao dao = new QLoanNoticeBookDao();

	public PagerInfo queryCounterTrans( QueryLoanNoticeInfo qInfo) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.getLoanNoticeBookSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QLoanNoticeBookBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
}
	
	public ArrayList resultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		long ContractID = -1;                          //借款合同ID
		String FormNo = "";                            //通知单号
		long ID = -1;                                  //主键       
		String ContractNo = "";                        //借款合同号
		String PayFormNo = "";                         //放款通知单号
		String FormYear = "";                          //通知书年度
		long FormNum = -1;                           //催收次数
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					ContractID = rs.getLong("ContractID");
					FormNo=rs.getString("FormNo");
					ID=rs.getLong("ID");
					ContractNo=rs.getString("ContractNo");
					PayFormNo=rs.getString("PayFormNo");
					FormYear=rs.getString("FormYear");
					FormNum=rs.getLong("FormNum");
					
					//处理数据
					
					String id1 = DataFormat.formatNumber(FormNum);
					String strFormNum="";
					if(FormNum>0)
					{
						strFormNum = DataFormat.formatNumber(FormNum);			
					}
					
					//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,ID); 
					PagerTools.returnCellList(cellList,ContractNo); 
					PagerTools.returnCellList(cellList,PayFormNo);
					PagerTools.returnCellList(cellList,FormYear);
					PagerTools.returnCellList(cellList,FormNo);
					PagerTools.returnCellList(cellList,strFormNum);
					
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
