/*
 * Created on 2003-10-28
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.report.bizlogic;
import java.util.*;

import com.iss.system.dao.PageLoader;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.loan.report.dataentity.*;
import com.iss.itreasury.loan.report.dao.*;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.ContractQueryInfo;

/**
 * @author hyzeng
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportOperation
{
	public Collection getWTLoanDetail(ReportCondition con) throws Exception
	{
		Collection c = null;
		ReportDao dao = new ReportDao();

		try
		{
			c = dao.getWTLoanDetail(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public Collection getLoanDetail(ReportCondition con) throws Exception
	{
		Collection c = null;
		ReportDao dao = new ReportDao();

		try
		{
			c = dao.getLoanDetail(con);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return c;
	}

	public Collection getGJTZ(ReportCondition con) throws Exception
	{
		Collection c=null;
		ReportDao dao=new ReportDao();
		
		try
		{
			c=dao.getGJTZ( con );
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			throw e;
		}
		return c;
	}

	public Collection getCreditReport(CreditReportCondition con) throws Exception
	{
		Collection c=null;
		ReportDao dao=new ReportDao();
		
		try
		{
			c=dao.getCreditDetail( con );
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			throw e;
		}
		return c;
	}

	/**
	* �õ�����̨��
	* Create Date: 2003-10-15
	* @param lContractID ��ͬID
	* @return ReportContractInfo 
	* @exception Exception
	*/
	public ReportContractInfo getLoanTZ(long lContractID) throws Exception
	{

		ReportContractInfo info = new ReportContractInfo();
		Collection c = null;
		if (lContractID > 0)
		{
			ReportDao dao = new ReportDao();
			ContractDao conDao = new ContractDao();

			ContractInfo conInfo = new ContractInfo();
			ReportContractDetailInfo RCDInfo = new ReportContractDetailInfo();
			String sTemp = "";
			try
			{
				conInfo = conDao.findByID(lContractID);
				RCDInfo.setLoanType(LOANConstant.LoanType.getName(conInfo.getLoanTypeID())); //��������
				RCDInfo.setContractCode(conInfo.getContractCode()); //��ͬ��
				RCDInfo.setClientName(conInfo.getBorrowClientName()); //��λ
				RCDInfo.setContractRate(conInfo.getFormatInterestRate()); //��ͬ����
				RCDInfo.setLoanStart(conInfo.getFormatLoanStart()); //���ʼ����
				RCDInfo.setLoanEnd(conInfo.getFormatLoanEnd()); //�����������
				RCDInfo.setCreditAmount(DataFormat.formatDisabledAmount(conInfo.getCreditAmount()/10000)); //���Ŷ��
				RCDInfo.setCheckAmount( DataFormat.formatDisabledAmount(conInfo.getExamineAmount()/10000));	//��׼���

				if (conInfo.getIsCredit() == 1)
				{
					sTemp += "���á�";
				}
				if (conInfo.getIsAssure() == 1)
				{
					sTemp += "��֤��";
				}
				if (conInfo.getIsPledge() == 1)
				{
					sTemp += "��Ѻ��";
				}
				if (conInfo.getIsImpawn() == 1)
				{
					sTemp += "��Ѻ��";
				}
				
				if (!sTemp.equals(""))
				{
					sTemp = sTemp.substring(0,sTemp.length()-1);
				}

				RCDInfo.setAssureType(sTemp); //������ʽ
				RCDInfo.setLoanStatus(conInfo.getStatus());//����״̬
				
				info.setRCDInfo(RCDInfo);//��ͬ������Ϣ
				
				ReportInterestInfo RIInfo = new ReportInterestInfo();
				RIInfo = dao.getInterestInfo(lContractID);
				info.setRIInfo(RIInfo);//��Ϣ���
				
				c = dao.getCorpusList(lContractID);
				info.setCorpusList(c);//����𷢷ţ��ջ����
				
				c = dao.getRePayList(lContractID);//��Ϣ�������ջ����
				info.setRepayList(c);
				
				c = dao.getRateHistory(lContractID);//���ʵ������
				info.setRateList(c);
				
				c = dao.getExtendList(lContractID);//չ�����
				info.setExtendList(c);
				
				c = dao.getFreeList(lContractID);//�⻹���
				info.setFreeList(c);			
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		return info;
	}
	
	public Collection getHaierLoanTZ(ContractQueryInfo qInfo)
	{
	    Vector v = new Vector();
	    Vector vTemp = new Vector();
	    ReportDao dao = new ReportDao();
	    try
        {
            vTemp = (Vector) dao.getLoanInfoforHaierTZ(qInfo);
        } catch (IException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    if(vTemp!=null && vTemp.size()>0)
	    {
	        long lId = -1;
	        Iterator it = vTemp.iterator();
	        while(it.hasNext())
	        {
	           lId = ((ContractInfo)it.next()).getContractID();
	           ReportContractInfo info = new ReportContractInfo();
	           try
            {
                info = this.getLoanTZ(lId);
            } catch (Exception e1)
            {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            	if(info.getRCDInfo() !=null)
            	{
            	    info.getRCDInfo().setContractId(lId);
            	    v.add(info);
            	}
	           
	        }
	    }
	    
	    
	    return (v.size() > 0 ? v : null);
	}

	/**
	 * ͨ��ҳ��������ѯ�����[����1]
	 * @author zqhu
	 * @serialData 2005-10-19
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return PageLoader
	 * @exception Exception
	 */
	public PageLoader findForReport1(ReportWhereInfo reportWhereInfo) throws Exception {
		return new Report1Dao().findForReport1(reportWhereInfo);
	}

	/**
	 * ͨ��ҳ��������ѯ�����[����2]
	 * @author zqhu
	 * @serialData 2005-10-19
	 * @param  ReportWhereInfo reportWhereInfo
	 * @return PageLoader
	 * @exception Exception
	 */
	public PageLoader findForReport2(ReportWhereInfo reportWhereInfo) throws Exception {
		return new Report2Dao().findForReport2(reportWhereInfo);
	}

	public static void main(String[] argv)
	{
	    ReportOperation test = new ReportOperation();
	    ReportContractInfo tt =new ReportContractInfo();
	    try
        {
            tt = test.getLoanTZ(87);
        } catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		Vector v = new Vector();
		ContractQueryInfo  qInfo = new ContractQueryInfo();
		qInfo.setOfficeID(1);
		qInfo.setCurrencyID(1);
		
		try
		{
		    v = (Vector) test.getHaierLoanTZ(qInfo);
		    if(v!=null)
		    {
		        System.out.println("===========+++++++++++++++++++++\t"+v.size());
		    }
		    //System.out.println("===========+++++++++++++++++++++\t"+v.size());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}