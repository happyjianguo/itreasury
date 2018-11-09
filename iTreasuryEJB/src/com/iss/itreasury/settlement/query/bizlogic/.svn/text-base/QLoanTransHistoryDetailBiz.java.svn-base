package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.iss.itreasury.settlement.query.Dao.QLoanTransHistoryDetailDao;
import com.iss.itreasury.settlement.query.paraminfo.QueryTransactionConditionInfo;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;


/**
 * 贷款交易历史明细查询
 * @author songwenxiao
 *
 */
public class QLoanTransHistoryDetailBiz {
	
	QLoanTransHistoryDetailDao dao = new QLoanTransHistoryDetailDao();

	public PagerInfo queryLoanTrans( QueryTransactionConditionInfo info) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//得到查询SQL
			sql = dao.getLoanTransHistoryDetailSQL(info);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			
			map.put("key1", info);
			
			pagerInfo.setExtensionMothod(QLoanTransHistoryDetailBiz.class, "resultSetHandle",map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
}
	
	public ArrayList resultSetHandle(ResultSet rs,Map map) throws IException{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		
		QueryTransactionConditionInfo info =(QueryTransactionConditionInfo) map.get("key1");
		
		long ID = -1; ////记录ID
		String TransNo = ""; ////交易号
		long TransactionTypeID = -1; ////交易类型
		String strPayAccountNoStart="";//贷款账户号
		long ContractID = -1; ///合同号ID
		long LoanFormID = -1; ///通知单号ID
		double PayAmount = 0.0; ////交易付款金额
		Timestamp InterestStart = null; ////交易起息日
		String Abstract = ""; ////交易摘要
		long StatusID = -1; ////交易状态
		long InputUserID = -1; ////交易录入人
		long CheckUserID = -1; ////交易复核人
		String ReceiveAccountNo = ""; ////收款方账户编码
		String PayAccountNo = ""; ////付款方账户编码
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//获取数据
					ID = rs.getLong("ID");
					TransNo=rs.getString("TransNo");
					TransactionTypeID=rs.getLong("TransactionTypeID");
					ContractID=rs.getLong("ContractID");
					LoanFormID=rs.getLong("LoanFormID");
					PayAmount=rs.getDouble("PayAmount");
					InterestStart=rs.getTimestamp("InterestStart");
					Abstract=rs.getString("Abstract");
					StatusID=rs.getLong("StatusID");
					InputUserID=rs.getLong("InputuserID");
					CheckUserID=rs.getLong("CheckuserID");
					ReceiveAccountNo=rs.getString("ReceiveAccountNo");
					PayAccountNo=rs.getString("PayAccountNo");
					
					
					//处理数据
					
					//帐户组ID
					long lTmpAccountGroupID = -1;
					//帐户ID
					long lTmpAccountID = -1;
					
					
					String strDetailPageURL = "/settlement/query/control/querycontrol.jsp?lID="+ID+"&TransactionTypeID="+TransactionTypeID+"&TransNo="+TransNo+"&strFailPageURL=../query/view/v022.jsp";
					
					String strLoanAccountNo = "";//贷款账户号
					if (ReceiveAccountNo != null && !ReceiveAccountNo.equals("")/* && resultInfo.getReceiveAccountNo().length() <= 10*/)
					{
						lTmpAccountGroupID = -1;
						lTmpAccountID = -1;
						
						lTmpAccountID = NameRef.getAccountIdByNoAndStatus(ReceiveAccountNo,-1);
						lTmpAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(NameRef.getAccountTypeByID(lTmpAccountID));
						if(lTmpAccountGroupID != -1 && lTmpAccountGroupID != SETTConstant.AccountGroupType.CURRENT){
							strLoanAccountNo = ReceiveAccountNo;
						}
					}
					else if (PayAccountNo != null && !PayAccountNo.equals("")/* && resultInfo.getPayAccountNo().length() <= 10*/)
					{
						lTmpAccountGroupID = -1;
						lTmpAccountID = -1;
						
						lTmpAccountID = NameRef.getAccountIdByNoAndStatus(PayAccountNo,-1);
						lTmpAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(NameRef.getAccountTypeByID(lTmpAccountID));
						if(lTmpAccountGroupID != -1 && lTmpAccountGroupID != SETTConstant.AccountGroupType.CURRENT){
							strLoanAccountNo = PayAccountNo;
						}
					}
					else
					{
						strLoanAccountNo = "&nbsp;";
					}
					String strCurrenctAccountNo = "";//活期账户号
					if (ReceiveAccountNo != null && !ReceiveAccountNo.equals("")/* && resultInfo.getReceiveAccountNo().length() > 10*/)
					{	
						lTmpAccountGroupID = -1;
						lTmpAccountID = -1;
						
						lTmpAccountID = NameRef.getAccountIdByNoAndStatus(ReceiveAccountNo,-1);
						lTmpAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(NameRef.getAccountTypeByID(lTmpAccountID));
						if(lTmpAccountGroupID == SETTConstant.AccountGroupType.CURRENT){
							strCurrenctAccountNo = ReceiveAccountNo;
						}
					}
					else if (PayAccountNo != null && !PayAccountNo.equals("")/* && resultInfo.getPayAccountNo().length() > 10*/)
					{
						lTmpAccountGroupID = -1;
						lTmpAccountID = -1;
						
						lTmpAccountID = NameRef.getAccountIdByNoAndStatus(PayAccountNo,-1);
						lTmpAccountGroupID = SETTConstant.AccountType.getAccountGroupTypeIDByAccountTypeID(NameRef.getAccountTypeByID(lTmpAccountID));
						if(lTmpAccountGroupID == SETTConstant.AccountGroupType.CURRENT){
							strCurrenctAccountNo = PayAccountNo;
						}
					}
					else
					{
						strCurrenctAccountNo = "&nbsp;";
					}	
					
					//第一列 交易号
					String StrtransNo=((TransNo == null || TransNo.equals("")) ? "&nbsp;" : TransNo);
					
					//第二列 业务类型
					String transActionType=SETTConstant.TransactionType.getName(TransactionTypeID);
					
					//第三列 贷款账户号
					strPayAccountNoStart=info.getPayAccountNoStart();
					
					//第四列 活期账户号
					
					
					//第五列 合同号
					String contractNo=(ContractID<=0 ? "&nbsp;" : NameRef.getContractNoByID(ContractID));
					
					//第六列 贷款放款通知单
					String loanFormNo=(LoanFormID<=0 ? "&nbsp;" : NameRef.getPayFormNoByID(LoanFormID));
					
					//第七列 金额
					String amount="￥"+DataFormat.formatDisabledAmount(PayAmount);
					
					//第八列 起息日
					String interestdate=(InterestStart == null ? "&nbsp;" : DataFormat.getDateString(InterestStart));
					
					//第九列 摘要
					String abstractdetail=Abstract == null ? "&nbsp;" : Abstract;
					
					//第十列 状态
					String statusStr=SETTConstant.TransactionStatus.getName(StatusID);
					
					//第十一列 录入人
					String user=(InputUserID<=0 ? "&nbsp;" : NameRef.getUserNameByID(InputUserID));
					
					//第十二列 复核人
					String checkUser="";
					 if( CheckUserID>0 )
				    	{
				    		if(StatusID>=SETTConstant.TransactionStatus.CHECK) 
				    			checkUser= NameRef.getUserNameByID(CheckUserID);
						 }
		       									
					else if(CheckUserID<0 && StatusID>=SETTConstant.TransactionStatus.CHECK)
					{
						if(StatusID!=SETTConstant.TransactionStatus.APPROVALING
							&&StatusID!=SETTConstant.TransactionStatus.APPROVALED
							&&StatusID!=SETTConstant.TransactionStatus.REFUSE)
							{
								//if(SETTConstant.TransactionType.isInterestTransaction(resultInfo.getTransactionTypeID()))
								//{
								checkUser=NameRef.getUserNameByID(Constant.MachineUser.CheckUser);
								//}
							}
					}
					
					 
					 
					
					//存储列数据
					cellList = new ArrayList();
					if (SETTConstant.TransactionType.isInterestTransaction(TransactionTypeID))
					{
		
						PagerTools.returnCellList(cellList,StrtransNo+","+""+","+""+","+"");
		
					}
					else
					{
					
						PagerTools.returnCellList(cellList,StrtransNo+","+ID+","+TransactionTypeID+","+TransNo); 
		
					}
					PagerTools.returnCellList(cellList,transActionType); 
					PagerTools.returnCellList(cellList,strPayAccountNoStart);
					PagerTools.returnCellList(cellList,strCurrenctAccountNo);
					PagerTools.returnCellList(cellList,contractNo);
					PagerTools.returnCellList(cellList,loanFormNo);
					PagerTools.returnCellList(cellList,amount);
					PagerTools.returnCellList(cellList,interestdate);
					PagerTools.returnCellList(cellList,abstractdetail);
					PagerTools.returnCellList(cellList,statusStr);
					PagerTools.returnCellList(cellList,user);
					PagerTools.returnCellList(cellList,checkUser);
					
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
