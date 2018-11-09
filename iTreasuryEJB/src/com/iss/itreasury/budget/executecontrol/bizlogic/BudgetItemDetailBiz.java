/*
 * Created on 2005-6-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.budget.executecontrol.bizlogic;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.iss.itreasury.budget.executecontrol.dao.Budget_ItemSumDAO;
import com.iss.itreasury.budget.executecontrol.dataentity.BudgetItemSumInfo;
import com.iss.itreasury.budget.executecontrol.dataentity.RowInfo;
import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.executecontrol.dao.Budget_ItemDetailDAO;
import com.iss.itreasury.budget.executecontrol.dataentity.BudgetItemDetailInfo;
import com.iss.itreasury.budget.query.paraminfo.QueryBudgetInfo;
import com.iss.itreasury.budget.util.BUDGETNameRef;
import com.iss.itreasury.budget.util.BUDGETConstant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.account.dao.sett_TransAccountDetailDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;


/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BudgetItemDetailBiz {

    Budget_ItemDetailDAO detailDao = new Budget_ItemDetailDAO();
    Budget_ItemSumDAO sumDao=new Budget_ItemSumDAO(); 
    public BudgetItemDetailBiz() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 保存执行情况信息，新增要判断交易号、账户号、执行金额是否重复
     * 只能新增，不能修改
     * @param info
     * @return
     * @throws BudgetException
     */
    public long save(BudgetItemDetailInfo info) throws BudgetException,Exception
    {
        long tmpLong = -1;
        Connection transConn = null;//新建连接
		//判断交易号、账户号、执行金额是否重复 ，如果重复不能新建
        try {
        	try{
    			transConn =Database.getConnection();
    			transConn.setAutoCommit(false);
            }catch (Exception e){
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
	        BudgetItemDetailInfo tmpInfo = new BudgetItemDetailInfo();	        
	        tmpInfo.setTransNo(info.getTransNo());		
	        tmpInfo.setAccountID(info.getAccountID());
	        tmpInfo.setExcuteAmount(info.getExcuteAmount());
			tmpInfo.setStatusID(Constant.RecordStatus.VALID);
			Collection c = detailDao.findByCondition(tmpInfo,"order by transno");
			if (c!=null && c.size()>0){
				//如果是新增操作,直接抛出异常
				if (info.getId()==-1){ 
					tmpLong= 0;
					throw new BudgetException();
				}
			}else{				
				if (info.getId() <0){	        	
		            detailDao.add(info);
		            String[] str=getParentItem(info.getItemNo());	        		        	
	    			for(int i=0;i<str.length ;i++){
	    				updateSumItem(str[i],info.getClientID(),info.getExecuteDate(),info.getExcuteAmount(),"1");
	    			}	        	
		        	tmpLong=10;
		        }
			}
			//交易号不重复可以新建
	        
	        try{
                if (transConn != null){
                    transConn.commit();
                    transConn = null;
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new ITreasuryDAOException("数据库关闭异常发生", e);
            }
        }catch (Exception e){
            try{
                if (transConn != null){
                    transConn.rollback();
                    transConn = null;
                }
            }catch (Exception es){
                es.printStackTrace();
            }
        }finally{
            try{
                if (transConn != null){
                    transConn.close();
                    transConn = null;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }   	
        return tmpLong;
    }
    public static String[] getParentItem(String itemNo){
    	
    	StringBuffer sb=new StringBuffer();
		sb.append(itemNo);
		sb.append("&");
		while(itemNo.indexOf("_")>-1)
		{
			int site=itemNo.lastIndexOf("_");
			itemNo=itemNo.substring(0,site);
			sb.append( itemNo);
			sb.append("&");
		}
		return sb.toString() .split("&");    	
    }
    /**
     * 删除某执行数据(只修改记录的状态不是真正删除)
     * @param id
     * @throws BudgetException
     */
    public long delete(long id) throws BudgetException
    {
    	Budget_ItemDetailDAO dao=new Budget_ItemDetailDAO();
    	BudgetItemDetailInfo info=findByID(id);
    	BudgetItemDetailBiz biz=new BudgetItemDetailBiz();
    	Collection coll=null;
    	long retlong=-1;
		try{
			//改变执行情况表状态
			dao.delete(id);
			//对汇总表进行更新操作
			String[] str=getParentItem(info.getItemNo());	        		        	
			for(int i=0;i<str.length ;i++){
				updateSumItem(str[i],info.getClientID(),info.getExecuteDate(),info.getExcuteAmount(),"1");
			}	 
			retlong=id;
		}catch (Exception e){ 
			e.printStackTrace();
		}
		return retlong;
    }
    
    /**
     * 根据条件查询执行情况明细
     * @param condition
     * @return
     * @throws BudgetException
     */
    public Collection findByCondition(QueryBudgetInfo condition)throws BudgetException,Exception
    {    	   	
        return detailDao.findByCondition(condition);
    }
    
    /**
     * 根据ID查询某条执行情况
     * @param id
     * @return
     * @throws BudgetException
     */
    public BudgetItemDetailInfo findByID(long id) throws BudgetException
    {
        BudgetItemDetailInfo info = null;
        try {
            info =  (BudgetItemDetailInfo) detailDao.findByID(id,BudgetItemDetailInfo.class);
        } catch (ITreasuryDAOException e) {
            e.printStackTrace();
            throw new BudgetException();
        }
        return info;
    }
    
    /**
     * 从excel导入数据,文件有以下几列
     * 预算项目编号、交易号、合同号/存单号、放款通知单号、单位编号、账户号、	金额、日期
     * @param file 导入文件目录
     * @return 导入的行数
     * @throws BudgetException
     */
    public long importExcelData(Collection coll)throws BudgetException
    {
    	Collection  cllRow = new ArrayList();	
    	BudgetItemDetailBiz biz=new BudgetItemDetailBiz();
		long retResult=-1;
		String panduan="0";
		try {						
			for(Iterator iter=coll.iterator();iter.hasNext();){
				RowInfo result=(RowInfo)iter.next();
				//判断是否有和数据库中重复的记录，如果重复则不导入
				BudgetItemDetailInfo tmpInfo = new BudgetItemDetailInfo();	        
		        tmpInfo.setTransNo(result.getTransNo());		
		        tmpInfo.setAccountID(BUDGETNameRef.getAccountIDByCode(result.getAccountNo()));
		        tmpInfo.setExcuteAmount(Double.parseDouble(result.getExcuteAmount()));
				tmpInfo.setStatusID(Constant.RecordStatus.VALID);
				Collection c = detailDao.findByCondition(tmpInfo,"order by transno");
				if (c!=null && c.size()>0){
					panduan="1";
				}
				retResult=0;
			}
			if(panduan.equals("0")){
				for(Iterator iter=coll.iterator();iter.hasNext();){
					RowInfo result=(RowInfo)iter.next();
					BudgetItemDetailInfo info=new BudgetItemDetailInfo();			
					info.setItemNo(result.getItemNo());
					info.setItemID(result.getItemID());//项目ID
					info.setItemName(result.getItemName());//项目名称
					info.setTransNo(result.getTransNo());//交易号
					info.setContractCode(result.getContractCode());//合同号
					info.setPayFormCode(result.getPayFormCode());//放款通知单号
					info.setClientID(result.getClientID());//单位ID
					info.setAccountID(result.getAccountID());//账户ID
					info.setAccountTypeID(result.getAccountType());//账户类型ID
					info.setExcuteAmount(Double.parseDouble(result.getExcuteAmount())); //金额
					info.setExecuteDate(DataFormat.getDateTime(result.getExecuteDate()));//执行日期
					info.setStatusID(1);//状态ID
					info.setDataSource(BUDGETConstant.DataSource.BUDGETIMPORT);//数据源					
					retResult=biz.save(info);
				}				
			}
		}catch(Exception ex){ex.printStackTrace();}
        return retResult;
    }
    
    /**
     * 检查文件中数据是否合法
     * @param cell
     * @return
     */
    public String getCellValue(HSSFCell cell){
		String str = "";
		if(cell!=null){
			if(cell.getCellType()==HSSFCell.CELL_TYPE_BLANK 
					||cell.getCellType()==HSSFCell.CELL_TYPE_ERROR
			   ){
				//HSSFDateUtil.isCellDateFormatted(cell)
			}else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					java.util.Date date = cell.getDateCellValue();
					str = DataFormat.formatDate(date,DataFormat.FMT_DATE_YYYYMMDD);
				}else{
					if(cell.getNumericCellValue()!=0.0)
					str = String.valueOf(cell.getNumericCellValue());
				}
			}else if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
				str = cell.getStringCellValue()==null?"":cell.getStringCellValue();
			}else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				str = String.valueOf(cell.getBooleanCellValue());
			}
		}
		
		return str.trim();
	}	
    /**
     * 导入结算未导入数据，4个表关联查询需要的数据
     * 业务类型为银行收款或者银行付款、状态为空(1为收款，2为付款)
     * 根据业务类型判断用哪个客户编号
     * 倒入数据后更新表SETT_TRANSACCOUNTDETAIL 的budgetstatusid=1(原来为空)
     * @return
     * @throws BudgetException
     */
    public long importSettlementData()throws BudgetException,Exception 
    {    	    	
    	long retlong=-1;
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        Connection conn=null;
        double ExecuteAmount=0.0;//实际执行数
        long clientID=-1;
        BudgetItemDetailBiz biz=new BudgetItemDetailBiz();
        //AccountOperation oper=new AccountOperation();
        sett_TransAccountDetailDAO dao=new sett_TransAccountDetailDAO();
        try{   
        	conn=Database.getConnection();
            sbSQL = new StringBuffer();
            sbSQL.append(" select b.nTransAccountid,b.stransno,b.dtexecute,b.mamount,b.budgetitemid,");
            sbSQL.append(" a.payclientid,a.receiveclientid,b.ntransactiontypeid,e.scontractcode,");
            sbSQL.append(" f.scode,a.abstract,c.naccounttypeid,d.itemno,d.itemname,b.ID");
            sbSQL.append(" from SETT_VTRANSACTION a,SETT_TRANSACCOUNTDETAIL b,");
            sbSQL.append(" SETT_ACCOUNT c,Budget_Templet d,loan_contractform e,loan_payform f");
            sbSQL.append(" where a.contractid = e.id and b.stransno = a.transno and a.loanformid=f.id"); 
            sbSQL.append(" and b.ntransaccountid = c.id and d.id=b.budgetitemid");
            sbSQL.append(" and not b.budgetitemid is null and b.budgetitemid!=-1");
            sbSQL.append(" and (b.ntransactiontypeid = 1 or b.ntransactiontypeid = 2)");                            
            sbSQL.append(" and b.budgetstatusid is null");                 
            ps = conn.prepareStatement(sbSQL.toString());	
            rs = ps.executeQuery();    
            while (rs.next()){   
            	if(rs.getLong(8)==1){//如果是银行收款，单位为收款单位
            		clientID=rs.getLong(7);            		
            	}else if(rs.getLong(8)==2){//如果是银行付款，单位为付款单位
            		clientID=rs.getLong(6);            		
            	}
            	BudgetItemDetailInfo info=new BudgetItemDetailInfo();
            	info.setId(-1);
            	info.setAccountID(rs.getLong(1));//账户ID
            	info.setTransNo(rs.getString(2));//交易号
            	info.setExecuteDate(rs.getTimestamp(3));//执行日期
            	info.setExcuteAmount(rs.getDouble(4));//执行金额
            	info.setItemID(rs.getLong(5));//项目ID
            	info.setClientID(clientID);//单位ID
            	info.setContractCode(rs.getString(9));//合同号
            	info.setPayFormCode(rs.getString(10));//放款通知单号
            	info.setRemark(rs.getString(11));//摘要
            	info.setAccountTypeID(rs.getLong(12));//账户类型ID
            	info.setItemNo(rs.getString(13));//项目编号
            	info.setItemName(rs.getString(14));//项目名称
            	info.setDataSource(4);//数据来源（结算）
            	info.setStatusID(1);//状态
            	retlong=biz.save(info);
            	if(retlong>0){//导入执行表成功则更新SETT_TRANSACCOUNTDETAIL 的状态为1
            		long statusid=1;
            		dao.updateBudgetStatusID(rs.getLong(15),statusid);	//更新此表的预算状态
            	}
            }
        }catch (Exception e){
            e.printStackTrace();          
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
        return retlong;
    }
    
    /**
     * 页面新建或者删除倒入数据时更新汇总记录，0为删除，1为新建
     * 删除时汇总表的执行数要减去执行情况表的执行数
     * 新建是汇总表的执行数要加上执行情况表的执行数
     * @param info
     * @param status
     * @return
     * @throws BudgetException
     */
    public void updateSumItem(String itemNo,long clientId,Timestamp excuteDate,double excuteAmountinfo,String status)throws BudgetException,Exception{
    	PreparedStatement ps = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;
        String sResult = " ";
        Connection conn=null;
        double ExecuteAmount=0.0;//实际执行数
        try{   
        	conn=Database.getConnection();
            sbSQL = new StringBuffer();
            	sbSQL.append(" select a.id,a.ExecuteAmount from Budget_ItemSum a ,budget_templet b");
                sbSQL.append(" WHERE a.StatusID=1 and a.ItemID=b.id");
                sbSQL.append(" and b.itemNo ='"+itemNo+"'");
                sbSQL.append(" and a.ClientID ="+clientId+"");
                sbSQL.append(" and decode(a.StartDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.StartDate) \n");
                sbSQL.append(" <= to_date('"+DataFormat.formatDate(excuteDate)+"','yyyy-mm-dd') \n");
                sbSQL.append(" and decode(a.EndDate,null,to_date('3000-01-01','yyyy-mm-dd'),a.EndDate) \n");
                sbSQL.append(" >= to_date('"+DataFormat.formatDate(excuteDate)+"','yyyy-mm-dd') \n");            
                ps = conn.prepareStatement(sbSQL.toString());	
                rs = ps.executeQuery();    
                while (rs.next()){   
                	BudgetItemSumInfo suminfo=new BudgetItemSumInfo();    
                	ExecuteAmount=rs.getDouble(2);             	
                	//如果删除:汇总表中的执行数要减去执行表中的执行数,否则要相加
                	suminfo.setId(rs.getLong(1));
                	if(status.equals("0")){           		            		          		
                		suminfo.setExecuteAmount(ExecuteAmount-excuteAmountinfo);
                	}else{
                		suminfo.setExecuteAmount(ExecuteAmount+excuteAmountinfo);
                	}
                	//对汇总表进行更新操作            	
                	sumDao.update(suminfo);
                }
            
            
        }catch (Exception e){
            e.printStackTrace();          
        }finally{
        	if(conn!=null){
        		conn.close();
        		conn=null;
        	}
            if (rs != null){
            	rs.close();
				rs = null;
			}
			if (ps != null){
				ps.close();
				ps = null;
			}
        }    	
    }
   
}
