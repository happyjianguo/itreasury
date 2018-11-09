package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.iss.itreasury.settlement.bizdelegation.AccountDailyTransGatherDelegation;
import com.iss.itreasury.settlement.query.Dao.QTransAccountDao;
import com.iss.itreasury.settlement.query.paraminfo.AccountDailyTransGatherConditionInfo;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransAccountDetailWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

/**
 * �˻���ѯҵ���
 * @author xiang
 *
 */
public class QTransAccountBiz {
	
	QTransAccountDao dao = new QTransAccountDao();

	public PagerInfo queryAccount(QueryTransAccountDetailWhereInfo qInfo) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = dao.queryAccountSQL(qInfo);
			pagerInfo.setSqlString(sql);
			
//			ArrayList depictList = new ArrayList();
//			PagerDepictBaseInfo baseInfo = null;
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("AccountID");
//			baseInfo.setDisplayType(PagerTypeConstant.LONG);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("AccountNo");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("ClientCode");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			baseInfo = new PagerDepictBaseInfo();
//			baseInfo.setDisplayName("ClientName");
//			baseInfo.setDisplayType(PagerTypeConstant.STRING);
//			depictList.add(baseInfo);
//			
//			pagerInfo.setDepictList(depictList);
			
			pagerInfo.setExtensionMothod(QTransAccountBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList resultSetHandle(ResultSet rs) throws IException{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		QueryTransAccountDetailWhereInfo info = null;
		AccountDailyTransGatherConditionInfo cinfo = null;
		AccountDailyTransGatherDelegation accdel = null;
		Vector v = null;
		boolean isDisabled = false;
		
		long AccountID = -1;
		String AccountNo = "";
		String ClientCode = "";
		String ClientName = "";
		String StartDate = "";
		String EndDate = "";
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//��ȡ����
					AccountID = rs.getLong("AccountID");
					AccountNo = rs.getString("AccountNo");
					ClientCode = rs.getString("ClientCode");
					ClientName = rs.getString("ClientName");
					StartDate = rs.getString("StartDate");
					EndDate = rs.getString("EndDate");
					
					//��������
					info = new QueryTransAccountDetailWhereInfo();
					info.setAccountID(AccountID);
					info.setAccountNo(AccountNo);
					info.setClientCode(ClientCode);
					info.setStartDate(DataFormat.getDateTime(StartDate));
					info.setEndDate(DataFormat.getDateTime(EndDate));
					
					v = new Vector();
					cinfo = new AccountDailyTransGatherConditionInfo();		
					cinfo.setAccountNo(Long.toString(info.getAccountID()));
					cinfo.setStartDate(info.getStartDate());
					cinfo.setEndDate(info.getEndDate());		
					accdel = new AccountDailyTransGatherDelegation();		
					v = accdel.findAccountDailyTransGatherInfos(cinfo);          	
			        isDisabled=v.size()==0?true:false;
			        
					//�洢������
					cellList = new ArrayList();
					if(isDisabled){
						PagerTools.returnCellList(cellList,"");
					}else{
						PagerTools.returnCellList(cellList,info.getAccountID()+"");
					}
					PagerTools.returnCellList(cellList,info.getAccountNo()); 
					PagerTools.returnCellList(cellList,info.getClientCode()); 
					PagerTools.returnCellList(cellList,ClientName);
					
					//�洢������
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					rowInfo.setId(String.valueOf(rs.getLong("rownum1")));
					
					//��������
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
