
package com.iss.itreasury.ebank.fundplan.widget;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.iss.itreasury.ebank.fundplan.bizlogic.FundPlanCreateWidgetBiz;
import com.iss.itreasury.ebank.fundplan.dao.CapitalPlanDao;
import com.iss.itreasury.ebank.fundplan.model.FundPlanInfo;
import com.iss.itreasury.ebank.fundplan.model.FundPlanParamInfo;
import com.iss.itreasury.util.DataFormat;

/**
 * 周资金计划专用模板
 * @author ylguo
 *
 */
public class FundPlanWidget2 extends BodyTagSupport {

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
		createBiz = new FundPlanCreateWidgetBiz(paramInfo);
        CapitalPlanDao dao = new CapitalPlanDao();
        
        String isWrite = "";//是否可改写由JSP页面的JSP控制
        if(getDisabled() == true)
        {
        	isWrite = "disabled='true'";
        }
        
        try {
	        List list = createBiz.getCapitalPlanContentList();
	        
			StringBuffer sb=new StringBuffer();
			//sb.append("<script language=\"JavaScript\" src=\"/webob/js/fundplan2.js\"></script> \n");
			sb.append("<table border=1 width=100% class=normal> \n");
			sb.append("<tr> \n");
			sb.append("<td width=5% align=center nowrap>序号</td> \n");
			sb.append("<td width=25% align=center nowrap>项目</td> \n");
			sb.append("<td align=center>上周五</td> \n");
			sb.append("<td align=center>星期一</td> \n");
			sb.append("<td align=center>星期二</td> \n");
			sb.append("<td align=center>星期三</td> \n");
			sb.append("<td align=center>星期四</td> \n");
			sb.append("<td align=center>星期五</td> \n");
			if(paramInfo.getType()=="submit"){
					sb.append("<td align=center>备注</td> \n");
			}
			sb.append("</tr> \n");
			
			if(list != null && list.size() > 0){
				//设置第一个输入焦点目标。
				String focuerowName ="";
				FundPlanInfo firstinfo = (FundPlanInfo)list.get(0);
		        focuerowName = "P_"+ String.valueOf(firstinfo.getConfigid()+2);
		        //左上角第一个文本框
		        String firstrowName ="";
		        String line2Name ="";
		        String line3Name ="";
		        String line4Name ="";
		        String line5Name ="";
		        String line6Name ="";
		        String line7Name ="";
		        String line8Name ="";
		        String line9Name ="";
		        String line10Name ="";
		        String line11Name ="";
		        String line12Name ="";
		        String line13Name ="";
		        String line14Name ="";
		        String line15Name ="";
		        String line16Name ="";
		        String line17Name ="";
		        String line18Name ="";
		        String line19Name ="";
		        String line20Name ="";
		        String line21Name ="";
		        String line22Name ="";
		       
				if(list != null && list.size() > 0){
		        	//FundPlanInfo firstinfo = (FundPlanInfo)list.get(0);
		        	firstrowName = "P_"+ String.valueOf(firstinfo.getConfigid());
		        	line2Name = "P_"+ String.valueOf(firstinfo.getConfigid()+1);
		        	line3Name = "P_"+ String.valueOf(firstinfo.getConfigid()+2);
		        	line4Name = "P_"+ String.valueOf(firstinfo.getConfigid()+3);
		        	line5Name = "P_"+ String.valueOf(firstinfo.getConfigid()+4);
		        	line6Name = "P_"+ String.valueOf(firstinfo.getConfigid()+5);
		        	line7Name = "P_"+ String.valueOf(firstinfo.getConfigid()+6);
		        	line8Name = "P_"+ String.valueOf(firstinfo.getConfigid()+7);
		        	line9Name = "P_"+ String.valueOf(firstinfo.getConfigid()+8);
		        	line10Name = "P_"+ String.valueOf(firstinfo.getConfigid()+9);
		        	line11Name = "P_"+ String.valueOf(firstinfo.getConfigid()+10);
		        	line12Name = "P_"+ String.valueOf(firstinfo.getConfigid()+11);
		        	line13Name = "P_"+ String.valueOf(firstinfo.getConfigid()+12);
		        	line14Name = "P_"+ String.valueOf(firstinfo.getConfigid()+13);
		        	line15Name = "P_"+ String.valueOf(firstinfo.getConfigid()+14);
		        	line16Name = "P_"+ String.valueOf(firstinfo.getConfigid()+15);
		        	line17Name = "P_"+ String.valueOf(firstinfo.getConfigid()+16);
		        	line18Name = "P_"+ String.valueOf(firstinfo.getConfigid()+17);
		        	line19Name = "P_"+ String.valueOf(firstinfo.getConfigid()+18);
		        	line20Name = "P_"+ String.valueOf(firstinfo.getConfigid()+19);
		        	line21Name = "P_"+ String.valueOf(firstinfo.getConfigid()+20);
		        	line22Name = "P_"+ String.valueOf(firstinfo.getConfigid()+21);
		        	
		        }
		        
				sb.append("<Script Language=\"JavaScript\"> \n");
				//sb.append("var ar = new Array(); \n");
				sb.append("window.onload = function sss(){\n" +
						"        document.getElementsByName(\""+focuerowName+"\")[1].select();\n" +
						"        var v = document.getElementsByName(\""+focuerowName+"\")[0].value;\n" +
						"		 if(v==\"\"||v==null){v=0.00;} \n" +
						"		 var leftFirstValue = reverseFormatAmount1(v); \n" +
						"        document.getElementsByName(\""+firstrowName+"\")[0].value = parseFloat(leftFirstValue).toFixed(2) ; \n" +
						"        allSum();"+
						" }\n" );
				//调用所有的求和公式
				sb.append(" function allSum(){\n" +
						"        sumD5();sumE5();sumF5();sumG5();sumH5(); \n" +
						"        sumC6();sumD6();sumE6();sumF6();sumG6();sumH6();" +
						"        sumC15();sumD15();sumE15();sumF15();sumG15();sumH15(); \n" +
						"        sumC18();sumC22();sumD18();sumD22();sumE18();sumE22();sumF18();sumF22();sumG18();sumG22();sumH18(); \n" +
						"        sumH22(); \n" +
						" }\n" );
				
				//第一行求和
				sb.append("// 给左上角第一个文本框赋值 \n" +
						  "function leftFirst(){ \n" +
						  "  var v=document.getElementsByName(\""+focuerowName+"\")[0].value;\n" +
						  "  if(v==\"\" || v==null){v=0.00;}\n" +
						  "  var leftFirstValue = reverseFormatAmount1(v);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[0].value = parseFloat(leftFirstValue).toFixed(2); \n" +
						  "}" );
				sb.append("// 特殊文本框D5(星期一)求和 \n" +
						  "function sumD5(){ \n" +
						  "  var vc5 =document.getElementsByName(\""+firstrowName+"\")[0].value;\n" +
						  "  var vd7 =document.getElementsByName(\""+line3Name+"\")[1].value;\n" +
						  "  var vd9 =document.getElementsByName(\""+line5Name+"\")[1].value;\n" +
						  "  var vd11=document.getElementsByName(\""+line7Name+"\")[1].value;\n" +
						  "  if(vc5==\"\" || vc5==null){vc5=0.00;}\n" +
						  "  if(vd7==\"\" || vd7==null){vd7=0.00;}\n"+
						  "  if(vd9==\"\" || vd9==null){vd9=0.00;}\n" +
						  "  if(vd11==\"\"|| vd11==null){vd11=0.00};\n" +
						  "  var c5 = reverseFormatAmount1(vc5);\n" +
						  "  var d7 = reverseFormatAmount1(vd7);\n" +
						  "  var d9 = reverseFormatAmount1(vd9);\n" +
						  "  var d11 = reverseFormatAmount1(vd11);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[1].value = (parseFloat(c5)+parseFloat(d7)+parseFloat(d9)+parseFloat(d11)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框E5(星期二)求和 \n" +
						  "function sumE5(){ \n" +
						  "  var vd5 =document.getElementsByName(\""+firstrowName+"\")[1].value;\n" +
						  "  var ve7 =document.getElementsByName(\""+line3Name+"\")[2].value;\n" +
						  "  var ve9 =document.getElementsByName(\""+line5Name+"\")[2].value;\n" +
						  "  var ve11=document.getElementsByName(\""+line7Name+"\")[2].value;\n" +
						  "  if(vd5==\"\" || vd5==null){vd5=0.00;}\n" +
						  "  if(ve7==\"\" || ve7==null){ve7=0.00;}\n"+
						  "  if(ve9==\"\" || ve9==null){ve9=0.00;}\n" +
						  "  if(ve11==\"\"|| ve11==null){ve11=0.00};\n" +
						  "  var d5 = reverseFormatAmount1(vd5);\n" +
						  "  var e7 = reverseFormatAmount1(ve7);\n" +
						  "  var e9 = reverseFormatAmount1(ve9);\n" +
						  "  var e11 = reverseFormatAmount1(ve11);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[2].value = (parseFloat(d5)+parseFloat(e7)+parseFloat(e9)+parseFloat(e11)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框F5(星期三)求和 \n" +
						  "function sumF5(){ \n" +
						  "  var ve5 =document.getElementsByName(\""+firstrowName+"\")[2].value;\n" +
						  "  var vf7 =document.getElementsByName(\""+line3Name+"\")[3].value;\n" +
						  "  var vf9 =document.getElementsByName(\""+line5Name+"\")[3].value;\n" +
						  "  var vf11=document.getElementsByName(\""+line7Name+"\")[3].value;\n" +
						  "  if(ve5==\"\" || ve5==null){ve5=0.00;}\n" +
						  "  if(vf7==\"\" || vf7==null){vf7=0.00;}\n"+
						  "  if(vf9==\"\" || vf9==null){vf9=0.00;}\n" +
						  "  if(vf11==\"\"|| vf11==null){vf11=0.00};\n" +
						  "  var e5 = reverseFormatAmount1(ve5);\n" +
						  "  var f7 = reverseFormatAmount1(vf7);\n" +
						  "  var f9 = reverseFormatAmount1(vf9);\n" +
						  "  var f11 = reverseFormatAmount1(vf11);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[3].value = (parseFloat(e5)+parseFloat(f7)+parseFloat(f9)+parseFloat(f11)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框G5(星期四)求和 \n" +
						  "function sumG5(){ \n" +
						  "  var vf5 =document.getElementsByName(\""+firstrowName+"\")[3].value;\n" +
						  "  var vg7 =document.getElementsByName(\""+line3Name+"\")[4].value;\n" +
						  "  var vg9 =document.getElementsByName(\""+line5Name+"\")[4].value;\n" +
						  "  var vg11=document.getElementsByName(\""+line7Name+"\")[4].value;\n" +
						  "  if(vf5==\"\" || vf5==null){vf5=0.00;}\n" +
						  "  if(vg7==\"\" || vg7==null){vg7=0.00;}\n"+
						  "  if(vg9==\"\" || vg9==null){vg9=0.00;}\n" +
						  "  if(vg11==\"\"|| vg11==null){vg11=0.00};\n" +
						  "  var f5 = reverseFormatAmount1(vf5);\n" +
						  "  var g7 = reverseFormatAmount1(vg7);\n" +
						  "  var g9 = reverseFormatAmount1(vg9);\n" +
						  "  var g11 = reverseFormatAmount1(vg11);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[4].value = (parseFloat(f5)+parseFloat(g7)+parseFloat(g9)+parseFloat(g11)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框H5(星期五)求和 \n" +
						  "function sumH5(){ \n" +
						  "  var vg5 =document.getElementsByName(\""+firstrowName+"\")[4].value;\n" +
						  "  var vh7 =document.getElementsByName(\""+line3Name+"\")[5].value;\n" +
						  "  var vh9 =document.getElementsByName(\""+line5Name+"\")[5].value;\n" +
						  "  var vh11=document.getElementsByName(\""+line7Name+"\")[5].value;\n" +
						  "  if(vg5==\"\" || vg5==null){vg5=0.00;}\n" +
						  "  if(vh7==\"\" || vh7==null){vh7=0.00;}\n"+
						  "  if(vh9==\"\" || vh9==null){vh9=0.00;}\n" +
						  "  if(vh11==\"\"|| vh11==null){vh11=0.00};\n" +
						  "  var g5 = reverseFormatAmount1(vg5);\n" +
						  "  var h7 = reverseFormatAmount1(vh7);\n" +
						  "  var h9 = reverseFormatAmount1(vh9);\n" +
						  "  var h11 = reverseFormatAmount1(vh11);\n" +
						  "  document.getElementsByName(\""+firstrowName+"\")[5].value = (parseFloat(g5)+parseFloat(h7)+parseFloat(h9)+parseFloat(h11)).toFixed(2) ; \n" +
						  "}" );
				//第二行求和
				sb.append("// 特殊文本框C6(星期一)求和 \n" +
						  "function sumC6(){ \n" +
						  "  var vc7 =document.getElementsByName(\""+line3Name+"\")[0].value; \n" +
						  "  var vc8 =document.getElementsByName(\""+line4Name+"\")[0].value; \n" +
						  "  var vc9 =document.getElementsByName(\""+line5Name+"\")[0].value; \n" +
						  "  var vc10=document.getElementsByName(\""+line6Name+"\")[0].value; \n" +
						  "  var vc11=document.getElementsByName(\""+line7Name+"\")[0].value; \n" +
						  "  var vc12=document.getElementsByName(\""+line8Name+"\")[0].value; \n" +
						  "  var vc13=document.getElementsByName(\""+line9Name+"\")[0].value; \n" +
						  "  var vc14=document.getElementsByName(\""+line10Name+"\")[0].value;\n" +
						  "  if( vc7==\"\" ||vc7==null){vc7=0.00;}\n" +
						  "  if( vc8==\"\" ||vc8==null){vc8=0.00;}\n" +
						  "  if( vc9==\"\" ||vc9==null){vc9=0.00;}\n" +
						  "  if( vc10==\"\" ||vc10==null){vc10=0.00;}\n" +
						  "  if( vc11==\"\" ||vc11==null){vc11=0.00;}\n" +
						  "  if( vc12==\"\" ||vc12==null){vc12=0.00;}\n" +
						  "  if( vc13==\"\" ||vc13==null){vc13=0.00;}\n" +
						  "  if( vc14==\"\" ||vc14==null){vc14=0.00;}\n" +
						  "  var c7 = reverseFormatAmount1(vc7);\n" +
						  "  var c8 = reverseFormatAmount1(vc8);\n" +
						  "  var c9 = reverseFormatAmount1(vc9);\n" +
						  "  var c10 = reverseFormatAmount1(vc10);\n" +
						  "  var c11 = reverseFormatAmount1(vc11);\n" +
						  "  var c12 = reverseFormatAmount1(vc12);\n" +
						  "  var c13 = reverseFormatAmount1(vc13);\n" +
						  "  var c14 = reverseFormatAmount1(vc14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[0].value = (parseFloat(c7)+parseFloat(c8)+parseFloat(c9)+parseFloat(c10)+parseFloat(c11) +parseFloat(c12) +parseFloat(c13) +parseFloat(c14)-parseFloat(c13)).toFixed(2)  ; \n" +
						  "}" );
				sb.append("// 特殊文本框D6(星期一)求和 \n" +
						  "function sumD6(){ \n" +
						  "  var vd7 =document.getElementsByName(\""+line3Name+"\")[1].value; \n" +
						  "  var vd8 =document.getElementsByName(\""+line4Name+"\")[1].value; \n" +
						  "  var vd9 =document.getElementsByName(\""+line5Name+"\")[1].value; \n" +
						  "  var vd10=document.getElementsByName(\""+line6Name+"\")[1].value; \n" +
						  "  var vd11=document.getElementsByName(\""+line7Name+"\")[1].value; \n" +
						  "  var vd12=document.getElementsByName(\""+line8Name+"\")[1].value; \n" +
						  "  var vd13=document.getElementsByName(\""+line9Name+"\")[1].value; \n" +
						  "  var vd14=document.getElementsByName(\""+line10Name+"\")[1].value;\n" +
						  "  if( vd7==\"\" ||vd7==null){vd7=0.00;}\n" +
						  "  if( vd8==\"\" ||vd8==null){vd8=0.00;}\n" +
						  "  if( vd9==\"\" ||vd9==null){vd9=0.00;}\n" +
						  "  if( vd10==\"\" ||vd10==null){vd10=0.00;}\n" +
						  "  if( vd11==\"\" ||vd11==null){vd11=0.00;}\n" +
						  "  if( vd12==\"\" ||vd12==null){vd12=0.00;}\n" +
						  "  if( vd13==\"\" ||vd13==null){vd13=0.00;}\n" +
						  "  if( vd14==\"\" ||vd14==null){vd14=0.00;}\n" +
						  "  var d7 = reverseFormatAmount1(vd7);\n" +
						  "  var d8 = reverseFormatAmount1(vd8);\n" +
						  "  var d9 = reverseFormatAmount1(vd9);\n" +
						  "  var d10 = reverseFormatAmount1(vd10);\n" +
						  "  var d11 = reverseFormatAmount1(vd11);\n" +
						  "  var d12 = reverseFormatAmount1(vd12);\n" +
						  "  var d13 = reverseFormatAmount1(vd13);\n" +
						  "  var d14 = reverseFormatAmount1(vd14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[1].value = (parseFloat(d7)+parseFloat(d8)+parseFloat(d9)+parseFloat(d10)+parseFloat(d11) +parseFloat(d12) +parseFloat(d13) +parseFloat(d14)).toFixed(2)  ; \n" +
						 "}" );
				sb.append("// 特殊文本框E6(星期二)求和 \n" +
						  "function sumE6(){ \n" +
						  "  var ve7 =document.getElementsByName(\""+line3Name+"\")[2].value; \n" +
						  "  var ve8 =document.getElementsByName(\""+line4Name+"\")[2].value; \n" +
						  "  var ve9 =document.getElementsByName(\""+line5Name+"\")[2].value; \n" +
						  "  var ve10=document.getElementsByName(\""+line6Name+"\")[2].value; \n" +
						  "  var ve11=document.getElementsByName(\""+line7Name+"\")[2].value; \n" +
						  "  var ve12=document.getElementsByName(\""+line8Name+"\")[2].value; \n" +
						  "  var ve13=document.getElementsByName(\""+line9Name+"\")[2].value; \n" +
						  "  var ve14=document.getElementsByName(\""+line10Name+"\")[2].value;\n" +
						  "  if( ve7==\"\" ||ve7==null){ve7=0.00;}\n" +
						  "  if( ve8==\"\" ||ve8==null){ve8=0.00;}\n" +
						  "  if( ve9==\"\" ||ve9==null){ve9=0.00;}\n" +
						  "  if( ve10==\"\" ||ve10==null){ve10=0.00;}\n" +
						  "  if( ve11==\"\" ||ve11==null){ve11=0.00;}\n" +
						  "  if( ve12==\"\" ||ve12==null){ve12=0.00;}\n" +
						  "  if( ve13==\"\" ||ve13==null){ve13=0.00;}\n" +
						  "  if( ve14==\"\" ||ve14==null){ve14=0.00;}\n" +
						  "  var e7 = reverseFormatAmount1(ve7);\n" +
						  "  var e8 = reverseFormatAmount1(ve8);\n" +
						  "  var e9 = reverseFormatAmount1(ve9);\n" +
						  "  var e10 = reverseFormatAmount1(ve10);\n" +
						  "  var e11 = reverseFormatAmount1(ve11);\n" +
						  "  var e12 = reverseFormatAmount1(ve12);\n" +
						  "  var e13 = reverseFormatAmount1(ve13);\n" +
						  "  var e14 = reverseFormatAmount1(ve14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[2].value = (parseFloat(e7)+parseFloat(e8)+parseFloat(e9)+parseFloat(e10)+parseFloat(e11) +parseFloat(e12) +parseFloat(e13) +parseFloat(e14)).toFixed(2)  ; \n" +
						 "}" );
				sb.append("// 特殊文本框F6(星期三)求和 \n" +
						  "function sumF6(){ \n" +
						  "  var vf7 =document.getElementsByName(\""+line3Name+"\")[3].value; \n" +
						  "  var vf8 =document.getElementsByName(\""+line4Name+"\")[3].value; \n" +
						  "  var vf9 =document.getElementsByName(\""+line5Name+"\")[3].value; \n" +
						  "  var vf10=document.getElementsByName(\""+line6Name+"\")[3].value; \n" +
						  "  var vf11=document.getElementsByName(\""+line7Name+"\")[3].value; \n" +
						  "  var vf12=document.getElementsByName(\""+line8Name+"\")[3].value; \n" +
						  "  var vf13=document.getElementsByName(\""+line9Name+"\")[3].value; \n" +
						  "  var vf14=document.getElementsByName(\""+line10Name+"\")[3].value;\n" +
						  "  if( vf7==\"\" ||vf7==null){vf7=0.00;}\n" +
						  "  if( vf8==\"\" ||vf8==null){vf8=0.00;}\n" +
						  "  if( vf9==\"\" ||vf9==null){vf9=0.00;}\n" +
						  "  if( vf10==\"\" ||vf10==null){vf10=0.00;}\n" +
						  "  if( vf11==\"\" ||vf11==null){vf11=0.00;}\n" +
						  "  if( vf12==\"\" ||vf12==null){vf12=0.00;}\n" +
						  "  if( vf13==\"\" ||vf13==null){vf13=0.00;}\n" +
						  "  if( vf14==\"\" ||vf14==null){vf14=0.00;}\n" +
						  "  var f7 = reverseFormatAmount1(vf7);\n" +
						  "  var f8 = reverseFormatAmount1(vf8);\n" +
						  "  var f9 = reverseFormatAmount1(vf9);\n" +
						  "  var f10 = reverseFormatAmount1(vf10);\n" +
						  "  var f11 = reverseFormatAmount1(vf11);\n" +
						  "  var f12 = reverseFormatAmount1(vf12);\n" +
						  "  var f13 = reverseFormatAmount1(vf13);\n" +
						  "  var f14 = reverseFormatAmount1(vf14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[3].value = (parseFloat(f7)+parseFloat(f8)+parseFloat(f9)+parseFloat(f10)+parseFloat(f11) +parseFloat(f12) +parseFloat(f13) +parseFloat(f14)).toFixed(2)  ; \n" +
						 "}" );
				sb.append("// 特殊文本框G6(星期四)求和 \n" +
						  "function sumG6(){ \n" +
						  "  var vg7 =document.getElementsByName(\""+line3Name+"\")[4].value; \n" +
						  "  var vg8 =document.getElementsByName(\""+line4Name+"\")[4].value; \n" +
						  "  var vg9 =document.getElementsByName(\""+line5Name+"\")[4].value; \n" +
						  "  var vg10=document.getElementsByName(\""+line6Name+"\")[4].value; \n" +
						  "  var vg11=document.getElementsByName(\""+line7Name+"\")[4].value; \n" +
						  "  var vg12=document.getElementsByName(\""+line8Name+"\")[4].value; \n" +
						  "  var vg13=document.getElementsByName(\""+line9Name+"\")[4].value; \n" +
						  "  var vg14=document.getElementsByName(\""+line10Name+"\")[4].value;\n" +
						  "  if( vg7==\"\" ||vg7==null){vg7=0.00;}\n" +
						  "  if( vg8==\"\" ||vg8==null){vg8=0.00;}\n" +
						  "  if( vg9==\"\" ||vg9==null){vg9=0.00;}\n" +
						  "  if( vg10==\"\" ||vg10==null){vg10=0.00;}\n" +
						  "  if( vg11==\"\" ||vg11==null){vg11=0.00;}\n" +
						  "  if( vg12==\"\" ||vg12==null){vg12=0.00;}\n" +
						  "  if( vg13==\"\" ||vg13==null){vg13=0.00;}\n" +
						  "  if( vg14==\"\" ||vg14==null){vg14=0.00;}\n" +
						  "  var g7 = reverseFormatAmount1(vg7);\n" +
						  "  var g8 = reverseFormatAmount1(vg8);\n" +
						  "  var g9 = reverseFormatAmount1(vg9);\n" +
						  "  var g10 = reverseFormatAmount1(vg10);\n" +
						  "  var g11 = reverseFormatAmount1(vg11);\n" +
						  "  var g12 = reverseFormatAmount1(vg12);\n" +
						  "  var g13 = reverseFormatAmount1(vg13);\n" +
						  "  var g14 = reverseFormatAmount1(vg14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[4].value = (parseFloat(g7)+parseFloat(g8)+parseFloat(g9)+parseFloat(g10)+parseFloat(g11) +parseFloat(g12) +parseFloat(g13) +parseFloat(g14)).toFixed(2)  ; \n" +
						 "}" );
				sb.append("// 特殊文本框H6(星期五)求和 \n" +
						  "function sumH6(){ \n" +
						  "  var vh7 =document.getElementsByName(\""+line3Name+"\")[5].value; \n" +
						  "  var vh8 =document.getElementsByName(\""+line4Name+"\")[5].value; \n" +
						  "  var vh9 =document.getElementsByName(\""+line5Name+"\")[5].value; \n" +
						  "  var vh10=document.getElementsByName(\""+line6Name+"\")[5].value; \n" +
						  "  var vh11=document.getElementsByName(\""+line7Name+"\")[5].value; \n" +
						  "  var vh12=document.getElementsByName(\""+line8Name+"\")[5].value; \n" +
						  "  var vh13=document.getElementsByName(\""+line9Name+"\")[5].value; \n" +
						  "  var vh14=document.getElementsByName(\""+line10Name+"\")[5].value;\n" +
						  "  if( vh7==\"\" ||vh7==null){vh7=0.00;}\n" +
						  "  if( vh8==\"\" ||vh8==null){vh8=0.00;}\n" +
						  "  if( vh9==\"\" ||vh9==null){vh9=0.00;}\n" +
						  "  if( vh10==\"\" ||vh10==null){vh10=0.00;}\n" +
						  "  if( vh11==\"\" ||vh11==null){vh11=0.00;}\n" +
						  "  if( vh12==\"\" ||vh12==null){vh12=0.00;}\n" +
						  "  if( vh13==\"\" ||vh13==null){vh13=0.00;}\n" +
						  "  if( vh14==\"\" ||vh14==null){vh14=0.00;}\n" +
						  "  var h7 = reverseFormatAmount1(vh7);\n" +
						  "  var h8 = reverseFormatAmount1(vh8);\n" +
						  "  var h9 = reverseFormatAmount1(vh9);\n" +
						  "  var h10 = reverseFormatAmount1(vh10);\n" +
						  "  var h11 = reverseFormatAmount1(vh11);\n" +
						  "  var h12 = reverseFormatAmount1(vh12);\n" +
						  "  var h13 = reverseFormatAmount1(vh13);\n" +
						  "  var h14 = reverseFormatAmount1(vh14);\n" +
						  "  document.getElementsByName(\""+line2Name+"\")[5].value = (parseFloat(h7)+parseFloat(h8)+parseFloat(h9)+parseFloat(h10)+parseFloat(h11) +parseFloat(h12) +parseFloat(h13) +parseFloat(h14)).toFixed(2)  ; \n" +
						 "}" );
				
				//第11行求和
				sb.append("// 特殊文本框C15(上周)求和 \n" +
						  "function sumC15(){ \n" +
						  "  var vc12=document.getElementsByName(\""+line12Name+"\")[0].value;\n" +
						  "  var vc13=document.getElementsByName(\""+line13Name+"\")[0].value;\n" +
						  "  var vc14=document.getElementsByName(\""+line14Name+"\")[0].value;\n" +
						  "  var vc15=document.getElementsByName(\""+line15Name+"\")[0].value;\n" +
						  "  var vc16=document.getElementsByName(\""+line16Name+"\")[0].value;\n" +
						  "  var vc17=document.getElementsByName(\""+line17Name+"\")[0].value;\n" +
						  "  if(vc12==\"\" ||vc12==null){vc12=0.00;}\n" +
						  "  if(vc13==\"\" ||vc13==null){vc13=0.00;}\n" +
						  "  if(vc14==\"\" ||vc14==null){vc14=0.00;}\n" +
						  "  if(vc15==\"\" ||vc15==null){vc15=0.00;}\n" +
						  "  if(vc16==\"\" ||vc16==null){vc16=0.00;}\n" +
						  "  if(vc17==\"\" ||vc17==null){vc17=0.00;}\n" +
						  "  var c12 = reverseFormatAmount1(vc12);\n" +
						  "  var c13 = reverseFormatAmount1(vc13);\n" +
						  "  var c14 = reverseFormatAmount1(vc14);\n" +
						  "  var c15 = reverseFormatAmount1(vc15);\n" +
						  "  var c16 = reverseFormatAmount1(vc16);\n" +
						  "  var c17 = reverseFormatAmount1(vc17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[0].value = (parseFloat(c12)+parseFloat(c13)+parseFloat(c14)+parseFloat(c15)+parseFloat(c16)+parseFloat(c17)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框D15(星期一)求和 \n" +
						  "function sumD15(){ \n" +
						  "  var vd12=document.getElementsByName(\""+line12Name+"\")[1].value;\n" +
						  "  var vd13=document.getElementsByName(\""+line13Name+"\")[1].value;\n" +
						  "  var vd14=document.getElementsByName(\""+line14Name+"\")[1].value;\n" +
						  "  var vd15=document.getElementsByName(\""+line15Name+"\")[1].value;\n" +
						  "  var vd16=document.getElementsByName(\""+line16Name+"\")[1].value;\n" +
						  "  var vd17=document.getElementsByName(\""+line17Name+"\")[1].value;\n" +
						  "  if(vd12==\"\" ||vd12==null){vd12=0.00;}\n" +
						  "  if(vd13==\"\" ||vd13==null){vd13=0.00;}\n" +
						  "  if(vd14==\"\" ||vd14==null){vd14=0.00;}\n" +
						  "  if(vd15==\"\" ||vd15==null){vd15=0.00;}\n" +
						  "  if(vd16==\"\" ||vd16==null){vd16=0.00;}\n" +
						  "  if(vd17==\"\" ||vd17==null){vd17=0.00;}\n" +
						  "  var d12 = reverseFormatAmount1(vd12);\n" +
						  "  var d13 = reverseFormatAmount1(vd13);\n" +
						  "  var d14 = reverseFormatAmount1(vd14);\n" +
						  "  var d15 = reverseFormatAmount1(vd15);\n" +
						  "  var d16 = reverseFormatAmount1(vd16);\n" +
						  "  var d17 = reverseFormatAmount1(vd17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[1].value = (parseFloat(d12)+parseFloat(d13)+parseFloat(d14)+parseFloat(d15)+parseFloat(d16)+parseFloat(d17)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框E15(星期二)求和 \n" +
						  "function sumE15(){ \n" +
						  "  var ve12=document.getElementsByName(\""+line12Name+"\")[2].value;\n" +
						  "  var ve13=document.getElementsByName(\""+line13Name+"\")[2].value;\n" +
						  "  var ve14=document.getElementsByName(\""+line14Name+"\")[2].value;\n" +
						  "  var ve15=document.getElementsByName(\""+line15Name+"\")[2].value;\n" +
						  "  var ve16=document.getElementsByName(\""+line16Name+"\")[2].value;\n" +
						  "  var ve17=document.getElementsByName(\""+line17Name+"\")[2].value;\n" +
						  "  if(ve12==\"\" ||ve12==null){ve12=0.00;}\n" +
						  "  if(ve13==\"\" ||ve13==null){ve13=0.00;}\n" +
						  "  if(ve14==\"\" ||ve14==null){ve14=0.00;}\n" +
						  "  if(ve15==\"\" ||ve15==null){ve15=0.00;}\n" +
						  "  if(ve16==\"\" ||ve16==null){ve16=0.00;}\n" +
						  "  if(ve17==\"\" ||ve17==null){ve17=0.00;}\n" +
						  "  var e12 = reverseFormatAmount1(ve12);\n" +
						  "  var e13 = reverseFormatAmount1(ve13);\n" +
						  "  var e14 = reverseFormatAmount1(ve14);\n" +
						  "  var e15 = reverseFormatAmount1(ve15);\n" +
						  "  var e16 = reverseFormatAmount1(ve16);\n" +
						  "  var e17 = reverseFormatAmount1(ve17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[2].value = (parseFloat(e12)+parseFloat(e13)+parseFloat(e14)+parseFloat(e15)+parseFloat(e16)+parseFloat(e17)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框F15(星期三)求和 \n" +
						  "function sumF15(){ \n" +
						  "  var vf12=document.getElementsByName(\""+line12Name+"\")[3].value;\n" +
						  "  var vf13=document.getElementsByName(\""+line13Name+"\")[3].value;\n" +
						  "  var vf14=document.getElementsByName(\""+line14Name+"\")[3].value;\n" +
						  "  var vf15=document.getElementsByName(\""+line15Name+"\")[3].value;\n" +
						  "  var vf16=document.getElementsByName(\""+line16Name+"\")[3].value;\n" +
						  "  var vf17=document.getElementsByName(\""+line17Name+"\")[3].value;\n" +
						  "  if(vf12==\"\" ||vf12==null){vf12=0.00;}\n" +
						  "  if(vf13==\"\" ||vf13==null){vf13=0.00;}\n" +
						  "  if(vf14==\"\" ||vf14==null){vf14=0.00;}\n" +
						  "  if(vf15==\"\" ||vf15==null){vf15=0.00;}\n" +
						  "  if(vf16==\"\" ||vf16==null){vf16=0.00;}\n" +
						  "  if(vf17==\"\" ||vf17==null){vf17=0.00;}\n" +
						  "  var f12 = reverseFormatAmount1(vf12);\n" +
						  "  var f13 = reverseFormatAmount1(vf13);\n" +
						  "  var f14 = reverseFormatAmount1(vf14);\n" +
						  "  var f15 = reverseFormatAmount1(vf15);\n" +
						  "  var f16 = reverseFormatAmount1(vf16);\n" +
						  "  var f17 = reverseFormatAmount1(vf17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[3].value = (parseFloat(f12)+parseFloat(f13)+parseFloat(f14)+parseFloat(f15)+parseFloat(f16)+parseFloat(f17)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框G15(星期四)求和 \n" +
						  "function sumG15(){ \n" +
						  "  var vg12=document.getElementsByName(\""+line12Name+"\")[4].value;\n" +
						  "  var vg13=document.getElementsByName(\""+line13Name+"\")[4].value;\n" +
						  "  var vg14=document.getElementsByName(\""+line14Name+"\")[4].value;\n" +
						  "  var vg15=document.getElementsByName(\""+line15Name+"\")[4].value;\n" +
						  "  var vg16=document.getElementsByName(\""+line16Name+"\")[4].value;\n" +
						  "  var vg17=document.getElementsByName(\""+line17Name+"\")[4].value;\n" +
						  "  if(vg12==\"\" ||vg12==null){vg12=0.00;}\n" +
						  "  if(vg13==\"\" ||vg13==null){vg13=0.00;}\n" +
						  "  if(vg14==\"\" ||vg14==null){vg14=0.00;}\n" +
						  "  if(vg15==\"\" ||vg15==null){vg15=0.00;}\n" +
						  "  if(vg16==\"\" ||vg16==null){vg16=0.00;}\n" +
						  "  if(vg17==\"\" ||vg17==null){vg17=0.00;}\n" +
						  "  var g12 = reverseFormatAmount1(vg12);\n" +
						  "  var g13 = reverseFormatAmount1(vg13);\n" +
						  "  var g14 = reverseFormatAmount1(vg14);\n" +
						  "  var g15 = reverseFormatAmount1(vg15);\n" +
						  "  var g16 = reverseFormatAmount1(vg16);\n" +
						  "  var g17 = reverseFormatAmount1(vg17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[4].value = (parseFloat(g12)+parseFloat(g13)+parseFloat(g14)+parseFloat(g15)+parseFloat(g16)+parseFloat(g17)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框H15(星期五)求和 \n" +
						  "function sumH15(){ \n" +
						  "  var vh12=document.getElementsByName(\""+line12Name+"\")[5].value;\n" +
						  "  var vh13=document.getElementsByName(\""+line13Name+"\")[5].value;\n" +
						  "  var vh14=document.getElementsByName(\""+line14Name+"\")[5].value;\n" +
						  "  var vh15=document.getElementsByName(\""+line15Name+"\")[5].value;\n" +
						  "  var vh16=document.getElementsByName(\""+line16Name+"\")[5].value;\n" +
						  "  var vh17=document.getElementsByName(\""+line17Name+"\")[5].value;\n" +
						  "  if(vh12==\"\" ||vh12==null){vh12=0.00;}\n" +
						  "  if(vh13==\"\" ||vh13==null){vh13=0.00;}\n" +
						  "  if(vh14==\"\" ||vh14==null){vh14=0.00;}\n" +
						  "  if(vh15==\"\" ||vh15==null){vh15=0.00;}\n" +
						  "  if(vh16==\"\" ||vh16==null){vh16=0.00;}\n" +
						  "  if(vh17==\"\" ||vh17==null){vh17=0.00;}\n" +
						  "  var h12 = reverseFormatAmount1(vh12);\n" +
						  "  var h13 = reverseFormatAmount1(vh13);\n" +
						  "  var h14 = reverseFormatAmount1(vh14);\n" +
						  "  var h15 = reverseFormatAmount1(vh15);\n" +
						  "  var h16 = reverseFormatAmount1(vh16);\n" +
						  "  var h17 = reverseFormatAmount1(vh17);\n" +
						  "  document.getElementsByName(\""+line11Name+"\")[5].value = (parseFloat(h12)+parseFloat(h13)+parseFloat(h14)+parseFloat(h15)+parseFloat(h16)+parseFloat(h17)).toFixed(2); \n" +
						  "}" );
				//第18行求和
				sb.append("// 特殊文本框C18(上周)求和 \n" +
						  "function sumC18(){ \n" +
						  "  var vc6 = document.getElementsByName(\""+line2Name+"\")[0].value;\n" +
						  "  var vc15= document.getElementsByName(\""+line11Name+"\")[0].value;\n" +
						  "  if(vc6==\"\" || vc6==null){vc6=0.00;}\n" +
						  "  if(vc15==\"\" || vc15==null){vc15=0.00;}\n" +
						  "  var c6 = reverseFormatAmount1(vc6);\n" +
						  "  var c15 = reverseFormatAmount1(vc15);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[0].value = (parseFloat(c6)-parseFloat(c15)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框D18(星期一)求和 \n" +
						  "function sumD18(){ \n" +
						  "  var vd15 =document.getElementsByName(\""+line11Name+"\")[1].value;\n" +
						  "  var vd6 = document.getElementsByName(\""+line2Name+"\")[1].value;\n" +
						  "  var vc26=document.getElementsByName(\""+line22Name+"\")[0].value;\n" +
						  "  if(vd15==\"\" || vd15==null){vd15=0.00;}\n" +
						  "  if(vd6==\"\" || vd6==null){vd6=0.00;}\n" +
						  "  if(vc26==\"\" || vc26==null){vc26=0.00;}\n" +
						  "  var d15 = reverseFormatAmount1(vd15);\n" +
						  "  var d6 = reverseFormatAmount1(vd6);\n" +
						  "  var c26 = reverseFormatAmount1(vc26);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[1].value = (parseFloat(c26)+parseFloat(d6)-parseFloat(d15)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框E18(星期二)求和 \n" +
						  "function sumE18(){ \n" +
						  "  var ve15 =document.getElementsByName(\""+line11Name+"\")[2].value;\n" +
						  "  var ve6 = document.getElementsByName(\""+line2Name+"\")[2].value;\n" +
						  "  var vd26=document.getElementsByName(\""+line22Name+"\")[1].value;\n" +
						  "  if(ve15==\"\" || ve15==null){ve15=0.00;}\n" +
						  "  if(ve6==\"\" || ve6==null){ve6=0.00;}\n" +
						  "  if(vd26==\"\" || vd26==null){vd26=0.00;}\n" +
						  "  var e15 = reverseFormatAmount1(ve15);\n" +
						  "  var e6 = reverseFormatAmount1(ve6);\n" +
						  "  var d26 = reverseFormatAmount1(vd26);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[2].value = (parseFloat(d26)+parseFloat(e6)-parseFloat(e15)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框F18(星期三)求和 \n" +
						  "function sumF18(){ \n" +
						  "  var vf15 =document.getElementsByName(\""+line11Name+"\")[3].value;\n" +
						  "  var vf6 = document.getElementsByName(\""+line2Name+"\")[3].value;\n" +
						  "  var ve26=document.getElementsByName(\""+line22Name+"\")[2].value;\n" +
						  "  if(vf15==\"\" || vf15==null){vf15=0.00;}\n" +
						  "  if(vf6==\"\" || vf6==null){vf6=0.00;}\n" +
						  "  if(ve26==\"\" || ve26==null){ve26=0.00;}\n" +
						  "  var f15 = reverseFormatAmount1(vf15);\n" +
						  "  var f6 = reverseFormatAmount1(vf6);\n" +
						  "  var e26 = reverseFormatAmount1(ve26);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[3].value = (parseFloat(e26)+parseFloat(f6)-parseFloat(f15)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框G18(星期四)求和 \n" +
						  "function sumG18(){ \n" +
						  "  var vg15 =document.getElementsByName(\""+line11Name+"\")[4].value;\n" +
						  "  var vg6 = document.getElementsByName(\""+line2Name+"\")[4].value;\n" +
						  "  var vf26=document.getElementsByName(\""+line22Name+"\")[3].value;\n" +
						  "  if(vg15==\"\" || vg15==null){vg15=0.00;}\n" +
						  "  if(vg6==\"\" || vg6==null){vg6=0.00;}\n" +
						  "  if(vf26==\"\" || vf26==null){vf26=0.00;}\n" +
						  "  var g15 = reverseFormatAmount1(vg15);\n" +
						  "  var g6 = reverseFormatAmount1(vg6);\n" +
						  "  var f26 = reverseFormatAmount1(vf26);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[4].value = (parseFloat(f26)+parseFloat(g6)-parseFloat(g15)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框H18(星期五)求和 \n" +
						  "function sumH18(){ \n" +
						  "  var vh15 =document.getElementsByName(\""+line11Name+"\")[5].value;\n" +
						  "  var vh6 = document.getElementsByName(\""+line2Name+"\")[5].value;\n" +
						  "  var vg26=document.getElementsByName(\""+line22Name+"\")[4].value;\n" +
						  "  if(vh15==\"\" || vh15==null){vh15=0.00;}\n" +
						  "  if(vh6==\"\" || vh6==null){vh6=0.00;}\n" +
						  "  if(vg26==\"\" || vg26==null){vg26=0.00;}\n" +
						  "  var h15 = reverseFormatAmount1(vh15);\n" +
						  "  var h6 = reverseFormatAmount1(vh6);\n" +
						  "  var g26 = reverseFormatAmount1(vg26);\n" +
						  "  document.getElementsByName(\""+line18Name+"\")[5].value = (parseFloat(g26)+parseFloat(h6)-parseFloat(h15)).toFixed(2) ; \n" +
						  "}" );
				//第22行求和
				sb.append("// 特殊文本框C22(上周)求和 \n" +
						  "function sumC22(){ \n" +
						  "  var vc22 = document.getElementsByName(\""+line18Name+"\")[0].value;\n" +
						  "  var vc23 = document.getElementsByName(\""+line19Name+"\")[0].value;\n" +
						  "  var vc24 = document.getElementsByName(\""+line20Name+"\")[0].value;\n" +
						  "  var vc25 = document.getElementsByName(\""+line21Name+"\")[0].value;\n" +
						  "  if(vc22==\"\" || vc22==null){vc22=0.00;}\n" +
						  "  if(vc23==\"\" || vc23==null){vc23=0.00;}\n" +
						  "  if(vc24==\"\" || vc24==null){vc24=0.00;}\n" +
						  "  if(vc25==\"\" || vc25==null){vc25=0.00;}\n" +
						  "  var c22 = reverseFormatAmount1(vc22);\n" +
						  "  var c23 = reverseFormatAmount1(vc23);\n" +
						  "  var c24 = reverseFormatAmount1(vc24);\n" +
						  "  var c25 = reverseFormatAmount1(vc25);\n" +
						  "  document.getElementsByName(\""+line22Name+"\")[0].value = (parseFloat(c22)+parseFloat(c23)+parseFloat(c24)-parseFloat(c25)).toFixed(2) ; \n" +
						  "}" );
				sb.append("// 特殊文本框d22(星期一)求和 \n" +
						  "function sumD22(){ \n" +
						  "  var vd22 = document.getElementsByName(\""+line18Name+"\")[1].value;\n" +
						  "  var vd23 = document.getElementsByName(\""+line19Name+"\")[1].value;\n" +
						  "  var vd24 = document.getElementsByName(\""+line20Name+"\")[1].value;\n" +
						  "  var vd25 = document.getElementsByName(\""+line21Name+"\")[1].value;\n" +
						  "  if(vd22==\"\" || vd22==null){vd22=0.00;}\n" +
						  "  if(vd23==\"\" || vd23==null){vd23=0.00;}\n" +
						  "  if(vd24==\"\" || vd24==null){vd24=0.00;}\n" +
						  "  if(vd25==\"\" || vd25==null){vd25=0.00;}\n" +
						  "  var d22 = reverseFormatAmount1(vd22);\n" +
						  "  var d23 = reverseFormatAmount1(vd23);\n" +
						  "  var d24 = reverseFormatAmount1(vd24);\n" +
						  "  var d25 = reverseFormatAmount1(vd25);\n" +
						  
						  "  document.getElementsByName(\""+line22Name+"\")[1].value = (parseFloat(d22)+parseFloat(d23)+parseFloat(d24)-parseFloat(d25)).toFixed(2) ; \n" +
						  "}");
				sb.append("// 特殊文本框e22(星期二)求和 \n" +
						  "function sumE22(){ \n" +
						  "  var ve22 = document.getElementsByName(\""+line18Name+"\")[2].value;\n" +
						  "  var ve23 = document.getElementsByName(\""+line19Name+"\")[2].value;\n" +
						  "  var ve24 = document.getElementsByName(\""+line20Name+"\")[2].value;\n" +
						  "  var ve25 = document.getElementsByName(\""+line21Name+"\")[2].value;\n" +
						  "  if(ve22==\"\" || ve22==null){ve22=0.00;}\n" +
						  "  if(ve23==\"\" || ve23==null){ve23=0.00;}\n" +
						  "  if(ve24==\"\" || ve24==null){ve24=0.00;}\n" +
						  "  if(ve25==\"\" || ve25==null){ve25=0.00;}\n" +
						  "  var e22 = reverseFormatAmount1(ve22);\n" +
						  "  var e23 = reverseFormatAmount1(ve23);\n" +
						  "  var e24 = reverseFormatAmount1(ve24);\n" +
						  "  var e25 = reverseFormatAmount1(ve25);\n" +
						  
						  "  document.getElementsByName(\""+line22Name+"\")[2].value = (parseFloat(e22)+parseFloat(e23)+parseFloat(e24)-parseFloat(e25)).toFixed(2) ; \n" +
						  "}");
				sb.append("// 特殊文本框f22(星期三)求和 \n" +
						  "function sumF22(){ \n" +
						  "  var vf22 = document.getElementsByName(\""+line18Name+"\")[3].value;\n" +
						  "  var vf23 = document.getElementsByName(\""+line19Name+"\")[3].value;\n" +
						  "  var vf24 = document.getElementsByName(\""+line20Name+"\")[3].value;\n" +
						  "  var vf25 = document.getElementsByName(\""+line21Name+"\")[3].value;\n" +
						  "  if(vf22==\"\" || vf22==null){vf22=0.00;}\n" +
						  "  if(vf23==\"\" || vf23==null){vf23=0.00;}\n" +
						  "  if(vf24==\"\" || vf24==null){vf24=0.00;}\n" +
						  "  if(vf25==\"\" || vf25==null){vf25=0.00;}\n" +
						  "  var f22 = reverseFormatAmount1(vf22);\n" +
						  "  var f23 = reverseFormatAmount1(vf23);\n" +
						  "  var f24 = reverseFormatAmount1(vf24);\n" +
						  "  var f25 = reverseFormatAmount1(vf25);\n" +
						  
						  "  document.getElementsByName(\""+line22Name+"\")[3].value = (parseFloat(f22)+parseFloat(f23)+parseFloat(f24)-parseFloat(f25)).toFixed(2) ; \n" +
						  "}");
				sb.append("// 特殊文本框G22(星期四)求和 \n" +
						  "function sumG22(){ \n" +
						  "  var vg22 = document.getElementsByName(\""+line18Name+"\")[4].value;\n" +
						  "  var vg23 = document.getElementsByName(\""+line19Name+"\")[4].value;\n" +
						  "  var vg24 = document.getElementsByName(\""+line20Name+"\")[4].value;\n" +
						  "  var vg25 = document.getElementsByName(\""+line21Name+"\")[4].value;\n" +
						  "  if(vg22==\"\" || vg22==null){vg22=0.00;}\n" +
						  "  if(vg23==\"\" || vg23==null){vg23=0.00;}\n" +
						  "  if(vg24==\"\" || vg24==null){vg24=0.00;}\n" +
						  "  if(vg25==\"\" || vg25==null){vg25=0.00;}\n" +
						  "  var g22 = reverseFormatAmount1(vg22);\n" +
						  "  var g23 = reverseFormatAmount1(vg23);\n" +
						  "  var g24 = reverseFormatAmount1(vg24);\n" +
						  "  var g25 = reverseFormatAmount1(vg25);\n" +
						  
						  "  document.getElementsByName(\""+line22Name+"\")[4].value = (parseFloat(g22)+parseFloat(g23)+parseFloat(g24)-parseFloat(g25)).toFixed(2) ; \n" +
						  "}");
				sb.append("// 特殊文本框h22(星期五)求和 \n" +
						  "function sumH22(){ \n" +
						  "  var vh22 = document.getElementsByName(\""+line18Name+"\")[5].value;\n" +
						  "  var vh23 = document.getElementsByName(\""+line19Name+"\")[5].value;\n" +
						  "  var vh24 = document.getElementsByName(\""+line20Name+"\")[5].value;\n" +
						  "  var vh25 = document.getElementsByName(\""+line21Name+"\")[5].value;\n" +
						  "  if(vh22==\"\" || vh22==null){vh22=0.00;}\n" +
						  "  if(vh23==\"\" || vh23==null){vh23=0.00;}\n" +
						  "  if(vh24==\"\" || vh24==null){vh24=0.00;}\n" +
						  "  if(vh25==\"\" || vh25==null){vh25=0.00;}\n" +
						  "  var h22 = reverseFormatAmount1(vh22);\n" +
						  "  var h23 = reverseFormatAmount1(vh23);\n" +
						  "  var h24 = reverseFormatAmount1(vh24);\n" +
						  "  var h25 = reverseFormatAmount1(vh25);\n" +
						  
						  "  document.getElementsByName(\""+line22Name+"\")[5].value = (parseFloat(h22)+parseFloat(h23)+parseFloat(h24)-parseFloat(h25)).toFixed(2) ; \n" +
						  "} \n");
				sb.append(" function   checkM(textname,no){ \n");
				sb.append("		if(document.getElementsByName(textname)[no].value==null||document.getElementsByName(textname)[no].value==\"\"){ \n");
				sb.append("	       document.getElementsByName(textname)[no].value=\"0.00\"; \n");
				sb.append("	   } \n"+
						  "		else{ \n" +
						  "          var   a=reverseFormatAmount1(document.getElementsByName(textname)[no].value);\n" +
						  "          t=/^(|[+-]?(0|([1-9]\\d*)|((0|([1-9]\\d*))?\\.\\d{1,2})){1,1})$/;\n" +
						  "          if(!t.test(a)){ \n " +
						  "             alert(\"数据输入有误!\"); \n" +
						  "             document.getElementsByName(textname)[no].select();\n" +
						  "             return false;\n" +
						  "         }\n" +
						  "     }\n");
				sb.append("	} \n");
							
				sb.append("</Script> \n");
				
				String levelcode = "";
				int rowSpan = 0;
				int countRoot = 1;
				int countChild = 1;
				long curLineIndex = 0;
				double[] balances = dao.findWeekPlanInfo(paramInfo);
				int TotleLine = 0;
				for(int i=0; i<list.size(); i++){
					FundPlanInfo entity = (FundPlanInfo)list.get(i);
					
					String rowName = "P_"+ String.valueOf(entity.getConfigid());	
//					下一行,如果下一行不是统计行，则往下加1行，得到下一行的名称
					String nextlineName = rowName;
					//String nextlineName = "P_"+ String.valueOf(entity.getConfigid()+1);
					//如果下一行是统计行，则加上统计行的行数，得到过滤掉后面的统计行之后的非统计行的名称
					//如果当前行有下一行
					if(i+1<list.size()){
						
						FundPlanInfo NextEntity = (FundPlanInfo)list.get(i+1);
						if(NextEntity.getParentid() == 0){//下一行是统计行
							TotleLine  = TotleLine + 1;
							//nextlineName = "P_"+ String.valueOf(entity.getConfigid()+TotleLine);
						}else{							  //下一行不是统计行，则行号加1，同时统计行归0
							nextlineName = "P_"+ String.valueOf(entity.getConfigid()+1);
							TotleLine = 0;
						}
						
					}
					if(entity.getParentid() == 0)
					{
						countChild = 1;
						levelcode = "";
						rowSpan = 0;
						levelcode = entity.getCode();
						rowSpan = createBiz.getRowSpan(levelcode);
						String h = createStr(countRoot++);
						sb.append("<tr> \n");
						sb.append("<td width=100 align=center><B>"+h+"</B></td> \n");
						sb.append("<td align=center nowrap><B>"+entity.getName().trim()+"</td> \n");
						
						int next = 1;
						
						
						//上周五
						sb.append("<td align=center> \n");
						sb.append("<input type='hidden' name='itemId' value='"+ entity.getPlanitemid() + "'> \n");						
						sb.append("<input type='hidden' name='configId' value='"+ entity.getConfigid() + "'> \n");
						
						sb.append("<input type='hidden' name='name' value='"+ entity.getName().trim() + "'> \n");
						sb.append("<input type='hidden' name='parentId' value='"+ entity.getParentid() + "'> \n");
						sb.append("<input type='hidden' name='code' value='"+ entity.getCode() + "'> \n");
						sb.append("<input type='hidden' name='modelId' value='"+ entity.getModelId() + "'> \n");
						
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='" + DataFormat.formatNumber(entity.getTotal(), 2)+ "'size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' readonly >");
						}else{
							sb.append("<input "+ isWrite +"  class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='" + DataFormat.formatNumber(entity.getTotal(), 2)+ "'size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"0\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange ='allSum();adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' >");
							
						}
						sb.append("</td> \n");
						
						
						sb.append("<td align=center> \n");
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getMondaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");'style='width:70' readonly>");
						}else{
							sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\"  onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getMondaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"1\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' ");
							
						}
						sb.append("</td> \n");
						
						sb.append("<td align=center> \n");
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getTuesdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur = 'allSum();' onchange = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");'style='width:70' readonly>");
						}else{
							sb.append("<input "+ isWrite +"  class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getTuesdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"2\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum();adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\"); ' style='width:70' >");
						}
						sb.append("</td> \n");
						
