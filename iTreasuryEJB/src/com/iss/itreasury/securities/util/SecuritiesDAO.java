package com.iss.itreasury.securities.util;



import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.securities.exception.*;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.ITreasuryException;


/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2003
 * Company:             iSoftStone
 * @author             yehuang 
 * @version
 *  Date of Creation    2004-2-26
 */
/**
 * 以下DAO的所有通用操作有以下假设： <p>
 * 1.数据库字段名与DataEntity中属性名一一对应且相同 <p>
 * 2.所有被操作的DataEntity继承抽象类SECBaseDataEntity<p>
 * 3.所有操作假设操作的数据库表的主键名为ID且类型是long<p>
 * 
 * 如果不符合以上假设的特殊操作，请自定义各种操作，但仍需要继承SecuritiesDAO，<p>
 * 1.并在操作开始前调用initDAO<p>
 * 2.在结束前使用finalizeDAO<p>
 * 3.子类中不对PrepareStatement和ResultSet直接进行操作，即它们的生命周期由父类维护使用SecuritiesDAO定义的方法：<p> 
 * PreparedStatement prepareStatement(String sql) 		<p>
 * ResultSet　executeQuery()                                    <p>
 * void	 	 executeUpdate()                                <p>
 * 对它们进行操作<p>
 * 
 * 如果只针对setPrepareStatementByDataEntity或<p>
 *getDataEntityFromResultSet有特殊操作，可以在子类中重载并且实现该方法而继续使用通用的数据库操作方法<p>
 * 				
 * 
 * 		
 */
public abstract class SecuritiesDAO extends ITreasuryDAO{
	
	public SecuritiesDAO(String tableName){
		super(tableName);
	}
	
	public SecuritiesDAO(String tableName,Connection conn){
		super(tableName,conn);
	}	
	/**
	 * 数据库逻辑删除操作
	 * @param id　　　
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void delete(long id)  throws ITreasuryDAOException{
		//To be modify the delete status defined in Constant
		updateStatus(id, SECConstant.NoticeFormStatus.CANCELED);
		
	}

	/**
	 * 数据库更新操作操作
	 * @param id　　　
	 * @param statusID 需要更新到的状态
	 * @return
	 * @throws ITreasuryDAOException
	 */		
	public void updateStatus(long id, long statusID) throws ITreasuryDAOException{
		try {
			initDAO();
			StringBuffer buffer = new StringBuffer();
			buffer.append("UPDATE \n");
			buffer.append(strTableName);
			buffer.append(" SET STATUSID = " + statusID);
			//TBD: maybe need add update execute date
			buffer.append("\n  WHERE ID = " + id);
			String strSQL = buffer.toString();
			log.debug(strSQL);
			prepareStatement(strSQL);
			executeQuery();
			finalizeDAO();
		} catch (ITreasuryDAOException e) {
			throw new ITreasuryDAOException("状态更新异常",e);
		}
		
	}
	/**
	 * 检查数据库中是否已有关键字重复的记录
	 * @param arr 需要核对的所有字段集合（不必包括ID）
	 * @return		
	 * @throws Exception
	 */
	public boolean checkDuplicate(ITreasuryBaseDataEntity info,ArrayList arrFieldsNames) throws SecuritiesException{
		boolean isDuplicate=false;
		StringBuffer bufWhere=new StringBuffer();
		String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp"};			//支持的数据类型

		BeanInfo beanInfo = null;
		try{
			System.out.println("ffffffffffffffffff");
			System.out.println(info.getClass().getName());
			beanInfo = Introspector.getBeanInfo(info.getClass());	
			System.out.println("ffffffffffffffffff2");
			PropertyDescriptor[] p = beanInfo.getPropertyDescriptors();
			System.out.println("ffffffffffffffffff3"+p.length);
			for (int n=0;n<p.length;n++){
				System.out.println("fffjjjjjjjjjjjjjjjjjjjj");
			
				System.out.println("----p["+n+"].getName() = '"+p[n].getName()+"'");
				System.out.println("----String.valueOf(p["+n+"].getReadMethod().invoke(this,null) = "+String.valueOf(p[n].getReadMethod().invoke(info,new Object[]{})));
				if (p[n].getName().compareToIgnoreCase("class")==0) continue;
				
				for(int i=0;i<arrFieldsNames.size();i++){
					System.out.println("arrFieldsNames.get("+i+")='"+arrFieldsNames.get(i)+"'");
					if (p[n].getName().compareToIgnoreCase((String)arrFieldsNames.get(i))==0){
						try{
							if (p[n].getReadMethod()!=null){
								String strValue = (p[n].getReadMethod().invoke(info,new Object[]{})==null)?"":String.valueOf(p[n].getReadMethod().invoke(info,new Object[]{}));
								String strReturnType = p[n].getReadMethod().getReturnType().getName();
								
								if( strReturnType.equals(dataType[0]) || strReturnType.equals(dataType[1])){//parameter type is double
									bufWhere.append(p[n].getName()+" = "+strValue +" and ");
								}							
								else if(strReturnType.equals(dataType[2])){			//parameter type is String
									bufWhere.append(p[n].getName()+" = '"+strValue +"' and ");
								}
								else{
									throw new Exception("不支持"+strReturnType+"类型字段的重复记录比较");
								}							
							}					
						}
						catch (IllegalAccessException e){				
							throw new Exception("检查重复记录时出错",e);
						}
						catch(InvocationTargetException e){
							throw new Exception("检查重复记录时出错",e);
						}			
					}			
				}		
			}
			System.out.println("ffffffffffffffffff4");
			if (bufWhere.length()>0){
				String where=bufWhere.substring(0,(bufWhere.length()-4));
				String strSql="select * from "+super.strTableName+" where "+where;
				System.out.println("eeeeeeeeeeeeeeeeeeeeeee strSql = "+strSql);
				initDAO();
				prepareStatement(strSql);
				System.out.println("ffffffffffffffffff53");
				executeQuery();
				
				System.out.println("ffffffffffffffffff5");
				if(super.transRS!=null && super.transRS.next()){
					System.out.println("ddddddddddduplicate");
					isDuplicate=true;
				}
				finalizeDAO();
			}
		
		}	
		catch(Exception ex){
			ex.printStackTrace();
			throw new SecuritiesException("sec_E151",ex.getMessage(),ex);
		}		
		return isDuplicate;
	}

}
