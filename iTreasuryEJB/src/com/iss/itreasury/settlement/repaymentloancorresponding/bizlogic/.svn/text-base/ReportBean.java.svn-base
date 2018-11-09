/*
 * Created on 2004-10-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.repaymentloancorresponding.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.settlement.base.SettlementDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.repaymentloancorresponding.dao.GrantLoanDao;
import com.iss.itreasury.settlement.repaymentloancorresponding.dao.ReportDao;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.LoanDetailInfo;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.ReportInfo;
import com.iss.itreasury.settlement.repaymentloancorresponding.dataentity.ReportQueryInfo;
import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;

/**  
 * @author fanyang  
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportBean
{
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	public ReportBean()
	{
		super();    
	}
	
	public Collection getReport(ReportQueryInfo queryInfo)throws SettlementException
	{
		Collection c1 = null;
		Collection c2 = null;
		Vector result = new Vector();
		ReportDao dao = new ReportDao();
		//先获得所有满足条件的银行收款
		c1 = dao.getReceiveResult(queryInfo);
		//再获得所有满足条件的银行付款
		c2 = dao.getPayResult(queryInfo);
		
		boolean payResult = false;
		Iterator iter2 = null;
		if(c2!=null && c2.size() > 0)		//判断付款有无记录
		{
			iter2 = c2.iterator();
			payResult = true;
		}
		ReportInfo tempInfo = null;//跟踪记录result2里没有和1对应的第一条
		
		//进行连结成一新的Collection
		if(c1!=null && c1.size() > 0)
		{
			Iterator iter1 = c1.iterator();
			while(iter1.hasNext())
			{
				ReportInfo info1 = new ReportInfo();
				info1 = (ReportInfo)iter1.next();
				result.add(info1);//先将收款记录加入结果记录
				
				if(tempInfo!=null)//如果上次比较里有不相等的,则这里对这条进行比较
				{
					if(tempInfo.getLoanDetailId() == info1.getLoanDetailId())
					{
						result.add(tempInfo);
						tempInfo = null;//用完就释放
					}
				}
				if(payResult&&tempInfo==null)//iter2有数据并且tempInfo都释放完
				{
					while(iter2.hasNext())
					{
						ReportInfo info2 = new ReportInfo();
						info2 = (ReportInfo)iter2.next();
						if(info1.getLoanDetailId() == info2.getLoanDetailId())
						{
							result.add(info2);
						}
						else
						{
							tempInfo = new ReportInfo();
							tempInfo = info2;
							break;//暂时跳出循环
						}
					}
				}
			}
		}
		return result;
	}
    
    public static void main(String args[])throws Exception
    {
    	Collection c = null;
    	ReportQueryInfo queryInfo = new ReportQueryInfo();
    	ReportBean bean = new ReportBean();
    	c = bean.getReport(queryInfo);
    	ReportInfo info = new ReportInfo();
    	if(c!=null&&c.size()>0)
    	{
    		Iterator iter = c.iterator();
    		while(iter.hasNext())
    		{
    			info = (ReportInfo)iter.next();
    			System.out.println("typename="+info.getTypeName());
    		}
    	}
    	/*bean.deleteLoanDetail(transInfo);
    	System.out.println("over");*/
    }
	
}
