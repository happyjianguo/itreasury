<%@ page contentType="text/html; charset=gbk" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="java.util.*" %>          
<%@ page import=" com.iss.itreasury.ebank.privilege.dataentity.OB_PrivilegeInfo,
                  com.iss.itreasury.ebank.privilege.dao.OB_PrivilegeDAO,
                  com.iss.itreasury.system.bulletin.dataentity.*,
                  com.iss.itreasury.system.bulletin.bizlogic.*,
                  com.iss.itreasury.util.*,
                  com.iss.itreasury.ebank.util.OBHtml" 
%>                    
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>

<%
/* 标题固定变量 */
	String strTitle = "[下级菜单]";
try 
	{
        //用户登录检测 
        if (sessionMng.isLogin() == false)
        {
        	OBHtml.showCommonMessage(out, sessionMng,  strTitle, "",1, "Gen_E002");
        	out.flush();
        	return;
        }

        // 判断用户是否有权限 
        if (sessionMng.hasRight(request) == false)
        {
            out.println(sessionMng.hasRight(request));
        	OBHtml.showCommonMessage(out, sessionMng, strTitle, "", 1, "Gen_E003");
        	out.flush();
        	return;
        }
%>
<%
  int i = 0;
  int j = 0;       
  Collection coll = null;
  Collection coll1 = null;
  Collection coll2 = null;
  if(session.getAttribute("userprivilege")!=null){
	  coll = (Collection)session.getAttribute("userprivilege");
	  coll1 = coll;
 	  coll2 = coll;
 	  System.out.println("size"+coll.size());
   session.removeAttribute("userprivilege");
	
  }else{    
      String userPrivilegeNo = request.getParameter("userPrivilegeNo")!=null?request.getParameter("userPrivilegeNo"):null;
      if(userPrivilegeNo==null || "null".equals(userPrivilegeNo))
      {
%>
      <script language= "javascript">
           alert("您没有该模块的操作权限");
           window.open('<%=strContext%>/Index.jsp?istroy=isoftstone','_parent');
      </script>
<%    
        return;
      }
      long m_lUserID = sessionMng.m_lUserID;
      long lModuleID = sessionMng.m_lModuleID;
      OB_PrivilegeDAO privilegedao = new OB_PrivilegeDAO();
      coll = privilegedao.getPrivilegeOfUserForYQ(m_lUserID,lModuleID,userPrivilegeNo);
      coll1 = coll;
      coll2 = coll;
  }
  Iterator it2 = coll2.iterator();
  while(it2.hasNext()){
      OB_PrivilegeInfo info2 = (OB_PrivilegeInfo)it2.next();
      if(info2.getMenuUrl()!= null && !"null".equals(info2.getMenuUrl())){
         ++j;}
  }
  HashMap hm = new HashMap();
  // (HashMap)session.getAttribute("Popedom");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title></title>
<link href="<%=strContext%>/config/form_window.css" rel="stylesheet" type="text/css">
<link href="<%=strContext%>/config/form_shengwei.css" rel="stylesheet" type="text/css">
<link href="config/form_window.css" rel="stylesheet" type="text/css">
<link href="config/form_shengwei.css" rel="stylesheet" type="text/css">
<link href="/itreasury/css/style.css" rel="stylesheet" type="text/css">
<link href="/itreasury/css/menu.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/webob/js/util.js"></script>
</head>
<body leftmargin="0" topmargin="0" style="overflow:hidden; ">
	<script type="text/javascript">
			var wHeight1=window.screen.height-258;
			if(window.screen.height!=768){
			 	document.write("<table width='100%' height="+wHeight1+" border='0' cellpadding='0' cellspacing='0'>");
			}
			else{
			//alert("你的屏幕高度："+window.screen.height);
				document.write("<table width='100%' height='520' border='0' cellpadding='0' cellspacing='0'>");
			}
	</script>
<%--<table width="100%" height="520" border="0" cellpadding="0" cellspacing="0">
  --%><tr>
    <!--td width="8px" height="100%" background="images/sjzfz/intern1.jpg"> <img src="images/sjzfz/intern1.jpg">
    </td-->
          <td width="100%" valign="top" bgcolor="#D6EAF3">
		 <!--- 测试具体内容----------->
		  <!--%@ include file="/sjzgov/commontentPage/common.jsp"%-->

<table width="1000" height="90%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td rowspan="2" width="178" valign="top"  background="/webob/graphics/bodyleft_bg2.jpg" align="right">
	<table id="leftTab" width="98%" border="0" cellpadding="0" cellspacing="0">
	<tr>
       <td><img src="/webob/graphics/body_left_til2.jpg" width="179" height="27"></td>
    </tr>
	 
<%
   Iterator it = coll.iterator();
   while(it.hasNext()){
      OB_PrivilegeInfo info = (OB_PrivilegeInfo)it.next();
      if(info.getPlevel() == 2){
         if(info.getMenuUrl() != null && !"".equals(info.getMenuUrl())&&!"null".equals(info.getMenuUrl())){
         i++;
   %>
   <tr>
	  <td height="25"  align="left" valign='middle' style='cursor:hand;'  >&nbsp;&nbsp;<img src="/webob/graphics/conner5.gif" alt="" name="imgKB1" id="imgKB1" border="0">&nbsp;&nbsp;<a onclick="showarrowhead('imgCL<%=i%>','<%=j%>')" href="javascript:showiniframe('<%=info.getMenuUrl()%>')" class="txt_black"><font style="color: #000000;
	text-decoration: none;"><%=info.getName()%></font></a>&nbsp;<img id="imgCL<%=i%>" src="/webob/graphics/shim.gif"></td>
   </tr>
   <tr>
     <td height="1" colspan="2" align="center" background="/webob/graphics/line_bg.gif" class="box01"></td>
   </tr>
   <%
     }else{
   %>
    <tr>
     <td height="25" align="left" valign='middle' style='cursor:hand;'  class="box1"  onclick='javascript:turnit(scontent<%=info.getId()%>,stag<%=info.getId()%>);'  id='stag<%=info.getId()%>'>&nbsp;&nbsp;<img src="/webob/graphics/conner5.gif" alt="" name="imgKB1" id="imgKB1" border="0">&nbsp;&nbsp;<%=info.getName()%></td>
   </tr>
    <tr>
     <td height="1" colspan="2" align="center" background="/webob/graphics/line_bg.gif" class="box01"></td>
   </tr>
   <%}%>
   <tr>
	<td valign="top" align=center style='DISPLAY: none'  id='scontent<%=info.getId()%>' >
	<table width="74%" class="shw-table-submenu" border="0" cellpadding="0" cellspacing="0">

	<tr>
	<td valign="top">
		<table width='100%' border='0' cellpadding='0' cellspacing='0' >
<%
      
       Iterator it1 = coll1.iterator();
       while(it1.hasNext()){
           OB_PrivilegeInfo info1 = (OB_PrivilegeInfo)it1.next();
           String str = info1.getPrivilegeNo();
           str = str.substring(0,7);
           if(info1.getPrivilegeNo()!= info.getPrivilegeNo() && info.getPrivilegeNo().equals(str)){
           ++i;
%>
                  <tr>
                  <td height='25' align="left" valign='middle' style='cursor:hand;'><img src="/webob/graphics/conner6.gif">
                  <a onclick="showarrowhead('imgCL<%=i%>','<%=j%>')" href="javascript:showiniframe('<%=info1.getMenuUrl()%>')" class='txt_black'><font style="color: #000000;
	                 text-decoration: none;"><%=info1.getName()%></font></a>&nbsp;<img id="imgCL<%=i%>" src="/webob/graphics/shim.gif">
                  </td>
                  </tr>

<%           
           }        
       }
%>
</table>
</td>
</tr>
</table>
</td>
</tr>
<%
     }
   }
%>

<!------end 输出菜单---------->
      </table>
	  </td>
	  
    <td valign="top" >
        <!-- 
        <img width="820" src="/webob/graphics/banner.jpg"/><BR>
		-->
		<script type="text/javascript">
			var wWidth1=window.screen.width-178;
		 	if(window.screen.width!=1024){
			 	document.write("<table width="+wWidth1+" border='0' align='right' cellpadding='0' cellspacing='0' height='100%'>");
			}
			else{
				//alert("你的屏幕宽度："+window.screen.width);
				document.write("<table width='841' border='0' align='right' cellpadding='0' cellspacing='0' height='100%'>");
			}
		</script>
		<!-- <table width="" border="0" align="right" cellpadding="0" cellspacing="0" height="100%">
		  --><tr>
              <td height="1" colspan="3" bgcolor="#0077B9"></td>
            </tr>
            <tr>
              <td  width="1" bgcolor="#0077B9"></td>
    <script type="text/javascript">
			var wHeight2=window.screen.height-285;
		 	var wWidth2=window.screen.width-179;
		 	if(window.screen.width==1024 && window.screen.height==768){
		 		document.write("<td width='817' height='481' align='left' valign='middle' bgcolor='#FDFFFC' class='' height='1'>");
		 	}
		 	else{
			 	document.write("<td width="+wWidth2+" height="+wHeight2+" align='left' valign='middle' bgcolor='#FDFFFC'>");
		 	}
	</script>
              <%--<td width="789" height="460" align="center" valign=middle bgcolor="#FDFFFC" class="" height="1">
		--%><iframe name="vivian" src="bulletinCue.jsp" align="center" allowtransparency=true frameborder="0" marginwidth="1" marginheight="1" height="100%" border="0" scrolling=auto style="background-color=transparent" width="100%">
		</iframe>		
		</td>
              <td width="1" bgcolor="#0077B9"></td>
            </tr>
            <tr>
              <td colspan="3" >
              <script type="text/javascript">
				 	var wWidth3=window.screen.width-183;
				 	if(window.screen.width==1024 && window.screen.height==768){
				 		document.write("<img src='\/webob\/graphics\/bodyright_bottom.jpg' width='841' height='11'>");
				 	}
				 	else{
					 	document.write("<img src='\/webob\/graphics\/bodyright_bottom.jpg' width="+wWidth3+" height='10'>");
				 	}
			  </script>
              <%--<img src="/webob/graphics/bodyright_bottom.jpg" width="815" height="6">
              --%></td>
            </tr>
		</table>
       </td>
  </tr> 
</table>
<table>
<tr>
   <td><img src="/webob/graphics/bottom.jpg" width="995" height="49"></td>
</tr>
</table>
</td>
</tr>
</table>
<script language= "javascript">

/**
*主菜单的开合
*/
var tt='start';
var ii='start';
function turnit(ss,bb) {

  if (ss.style.display=="none") {
    if(tt!='start') tt.style.display="none";
    //if(ii!='start') ii.src="dian.gif";
    ss.style.display="";
    tt=ss;
    ii=bb;
    //bb.src="ball.gif";
  }
  else {
    ss.style.display="none";
    //bb.src="dian.gif";
  }
}

function getMenuArra(StrSource) {
   var personMenu = new Array();
   personMenu = StrSource.split(",");
   return personMenu;
}

function getSiglemenuArra(str1) {
   var sigleMenu = new Array();
   sigleMenu = str1.split("|");
   return sigleMenu;
}

function writeMenu(Strsource) {
  var sjz_menus = new Array;//所有菜单的数组
  var sjz_siglemenu = new Array; //每个菜单项拆分而成的数组
  var sjz_menustr="";
  sjz_menus = getMenuArra(Strsource);
  var maincount=0;//用来批示主菜单的编号
  for (i=0; i<sjz_menus.length; i++) {
	maincount = maincount + 1;
	sjz_siglemenu = getSiglemenuArra(sjz_menus[i]);
	if (sjz_siglemenu[1] == "0") {
	  sjz_menustr = sjz_menustr + "<tr><td height='28' valign='middle' style='cursor:hand;' language='JScript' onclick='turnit(scontent" + maincount + ",stag" + maincount + ");'  id='stag" + maincount + "' align=center height=26  class=ptl-homepage-mainmenu ><img src='<%=strContext%>/images/sjzfz/title.gif' width='17' height='17'> ";
          sjz_menustr = sjz_menustr + sjz_siglemenu[2];
          if (sjz_siglemenu[2]=="我的工作"){
          sjz_menustr = sjz_menustr + "</td></tr><tr><td  align=right style='DISPLAY: none'  id='scontent" + maincount + "'>";
          }
          else{
          sjz_menustr = sjz_menustr + "</td></tr><tr><td  align=right style='DISPLAY: none' id='scontent" + maincount + "'>";
          }
          sjz_menustr = sjz_menustr + "<table width='68%' border='0' cellpadding='0' cellspacing='0' >";
	  for (j=i+1; j< sjz_menus.length; j++) {
	     sjz_siglemenu = getSiglemenuArra(sjz_menus[j]);
	     if (sjz_siglemenu[1]=="0") {
	     	i=j-1;
	     	sjz_menustr = sjz_menustr + "</table></td></tr>";
		break;
	     }
	else {
		//sjz_menustr = sjz_menustr + "<tr><td style='cursor:hand;'onClick='javascript:showfullwin(\"" + sjz_siglemenu[3] + "\")'> *&nbsp;" + sjz_siglemenu[2] + "</td></tr>";
		//sjz_menustr = sjz_menustr + "<tr><td height='20' valign='middle' style='cursor:hand;'onClick='javascript:showiniframe(\"" + sjz_siglemenu[3] + "\")'><img src='<%=strContext%>/images/sjzfz/icon_dot01.gif' width='3' height='3'>&nbsp;" + sjz_siglemenu[2] + "</td></tr>";
		sjz_menustr = sjz_menustr + "<tr><td height='20' valign='middle' style='cursor:hand;'><img src='<%=strContext%>/images/sjzfz/icon_dot01.gif' width='3' height='3'>&nbsp;<a href='javascript:showiniframe(\"" + sjz_siglemenu[3] + "\")' class='menuLink'>" + sjz_siglemenu[2] + "</a></td></tr>";
              }
	  }

	}
  }
  sjz_menustr = sjz_menustr + "</table></td></tr>";
  //return (sjz_menustr);
//alert ("long a:---" + sjz_menustr) ;
document.write(sjz_menustr);

}
function showfullwin(myurl) {
  var fullwindow = window.open(myurl,'',"scrollbars=no");
  // var fullwindow = window.open(myurl,'');
  fullwindow.moveTo(0,0);
  fullwindow.resizeTo(1024,768);
}
function showiniframe(myurl) {
  var aaa = myurl;
  document.all.vivian.src=aaa;
}

function showarrowhead(imgid,wid){
    var str = imgid.substring(0,5);
    var str1 = imgid.substring(5);
    document.getElementById(imgid).src="/webob/graphics/newconer2.gif";
    for(i=1;i<=wid;i++){
      var str2 = str+i;
      if(str1 != i){
         document.getElementById(str2).src="/webob/graphics/shim.gif";
      }
    }
 }

</script>
</body>
</html>
<%
 }
	catch (IException ie)
	{
        OBHtml.showExceptionMessage(out,sessionMng, ie, strTitle,"",1);
		return;
    }
%>
