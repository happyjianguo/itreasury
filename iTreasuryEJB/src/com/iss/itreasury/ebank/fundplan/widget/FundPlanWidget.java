/**
 * jlzhang
 * Oct 14, 2008
 */
package com.iss.itreasury.ebank.fundplan.widget;


import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.iss.itreasury.ebank.fundplan.bizlogic.FundPlanCreateWidgetBiz;
import com.iss.itreasury.ebank.fundplan.model.FundPlanInfo;
import com.iss.itreasury.ebank.fundplan.model.FundPlanParamInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * @author Colin
 *
 */
public class FundPlanWidget extends BodyTagSupport {

	private static final long serialVersionUID = 6212909753262327260L;
	
	private FundPlanCreateWidgetBiz createBiz = null;
	
	private FundPlanParamInfo paramInfo = new FundPlanParamInfo();

	private static long LINE_TOTAL = 0;
	private static long LINE_MONDAY = 1;
	private static long LINE_TUESDAY = 2;
	private static long LINE_WEDNESDAY = 3;
	private static long LINE_THURSDAY = 4;
	private static long LINE_FRIDAY = 5;
	private static long LINE_NEXTWEEK = 6;
		
	public void setDateFrom(String dateFrom) {
		this.paramInfo.setDateFrom(dateFrom);
	}
	
	public void setDateTo(String dateTo) {
		this.paramInfo.setDateTo(dateTo);
	}
	public void setClientId(long clientId) {
		this.paramInfo.setClientId(clientId);
	}
	public void setCapitalplanId(long capitalplanId) {
		this.paramInfo.setCapitalplanId(capitalplanId);
	}
  	public void setDisabled(boolean disabled)
  	{
  		this.paramInfo.setDisabled(disabled);
  	}
	public void setOffice(long office) {
		this.paramInfo.setOffice(office);
	}
	public void setCurrency(long currency) {
		this.paramInfo.setCurrency(currency);
	}
	
	public void setType(String type) {
		this.paramInfo.setType(type);
	}
	
	public void setModelId(long modelId) {
		this.paramInfo.setModelId(modelId);
	}
	
