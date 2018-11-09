<!--Header start-->
<%@ page contentType = "text/html;charset=gbk"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%@ page import="com.iss.itreasury.ebank.util.*,
                com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.util.Constant" %>
<%@page import="java.util.*,
				com.iss.itreasury.system.bulletin.dataentity.*,
				com.iss.itreasury.system.bulletin.bizlogic.*,
				com.iss.itreasury.util.*" 
%>
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<script type="text/javascript" src="/webob/js/util.js"></script>
<!--Header end-->
<%--<script type="text/javascript">
function openWin(){
	//window.open("bulletinCue.jsp","","width=900,height=600,status=yes,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars =yes");
	OpenWindow=window.open("","公告信息",'height=150,width=400,top=300,left= 350,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
	
	}
</script>
--%><%
	//sessionMng.logout();
	String strTmp = null;
	String strUserName = null;
	String strPassword = null;
    long lCurrencyID = -1;
    String strTitle = "";
	//
	if (sessionMng.isLogin() == false)
	{
	
		strTmp = request.getParameter("UserName");		
		if( strTmp != null && strTmp.length() > 0 )
		{
		   	strUserName = strTmp;
		}
		strTmp = request.getParameter("Password");		
		if( strTmp != null && strTmp.length() > 0 )
		{
		   	strPassword = strTmp;
		}
		strTmp = request.getParameter("currency");		
		if (strTmp != null && strTmp.length() > 0 )
		{
		    lCurrencyID = Long.parseLong(strTmp);
		}
		Log.print("Username :" + request.getParameter("UserName"));
		Log.print("Password :" + request.getParameter("Password"));
		Log.print("Currency :" + request.getParameter("currency"));
		sessionMng.init(strUserName,strPassword,6);
		
		if (sessionMng.isLogin() == false)
		{
			OBHtml.showCommonMessage(out, sessionMng, strTitle,"",1,"Gen_E007");
			return;
		}
		System.out.println("开始准备菜单");
		sessionMng.prepareMenuForYQ();
		System.out.println("准备菜单结束");
		
		sessionMng.m_lCurrencyID=lCurrencyID;
		//sessionMng.prepareMenu();
	}
    OBHtml.showOBHomeHeadForYQ(out, sessionMng, strTitle, Constant.ShowMenu.YES);
    //OBHtml.showOBHomeHead(out, sessionMng, strTitle, Constant.ShowMenu.YES);
       
	

%>
<%
long id = -1;
String clients = "";//公告客户
String header = "";//公告标题
String content = "";//公告内容
long statusID = -1;//公告状态
String statusName="";//公告状态
String releaseDate ="";//发布日期
long userID = -1;//发布人
String userName="";//发布人姓名
//为了查询而设的属性
String fromReleaseDate ="";//发布日期由
String endReleaseDate ="";//发布日期到
long modifyId=3;//查询标识
Collection collection=null;
BulletinInfo bulletinInfo=new BulletinInfo();
bulletinInfo.setModifyId(modifyId);
clients=""+sessionMng.m_lClientID;
bulletinInfo.setClients(clients);

Log.print("-------------ClientCode:"+sessionMng.m_strClientCode);
BulletinBiz bulletinBiz=new BulletinBiz();
collection=bulletinBiz.findByCondition(bulletinInfo);
if(collection!=null){
 %> 
		       <script>
		        OpenWindow=window.open("bulletinCue.jsp","公告信息",'width=640,height=250,top=200,left=200,toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
		        </script>
<%}			
else{
	//out.print("无值");
}
%>
<!-- 
<table width="730" border="0" cellspacing="0" cellpadding="0" align="center" >
  <tr> 
    <td width="20" rowspan="2" align="left" valign="top"></td>
    <td width="65" rowspan="2" align="left" valign="top">&nbsp;
  
    <p>&nbsp;</p></td>
    <td width="5" rowspan="2"></td>
    <td width="680" height="130" align="left" valign="top"><img src="/itreasury/graphics/mainbank.jpg" ></td>
  </tr>
  <tr> 
    <td width="580" align="left" valign="top">&nbsp;
    
    
    </td>
  </tr>
</table>
-->
<%
	//显示文件尾
	OBHtml.showOBHomeEnd(out);
%>

