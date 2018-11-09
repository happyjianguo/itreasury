<%--
 ҳ������ ��d016-v.jsp
 ҳ�湦�� : ������������--�ı�����ҳ��
 ��    �� ��gqzhang
 ��    �� ��2004��1��
 ����˵�� ��ʵ�ֲ���˵����
 �޸���ʷ ��
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
<%@ taglib uri="/WEB-INF/tlds/iss-safety.tld" prefix="safety"%>
<%!

public  Collection saveUploadFileToDateBase (long lDiscountApplyID,com.jspsmart.upload.SmartUpload mySmartUpload) throws Exception
{
			long lResult = 0;
                  
            String strFileContent="";
            String sbExecuteSQL= "";
			String strSQLGetOtherInfo = "";
            Vector returnVector = new Vector ();
			Vector conditionVector = new Vector ();
            String strAdd ="";//ÿ�δ��ϴ��ļ��ж�����һ����Ԫ��
            int index=0;
            int toIndex= 0;
            long lNumberRow = 0;
            int fromIndex = 0;
                  
			boolean  bIsValid = true; //��ǰ���Ƿ���Ч
				  
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
                        upLoanReturnInfo.setReason("���ܵ���յ�Excel�ļ���");
						Log.print("����3");
					}
						
					row = sheet.getRow(2); 
					for (int i=2;row!=null;i++,row =sheet.getRow(i))
					{
						Log.print("���ѭ��****"+i);
						bIsValid=true;
						DiscountBillInfo upLoadDiscountBillInfo= new DiscountBillInfo();
                        //1-���,2-ԭʼ��Ʊ��,3-�жҷ�,4-�Ƿ񱾵�,5-��Ʊ��,6-������,7-��Ʊ���,
						//8-��Ʊ���,9-�ڼ�����������,10-ֱ��ǰ��,11-��Ʊ����
						upLoadDiscountBillInfo.setDiscountApplyID(lDiscountApplyID);
						for  (index = 1; index <= 11; index++)
                        {
                            Log.print("�ڲ�ѭ��****"+index);
							strAdd="";
                            Log.print ("for  ѭ����ʼ"+index);
							cell = row.getCell((short) (index-1));
							if (cell!=null)
							{
								//��Excel�е��������ı�������
								if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
								{
									strAdd=cell.getStringCellValue();
								}
								// ��Excel�е���������ֵ
								else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
								{
									strAdd=String.valueOf(cell.getNumericCellValue());
								}
							}
							if (strAdd==null)
							{
								strAdd="";
							}
							Log.print ("��ȡ����strAdd=="+strAdd+"==");
						    //1-���
							if (index==1)
                            {                                                 
                            }
							//2-ԭʼ��Ʊ��
                            if (index==2)
                            {
								strAdd = strAdd.trim ();
  								if (strAdd.indexOf(".")>0)
									strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("ȡ��2������!");
                                Log.print ("data="+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
								}else
								{
									upLoadDiscountBillInfo.setUser(strAdd);
									conditionVector.addElement(upLoadDiscountBillInfo);
								}
                            }
							//3-�жҷ�
                            if (index==3)
                            {
                                strAdd = strAdd.trim ();
								if (strAdd.indexOf(".")>0)
									strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("ȡ��3������!");
                                Log.print ("data="+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
								}else
								{
									upLoadDiscountBillInfo.setBank(strAdd);
								}
                            }
							//4-�Ƿ񱾵�
                            if (index==4)
                            {
                                strAdd = strAdd.trim ();
                                Log.print ("ȡ��4������!");
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
								}
                                else if (strAdd.equals("����"))
								{
									upLoadDiscountBillInfo.setIsLocal(Constant.YesOrNo.YES);
                                }
								else
								{
									upLoadDiscountBillInfo.setIsLocal(Constant.YesOrNo.NO);
								}								
                            }
							//5-��Ʊ��
                            if (index==5)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("ȡ��5������!");
                                try
								{
									upLoadDiscountBillInfo.setCreate(DataFormat.getDateTime(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("ȡ��5������!��ʽ������!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���ڣ�::"+strAdd);
                                }
                            }
							//6-������
                            if (index==6)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("ȡ��6������!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setEnd(DataFormat.getDateTime(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("ȡ��6������!��ʽ������!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���ڣ�::"+strAdd);
                                }
                            }
							//7-��Ʊ���
                            if (index==7)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("ȡ��7������!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
								}
								else
								{
									upLoadDiscountBillInfo.setCode(strAdd);
								}
                            }
							//8-��Ʊ���
                            if (index==8)
                            {
								strAdd = strAdd.trim ();
								Log.print ("ȡ��8������!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setAmount(Double.parseDouble(strAdd));
                                }
								catch(Exception e)
                                {
                                    Log.print ("ȡ��8������!��ʽ������!");
                                    UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���֣�::"+strAdd);
                                }
                            }
							//9-�ڼ�����������
                            if (index==9)
                            {
                                strAdd = strAdd.trim ();
								if (strAdd.indexOf(".")>0)
							    strAdd = strAdd.substring(0,strAdd.indexOf("."));
                                Log.print ("ȡ��9������!"+strAdd);
                                try
                                {
									upLoadDiscountBillInfo.setAddDay(Long.parseLong(strAdd));
                                }
                                catch(Exception e)
                                {
                                    Log.print ("ȡ��9������!��ʽ������!");
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���֣�::"+strAdd);
                                }
                            }
							//10-ֱ��ǰ��
							if (index==10)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("ȡ��10������!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                    returnVector.addElement (upLoanReturnInfo);
                                    upLoanReturnInfo.setIsOk(true);
                                    upLoanReturnInfo.setPositionRow(i);
                                    upLoanReturnInfo.setPositionCol(index);
                                    upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
								}
								else
								{
									upLoadDiscountBillInfo.setFormerOwner(strAdd);
								}
                            }
							//11-��Ʊ����
							if (index==11)
                            {
                                strAdd = strAdd.trim ();
								Log.print ("ȡ��11������!"+strAdd);
								if (strAdd.equals(""))
								{
									UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
									returnVector.addElement (upLoanReturnInfo);
									upLoanReturnInfo.setIsOk(true);
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("ȱ���ֶ�::"+strAdd);
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
                              
						Log.print("��ʼ��������");
                         
						Log.print("ɾ����ǰ���������");
						con = Database.getConnection ();
						  
						ps = con.prepareStatement (" delete from ob_discountbill where nLoanID = " + lDiscountApplyID);
						ps.executeQuery ();
						Log.print("�ɹ�ɾ����ǰ���������");
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
							Log.print("��������id:"+upLoadDiscountBillInfo.getDiscountApplyID());
							
							lResult = obDiscountApply.saveDiscountBill(upLoadDiscountBillInfo);
									
							if (lResult>0)
							{													
								Log.print("�ɹ������" + lIsertRowSum + "����¼��");
							}
							else
							{
                                UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                                returnVector.addElement (upLoanReturnInfo);
                                upLoanReturnInfo.setIsOk(false);
									
								upLoanReturnInfo.setPositionRow(lIsertRowSum);
                                upLoanReturnInfo.setPositionCol(0);
        	                         
								upLoanReturnInfo.setReason("����д�����ݿⷢ���쳣����ȫ�����µ��룡");
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
        	        upLoanReturnInfo.setReason("д����߶�ȡ���ݿⷢ���쳣����ȫ�����µ��룡");
                }
				catch (IOException ex){
					throw new IOException("��ʽ����");
					//returnVector = new Vector ();
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
					//UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo ();
                    //returnVector.addElement (upLoanReturnInfo);
                    //upLoanReturnInfo.setIsOk(false);
                    //upLoanReturnInfo.setPositionRow(lIsertRowSum);
                    //upLoanReturnInfo.setPositionCol(0);
        	        //upLoanReturnInfo.setReason(e.toString()+"��");
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
						upLoanReturnInfo.setReason(e.toString()+"��");
                    }
                }
                return (returnVector.size () > 0 ? returnVector : null);
            }
			
			
