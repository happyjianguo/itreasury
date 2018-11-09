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
 * �������ʷ��ϸ��ѯ
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
			//�õ���ѯSQL
			sql = dao.getLoanTransHistoryDetailSQL(info);
			pagerInfo.setSqlString(sql);
			
			Map map=new HashedMap();
			
			map.put("key1", info);
			
			pagerInfo.setExtensionMothod(QLoanTransHistoryDetailBiz.class, "resultSetHandle",map);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
}
	
	public ArrayList resultSetHandle(ResultSet rs,Map map) throws IException{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		QueryTransactionConditionInfo info =(QueryTransactionConditionInfo) map.get("key1");
		
		long ID = -1; ////��¼ID
		String TransNo = ""; ////���׺�
		long TransactionTypeID = -1; ////��������
		String strPayAccountNoStart="";//�����˻���
		long ContractID = -1; ///��ͬ��ID
		long LoanFormID = -1; ///֪ͨ����ID
		double PayAmount = 0.0; ////���׸�����
		Timestamp InterestStart = null; ////������Ϣ��
		String Abstract = ""; ////����ժҪ
		long StatusID = -1; ////����״̬
		long InputUserID = -1; ////����¼����
		long CheckUserID = -1; ////���׸�����
		String ReceiveAccountNo = ""; ////�տ�˻�����
		String PayAccountNo = ""; ////����˻�����
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//��ȡ����
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
					
					
					//��������
					
					//�ʻ���ID
					long lTmpAccountGroupID = -1;
					//�ʻ�ID
					long lTmpAccountID = -1;
					
					
					String strDetailPageURL = "/settlement/query/control/querycontrol.jsp?lID="+ID+"&TransactionTypeID="+TransactionTypeID+"&TransNo="+TransNo+"&strFailPageURL=../query/view/v022.jsp";
					
					String strLoanAccountNo = "";//�����˻���
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
					String strCurrenctAccountNo = "";//�����˻���
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
					
					//��һ�� ���׺�
					String StrtransNo=((TransNo == null || TransNo.equals("")) ? "&nbsp;" : TransNo);
					
					//�ڶ��� ҵ������
					String transActionType=SETTConstant.TransactionType.getName(TransactionTypeID);
					
					//������ �����˻���
					strPayAccountNoStart=info.getPayAccountNoStart();
					
					//������ �����˻���
					
					
					//������ ��ͬ��
					String contractNo=(ContractID<=0 ? "&nbsp;" : NameRef.getContractNoByID(ContractID));
					
					//������ ����ſ�֪ͨ��
					String loanFormNo=(LoanFormID<=0 ? "&nbsp;" : NameRef.getPayFormNoByID(LoanFormID));
					
					//������ ���
					String amount="��"+DataFormat.formatDisabledAmount(PayAmount);
					
					//�ڰ��� ��Ϣ��
					String interestdate=(InterestStart == null ? "&nbsp;" : DataFormat.getDateString(InterestStart));
					
					//�ھ��� ժҪ
					String abstractdetail=Abstract == null ? "&nbsp;" : Abstract;
					
					//��ʮ�� ״̬
					String statusStr=SETTConstant.TransactionStatus.getName(StatusID);
					
					//��ʮһ�� ¼����
					String user=(InputUserID<=0 ? "&nbsp;" : NameRef.getUserNameByID(InputUserID));
					
					//��ʮ���� ������
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
					
					 
					 
					
					//�洢������
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
