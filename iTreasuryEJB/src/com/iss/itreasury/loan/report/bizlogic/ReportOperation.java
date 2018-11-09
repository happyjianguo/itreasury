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
	* 得到贷款台账
	* Create Date: 2003-10-15
	* @param lContractID 合同ID
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
				RCDInfo.setLoanType(LOANConstant.LoanType.getName(conInfo.getLoanTypeID())); //贷款种类
				RCDInfo.setContractCode(conInfo.getContractCode()); //合同号
				RCDInfo.setClientName(conInfo.getBorrowClientName()); //借款单位
				RCDInfo.setContractRate(conInfo.getFormatInterestRate()); //合同利率
				RCDInfo.setLoanStart(conInfo.getFormatLoanStart()); //贷款开始日期
				RCDInfo.setLoanEnd(conInfo.getFormatLoanEnd()); //贷款结束日期
				RCDInfo.setCreditAmount(DataFormat.formatDisabledAmount(conInfo.getCreditAmount()/10000)); //授信额度
				RCDInfo.setCheckAmount( DataFormat.formatDisabledAmount(conInfo.getExamineAmount()/10000));	//批准金额

				if (conInfo.getIsCredit() == 1)
				{
					sTemp += "信用、";
				}
				if (conInfo.getIsAssure() == 1)
				{
					sTemp += "保证、";
				}
				if (conInfo.getIsPledge() == 1)
				{
					sTemp += "抵押、";
				}
				if (conInfo.getIsImpawn() == 1)
				{
					sTemp += "质押、";
				}
				
				if (!sTemp.equals(""))
				{
					sTemp = sTemp.substring(0,sTemp.length()-1);
				}

				RCDInfo.setAssureType(sTemp); //担保方式
				RCDInfo.setLoanStatus(conInfo.getStatus());//贷款状态
				
				info.setRCDInfo(RCDInfo);//合同基本信息
				
				ReportInterestInfo RIInfo = new ReportInterestInfo();
				RIInfo = dao.getInterestInfo(lContractID);
				info.setRIInfo(RIInfo);//利息情况
				
				c = dao.getCorpusList(lContractID);
				info.setCorpusList(c);//贷款本金发放，收回情况
				
				c = dao.getRePayList(lContractID);//利息‘费用收回情况
				info.setRepayList(c);
				
				c = dao.getRateHistory(lContractID);//利率调整情况
				info.setRateList(c);
				
				c = dao.getExtendList(lContractID);//展期情况
				info.setExtendList(c);
				
				c = dao.getFreeList(lContractID);//免还情况
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
	 * 通过页面条件查询结果集[报表1]
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
	 * 通过页面条件查询结果集[报表2]
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