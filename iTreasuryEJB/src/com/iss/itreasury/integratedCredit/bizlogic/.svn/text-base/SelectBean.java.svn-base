package com.iss.itreasury.integratedCredit.bizlogic;

import java.util.Collection;

import com.iss.itreasury.integratedCredit.dao.SelectDao;
import com.iss.itreasury.integratedCredit.dataentity.LoanAccountreceivableinfo;
import com.iss.itreasury.integratedCredit.dataentity.LoanCreditgradeInfo;
import com.iss.itreasury.integratedCredit.dataentity.ClientInfo;

import com.iss.itreasury.integratedCredit.CreditUtil.Tools;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log4j;
public class SelectBean {
private SelectDao detailDao = null;

	
	private long m_luserID = -1;

	public SelectBean() {

		detailDao = new SelectDao();
	}
	private Log4j log = new Log4j(Constant.ModuleType.SETTLEMENT, this);
	
	public Collection findbycondition(LoanCreditgradeInfo info)
	throws SettlementException {
	
		
		Collection res = null;

		StringBuffer sql = null;
		try {
			log.debug("文件上传Bean文件");
			sql = new StringBuffer();
			sql.append("select * from sett_candebusinessapplications where status>0  ");
			log.debug("打印SQL语句"+sql.toString());
//			res = detailDao.findByPrint(info, sql.toString());
			log.debug("打印返回结果集"+res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return res;
		
	}
	
	
	
	
	/**
	 * 按页面的多个输入条件进行查询
	 * @param info
	 * @return
	 * @author songjiahan
	 * @throws SettlementException
	 */
	public Collection findUploadfiles(LoanCreditgradeInfo info)throws SettlementException
	{
		Collection res = null;

		StringBuffer sql = null;
		try {
			log.debug("文件上传Bean文件");
			sql = new StringBuffer();
			if (info != null) {
				sql.append("select * from Loan_Creditgrade  where status>0 ");
				if (info.getClientid() > 0) {
					sql.append("and CLIENTID=" + info.getClientid() + "");
				}
				if(info.getCreditlevel()!=null)
				{
					if (info.getCreditlevel() !=""&& info.getCreditcode()!= null) {
						sql.append("and CREDITLEVEL=" + info.getCreditlevel() + "");
					}
				}
				if (info.getCreditcode() !=null && info.getCreditcode()!="") {
					sql.append("and CREDITLEVEL=" + info.getCreditcode() + "");
				}
				if (info.getGradedate() != null && info.getCreditcode()!="") {
					
					sql.append(" and Dateofsubmission>=to_date(" + Tools.time(info.getGradedate())
							+ "),'yyyy-mm-dd') ");
				}		
				
				if(info.getCreditlevelvalue() >0)
				{
					sql.append("and CREDITLEVELVALUE=" + info.getCreditlevelvalue()+"");
							
				}
			}	
			 else {
				sql.append("select * from Loan_Creditgrade where status>0  ");
			}
			log.debug("打印sql --------------------"+sql);
//			res = detailDao.findByPrint(info, sql.toString());
			log.debug("调用findByPrint方法之后--------"+res);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return res;
		
	}
	/**
	 *  个根据用户ＩＤ　查询单位名称
	 * @param userid
	 * @return
	 * @throws SettlementException
	 */
	public ClientInfo quetyclient(long userid) throws SettlementException {
	ClientInfo collection = null;
		log.info("\n=======bean start1====");
		try {
			
//			collection=detailDao.quetyclient( userid);
			log.info("\n=======bean start2====");
		} catch (Exception e) {
			e.printStackTrace();
			throw new SettlementException();
		}
		return collection;
	}
	
	
	public LoanAccountreceivableinfo findId(LoanAccountreceivableinfo info)
	{
		SelectDao dao = new SelectDao();
		//ComplaintsAndSuggestionsInfo info = new ComplaintsAndSuggestionsInfo();
		
		if(info!=null)
		{
		//	info = dao.findId(info);
		}
		
		return info;
		
	}

	
}
