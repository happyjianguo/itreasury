package com.iss.itreasury.fcinterface.bankportal.privilegemgt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.iss.itreasury.fcinterface.bankportal.constant.BooleanValue;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dao.DataPrivilegeDAO;
import com.iss.itreasury.fcinterface.bankportal.privilegemgt.dataentity.DataPrivilegeInfo;
import com.iss.itreasury.fcinterface.bankportal.sysframe.exp.system.SystemException;
import com.iss.itreasury.fcinterface.bankportal.util.DAOFactory;
import com.iss.itreasury.fcinterface.bankportal.util.Env;
import com.iss.itreasury.fcinterface.bankportal.util.SessionMng;

public class DataPrivilegeUtil
{	
	private static HashMap hmPrivilege = new HashMap();
	public static String processSQL(String querySQL,SessionMng sessionMng) throws SystemException
	{
		try
		{
			if(querySQL == null || sessionMng == null)
			{
				return querySQL;
			}			
			String strResult = querySQL;			
			//如果系统配置项需要控制数据权限时，则才做下面的工作
			if(Env.getEnvConfigItem(Env.isNeedDataPrivilege).equals("true"))
			{
			  DataPrivilegeDAO dataPrivilegeDAO = (DataPrivilegeDAO)DAOFactory.getDAOImpl(DataPrivilegeDAO.class, null);
			  if(sessionMng.groupID < 0)
			  {
				  //判断是否传了userID，如果传了则通过userID取groupID
				  sessionMng.groupID = dataPrivilegeDAO.getGroupIDByUserID(sessionMng.userID);
			  }
			  //判断请求的操作类型
			  if(sessionMng.operationType > 0 && sessionMng.groupID > 0)
			  {
				  OperationConfig.Operation operation = OperationConfig.getOperation(sessionMng.operationType);
				  String strPostfix = "_" + sessionMng.groupID + "_" + operation.getType();
			      //判断用户组该操作类型是否授权，从授权设置表中查找是否有对应记录，如果没有则抛没有授权异常
				  String strIsAuth = (String)hmPrivilege.get(strPostfix);
				  if(strIsAuth == null)
				  {
					  DataPrivilegeInfo privilegeInfo = new DataPrivilegeInfo();
					  privilegeInfo.setUserGroupID(sessionMng.groupID);
					  privilegeInfo.setOperationType(operation.getType());
					  
					  Collection colPrivilegeInfo = dataPrivilegeDAO.findByCondition(privilegeInfo,null);
					  if(colPrivilegeInfo != null && colPrivilegeInfo.size()>0)
					  {
						  hmPrivilege.put(strPostfix,strPostfix);
						  strIsAuth = strPostfix;
					  }
					  colPrivilegeInfo = null;
					  privilegeInfo = null;
				  }
		          //如果授权了，则把操作类型定义的表替换成相应的视图
				  if(strIsAuth != null)
				  {					  					  
					  PatternCompiler compiler = new Perl5Compiler();
					  PatternMatcher matcher = new Perl5Matcher();
					  //替换办事处表
				  	  String strPattern = "(^|[\\s,]{1})(office)([\\s,.]{1}|$)";
					  Pattern pattern = compiler.compile(strPattern,Perl5Compiler.CASE_INSENSITIVE_MASK);//大小写不敏感					  
				      StringBuffer strBufTemp = new StringBuffer();
				      int begin = -1;	        
				      int curPos = 0;	
					  while(matcher.contains(strResult,pattern))
					  {
						  MatchResult result = matcher.getMatch();
						  if(result.group(2) != null)
						  {
						      begin = result.beginOffset(2);
							  strBufTemp.append(strResult.substring(0, begin));
							  String temp = result.group(2);
							  //替换的视图名是：表名_用户组ID_操作类型
							  temp = temp + strPostfix;
							  strBufTemp.append(temp);
							  curPos = result.endOffset(2);
						  }						  
						  strResult = strResult.substring(curPos, strResult.length());
					  }
					  strBufTemp.append(strResult);
					  strResult = strBufTemp.toString();
					  
					  //替换账户表
					  if(operation.getIsNeedAccount() == BooleanValue.TRUE)
					  {
						  strPattern = "(^|[\\s,]{1})(bs_bankaccountinfo)([\\s,.]{1}|$)";
						  pattern = compiler.compile(strPattern,Perl5Compiler.CASE_INSENSITIVE_MASK);//大小写不敏感					  
					      strBufTemp = new StringBuffer();
					      begin = -1;	        
					      curPos = 0;	
						  while(matcher.contains(strResult,pattern))
						  {
							  MatchResult result = matcher.getMatch();
							  if(result.group(2) != null)
							  {
							      begin = result.beginOffset(2);
								  strBufTemp.append(strResult.substring(0, begin));
								  String temp = result.group(2);
								  //替换的视图名是：表名_用户组ID_操作类型
								  temp = temp + strPostfix;
								  strBufTemp.append(temp);
								  curPos = result.endOffset(2);
							  }							  
							  strResult = strResult.substring(curPos, strResult.length());
						  }
						  strBufTemp.append(strResult);
						  strResult = strBufTemp.toString();
					  }			
					  //替换其他表
					  Collection colTable = operation.getTables();
					  if(colTable != null)
					  {
						  Iterator itTable = colTable.iterator();
						  while(itTable.hasNext())
						  {
							  strBufTemp = new StringBuffer();
							  String tableName = ((OperationConfig.Table)itTable.next()).getName();							  
							  strPattern = "(^|[\\s,]{1})(" + tableName + ")([\\s,.]{1}|$)";							  
							  pattern = compiler.compile(strPattern,Perl5Compiler.CASE_INSENSITIVE_MASK);//大小写不敏感						      
						      begin = -1;	        
						      curPos = 0;	
							  while(matcher.contains(strResult,pattern))
							  {
								  MatchResult result = matcher.getMatch();
								  if(result.group(2) != null)
								  {
								      begin = result.beginOffset(2);
								      strBufTemp.append(strResult.substring(0, begin));
									  String temp = result.group(2);
									  if(temp.equalsIgnoreCase("bs_accountpropertyonesetting"))
						    		  {
										  temp = "bs_acctpropertyone";
						    		  }
					    			  else
					    			  {
					    				  if(temp.equalsIgnoreCase("bs_accountpropertytwosetting"))
					    				  {
					    					  temp = "bs_acctpropertytwo";
						    			  }
						    			  else
						    			  {
						    				  if(temp.equalsIgnoreCase("bs_accountpropertythreesetting"))
						    				  {
						    					  temp = "bs_acctpropertythree";
						    				  }						    				  
						    			  }
					    			  }
									  temp = temp + strPostfix;
									  strBufTemp.append(temp);
									  curPos = result.endOffset(2);
								  }								  
								  strResult = strResult.substring(curPos, strResult.length());
							  }
							  strBufTemp.append(strResult);
							  strResult = strBufTemp.toString();
						  }
					  }						
				  }
				  else
				  {
					  throw new Exception("用户没有授权，不能执行此操作");
				  }
			  }
			}
			return strResult;
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new SystemException(e.getMessage(),e);
		}
	}	
	
	public static void main(String[] args)
	{
//		SessionInfo sessionInfo = new SessionInfo();
//		sessionInfo.setUserGroupID(8);
//		sessionInfo.setRequestURL("/account/view/v030.jsp");
//		String strSQL = "select * from abcbs_bankaccountInfo,bs_bankaccountInfo.a=bs_clientsetting";
//		try
//		{
//			System.out.println(processSQL(strSQL,sessionInfo));
//		} catch (SystemException e)
//		{
//			e.printStackTrace();
//		}
	}
}
