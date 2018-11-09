package com.iss.itreasury.budget.templet.bizlogic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.setting.dao.Budget_ItemPrivilegeDAO;
import com.iss.itreasury.budget.setting.dataentity.BudgetItemPrivilegeInfo;
import com.iss.itreasury.budget.templet.dao.Budget_TempletDAO;
import com.iss.itreasury.budget.templet.dataentity.BudgetTempletInfo;
import com.iss.itreasury.budget.util.BUDGETNameRef;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

public class BudgetTempletOperation {
	/**
	 *预算模板的保存操作，要求上级项目修改完成，下级项目自动复制上级项目属性
	 *要手动控制事务
	*/
	public long save(BudgetTempletInfo info) throws BudgetException
	{
		double suppleScale=0.0;
		String parentItemNo="";
		String itemNo="";
		long returnLong=-1;
		Connection transConn = null;//新建连接
		Collection col=null;//项目公式中包含其他项目的编号集合
		
		BudgetTempletOperation oper=new BudgetTempletOperation();
		long maxlevel=-1;
		String panduan="0";
		try{
			try{
    			transConn =Database.getConnection();
    			transConn.setAutoCommit(false);
            }catch (Exception e){
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Budget_TempletDAO dao =new Budget_TempletDAO("Budget_Templet",transConn);
			maxlevel=dao.getMaxLayer();//得到最大嵌套层数
			if(info.getId()>0){
				suppleScale=(new BigDecimal(info.getSuppleScale()).divide(new BigDecimal(100),5,4)).doubleValue();				
				info.setUpdateDate(Env.getSystemDateTime());//最新修改时间入库 
				info.setSuppleScale(suppleScale);
				if(info.getItemFormula()==null||info.getItemFormula().equals("")){
					panduan="1";					
				}else{
					col=dao.findByItemNo(info.getItemNO(),info.getItemFormula());//得到此公式包含其他项目的集合
					if(dao.checkItemFormula(info.getItemNO(),col,maxlevel)){
						panduan="1";//检查项目公式没有问题						
					}else{	
						returnLong=-2;
					}
				}
				//项目公式检查正确可以修改
				if(panduan.equals("1")){
					dao.update(info);
					returnLong=info.getId();					
				}												
			}else{			
				suppleScale=(new BigDecimal(info.getSuppleScale()).divide(new BigDecimal(100),5,4)).doubleValue();
				info.setSuppleScale(suppleScale);
				//System.out.println(info.getParentItemID()+"^^^^^^^^^");
				parentItemNo=BUDGETNameRef.getItemNoByID(info.getParentItemID());//根据项目ID得到项目编号				
				itemNo=dao.getLastFlow(info.getParentItemID(),parentItemNo);
				//System.out.println(parentItemNo+"^^^^^^^^^"+itemNo);
				if(itemNo.indexOf("_")>-1){
					String[] as = itemNo.split("_");
					info.setItemLevel(as.length);
				}else{
					info.setItemLevel(1);					
				}				
				info.setIsLeaf(1);//是叶子
				info.setIsReadOnly(0);	//可修改			
				info.setItemNO(itemNo);				
				info.setUpdateDate(Env.getSystemDateTime());//最新修改时间入库 
				info.setInputDate(Env.getSystemDateTime());//最新新建时间		
				if(info.getItemLevel()<=12){
					returnLong = dao.add(info);
					//修改父项目的信息，为根项目，不能修改
					if(info.getParentItemID()>0){
						BudgetTempletInfo parenItem=new BudgetTempletInfo();
						parenItem.setId(info.getParentItemID());
						parenItem.setIsLeaf(0);
						dao.update(parenItem);
					}					
				}else{
					returnLong=-3;					
				}
				
			}			
			try{
                if (transConn != null){
                    transConn.commit();
					transConn.close();
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
					transConn.close();
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
    	
	    return returnLong;
    }
	/**
	* 删除一个项目，要求如果上级项目没有其他子项目，上级自动变为叶子项目,如果存
    * 在子项目，不允许删除
    * 如果此项目已经存在于权限表中不允许删除
	*/
  	public long  delete(long id) throws BudgetException
  	{   
  		long systemID=-1;
  		long parentID=-1;
  		long templong=-1;
  		long retlong=-1;
  		String itemNo="";
  		String puanduan="0";
  		Connection transConn = null;//新建连接
  		BudgetTempletInfo info=new BudgetTempletInfo();
  		BudgetTempletOperation oper=new BudgetTempletOperation();
  		
  		try{
  			try{
    			transConn =Database.getConnection();
    			transConn.setAutoCommit(false);
            }catch (Exception e){
                throw new ITreasuryDAOException("数据库初使化异常发生", e);
            }
            Budget_TempletDAO dao =new Budget_TempletDAO("Budget_Templet",transConn);
	  		info=oper.findByID(id);
	  		itemNo=info.getItemNO();
	  		systemID=info.getBudgetSystemID();
	  		parentID=info.getParentItemID();
	  		//判断此项目是否已经存在于项目权限里，如存在不能删除
	  		Budget_ItemPrivilegeDAO privdao=new Budget_ItemPrivilegeDAO();
	  		BudgetItemPrivilegeInfo privinfo=new BudgetItemPrivilegeInfo();
	  		privinfo.setBudgetItemID(id);
	  		privinfo.setStatusID(1);
	  		Collection privCol=privdao.findByCondition(privinfo);
	  		if(!privCol.isEmpty()){
	  			puanduan="1";//不允许删除
  				retlong=-6;	  			
	  		}
	  		//判断此项目是否存在别的项目公式里，如果存在不允许删除
	  		Collection col=oper.findAll(systemID);
	  		for(Iterator iter=col.iterator();iter.hasNext();){
	  			BudgetTempletInfo searInfo=(BudgetTempletInfo)iter.next();
	  			if(searInfo.getItemFormula().indexOf(String.valueOf(itemNo))>-1){
	  				puanduan="1";//不允许删除
	  				retlong=-4;
	  			}
	  		}
	  		//检查要删除的项目是否还有子项目（如果有不能删除）
	  		templong=dao.checkOtherItem(id);
	  		if(templong!=-1 && templong>=1){	 
	  			puanduan="1";//不允许删除
	  			retlong=-5;
	  		}
	  		//没有父项目且不在别的项目的计算公式里
	  		if(puanduan.equals("0")){
	  			dao.delete(id);	
	  			retlong=id;
	  		//检查父项目是否有其他的子项，如没有父项目自动变为叶子项目
	  			templong=dao.checkOtherItem(parentID);
		  		if(templong!=-1 && templong==1){	 
		  			BudgetTempletInfo modifyinfo=new BudgetTempletInfo();
		  			modifyinfo.setId(parentID);
		  			modifyinfo.setIsLeaf(1);
		  			modifyinfo.setIsReadOnly(1);
		  			dao.update(modifyinfo);
		  		}	  			
	  		}
	  		try{
                if (transConn != null){
                    transConn.commit();
					transConn.close();
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
					transConn.close();
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
    	
  	  return retlong;
    }
 /**
  *   根据id查找某一项目详细信息
  */
    public BudgetTempletInfo findByID(long id) throws BudgetException {
    	BudgetTempletInfo info=new BudgetTempletInfo();
    	Budget_TempletDAO dao=new Budget_TempletDAO();
    	try{    		
    		info=(BudgetTempletInfo)dao.findByID(id,(new BudgetTempletInfo()).getClass());
    	}catch(Exception ex){
    		ex.printStackTrace();
    		throw new BudgetException();
    	}
        return info;
    }
	/**
	* 根据体系id查找体系内所有项目（要求按照项目编号排序）
	*/
	public Collection findAll(long ID) throws BudgetException
    {
		PreparedStatement ps = null;
        Connection conn = null;
        StringBuffer sbSQL = null;
        ResultSet rs = null;        
        String sResult = " ";
		Collection coll=new ArrayList();
		try{
			conn = Database.getConnection();
            sbSQL = new StringBuffer();
            sbSQL.append(" select id,budgetsystemid,itemno,itemname,itemlevel  from budget_templet ");
            sbSQL.append(" where budgetsystemid="+ID+"");                     
            sbSQL.append(" and statusid=1 order by itemno");
            Log.print(sbSQL.toString());
            ps = conn.prepareStatement(sbSQL.toString());
            rs = ps.executeQuery();             
            while (rs.next()){               	
            	BudgetTempletInfo tmpInfo1 = new BudgetTempletInfo();
            	tmpInfo1.setId(rs.getLong("id"));
            	tmpInfo1.setBudgetSystemID(rs.getLong("budgetsystemid"));
            	tmpInfo1.setItemNO(rs.getString("itemno"));
            	tmpInfo1.setItemName(rs.getString("itemname"));
            	tmpInfo1.setItemLevel(rs.getLong("itemlevel"));
            	coll.add(tmpInfo1);            	
            }
		}catch(Exception ex){
			ex.printStackTrace();
			throw new BudgetException();
		}		
		finally
		{
		    try {
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
		    } catch (SQLException e) {
                e.printStackTrace();
            }
		}		
	    return coll;
    }
	/**
	* 校验公式的正确性，基本原则：A项目公式中包含B项目，B项目的公式不允许再包括A项目
	*/
	public boolean checkItemFormula(String itemNo,Collection col,long maxlevel) throws BudgetException
	{
		Budget_TempletDAO dao=new Budget_TempletDAO();
	    return dao.checkItemFormula(itemNo,col,maxlevel);
	}
	
	
}