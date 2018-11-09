<html>
<SCRIPT LANGUAGE=javascript> 
<!--
function switchMar(obj)
{
	if(frm1.nSwitch.value==1)
	{
		obj.stop();
		frm1.nSwitch.value=2;
	}
	else
	{
		frm1.nSwitch.value=1;
		obj.start();
	}
}
//为适当简化功能而添加 苗佳 2006-5-15
function titleStop(obj) {//v1.0
 obj.stop();
}
function titleStart(obj) {//v1.0
 obj.start();
}
// -->
</SCRIPT>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<link rel="stylesheet" href="/itreasury/css/style.css" type="text/css">
<script type="text/javascript" src="/webob/js/util.js"></script>
</head>

<BODY bgColor=#f3f3f3 leftmargin="0" topmargin="0">
<form method="post" action="" name="frm1">
  <input type="hidden" name="nSwitch" value="1">
  <input type="hidden" name="textfield" id="txtField">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#f3f3f3" bordercolor="#f3f3f3" height="20">
    <tr> 
      <td height="20%" valign="top">  <!-- ondblclick="javascript:switchMar(this)" -->
		<marquee bgcolor="#f3f3f3" id="marQ" scrollamount=4  onmouseover="titleStop(this)" onmouseout="titleStart(this)"></marquee>
	  </td>
    </tr>
  </table>
  </form>
</body>
</html>