%>
<%
	/* ����̶����� */
	String strTitle = "[��������]";
%>					
<%
  try
  {
	Log.print("*******����ҳ��--ebank/loan/discountapply/d016-v.jsp*******");
	
	   /* �û���¼��� */
        if (sessionMng.isLogin() == false)
        {
            OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        /* �ж��û��Ƿ���Ȩ�� */
        if (sessionMng.hasRight(request) == false)
        {
        	out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
        	out.flush();
        	return;
        }
		
		//�������
		 String strTmp = "";
		 String strAction = "";
   	     long lUserID = -1;
		 long lDiscountID = -1;
		 boolean  bIsUpLoad = false;
  		 Iterator it = null; 
  		 Collection collection = null;
		 lUserID = sessionMng.m_lUserID;
		 Vector vDoc = new Vector();
		
		 strTmp = (String)request.getParameter("lID");
		 if(strTmp == null)
	     {
			  mySmartUpload.initialize(pageContext);
		      mySmartUpload.upload();
			 strTmp = (String)mySmartUpload.getRequest().getParameter("lID");
		 }
		 if(strTmp != null && strTmp.length() > 0)
		 {
		   lDiscountID = Long.parseLong(strTmp);
		 }
		strAction = (String)mySmartUpload.getRequest().getParameter("strAction");
		Log.print("-------------strAction:"+strAction);
		if(strAction != null && strAction.equals("upload"))
		{
			try{
			  collection = saveUploadFileToDateBase(lDiscountID,mySmartUpload);
			  bIsUpLoad = true;
			}catch(IOException ex){
				%>
				<script language="JavaScript">
				alert("��ʽ����");
				</script>
				<%
					bIsUpLoad = false;
			}
		}
		
		//��ʾ�ļ�ͷ
        OBHtml.showOBHomeHead(out, sessionMng, strTitle, OBConstant.ShowMenu.NO);
		%>	
<script language="JavaScript" src="/webob/js/Control.js"></script>
<script language="JavaScript" src="/webob/js/Check.js"></script>
<script language="JavaScript" src="/webob/js/date-picker.js"></script>
<script language="JavaScript" src="/webob/js/MagnifierSQL.js"></script>
<safety:resources />
<FORM METHOD="post" name="form_1" ACTION="d016-v.jsp" ENCTYPE="multipart/form-data">
<input type="hidden" name="lID" value="<%=lDiscountID%>" >
<input type="Hidden" name="strAction" value="upload">
<TABLE border=0 class=top height=2 width="99%">
  <TBODY>
  <TR class="tableHeader">
    <TD class=FormTitle height=2><B>�����ı���Ϣ</B></TD></TR>
  <TR>
    <TD height=101>
      <TABLE align=left  border=0 height=2  width="97%">
        <TBODY>
        <TR borderColor=#ffffff>
          <TD height=20 width="16%" align="center">ѡ���ĵ���</TD>
          <TD height=20 width="84%">
			<INPUT class=box name="file1" type="file" size = "64" > 
          </TD>
		</TR>
		<TR >
          <TD height=20>&nbsp;</TD>
          <TD height=20>&nbsp;</TD>
        </TR>
		<TR >
          <TD height=20>&nbsp;</TD>
          <TD height=20 align=right>
          <INPUT class=button name="upload"  type=button value=" �� �� " onclick="validate();" > 		  
		  <INPUT class=button name=Submit43 onclick="Javascript:window.close();" type=button value=" �� �� "> 
          </TD>
        </TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
</form>

<%
	if(collection == null && bIsUpLoad)
	{
	%>
	<script language="JavaScript">
	alert("�������ݳɹ���");
//	parent.opener.location.reload();
	parent.opener.location.href="../discountapply/d008-c.jsp?lID=<%=lDiscountID%>";
	</script>
	<%
	}
	if  (collection != null)
	{
 %>
  <TABLE align=center border=0 class=ItemList height=70 width="98%">
        <TBODY>
        <TR>
          <TD class=ItemTitle height=20 width="10%">  
            <DIV align=center>��λ��</DIV></TD>
          <TD class=ItemTitle height=20 width="10%">
            <DIV align=center>��λ��</DIV></TD>
          <TD class=ItemTitle height=20 width="60%">
            <DIV align=center>����ԭ��</DIV></TD>
        </TR>
		
 <%    	
  	it = collection.iterator();
	while(it.hasNext())
	{  
           UpLoanReturnInfo upLoanReturnInfo = (UpLoanReturnInfo)it.next();
	  %>
       <TR>
	      <TD class=ItemTitle height=20 width="10%">  
            <DIV align=center><%=upLoanReturnInfo.getPositionRow()%></DIV></TD>
          <TD class=ItemTitle height=20 width="10%">
            <DIV align=center><%=upLoanReturnInfo.getPositionCol()%></DIV></TD>
          <TD class=ItemTitle height=20 width="60%">
            <DIV align=center><%=upLoanReturnInfo.getReason()%></DIV></TD>
		</TR>
 <%
	 }
 %>	
	 </TBODY></TABLE>
 <%
 }
 %>

<script language="javascript">
function validate()
{
    if (!checkString(document.form_1.file1,"ѡ���ĵ�"))
    {
      return false;
    }
	form_1.strAction.value="upload";
	showSending();
	document.form_1.submit();
  return true;
}

</script>

<script language="javascript">
	firstFocus(document.form_1.file1);
	//setSubmitFunction("validate()");
    setFormName("form_1");
</script>
<%	
   //��ʾ�ļ�β
		OBHtml.showOBHomeEnd(out);	
    }
	catch(IException ie) 
	{
		//OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
<%@ include file="/common/SignValidate.inc" %>