package com.iss.itreasury.settlement.query.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.iss.itreasury.settlement.query.Dao.QueryGLDao;
import com.iss.itreasury.settlement.query.resultinfo.AccountTransactionTypeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;


/**
 * �ս��Ŀ���ܲ�ѯ-��Ŀ����
 * @author songwenxiao
 *
 */
public class QDailySubjectBiz {
	
	QueryGLDao dao = new QueryGLDao();

	public PagerInfo queryCounterTrans( long lAccount, long lOfficeID, long lCurrencyID, Timestamp tsDateStart, Timestamp tsDateEnd) throws Exception{
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = dao.findGLTransType(lAccount,  lOfficeID,  lCurrencyID,  tsDateStart,  tsDateEnd);
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(QDailySubjectBiz.class, "resultSetHandle");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
}
	
	public ArrayList resultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					AccountTransactionTypeInfo accountTransactionTypeInfo = new AccountTransactionTypeInfo();
					accountTransactionTypeInfo.m_dDebit = rs.getDouble("mDebit"); //�跽�ϼ�
					accountTransactionTypeInfo.m_dLoan = rs.getDouble("mLoan"); //�����ϼ�
					accountTransactionTypeInfo.m_lDebitNumber = rs.getLong("nDebitCount");
					accountTransactionTypeInfo.m_lCreditNumber = rs.getLong("nLoanCount");
					accountTransactionTypeInfo.m_lNumber = accountTransactionTypeInfo.m_lDebitNumber + accountTransactionTypeInfo.m_lCreditNumber; //ƾ֤����
					accountTransactionTypeInfo.m_lTransactionTypeID = rs.getLong("nTransactionTypeID");
					accountTransactionTypeInfo.m_strTransactionType = SETTConstant.TransactionType.getName(accountTransactionTypeInfo.m_lTransactionTypeID);
					
					//��������
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,(accountTransactionTypeInfo.m_strTransactionType==null?"":accountTransactionTypeInfo.m_strTransactionType)); 
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(accountTransactionTypeInfo.m_dDebit,1)+"#"+accountTransactionTypeInfo.m_lTransactionTypeID); 
					PagerTools.returnCellList(cellList,DataFormat.formatDisabledAmount(accountTransactionTypeInfo.m_dLoan,1)+"#"+accountTransactionTypeInfo.m_lTransactionTypeID); 
					PagerTools.returnCellList(cellList,(accountTransactionTypeInfo.m_lDebitNumber<=0?"":String.valueOf(accountTransactionTypeInfo.m_lDebitNumber)));
					PagerTools.returnCellList(cellList,(accountTransactionTypeInfo.m_lCreditNumber<=0?"":String.valueOf(accountTransactionTypeInfo.m_lCreditNumber)));
					
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
