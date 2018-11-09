package com.iss.itreasury.managerquery.report.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.managerquery.report.dataentity.ReportCondition;
import com.iss.itreasury.managerquery.report.dataentity.ReportResultInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Env;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ReportDao extends ITreasuryDAO{

    private Log4j logger = null;
    public ReportDao() {
        super();
        logger = new Log4j(Constant.ModuleType.MANAGERQUERY, this);
    }
	public Collection queryRptShow(ReportCondition condition)throws IException
	{
		Collection result = null;
		
			result = new ArrayList();
			try {
				initDAO();
				//prepareStatement("select t.rptitemcode,t.rptitemname,t.rptdefault,t.memo from rpt_input_item t where t.rptnameid = "+ condition.getRptNameID() +" order by t.rptitemcode");
				prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,t.rptdefault,t.memo from rpt_input_item t,rpt_input_name s where t.rptnameid = s.rptnameid and t.rptnameid = "+ condition.getRptNameID() +" order by t.rptitemcode");
				executeQuery();

				while(transRS.next()){
					ReportResultInfo resultInfo = new ReportResultInfo();
					resultInfo.setRptname(transRS.getString("rptname"));
					resultInfo.setRptitemcode(transRS.getString("rptitemcode"));
					resultInfo.setRptitemname(transRS.getString("rptitemname"));
					resultInfo.setRptdefault(transRS.getDouble("rptdefault"));
					resultInfo.setMemo(transRS.getString("memo"));
					resultInfo.setRptitemid(transRS.getLong("rptitemid"));
					
					result.add(resultInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException("查询报表制表页面显示栏出现错误");
			}
			finally{
				finalizeDAO();
			}
		return result;
	}
	
	public void setDefault(Collection coll)throws IException
	{
	
		try {
			initDAO();
			
			this.transConn.setAutoCommit(false);
			
			Collection coll1 = (Collection)coll.toArray()[0];
			Double item[] = (Double[])coll.toArray()[1];
//			String selectDate = (String)coll.toArray()[2];
			long userID = ((Long)coll.toArray()[3]).longValue();
			String userName = (String)coll.toArray()[4];
			String userNo = (String)coll.toArray()[5];
			
			Timestamp inputDate = Env.getSystemDate();
			SimpleDateFormat  sdf  =  new  SimpleDateFormat("yyyy-MM-dd");
			java.util.Calendar  calendar  =  java.util.Calendar.getInstance();  
	        calendar.setTime(inputDate); 
	        String inputDateString = sdf.format(calendar.getTime());            
			
			if(coll1!=null && coll1.size()>=1)
		    {
		       for(int i=0;i<coll1.size();i++)
		       {
			prepareStatement("update rpt_input_item t set t.rptdefault = " + item[i].doubleValue() + 
					",t.inputdate = to_date('" + inputDateString + "','yyyy-mm-dd'),t.inputid = " + userID + 
					",t.inputsname = '" + userName + "',t.inputsloginno = '" + userNo 
					+ "' where t.rptitemcode = '" + ((ReportResultInfo)(coll1.toArray()[i])).getRptitemcode() + "'");
			
			executeUpdate();
		       }
		    }
			
			this.transConn.commit();
			
		} catch (Exception e) {
			try {
				this.transConn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new IException("制表设置默认值回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("制表设置默认值出现错误");
		}
		finally{
			finalizeDAO();
		}
    }
	
	public void tabRpt(Collection coll)throws IException
	{
	
		try {
			initDAO();
			this.transConn.setAutoCommit(false);
			
			Collection coll1 = (Collection)coll.toArray()[0];
			Double item[] = (Double[])coll.toArray()[1];
			String selectDate = (String)coll.toArray()[2];
			long userID = ((Long)coll.toArray()[3]).longValue();
			String userName = (String)coll.toArray()[4];
			String userNo = (String)coll.toArray()[5];
			long rptNameID = ((Long)coll.toArray()[6]).longValue();
			
			Timestamp inputDate = Env.getSystemDate();
			SimpleDateFormat  sdf  =  new  SimpleDateFormat("yyyy-MM-dd");
			java.util.Calendar  calendar  =  java.util.Calendar.getInstance();  
	        calendar.setTime(inputDate); 
	        String inputDateString = sdf.format(calendar.getTime());            
			
	        prepareStatement("delete from rpt_input_value t where t.rptitemid in (select s.rptitemid from rpt_input_item s where s.rptnameid = " +
	        		rptNameID + ") and t.rptdate = to_date('"+ selectDate +"','yyyy-mm-dd') and t.inputid = " +
	        		userID + "and t.status = 2");
			
			executeUpdate();
			
			prepareStatement("select nvl(max(t.RPTVALUEID),0)+1 nextid from rpt_input_value t");
			
			executeQuery();
	        
			int nextid = 1;
			
			if(transRS.next()){
				nextid = transRS.getInt("nextid");				
			}
			
			if(coll1!=null && coll1.size()>=1)
		    {
		       for(int i=0;i<coll1.size();i++)
			   {
				prepareStatement("insert into rpt_input_value values(" + nextid + "," +
				((ReportResultInfo)(coll1.toArray()[i])).getRptitemid() + ",to_date('" + 
				selectDate +"','yyyy-mm-dd')," + item[i] +",to_date('" + inputDateString + "','yyyy-mm-dd')," + 
				userID + ",'" + userName + "','" + userNo + "',2)");
				
//				System.out.println("insert into rpt_input_value values(" + nextid + "," +
//				((ReportResultInfo)(coll1.toArray()[i])).getRptitemid() + ",to_date('" + 
//				selectDate +"','yyyy-mm-dd')," + item[i] +",to_date('" + inputDateString + "','yyyy-mm-dd')," + 
//				userID + ",'" + userName + "','" + userNo + "',2)");
				
				executeUpdate();
				
				nextid = nextid + 1;
			   }
		    }
			
			this.transConn.commit();
			
		} catch (Exception e) {
			try {
				this.transConn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new IException("制表预览回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("制表预览出现错误");
		}
		finally{
			finalizeDAO();
		}
    }
	
	public void saveRpt(Collection coll)throws IException
	{
	
		try {
			initDAO();
			this.transConn.setAutoCommit(false);
			
			String selectDate = (String)coll.toArray()[0];
			long userID = ((Long)coll.toArray()[1]).longValue();
//			String userName = (String)coll.toArray()[2];
//			String userNo = (String)coll.toArray()[3];
			long rptNameID = ((Long)coll.toArray()[4]).longValue();
			
			prepareStatement("select count(*) countnum from rpt_input_item t,rpt_input_name s,rpt_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
					rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                    " and r.status = 2 order by t.rptitemcode");
			executeQuery();
			
			int countnum = 0;
			
			if(transRS.next()){
				countnum = transRS.getInt("countnum");				
			}
			
			if(countnum == 0)
			{}else{
		        prepareStatement("delete from rpt_input_value t where t.rptitemid in (select s.rptitemid from rpt_input_item s where s.rptnameid = " +
		        		rptNameID + ") and t.rptdate = to_date('"+ selectDate +"','yyyy-mm-dd') and t.status = 1");
				
				executeUpdate();
				
				prepareStatement("update rpt_input_value t set t.status = 1 where t.rptitemid in (select s.rptitemid from rpt_input_item s where s.rptnameid = "
						+ rptNameID + ") and t.rptdate = to_date('" + selectDate + "','yyyy-mm-dd') and t.inputid = " + userID);
				
				executeUpdate();
			}
			
			this.transConn.commit();
			
		} catch (Exception e) {
			try {
				this.transConn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new IException("制表保存回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("制表保存出现错误");
		}
		finally{
			finalizeDAO();
		}
    }
	
	public Collection backRptShow(Collection coll)throws IException
	{
		Collection result = null;
		
			result = new ArrayList();
			try {
				initDAO();
				
				String selectDate = (String)coll.toArray()[0];
				long userID = ((Long)coll.toArray()[1]).longValue();
//				String userName = (String)coll.toArray()[2];
//				String userNo = (String)coll.toArray()[3];
				long rptNameID = ((Long)coll.toArray()[4]).longValue();
				
				prepareStatement("select count(*) countnum from rpt_input_item t,rpt_input_name s,rpt_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
						rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                        " and r.status = 2 order by t.rptitemcode");
				executeQuery();
				
				int countnum = 0;
				
				if(transRS.next()){
					countnum = transRS.getInt("countnum");				
				}
				
				if(countnum == 0)
				{
					    prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,r.rptvalue rptdefault,t.memo from rpt_input_item t,rpt_input_name s,rpt_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
					    rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
	                    " and r.status = 1 order by t.rptitemcode");
				}else{
				        prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,r.rptvalue rptdefault,t.memo from rpt_input_item t,rpt_input_name s,rpt_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
						rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                        " and r.status = 2 order by t.rptitemcode");
				}
				executeQuery();

				while(transRS.next()){
					ReportResultInfo resultInfo = new ReportResultInfo();
					resultInfo.setRptname(transRS.getString("rptname"));
					resultInfo.setRptitemcode(transRS.getString("rptitemcode"));
					resultInfo.setRptitemname(transRS.getString("rptitemname"));
					resultInfo.setRptdefault(transRS.getDouble("rptdefault"));
					resultInfo.setMemo(transRS.getString("memo"));
					resultInfo.setRptitemid(transRS.getLong("rptitemid"));
					
					result.add(resultInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException("查询报表制表返回页面显示栏出现错误");
			}
			finally{
				finalizeDAO();
			}
		return result;
	}
	
}
