<%--
 页面名称 ：d019-c.jsp
 页面功能 : 新增贴现申请--导入文本控制页面
 作    者 ：gqzhang
 日    期 ：2004年1月
 特殊说明 ：实现操作说明：
 修改历史 ：
--%>

<%@ page contentType = "text/html;charset=gbk" %>
<%@ page import="java.io.*,
                 java.rmi.RemoteException,
				 java.util.*,
                 java.sql.*,
                 javax.servlet.*,
				 org.apache.poi.hssf.usermodel.*,
				 org.apache.poi.poifs.filesystem.POIFSFileSystem,
				 com.iss.itreasury.util.*,
				 com.iss.itreasury.dataentity.*,
				 com.iss.itreasury.loan.util.*,
                 com.iss.itreasury.ebank.obdiscountapply.bizlogic.*,
                 com.iss.itreasury.ebank.obdiscountapply.dataentity.*,
				 com.iss.itreasury.ebank.obsystem.bizlogic.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.*,
                 com.iss.itreasury.ebank.util.*,
				 com.iss.itreasury.ebank.obsystem.dataentity.OBClientInfo,
				 com.iss.itreasury.loan.loancommonsetting.dataentity.ClientInfo"
%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%!

public  Collection saveUploadFileToDateBase (long lDiscountApplyID,com.jspsmart.upload.SmartUpload mySmartUpload) throws Exception
{
			long lResult = 0;
                  
            String strFileContent="";
            String sbExecuteSQL= "";
			String strSQLGetOtherInfo = "";
            Vector returnVector = new Vector ();
			Vector conditionVector = new Vector ();
            String strAdd ="";//每次从上传文件中读到的一个单元。
            int index=0;
            int toIndex= 0;
            long lNumberRow = 0;
            int fromIndex = 0;
                  
			boolean  bIsValid = true; //当前行是否有效
				  
            String strAccuntID ="";
            String strClientID = "";
            String strGetUpClientID = "";
            String strClientCode="";
            String strTempClientCode ="";
            long  lUpClientID=0;
                  
			long  lIsertRowSum = 0;
				  
			long lMaxID = 0;
			long lMaxNO =0 ;
				  
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
                  
            int iTableKeyPositionFive=0;
                  
            boolean bIsEnd =true;
            // Retreive the current file
            com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile (0);
  			myFile.saveAs(Env.UPLOAD_PATH+myFile.getFileName());

			OBDiscountApplyHome  obDiscountApplyHome = null;
			OBDiscountApply      obDiscountApply = null;
			//DiscountLoanInfo  dli = new DiscountLoanInfo ();
			DiscountBillInfo  dbi = new DiscountBillInfo ();

			obDiscountApplyHome = (OBDiscountApplyHome)EJBObject.getEJBHome("OBDiscountApplyHome");
			obDiscountApply = obDiscountApplyHome.create();

			try
			{
					POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Env.UPLOAD_PATH+ myFile.getFileName()));
					HSSFWorkbook wb = null;
					HSSFSheet sheet = null;
					HSSFRow row =  null;
					HSSFCell cell = null;
					if (fs!=null)
					{
						wb = new HSSFWorkbook(fs);
					}
					if (wb!=null)
					{
						sheet = wb.getSheetAt(0);
					}
					if (sheet==null)
					{
				        UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                        returnVector.addElement (upLoanReturnInfo);
                        upLoanReturnInfo.setIsOk(true);
                        upLoanReturnInfo.setPositionRow(0);
                        upLoanReturnInfo.setPositionCol(0);
                        upLoanReturnInfo.setReason("不能导入空的Excel文件！");
						Log.print("测试3");
					}
						
					row = sheet.getRow(2); 
					for (int i=2;row!=null;i++,row =sheet.getRow(i))
					{
						Log.print("外层循环****"+i);
						bIsValid=true;
						DiscountBillInfo upLoadDiscountBillInfo= new DiscountBillInfo();
                        //1-编号,2-原始出票人,3-承兑方,4-是否本地,5-出票日,6-到期日,7-汇票编号,
						//8-汇票金额,9-节假日增加天数,10-直接前手,11-汇票类型
						upLoadDiscountBillInfo.setDiscountApplyID(lDiscountApplyID);
						for  (index = 1; index <= 11; index++)
                        {
                            Log.print("内层循环****"+index);
							strAdd="";
                            Log.print ("for  循环开始"+index);
							cell = row.getCell((short) (index-1));
							if (cell!=null)
							{
								//在Excel中的类型是文本、常规
								if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
								{
									strAdd=cell.getStringCellValue();
								}
								// 在Excel中的类型是数值
								else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
								{
									strAdd=String.valueOf(cell.getNumericCellValue());
								}
							}
							if (strAdd==null)
							{
								strAdd="";
							}
							Log.print ("读取到的strAdd=="+strAdd+"==");
						    //1-编号
							if (index==1)
                            {                                                 
                            }
							//2-原始出票人
                            if (index==2)
                            {
								strAdd = strAdd.trim ();
  								if (strAdd.indexOf(".")>0)
									strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("取第2列数据!");
                                Log.print ("data="+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}else
								{
									upLoadDiscountBillInfo.setUser(strAdd);
									conditionVector.addElement(upLoadDiscountBillInfo);
								}
                            }
							//3-承兑方
                            if (index==3)
                            {
                                strAdd = strAdd.trim ();
								if (strAdd.indexOf(".")>0)
									strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("取第3列数据!");
                                Log.print ("data="+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}else
								{
									upLoadDiscountBillInfo.setBank(strAdd);
								}
                            }
							//4-是否本地
                            if (index==4)
                            {
                                strAdd = strAdd.trim ();
                                Log.print ("取第4列数据!");
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}
                                else if (strAdd.equals("本地"))
								{
									upLoadDiscountBillInfo.setIsLocal(Constant.YesOrNo.YES);
                                }
								else
								{
									upLoadDiscountBillInfo.setIsLocal(Constant.YesOrNo.NO);
								}								
                            }
							//5-出票日
                            if (index==5)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("取第5列数据!");
                                try
								{
									upLoadDiscountBillInfo.setCreate(DataFormat.getDateTime(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("取第5列数据!格式化错误!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("导入的内容不能转化为日期！::"+strAdd);
                                }
                            }
							//6-到期日
                            if (index==6)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("取第6列数据!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setEnd(DataFormat.getDateTime(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("取第6列数据!格式化错误!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("导入的内容不能转化为日期！::"+strAdd);
                                }
                            }
							//7-汇票编号
                            if (index==7)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("取第7列数据!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}
								else
								{
									upLoadDiscountBillInfo.setCode(strAdd);
								}
                            }
							//8-汇票金额
                            if (index==8)
                            {
								strAdd = strAdd.trim ();
								Log.print ("取第8列数据!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setAmount(Double.parseDouble(strAdd));
                                }
								catch(Exception e)
                                {
                                    Log.print ("取第8列数据!格式化错误!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("导入的内容不能转化为数字！::"+strAdd);
                                }
                            }
							//9-节假日增加天数
                            if (index==9)
                            {
                                strAdd = strAdd.trim ();
								if (strAdd.indexOf(".")>0)
							    strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("取第9列数据!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setAddDay(Long.parseLong(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("取第9列数据!格式化错误!");
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("导入的内容不能转化为数字！::"+strAdd);
                                }
                            }
							//10-直接前手
							if (index==10)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("取第10列数据!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}
								else
								{
									upLoadDiscountBillInfo.setFormerOwner(strAdd);
								}
                            }
							//11-汇票类型
							if (index==11)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("取第11列数据!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
									returnVector.addElement (upLoanReturnInfo);
									upLoanReturnInfo.setIsOk(true);
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("缺少字段::"+strAdd);
								}
								else if (strAdd.equals(LOANConstant.DraftType.getName(LOANConstant.DraftType.BANK)))
								{
									upLoadDiscountBillInfo.setAcceptPOTypeID(LOANConstant.DraftType.BANK);
								}
								else if (strAdd.equals(LOANConstant.DraftType.getName(LOANConstant.DraftType.BIZ)))
								{
									upLoadDiscountBillInfo.setAcceptPOTypeID(LOANConstant.DraftType.BIZ);
								}
                            }
						}
					}
						
                    if (returnVector.size () < 1 )
                    {
						Iterator it = conditionVector.iterator ();
                              
						Log.print("开始插入数据");
                         
						Log.print("删除以前导入的数据");
						con = Database.getConnection ();
						  
						ps = con.prepareStatement (" delete from ob_discountbill where nLoanID = " + lDiscountApplyID);
						ps.executeQuery ();
						Log.print("成功删除以前导入的数据");
						if (ps!=null)
						{
							ps.close();
							ps=null;
						}
							   
						lIsertRowSum = 0;
							
						while (it.hasNext ())
                        {
							DiscountBillInfo upLoadDiscountBillInfo = (DiscountBillInfo)it.next();
							lIsertRowSum++;
							Log.print("贴现申请id:"+upLoadDiscountBillInfo.getDiscountApplyID());
							
							lResult = obDiscountApply.saveDiscountBill(upLoadDiscountBillInfo);
									
							if (lResult>0)
							{													
								Log.print("成功插入第" + lIsertRowSum + "条记录。");
							}
							else
							{
                                UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                returnVector.addElement (upLoanReturnInfo);
                                upLoanReturnInfo.setIsOk(false);
									
								upLoanReturnInfo.setPositionRow(lIsertRowSum);
                                upLoanReturnInfo.setPositionCol(0);
        	                         
								upLoanReturnInfo.setReason("数据写入数据库发生异常！请全部重新导入！");
							}
							if (ps!=null)
							{
								ps.close ();
                        		ps = null;
							}
									
						}
						if (rs!=null)
						{
							rs.close ();
							rs = null;
						}
						if (ps!=null)
						{
							ps.close ();
							ps = null;
						}
						if( con != null )
						{
							con.close ();
							con = null;
						}
					}
                        
                }
                catch(SQLException  sqle)
                {
					Log.print (sqle.toString ());
                    if( rs != null )
                    {
						rs.close ();
                        rs = null;
                    }
                    if( ps != null )
                    {
						ps.close ();
						ps = null;
                    }
                    if( con != null )
                    {
                        con.close ();
                        con = null;
                    }
                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                    returnVector.addElement (upLoanReturnInfo);
                    upLoanReturnInfo.setIsOk(false);
                    upLoanReturnInfo.setPositionRow(lIsertRowSum);
                    upLoanReturnInfo.setPositionCol(0);
        	        upLoanReturnInfo.setReason("写入或者读取数据库发生异常！请全部重新导入！");
                }
                catch(Exception e)
                {
					Log.print (e.toString ());
                    if( rs != null )
                    {
					    rs.close ();
                        rs = null;
                    }
                    if( ps != null )
                    {
                        ps.close ();
                        ps = null;
                    }
                    if( con != null )
                    {
                        con.close ();
                        con = null;
                    }
					UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                    returnVector.addElement (upLoanReturnInfo);
                    upLoanReturnInfo.setIsOk(false);
                    upLoanReturnInfo.setPositionRow(lIsertRowSum);
                    upLoanReturnInfo.setPositionCol(0);
        	        upLoanReturnInfo.setReason(e.toString()+"！");
                }
                finally
                {
                    try
                    {
					    if (myFile != null)
						    myFile = null;
                        if( rs != null )
                        {
						    rs.close ();
                            rs = null;
                        }
                        if( ps != null )
                        {
                            ps.close ();
                            ps = null;
                        }
                        if( con != null )
                        {
                            con.close ();
                            con = null;
                        }
                    }
                    catch(Exception e)
                    {
						Log.print (e.toString ());
					    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                        returnVector.addElement (upLoanReturnInfo);
                        upLoanReturnInfo.setIsOk(false);
						upLoanReturnInfo.setPositionRow(lIsertRowSum);
						upLoanReturnInfo.setPositionCol(0);
						upLoanReturnInfo.setReason(e.toString()+"！");
                    }
                }
                return (returnVector.size () > 0 ? returnVector : null);
            }
			
			
