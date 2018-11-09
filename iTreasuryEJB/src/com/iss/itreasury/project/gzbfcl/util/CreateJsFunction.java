package com.iss.itreasury.project.gzbfcl.util;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.QueryFoundsdispatchDetailInfo;
import com.iss.itreasury.util.DataFormat;

public class CreateJsFunction {
	
	
	
	/**
	 * 资金调度令-新增 动态增减信息输入框的控制函数的生成
	 * jiangqi
	 * 2010-11-25
	 * 
	 */
	public static void createToaddrowsJsFunction(long MaxNumber, JspWriter out) throws Exception
	{
		try
		{

			//long number=0;
			
			StringBuffer sVar=new StringBuffer();
			
			StringBuffer sRemove=new StringBuffer();
			
			StringBuffer toaddrows=new StringBuffer();
			
			StringBuffer todelrows=new StringBuffer();
			
			StringBuffer toAmount=new StringBuffer();
			
			sVar.append("var a=new Array();		\n");
			sVar.append("a[0]=1;	\n");
			
			for(int i=1;i<MaxNumber;i++){
				sVar.append("a["+i+"]=0;	\n");
			}
			for(int i=1;i<=2;i++){
	
								        
			      for(int j=0;j<MaxNumber;j++){	
								        
			    	  sVar.append("var tr"+i+j+"=\"<tr id=\\\"tr"+i+j+"\\\">\"+$(\"#tr"+i+j+"\").html()+\"</tr>\";	\n");	
								        
			      }
			      
			      for(int j=1;j<MaxNumber;j++){
			    	  
			    	  
			    	  sRemove.append("$(\"#tr"+i+j+"\").remove(); \n");
			    	  
			      }
			
			}		
						
			toaddrows.append("function toaddrow()	\n");
			toaddrows.append("{	\n");
			toaddrows.append("    var flag = true; \n");
			toaddrows.append("	for(var i = 0; i<a.length; i++)	\n");
			toaddrows.append("	{	\n");
			toaddrows.append("		if(a[i]==0)	\n");
			toaddrows.append("		{	\n");
			for(int i=0;i<MaxNumber;i++){
				
				if (i==0){
				
				toaddrows.append("			if(i=="+i+")	\n");
				toaddrows.append("			{	\n");
				toaddrows.append("				$(\"#minzhao\").append(tr1"+i+");	\n");
				toaddrows.append("		        $(\"#minzhao\").append(tr2"+i+"); 	\n");
				toaddrows.append("			}	\n");
				}
				else{
					
					toaddrows.append("		else if(i=="+i+")	\n");
					toaddrows.append("		{	\n");
					toaddrows.append("			$(\"#minzhao\").append(tr1"+i+");	\n");
					toaddrows.append("		    $(\"#minzhao\").append(tr2"+i+"); 	\n");
					toaddrows.append("		}	\n");
					
				}
				
			}
			
			toaddrows.append("			a[i]=1; \n");
			toaddrows.append("			flag = false; \n");
			toaddrows.append("			break; \n");
			toaddrows.append("		}	\n");
			toaddrows.append("	}	\n");
			toaddrows.append("    if(flag)	\n");
			toaddrows.append("    {	\n");
			toaddrows.append("   	alert(\"资金调度令双方信息不能超过"+MaxNumber+"条\"); \n");
			toaddrows.append("    }	\n");
			toaddrows.append("}	\n");

			
			
			
			todelrows.append("function todelrow(){	\n");
			todelrows.append("var hasChecked=0; \n");
			todelrows.append("$(\":checkbox\").each(function(){	\n");
			todelrows.append("if($(this).attr(\"checked\")==true)	\n");
			todelrows.append("{		\n");
			todelrows.append("hasChecked=hasChecked+1; \n");
			
			for (int i=0;i<MaxNumber;i++){
				if(i==0){
					todelrows.append("	if($(this).val()=="+i+")	\n");
					todelrows.append("	{	\n");
					todelrows.append("		$(\"#tr1"+i+"\").remove(); \n");
					todelrows.append("	    $(\"#tr2"+i+"\").remove(); \n");
					todelrows.append("	    a["+i+"]=0; \n");
					todelrows.append("	}	\n");
				}
				else{
					
					todelrows.append("	else if($(this).val()=="+i+")	\n");
					todelrows.append("	{	\n");
					todelrows.append("		$(\"#tr1"+i+"\").remove(); \n");
					todelrows.append("	    $(\"#tr2"+i+"\").remove(); \n");
					todelrows.append("	    a["+i+"]=0; \n");
					todelrows.append("	}	\n");
					
				}
			}
			todelrows.append("}	\n");
			todelrows.append("}); \n");
			todelrows.append("	if(hasChecked==0){	\n");
			todelrows.append("	alert(\"请选择所需删除的调度双方信息！\"); \n");
			todelrows.append("	}	\n");
			todelrows.append("} 	\n");

			
			
			toAmount.append("function toAmount(frm)	\n");
			toAmount.append("{		\n");	
			toAmount.append("	var AmountFlag=0;		\n");
			//toAmount.append("	for(var i = 0; i<a.length; i++)	\n");
			//toAmount.append("	{	\n");	
			for(int i=0;i<MaxNumber;i++){

					toAmount.append("		if(a["+i+"]==1)	{	\n");
					toAmount.append("			if(reverseFormatAmount(frm.Amount"+i+".value) <= 0){	\n");
					toAmount.append("					AmountFlag=AmountFlag+1;	\n");
					//toAmount.append("					AmountFlag=false;		\n");
					//toAmount.append("					return AmountFlag;	\n");
					toAmount.append("			}	\n");
					toAmount.append("		}	\n");
			}
			toAmount.append("					return AmountFlag;	\n");
			toAmount.append("}		\n");

			out.print(	
				"<script type=\"text/javascript\">	\n"		
					
				+sVar
				
				+sRemove
				
				+toaddrows
				
				+todelrows
				
				+toAmount
				
				+"</script>	\n" );

		}
		catch (Exception e)
		{
				e.printStackTrace();
		}
	}
	
	
	/**
	 * 资金调度令-修改 动态增减信息输入框的控制函数的生成
	 * jiangqi
	 * 2010-11-25
	 * 
	 */
	public static void createModifyToaddrowsJsFunction(long MaxNumber, JspWriter out) throws Exception
	{
		try
		{

			//long number=0;
			
			StringBuffer sVar=new StringBuffer();
			StringBuffer aVar=new StringBuffer();
			
			StringBuffer sRemove=new StringBuffer();
			
			StringBuffer toaddrows=new StringBuffer();
			
			StringBuffer todelrows=new StringBuffer();
			
			StringBuffer toAmount=new StringBuffer();
			
			aVar.append("var a=new Array();		\n");
			
			for(long i=0;i<MaxNumber;i++){
				aVar.append("a["+i+"]=1;	\n");
			}
			
			for(long i=1;i<=2;i++){
				
		        
			      for(long j=0;j<MaxNumber;j++){	
								        
			    	  sVar.append("var tr"+i+j+"=\"<tr id=\\\"tr"+i+j+"\\\">\"+$(\"#tr"+i+j+"\").html()+\"</tr>\";	\n");	
								        
			      }
			      
			
			}	
			
			sVar.append("var j=document.frmV003.allFoundsdispatchDetailNumber.value;	\n");
			for(long i=0;i<=MaxNumber;i++){
				sVar.append("if(j=="+i+"){	\n");
					for(long j=MaxNumber-1;j>=i;j--){
						sVar.append("$(\"#tr1"+j+"\").remove();		\n");
						sVar.append("$(\"#tr2"+j+"\").remove();		\n");
						sVar.append("a["+j+"]=0;	\n");
					}
				sVar.append("	}	\n");
				

			}

	
						
			toaddrows.append("function toaddrow()	\n");
			toaddrows.append("{	\n");
			toaddrows.append("    var flag = true; \n");
			toaddrows.append("	for(var i = 0; i<a.length; i++)	\n");
			toaddrows.append("	{	\n");
			toaddrows.append("		if(a[i]==0)	\n");
			toaddrows.append("		{	\n");
			for(long i=0;i<MaxNumber;i++){
				
				if (i==0){
				
				toaddrows.append("			if(i=="+i+")	\n");
				toaddrows.append("			{	\n");
				toaddrows.append("				$(\"#minzhao\").append(tr1"+i+");	\n");
				toaddrows.append("		        $(\"#minzhao\").append(tr2"+i+"); 	\n");
								
				toaddrows.append("		       $(\"#tr1"+i+"\").find(\"input\").each(function(){	\n");
				toaddrows.append("		         if($(this).attr(\"type\")==\"hidden\" )	\n");
				toaddrows.append("		       		        {	\n");
				toaddrows.append("		       			        $(this).val(\"-1\");	\n");
				toaddrows.append("		       		        }	\n");
				toaddrows.append("		       		        else if($(this).attr(\"type\")!=\"checkbox\")	\n");
				toaddrows.append("		       		        {	\n");
				toaddrows.append("								        $(this).val(\"\");	\n");
				toaddrows.append("							        }	\n");
				toaddrows.append("				 });	\n");
				
				toaddrows.append("		       $(\"#tr1"+i+"\").find(\"textarea\").each(function(){	\n");
				toaddrows.append("		         if($(this).attr(\"type\")==\"text\" )	\n");
				toaddrows.append("		       		        {	\n");
				toaddrows.append("		       			        $(this).val(\"\");	\n");
				toaddrows.append("		       		        }	\n");
				toaddrows.append("				 });	\n");
				
				
				toaddrows.append("				$(\"#tr2"+i+"\").find(\"input\").each(function(){	\n");
				toaddrows.append("					        if($(this).attr(\"type\")==\"hidden\")	\n");
				toaddrows.append("							        {	\n");
				toaddrows.append("								        $(this).val(\"-1\");	\n");
				toaddrows.append("							        }	\n");
				toaddrows.append("							        else if($(this).attr(\"type\")!=\"checkbox\")	\n");
				toaddrows.append("							        {	\n");
				toaddrows.append("								        $(this).val(\"\");	\n");
				toaddrows.append("							        }	\n");
				toaddrows.append("				 });	\n");

				
				
				toaddrows.append("		       $(\"#tr2"+i+"\").find(\"textarea\").each(function(){	\n");
				toaddrows.append("		         if($(this).attr(\"type\")==\"text\" )	\n");
				toaddrows.append("		       		        {	\n");
				toaddrows.append("		       			        $(this).val(\"\");	\n");
				toaddrows.append("		       		        }	\n");
				toaddrows.append("				 });	\n");
								
				toaddrows.append("			}	\n");
				}
				else{
					
					toaddrows.append("		else if(i=="+i+")	\n");
					toaddrows.append("		{	\n");
					toaddrows.append("			$(\"#minzhao\").append(tr1"+i+");	\n");
					toaddrows.append("		    $(\"#minzhao\").append(tr2"+i+"); 	\n");
										
					toaddrows.append("		       $(\"#tr1"+i+"\").find(\"input\").each(function(){	\n");
					toaddrows.append("		         if($(this).attr(\"type\")==\"hidden\" )	\n");
					toaddrows.append("		       		        {	\n");
					toaddrows.append("		       			        $(this).val(\"-1\");	\n");
					toaddrows.append("		       		        }	\n");
					toaddrows.append("		       		        else if($(this).attr(\"type\")!=\"checkbox\")	\n");
					toaddrows.append("		       		        {	\n");
					toaddrows.append("								        $(this).val(\"\");	\n");
					toaddrows.append("							        }	\n");
					toaddrows.append("				 });	\n");

					toaddrows.append("		       $(\"#tr1"+i+"\").find(\"textarea\").each(function(){	\n");
					toaddrows.append("		         if($(this).attr(\"type\")==\"text\" )	\n");
					toaddrows.append("		       		        {	\n");
					toaddrows.append("		       			        $(this).val(\"\");	\n");
					toaddrows.append("		       		        }	\n");
					toaddrows.append("				 });	\n");
					
					
					toaddrows.append("				$(\"#tr2"+i+"\").find(\"input\").each(function(){	\n");
					toaddrows.append("					        if($(this).attr(\"type\")==\"hidden\")	\n");
					toaddrows.append("							        {	\n");
					toaddrows.append("								        $(this).val(\"-1\");	\n");
					toaddrows.append("							        }	\n");
					toaddrows.append("							        else if($(this).attr(\"type\")!=\"checkbox\")	\n");
					toaddrows.append("							        {	\n");
					toaddrows.append("								        $(this).val(\"\");	\n");
					toaddrows.append("							        }	\n");
					toaddrows.append("				 });	\n");

					toaddrows.append("		       $(\"#tr2"+i+"\").find(\"textarea\").each(function(){	\n");
					toaddrows.append("		         if($(this).attr(\"type\")==\"text\" )	\n");
					toaddrows.append("		       		        {	\n");
					toaddrows.append("		       			        $(this).val(\"\");	\n");
					toaddrows.append("		       		        }	\n");
					toaddrows.append("				 });	\n");
					
					toaddrows.append("		}	\n");
					
				}
				
			}
			
			toaddrows.append("			a[i]=1; \n");
			toaddrows.append("			flag = false; \n");
			toaddrows.append("			break; \n");
			toaddrows.append("		}	\n");
			toaddrows.append("	}	\n");
			toaddrows.append("    if(flag)	\n");
			toaddrows.append("    {	\n");
			toaddrows.append("   	alert(\"资金调度令双方信息不能超过"+MaxNumber+"条\"); \n");
			toaddrows.append("    }	\n");
			toaddrows.append("}	\n");

			
			
			
			todelrows.append("function todelrow(){	\n");
			todelrows.append("var hasChecked=0; \n");
			todelrows.append("$(\":checkbox\").each(function(){	\n");
			todelrows.append("if($(this).attr(\"checked\")==true)	\n");
			todelrows.append("{		\n");
			todelrows.append("hasChecked=hasChecked+1; \n");
			
			for (long i=0;i<MaxNumber;i++){
				if(i==0){
					todelrows.append("	if($(this).val()=="+i+")	\n");
					todelrows.append("	{	\n");
					todelrows.append("		$(\"#tr1"+i+"\").remove(); \n");
					todelrows.append("	    $(\"#tr2"+i+"\").remove(); \n");
					todelrows.append("	    a["+i+"]=0; \n");
					todelrows.append("	}	\n");
				}
				else{
					
					todelrows.append("	else if($(this).val()=="+i+")	\n");
					todelrows.append("	{	\n");
					todelrows.append("		$(\"#tr1"+i+"\").remove(); \n");
					todelrows.append("	    $(\"#tr2"+i+"\").remove(); \n");
					todelrows.append("	    a["+i+"]=0; \n");
					todelrows.append("	}	\n");
					
				}
			}
			todelrows.append("}	\n");
			todelrows.append("}); \n");
			todelrows.append("	if(hasChecked==0){	\n");
			todelrows.append("	alert(\"请选择所需删除的调度双方信息！\"); \n");
			todelrows.append("	}	\n");
			todelrows.append("} 	\n");

			
			
			toAmount.append("function toAmount(frm)	\n");
			toAmount.append("{		\n");	
			toAmount.append("	var AmountFlag=0;		\n");
			//toAmount.append("	for(var i = 0; i<a.length; i++)	\n");
			//toAmount.append("	{	\n");	
			for(int i=0;i<MaxNumber;i++){

					toAmount.append("		if(a["+i+"]==1)	{	\n");
					toAmount.append("			if(reverseFormatAmount(frm.Amount"+i+".value) <= 0){	\n");
					toAmount.append("					AmountFlag=AmountFlag+1;	\n");
					//toAmount.append("					AmountFlag=false;		\n");
					//toAmount.append("					return AmountFlag;	\n");
					toAmount.append("			}	\n");
					toAmount.append("		}	\n");
			}
			toAmount.append("					return AmountFlag;	\n");
			toAmount.append("}		\n");

			out.print(	
				"<script type=\"text/javascript\">	\n"	
				
				+aVar
					
				+sVar
								
				+toaddrows
				
				+todelrows
				
				+toAmount
				
				+"</script>	\n" );

		}
		catch (Exception e)
		{
				e.printStackTrace();
		}
	}

}
