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
 * ����DAO������ͨ�ò��������¼��裺 <p>
 * 1.���ݿ��ֶ�����DataEntity��������һһ��Ӧ����ͬ <p>
 * 2.���б�������DataEntity�̳г�����SECBaseDataEntity<p>
 * 3.���в���������������ݿ���������ΪID��������long<p>
 * 
 * ������������ϼ����������������Զ�����ֲ�����������Ҫ�̳�SecuritiesDAO��<p>
 * 1.���ڲ�����ʼǰ����initDAO<p>
 * 2.�ڽ���ǰʹ��finalizeDAO<p>
 * 3.�����в���PrepareStatement��ResultSetֱ�ӽ��в����������ǵ����������ɸ���ά��ʹ��SecuritiesDAO����ķ�����<p> 
 * PreparedStatement prepareStatement(String sql) 		<p>
 * ResultSet��executeQuery()                                    <p>
 * void	 	 executeUpdate()                                <p>
 * �����ǽ��в���<p>
 * 
 * ���ֻ���setPrepareStatementByDataEntity��<p>
 *getDataEntityFromResultSet��������������������������ز���ʵ�ָ÷���������ʹ��ͨ�õ����ݿ��������<p>
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
	 * ���ݿ��߼�ɾ������
	 * @param id������
	 * @param 
	 * @return
	 * @throws ITreasuryDAOException
	 */	
	public void delete(long id)  throws ITreasuryDAOException{
		//To be modify the delete status defined in Constant
		updateStatus(id, SECConstant.NoticeFormStatus.CANCELED);
		
	}

	/**
	 * ���ݿ���²�������
	 * @param id������
	 * @param statusID ��Ҫ���µ���״̬
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
			throw new ITreasuryDAOException("״̬�����쳣",e);
		}
		
	}
	/**
	 * ������ݿ����Ƿ����йؼ����ظ��ļ�¼
	 * @param arr ��Ҫ�˶Ե������ֶμ��ϣ����ذ���ID��
	 * @return		
	 * @throws Exception
	 */
	public boolean checkDuplicate(ITreasuryBaseDataEntity info,ArrayList arrFieldsNames) throws SecuritiesException{
		boolean isDuplicate=false;
		StringBuffer bufWhere=new StringBuffer();
		String[] dataType = {"double","long","java.lang.String","java.sql.Timestamp"};			//֧�ֵ���������

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
									throw new Exception("��֧��"+strReturnType+"�����ֶε��ظ���¼�Ƚ�");
								}							
							}					
						}
						catch (IllegalAccessException e){				
							throw new Exception("����ظ���¼ʱ����",e);
						}
						catch(InvocationTargetException e){
							throw new Exception("����ظ���¼ʱ����",e);
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