%>		
<%
/* 标题固定变量 */
String strTitle = "[贴现申请]";
%>			
<%
  try
  {
	   Log.print("*******进入页面--ebank/loan/discountapply/d019-c.jsp*******");
	
	   /* 用户登录检测 */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* 判断用户是否有权限 */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		 //定义变量
		  String strTmp = "";
		 
		  long lUserID = -1;
		  long lDiscountID = -1;
		  
		  boolean  bIsUpLoad = false;
  		  Iterator it = null; 
  		  Collection collection = null;
		  lUserID = sessionMng.m_lUserID;
		    
		  Vector vDoc = new Vector();
		  
		  strTmp = (String)request.getAttribute("lID");
		  
		  if(strTmp != null && strTmp.length() > 0)
		  {
			lDiscountID = Long.parseLong(strTmp);
		  }
		  Log.print("------------------贴现申请id22:"+lDiscountID);
		  mySmartUpload.initialize(pageContext);
		  mySmartUpload.upload();
		  collection = saveUploadFileToDateBase(lDiscountID,mySmartUpload);
		  bIsUpLoad = true;
          if(bIsUpLoad)
		  {
		    Log.print("4444444444444"); 
		    request.setAttribute("bIsUpLoad",bIsUpLoad+"");
		    Log.print("5555555555"); 
		    request.setAttribute("resultInfo",collection);
			  /* 获取上下文环境 */
	        ServletContext sc = getServletContext();
	          /* 设置返回地址 */
	        RequestDispatcher rd = sc.getRequestDispatcher(PageController.getDispatcherURL(new PageControllerInfo(sessionMng,"/loan/discountapply/d016-v.jsp")));
			  /* forward到结果页面 */
	        rd.forward(request, response);
		  }
		 else
		 {
		    OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E001");
		    return;
		 }
%>
<%	}
	catch(IException ie) 
	{
	    ie.printStackTrace();
		OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
