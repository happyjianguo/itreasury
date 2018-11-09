/*
 * Created on 2006-12-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.evoucher.setting.dao;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author boxu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PrintDAO extends ITreasuryDAO
{
	public PrintDAO(String tableName){
		super(tableName);
	}
	public PrintDAO(String tableName,Connection conn){
		super(tableName,conn);
	}
	public PrintDAO(){

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
		updateStatus(id, 0);
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
	public boolean checkDuplicate(ITreasuryBaseDataEntity info,ArrayList arrFieldsNames) throws Exception{
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
			throw new Exception();
		}		
		return isDuplicate;
	}
	
}
