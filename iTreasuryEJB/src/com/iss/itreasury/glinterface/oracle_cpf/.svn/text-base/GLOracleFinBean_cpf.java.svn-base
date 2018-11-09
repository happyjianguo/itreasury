/*
 * Created on 2004-2-11
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.glinterface.oracle_cpf;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


import com.iss.itreasury.closesystem.CloseSystemMain;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;

;
/**
 * @author yychen
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLOracleFinBean_cpf extends GLExtendBaseBean
{
	

    /*
     * @author yychen GLSystemBaseBean.postGLVoucher()：导出会计凭证 参数：Collection
     * collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
     * 返回值：boolean bIsSuccess:是否成功； 功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。 流程描述： l
     * 调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件； l
     * 调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统； l 返回是否成功!
     */
    public Collection postGLVoucher(Collection collGlVoucher) throws Exception
    {
        try
        {
			//将办事处和币种设置为-1以上它各不到 日期为空
            postGLVoucherToOracleFinCPF(collGlVoucher,-1,-1,null);
            triggerPostGLVoucherCPF(-1,-1);
            collGlVoucher = getPostGlVoucherBackInfo(collGlVoucher,-1,-1);
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }
    
    /*
     * @author yychen GLSystemBaseBean.postGLVoucher()：导出会计凭证 参数：Collection
     * collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
     * 返回值：boolean bIsSuccess:是否成功； 功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。 流程描述： l
     * 调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件； l
     * 调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统； l 返回是否成功!
     */
    public Collection postGLVoucher(Collection collGlVoucher,long lOfficeID,long lCurrencyID,Timestamp date) throws Exception
    {
        try
        {
        	Log.print("*************中油ORACLE接口:***********导出信息处理***开始*************");
        	postGLVoucherToOracleFinCPF(collGlVoucher,lOfficeID,lCurrencyID,date);			//导出内部数据到中油ORACLE接口
            triggerPostGLVoucherCPF(lOfficeID,lCurrencyID);									//触发器更新外部数据的状态
            collGlVoucher = getPostGlVoucherBackInfo(collGlVoucher,lOfficeID,lCurrencyID);	//导出，触发接收后得到更新后的凭证集合
            Log.print("*************中油ORACLE接口:***********导出信息处理***结束*************");
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    //有则该线程一直等待
    private static void waitOracleFinish(Connection conn) throws Exception
	{
    	long lPostCount=0;
    	while (checkNotFinish(conn))
    	{
            lPostCount++;
            if (lPostCount > CloseSystemMain.getExportMaxSecond())
            {
            	//循环6000次，此时就把状态设为“失败”
                throw new Exception("time expired");
            }    		
    		Thread.currentThread().sleep(2000);    		
    	}
	}
    //检查有没有状态为new的数据
    private static boolean checkNotFinish(Connection conn) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL="";
		boolean ret=false;
    	
    	try {
			
			strSQL="select * from GL_Interface where user_je_source_name = ? and status=?";
			ps=conn.prepareStatement(strSQL);
			ps.setString(1,"电子商务系统");
			ps.setString(2,"NEW");
			
			rs=ps.executeQuery() ;
			if ( rs.next())
			{
				ret=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null)
			{
				rs.close(); 
				rs=null;
			}
			if(ps!=null)
			{
				ps.close();
				ps=null;
			}
		}
		return ret;
	}

    //主方法测试
	public static void main(String args[])
	{
		try {
			Connection conn=conn=GLOracleFinBean_cpf.getOracleConnection(1,1,1);
			boolean ret=checkNotFinish(conn);
			System.out.println(ret);
			conn.close();
			conn=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
    //按照指定的数据格式输出到Oracle GL (CPF)
    private static long postGLVoucherToOracleFinCPF(Collection collVoucher,long lOfficeID,long lCurrencyID,Timestamp tsDate) throws Exception
    {
    	long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String sbRecord = "";
		try
		{
			Log.print("++++++++++启动中油OracleCPF程序++++++++开始导出！++++++++++");
			if (collVoucher != null)
			{
				System.out.println("------------xxxxxxxxxxxxxxx");
				/////////11111111111111111
	        	conn=GLOracleFinBean_cpf.getOracleConnection(lOfficeID,lCurrencyID,1);	//运用自己的私有方法根据币种办事处得到数据库的连接
	            conn.setAutoCommit(false);

	            System.out.println("------------yyyyyyyyyyyyyyyyyy");
	            
	            //检查oracle有没有状态是NEW的记录,如果有,循环
	            waitOracleFinish(conn);
	            
				sbRecord = " delete from GL_INTERFACE where   user_je_source_name = ? ";
				ps = conn.prepareStatement(sbRecord);
				//ps.setTimestamp(1,tsDate);
				ps.setString(1, "电子商务系统");
				ps.executeUpdate();
				Log.print(sbRecord);
				ps.close();
				ps = null;
				//////////////////2222222222222
				Iterator it = collVoucher.iterator();
				int jj=1;
                while (it.hasNext())
                {
                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();	//得到会计凭证信息
                    for (int i = 0; voucher.getList() != null && i < voucher.getList().size(); i++)
                    {
                        GLEntryInfo entryinfo = (GLEntryInfo) voucher.getList().get(i);	//得到会计分录信息
                        OracleGLInfo oracleinfo = new OracleGLInfo();	//oracle中的info(将要导出到oracle表的格式)
                        //--Log.print("=========Oracle:有需要导出的数据!");
                        oracleinfo.status="NEW";
                        
                        	//办事处代号*****************(目前全用沈阳的这个账套:9)
                        oracleinfo.set_of_books_id=GLOracleFinBean_cpf.getSetOfBooksID(lOfficeID);
                       
                    	/////摘要，特殊处理
                        oracleinfo.reference10 = entryinfo.getAbstract();
                        oracleinfo.reference5 = "";
    					//Log.print("**************after reference10**************");
    					if (oracleinfo.reference10 == null)
    						oracleinfo.reference10 = "";
    					////////////	
    					oracleinfo.accounting_date = entryinfo.getExecute();
    					Timestamp tsInterestStart = entryinfo.getInterestStart();	//在后面给Attribute12赋值
    					if (oracleinfo.accounting_date == null)
    						oracleinfo.accounting_date = tsDate;
    					////////得到会计日期并处理
    					boolean bHavaAccountDate = false;
    					Timestamp tsStartAccountDate = tsDate;
    					sbRecord = "SELECT 	SET_OF_BOOKS_ID,PERIOD_NAME,START_DATE,END_DATE  FROM 	GL_PERIOD_STATUSES     WHERE 	CLOSING_STATUS = 'N' and START_DATE <= ? and END_DATE >= ? and  SET_OF_BOOKS_ID = " + oracleinfo.set_of_books_id;
    					ps = conn.prepareStatement(sbRecord);
    					ps.setTimestamp(1, oracleinfo.accounting_date);
    					ps.setTimestamp(2, oracleinfo.accounting_date);
    					rs = ps.executeQuery();
    					//--Log.print(sbRecord);
    					if (rs != null & rs.next())
    					{
    						bHavaAccountDate = true;
    					}
    					rs.close();
    					rs = null;
    					ps.close();
    					ps = null;
    					if (bHavaAccountDate == false)
    					{
    						//////////////如果没有取得会计日期，则取最近的会计日期
    						sbRecord = "SELECT min(START_DATE) START_DATE  FROM 	GL_PERIOD_STATUSES     WHERE 	CLOSING_STATUS = 'N' and START_DATE > ?  and  SET_OF_BOOKS_ID = " + oracleinfo.set_of_books_id;
    						ps = conn.prepareStatement(sbRecord);
    						ps.setTimestamp(1, oracleinfo.accounting_date);
    						rs = ps.executeQuery();
    						//--Log.print(sbRecord);
    						while (rs != null & rs.next())
    						{
    							tsStartAccountDate = rs.getTimestamp(1);
    						}
    						rs.close();
    						rs = null;
    						ps.close();
    						ps = null;
    						////////////////
    						oracleinfo.accounting_date = tsStartAccountDate;
    						oracleinfo.reference5 = "调整账";
    					}
    					//Log.print("**************after accounting_date**************");
    					oracleinfo.currency_code=GLOracleFinBean_cpf.getCurrencyCode(entryinfo.getCurrencyID());
    					oracleinfo.user_je_category_name=GLOracleFinBean_cpf.getUserJeCategoryName(entryinfo.getCurrencyID());
    					
    					oracleinfo.date_created = tsDate;
    					oracleinfo.created_by = -1;
    					ps = conn.prepareStatement("select user_id from fnd_user where user_name = 'INTERFACE' ");
    					rs = ps.executeQuery();
    					//--Log.print(sbRecord);
    					if (rs != null & rs.next())
    					{
    						oracleinfo.created_by = rs.getLong(1);
    					}
    					rs.close();
    					rs = null;
    					ps.close();
    					ps = null;
    					oracleinfo.Actual_flag = "A";
    					oracleinfo.user_je_source_name = "电子商务系统";
    					// Log.print("**************after user_je_source_name**************");
    					
    					oracleinfo.currency_conversion_date = entryinfo.getExecute();
    					oracleinfo.segment1 = "";
    					oracleinfo.segment2 = "";
    					oracleinfo.segment3 = "";
    					oracleinfo.segment4 = "";
    					
    					String sAccountTemp = entryinfo.getSubject(); //得到科目号(m_strAccount)
    					int index = -1;
    					index = sAccountTemp.indexOf(".");
    					if (index > 0)
    					{
    						oracleinfo.segment1 = sAccountTemp.substring(0, index);
    						//--Log.print(index + ",sAccountTemp=" + sAccountTemp);
    						sAccountTemp = sAccountTemp.substring(index + 1);
    						index = sAccountTemp.indexOf(".");
    						//--Log.print(index + ",sAccountTemp=" + sAccountTemp);
    					}
    					if (index > 0)
    					{
    						oracleinfo.segment2 = sAccountTemp.substring(0, index);
    						sAccountTemp = sAccountTemp.substring(index + 1);
    						index = sAccountTemp.indexOf(".");
    					}
    					if (index > 0)
    					{
    						oracleinfo.segment3 = sAccountTemp.substring(0, index);
    						oracleinfo.segment4 = sAccountTemp.substring(index + 1);
    					}
    					//--Log.print("segment1=" + oracleinfo.segment1 + ",segment2=" + oracleinfo.segment2 + ",segment3=" + oracleinfo.segment3 + ",segment4=" + oracleinfo.segment4);
    					oracleinfo.segment5 = "000";
    					oracleinfo.segment6 = "000";
    					//Log.print("**************after segment6**************");  
    					
    					oracleinfo.entered_dr = 0;
    					oracleinfo.entered_cr = 0;
    					//--Log.print("\n\n\n=============借贷方向:"+entryinfo.getDirectionID());
    					//if (entryinfo.getDirectionID() == Notes.CODE_RECORD_GETORPAY_GET)
    					if (entryinfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
    					{
    						oracleinfo.entered_dr = entryinfo.getAmount();
    						//--Log.print("借贷方向get:"+SETTConstant.DebitOrCredit.DEBIT);
    					}
    					else					
    					//if (entryinfo.getDirectionID() == Notes.CODE_RECORD_GETORPAY_PAY)
    					if (entryinfo.getDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
    					{
    						oracleinfo.entered_cr = entryinfo.getAmount();
    						//--Log.print("借贷方向pay:"+SETTConstant.DebitOrCredit.CREDIT);
    					}
    					//--Log.print("=============entered_dr金额:"+oracleinfo.entered_dr);
    					//--Log.print("=============entered_cr金额:"+oracleinfo.entered_cr);
    					oracleinfo.reference1 = "电子商务系统" + DataFormat.getDateString(tsDate);
    					oracleinfo.reference2 = "";
    					oracleinfo.reference3 = "";
    					oracleinfo.reference4 = "电子商务系统" + DataFormat.getDateString(tsDate);
    					//Log.print("**************after reference10**************");
    					
    					//////////////////  Attribute1
    					oracleinfo.attribute2 = "";
    					//它的意思是一个凭证下有多少笔分录信息(个人觉得应该是分录的交易号在凭证的信息下可以得到)
    					//oracleinfo.attribute2 = String.valueOf(glentryinfo.m_lNumber);	//--得不到lNumber信息(在entryinfo)
    					oracleinfo.attribute2 = voucher.getTransNo();
    					oracleinfo.user_currency_conversion_type = "公司";

    					//////多维码		此为中油的需要现金流向的需要而得到的值
    					System.out.println("=====kkf1217得到多维码的值为:"+entryinfo.getMultiCode());
    					oracleinfo.attribute11=entryinfo.getMultiCode();
    					if (oracleinfo.attribute11 == null)
    						oracleinfo.attribute11 = "";
    					
    					//////////////////currency_conversion_rate
    					//////////////////Actual flag
    					//////////////////currency_conversion_rate
    					//////////////////Actual flag
    					/////新增加的东西
    					oracleinfo.Context3 = "";
    					oracleinfo. Attribute12 = null;
    					/*			特殊处理，进入改写  现在是取第二
    					if (oracleinfo.segment2.startsWith("101") || oracleinfo.segment2.startsWith("102") || oracleinfo.segment2.startsWith("132"))
    					{
    						oracleinfo.Context3 = oracleinfo.segment2;
    					}
    					if (oracleinfo.segment2.startsWith("102"))
    					{
    						oracleinfo.Attribute12 = tsInterestStart;
    					}
    					/////602，666等表外科目的处理
    					if (oracleinfo.segment2.startsWith("6"))
    					{
    						oracleinfo.currency_code = "STAT";
    						oracleinfo.user_je_category_name = "CPF_凭证_人民币";
    					}
    					*/
    				if(oracleinfo.segment2!=null && oracleinfo.segment2.length()>=3)
    				{	
    					
    					if (oracleinfo.segment2.substring(0,3).equals("101") || oracleinfo.segment2.substring(0,3).equals("102") || oracleinfo.segment2.substring(0,3).equals("132"))
    					{
    						oracleinfo.Context3 = oracleinfo.segment2;
    					}
    					if (oracleinfo.segment2.substring(0,3).equals("102"))
    					{
    						oracleinfo.Attribute12 = tsInterestStart;
    					}
    					/////602，666等表外科目的处理
    					if (oracleinfo.segment2.substring(0,1).equals("6"))
    					{
    						oracleinfo.currency_code = "STAT";
    						oracleinfo.user_je_category_name = "CPF_凭证_人民币";
    					}
    				}
    					Log.print("第几笔:"+(jj++)+"科目号:"+oracleinfo.segment1+"."+oracleinfo.segment2+"."+oracleinfo.segment3+"."+oracleinfo.segment4+"余额方向:"+entryinfo.getDirectionID()+
						"借方发生额:"+oracleinfo.entered_dr+"贷方发生额:"+oracleinfo.entered_cr+"说明:"+oracleinfo.reference10+"    现金流量:"+oracleinfo.attribute11);
    					 Log.print("**************before insert **************");
						sbRecord =
							"insert into GL_INTERFACE(status,set_of_books_id,accounting_date,currency_code,date_created,created_by,Actual_flag,user_je_category_name,"
								+ "user_je_source_name,currency_conversion_date,"
								+ "segment1,segment2,segment3,segment4,segment5,segment6,"
								+ "entered_dr,entered_cr,"
								+ "reference1,reference2,reference3,reference4,reference5,reference10,"
								+ "attribute2,user_currency_conversion_type,Attribute11,Context3,Attribute12)"
								+ " values('"
								+ oracleinfo.status
								+ "',"
								+ oracleinfo.set_of_books_id
								+ ",?,'"
								+ oracleinfo.currency_code			//--多维码信息没有
								+ "',?," ////5
		+oracleinfo.created_by + ",'" + oracleinfo.Actual_flag + "','" + oracleinfo.user_je_category_name + "','" + oracleinfo.user_je_source_name + "',?,'" /////5
		+oracleinfo.segment1 + "','" + oracleinfo.segment2 + "','" + oracleinfo.segment3 + "','" + oracleinfo.segment4 + "','" + oracleinfo.segment5 + "','" + oracleinfo.segment6 /////6
		+"'," + oracleinfo.entered_dr + "," + oracleinfo.entered_cr /////2
		+",'" + oracleinfo.reference1 + "','" + oracleinfo.reference2 + "','" + oracleinfo.reference3 + "','" + oracleinfo.reference4 + "','" + oracleinfo.reference5 + "','" + oracleinfo.reference10 //////6666666666 
	+"','" + oracleinfo.attribute2 + "','" + oracleinfo.user_currency_conversion_type + "','" + oracleinfo.attribute11 + "','" + oracleinfo.Context3 + "',to_char(?,'yyyy-mm-dd'))"; //4444
					Log.print(sbRecord);
					ps = conn.prepareStatement(sbRecord);
					ps.setTimestamp(1, oracleinfo.accounting_date);
					ps.setTimestamp(2, oracleinfo.date_created);
					ps.setTimestamp(3, oracleinfo.currency_conversion_date);
					//pstemp.setString (4,reference10);
					ps.setTimestamp(4, oracleinfo.Attribute12);
					ps.executeUpdate();
					// Common.log("**************after insert **************");
					ps.close();
					ps = null;
				}//分录循环
                }//凭证循环
                // 在cpf中此时是做为触发器使用，现在则是搬到了其它方法中
                
                //关闭连接
                conn.commit();
                conn.setAutoCommit(true);
                //conn.close();
                //conn = null; 
			}//有凭证记录判断
			Log.print("++++++++++++++启动中油OracleCPF程序++++++++++结束导出！++++++++++");
		}catch (Exception e)
		{
			lDealStatusID = Constant.ShutDownStatus.FAIL;
			e.printStackTrace();
			if (ps != null)
			{
				ps.close();
				ps = null;
			}
			System.out.println("------插入中间表数据时碰到异常，执行回滚事务开始!");
			conn.rollback();
			conn.setAutoCommit(true);
			System.out.println("------插入中间表数据时碰到异常，执行回滚事务结束!");
			throw new RemoteException(e.getMessage());
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
					ps = null;
				}
				if (conn != null)
				{
					conn.setAutoCommit(true);
					conn.close();					
					conn = null;
				}
			}
			catch (Exception e)
			{
				throw new RemoteException("remote exception : " + e.toString());
			}
		}
		return lDealStatusID;
    }
    
    //触发Oracle GL端自动导入凭证的trigger (CPF)
    private static long triggerPostGLVoucherCPF(long lOfficeID,long lCurrencyID) throws Exception
    {
    	long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        StringBuffer sbRecord = new StringBuffer();
        PreparedStatement ps = null;
        Connection conn = null;
        
        try
        {
            //		////启动Oracle程序，开始导出！
            Log.print("+++++++++++++++启动中油OracleCPF程序++++++++++开始触发程序++++++");
            
            //conn = Database.get_jdbc_connection();	//原来是database库里面的jdbc中的Oracle连接
        	conn=GLOracleFinBean_cpf.getOracleConnection(lOfficeID,lCurrencyID,1);	//运用自己的私有方法根据币种办事处得到数据库的连接

            sbRecord.setLength(0);
            
            sbRecord.append(" insert into cpf_submit_request values ('1') ");
            Log.print("----------触发SQL1："+sbRecord);
            ps = conn.prepareStatement(sbRecord.toString());
            ps.executeUpdate();
            ps.close();
            ps = null;
            sbRecord.setLength(0);
            sbRecord.append(" update fnd_concurrent_requests ");
            sbRecord.append(" set NLS_LANGUAGE = 'SIMPLIFIED CHINESE', PRINT_STYLE = 'A4' ");
            sbRecord.append(" where concurrent_program_id = 41907 ");
            sbRecord.append(" and requested_by = 1109 ");
            sbRecord.append(" and NLS_LANGUAGE = 'AMERICAN' ");
            Log.print("----------触发SQL2："+sbRecord);
            ps = conn.prepareStatement(sbRecord.toString());
            ps.executeUpdate();
            ps.close();
            ps = null;
            conn.close();
            conn = null;
            Log.print("+++++++++++++++启动中油OracleCPF程序++++结束触发程序+++++++++");
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            throw e;
        }
        return lDealStatusID;
    }
    /*
     * U850GLBean. getPostGlVoucherBackInfo () 参数：； 返回值：Collection
     * collVoucher:解析EAI返回信息所得到的信息集合； 功能描述：循环等待并解析EAI返回信息； 流程描述： l 循环开始； l
     * 调用U850GLBean .isCompleted()判断EAI是否处理完毕并返回信息； l 如果处理完毕，则调用U850GLBean
     * .resolveGLSubjectXML()，解析返回的信息，循环结束； l 如果没有处理完毕，则继续循环等待； l 返回解析所得的信息集合；
     */
    //得到GLOracleFinBean_cpf导出后的是否成功的信息
    private Collection getPostGlVoucherBackInfo(Collection collGlVoucher,long lOfficeID,long lCurrencyID) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.DOING;
        try
        {
        	Log.print("+++++++++++++++启动中油OracleCPF程序++++开始得到返回更新凭证信息程序+++++++++");
            long lPostCount = 0;
            while (lDealStatusID == Constant.ShutDownStatus.DOING)
            {
            	Log.print("+++++++++++++++开始一次到得到是否成功状态循环+++++++++");
                lPostCount++;
                if (lPostCount > CloseSystemMain.getExportMaxSecond())
                {
                	//循环6000次，此时就把状态设为“失败”
                    lDealStatusID = Constant.ShutDownStatus.FAIL;
                    break;
                }
                //等待两秒钟然后再判断数据是否更成功，主要是怕触发器程序执行的功能没有执行完毕
                Thread.currentThread().sleep(2000);
 
                //11.2  增加检测oracle触发后导出的状态（优化一下）重复二次执行查找返回状态，之间间隔10秒.
                //如果第一次检测失败则执行第二次，如果两次均未成功则还是返回的不成功
                try{
                	System.out.println("++++++++++++++++++++==：系统开始第一次检查Oracle导出成功状态");
                	lDealStatusID = isCompleted(lOfficeID,lCurrencyID);	//招待是否完成的方法
                }catch(Exception e){
                	try {
						System.out.println("++++++++++++++++++++==：系统第一次检查Oracle导出成功状态不成功,等待10秒种天始-----------lDealStatusID:"+lDealStatusID);
						Thread.currentThread().sleep(10000);
						System.out.println("++++++++++++++++++++==：系统开始第二次检查Oracle导出成功状态");
						lDealStatusID = isCompleted(lOfficeID,lCurrencyID);	//招待是否完成的方法
						System.out.println("++++++++++++++++++++==：系统第二次检查Oracle导出成功状态成功！-----------lDealStatusID:"+lDealStatusID);
					} catch (Exception e1) {
	                	System.out.println("++++++++++++++++++++==：系统第二次检查Oracle导出成功状态不成功,等待10秒种天始-----------lDealStatusID:"+lDealStatusID);
	                	Thread.currentThread().sleep(10000);
	                	System.out.println("++++++++++++++++++++==：系统开始第三次检查Oracle导出成功状态");
	                	lDealStatusID = isCompleted(lOfficeID,lCurrencyID);	//招待是否完成的方法
	                	System.out.println("++++++++++++++++++++==：系统第三次检查Oracle导出成功状态成功！-----------lDealStatusID:"+lDealStatusID);
					}
                }
                
                Log.print("+++++++++++++++这一次循环得到的状态为："+lDealStatusID);
            }
            Log.print("+++++++++++++++循环结束得到返回状态为："+lDealStatusID);
            Log.print("++++++++++++++开始设置凭证信息的导出状态！");
            if (collGlVoucher != null)	//其实在上一步传来的有正在处理这个信息，但此时把它忽略了只有成功和失败
            {        
            	int i=1;
                Iterator it = collGlVoucher.iterator();
                while (it.hasNext())
                {
                    GLVoucherInfo voucher = (GLVoucherInfo) it.next();
                    if (lDealStatusID == Constant.ShutDownStatus.SUCCESS)
                    {
                        voucher.setPostStatusID(Constant.GLPostStatus.SUCCESS);
                    }
                    else
                    {
                        voucher.setPostStatusID(Constant.GLPostStatus.FAILED);
                    }
                    Log.print("++++++++++++++更新："+i++);
                }
            }
            Log.print("+++++++++++++结束设置凭证信息的导出状态！");
            Log.print("+++++++++++++++启动中油OracleCPF程序++++结束得到返回更新凭证信息程序+++++++++");
        }
        catch (Exception e)
        {
            throw new RemoteException(e.getMessage());
        }
        return collGlVoucher;
    }

    
    //===============得到总账科目信息		(CPF)
    /* 1.得到本地连接
     * 2.在总账接口配置中对应的办事处币种对应的总账类型不是isoftstone则往下处理，否则任何事情都不做
     * 3.从表gl_code_combinations@cpf_t中执行指规定规则的SQL语句
     * 4.将获得的数据按一定的格式封装到内部财务中的GLSubjectDefinitionInfo里面 
     */
    public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
    {
    	Log.print("进入到得到科目的方法里面！--------开始！!!");
    	Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;        
      
        //得到配置info里面的内容
		GlSettingInfo glSettingInfo=new GlSettingInfo();
		glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID,0);
	
		 //此时得到的是本库连接
    	conn = Database.getConnection();
		try
		{
			if(!glSettingInfo.getGlName().equalsIgnoreCase("ISOFTSTONE")) //说明不是内部
			{
				/////更新科目号
				StringBuffer strSQL = new StringBuffer();
				strSQL.setLength(0);
	            //strSQL.append(" insert into OracleSubject  ");	//ACCOUNT_TYPE需要查找到账户类型(8.30暂时为空)
				strSQL.append(" select gcc.segment1, co.description description1, gcc.account_type account_type, ");
				strSQL.append(" gcc.segment2, ac.description description2,  ");
				strSQL.append(" gcc.segment3, cc.description description3,  ");
				strSQL.append(" gcc.segment4, cu.description description4, ");
				
				strSQL.append(" gcc.segment5, cu.description description5, ");
				
				strSQL.append(" gcc.segment1 || '.' || gcc.segment2 || '.' || gcc.segment3 || '.' || gcc.segment4 sSubject ");
				strSQL.append(" from ");
				strSQL.append(" gl_code_combinations@cpf_t gcc,  ");	//GCC:segment1,segment2,segment3,segment4,sSubject
				strSQL.append(" (  ");
				strSQL.append(" select a.flex_value, c.description  ");
				strSQL.append(" from fnd_flex_values@cpf_t a, fnd_flex_value_sets@cpf_t b, fnd_flex_values_tl@cpf_t c  ");
				strSQL.append(" where a.flex_value_set_id = b.flex_value_set_id  ");
				strSQL.append(" and flex_value_set_name like 'CPF_公司段值_VS1'  ");
				strSQL.append(" and a.flex_value_id = c.flex_value_id  ");
				strSQL.append(" and c.language = 'ZHS'   ");
				strSQL.append(" and a.summary_flag = 'N'   ");
				strSQL.append(" and a.enabled_flag = 'Y'   ");
				strSQL.append("  ) co,   ");	//CO:description description1
				strSQL.append("  (  ");
				strSQL.append("  select a.flex_value, c.description  ");
				strSQL.append("  from fnd_flex_values@cpf_t a, fnd_flex_value_sets@cpf_t b, fnd_flex_values_tl@cpf_t c  ");
				strSQL.append("  where a.flex_value_set_id = b.flex_value_set_id  ");
				strSQL.append("  and flex_value_set_name like 'CPF_会计科目段值_VS1'  ");
				strSQL.append("  and a.flex_value_id = c.flex_value_id  ");
				strSQL.append("  and c.language = 'ZHS'  ");
				strSQL.append("  and a.summary_flag = 'N' ");
				strSQL.append("  and a.enabled_flag = 'Y' ");
				strSQL.append("  ) ac,    ");	//AC:description description2
				strSQL.append("  ( ");
				strSQL.append("  select a.flex_value, c.description ");
				strSQL.append("  from fnd_flex_values@cpf_t a, fnd_flex_value_sets@cpf_t b, fnd_flex_values_tl@cpf_t c ");
				strSQL.append("  where a.flex_value_set_id = b.flex_value_set_id ");
				strSQL.append("  and flex_value_set_name like 'CPF_成本中心段值_VS1' ");
				strSQL.append("  and a.flex_value_id = c.flex_value_id ");
				strSQL.append("  and c.language = 'ZHS' ");
				strSQL.append("  and a.summary_flag = 'N' ");
				strSQL.append("  and a.enabled_flag = 'Y' ");
				strSQL.append("  ) cc, ");		//CC:description description3
				strSQL.append("  (  ");
				strSQL.append("  select a.flex_value, c.description ");
				strSQL.append("  from fnd_flex_values@cpf_t a, fnd_flex_value_sets@cpf_t b, fnd_flex_values_tl@cpf_t c ");
				strSQL.append("  where a.flex_value_set_id = b.flex_value_set_id ");
				strSQL.append("  and flex_value_set_name like 'CPF_客户群段值_VS1' ");
				strSQL.append("  and a.flex_value_id = c.flex_value_id ");
				strSQL.append("  and c.language = 'ZHS' ");
				strSQL.append("  and a.summary_flag = 'N' ");
				strSQL.append("  and a.enabled_flag = 'Y' ");
				strSQL.append("  ) cu ");		//CU:description description4
				strSQL.append("  where ");
				strSQL.append("  gcc.segment1 = co.flex_value ");
				strSQL.append("  and gcc.segment2 = ac.flex_value ");
				strSQL.append("  and gcc.segment3 = cc.flex_value ");
				strSQL.append("  and gcc.segment4 = cu.flex_value ");
				strSQL.append("  and gcc.enabled_flag='Y' ");
				Log.print(strSQL.toString());
				ps = conn.prepareStatement(strSQL.toString());
				rs = ps.executeQuery();
			
				int i=1;
				//往GLSubjectDefinitionInfo往科目信息里添加数据
				while (rs != null && rs.next())		
	            {
	                GLSubjectDefinitionInfo info = new GLSubjectDefinitionInfo();
	                info.setOfficeID(lOfficeID);	//得到的是SessionMng
	                info.setCurrencyID(lCurrencyID);	//得到的是SessionMng里面的
               
	                info.setSegmentCode1(rs.getString("segment1"));
	                info.setSegmentCode2(rs.getString("segment2"));
	                info.setSegmentCode3(rs.getString("segment3"));
	                info.setSegmentCode4(rs.getString("segment4"));
	                info.setSegmentCode5(rs.getString("segment5"));
	                info.setSegmentCode6("");
	                info.setSegmentCode7("");
	                
	                info.setSegmentName1(rs.getString("description1"));
	                info.setSegmentName2(rs.getString("description2"));
	                info.setSegmentName3(rs.getString("description3"));
	                info.setSegmentName4(rs.getString("description4"));
	                info.setSegmentName5("");
	                info.setSegmentName6("");
	                info.setSegmentName7("");
	                //info.setSubjectType(getSubjectType(rs.getString("ACCOUNT_TYPE")));	//按照SQL的逻辑没有取到,陈宇艺说暂时可以存为-1
	                info.setLeaf(true);
	                info.setStatusID(Constant.RecordStatus.VALID);
	                
	                long lDirectionID = SETTConstant.SubjectAttribute.getDirection(getSubjectType(rs.getString("account_type").trim().substring(0,1)));
	                
	                info.setSubjectType(getSubjectType(rs.getString("account_type").trim()));
	                System.out.println("---------------在导入科目定义时得到科目的类型为:"+rs.getString("account_type").trim());
	                
	                //防止里面有为空的字段，如果有任意一个为空则不添加到List集合中.
	                if(info.getSegmentCode1()==null){	                	
	                	continue;
	                }
	                if(info.getSegmentCode2()==null){	//此字段Sett_GlSubjectDefinition不为空
	                	continue;
	                }
	                if(info.getSegmentCode3()==null){
	                	continue;
	                }
	                if(info.getSegmentCode4()==null){
	                	continue;
	                }
	                
	                if(info.getSegmentName1()==null){
	                	continue;
	                }
	                if(info.getSegmentName2()==null){	//此字段Sett_GlSubjectDefinition不为空
	                	continue;
	                }
	                if(info.getSegmentName3()==null){
	                	continue;
	                }
	                if(info.getSegmentName4()==null){
	                	continue;
	                }
	                
	                //保证只导入指定的办事处的科目号(根据办事处ID得到段1的值，然后和别人库中得到的段1进行对比)
	                if(!GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID).equals(info.getSegmentCode1().trim())){
	                	Log.print("------不是本办事处的数据："+info.getSegmentCode1().trim()+"本办事处ID是:"+lOfficeID);
	                	continue;
	                }
	                
	                //10.14 叶飞 因为出现段5的值作特殊处理
	                if(info.getSegmentCode2().trim().equals("10801000000") && info.getSegmentCode5().trim().equals("341")){
	                	Log.print("不添加数据，因为它有段5"+info.getSegmentCode2()+info.getSegmentCode5());
	                }else{
	                	//实际的添加
		                list.add(info);
		                Log.print("****添加了第几条数据:"+i++);
	                }
	            }
				
				Log.print("进入到得到科目的方法里面！--------结束!!！");
	            rs.close();
	            rs = null;
	            ps.close();
	            ps = null;
	            conn.close();
	            conn = null;
			}
        }catch (Exception e)
        {
            Log.print(e.toString());
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception e)
            {
                Log.print(e.toString());
            }
        }
        return (list != null && list.size() > 0 ? list : null);
    
    }
    
    
    
    	
   
	    
	//得到总账科目余额		(CPF)
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		Log.print("进入到得到科目余额的方法里面！--------开始！!!");
    	 //原来的方法
        Connection conn = null;
        ArrayList list = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        long lImportCurrencyID = -1;
        try
        {
        	lImportCurrencyID = lCurrencyID;
            /////更新科目号余额
            conn = Database.getConnection();
        	//conn=GLOracleFinBean_cpf.getOracleConnection(lOfficeID,lCurrencyID);
        	
            StringBuffer strSQL = new StringBuffer();
            strSQL.setLength(0);
            
            //得到账套号 根据officeID的值
            long set_of_books_id=0;
            //在2006年1月1日以前是从汇总账套时导数 2006.1.6  叶飞  2006.1.1以后从各自的账套里导入科目余额
            //set_of_books_id=12;	//从沈阳汇总里面导入科目余额		
            set_of_books_id=GLOracleFinBean_cpf.getSetOfBooksID(lOfficeID);
            
            Log.print("-----------得到的账套号为:"+set_of_books_id+"   对应的officeID为:"+lOfficeID);
            
			/////得到币种对应关系
			String currency_code = "";
			currency_code=GLOracleFinBean_cpf.getCurrencyCode(lImportCurrencyID);
            
			///表外科目处理
			Log.print("***********币种*********" + lImportCurrencyID);
			if (lCurrencyID == 0)
			{
				lCurrencyID =Constant.CurrencyType.RMB;
			}
            
            strSQL.append(" select account,account_type,balance,to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd')," + lOfficeID + "," + lCurrencyID + "  ");
			strSQL.append(" from  ");
			strSQL.append(" (  ");
			strSQL.append(" select glcc.segment1||'.'|| glcc.segment2||'.'|| glcc.segment3||'.'|| glcc.segment4  account ");
			strSQL.append(" ,glk.description  account_type ");
			strSQL.append(" ,(nvl(a.curr_bla,0)+nvl(b.end_balance,0))  balance ");
			strSQL.append(" from ");
			strSQL.append(" (select code_combination_id ");
			strSQL.append("  ,sum(nvl(entered_dr,0)-nvl(entered_cr,0))  curr_bla ");
			strSQL.append(" from gl_je_lines@CPF_T   gjl ");
			strSQL.append(" ,gl_je_headers@CPF_T  gjh ");
			strSQL.append(" where to_char(gjl.effective_date,'yyyymmdd') >=to_char(trunc(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),'month'),'yyyymmdd') ");
			strSQL.append(" and   to_char(gjl.effective_date,'yyyymmdd') <=to_char(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),'yyyymmdd') ");
			strSQL.append(" and   gjl.JE_HEADER_ID = gjh.JE_HEADER_ID ");
			strSQL.append(" and   gjh.CURRENCY_CODE=upper('" + currency_code + "') ");
			strSQL.append(" and   gjl.set_of_books_id=  " + set_of_books_id);
			strSQL.append(" group by gjl.code_combination_id)    a ");
			strSQL.append(" ,(select code_combination_id ");
			strSQL.append("    , SUM(gb.begin_balance_dr-gb.begin_balance_cr+ gb.period_net_dr- gb.period_net_cr) end_balance ");
			strSQL.append(" from 	gl_balances@CPF_T  gb ");
			strSQL.append(" where gb.period_name = to_char(add_months(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),-1),'MM-YYYY') ");
			strSQL.append(" and   gb.CURRENCY_CODE =upper('" + currency_code + "') ");
			strSQL.append("  AND   gb.set_OF_BOOKS_ID= " + set_of_books_id);
			strSQL.append("  and   gb.ACTUAL_FLAG='A' ");
			strSQL.append("  group by gb.code_combination_id)   b ");
			strSQL.append(" ,gl_code_combinations@CPF_T     glcc ");
			strSQL.append(" ,gl_lookups@CPF_T        glk ");
			strSQL.append(" where  b. code_combination_id=a.code_combination_id(+) ");
			strSQL.append(" and    glcc.code_combination_id=b.code_combination_id ");
			strSQL.append(" and    glcc.account_type= glk.lookup_code ");
			strSQL.append(" and    glk.lookup_TYPE='ACCOUNT TYPE' ");
			
			//---strSQL.append(" and    glcc.segment1='" + getFirstSegment(lOfficeID, lCurrencyID) + "' ");
			strSQL.append(" and    glcc.segment1='" + GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID) + "' ");
			
			strSQL.append(" and    glcc.segment3<>'T' ");
			strSQL.append(" and    glcc.segment4<>'T' ");
			strSQL.append(" union ");
			strSQL.append(" select glcc.segment1||'.'|| glcc.segment2||'.'|| glcc.segment3||'.'|| glcc.segment4  account ");
			strSQL.append("   ,glk.description  account_type ");
			strSQL.append("  ,(nvl(a.curr_bla,0)+nvl(b.end_balance,0))  balance ");
			strSQL.append(" from ");
			strSQL.append(" (select code_combination_id ");
			strSQL.append("  ,sum(nvl(entered_dr,0)-nvl(entered_cr,0))  curr_bla ");
			strSQL.append(" from gl_je_lines@CPF_T   gjl ");
			strSQL.append(" ,gl_je_headers@CPF_T  gjh ");
			strSQL.append(" where to_char(gjl.effective_date,'yyyymmdd') >=to_char(trunc(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),'month'),'yyyymmdd') ");
			strSQL.append(" and   to_char(gjl.effective_date,'yyyymmdd') <=to_char(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),'yyyymmdd') ");
			strSQL.append(" and   gjl.JE_HEADER_ID = gjh.JE_HEADER_ID ");
			strSQL.append(" and   gjh.CURRENCY_CODE=upper('" + currency_code + "') ");
			strSQL.append(" and   gjl.set_of_books_id= " + set_of_books_id);
			strSQL.append(" group by gjl.code_combination_id)    a ");
			strSQL.append(" ,(select code_combination_id ");
			strSQL.append("       , SUM(gb.begin_balance_dr-gb.begin_balance_cr+ gb.period_net_dr- gb.period_net_cr) end_balance ");
			strSQL.append(" from 	gl_balances@CPF_T  gb ");
			strSQL.append(" where gb.period_name = to_char(add_months(to_date('"+DataFormat.getDateString(tsDate)+"','yyyy-mm-dd'),-1),'MM-YYYY') ");
			strSQL.append("  and   gb.CURRENCY_CODE =upper('" + currency_code + "') ");
			strSQL.append(" AND   gb.set_OF_BOOKS_ID= " + set_of_books_id);
			strSQL.append(" and   gb.ACTUAL_FLAG='A' ");
			strSQL.append(" group by gb.code_combination_id)   b ");
			strSQL.append(" ,gl_code_combinations@CPF_T     glcc ");
			strSQL.append(" ,gl_lookups@CPF_T        glk ");
			strSQL.append(" where  a.code_combination_id=b.code_combination_id(+) ");
			strSQL.append(" and    glcc.code_combination_id=a.code_combination_id ");
			strSQL.append(" and    glcc.account_type= glk.lookup_code ");
			strSQL.append(" and    glk.lookup_TYPE='ACCOUNT TYPE' ");
			
			//-----strSQL.append(" and    glcc.segment1='" + getFirstSegment(lOfficeID, lCurrencyID) + "' ");
			strSQL.append(" and    glcc.segment1='" + GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID) + "' ");
			
			strSQL.append(" and    glcc.segment3<>'T' ");
			strSQL.append(" and    glcc.segment4<>'T'   ");
			strSQL.append(" )  ");
			if (lImportCurrencyID > 0)
			{
				strSQL.append(" where account not like '_01.6%' ");
			}
			else
			{
				//-----strSQL.append(" where account like '" + getFirstSegment(lOfficeID, lCurrencyID) + ".6%' ");
				strSQL.append(" where account like '" + GLOracleFinBean_cpf.getSegmentCode1ForOfficeID(lOfficeID) + ".6%' ");
			}
			ps = conn.prepareStatement(strSQL.toString());
			Log.print("***********查询科目余额：" + strSQL.toString());
			rs = ps.executeQuery();
			
			int i=1;
			//GLBalanceInfo往科目信息里添加数据，此时有可能会错，因为前SQL是中油的，与3。0信息有点不匹配
			while (rs != null && rs.next())			
            {
                GLBalanceInfo info = new GLBalanceInfo();
                //根据账户类型得到账户类型ID从而得到借贷方向
                long lDirectionID = SETTConstant.SubjectAttribute.getDirection(getSubjectType(rs.getString("account_type").trim().substring(0,1)));	
                double dBalance = rs.getDouble("balance");	//余额
                info.setGLDate(tsDate);			//日期--
                info.setOfficeID(lOfficeID);		//办事处--
                info.setCurrencyID(lCurrencyID);	//币种--
                info.setGLSubjectCode(rs.getString("account"));	//科目代号--
                info.setBalanceDirection(lDirectionID);	//借贷方向
                
                Log.print("******************添加余额的个数为:"+i++);
                Log.print("--从GL中得到科目号为:getGLSubjectCode："+info.getGLSubjectCode());
                
                if (lDirectionID == SETTConstant.DebitOrCredit.DEBIT)		//资产，支出，表外
                {
                	Log.print("    当前科目的借贷方向:"+lDirectionID+"  在借方(DEBIT)添加余额为setDebitBalance:"+dBalance+"  科目类型:"+rs.getString("account_type"));
                    info.setDebitBalance(dBalance);
                }
                else
                {
                	//(值得考虑)中油财务的贷余额在读过来后，贷方的数据要＊(-1)来进入到我们内部的科目余额系统表中()
                	Log.print("    当前科目的借贷方向:"+lDirectionID+"  在贷方(CREDIT)添加余额为setDebitBalance:"+dBalance+"  科目类型:"+rs.getString("account_type") + "要乘以-1");
                    info.setCreditBalance( dBalance * (-1) );		//负债，权益，收入    oracle记录的是负数，此时把它*-1变成正数记录我们系统
                	//info.setCreditBalance( dBalance  );		
                }
                list.add(info);
            }
			Log.print("进入到得到科目余额的方法里面！--------结束!!！");
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
        }
        catch (Exception e)
        {
            Log.print(e.toString());
            throw new Exception(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception e)
            {
                Log.print(e.toString());
            }
        }
        return (list != null && list.size() > 0 ? list : null);
    }

	  
	
    
    //得到科目金额  (CPF)     
    public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID,long lModelID, Timestamp tsDate) throws Exception
    {
    	//直接返回空，问陈宇艺，此时的中油没有发生额导入这个要求，
    	//此时为NULL，则在GLDealBean中在更新科目余额时不管什么接口都调用了这个方法，但里在的集合为空
    	//则此时实际没有做任何处理，流程没有改
        return null;
    }


    /*
     * U850GLBean.isCompleted()：判断对方返回信息是否完毕
     */
    //(Oracle GL数据导入是否结束)
    private static long isCompleted(long lOfficeID,long lCurrencyID) throws Exception
    {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long lDealStatusID = Constant.ShutDownStatus.FAIL;
        try
        {
        	Log.print("+++++++++++++++进入到是导出成功判断！ isCompleted方法   ((开始))+++++++++");
            //conn=GLOracleFinBean_cpf.getOracleConnection(lOfficeID,lCurrencyID);	//运用自己的私有方法根据币种办事处得到数据库的连接
            //此时得到的是本库连接
        	conn = Database.getConnection();
            if(conn!=null)
                 System.out.println("得到了数据库连接！");
            else
            	System.out.println("没有得到了数据库连接！");
            
            
            //ps = conn.prepareStatement("select status from GL_INTERFACE@CPF_T where status = 'NEW' and currency_conversion_date = ? and user_je_source_name ='电子商务系统' ");
            //ps = conn.prepareStatement(" select * from CUX_GL_IMPORT@ITREASURY_T ");
            //ps = conn.prepareStatement("select status from GL_INTERFACE@CPF_T where status like 'E%' and currency_conversion_date = ? and user_je_source_name ='电子商务系统' ");
            //此时要装dblink
            //Log.print("==================SQL:select status from GL_INTERFACE@CPF_T where  user_je_source_name ='电子商务系统' ");
            //ps = conn.prepareStatement("select status from GL_INTERFACE@CPF_T where  user_je_source_name ='电子商务系统' ");           
            
            //需要设置办事处的条件
            long lSBID=GLOracleFinBean_cpf.getSetOfBooksID(lOfficeID);
            ps = conn.prepareStatement("select status from GL_INTERFACE@CPF_T where  user_je_source_name ='电子商务系统' and set_of_books_id= "+lSBID);
            Log.print("==================SQL:select status from GL_INTERFACE@CPF_T where  user_je_source_name ='电子商务系统' and set_of_books_id= "+lSBID);
            rs = ps.executeQuery();
 
            if (rs != null & rs.next())
            {
                String strStatusID = rs.getString("STATUS");
                if (strStatusID.equals("NEW")) ////正在处理
                {
                    lDealStatusID = Constant.ShutDownStatus.DOING;
                }
                else  /////失败	(可能开头是E,等其它的各种情况)
                {
                    lDealStatusID = Constant.ShutDownStatus.FAIL;
                }
            }else{
            	/////成功
            	lDealStatusID = Constant.ShutDownStatus.SUCCESS;
            }
            Log.print("+++++++++++++++进入到是导出成功判断！ isCompleted方法   ((结束!!))+++++++++,返回的传输状态为："+lDealStatusID);
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            conn.close();
            conn = null;
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (conn != null)
            {
                conn.close();
                conn = null;
            }
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                }
            }
            catch (Exception e)
            {
                throw new RemoteException("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }
    
    
   
    private static long findPostGLVoucherInfo(Collection collVoucher) throws Exception
    {
        long lDealStatusID = Constant.ShutDownStatus.SUCCESS;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sbRecord = new StringBuffer();
        try
        {
        }
        catch (Exception e)
        {
            lDealStatusID = Constant.ShutDownStatus.FAIL;
            e.printStackTrace();
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            throw new RemoteException(e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                    rs = null;
                }
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
            }
            catch (Exception e)
            {
                throw new Exception("remote exception : " + e.toString());
            }
        }
        return lDealStatusID;
    }

    /**
     * 得到与Oralce系统相对应的账套
     * 
     * @param lOfficeID
     * @return
     */
    //得到Oracle GL的账簿ID			+++++++++++++++++原来是long型的，现在变成String型的了
    public static long getSetOfBooksID(long lOfficeID)
    {
    	long lSet_Of_Books_ID = 1;
        switch ((int) lOfficeID)
        {
            case 1:				//大连
                lSet_Of_Books_ID = 4;
                break;
            case 2:				//锦西
                lSet_Of_Books_ID = 6;
                break;
            case 3:				//锦州
                lSet_Of_Books_ID = 7;
                break;
            case 4:				//辽阳
                lSet_Of_Books_ID = 8;
                break;  
            default:			//大连(default)		
                lSet_Of_Books_ID = 4;
                break;
        }
        //return 12;		//沈阳汇总(现在就用这个)
        return lSet_Of_Books_ID;
    }
    
    //根据办事处ID得到相应的科目段
    public static String getSegmentCode1ForOfficeID(long lOfficeID){
    	String strSeg="";
    	switch ((int) lOfficeID)
        {
            case 1:		//大连
            	strSeg = "331";
                break;
            case 2: 	//锦西
            	strSeg = "321";
                break;
            case 3: 	//锦州
            	strSeg = "311";
                break;
            case 4: 	//辽阳
            	strSeg = "341";
                break;
            default:	//大连(默认)
            	strSeg = "331";
                break;
        }
    	return strSeg;
    }
   
    //得到总账科目信息的第一段			-----------------
    public static String getFirstSegment(long lOfficeID,long lCurrencyID)
    {
        String strFirstSegment = "";
		switch ((int) lOfficeID)		//办事处代号判断
		{
			case 1 :
				strFirstSegment = "1";
				break;
			case 2 :
				strFirstSegment = "2";
				break;
			case 3 :
				strFirstSegment = "3";
				break;
			case 4 :
				strFirstSegment = "4";
				break;
			case 5 :
				strFirstSegment = "5";
				break;
			case 9 :
				strFirstSegment = "9";
				break;
			case 20 :
				strFirstSegment = "20";
				break;
			default :
				strFirstSegment = String.valueOf(lOfficeID);
				break;
		}
		if (strFirstSegment.length() == 1)
		{
			strFirstSegment = strFirstSegment + "0";
		}
		switch ((int) lCurrencyID)		//币种代号判断
		{
			case (int) Constant.CurrencyType.RMB:
				strFirstSegment = strFirstSegment + "1";
				break;
			case (int) Constant.CurrencyType.USD :
				strFirstSegment = strFirstSegment + "2";
				break;
			case (int) Constant.CurrencyType.UKP :
				strFirstSegment = strFirstSegment + "3";
				break;
			case (int) Constant.CurrencyType.ED :
				strFirstSegment = strFirstSegment + "6";
				break;
			case (int) Constant.CurrencyType.JP :
				strFirstSegment = strFirstSegment + "4";
				break;
			case (int) Constant.CurrencyType.SP :
				strFirstSegment = strFirstSegment + "5";
				break;
			case (int) Constant.CurrencyType.HKD :
				strFirstSegment = strFirstSegment + "7";
				break;
		}
		return strFirstSegment;
		
    }

 
    //得到Oracle GL的币种符号			----------------------
    public static String getCurrencyCode(long lCurrencyID)
    {
        String strCurrencyCode = "";
        switch ((int) lCurrencyID)
        {
        	case (int) Constant.CurrencyType.RMB:
        		strCurrencyCode = "RMB";
        		break;
        	case (int) Constant.CurrencyType.USD:
        		strCurrencyCode = "USD";
                break;
            case (int) Constant.CurrencyType.UKP:
            	strCurrencyCode = "UKP";
                break;
            case (int) Constant.CurrencyType.ED:
            	strCurrencyCode = "ED";
                break;
            case (int) Constant.CurrencyType.SP:
            	strCurrencyCode = "SP";
                break;
            case (int) Constant.CurrencyType.JP:
            	strCurrencyCode = "JP";
                break;
            case (int) Constant.CurrencyType.HKD:
            	strCurrencyCode = "HKD";
                break;
            case (int) 0 :
            	strCurrencyCode = "STAT";
            default:
            	strCurrencyCode = "RMB";
                break;
        }
        return strCurrencyCode;
    }
    
    
    //得到Oracle GL的类别名(币种归类名称)		--------------------
    public static String getUserJeCategoryName(long lCurrencyID)
    {
    	
        String user_je_category_name = "CPF_凭证_人民币";
        switch ((int) lCurrencyID)
        {
            case (int) Constant.CurrencyType.RMB:
                user_je_category_name = "CPF_凭证_人民币";
                break;
            case (int) Constant.CurrencyType.USD:
                user_je_category_name = "CPF_凭证_美元";
                break;
            case (int) Constant.CurrencyType.UKP:
                user_je_category_name = "CPF_凭证_英镑";
                break;
            case (int) Constant.CurrencyType.ED:
                user_je_category_name = "CPF_凭证_欧元";
                break;
            case (int) Constant.CurrencyType.SP:
                user_je_category_name = "CPF_凭证_新加坡元";
                break;
            case (int) Constant.CurrencyType.JP:
                user_je_category_name = "CPF_凭证_日元";
                break;
            case (int) Constant.CurrencyType.HKD:
                user_je_category_name = "CPF_凭证_港元";
                break;
            default:
                user_je_category_name = "CPF_凭证_人民币";
                break;
        }
        return user_je_category_name;
    }

    /**
     * 得到与Oralce系统相对应的模块名称
     * 
     * @param lCurrencyID
     * @return
     */
    //得到模块名称
    public static String getModelName(long lModelID)
    {
        String strModelName = "存贷款结算";
        switch ((int) lModelID)
        {
            case (int) Constant.ModuleType.SETTLEMENT:
                strModelName = "存贷款结算";
                break;
            case (int) Constant.ModuleType.SECURITIES:
                strModelName = "证券业务";
                break;
            default:
                strModelName = "存贷款结算";
                break;
        }
        return strModelName;
    }

    /**
     * 得到与Oralce系统相对应的模块名称
     * 
     * @param lCurrencyID
     * @return
     */
    //得到Oracle GL的批名
    public static String getBatchName(Connection conn, long lModelID, long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
    {
        String strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
        try
        {
            switch ((int) lModelID)
            {
                case (int) Constant.ModuleType.SETTLEMENT:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
                    break;
                case (int) Constant.ModuleType.SECURITIES:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateTimeString(conn) + getUserJeCategoryName(lCurrencyID);
                    break;
                default:
                    strBatchName = getModelName(lModelID) + DataFormat.getDateString(tsDate) + getUserJeCategoryName(lCurrencyID);
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return strBatchName;
    }

    /**
     * 得到与Oralce系统相对应的科目类型
     * 
     * @param lOfficeID
     * @return
     */
    //根据账户类型得到账户类型ID
    public static long getSubjectType(String strType)
    {
        long lTypeID = -1;
        if (strType != null)
        {
            if (strType.equals("A"))//资产
            {
                lTypeID = SETTConstant.SubjectAttribute.ASSET;
            }
            if (strType.equals("L"))  //负债
            {
                lTypeID = SETTConstant.SubjectAttribute.DEBT;
            }
            if (strType.equals("O"))	//权益
            {
                lTypeID = SETTConstant.SubjectAttribute.RIGHT;
            }
            if (strType.equals("R"))//收入
            {
                lTypeID = SETTConstant.SubjectAttribute.INCOME;
            }
            if (strType.equals("E"))	//支出
            {
                lTypeID = SETTConstant.SubjectAttribute.PAYOUT;
            }
        }
        return lTypeID;
    }

    /**
     * 得到与Oralce系统相对应的科目区间名称
     * 
     * @param lOfficeID
     * @return
     */
    //得到日期格式(月份的英文代号)	--删掉该方法体
    public static String getPeriodName(Timestamp tsDate)
    {
        String strPeriodName = "";
        if (tsDate != null)
        {
            int lMonth = tsDate.getMonth() + 1;
            int lYear = tsDate.getYear();
            String strMonth = "";
            String strYear = "";
            switch (lMonth)
            {
                case 1:
                    strMonth = "JAN";
                    break;
                case 2:
                    strMonth = "FEB";
                    break;
                case 3:
                    strMonth = "MAR";
                    break;
                case 4:
                    strMonth = "APR";
                    break;
                case 5:
                    strMonth = "MAY";
                    break;
                case 6:
                    strMonth = "JUN";
                    break;
                case 7:
                    strMonth = "JUL";
                    break;
                case 8:
                    strMonth = "AUG";
                    break;
                case 9:
                    strMonth = "SEP";
                    break;
                case 10:
                    strMonth = "OCT";
                    break;
                case 11:
                    strMonth = "NOV";
                    break;
                case 12:
                    strMonth = "DEC";
                    break;
            }
            strYear = DataFormat.formatInt((int) lYear % 100, 2);
            strPeriodName = strMonth + "-" + strYear;
            Log.print("*****lMonth=" + lMonth + ",lYear=" + lYear);
            Log.print("*****strMonth=" + strMonth + ",strYear=" + strYear);
            Log.print("*****strPeriodName=" + strPeriodName);
        }
        return strPeriodName;
    }
    
   
    //得到数据来源名称			--删掉该方法体
    public static String getSourceName(long lModelID,long lOfficeID, long lCurrencyID)
    { 
        String strSourceName = "";
        switch ((int)lModelID)
        {
            case (int)Constant.ModuleType.SETTLEMENT:
                strSourceName = "1";
                break;
            case (int)Constant.ModuleType.SECURITIES:
                strSourceName = "3";
                break;
            
        }
        return strSourceName;
    } 
    
    
    /**
	 * 此方法根据数据库中保存的参数，直接连接Oracle数据库
	 * @return Connection
	 */
	//直接连到Oracle库					--------------------
	private static Connection getOracleConnection(long lOfficeID,long lCurrencyID,long lType) throws Exception
	{
		Connection conn = null;
		try
		{		
				Log.print("==========得到OracleCPF JDBC连接====(开始)=====  ");
				//得到配置信息
				GlSettingInfo glSettingInfo=new GlSettingInfo();
				glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID, lCurrencyID,lType);
				
	            String DB_IP = glSettingInfo.getGlDBIP();			//IP
	            String DB_SID = glSettingInfo.getGlDBDatabaseName();	//库名称
	            String DB_USERNAME = glSettingInfo.getGlDBUserName();		//用户名
	            String DB_PASSWORD = DataFormat.formatNullString(glSettingInfo.getGlDBPassWord());	//密码
	            String DB_PORT = glSettingInfo.getGlDBPort();		//端口

				String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	            String dbURL = "jdbc:oracle:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	            
	            Log.print("dbURL = " + dbURL);
	            Log.print("DB_USERNAME = " + DB_USERNAME);
	            Log.print("DB_PASSWORD = " + DB_PASSWORD);

	            Class.forName(jdbcDriver).newInstance();
	            conn = DriverManager.getConnection(dbURL, DB_USERNAME, DB_PASSWORD);
	            Log.print("==========得到OracleCPF JDBC连接====(结束)=====  ");
		}		
		catch (SQLException sqe)
        {
            Log.print("connect oracleCPF db failed by oracle jdbc driver. " + sqe.toString());
            throw sqe;
        }		
		return conn;
	}
    
	//Oracle接口封装的Info信息				--------------
    private static class OracleGLInfo implements Serializable
    {
        public String modelname = "";

        public String status = "";

        public long set_of_books_id = -1;

        public Timestamp accounting_date = null;

        public String currency_code = "";

        public Timestamp date_created = null;

        public long created_by = -1;

        public String Actual_flag = "";

        public String user_je_category_name = "";

        public String user_je_source_name = "";

        public Timestamp currency_conversion_date = null;

        public String user_currency_conversion_type = "";

        public String segment1 = "";

        public String segment2 = "";

        public String segment3 = "";

        public String segment4 = "";

        public String segment5 = "";

        public String segment6 = "";

        //public String segment7 = "";

        public double entered_dr = 0;

        public double entered_cr = 0;

        public String reference1 = "";

        public String reference4 = "";

        public String reference10 = "";

        //去掉的属性值
        //public String Period_Name = "";
        //public long je_line_num = 1;
        //public String attribute5 = "";
        //public String attribute6 = "";
        
        //添加的属性值
        public String reference2 = "";
        public String reference3 = "";
        public String reference5 = "";
        public String attribute2 = "";
        public String attribute11 = "";	//设计中是大写Attribute11
        Timestamp Attribute12 = null;	//设计中是大写Attribute12
        public String Context3 =" ";
        
    }
}