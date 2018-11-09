/*
 * Created on 2005-6-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.awake.bizlogic;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.budget.awake.dataentity.AwakeInfo;
import com.iss.itreasury.budget.constitute.dao.Budget_PlanDAO;
import com.iss.itreasury.budget.util.BUDGETConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Awake {

    /**
     * 
     */
    public Awake() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 组合所有业务提醒字符串，并至于
     * @throws Exception
     */
    public void getAllAwake() throws Exception
	{
        PreparedStatement ps = null;
        Connection conn = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        
        String sResult = " ";
        try{
            Log.print("***进入方法 -- getAllAwake ***");
            
            conn = Database.getConnection();

            sbSQL = new StringBuffer();
            sbSQL.append(" select distinct a.nOfficeID,a.nCurrencyID,b.nclientid,b.id userID from SETT_OFFICETIME a,ob_user b where a.nOfficeID=b.nOfficeID and a.nCurrencyID=b.nCurrencyID and b.nstatus="+ Constant.RecordStatus.VALID +" \n");
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();
            
            while (rs.next()){
                long lCurrencyID = -1;
                long lOfficeID = -1;
                long lClientID = -1;
                long lUserID = -1;
                
                AwakeInfo awakeinfo = new AwakeInfo();
                
                lOfficeID=rs.getLong("nOfficeID");
                lCurrencyID=rs.getLong("nCurrencyID");
                lClientID=rs.getLong("nclientid");
                lUserID=rs.getLong("userID");
                
                awakeinfo.setOfficeID(lOfficeID);
                awakeinfo.setCurrencyID(lCurrencyID);
                awakeinfo.setClientID(lClientID);
                awakeinfo.setUserID(lUserID);
                awakeinfo.setCon(conn);
                
                sResult += getAwakeApprove(awakeinfo);//待审批的业务提醒
                sResult += getAwakeOfReceive(awakeinfo);//待接收的业务提醒
                sResult += getAwakeFlexibleWarning(awakeinfo);//超过预警比例的预警提醒
                sResult += getAwakeRigidWarning(awakeinfo);//超过柔性比例的预警提醒
                
                String strMsg = sResult;
                //保存业务提醒信息
                String strKey = String.valueOf(lOfficeID)+String.valueOf(lCurrencyID)+String.valueOf(lUserID);
                AwakeInfo.AwakeMSG.put(strKey,strMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
            if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
			if (conn != null){
                conn.close();
                conn = null;
            }
        }
	}
    /**
     * 得到待审批的业务提醒
     * 查询预算表中下一级审核人为当前单位的预算
     * @param info
     * @return
     * @throws Exception
     */
    public String getAwakeApprove(AwakeInfo info)throws Exception
    {
        String sResult = " ";
        Budget_PlanDAO dao = new Budget_PlanDAO();
        Collection c = dao.findUnCheckBudget(info.getClientID(),info.getUserID(),info.getOfficeID(),info.getCurrencyID());
        
        if(c != null && c.size()>0){
    		sResult="共有"+ c.size() +"笔待审批的业务  ";
    	}
        
        return sResult;
    }
    
    /**
     * 得到待接收的业务提醒
     * @param info
     * @return
     * @throws Exception
     */
    public String getAwakeOfReceive(AwakeInfo info)throws Exception
    {
        String sResult = " ";
        Budget_PlanDAO dao = new Budget_PlanDAO();
        Collection c = dao.findLowerClient(info.getClientID(),BUDGETConstant.ConstituteStatus.COMMIT,info.getOfficeID(),info.getCurrencyID());
        if(c != null && c.size()>0){
    		sResult="共有"+ c.size() +"笔待接收的业务  ";
    	}
        
        return sResult;
    }
    
    /**
     * 得到超过预警比例的预警提醒，针对刚性控制
     * @param info,当前办事处币种下的体系下的所有当前客户有权限的项目的检查
     * 以当前办事处ID和币种ID作为条件(用户ID是否也应该做为条件？)，查询预算体系表、预算项目模板表、预算执行情况汇总表查询项目的记录
     * 状态为目前默认正常都为1
     * 根据查询出来的实际执行数/预算数得到的值和预警比例比较，如大于预警比例则提示
     * @return
     * @throws Exception
     */
    public String getAwakeFlexibleWarning(AwakeInfo info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//项目名称
        double WarnScale;//刚性比例
        double BudgetAmount=0.0;//预算数
        double ExecuteAmount=0.0;//实际执行数
        double lastDouble=0.0;//除后得到的数
        try{           
            sbSQL = new StringBuffer();
            sbSQL.append(" select a.itemName,c.warnScale,b.BudgetAmount,b.ExecuteAmount");
            sbSQL.append(" from Budget_Templet a,Budget_ItemSum b,Budget_System c");
            sbSQL.append(" WHERE c.id=a.budgetSystemID");
            sbSQL.append(" and a.budgetSystemID= b.BudgetSystemID");
            sbSQL.append(" and a.id=b.ItemID and a.StatusID=1");
            sbSQL.append(" and a.budgetType = "+BUDGETConstant.BudgetControlType.RIGID+"");
            sbSQL.append(" and a.controlType="+Constant.YesOrNo.YES+"");
            sbSQL.append(" and a.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and b.StatusID = "+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.StatusID="+Constant.RecordStatus.VALID+""); 
            sbSQL.append(" and c.OfficeID = ?");
            sbSQL.append(" and c.CurrencyID=?");
            sbSQL.append(" and b.ClientID = ?");
            sbSQL.append(" ORDER BY a.ID");
            ps = info.getCon().prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			ps.setLong(3, info.getClientID());
			
            rs = ps.executeQuery();        
            while (rs.next()){
            	itemName=rs.getString(1);
            	WarnScale=rs.getDouble(2);
            	BudgetAmount=rs.getDouble(3);
            	ExecuteAmount=rs.getDouble(4);
            	//预警比例如果为0，不做比较
            	if(WarnScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//判断预算数是否为0
            			lastDouble=ExecuteAmount/BudgetAmount;     //得到实际执行数/预算数得到的值               		
            			if(lastDouble>WarnScale){//除后得到的值如果大于预警比例，则提示
                			sResult+="项目"+itemName+"超出预警预算  ";            			
                		}            			
            		}           		
            	}
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
            if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
        }
        return sResult;
    }
    
    /**
     * 得到超过柔性比例的预警提醒，针对柔性控制
     * @param info,当前办事处币种下的体系下的所有当前客户有权限的项目的检查
     * 以当前办事处ID和币种ID作为条件(用户ID是否也应该做为条件？)，查询预算体系表、预算项目模板表、预算执行情况汇总表查询项目的记录
     * 状态为目前默认正常都为1
     * 根据查询出来的实际执行数/预算数得到的值和柔性比例比较，如大于柔性比例则提示
     * @return
     * @throws Exception
     */
    
    public String getAwakeRigidWarning(AwakeInfo info)throws Exception
    {
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        String itemName="";//项目名称
        double SuppleScale;//柔性比例
        double BudgetAmount=0.0;//预算数
        double ExecuteAmount=0.0;//实际执行数
        double lastDouble=0.0;//除后得到的数
        try{        
            sbSQL = new StringBuffer();
            sbSQL.append(" select a.itemName,a.SuppleScale,b.BudgetAmount,b.ExecuteAmount");
            sbSQL.append(" from Budget_Templet a,Budget_ItemSum b,Budget_System c");
            sbSQL.append(" WHERE c.id=a.budgetSystemID");
            sbSQL.append(" and a.budgetSystemID= b.BudgetSystemID");
            sbSQL.append(" and a.id=b.ItemID");
            sbSQL.append(" and a.budgetType = "+BUDGETConstant.BudgetControlType.FLEXIBLE+"");
            sbSQL.append(" and a.controlType="+Constant.YesOrNo.YES+"");
            sbSQL.append(" and a.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and b.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.StatusID="+Constant.RecordStatus.VALID+"");
            sbSQL.append(" and c.OfficeID = ? ");
            sbSQL.append(" and c.CurrencyID =  ?");
            sbSQL.append(" and b.ClientID = ?");
            sbSQL.append(" ORDER BY a.ID");
            ps = info.getCon().prepareStatement(sbSQL.toString());
			ps.setLong(1, info.getOfficeID());
			ps.setLong(2, info.getCurrencyID());
			ps.setLong(3, info.getClientID());
            rs = ps.executeQuery();        
            while (rs.next()){
            	itemName=rs.getString(1);
            	SuppleScale=rs.getDouble(2);
            	BudgetAmount=rs.getDouble(3);
            	ExecuteAmount=rs.getDouble(4);  
            	//柔性比例如果为0，不比较
            	if(SuppleScale!=0){
            		if(BudgetAmount!=0.0 && BudgetAmount!=0){//判断预算数是否为0
            			lastDouble=ExecuteAmount/BudgetAmount;     //得到实际执行数/预算数得到的值  
            			if(lastDouble>SuppleScale){//除后得到的值如果大于柔性比例，则提示
            				sResult+="项目"+itemName+"超出柔性预算  ";            			
            			}
            		}
            	}
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally{
            if (rs != null){
				rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
        }
        return sResult;
    }
    /*public static void main(String[] args){
    	Connection conn=null;
    	Awake wa=new Awake();
    	try{
    		conn = Database.getConnection();
    		AwakeInfo info=new AwakeInfo();
    		info.setClientID(1);
    		info.setCurrencyID(1);
    		info.setOfficeID(1);
    		info.setCon(conn);
    		wa.getAllAwake();    		
    	}catch(Exception ex){ex.printStackTrace();}
    }*/
    
}
