package com.iss.itreasury.audit.datamaintenance.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.iss.itreasury.audit.datamaintenance.dataentity.DatamaintenanceCondition;
import com.iss.itreasury.audit.datamaintenance.dataentity.DatamaintenanceResultInfo;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;


public class DatamaintenanceDao extends ITreasuryDAO{
	 // SQL语法结构
    private StringBuffer m_sbSelect = null;
    private StringBuffer m_sbFrom = null;
    private StringBuffer m_sbWhere = null;
    private StringBuffer m_sbOrderBy = null;
    private Log4j logger = null;
    public DatamaintenanceDao() {
        super();
        logger = new Log4j(Constant.ModuleType.AUDIT, this);
    }
	public Collection queryBasic(DatamaintenanceCondition condition)throws IException
	{
		Connection conn = null;
    	CallableStatement cstmt = null;
		Collection result = null;
		m_sbSelect = new StringBuffer();
		m_sbSelect.append("select t.indexid, \n");
		m_sbSelect.append("s.basicid, \n");
		m_sbSelect.append("t.sort, \n");
		m_sbSelect.append("t.attribute4, \n");
		m_sbSelect.append("t.indexname, \n");
		m_sbSelect.append("t.sdefine, \n");
		m_sbSelect.append("t.sfrequency, \n");
		m_sbSelect.append("s.basicname, \n");
		m_sbSelect.append("s.procedure, \n");
		m_sbSelect.append("nvl(r.value,-999999999999999) value \n");
		
		m_sbFrom = new StringBuffer();
		m_sbFrom.append("from (select * from rpt_audit_basicvalue where thedate = to_date('"+condition.getSelectDate()+"','yyyy-mm-dd')) r, \n");
		m_sbFrom.append("rpt_audit_basic s,rpt_audit_index t \n");
		
		m_sbWhere = new StringBuffer();
		m_sbWhere.append(" where r.basicid(+) = s.basicid  \n");
		m_sbWhere.append(" and s.indexid = t.indexid  \n");
		
		m_sbOrderBy  = new StringBuffer();
		m_sbOrderBy.append("  order by s.basiccode \n");
			result = new ArrayList();
			try {
				try {
					//conn = Database.get_1104jdbc_connection();
					conn = Database.getConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				initDAO();
				prepareStatement( m_sbSelect.toString()+m_sbFrom.toString()+m_sbWhere.toString()+m_sbOrderBy.toString());
				System.out.println("风险监测指标基础数据维护: \n"+m_sbSelect.toString() + m_sbFrom.toString()
						+ m_sbWhere.toString() + m_sbOrderBy.toString());
				executeQuery();
				int tempOnePropertyCount = 0;
				int tempTwoPropertyCount = 0;
				String tempsort  = "";
				long tempindexid = -1;
				
				HashMap map = new HashMap();
				result.add(map);
				while(transRS.next()){
					DatamaintenanceResultInfo resultInfo = new DatamaintenanceResultInfo();
					
					if(tempsort.equals(transRS.getString("sort")))
					{
						tempOnePropertyCount++;
						if(!tempsort.equals(""))
						{
							map.put("sort"+tempsort, String.valueOf(tempOnePropertyCount));
						}
					}
					else
					{
						tempOnePropertyCount=1;
						if(!transRS.getString("sort").equals(""))
						{
							map.put("sort"+transRS.getString("sort"), String.valueOf(tempOnePropertyCount));
						}
						
					}
					if(tempindexid==transRS.getLong("indexid"))
					{
						tempTwoPropertyCount++;
						if(tempindexid>0)
						{
							map.put("indexid"+tempindexid, String.valueOf(tempTwoPropertyCount));
						}
					}
					else
					{
						tempTwoPropertyCount=1;
						if(transRS.getLong("indexid")>0)
						{
							map.put("indexid"+transRS.getLong("indexid"), String.valueOf(tempTwoPropertyCount));
						}
						
					}
					
					
					resultInfo.setIndexid(transRS.getLong("indexid"));
					
					tempindexid = resultInfo.getIndexid();
					
					resultInfo.setBasicid(transRS.getLong("basicid"));
					resultInfo.setSort(transRS.getString("sort"));
					
					tempsort = resultInfo.getSort();
					
					resultInfo.setAttribute4(transRS.getString("attribute4"));
					resultInfo.setIndexname(transRS.getString("indexname"));
					resultInfo.setSdefine(transRS.getString("sdefine"));
					resultInfo.setSfrequency(transRS.getString("sfrequency"));
					resultInfo.setBasicname(transRS.getString("basicname"));
					resultInfo.setProcedure(transRS.getString("procedure"));
					resultInfo.setValue(transRS.getDouble("value"));
					
					if(resultInfo.getProcedure().equals("暂缺"))
					{
					   resultInfo.setCalValue(new Double("-999999999999999").doubleValue());
					}else{
//						String procedure = resultInfo.getProcedure();
//						cstmt = conn.prepareCall("{call "+procedure+"(?,?,?)}");
//					    cstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
//					    cstmt.registerOutParameter(2, java.sql.Types.NUMERIC);
//						cstmt.setString(3, condition.getSelectDate());
//						cstmt.executeQuery();
//						double nValue = cstmt!=null?cstmt.getDouble(1):0;
//						double nFlag = cstmt!=null?cstmt.getDouble(2):1;
//						if(nFlag == 1)
//						{	
//					       resultInfo.setCalValue(nValue);
//						}else{
							resultInfo.setCalValue(new Double("-999999999999999").doubleValue());
//						}
					}
					
					result.add(resultInfo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new IException("风险监测指标基础数据维护--查询出现错误");
			}
			finally{
				try {
				    if(cstmt!=null)
				    	cstmt.close();
				    if(conn!=null)
				    	conn.close();
				   } catch (SQLException e) {			    
				    e.printStackTrace();
				   }
				finalizeDAO();
			}
		return result;
	}
	
	public void saveBasic(DatamaintenanceCondition condition)throws IException
	{
	
		try {
			initDAO();
			this.transConn.setAutoCommit(false);
			
			String selectDate = condition.getSelectDate();
			String[] adjustvalue = condition.getAdjustvalue();
			
			prepareStatement("select count(*) countnum from rpt_audit_basicvalue m where m.thedate = to_date('" + selectDate + "','yyyy-mm-dd')");
			executeQuery();
			
			int countnum = 0;
			
			if(transRS.next()){
				countnum = transRS.getInt("countnum");				
			}
			
			if(countnum == 0)
			{}else{
		        prepareStatement("delete from rpt_audit_basicvalue m where m.thedate = to_date('" + selectDate + "','yyyy-mm-dd')");
				
				executeUpdate();
			}
			
            prepareStatement("select s.basicid from rpt_audit_basic s,rpt_audit_index t where s.indexid = t.indexid order by s.basiccode");
			
			executeQuery();
	        
			Collection result = new ArrayList();;
			
			while(transRS.next()){
				result.add(new Integer(transRS.getInt("basicid")));				
			}
			
            prepareStatement("select nvl(max(m.id),0)+1 nextid from rpt_audit_basicvalue m");
			
			executeQuery();
	        
			int nextid = 1;
			
			if(transRS.next()){
				nextid = transRS.getInt("nextid");				
			}
			
			if(result!=null && result.size()>=1)
		    {
		       for(int i=0;i<result.size();i++)
			   {
				prepareStatement("insert into rpt_audit_basicvalue values(" + nextid + "," + ((Integer)result.toArray()[i]).intValue() + ",to_date('" + selectDate + "','yyyy-mm-dd')," + adjustvalue[i] + ")");
				
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
				throw new IException("风险监测指标基础数据维护--保存出现错误");
			}
			
			e.printStackTrace();
			throw new IException("风险监测指标基础数据维护--保存出现错误");
		}
		finally{
			finalizeDAO();
		}
    }
	
	public Collection queryRptShow(DatamaintenanceCondition condition)throws IException
	{
		Collection result = null;
		
			result = new ArrayList();
			try {
				initDAO();
				//prepareStatement("select t.rptitemcode,t.rptitemname,t.rptdefault,t.memo from rpt_audit_input_item t where t.rptnameid = "+ condition.getRptNameID() +" order by t.rptitemcode");
				prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,t.rptdefault,t.memo from rpt_audit_input_item t,rpt_audit_input_name s where t.rptnameid = s.rptnameid and t.rptnameid = "+ condition.getRptNameID() +" order by t.rptitemcode");
				executeQuery();

				while(transRS.next()){
					DatamaintenanceResultInfo resultInfo = new DatamaintenanceResultInfo();
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
				throw new IException("风险监测指标基础数据维护--查询报表制表页面显示栏出现错误");
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
			prepareStatement("update rpt_audit_input_item t set t.rptdefault = " + item[i].doubleValue() + 
					",t.inputdate = to_date('" + inputDateString + "','yyyy-mm-dd'),t.inputid = " + userID + 
					",t.inputsname = '" + userName + "',t.inputsloginno = '" + userNo 
					+ "' where t.rptitemcode = '" + ((DatamaintenanceResultInfo)(coll1.toArray()[i])).getRptitemcode() + "'");
			
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
				throw new IException("风险监测指标基础数据维护--制表设置默认值回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("风险监测指标基础数据维护--制表设置默认值出现错误");
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
			
	        prepareStatement("delete from rpt_audit_input_value t where t.rptitemid in (select s.rptitemid from rpt_audit_input_item s where s.rptnameid = " +
	        		rptNameID + ") and t.rptdate = to_date('"+ selectDate +"','yyyy-mm-dd') and t.inputid = " +
	        		userID + "and t.status = 2");
			
			executeUpdate();
			
			prepareStatement("select nvl(max(t.RPTVALUEID),0)+1 nextid from rpt_audit_input_value t");
			
			executeQuery();
	        
			int nextid = 1;
			
			if(transRS.next()){
				nextid = transRS.getInt("nextid");				
			}
			
			if(coll1!=null && coll1.size()>=1)
		    {
		       for(int i=0;i<coll1.size();i++)
			   {
				prepareStatement("insert into rpt_audit_input_value values(" + nextid + "," +
				((DatamaintenanceResultInfo)(coll1.toArray()[i])).getRptitemid() + ",to_date('" + 
				selectDate +"','yyyy-mm-dd')," + item[i] +",to_date('" + inputDateString + "','yyyy-mm-dd')," + 
				userID + ",'" + userName + "','" + userNo + "',2)");
				
//				System.out.println("insert into rpt_audit_input_value values(" + nextid + "," +
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
				throw new IException("风险监测指标基础数据维护--制表预览回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("风险监测指标基础数据维护--制表预览出现错误");
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
			
			prepareStatement("select count(*) countnum from rpt_audit_input_item t,rpt_audit_input_name s,rpt_audit_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
					rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                    " and r.status = 2 order by t.rptitemcode");
			executeQuery();
			
			int countnum = 0;
			
			if(transRS.next()){
				countnum = transRS.getInt("countnum");				
			}
			
			if(countnum == 0)
			{}else{
		        prepareStatement("delete from rpt_audit_input_value t where t.rptitemid in (select s.rptitemid from rpt_audit_input_item s where s.rptnameid = " +
		        		rptNameID + ") and t.rptdate = to_date('"+ selectDate +"','yyyy-mm-dd') and t.status = 1");
				
				executeUpdate();
				
				prepareStatement("update rpt_audit_input_value t set t.status = 1 where t.rptitemid in (select s.rptitemid from rpt_audit_input_item s where s.rptnameid = "
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
				throw new IException("风险监测指标基础数据维护--制表保存回滚出现错误");
			}
			
			e.printStackTrace();
			throw new IException("风险监测指标基础数据维护--制表保存出现错误");
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
				
				prepareStatement("select count(*) countnum from rpt_audit_input_item t,rpt_audit_input_name s,rpt_audit_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
						rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                        " and r.status = 2 order by t.rptitemcode");
				executeQuery();
				
				int countnum = 0;
				
				if(transRS.next()){
					countnum = transRS.getInt("countnum");				
				}
				
				if(countnum == 0)
				{
					    prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,r.rptvalue rptdefault,t.memo from rpt_audit_input_item t,rpt_audit_input_name s,rpt_audit_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
					    rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
	                    " and r.status = 1 order by t.rptitemcode");
				}else{
				        prepareStatement("select s.rptname,t.rptitemid,t.rptitemcode,t.rptitemname,r.rptvalue rptdefault,t.memo from rpt_audit_input_item t,rpt_audit_input_name s,rpt_audit_input_value r where t.rptnameid = s.rptnameid and t.rptitemid = r.rptitemid  and t.rptnameid = " + 
						rptNameID  + "and r.rptdate = to_date('"+ selectDate + "','yyyy-mm-dd') and r.inputid =" + userID +
                        " and r.status = 2 order by t.rptitemcode");
				}
				executeQuery();

				while(transRS.next()){
					DatamaintenanceResultInfo resultInfo = new DatamaintenanceResultInfo();
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
				throw new IException("风险监测指标基础数据维护--查询报表制表返回页面显示栏出现错误");
			}
			finally{
				finalizeDAO();
			}
		return result;
	}
}