						sb.append("<td align=center> \n");
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getWednesdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' readonly>");
						}else{
							sb.append("<input "+ isWrite +"  class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getWednesdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"3\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' >");
						}
						
						sb.append("</td> \n");
						
						sb.append("<td align=center> \n");
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getThursdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70'readonly >");
						}else{
							sb.append("<input "+ isWrite +"  class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getThursdaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"4\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70' >");
							
						}
							
						sb.append("</td> \n");
						next = 0;
						sb.append("<td align=center> \n");
						if(h.equals("一")||h.equals("二")||h.equals("三")||h.equals("四")||h.equals("八")){
							sb.append("<input "+ isWrite +" style=\"background-color: #CCCCCC;color: #666666\" class=tar type='text' name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getFridaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();' onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");' style='width:70'readonly >");
						}else{
							sb.append("<input "+ isWrite +"  class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getFridaycapital(),2) +"' size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);'onblur = 'checkM(\""+rowName+"\",\"5\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "[0]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"[0]\",1,\"\",\"\");'style='width:70' >");
							
						}
						sb.append("</td> \n");
						
						
						if(paramInfo.getType()=="submit"){
							if(rowSpan==1){
								sb.append("<td valign=top align=center rowspan="+rowSpan+"><B><textarea "+ isWrite +" name="+ rowName +" rows="+1+" style='width:10,height:10;font-weight:bold'>"+ DataFormat.formatString(entity.getRemark()) +"</textarea></B></td>");
							}else{
								sb.append("<td valign=top align=center rowspan="+rowSpan+"><B><textarea "+ isWrite +" name="+ rowName +" rows="+(rowSpan*2)+" style='width:10,height:10;font-weight:bold'>"+ DataFormat.formatString(entity.getRemark()) +"</textarea></B></td>");
							}
						}
						sb.append("</tr>");
					}
					else
					{
						int next = 1;
						curLineIndex = 0;
						sb.append("<tr>");
						sb.append("<td width=100 align=center><B>"+ countChild++ +"</B></td> \n");
						sb.append("<input type='hidden' name='itemId' value='"+ entity.getPlanitemid() + "'> \n");						
						sb.append("<input type='hidden' name='configId' value='"+ entity.getConfigid() + "'> \n");
						
						sb.append("<input type='hidden' name='name' value='"+ entity.getName().trim() + "'> \n");
						sb.append("<input type='hidden' name='parentId' value='"+ entity.getParentid() + "'> \n");
						sb.append("<input type='hidden' name='code' value='"+ entity.getCode() + "'> \n");
						sb.append("<input type='hidden' name='modelId' value='"+ entity.getModelId() + "'> \n");
						
						sb.append("<td align=center>"+entity.getName().trim()+"</td>");
						if("集团公司短期存款".equals(entity.getName().trim())){
							sb.append("<td align=center> \n");
							sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='" +DataFormat.formatNumber(balances[0],2)+ "'size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'onchange = 'leftFirst(); allSum(); adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
							sb.append("</td> \n");
							
						}else if("集团公司长期存款".equals(entity.getName().trim())){
							sb.append("<td align=center> \n");
							sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='" +DataFormat.formatNumber(balances[1],2)+ "'size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
							sb.append("</td> \n");
						}else {
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\"name='"+ rowName + "' value='" + DataFormat.formatNumber(entity.getTotal(),2) + "'   size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70'>");
						
						sb.append("</td> \n");
						}
						
						curLineIndex = LINE_MONDAY;
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getMondaycapital(),2) + "'  size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_TUESDAY;						
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getTuesdaycapital(),2) + "'  size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_WEDNESDAY;						
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getWednesdaycapital(),2) + "'  size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
						sb.append("</td> \n");
						
						curLineIndex = LINE_THURSDAY;						
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+rowName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getThursdaycapital(),2) + "'  size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
						sb.append("</td> \n");
						next = 0;//下一行的第一个焦点，也就是第1个文本框
						curLineIndex = LINE_FRIDAY;						
						sb.append("<td align=center> \n");
						sb.append("<input "+ isWrite +" class=tar type='text' onDblClick=\"this.select();\" onkeydown=\"if(event.keyCode==13) document.all."+nextlineName+"["+(next++)+"].select();\" name='"+ rowName + "' value='"+ DataFormat.formatNumber(entity.getFridaycapital(),2) + "'  size='22' maxlength='70' onKeyPress = 'event.returnValue=IsDigit(false);' onblur='checkM(\""+rowName+"\",\""+curLineIndex+"\") ;adjustAmount(\"" + "frm001" +"\",\"" + rowName+ "["+ curLineIndex+"]\",1,\"" + "" +"\"," + 1+");allSum();'  onchange = 'allSum();  adjustAmount(\"" + "frm001" +"\",\"" + rowName +"["+ curLineIndex + "]\",1,\"\",\"\");' style='width:70' >");
						sb.append("</td> \n");
						
						
					}
					
//					if(entity.getExpression() != null && entity.getExpression().length() > 0)
//					{
//						sb.append("\n");
//						sb.append("<Script Language=\"JavaScript\"> \n");
//						sb.append("		var curObj = new Array();");
//						sb.append("		curObj.push('" + rowName + "');");
//						sb.append("		curObj.push('" + entity.getExpression() + "');");
//						sb.append("		ar.push(curObj); \n");
//						sb.append("</Script> \n");
//					}
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