	public boolean getDisabled()
	{
		return paramInfo.isDisabled();
	}
	public int doStartTag() throws JspException{
		//DecimalFormat df = new DecimalFormat("#####################.##");//定义整数有效长度为21
		
		createBiz = new FundPlanCreateWidgetBiz(paramInfo);
        
        String isWrite = "";
        if(getDisabled() == true)
        {
        	isWrite = "disabled='true'";
        }
        
        try {
	        List list = createBiz.getCapitalPlanContentList();
	        
	        
	        
			
			StringBuffer sb=new StringBuffer();
			//sb.append("<script language=\"JavaScript\" src=\"/webob/js/fundplan.js\"></script> \n");
			sb.append("<table border=1 width=100% class=normal> \n");
			sb.append("<tr> \n");
			sb.append("<td width=5% align=center nowrap>序号</td> \n");
			sb.append("<td width=25% align=center nowrap>项目</td> \n");
			sb.append("<td align=\"center\">合计</td> \n");
			sb.append("<td align=\"center\">星期一</td> \n");
			sb.append("<td align=\"center\">星期二</td> \n");
			sb.append("<td align=\"center\">星期三</td> \n");
			sb.append("<td align=\"center\">星期四</td> \n");
			sb.append("<td align=\"center\">星期五</td> \n");
			sb.append("<td align=\"center\">下一周</td> \n");
			if(paramInfo.getType()=="submit"){
					sb.append("<td align=\"center\">备注</td> \n");
			}
			sb.append("</tr> \n");
//			控制备注汉字字数不能超过50
			sb.append("\n");
			sb.append("<Script Language=\"JavaScript\"> \n");
			//设置第一个输入焦点目标。
			String firstrowName ="";
			if(list != null && list.size() > 0){
	        	FundPlanInfo firstinfo = (FundPlanInfo)list.get(0);
	        	firstrowName = "P_"+ String.valueOf(firstinfo.getConfigid()+1);
	        	
	        }
			sb.append("window.onload = function sss(){\n" +
					"        document.getElementsByName(\""+firstrowName+"\")[1].select();\n" +
					"   }\n" );
			sb.append("		function   checkdata(txtname){ \n");
			sb.append("		var str=document.getElementsByName(txtname)[7].value; \n");
			sb.append("		var lCount = 0; \n");
			sb.append("		var temp = new String(str); \n");
			sb.append("		for(var i =0;i<temp.length;i++){ \n");
			sb.append("		    if(temp.charCodeAt(i)>255) \n");
			sb.append("		        lCount +=2; \n");
			sb.append("		    else \n");
			sb.append("		        lCount +=1; \n");
			sb.append("		} \n");
			sb.append("		if   (lCount>250){  \n");
			sb.append("		     alert(\"字符过多，请删减\"); \n");
			sb.append("		     // str1=str.substr(0,100); \n");
			sb.append("		     document.getElementsByName(txtname)[7].value =str; \n");
			sb.append("		     document.getElementsByName(txtname)[7].style.cssText=\"background-color:#7e7774\";");
			sb.append("		     document.getElementsByName(txtname)[7].focus(); \n");
			sb.append("		     return   false ;  \n");
			sb.append("		}    \n");
			sb.append("		     document.getElementsByName(txtname)[7].style.cssText=\"\";");
			sb.append("		return   true ; \n");
			sb.append("	} \n");
			sb.append("	\n");
			sb.append("	\n");
			sb.append(" function   checkM(textname,no){ \n");
			sb.append("		if(document.getElementsByName(textname)[no].value==null||document.getElementsByName(textname)[no].value==\"\"){");
			sb.append("	       document.getElementsByName(textname)[no].value=\"0.00\";");
			sb.append("	   } \n" +
					  "		else{ \n" +
					  "          var   a=reverseFormatAmount1(document.getElementsByName(textname)[no].value);\n" +
					  "          t=/^(|[+-]?(0|([1-9]\\d*)|((0|([1-9]\\d*))?\\.\\d{1,2})){1,1})$/;\n" +
					  "          if(!t.test(a)){ \n " +
					  "　　　　　　　//　document.getElementsByName(textname)[no].value=\"0.00\";\n" +
					  "           　　alert(\"数据输入有误!\"); \n" +
					  "　　　　　　 　 document.getElementsByName(textname)[no].select();\n" +
					  "             return false;\n" +
					  "         }\n" +
					  "     }\n");
			sb.append("	} \n");
			sb.append(" function isPressEnter(configId,coNext) {  \n" +
					  "       var nextCtrlName = \"\" ; \n" +
					  "       nextCtrlName = nextCtrlOnFocus(configId,coNext); \n" +
					  "       // alert(nextCtrlName); \n" +
					  "       eval(\"document.all.\"+nextCtrlName +\".select();\");\n"  +
					  "} \n" );
			sb.append("</Script> \n");
			
			if(list != null && list.size() > 0){
				
				sb.append("<Script Language=\"JavaScript\"> \n");
				sb.append("var ar = new Array(); \n");
				sb.append("</Script> \n");
				
				String levelcode = "";
				int rowSpan = 0;
				int countRoot = 1;
				int countChild = 1;
				long curLineIndex = 0;
				int maxLine = list.size();
				for(int i=0; i<maxLine; i++){
					FundPlanInfo entity = (FundPlanInfo)list.get(i);
					String rowName = "P_"+ String.valueOf(entity.getConfigid());
					
					if(entity.getParentid() == 0)
					{
						countChild = 1;
						levelcode = "";
						rowSpan = 0;
						levelcode = entity.getCode();
						rowSpan = createBiz.getRowSpan(levelcode);
						
						
						sb.append("<tr> \n");
						sb.append("<td width=100 align=center><B>"+createStr(countRoot++)+"</B></td> \n");
						sb.append("<td align=\"center\" nowrap><B>"+entity.getName().trim()+"</td> \n");
						
						//合计
						sb.append("<td align=\"center\"> \n");
						sb.append("<input type='hidden' name='itemId' value='"+ entity.getPlanitemid() + "'> \n");						
						sb.append("<input type='hidden' name='configId' value='"+ entity.getConfigid() + "'> \n");
						sb.append("<input type='hidden' name='parentId' value='"+ entity.getParentid() + "'> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='" + DataFormat.formatNumber(entity.getTotal(),2)+ "'onBlur=\"checkM('"+rowName+"','0')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						//星期一
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getMondaycapital(),2) +"' onBlur=\"checkM('"+rowName+"','1')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getTuesdaycapital(),2) +"' onBlur=\"checkM('"+rowName+"','2')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getWednesdaycapital(),2) +"'onBlur=\"checkM('"+rowName+"','3')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getThursdaycapital(),2) +"'onBlur=\"checkM('"+rowName+"','4')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getFridaycapital(),2) +"' onBlur=\"checkM('"+rowName+"','5')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						
						
						//下一周
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\"  class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getNextweekcapital(),2) +"'onBlur=\"checkM('"+rowName+"','6')\" size='22' maxlength='21' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("</td> \n");
						//备注
						if(paramInfo.getType()=="submit"){
							if(rowSpan==1){
								sb.append("<td valign=top align=center rowspan="+rowSpan+"><B><textarea style='background-color:#ffffff;' "+ isWrite +"STYLE=\"\" onChange=\"javascript:checkdata('"+rowName+"')\"  onKeyup=\"javascript:checkdata('"+rowName+"')\""+" name="+ rowName +" rows="+1+" style='width:10,height:10;font-weight:bold'>"+ DataFormat.formatString(entity.getRemark()) +"</textarea></B></td>");
							}else{
								sb.append("<td valign=top align=center rowspan="+rowSpan+"><B><textarea style='background-color:#ffffff;' "+ isWrite +"STYLE=\"\" onChange=\"javascript:checkdata('"+rowName+"')\"  onKeyup=\"javascript:checkdata('"+rowName+"')\""+" name="+ rowName +" rows="+(rowSpan*2)+" style='width:10,height:10;font-weight:bold'>"+ DataFormat.formatString(entity.getRemark()) +"</textarea></B></td>");
							}
						}
						sb.append("</tr>");
						
						
						
					}
					else
					{
						
						int next = 2;
						sb.append("<tr>");
						sb.append("<td width=100 align=center><B>"+ countChild++ +"</B></td> \n");
						sb.append("<td align=\"center\">"+entity.getName().trim()+"</td>");
						
						//合计
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' name='"+ rowName + "' value='" + DataFormat.formatNumber(entity.getTotal(),2) + "'  size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' style='width:57' readOnly>");
						sb.append("<input type='hidden' name='itemId' value='"+ entity.getPlanitemid() + "'> \n");
						sb.append("<input type='hidden' name='configId' value='"+ entity.getConfigid() + "'> \n");
						sb.append("<input type='hidden' name='parentId' value='"+ entity.getParentid() + "'> \n");						
						sb.append("</td> \n");
						
						curLineIndex = LINE_MONDAY;
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+entity.getConfigid()+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getMondaycapital(),2) + "' onBlur=\"checkM('"+rowName+"','1')\"  size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_TUESDAY;						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+entity.getConfigid()+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getTuesdaycapital(),2) + "' onBlur=\"checkM('"+rowName+"','2')\" size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						sb.append("</td> \n");
					
						curLineIndex = LINE_WEDNESDAY;						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+entity.getConfigid()+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getWednesdaycapital(),2) + "'onBlur=\"checkM('"+rowName+"','3')\"  size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_THURSDAY;						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+entity.getConfigid()+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getThursdaycapital(),2) + "' onBlur=\"checkM('"+rowName+"','4')\" size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_FRIDAY;						
						sb.append("<td align=\"center\"> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+entity.getConfigid()+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getFridaycapital(),2) + "' onBlur=\"checkM('"+rowName+"','5')\" size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_NEXTWEEK;	
						sb.append("<td align=\"center\"> \n");
						if(i+1 == maxLine){ // 最后一行，回车焦点到保存按钮
							String lastlineName = "save";
							sb.append("<input "+ isWrite +" class=tar type='text' onkeydown=\"if(event.keyCode==13) document.frm001."+lastlineName+".focus();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getNextweekcapital(),2) + "' onBlur=\"checkM('"+rowName+"','6')\" size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						}else{
							next = 1;//下一行的第一个焦点，也就是第2个文本框
							sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) isPressEnter('"+(entity.getConfigid()+1)+"','"+(next++)+"');\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getNextweekcapital(),2) + "' onBlur=\"checkM('"+rowName+"','6')\" size='22' maxlength='15' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");'  onchange = 'sumExpression(\"" + "frm001" +"\",\"" + rowName +"\"," + curLineIndex + "); totalupOneRow(\"" + "frm001" +"\",\"" + rowName +"\",\"" +"\"," + 1+"); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:57' >");
						}
							sb.append("</td> \n");
						sb.append("</tr>");
					}
					
					if(entity.getExpression() != null && entity.getExpression().length() > 0)
					{
						sb.append("\n");
						sb.append("<Script Language=\"JavaScript\"> \n");
						sb.append("		var curObj = new Array();");
						sb.append("		curObj.push('" + rowName + "');");
						sb.append("		curObj.push('" + entity.getExpression() + "');");
						sb.append("		ar.push(curObj); \n");
						sb.append("</Script> \n");
					}
				}
			}
			sb.append("</table>");
			
        	this.pageContext.getOut().print(sb.toString());
        	
        } catch (Exception e) {
            throw new JspException("初始化列表失败，原因：" + e.getMessage(), e);
        }
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException{
		return EVAL_PAGE;
	}
	
	private String createStr(int num){
		//目前只适用与100以下(不含100)的数据
		//int n=num.length();
		//String s="";
		if(num<=10){
			return convertNumToStr(num);
		}else {
			return convertNumToStr(num/10)+"十"+convertNumToStr(num%10);
		}
		//return "";
	}
	
	private String convertNumToStr(int num){
		if(num==1){
			return "一";
		}else if(num==2){
			return "二";
		}else if(num==3){
			return "三";
		}else if(num==4){
			return "四";
		}else if(num==5){
			return "五";
		}else if(num==6){
			return "六";
		}else if(num==7){
			return "七";
		}else if(num==8){
			return "八";
		}else if(num==9){
			return "九";
		}
		return "";
	}
	
}