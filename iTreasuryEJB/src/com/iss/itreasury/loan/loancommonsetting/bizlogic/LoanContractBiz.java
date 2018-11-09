package com.iss.itreasury.loan.loancommonsetting.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.loancommonsetting.dao.LoanSettingDao;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class LoanContractBiz {

LoanSettingDao loanSettingDao = new LoanSettingDao();
	
/**
 * ��ͬ������Ȩ��ת��biz��
 * add by liaiyi 2012-12-17
 * @return
 * @throws Exception
 */
	public PagerInfo queryContract(ContractInfo ci) throws Exception
	// TODO Auto-generated method stub
	{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			//�õ���ѯSQL
			sql = loanSettingDao.queryContractSQL(ci);
			pagerInfo.setSqlString(sql);

			pagerInfo.setExtensionMothod(LoanContractBiz.class, "resultSetHandle");
			
			pagerInfo.setUsertext("���ϼ�{LoanAmount}");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>��ѯ�쳣", e);
		}
		return pagerInfo;
	}
	
	public ArrayList resultSetHandle(ResultSet rs) throws Exception{
		
		ArrayList resultList = new ArrayList(); //���շ��ؽ��
		ArrayList cellList = null;//��
		ResultPagerRowInfo rowInfo = null;//��
		
		RateInfo ri = new RateInfo();
        ContractDao dao = new ContractDao();
        ContractInfo ci = null;
        
		long ContractID = -1;
		long LoanTypeID = -1;
		long ConsignClientID = -1;
		long Rate = -1;
		long StatusID = -1;//��ͬ��״̬
		long Status = -1;//״̬����
		long IntervalNum = -1;//����
		String ContractCode = "";//��ͬ����
		String LoanTypeName = "";//��������
		String BorrowClientName = "";//��λ
		String ClientName = "";//ί�е�λ
		String InputUserName = "";//��ͬ�������
		String ConsignClientName = "";
		double mLoanAmount = 0.00;//���
		double InterestRate = 0.00;//ִ������
		double mdiscountrate  = 0.00;
		
		try {
			if(rs!=null)
			{
				while(rs.next())
				{
					//��ȡ����
					ContractID = rs.getLong("ContractID");
					ContractCode = rs.getString("ContractCode");//��ͬ����
					LoanTypeID = rs.getLong("LoanTypeID");//��������
					BorrowClientName = rs.getString("BorrowClientName");//��λ
					ClientName = rs.getString("ConsignClientName");//ί�е�λ
					mLoanAmount = rs.getDouble("LoanAmount");//���
					InterestRate =rs.getDouble("mdiscountrate");//ִ������
					IntervalNum = rs.getLong("RateName");//��������
					StatusID = rs.getLong("NSTATUSID");//��ͬ��״̬
					InputUserName = rs.getString("InputUserName");//��ͬ�������
					
					//�洢������
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,ContractID); 
					PagerTools.returnCellList(cellList,ContractCode); //��ͬ����
					PagerTools.returnCellList(cellList,LOANConstant.LoanType.getName(LoanTypeID)); //��������
					PagerTools.returnCellList(cellList,BorrowClientName); //��λ
					PagerTools.returnCellList(cellList,ClientName); //ί�е�λ
					PagerTools.returnCellList(cellList,DataFormat.formatListAmount(mLoanAmount)); //���
					PagerTools.returnCellList(cellList,InterestRate+"%"); //ִ������
					PagerTools.returnCellList(cellList,IntervalNum); //��������
					PagerTools.returnCellList(cellList,LOANConstant.ContractStatus.getName(StatusID)); //��ͬ��״̬
					PagerTools.returnCellList(cellList,InputUserName); //��ͬ�������
					
					mdiscountrate  = 0.00;
					if(LoanTypeID==LOANConstant.LoanType.TX)
                    {
						mdiscountrate = rs.getDouble("mdiscountrate");
                    }
                    else
                    {
                        ri = new RateInfo();
                        ri=dao.getLatelyRate(ContractID,-1,null);
                        mdiscountrate = ri.getLateRate();
                    }
					PagerTools.returnCellList(cellList,mdiscountrate);
					
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
