/*
 * Created on 2005-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.executecontrol.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.budget.dao.BudgetDAO;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.executecontrol.dataentity.BudgetItemDetailInfo;
import com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Budget_ItemDetailDAO extends BudgetDAO{

    /**
     * 
     */
    public Budget_ItemDetailDAO() {
        super("Budget_ItemDetail");
        super.setUseMaxID(); 
        // TODO Auto-generated constructor stub
    }

    public Budget_ItemDetailDAO(Connection conn) {
        super("Budget_ItemDetail",conn);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 根据条件查询执行情况明细
     * 没有选择条件则查询所有yliu
     * @param condition
     * @return
     * @throws BudgetException
     */
    public Collection findByCondition(QueryBudgetInfo info)throws BudgetException,Exception{
    	PreparedStatement ps = null;
	    StringBuffer sbSQL = null;
	    ResultSet rs = null;
	    String sResult = " ";
	    Connection conn=null;
	    Collection coll=new ArrayList();
	    double ExecuteAmount=0.0;//实际执行数
	    try{   
	    	conn=Database.getConnection();
	        sbSQL = new StringBuffer();	       	        
	        sbSQL.append(" select distinct a.id,a.ItemNo,a.ItemName,a.ExecuteDate,a.TransNo,a.ExcuteAmount,a.PayFormCode,a.contractcode,a.remark,a.clientID");
	        sbSQL.append(" from Budget_ItemDetail a");	        
	        sbSQL.append(" WHERE a.StatusID=1");
	        if(info.getItemID()!=-1){
	        	sbSQL.append(" and b.ItemID ="+info.getItemID()+"");	        	
	        }
	        if(info.getClientID()!=-1){
	        	sbSQL.append(" and b.budgetClientID ="+info.getClientID()+"");	        	
	        }	       
	        if(info.getStartDate()!=null){
	        	sbSQL.append(" and to_date('"+DataFormat.getDateString(info.getStartDate())+"','yyyy-mm-dd') <=decode(a.ExecuteDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.ExecuteDate)");
	        }
	        if(info.getEndDate()!=null){
	        	sbSQL.append(" and to_date('"+DataFormat.getDateString(info.getEndDate())+"','yyyy-mm-dd') >=decode(a.ExecuteDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.ExecuteDate)");	        	
	        }	       
	        Log.print(sbSQL.toString());
	        ps = conn.prepareStatement(sbSQL.toString());	
	        rs = ps.executeQuery();  	        
	        while (rs.next()){  
	        	BudgetItemDetailInfo detinfo=new BudgetItemDetailInfo(); 
	        	detinfo.setId(rs.getLong(1));
	        	detinfo.setItemNo(rs.getString(2));
	        	detinfo.setItemName(rs.getString(3));
	        	detinfo.setExecuteDate(rs.getTimestamp(4));
	        	detinfo.setTransNo(rs.getString(5));
	        	detinfo.setExcuteAmount(new BigDecimal(rs.getString(6)).doubleValue());
	        	detinfo.setPayFormCode(rs.getString(7));
	        	detinfo.setContractCode(rs.getString(8));
	        	detinfo.setRemark(rs.getString(9));
	        	detinfo.setClientID(rs.getLong(10));
	        	coll.add(detinfo);
	        }
	        
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    	throw new Exception(ex.getMessage());
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
        return coll;
    }
}
