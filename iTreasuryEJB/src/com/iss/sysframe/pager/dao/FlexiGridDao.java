package com.iss.sysframe.pager.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.sysframe.util.DataFormat;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.tools.PagerTools;

public class FlexiGridDao extends ITreasuryDAO {

	private long countNum = 0;
	
	public long getCountNum()
	{
		return countNum;
	}

	public List getResultList(long startNum, long finishNum, String sql, String order, String orderMark, PagerInfo pagerInfo,String pfFlag) throws Exception
	{
		StringBuffer strSQL = null;
		List returnList = null;
		
		try
		{
			/*----------------- init DAO --------------------*/
			try {
				if(sql.toLowerCase().indexOf("ct_cus_fmbusiness")>0){
					initKingDeeDAO(1,1);
				}else{
					initDAO();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append(" select count(*) countNum from ("+ sql +")");
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	
				while (transRS.next())
				{
					countNum = transRS.getLong(1);
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}
		
			try {
			//所有结果的合计统计
			
				//合计
				String strUsertext = null;//存放	pagerInfo中usertext的内容
				String[] singleTexts=null;//存放pagerInfo中usertext的内容按“;”分割后的结果集
				
				String displayName = "";//存放汇总的字段
				String m_sbWhere = "";//汇总的查询条件
				boolean formatAmount = true;//是否需要对金额格式化
					
				if( pagerInfo.getUsertext()!=null && !pagerInfo.getUsertext().equals("")){
					strUsertext = pagerInfo.getUsertext();
					if(strUsertext.indexOf("{")>0&&strUsertext.indexOf("}")>strUsertext.indexOf("{")){
	
						singleTexts=strUsertext.split(";");
						int singleTextNum=singleTexts.length; //存放pagerInfo中usertext的内容按“;”分割后的结果的数目;
						
						StringBuffer textBuffer= new StringBuffer(); 
						
						for(int i=0;i<singleTextNum;i++){
							
							displayName = singleTexts[i].substring(singleTexts[i].indexOf("{") + 1, singleTexts[i].indexOf("}"));
							
							m_sbWhere = "";
							if(singleTexts[i].indexOf("[")>0&&singleTexts[i].indexOf("]")>singleTexts[i].indexOf("[")){
								m_sbWhere = singleTexts[i].substring(singleTexts[i].indexOf("[") + 1, singleTexts[i].indexOf("]"));
							}
							
							formatAmount = true;
							if(singleTexts[i].indexOf("#")>0){
								if(singleTexts[i].substring(singleTexts[i].indexOf("#") + 1, singleTexts[i].length()).equalsIgnoreCase("N")){
									formatAmount = false;
								}
							}
							
				    		strSQL = new StringBuffer();
				    		
			    			strSQL.append("select sum("+ displayName+") allSum_"+ i +" from ("+ sql +")" + m_sbWhere);
							
							prepareStatement(strSQL.toString());
				        	this.executeQuery();
				        	
				        	double dataTotal = 0.00;
							if (transRS.next())
							{
								//用于存放汇总的结果
								dataTotal = transRS.getDouble("allSum_"+ i);
								
							}
							if(formatAmount){
								singleTexts[i] = "<b>"+singleTexts[i].substring(0, singleTexts[i].indexOf("{"))+"</b>："+DataFormat.formatAmount(dataTotal,2);
							}else{
								singleTexts[i] = "<b>"+singleTexts[i].substring(0, singleTexts[i].indexOf("{"))+"</b>："+DataFormat.toString(dataTotal);
							}
														
							if(i>1&&(i)%6==0){
								textBuffer.append("<br>&nbsp;&nbsp;");
							}else{
								textBuffer.append("&nbsp;&nbsp;");
							}
							textBuffer.append(singleTexts[i]);
						}

						pagerInfo.setUsertext(textBuffer.toString());
					}
					
				}
				/**
				 * 对于自定义汇总的处理方式 add by xiangzhou 20120806
				 */
				else if(pagerInfo.getExtTotalClazz()!=null && pagerInfo.getExtTotalMothodName()!=null){
					
					//查询全部记录
					strSQL = new StringBuffer();
					strSQL.append(" select * from ("+ sql +")");
					prepareStatement(strSQL.toString());
		        	this.executeQuery();
					
					Class clazz = pagerInfo.getExtTotalClazz();
					String methodName = pagerInfo.getExtTotalMothodName();
					
					Method method = null;
					Method _method[] = clazz.getMethods();
					
					for(int i=0;i<_method.length;i++){
						if(_method[i].getName().equals(methodName)){
							method = _method[i];
							break;
						}
					}
					
					if(Modifier.isStatic(method.getModifiers())){
						if(pagerInfo.getTotalParams()!=null)
							returnList = (ArrayList)method.invoke(null, new Object[]{transRS, pagerInfo.getTotalParams()});
						else
							returnList = (ArrayList)method.invoke(null, new Object[]{transRS});
					}else{
						if(pagerInfo.getTotalParams()!=null)
							returnList = (ArrayList)method.invoke(clazz.newInstance(), new Object[]{transRS, pagerInfo.getTotalParams()});
						else
							returnList = (ArrayList)method.invoke(clazz.newInstance(), new Object[]{transRS});
					}
					
					StringBuffer textBuffer= new StringBuffer(); 
					
					for(int i=0;i<returnList.size();i++){
						
						strUsertext = returnList.get(i).toString();
						strUsertext = "<b>"+strUsertext.substring(0, strUsertext.indexOf("{"))+"</b>："+strUsertext.substring(strUsertext.indexOf("{")+1, strUsertext.indexOf("}"));
						if(i>1&&(i)%pagerInfo.getRowShowNum()==0){
							textBuffer.append("<br>&nbsp;&nbsp;");
						}else{
							textBuffer.append("&nbsp;&nbsp;");
						}
						textBuffer.append(strUsertext);
					}
					
					pagerInfo.setUsertext(textBuffer.toString());
					
				}
				
			}
			catch (Exception e) {
				if( e instanceof InvocationTargetException) {
					
					InvocationTargetException e1 = (InvocationTargetException)e;
					throw new Exception(e1.getTargetException().getMessage());
				}else{
					e.printStackTrace();
					throw e;
				}
			}
			
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append("select * from (");
				strSQL.append("  select t.*, rownum as rownum1 from (");
				strSQL.append("    select * from (" + sql + ")");
				if(order != null && !order.equals("")){
					strSQL.append("    order by "+ order + " " + orderMark);
				}
				strSQL.append("  ) t where 1 = 1 " );
				strSQL.append(") where rownum1 > " + startNum + " and rownum1 <= " + finishNum);
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	returnList = PagerTools.convertResultSetToJSONListEx(transRS,pagerInfo,pfFlag);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}

			 /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		finally {
			finalizeDAO();
		}
		return returnList;
	}
	
	/**
	 * 
	 * @param sql
	 * @param order
	 * @param orderMark
	 * @param pagerInfo
	 * @return
	 * @throws Exception
	 */
	public List getAllResultList(String sql, String order, String orderMark, PagerInfo pagerInfo,String pfFlag) throws Exception
	{
		StringBuffer strSQL = null;
		List returnList = null;
		
		try
		{
			/*----------------- init DAO --------------------*/
			try {
				initDAO();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append(" select count(*) countNum from ("+ sql +")");
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	
				while (transRS.next())
				{
					countNum = transRS.getLong(1);
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}
			
			try {
				//查询总记录
				strSQL = new StringBuffer();
				//strSQL.append("select * from (");
				strSQL.append("  select t.*, rownum as rownum1 from (");
				strSQL.append("    select * from (" + sql + ")");
				if(order != null && !order.equals("")){
					strSQL.append("    order by "+ order + " " + orderMark);
				}
				strSQL.append("  ) t where 1 = 1 ");
				//strSQL.append("  ) t where rownum<= " + finishNum);
				//strSQL.append(") where rownum1 > " + startNum);
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	returnList = PagerTools.convertResultSetToJSONListEx(transRS, pagerInfo,pfFlag);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}

			 /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		finally {
			finalizeDAO();
		}
		return returnList;
	}
	
	public List getResultList(PagerInfo pagerInfo,Map paramMap) throws Exception
	{
		StringBuffer strSQL = null;
		List returnList = null;

		
		try
		{
			//获取查询相关信息
			String sql = pagerInfo.getSqlString();
			long page = Integer.parseInt((String)paramMap.get("page"));
			long lRp = Integer.parseInt((String)paramMap.get("lRp"));
			String order = (String)paramMap.get("order");
			String orderMark = (String)paramMap.get("orderMark");	
			long startNum = (page-1)*lRp;// 计算查询开始数据下标
			long finishNum = startNum + lRp;
			
			/*----------------- init DAO --------------------*/
			try
			{
				initDAO();
			}catch(Exception e)
			{
				e.printStackTrace();
				throw new Exception("创建连接时异常",e);
			}
			/*----------------- end DAO --------------------*/
		
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append(" select count(*) countNum from ("+ sql +")");
				//System.out.println("放大镜查询sql="+strSQL.toString());
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
				while (transRS.next())
				{
					countNum = transRS.getLong(1);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}
			
			try {
				//查询总记录
				strSQL = new StringBuffer();
				strSQL.append("select * from (");
				strSQL.append("  select t.*, rownum as rownum1 from (");
				strSQL.append("    select * from (" + sql + ")");
				if(order != null && !order.equals("")){
					strSQL.append("    order by "+ order + " " + orderMark);
				}
				strSQL.append("  ) t where rownum<= " + finishNum);
				strSQL.append(") where rownum1 > " + startNum);
				//System.out.println("放大镜查询sql="+strSQL.toString());
				prepareStatement(strSQL.toString());
	        	this.executeQuery();
	        	returnList = PagerTools.convertResultSetToJSONListEx(transRS,pagerInfo,"false");
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new Exception("查询数据库时异常",e);
			}

			 /*----------------finalize Dao-----------------*/
		    try {
		        finalizeDAO();
		    }
		    catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("关闭连接时异常",e);
		    }
		    /*----------------end of finalize---------------*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		finally {
			finalizeDAO();
		}
		return returnList;
	}
	
	/**
	 * 获取记录总条数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public long getRowNum(String sql) throws Exception
	{
		long RowNum = 0;
		StringBuffer strSQL = null;
		/*----------------- init DAO --------------------*/
		try
		{
			initDAO();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("创建连接时异常",e);
		}
		/*----------------- end DAO --------------------*/
		try {
			//查询总记录
			strSQL = new StringBuffer();
			strSQL.append(" select count(*) countNum from ("+ sql +")");
			prepareStatement(strSQL.toString());
        	this.executeQuery();
			while (transRS.next())
			{
				RowNum = transRS.getLong(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询数据库时异常",e);
		}
		return RowNum;
	}
	
	
}
