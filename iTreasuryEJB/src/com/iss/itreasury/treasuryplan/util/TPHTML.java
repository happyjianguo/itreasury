/*
 * Created on 2003-8-18
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.treasuryplan.util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.HtmlHeaderInfo;
import com.iss.itreasury.settlement.enddayprocess.process.EndDayProcess;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateDetailInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateInfo;
import com.iss.itreasury.treasuryplan.setting.dataentity.Trea_TPTemplateInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.ConfigConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.IExceptionMessage;
import com.iss.itreasury.util.Log;
import com.iss.itreasury.util.SessionMng;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TPHTML
{	
	private static String beginUsefulBalanceRowNo = "004";//�ڳ��������
	private static String beginBalanceRowNo = "001";//�ڳ����
	private static String formRowNo = "005";//�ʽ���Դ
	private static String toRowNo = "006";//�ʽ�����
	private static String endUsefulBalanceRowNo = "012";//��ĩ�������
	private static String endBalanceRowNo = "011";//��ĩ���
	private static String depositRowNo = "002";//���׼����
	private static String assureRowNo = "003";//��֤��
	private static String cashRowNo = "008";//�ʽ�ͷ��
	private static String adjustCashRowNo = "010";//���ں��ʽ�ͷ��
	private static String needMoneyRowNo = "007";//��Ҫ������
	private static String balanceRegulateRowNo = "009";//������
	
	/**
	 * ��ʾ�ʽ�ƻ�ģ����
	 * @param out
	 * @param aryHeadDateString ��������������λ
	 * @param aryColumnStatus ��Ԥ�⡱��ʵ�ʡ����ƻ��������λ��״̬��0������ʾ��1��Ϊֻ����2��Ϊ��д��
	 * @param vTemplate ����������Ϣ��
	 * @param lLevelCount �������ܹ��ļ���
	 * @param aryLevelStatus ÿ����Ĭ��״̬��1,display;0,hide;���磺���õ�һ��Ĭ����ʾ���������أ�������Ϊ{1}��
	 * 											�������ǰ����Ĭ����ʾ���������Ӧ����{1,1,1}
	 * @throws IException
	 */
	public static void showTreasuryPlanTemplate(JspWriter out, String[] aryHeadDateString, long[] aryColumnStatus, Vector vTemplate, long lLevelCount, long[] aryLevelStatus) throws IException
	{
		try
		{
			out.println("<!------------------------------------------------------------------------------------------>");
			out.println("					<input type=\"hidden\" name=\"IsNeedConvertToChinese\" value=\"-1\">");
			out.println("					<input type=\"hidden\" name=\"hdnTemplateLevelCount\" value=\""+lLevelCount+"\">");
			out.println("					<table width=\"99%\" border=\"0\" align=\"center\" height=\"70\" class=\"ItemList\">");
			out.println("						<tr id='treeTitle'  style='display:'>");
			out.println("							<td class=\"ItemTitle\" width=\"20%\" rowspan=\"2\">&nbsp;</td>");
			//��ʾ���е���Ŀ
			long lDisplayColumnCount = 0;
			for (int i = 0; i < aryColumnStatus.length; i++)
			{
				if (aryColumnStatus[i] > 0)
				{
					lDisplayColumnCount++;
				}
			}
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				out.println("							<input type=\"hidden\" name=\"hdnDate\" value=\"" + aryHeadDateString[i] + "\">");
				out.println("							<td class=\"ItemTitle\" height=\"20\" colspan=\"" + lDisplayColumnCount + "\">");
				out.println("								<div align=\"center\">" + aryHeadDateString[i] + "</div>");
				out.println("							</td>");
			}
			out.println("						</tr>");
			out.println("						<tr id='treeTitle'  style='display:'>");
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				if (aryColumnStatus[0] > 0)
				{
					out.println("							<td class=\"ItemTitle\" height=\"20\">");
					out.println("								<div align=\"center\">Ӧ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[1] > 0)
				{
					out.println("							<td class=\"ItemTitle\" height=\"20\">");
					out.println("								<div align=\"center\">ʵ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[2] > 0)
				{
					out.println("							<td class=\"ItemTitle\" height=\"20\">");
					out.println("								<div align=\"center\">�ƻ�</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[3] > 0)
				{
					out.println("							<td class=\"ItemTitle\" height=\"20\">");
					out.println("								<div align=\"center\">��</div>");
					out.println("							</td>");
				}
			}
			out.println("						</tr>");
			if (vTemplate != null && vTemplate.size() > 0)
			{
				Iterator it = vTemplate.iterator();
				while (it.hasNext())
				{
					TemplateInfo info = (TemplateInfo) it.next();
					ArrayList detailInfos = info.getDetailInfos();
					String strDisplay = "none";
					if (aryLevelStatus != null && info.getLineLevel() <= aryLevelStatus.length && aryLevelStatus[(int) info.getLineLevel() - 1] == 1)
					{
						strDisplay = "";
					}
					out.println("						<tr id=\"tree" + info.getLineNo() + "\"  style='display:" + strDisplay + "'>");
					out.println("						<input type=\"hidden\" name=\"hdnLevel" + info.getLineNo() + "\" value=\"" + info.getLineLevel() + "\">");
					out.println("						<input type=\"hidden\" name=\"hdnLineID" + info.getLineNo() + "\" value=\"" + info.getLineID() + "\">");
					out.println("						<input type=\"hidden\" name=\"hdnLineNo\" value=\""+info.getLineNo()+"\">");
					String strPrefix = "";
					for (int i = 1; i < info.getLineLevel(); i++)
					{
						strPrefix = "&nbsp;&nbsp;&nbsp;&nbsp;" + strPrefix;
					}
					out.println("							<td class=\"ItemBody\" nowrap>");
					if (info.getIsLeaf() == 1)
					{//ĩ�����
						out.println("								" + strPrefix + "<input name=\"button" + info.getLineNo() + "\" type=\"button\" class=\"button\" value=\"-\" onclick=\"showChild('" + info.getLineNo()
								+ "');\">");
					}
					else
					{//��ĩ�����
						out.println("								" + strPrefix + "<input name=\"button" + info.getLineNo() + "\" type=\"button\" class=\"button\" value=\"+\" onclick=\"showChild('" + info.getLineNo()
								+ "');\">");
					}
					out.println("								" + info.getLineName());
					out.println("							</td>");
					java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
					//String s=nf.format(33334444.88);
					
					for (int i = 0; i < detailInfos.size(); i++)
					{
						if (aryColumnStatus[0] > 0)
						{
							String strReadOnly = ((info.getIsLeaf() != 1 || aryColumnStatus[0] == 1) ? "disabled" : "");
							//String strForecastAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getForecastAmount());
							String strForecastAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getForecastAmount());
							out.println("							<td class=\"ItemBody\" height=\"20\">");
							out.println("								<input name=\"txtForcast" + i + "_" + info.getLineNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strForecastAmount + "\" "
									+ strReadOnly + ">");
							out.println("							</td>");
						}
						if (aryColumnStatus[1] > 0)
						{
							String strReadOnly = ((info.getIsLeaf() != 1 || aryColumnStatus[1] == 1) ? "disabled" : "");
							//String strActualAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getActualAmount());
							String strActualAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getActualAmount());
							out.println("							<td class=\"ItemBody\" height=\"20\">");
							out.println("								<input name=\"txtActual" + i + "_" + info.getLineNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strActualAmount + "\" " + strReadOnly
									+ ">");
							out.println("							</td>");
						}
						if (aryColumnStatus[2] > 0)
						{
							String strReadOnly = ((info.getIsLeaf() != 1 || aryColumnStatus[2] == 1 || info.getIsReadOnly() == 1) ? "readonly" : "onBlur=\"doCount('" + i + "','" + info.getLineNo() + "',this);\" onFocus=\"doFocus('"
									+ i + "','" + info.getLineNo() + "');\"");
							//String strPlanAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getPlanAmount());
							String strPlanAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount());
							double dPlanAmount = ((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount();
							out.println("							<td class=\"ItemBody\" height=\"20\">");
							out.println("								<input name=\"txtPlan" + i + "_" + info.getLineNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strPlanAmount + "\" " + strReadOnly
									+ ">");
							out.println("								<input name=\"hdnPlan" + i + "_" + info.getLineNo() + "\" type=\"hidden\" value=\"" + dPlanAmount + "\">");
							out.println("								<input name=\"hdnPlanIsChange" + i + "_" + info.getLineNo() + "\" type=\"hidden\" value=\"0\">");
							out.println("							</td>");
						}
						if (aryColumnStatus[3] > 0)
						{
							String strReadOnly = ((info.getIsLeaf() != 1 || aryColumnStatus[3] == 1) ? "disabled" : "");
							//String strDifferenceAmount = DataFormat.formatDisabledAmount(((TemplateDetailInfo)detailInfos.get(i)).getDifferenceAmount());
							String strDifferenceAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getDifferenceAmount());
							out.println("							<td class=\"ItemBody\" height=\"20\">");
							out.println("								<input name=\"txtDifference" + i + "_" + info.getLineNo() + "\" size=\"10\" type=\"text\" class=\"tar\" value=\"" + strDifferenceAmount + "\" "
									+ strReadOnly + ">");
							out.println("							</td>");
						}
					}
					out.println("						</tr>");
				}
			}
			out.println("					</table>");
			out.println("<script language=\"javascript\">");
			if (vTemplate != null && vTemplate.size() > 0)
			{
				out.println("	var arrayLineNo = new Array(" + vTemplate.size() + ");");
				out.println("	var arrayLineNoStatus = new Array(" + vTemplate.size() + ");");
				Iterator it = vTemplate.iterator();
				int index = 0;
				while (it.hasNext())
				{
					TemplateInfo info = (TemplateInfo) it.next();
					long lIsDefaultDisplay = -1;
					if (aryLevelStatus != null && info.getLineLevel() <= aryLevelStatus.length && aryLevelStatus[(int) info.getLineLevel() - 1] == 1)
					{
						lIsDefaultDisplay = 1;
					}
					out.println("	arrayLineNo[" + index + "]='" + info.getLineNo() + "';");
					out.println("	arrayLineNoStatus[" + index + "]=" + lIsDefaultDisplay + ";");
					index++;
				}
			}
			out.println("function showChild(treeLineNo)");
			out.println("{");
			out.println("	var lLevel;");
			out.println("	var lLevelCount=document.all.hdnLevelCount.value;");
			out.println("	eval(\"lLevel=document.all.hdnLevel\"+treeLineNo+\".value\");");
			out.println("	if (lLevel < lLevelCount)");
			out.println("	{");
			out.println("		var temp;");
			out.println("		eval(\"temp=document.all.button\"+treeLineNo+\".value\");");
			out.println("		if (temp == '+')");
			out.println("		{");
			out.println("			eval(\"document.all.button\"+treeLineNo+\".value='-'\");");
			out.println("			closeOrOpen(treeLineNo,1);");
			out.println("		}");
			out.println("		else");
			out.println("		{");
			out.println("			eval(\"document.all.button\"+treeLineNo+\".value='+'\");");
			out.println("			closeOrOpen(treeLineNo,-1);");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("");
			out.println("function closeOrOpen(treeLineNo,nType)");
			out.println("{");
			out.println("	for(var i=0;i<arrayLineNo.length;i++)");
			out.println("	{");
			out.println("		if (arrayLineNo[i].length > treeLineNo.length)");
			out.println("		{");
			out.println("			if (nType == 1)");
			out.println("			{");
			out.println("				//չ��");
			out.println("				if (arrayLineNo[i].length == (treeLineNo.length + 4) && arrayLineNo[i].indexOf(treeLineNo) == 0)");
			out.println("				{");
			out.println("					eval(\"document.all.tree\"+arrayLineNo[i]+\".style.display=''\");");
			out.println("					arrayLineNoStatus[i] = 0;");
			out.println("				}");
			out.println("			}");
			out.println("			else");
			out.println("			{");
			out.println("				if (arrayLineNoStatus[i] == 0 && arrayLineNo[i].length > treeLineNo.length && arrayLineNo[i].indexOf(treeLineNo) == 0)");
			out.println("				{");
			out.println("					eval(\"document.all.tree\"+arrayLineNo[i]+\".style.display='none'\");");
			out.println("					eval(\"document.all.button\"+arrayLineNo[i]+\".value='+'\");");
			out.println("					arrayLineNoStatus[i] = 1;");
			out.println("				}");
			out.println("			}");
			out.println("		}");
			out.println("	}");
			out.println("}");
			out.println("");
			out.println("function doFocus(i,sLineNo)");
			out.println("{");
			out.println("	adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,2);");
			out.println("}");
			out.println("");
			out.println("function doBalance(i,obj)");
			out.println("{");
			out.println("	eval(\"fromPlanObj=document.all.txtPlan\"+i+\"_\"+\""+formRowNo+"\");");
			out.println("	eval(\"toPlanObj=document.all.txtPlan\"+i+\"_\"+\""+toRowNo+"\");");
			out.println("	eval(\"beginPlanObj=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\");");
			out.println("	eval(\"beginUsefulPlanObj=document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\");");
			out.println("	eval(\"endPlanObj=document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\");");
			out.println("	eval(\"endUsefulPlanObj=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\");");			
			out.println("	eval(\"depositPlanObj=document.all.txtPlan\"+i+\"_\"+\""+depositRowNo+"\");");
			out.println("	eval(\"assurePlanObj=document.all.txtPlan\"+i+\"_\"+\""+assureRowNo+"\");");	
			
			out.println("	eval(\"cashPlanObj=document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\");");
			out.println("	eval(\"needMoneyPlanObj=document.all.txtPlan\"+i+\"_\"+\""+needMoneyRowNo+"\");");
			out.println("	eval(\"adjustCashPlanObj=document.all.txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\");");
			out.println("	eval(\"balanceRegulatePlanObj=document.all.txtPlan\"+i+\"_\"+\""+balanceRegulateRowNo+"\");");
			
			out.println("	var beginPlanAmount;");
			out.println("	var beginUsefulPlanAmount;");
			out.println("	var endPlanAmount;");
			out.println("	var endUsefulPlanAmount;");
			
			out.println("");
			out.println("	if (beginPlanObj!=null && depositPlanObj!=null && assurePlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\")");
			out.println("	{");
			out.println("		eval(\"beginPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
			out.println("		beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
			out.println("");
			out.println("		var depositPlanAmount;");
			out.println("		eval(\"depositPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+depositRowNo+"\"+\".value\");");
			out.println("		depositPlanAmount = reverseFormatAmount(depositPlanAmount);");
			out.println("");
			out.println("		var assurePlanAmount;");
			out.println("		eval(\"assurePlanAmount=document.all.txtPlan\"+i+\"_\"+\""+assureRowNo+"\"+\".value\");");
			out.println("		assurePlanAmount = reverseFormatAmount(assurePlanAmount);");
			out.println("");
			out.println("		beginUsefulPlanAmount = parseFloat(beginPlanAmount) - parseFloat(depositPlanAmount) - parseFloat(assurePlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = 1\");");
			out.println("");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\",1);");
			out.println("");
			out.println("	}");
			out.println("");
			out.println("	if (fromPlanObj==null||toPlanObj==null){return;}");
			out.println("");
			out.println("	var fromPlanAmount;");
			out.println("	var toPlanAmount;");
			out.println("	eval(\"fromPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+formRowNo+"\"+\".value\");");
			out.println("	eval(\"toPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+toRowNo+"\"+\".value\");");
			out.println("	fromPlanAmount = reverseFormatAmount(fromPlanAmount);");
			out.println("	toPlanAmount = reverseFormatAmount(toPlanAmount);");
			out.println("");
			out.println("	if (beginPlanObj!=null && endPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\")");
			out.println("	{");
			out.println("		eval(\"beginPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
			out.println("		beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
			out.println("");
			out.println("		endPlanAmount = parseFloat(beginPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = 1\");");
			out.println("");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\",1);");
			out.println("");
			out.println("	}");
			out.println("");
			
			out.println(" if (beginPlanObj!=null && endPlanObj!=null && i<"+(aryHeadDateString.length-1)+")");
			out.println("{");
			out.println("		eval(\"endPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value\");");
			out.println("		endPlanAmount = reverseFormatAmount(endPlanAmount);");
			out.println("");
			out.println(" 		var temp = parseFloat(i)+1;");
			out.println("		eval(\"document.all.txtPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = endPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = 1\");");
			out.println("");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\",1);");
			out.println("}");
			out.println("");
			
			out.println("	if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\")");
			out.println("	{");
			out.println("		eval(\"beginUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value\");");
			out.println("		beginUsefulPlanAmount = reverseFormatAmount(beginUsefulPlanAmount);");
			out.println("");
			out.println("		endUsefulPlanAmount = parseFloat(beginUsefulPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = 1\");");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\",1);");
			out.println("	}");
			out.println("");
			
			out.println(" if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null && i<"+(aryHeadDateString.length-1)+")");
			out.println("{");
			out.println("		eval(\"endUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value\");");
			out.println("		endUsefulPlanAmount = reverseFormatAmount(endUsefulPlanAmount);");
			out.println("");
			out.println(" 		var temp = parseFloat(i)+1;");
			out.println("		eval(\"document.all.txtPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = 1\");");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\",1);");
			out.println("}");
			
			out.println("");
			out.println("	if (cashPlanObj!=null && endUsefulPlanObj!=null && needMoneyPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+cashRowNo+"\")");
			out.println("	{");
			out.println("		eval(\"endUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value\");");
			out.println("		endUsefulPlanAmount = reverseFormatAmount(endUsefulPlanAmount);");
			out.println("");
			out.println("		var needMoneyPlanAmount;");
			out.println("		eval(\"needMoneyPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+needMoneyRowNo+"\"+\".value\");");
			out.println("		needMoneyPlanAmount = reverseFormatAmount(needMoneyPlanAmount);");
			out.println("");
			out.println("		var cashPlanAmount;");
			out.println("		cashPlanAmount = parseFloat(endUsefulPlanAmount) - parseFloat(needMoneyPlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value = cashPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value = cashPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+cashRowNo+"\"+\".value = 1\");");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+cashRowNo+"\",1);");
			out.println("	}");
			out.println("");
			
			out.println("	if (adjustCashPlanObj!=null && cashPlanObj!=null && balanceRegulatePlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\")");
			out.println("	{");
			out.println("		var cashPlanAmount;");
			out.println("		eval(\"cashPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value\");");
			out.println("		cashPlanAmount = reverseFormatAmount(cashPlanAmount);");
			out.println("");
			out.println("		var balanceRegulatePlanAmount;");
			out.println("		eval(\"balanceRegulatePlanAmount=document.all.txtPlan\"+i+\"_\"+\""+balanceRegulateRowNo+"\"+\".value\");");
			out.println("		balanceRegulatePlanAmount = reverseFormatAmount(balanceRegulatePlanAmount);");
			out.println("");
			out.println("		var adjustCashPlanAmount;");
			out.println("		adjustCashPlanAmount = parseFloat(cashPlanAmount) - parseFloat(balanceRegulatePlanAmount)");
			out.println("");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = adjustCashPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = adjustCashPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = 1\");");
			out.println("		adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\",1);");
			out.println("	}");
			out.println("");
			
			out.println("}");
			out.println("");
			out.println("function doCount(i,sLineNo,obj)");
			out.println("{");
			out.println("	var hdnPlanAmount;");
			out.println("	var txtPlanAmount;");
			out.println("	eval(\"hdnPlanAmount=document.all.hdnPlan\"+i+\"_\"+sLineNo+\".value\");");
			out.println("	eval(\"txtPlanAmount=document.all.txtPlan\"+i+\"_\"+sLineNo+\".value\");");
			out.println("	if (isFloat(txtPlanAmount) == false || txtPlanAmount == \"\" )");
			out.println("	{");
			out.println("		alert(\"��������ȷ�����֣�\");");
			out.println("		eval(\"document.all.txtPlan\"+i+\"_\"+sLineNo+\".value=\"+hdnPlanAmount);");
			out.println("		return;");
			out.println("	}");
			out.println("	adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,1);");
			out.println("	hdnPlanAmount = reverseFormatAmount(hdnPlanAmount);");
			out.println("	txtPlanAmount = reverseFormatAmount(txtPlanAmount);");
			out.println("");
			out.println("	if (parseFloat(hdnPlanAmount) != parseFloat(txtPlanAmount))");
			out.println("	{");
			out.println("		eval(\"document.all.hdnPlan\"+i+\"_\"+sLineNo+\".value = txtPlanAmount\");");
			out.println("		eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+sLineNo+\".value = 1\");");
			out.println("");
			out.println("		var diffAmount = parseFloat(txtPlanAmount) - parseFloat(hdnPlanAmount);");
			out.println("		");
			out.println("		while(sLineNo.length > 4)");
			out.println("		{");
			out.println("			sLineNo = sLineNo.substring(0,sLineNo.length-4);");
			out.println("			var tempAmount;");
			out.println("			eval(\"tempAmount=document.all.txtPlan\"+i+\"_\"+sLineNo+\".value\");");
			out.println("			tempAmount = parseFloat(reverseFormatAmount(tempAmount))+parseFloat(diffAmount);");
			out.println("			eval(\"document.all.txtPlan\"+i+\"_\"+sLineNo+\".value = tempAmount\");");
			out.println("			eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+sLineNo+\".value = 1\");");
			//out.println("			alert('t');");
			out.println("			adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,1);");
			out.println("		}");
			out.println("	}");
			out.println("	doBalance(i,obj)");
			out.println("}");
			out.println("");
			out.println("</script>");
			out.println("<!------------------------------------------------------------------------------------------>");
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
    
   
    /**
     *@Description: ��ʾ�ʽ�ƻ�ģ����(���ʽ�ƻ�ģ������ʹ��)
     *void
     *@param out
     *@param vTemplate ����������Ϣ��
     *@param lLevelCount �������ܹ��ļ���
     *@param aryLevelStatus ÿ����Ĭ��״̬��1,display;0,hide;���磺���õ�һ��Ĭ����ʾ���������أ�������Ϊ{1}��
     *                                          �������ǰ����Ĭ����ʾ���������Ӧ����{1,1,1}
     *@throws IException
     *@author jason,15th Nov 2005
     */
    public static void showTreasuryPlanTemplateForSetting( JspWriter out , Vector vTemplate , long lLevelCount , long[] aryLevelStatus ) throws IException
    {
        try
        {
            Trea_TPTemplateInfo trea_TPTemplateInfo = new Trea_TPTemplateInfo();
            out.println("<!------------------------------------------------------------------------------------------>");
            out.println("                <input type=\"hidden\" name=\"IsNeedConvertToChinese\" value=\"-1\">");
            out.println("                <input type=\"hidden\" name=\"hdnTemplateLevelCount\" value=\""+lLevelCount+"\">");
            out.println("                   <table width=\"99%\" border=\"0\" align=\"center\" class=\"ItemList\">");
            
            //�����һ�� ����ͷ
            out.println("                       <tr>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> ��Ŀ��� </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> ��Ŀ���� </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> ��Ŀ���� </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> �ϼ���Ŀ </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> �Ƿ�Ҷ�� </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> �Ƿ�ֻ�� </td>");
            out.println("                           <td class=\"ItemTitle\" align=\"center\" nowrap> ȡ���߼� </td>");
            out.println("                       </tr>");
            
            
            //ѭ���������Ŀ���������
            
            Connection conn = null;
            conn = Database.getConnection();
            
            if (vTemplate != null && vTemplate.size() > 0)
            {
                Iterator it = vTemplate.iterator();
                while (it.hasNext())
                {
                    Trea_TPTemplateInfo info = (Trea_TPTemplateInfo) it.next();
                    
                    String strDisplay = "none";
                    
                    if (aryLevelStatus != null && 
                        info.getLineLevel() <= aryLevelStatus.length && 
                        aryLevelStatus[(int) info.getLineLevel() - 1] == 1)
                    {
                        strDisplay = "";
                    }
                    
                    //���"��Ŀ����"����ת������ҳ�棺
                    //String strDetailPageURL = "/NASApp/iTreasury-treasuryplan/treasuryplan/setting/control/c402.jsp?lineId="+info.getId()+"&strSuccessPageURL=/treasuryplan/setting/view/v402.jsp"+"&strFailPageURL=/treasuryplan/setting/control/c401.jsp";
                    
                    out.println("                    <tr id=\"tree" + info.getLineNo() + "\"  style='display:" + strDisplay + "'>");
                    out.println("                       <input type=\"hidden\" name=\"hdnLevel" + info.getLineNo() + "\" value=\"" + info.getLineLevel() + "\">");
                    out.println("                       <input type=\"hidden\" name=\"hdnLineID" + info.getLineNo() + "\" value=\"" + info.getId() + "\">");
                    out.println("                       <input type=\"hidden\" name=\"hdnLineNo\" value=\""+info.getLineNo()+"\">");
                    String strPrefix = "";
                    for (int i = 1; i < info.getLineLevel(); i++)
                    {
                        strPrefix = "&nbsp;&nbsp;&nbsp;&nbsp;" + strPrefix;
                    }
                    
                    //��һ�У���Ŀ���
                    out.println("                           <td class=\"ItemBody\" nowrap>");
                    if (info.getIsLeaf() == 1)
                    {//ĩ�����
                        out.println("                               " + strPrefix + "<input name=\"button" + info.getLineNo() + "\" type=\"button\" class=\"button\" value=\"-\" onclick=\"showChild('" + info.getLineNo()
                                + "');\">");
                    }
                    else
                    {//��ĩ�����
                        out.println("                               " + strPrefix + "<input name=\"button" + info.getLineNo() + "\" type=\"button\" class=\"button\" value=\"+\" onclick=\"showChild('" + info.getLineNo()
                                + "');\">");
                    }
                    out.println("                               " + DataFormat.formatString(info.getLineNo()));
                    out.println("                           </td>");
                    
                    //�ڶ��У���Ŀ����
                    out.println("                           <td class=\"ItemBody\" nowrap>");
                    out.println("                               <a href=\"#\"  onClick=\"doLineName("+info.getId()+");\" >" );  
                    out.println(                                    DataFormat.formatString( info.getLineName() ));
                    out.println("                               </a>\n" ); 
                    out.println("                           </td>");
                    
                    //�����У���Ŀ����
                    out.println("                           <td class=\"ItemBody\" align=\"center\" nowrap>");
                    out.println(info.getLineLevel());
                    out.println("                           </td>");
                    
                    //�����У��ϼ���Ŀ
                    out.println("                           <td class=\"ItemBody\" nowrap>");
                    out.println(DataFormat.formatString(TPNameRef.getParentLevelNoById(conn,info.getId())));
                    out.println(DataFormat.formatString(TPNameRef.getParentLevelNameById(conn,info.getId())));
                    out.println("                           </td>");
                    
                    //�����У��Ƿ�Ҷ��
                    out.println("                           <td class=\"ItemBody\" align=\"center\" nowrap>");
                    if( info.getIsLeaf() == Constant.YesOrNo.YES )
                    {
                        out.println("��");
                    }
                    else
                    {
                        out.println("��");
                    }
                    out.println("                           </td>");
                    
                    //�����У��Ƿ�ֻ��
                    out.println("                           <td class=\"ItemBody\" align=\"center\" nowrap>");
                    if( info.getIsReadonly() == Constant.YesOrNo.YES )
                    {
                        out.println("��");
                    }
                    else
                    {
                        out.println("��");
                    }
                    out.println("                           </td>");
                    
                    
                    
                    //���"ȡ���߼�"����ת������ҳ�棺
                    //String strItemPageURL = "/NASApp/iTreasury-treasuryplan/treasuryplan/setting/control/c404.jsp?lineId="+info.getId()+"&strSuccessPageURL=/treasuryplan/setting/view/v403.jsp"+"&strFailPageURL=/treasuryplan/setting/control/c401.jsp";
                    
                    //ʵ�������� - ����Ŀ
                    Trea_TPTemplateItemDAO  itemDao   = new Trea_TPTemplateItemDAO();
                    Collection coll = null; 
                    coll = itemDao.findAllByLineID(info.getId());
                   
                    String strItemRule = "";
                    if( coll != null && coll.size() > 0 )
                    {
                        strItemRule = "�Ѷ���";
                    }
                    else
                    {
                        strItemRule = "δ����";
                    }
                    
                    
                    //�����У�ȡ���߼�
                    out.println("                           <td class=\"ItemBody\" align=\"center\" nowrap>");
                    if( info.getIsLeaf() == Constant.YesOrNo.YES )//Ҷ����Ŀ���ܶ���ȡ���߼�
                    {
                        out.println("                               <a href=\"#\"  onClick=\"doLineItem("+info.getId()+");\" >" );  
                        out.println("                                    "+DataFormat.formatString(strItemRule) );
                        out.println("                               </a>\n" );
                    }
                    else
                    {
                        out.println("&nbsp;");
                    }
                     
                    out.println("                           </td>");
                    
                    out.println("                       </tr>");
                }
                
            }
            else
            {
                //��������Ϊ�գ������һ������
                out.println("                       <tr>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                           <td class=\"ItemBody\" nowrap> &nbsp; </td>");
                out.println("                       </tr>");
            }
            
            out.println("                   </table>");
            
            out.println("<script language=\"javascript\">");
            
            if (vTemplate != null && vTemplate.size() > 0)
            {
                out.println("   var arrayLineNo = new Array(" + vTemplate.size() + ");");
                out.println("   var arrayLineNoStatus = new Array(" + vTemplate.size() + ");");
                Iterator it = vTemplate.iterator();
                int index = 0;
                while (it.hasNext())
                {
                    Trea_TPTemplateInfo info = (Trea_TPTemplateInfo) it.next();
                    long lIsDefaultDisplay = -1;
                    if (aryLevelStatus != null && info.getLineLevel() <= aryLevelStatus.length && aryLevelStatus[(int) info.getLineLevel() - 1] == 1)
                    {
                        lIsDefaultDisplay = 1;
                    }
                    out.println("   arrayLineNo[" + index + "]='" + info.getLineNo() + "';");
                    out.println("   arrayLineNoStatus[" + index + "]=" + lIsDefaultDisplay + ";");
                    index++;
                }
            }
            
            
            out.println("function showChild(treeLineNo)");
            out.println("{");
            out.println("   var lLevel;");
            out.println("   var lLevelCount=document.all.hdnLevelCount.value;");
            out.println("   eval(\"lLevel=document.all.hdnLevel\"+treeLineNo+\".value\");");
            out.println("   if (lLevel < lLevelCount)");
            out.println("   {");
            out.println("       var temp;");
            out.println("       eval(\"temp=document.all.button\"+treeLineNo+\".value\");");
            out.println("       if (temp == '+')");
            out.println("       {");
            out.println("           eval(\"document.all.button\"+treeLineNo+\".value='-'\");");
            out.println("           closeOrOpen(treeLineNo,1);");
            out.println("       }");
            out.println("       else");
            out.println("       {");
            out.println("           eval(\"document.all.button\"+treeLineNo+\".value='+'\");");
            out.println("           closeOrOpen(treeLineNo,-1);");
            out.println("       }");
            out.println("   }");
            out.println("}");
            
            
            out.println("");
            out.println("function closeOrOpen(treeLineNo,nType)");
            out.println("{");
            out.println("   for(var i=0;i<arrayLineNo.length;i++)");
            out.println("   {");
            out.println("       if (arrayLineNo[i].length > treeLineNo.length)");
            out.println("       {");
            out.println("           if (nType == 1)");
            out.println("           {");
            out.println("               //չ��");
            out.println("               if (arrayLineNo[i].length == (treeLineNo.length + 4) && arrayLineNo[i].indexOf(treeLineNo) == 0)");
            out.println("               {");
            out.println("                   eval(\"document.all.tree\"+arrayLineNo[i]+\".style.display=''\");");
            out.println("                   arrayLineNoStatus[i] = 0;");
            out.println("               }");
            out.println("           }");
            out.println("           else");
            out.println("           {");
            out.println("               if (arrayLineNoStatus[i] == 0 && arrayLineNo[i].length > treeLineNo.length && arrayLineNo[i].indexOf(treeLineNo) == 0)");
            out.println("               {");
            out.println("                   eval(\"document.all.tree\"+arrayLineNo[i]+\".style.display='none'\");");
            out.println("                   eval(\"document.all.button\"+arrayLineNo[i]+\".value='+'\");");
            out.println("                   arrayLineNoStatus[i] = 1;");
            out.println("               }");
            out.println("           }");
            out.println("       }");
            out.println("   }");
            out.println("}");
            out.println("");
            out.println("function doFocus(i,sLineNo)");
            out.println("{");
            out.println("   adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,2);");
            out.println("}");
            out.println("");
            out.println("function doBalance(i,obj)");
            out.println("{");
            out.println("   eval(\"fromPlanObj=document.all.txtPlan\"+i+\"_\"+\""+formRowNo+"\");");
            out.println("   eval(\"toPlanObj=document.all.txtPlan\"+i+\"_\"+\""+toRowNo+"\");");
            out.println("   eval(\"beginPlanObj=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\");");
            out.println("   eval(\"beginUsefulPlanObj=document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\");");
            out.println("   eval(\"endPlanObj=document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\");");
            out.println("   eval(\"endUsefulPlanObj=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\");");           
            out.println("   eval(\"depositPlanObj=document.all.txtPlan\"+i+\"_\"+\""+depositRowNo+"\");");
            out.println("   eval(\"assurePlanObj=document.all.txtPlan\"+i+\"_\"+\""+assureRowNo+"\");");    
            
            out.println("   eval(\"cashPlanObj=document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\");");
            out.println("   eval(\"needMoneyPlanObj=document.all.txtPlan\"+i+\"_\"+\""+needMoneyRowNo+"\");");
            out.println("   eval(\"adjustCashPlanObj=document.all.txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\");");
            out.println("   eval(\"balanceRegulatePlanObj=document.all.txtPlan\"+i+\"_\"+\""+balanceRegulateRowNo+"\");");
            
            out.println("   var beginPlanAmount;");
            out.println("   var beginUsefulPlanAmount;");
            out.println("   var endPlanAmount;");
            out.println("   var endUsefulPlanAmount;");
            
            out.println("");
            out.println("   if (beginPlanObj!=null && depositPlanObj!=null && assurePlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\")");
            out.println("   {");
            out.println("       eval(\"beginPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
            out.println("       beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
            out.println("");
            out.println("       var depositPlanAmount;");
            out.println("       eval(\"depositPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+depositRowNo+"\"+\".value\");");
            out.println("       depositPlanAmount = reverseFormatAmount(depositPlanAmount);");
            out.println("");
            out.println("       var assurePlanAmount;");
            out.println("       eval(\"assurePlanAmount=document.all.txtPlan\"+i+\"_\"+\""+assureRowNo+"\"+\".value\");");
            out.println("       assurePlanAmount = reverseFormatAmount(assurePlanAmount);");
            out.println("");
            out.println("       beginUsefulPlanAmount = parseFloat(beginPlanAmount) - parseFloat(depositPlanAmount) - parseFloat(assurePlanAmount)");
            out.println("");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = beginUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = 1\");");
            out.println("");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\",1);");
            out.println("");
            out.println("   }");
            out.println("");
            out.println("   if (fromPlanObj==null||toPlanObj==null){return;}");
            out.println("");
            out.println("   var fromPlanAmount;");
            out.println("   var toPlanAmount;");
            out.println("   eval(\"fromPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+formRowNo+"\"+\".value\");");
            out.println("   eval(\"toPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+toRowNo+"\"+\".value\");");
            out.println("   fromPlanAmount = reverseFormatAmount(fromPlanAmount);");
            out.println("   toPlanAmount = reverseFormatAmount(toPlanAmount);");
            out.println("");
            out.println("   if (beginPlanObj!=null && endPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\")");
            out.println("   {");
            out.println("       eval(\"beginPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginBalanceRowNo+"\"+\".value\");");
            out.println("       beginPlanAmount = reverseFormatAmount(beginPlanAmount);");
            out.println("");
            out.println("       endPlanAmount = parseFloat(beginPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
            out.println("");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = endPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value = 1\");");
            out.println("");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\",1);");
            out.println("");
            out.println("   }");
            out.println("");
            
            //out.println(" if (beginPlanObj!=null && endPlanObj!=null && i<"+(aryHeadDateString.length-1)+")");
            out.println(" if (beginPlanObj!=null && endPlanObj!=null )");
            out.println("{");
            out.println("       eval(\"endPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endBalanceRowNo+"\"+\".value\");");
            out.println("       endPlanAmount = reverseFormatAmount(endPlanAmount);");
            out.println("");
            out.println("       var temp = parseFloat(i)+1;");
            out.println("       eval(\"document.all.txtPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = endPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = endPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+temp+\"_\"+\""+beginBalanceRowNo+"\"+\".value = 1\");");
            out.println("");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+temp+\"_\"+\""+beginBalanceRowNo+"\",1);");
            out.println("}");
            out.println("");
            
            out.println("   if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\")");
            out.println("   {");
            out.println("       eval(\"beginUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value\");");
            out.println("       beginUsefulPlanAmount = reverseFormatAmount(beginUsefulPlanAmount);");
            out.println("");
            out.println("       endUsefulPlanAmount = parseFloat(beginUsefulPlanAmount) + parseFloat(fromPlanAmount) - parseFloat(toPlanAmount)");
            out.println("");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value = 1\");");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\",1);");
            out.println("   }");
            out.println("");
            
            //out.println(" if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null && i<"+(aryHeadDateString.length-1)+")");
            out.println(" if (beginUsefulPlanObj!=null && endUsefulPlanObj!=null )");
            out.println("{");
            out.println("       eval(\"endUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value\");");
            out.println("       endUsefulPlanAmount = reverseFormatAmount(endUsefulPlanAmount);");
            out.println("");
            out.println("       var temp = parseFloat(i)+1;");
            out.println("       eval(\"document.all.txtPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = endUsefulPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\"+\".value = 1\");");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+temp+\"_\"+\""+beginUsefulBalanceRowNo+"\",1);");
            out.println("}");
            
            out.println("");
            out.println("   if (cashPlanObj!=null && endUsefulPlanObj!=null && needMoneyPlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+cashRowNo+"\")");
            out.println("   {");
            out.println("       eval(\"endUsefulPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+endUsefulBalanceRowNo+"\"+\".value\");");
            out.println("       endUsefulPlanAmount = reverseFormatAmount(endUsefulPlanAmount);");
            out.println("");
            out.println("       var needMoneyPlanAmount;");
            out.println("       eval(\"needMoneyPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+needMoneyRowNo+"\"+\".value\");");
            out.println("       needMoneyPlanAmount = reverseFormatAmount(needMoneyPlanAmount);");
            out.println("");
            out.println("       var cashPlanAmount;");
            out.println("       cashPlanAmount = parseFloat(endUsefulPlanAmount) - parseFloat(needMoneyPlanAmount)");
            out.println("");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value = cashPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value = cashPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+cashRowNo+"\"+\".value = 1\");");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+cashRowNo+"\",1);");
            out.println("   }");
            out.println("");
            
            out.println("   if (adjustCashPlanObj!=null && cashPlanObj!=null && balanceRegulatePlanObj!=null && obj.name!=\"txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\")");
            out.println("   {");
            out.println("       var cashPlanAmount;");
            out.println("       eval(\"cashPlanAmount=document.all.txtPlan\"+i+\"_\"+\""+cashRowNo+"\"+\".value\");");
            out.println("       cashPlanAmount = reverseFormatAmount(cashPlanAmount);");
            out.println("");
            out.println("       var balanceRegulatePlanAmount;");
            out.println("       eval(\"balanceRegulatePlanAmount=document.all.txtPlan\"+i+\"_\"+\""+balanceRegulateRowNo+"\"+\".value\");");
            out.println("       balanceRegulatePlanAmount = reverseFormatAmount(balanceRegulatePlanAmount);");
            out.println("");
            out.println("       var adjustCashPlanAmount;");
            out.println("       adjustCashPlanAmount = parseFloat(cashPlanAmount) - parseFloat(balanceRegulatePlanAmount)");
            out.println("");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = adjustCashPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = adjustCashPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+\""+adjustCashRowNo+"\"+\".value = 1\");");
            out.println("       adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+\""+adjustCashRowNo+"\",1);");
            out.println("   }");
            out.println("");
            
            out.println("}");
            out.println("");
            out.println("function doCount(i,sLineNo,obj)");
            out.println("{");
            out.println("   var hdnPlanAmount;");
            out.println("   var txtPlanAmount;");
            out.println("   eval(\"hdnPlanAmount=document.all.hdnPlan\"+i+\"_\"+sLineNo+\".value\");");
            out.println("   eval(\"txtPlanAmount=document.all.txtPlan\"+i+\"_\"+sLineNo+\".value\");");
            out.println("   if (isFloat(txtPlanAmount) == false || txtPlanAmount == \"\" )");
            out.println("   {");
            out.println("       alert(\"��������ȷ�����֣�\");");
            out.println("       eval(\"document.all.txtPlan\"+i+\"_\"+sLineNo+\".value=\"+hdnPlanAmount);");
            out.println("       return;");
            out.println("   }");
            out.println("   adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,1);");
            out.println("   hdnPlanAmount = reverseFormatAmount(hdnPlanAmount);");
            out.println("   txtPlanAmount = reverseFormatAmount(txtPlanAmount);");
            out.println("");
            out.println("   if (parseFloat(hdnPlanAmount) != parseFloat(txtPlanAmount))");
            out.println("   {");
            out.println("       eval(\"document.all.hdnPlan\"+i+\"_\"+sLineNo+\".value = txtPlanAmount\");");
            out.println("       eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+sLineNo+\".value = 1\");");
            out.println("");
            out.println("       var diffAmount = parseFloat(txtPlanAmount) - parseFloat(hdnPlanAmount);");
            out.println("       ");
            out.println("       while(sLineNo.length > 4)");
            out.println("       {");
            out.println("           sLineNo = sLineNo.substring(0,sLineNo.length-4);");
            out.println("           var tempAmount;");
            out.println("           eval(\"tempAmount=document.all.txtPlan\"+i+\"_\"+sLineNo+\".value\");");
            out.println("           tempAmount = parseFloat(reverseFormatAmount(tempAmount))+parseFloat(diffAmount);");
            out.println("           eval(\"document.all.txtPlan\"+i+\"_\"+sLineNo+\".value = tempAmount\");");
            out.println("           eval(\"document.all.hdnPlanIsChange\"+i+\"_\"+sLineNo+\".value = 1\");");
            //out.println("         alert('t');");
            out.println("           adjustAmountForTemplateTree(\"txtPlan\"+i+\"_\"+sLineNo,1);");
            out.println("       }");
            out.println("   }");
            out.println("   doBalance(i,obj)");
            out.println("}");
            out.println("");
            out.println("</script>");
            out.println("<!------------------------------------------------------------------------------------------>");
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }
    
	
	/**
	 * ��ʾ�ʽ�ƻ�ģ������רΪ���ش�ӡ��
	 * @param out
	 * @param aryHeadDateString ��������������λ
	 * @param aryColumnStatus ��Ԥ�⡱��ʵ�ʡ����ƻ��������λ��״̬��0������ʾ��1��Ϊֻ����2��Ϊ��д��
	 * @param vTemplate ����������Ϣ��
	 * @param lLevelCount �������ܹ��ļ���
	 * @param aryLevelStatus ÿ����Ĭ��״̬��1,display;0,hide;���磺���õ�һ��Ĭ����ʾ���������أ�������Ϊ{1}��
	 * 											�������ǰ����Ĭ����ʾ���������Ӧ����{1,1,1}
	 * @throws IException
	 */
	public static void showTreasuryPlanTemplateForDownLoad(JspWriter out, String[] aryHeadDateString, long[] aryColumnStatus, Vector vTemplate, long lLevelCount, long[] aryLevelStatus) throws IException
	{
		try
		{
			out.println("<!------------------------------------------------------------------------------------------>");
			out.println("					<table width=\"99%\" border=\"0\" align=\"center\" height=\"70\" class=\"table1\" cellpadding=\"3\" cellspacing=\"0\">");
			out.println("						<tr id='treeTitle'  style='display:'>");
			out.println("							<td class=\"td-rightbottom\" width=\"20%\" rowspan=\"2\" colspan=\""+lLevelCount+"\">&nbsp;</td>");
			//��ʾ���е���Ŀ
			long lDisplayColumnCount = 0;
			for (int i = 0; i < aryColumnStatus.length; i++)
			{
				if (aryColumnStatus[i] > 0)
				{
					lDisplayColumnCount++;
				}
			}
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				out.println("							<td class=\"td-rightbottom\" height=\"20\" colspan=\"" + lDisplayColumnCount + "\">");
				out.println("								<div align=\"center\">" + aryHeadDateString[i] + "</div>");
				out.println("							</td>");
			}
			out.println("						</tr>");
			out.println("						<tr id='treeTitle'  style='display:'>");
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				if (aryColumnStatus[0] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">Ӧ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[1] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">ʵ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[2] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">�ƻ�</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[3] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">��</div>");
					out.println("							</td>");
				}
			}
			out.println("						</tr>");
			if (vTemplate != null && vTemplate.size() > 0)
			{
				Iterator it = vTemplate.iterator();
				while (it.hasNext())
				{
					TemplateInfo info = (TemplateInfo) it.next();
					ArrayList detailInfos = info.getDetailInfos();
					out.println("						<tr id=\"tree" + info.getLineNo() + "\" >");
					for (int i = 1; i <= lLevelCount; i++)
					{
						if (info.getLineLevel() == i)
						{
							out.println("							<td class=\"td-rightbottom\" nowrap>");
							out.println("								" + info.getLineName());
							out.println("							</td>");
						}
						else
						{
							out.println("							<td class=\"td-rightbottom\" nowrap>");
							out.println("								&nbsp;");
							out.println("							</td>");
						}
					}
					java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
					//String s=nf.format(33334444.88);
					
					for (int i = 0; i < detailInfos.size(); i++)
					{
						if (aryColumnStatus[0] > 0)
						{
							String strForecastAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getForecastAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strForecastAmount );
							out.println("							</td>");
						}
						if (aryColumnStatus[1] > 0)
						{
							String strActualAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getActualAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strActualAmount);
							out.println("							</td>");
						}
						if (aryColumnStatus[2] > 0)
						{
							String strPlanAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount());
							double dPlanAmount = ((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount();
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strPlanAmount);
							out.println("							</td>");
						}
						if (aryColumnStatus[3] > 0)
						{
							String strDifferenceAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getDifferenceAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strDifferenceAmount);
							out.println("							</td>");
						}
					}
					out.println("						</tr>");
				}
			}
			out.println("					</table>");
			out.println("<!------------------------------------------------------------------------------------------>");
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	/**
	 * ��ʾ�ʽ�ƻ�ģ������רΪ���ش�ӡ��
	 * @param out
	 * @param aryHeadDateString ��������������λ
	 * @param aryColumnStatus ��Ԥ�⡱��ʵ�ʡ����ƻ��������λ��״̬��0������ʾ��1��Ϊֻ����2��Ϊ��д��
	 * @param vTemplate ����������Ϣ��
	 * @param lLevelCount �������ܹ��ļ���
	 * @param aryLevelStatus ÿ����Ĭ��״̬��1,display;0,hide;���磺���õ�һ��Ĭ����ʾ���������أ�������Ϊ{1}��
	 * 											�������ǰ����Ĭ����ʾ���������Ӧ����{1,1,1}
	 * @throws IException
	 */
	public static void showTreasuryPlanTemplateForFilterDownLoad(JspWriter out, String[] aryHeadDateString, long[] aryColumnStatus, Vector vTemplate, long lLevelCount, long[] aryLevelStatus) throws IException
	{
		try
		{
			out.println("<!------------------------------------------------------------------------------------------>");
			out.println("					<table width=\"99%\" border=\"0\" align=\"center\" height=\"70\" class=\"table1\" cellpadding=\"3\" cellspacing=\"0\">");
			out.println("						<tr id='treeTitle'  style='display:'>");
			out.println("							<td class=\"td-rightbottom\" width=\"20%\" rowspan=\"2\" colspan=\""+lLevelCount+"\">&nbsp;</td>");
			//��ʾ���е���Ŀ
			long lDisplayColumnCount = 0;
			for (int i = 0; i < aryColumnStatus.length; i++)
			{
				if (aryColumnStatus[i] > 0)
				{
					lDisplayColumnCount++;
				}
			}
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				out.println("							<td class=\"td-rightbottom\" height=\"20\" colspan=\"" + lDisplayColumnCount + "\">");
				out.println("								<div align=\"center\">" + aryHeadDateString[i] + "</div>");
				out.println("							</td>");
			}
			out.println("						</tr>");
			out.println("						<tr id='treeTitle'  style='display:'>");
			for (int i = 0; i < aryHeadDateString.length; i++)
			{
				if (aryColumnStatus[0] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">Ӧ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[1] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">ʵ��</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[2] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">�ƻ�</div>");
					out.println("							</td>");
				}
				if (aryColumnStatus[3] > 0)
				{
					out.println("							<td class=\"td-rightbottom\" height=\"20\">");
					out.println("								<div align=\"center\">��</div>");
					out.println("							</td>");
				}
			}
			out.println("						</tr>");
			if (vTemplate != null && vTemplate.size() > 0)
			{
				Iterator it = vTemplate.iterator();
				while (it.hasNext())
				{
					TemplateInfo info = (TemplateInfo) it.next();
					ArrayList detailInfos = info.getDetailInfos();
				   
					double  amountPlan = 0.00;
				    boolean flag =true;
					for(int m = 0; m< detailInfos.size(); m++){
						if(aryColumnStatus[2] > 0)
							amountPlan =((TemplateDetailInfo) detailInfos.get(m)).getPlanAmount();
						if(amountPlan!=0.00) 
						{
							flag = false;
							break;
						}
					}
				if(flag != true ) {
					out.println("						<tr id=\"tree" + info.getLineNo() + "\" >");
					for (int i = 1; i <= lLevelCount; i++)
					{
						if (info.getLineLevel() == i)
						{
							out.println("							<td class=\"td-rightbottom\" nowrap>");
							out.println("								" + info.getLineName());
							out.println("							</td>");
						}
						else
						{
							out.println("							<td class=\"td-rightbottom\" nowrap>");
							out.println("								&nbsp;");
							out.println("							</td>");
						}
					}
					java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
					//String s=nf.format(33334444.88);
					

					for (int i = 0; i < detailInfos.size(); i++)
					{
						if (aryColumnStatus[0] > 0)
						{
							String strForecastAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getForecastAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strForecastAmount );
							out.println("							</td>");
						}
						if (aryColumnStatus[1] > 0)
						{
							String strActualAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getActualAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strActualAmount);
							out.println("							</td>");
						}
						if (aryColumnStatus[2] > 0)
						{
							String strPlanAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount());
							double dPlanAmount = ((TemplateDetailInfo) detailInfos.get(i)).getPlanAmount();
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strPlanAmount);
							out.println("							</td>");
						}
						if (aryColumnStatus[3] > 0)
						{
							String strDifferenceAmount = nf.format(((TemplateDetailInfo) detailInfos.get(i)).getDifferenceAmount());
							out.println("							<td class=\"td-rightbottom\" height=\"20\">");
							out.println("								" + strDifferenceAmount);
							out.println("							</td>");
						}
					}
					out.println("						</tr>");
				}
			  }
			}
			out.println("					</table>");
			out.println("<!------------------------------------------------------------------------------------------>");
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
	}

	/**
	 * ���������������л�ò���
	 * @param request
	 * @param blnOnlyChanged �Ƿ�ֻ��Ҫ���޸Ĺ��ģ�true,ֻ���ر��޸Ĺ��ģ�false������ȫ����
	 * @return
	 * @throws IException
	 */
	public static Collection getTemplateDataFromRequest(HttpServletRequest request,boolean blnOnlyChanged) throws IException
	{

		String strTemp = null;
		Vector v = null;
		TemplateInfo info = null;
		TemplateDetailInfo detailInfo = null;
		try
		{
			String[] strArrayLineNo = null;
			String[] strArrayDate = null;
			strArrayLineNo = request.getParameterValues("hdnLineNo");
			strArrayDate = request.getParameterValues("hdnDate");
			Log.print("strArrayLineNo.length=" + strArrayLineNo.length);
			Log.print("strArrayDate.length=" + strArrayDate.length);
			if ((strArrayLineNo != null && strArrayLineNo.length > 0) && (strArrayDate != null && strArrayDate.length > 0))
			{
				v = new Vector();
				for (int i = 0; i < strArrayLineNo.length; i++)
				{
					info = new TemplateInfo();
					ArrayList detailInfos = new ArrayList();
					//��ѯ����Ϣ;
					info.setLineNo(strArrayLineNo[i]);
					strTemp = (String) request.getAttribute("hdnLineID" + strArrayLineNo[i]);
					if (strTemp != null && !strTemp.equals(""))
					{
						info.setLineID(Long.parseLong(strTemp));
					}
					//ѭ����ѯ����Ϣ
					for (int j = 0; j < strArrayDate.length; j++)
					{
						detailInfo = null;
						
						detailInfo = new TemplateDetailInfo();
						detailInfo.setTransactionDate(DataFormat.getDateTime(strArrayDate[j]));
						/*
						strTemp = (String) request.getAttribute("txtForcast" + String.valueOf(j) + "_" + strArrayLineNo[i]);
						if (strTemp != null && !strTemp.equals(""))
						{
							detailInfo.setForecastAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
						}
						strTemp = (String) request.getAttribute("txtActual" + String.valueOf(j) + "_" + strArrayLineNo[i]);
						if (strTemp != null && !strTemp.equals(""))
						{
							detailInfo.setActualAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
						}
						*/
						strTemp = (String) request.getAttribute("txtPlan" + String.valueOf(j) + "_" + strArrayLineNo[i]);
						if (strTemp != null && !strTemp.equals(""))
						{
							detailInfo.setPlanAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
						}
						strTemp = (String) request.getAttribute("hdnPlanIsChange" + String.valueOf(j) + "_" + strArrayLineNo[i]);
						if (strTemp != null && !strTemp.equals(""))
						{
							//Log.print("hdnPlanIsChange" + String.valueOf(j) + "_" + strArrayLineNo[i] + "="+strTemp);
							detailInfo.setIschange(Long.parseLong(strTemp));
						}
						/*
						strTemp = (String) request.getAttribute("txtDifference" + String.valueOf(j) + "_" + strArrayLineNo[i]);
						if (strTemp != null && !strTemp.equals(""))
						{
							detailInfo.setDifferenceAmount(Double.parseDouble(DataFormat.reverseFormatAmount(strTemp)));
						}
						*/
						
						if (blnOnlyChanged == true)
						{
							//ֻ�б��޸Ĺ��Ĳŷ���
							if (detailInfo.getIschange() == 1)
							{
								detailInfos.add(detailInfo);
							}
						}
						else
						{
							detailInfos.add(detailInfo);
						}
					}

					if (detailInfos != null && detailInfos.size() > 0)
					{
						//������¼�ű�����
						info.setDetailInfos(detailInfos);						
						v.add(info);
					}
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
		}
		return (v != null && v.size() > 0 ? v : null);	
	}

	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY��־ǿ(kewen) 2004-12-21
	 * @param out ���
	 * @param lTypeID ��������
	 */
	public static void showHomeEnd(JspWriter out) throws IOException
	{
		showHomeEnd(out, -1);
	}
	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY��־ǿ(kewen) 2006-04-13
	 * @param out ���
	 * @param lTypeID ��������(1����ӡ��-1����������)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID) throws IOException
	{
		long lOfficeID = 1;//Ĭ��ֵ
		long lCurrencyID = 1;//Ĭ��ֵ
		showHomeEnd(out, lTypeID, lOfficeID, lCurrencyID);
	}
	/**
	 * ��ʾһ����Ϣ(ҳ��β��)
	 * �޸�BY kenny 2006-04-08
	 * @param out ���
	 * @param lTypeID ��������(1����ӡ��-1����������)
	 */
	public static void showHomeEnd(JspWriter out, long lTypeID, long lOfficeID, long lCurrencyID) throws IOException
	{
		String[] date = DataFormat.getDateString(Env.getSystemDate(lOfficeID, lCurrencyID)).split("-");
		//��ӡ����
		if (lTypeID == 1)
		{
			out.println("</body>\n");
			out.println("</html>");
		}
		else
		{
			out.println("    <br></td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");
			out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
			out.println("  <tr>\n");
			out.println("    <td class=\"pagefootTl\"><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
			out.println("  </tr>\n");
			out.println("  <tr>\n");
			out.println(
				"    <td height=\"25\" class=\"pagefoot\"><font face=\"Arial, Helvetica, sans-serif\">&copy;</font> 2005-"+date[0]+" ��ͨ���� ��Ȩ���� "
					
					+ " Powered by ��ͨ����</td>\n");
			out.println("  </tr>\n");
			out.println("</table>\n");
			out.println("</body>\n");
			out.println("</html>");
		}
	}

	/**
	 * 
	 * @param out
	 * @param sessionMng
	 * @param exception
	 * @param request
	 * @param response
	 * @param strTitle
	 * @param lTypeID
	 * @param strErroCode
	 * @throws Exception
	 */
	public static void showException(JspWriter out, SessionMng sessionMng, IException exception, HttpServletRequest request, HttpServletResponse response, String strTitle, long lTypeID,
			String strErroCode) throws Exception
	{
		String strErroMessage = "";
		if (strErroCode != null && strErroCode.length() > 0)
		{
			strErroMessage = IExceptionMessage.getExceptionMessage(strErroCode);
		}
		if (exception != null)
		{
			strErroMessage = IExceptionMessage.getExceptionMSG(exception);
		}
		System.out.println("**************IExceptionMessage:" + strErroMessage);
		if (lTypeID == Constant.RecordStatus.VALID)
		{
			showHomeHead(out, sessionMng, strTitle, Constant.YesOrNo.YES);
		}
		out.println("<TABLE align=center border=0 class=top height=217 width=\"27%\">\n");
		out.println("  <TBODY>\n");
		out.println("  <TR>\n");
		//out.println("    <TD class=FormTitle height=2 width=\"100%\"><B>" + Env.getClientName() + "</B></TD></TR>\n");
		out.println("    <TD class=FormTitle height=2 width=\"100%\"><B></B></TD></TR>\n");
		out.println("  <TR>\n");
		out.println("      <TD height=190 vAlign=bottom width=\"100%\"> \n");
		out.println("        <TABLE align=center height=187 width=\"97%\">\n");
		out.println("        <TBODY>\n");
		out.println("        <TR>\n");
		out.println("          <TD height=40 vAlign=top width=\"96%\">\n");
		out.println("              <TABLE align=center border=1 borderColor=#999999 height=177 \n");
		out.println("            width=\"99%\">\n");
		out.println("                <TBODY> \n");
		out.println("                <TR borderColor=#D7DFE5 vAlign=center> \n");
		out.println("                  <TD height=162 colspan=\"3\" align=\"center\">" + strErroMessage + "</TD>\n");
		out.println("                </TR>\n");
		out.println("                </TBODY> \n");
		out.println("              </TABLE>\n");
		out.println("            </TD></TR></TBODY></TABLE></TD></TR>\n");
		out.println("  <TR>\n");
		out.println("    <TD height=11 vAlign=top width=\"100%\">\n");
		out.println("      <TABLE align=center height=17 width=\"97%\">\n");
		out.println("        <TBODY>\n");
		out.println("        <TR vAlign=center>\n");
		out.println("          <TD colSpan=6 height=40>\n");
		out.println("            <DIV align=center>\n");
			if (strErroMessage == null || strErroMessage.length() <= 0)
			{
				out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"����\"> \n");
			}
			else
			{
				//out.println("			 <INPUT type=\"button\" class=button name=\"goback\"  onclick=\"self.location.href=''\" value=\"����\"> \n");
				//modified by qhzhou 2008-03-06-16
				if(strErroCode != null && strErroCode.length() > 0 && strErroCode.equals("Gen_E002")){
					out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:window.parent.location.href='"+Constant.SDCURL+"';\"  value=\"����\"> \n");
				}else{
					out.println("			 <INPUT type=\"button\" class=button name=\"goback\"   onclick=\"javascript:history.back(-1)\"  value=\"����\"> \n");
				}
			}
		out.println("            </DIV></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>\n");
			out.println("<SCRIPT language=JavaScript>");
			out.println("   goback.focus();");
			out.println("</SCRIPT>");
		if (lTypeID == Constant.RecordStatus.VALID)
		{
			showHomeEnd(out);
		}
	}
	/**
	 * ͨ�ò�ѯ���ݵķ���
	 * @param strSQL ��ѯ���
	 * @param strField ����ֵ��Ӧ���ֶ�
	 * @return Vector
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String strSQL, String strField) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector v = new Vector();
		try
		{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Object oResult = new Object();
				oResult = rs.getObject(strField);
				if(oResult!=null)
				  v.add(oResult);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
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
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return v.size() > 0 ? v : null;
	}
	
	/**
	 * ͨ�ò�ѯ���ݵķ���
	 * @param strSQL ��ѯ���
	 * @param strField ����ֵ��Ӧ���ֶ�
	 * @return Vector
	 * @throws Exception
	 * 
	 * �ʽ�ƻ�ģ����ô˷����������ظ�ȡ���ݿ�����
	 */
	public static Vector getCommonSelectList(Connection conn , String strSQL, String strField) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Vector v = new Vector();
		try
		{
			if( conn == null || conn.isClosed() == true )
			{
				conn = Database.getConnection();
			}
			
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			while (rs.next())
			{
				Object oResult = new Object();
				oResult = rs.getObject(strField);
				if(oResult!=null)
				  v.add(oResult);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("�������ݿ����");
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
			catch (Exception _ex)
			{
				System.out.println("�ر����ݿ�����ʱ�������ݿ����");
			}
		}
		return v.size() > 0 ? v : null;
	}
	
	
	/**
	 * У��ͻ����������Ч�ԡ�
	 * ִ�в�����
	 * ����¼У��
	 * ��Ȩ��У��
	 * ���ظ�������
	 * @param out
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean validateRequest(JspWriter out, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean bResult = true;
		SessionMng sessionMng = null;
		try
		{
			HttpSession session = request.getSession(true);
			sessionMng = (SessionMng) session.getAttribute("sessionMng");
			//��¼���
			if (sessionMng.isLogin() == false)
			{
				TPHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				TPHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//�ж��Ƿ���Ȩ��
			if (sessionMng.hasRight(request) == false)
			{
				TPHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E002");
				TPHTML.showHomeEnd(out);
				out.flush();
				bResult = false;
			}
			//�����ظ�����
			String strTemp = (String) request.getAttribute("ActionID");
			Long lActionID = new Long(0);
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				try
				{
					lActionID = Long.valueOf(strTemp);
				}
				catch (Exception e)
				{
					Log.print("Request �е�ActionID ��Ч��");
				}
			}
			Long lTemp = (Long) session.getAttribute("ActionID");
			Log.print("Request �е�ActionID �� ��" + lActionID);
			Log.print("Session �е�ActionID �� ��" + lTemp);
			if (lActionID.longValue() != 0)
			{
				if (lTemp != null && lActionID.compareTo(lTemp) <= 0)
				{
					Log.print("�ظ�����" + strTemp);
					TPHTML.showMessage(out, sessionMng, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "Gen_E008");
					TPHTML.showHomeEnd(out);
					out.flush();
					bResult = false;
				}
				else
				{
					session.setAttribute("ActionID", lActionID);
					request.setAttribute("ActionID", String.valueOf(lActionID.longValue() + 1));
				}
			}
		}
		catch (IException ie)
		{
			TPHTML.showException(out, sessionMng, ie, request, response, Env.getClientName(), Constant.RecordStatus.INVALID, "");
			TPHTML.showHomeEnd(out);
			out.flush();
			ie.printStackTrace();
			bResult = false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			bResult = false;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bResult = false;
		}
		return bResult;
	}
	public static void showMessage(JspWriter out, SessionMng sessionMng, HttpServletRequest request, HttpServletResponse response, String strTitle, long lTypeID, String strErroCode) throws Exception
	{
		showException(out, sessionMng, null, request, response, strTitle, lTypeID, strErroCode);
	}
	
	/**
	 * �õ�ģ��������������
	 * @param out
	 * @param sessionMng
	 * @param strTitle
	 * @throws IException
	 */
	public static String[] getDateHeadArray(Timestamp tsStart,Timestamp tsEnd) 
	{
		if (tsStart != null && tsEnd != null)
		{
			Timestamp tsTemp = tsStart;
			int iIntervals = DataFormat.getIntervalDays(tsTemp,tsEnd);
			Log.print("iIntervals="+iIntervals);
			String[] aryDate = new String[iIntervals];
			for (int i=0; i<iIntervals; i++)
			{
				aryDate[i]=DataFormat.getDateString(tsTemp);
				tsTemp = DataFormat.getNextDate(tsTemp);
			}
			return aryDate;
		}
		else
		{
			return null;
		}
	}

		
	
	/**
	 * ��ʾͷ�ļ�
	 * @param out
	 * @param sessionMng
	 * @param strTitle
	 * @throws IException
	 */
	public static void showHomeHead(JspWriter out, SessionMng sessionMng, String strTitle, long lShowMenu) throws IException,IOException
	{
		String strRemindURL = Env.getInstance().getURL(Constant.ModuleType.PLAN) + "treasuryplan/msg/RemindMsg.jsp";
		//String strRemindURL = "";
		String strStatus = "";
		EndDayProcess process = new EndDayProcess();
		if (sessionMng.isLogin())
		{
			try
			{
					strStatus = sessionMng.m_strUserName + "  " + DataFormat.getDateString(Env.getSecuritiesSystemDate(sessionMng.m_lOfficeID, sessionMng.m_lCurrencyID)) + "  " + Env.getClientName()
					+ "  " + Constant.CurrencyType.getName(sessionMng.m_lCurrencyID);
					
			}
			catch (Exception exp)
			{
				;
			}
		}
        //////////////////////// 
        String strProject = Env.getClientName();
        //
        HtmlHeaderInfo htmlHeader = new HtmlHeaderInfo();
        htmlHeader.setJspWriter(out);
        htmlHeader.setSessionMng(sessionMng);
        htmlHeader.setTitle(strTitle);
        htmlHeader.setShowMenu(1);
        htmlHeader.setRemindURL(strRemindURL);
        htmlHeader.setStatus(strStatus);
        htmlHeader.setProjectName(strProject);
        htmlHeader.setTitle(strProject);		      		
        displyHtmlHeader(htmlHeader,lShowMenu);		      
	}

	/**
	 *
	 * @param out
	 * @param selName
	 * @param strValue
	 * @param strName
	 * @throws IOException
	 */
	 public static void showMultSelect(
	 	JspWriter out, 
		String selName, 
		String strValue, 
		String strName) throws IOException {
		   String strProperty = "size=\"3\" align=\"left\" style=\"width:150\"";
		   showMultSelect(out, selName, strValue, strName, strProperty);
	}

	/**
	 *
	 * @param out
	 * @param selName
	 * @param strValue
	 * @param strName
	 * @param strProperty
	 * @throws IOException
	 */
	public static void showMultSelect(
		JspWriter out, 
		String selName, 
		String strValue,
		String strName,
		String strProperty) throws IOException {
		out.println("<select name=\"" + selName + "\"" + strProperty + ">");
		if (strValue == null || strName == null || strValue.equals("") || strName.equals("")) {
			out.println("</select>");
			return;
		}

		String[] sValues = DataFormat.decode2String(strValue);
		String[] sNames = DataFormat.decode2String(strName);

		if (sValues.length == sNames.length) {
			for (int i = 0; i < sValues.length; i++) {
				out.println("<option value=" + sValues[i] + ">" + sNames[i] + "</option>");
			}
		}

		out.println("</select>");
	}
	

	   private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader,
			long lShowMenu) throws IException, IOException {
		htmlHeader.getJspWriter().println("<html>\n");
		htmlHeader.getJspWriter().println("<head>\n");
		htmlHeader.getJspWriter().println(
				"<title>" + htmlHeader.getTitle() + "</title>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
		htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
		htmlHeader.getJspWriter().println("<!--\n");
		htmlHeader.getJspWriter().println("function sendandquit()\n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader
				.getJspWriter()
				.println(
						"	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println(
				"function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
		htmlHeader
				.getJspWriter()
				.println(
						"  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
		htmlHeader.getJspWriter().println(
				"  if (restore) selObj.selectedIndex=0;\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("//-->\n");
		htmlHeader.getJspWriter().println("</Script>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("\n");

		htmlHeader
				.getJspWriter()
				.println(
						"<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
		htmlHeader.getJspWriter().println("</head>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<script language=\"JavaScript1.2\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>\n");
		htmlHeader.getJspWriter().println("<script language=javascript>\n");
		htmlHeader.getJspWriter().println("function showSending() \n");
		htmlHeader.getJspWriter().println("{\n");
		htmlHeader.getJspWriter().println(" gnIsShowSending=1;\n");
		htmlHeader.getJspWriter().println(
				"	sending.style.visibility=\"visible\";\n");
		htmlHeader.getJspWriter().println(
				"	cover.style.visibility=\"visible\";\n");
		htmlHeader.getJspWriter().println("}\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</script>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>����ִ����, ���Ժ�...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
		htmlHeader.getJspWriter().println("\n");

		htmlHeader
				.getJspWriter()
				.println(
						"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"    <td>\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagetop\">\n");
		htmlHeader.getJspWriter().println("      <tr>\n");
		if( htmlHeader.getSessionMng().m_lModuleID == Constant.ModuleType.CLIENTCENTER){			
        	htmlHeader.getJspWriter().println("<td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_jczc.gif\"></td>\n");
		}else{			
            htmlHeader.getJspWriter().println("<td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_zjjh.gif\"></td>\n");
            
		}
		
		
		
		     
     
        htmlHeader.getJspWriter().println(
            "        <td class=\"welcome\">" + htmlHeader.getStatus()
                    + "</td>\n");
        htmlHeader
            .getJspWriter()
            .println(
                    "        <td width=\"187\" align=\"right\" nowrap class=\"headerHelp\"><table border=\"0\" cellpadding=\"4\" cellspacing=\"2\">\n");
        
     
		htmlHeader.getJspWriter().println("      <tr>\n");
		htmlHeader.getJspWriter().println(
				"        <td align=\"center\" class=\"sys\">");
		if (htmlHeader.getSessionMng().m_lUserID > 0
				&& lShowMenu == Constant.YesOrNo.YES) {
			htmlHeader
					.getJspWriter()
					.println(
							"          <a href=\""
									+ Env
											.getInstance()
											.getURL(
													htmlHeader.getSessionMng().m_lModuleID)
									+ "Logout.jsp?control=view\" class=\"syslink\">�˳���¼</a> <a href=\""
									+ Env
											.getInstance()
											.getURL(
													htmlHeader.getSessionMng().m_lModuleID)
									+ "S520.jsp?control=view\" class=\"syslink\">�޸�����</a>");
		}
		htmlHeader.getJspWriter().println("        </td>\n");
		htmlHeader.getJspWriter().println("      </tr>\n");
		htmlHeader.getJspWriter().println("    </table></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println(
				"    <td height=\"24\" class=\"menuMain\">\n");
		htmlHeader
				.getJspWriter()
				.println(
						"      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("        <tr>\n");
		htmlHeader.getJspWriter().println("          <td>&nbsp;</td>\n");
		htmlHeader.getJspWriter().println("        </tr>\n");
		htmlHeader.getJspWriter().println("      </table>\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"    <td height=\"2\" class=\"menuBl1\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("   </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"    <td height=\"3\" class=\"menuBl2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"    <td><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td height=\"20\">\n");
		htmlHeader.getJspWriter().println("      <iframe");
		htmlHeader.getJspWriter().println(
				" border=0 frameborder=0 framespacing=0");
		htmlHeader.getJspWriter().println(" marginheight=0");
		htmlHeader
				.getJspWriter()
				.println(
						" marginwidth=0 name=new_date noResize scrolling=no height=\"20\"");
		htmlHeader.getJspWriter().println(
				" src=\"" + htmlHeader.getRemindURL()
						+ "\" width=\"100%\" vspale=\"0\"></iframe>\n");

		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		//
		htmlHeader.getJspWriter().println("<NOSCRIPT> \n");
		htmlHeader.getJspWriter().println(
				"<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <TBODY> \n");
		htmlHeader.getJspWriter().println("  <TR vAlign=center>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
		htmlHeader.getJspWriter().println(
				"            width=\"720\"></TD></TR></TBODY></TABLE>\n");
		htmlHeader.getJspWriter().println(
				"<table cellspacing=0 cellpadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <tbody> \n");
		htmlHeader
				.getJspWriter()
				.println(
						"  <tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> \n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println(
				"    <td width=90 bgcolor=white height=20><font \n");
		htmlHeader
				.getJspWriter()
				.println(
						"                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
		htmlHeader.getJspWriter().println("\n");
		htmlHeader.getJspWriter().println("</font></td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("  </tbody>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println(
				"<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
		htmlHeader.getJspWriter().println("  <TBODY> \n");
		htmlHeader.getJspWriter().println("  <TR>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
		htmlHeader.getJspWriter().println(
				"            width=\"720\"></TD></TR></TBODY></TABLE>\n");
		htmlHeader.getJspWriter().println("        </NOSCRIPT> \n");
		htmlHeader.getJspWriter().println("        \n");
		htmlHeader.getJspWriter().println("<p> \n");
		//
		if (htmlHeader.isShowMenu() == Constant.YesOrNo.YES) {
			htmlHeader.getJspWriter().println(
					"  <script language=\"JavaScript1.2\">\n");
			htmlHeader.getJspWriter().println(
					"      showMenu(\""
							+ htmlHeader.getSessionMng().m_strMenu
							+ "\",\""
							+ Env.getInstance().getURL(
									htmlHeader.getSessionMng().m_lModuleID)
							+ "\");\n");
			htmlHeader.getJspWriter().println("  </script>\n");
		}
		htmlHeader
				.getJspWriter()
				.println(
						"      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#999999\">\n");
		htmlHeader.getJspWriter().println("        <tr>\n");
		htmlHeader
				.getJspWriter()
				.println(
						"          <td><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
		htmlHeader.getJspWriter().println("        </tr>\n");
		htmlHeader.getJspWriter().println("      </table>\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("  </tr>\n");
		htmlHeader.getJspWriter().println("</table>\n");
		htmlHeader.getJspWriter().println("\n\n");
		htmlHeader
				.getJspWriter()
				.println(
						"<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
		htmlHeader.getJspWriter().println("  <tr>\n");
		htmlHeader.getJspWriter().println("    <td width=\"10\">\n");
		htmlHeader.getJspWriter().println("    </td>\n");
		htmlHeader.getJspWriter().println("    <td><br>\n");

	}

	
	
	/*
	private static void displyHtmlHeader(HtmlHeaderInfo htmlHeader) throws IException, IOException
	{

        htmlHeader.getJspWriter().println("<html>\n");
        htmlHeader.getJspWriter().println("<head>\n");
        htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
        htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
        htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
        htmlHeader.getJspWriter().println("<!--\n");
        htmlHeader.getJspWriter().println("function sendandquit()\n");
        htmlHeader.getJspWriter().println("{\n");
        htmlHeader
                .getJspWriter()
                .println(
                        "	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
        htmlHeader.getJspWriter().println("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
        htmlHeader.getJspWriter().println("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("\n");
        htmlHeader.getJspWriter().println("function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
        htmlHeader.getJspWriter().println("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
        htmlHeader.getJspWriter().println("  if (restore) selObj.selectedIndex=0;\n");
        htmlHeader.getJspWriter().println("}\n");
        htmlHeader.getJspWriter().println("//-->\n");
        htmlHeader.getJspWriter().println("</Script>\n");
        htmlHeader.getJspWriter().println("\n");
        htmlHeader.getJspWriter().println("\n");

        if (htmlHeader.getProjectName().equals("itreasury"))
        {
            htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/itreasury/css/style.css\" type=\"text/css\">\n");
            htmlHeader.getJspWriter().println("</head>\n");
            htmlHeader.getJspWriter().println("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
            htmlHeader.getJspWriter().println("<script language=\"JavaScript1.2\" src=\"/itreasury/js/coolmenus4.js\"></script>\n");
            htmlHeader.getJspWriter().println("<script language=\"JavaScript1.2\" src=\"/itreasury/js/cm_addins.js\"></script>\n");
            htmlHeader.getJspWriter().println("<script language=javascript>\n");
            htmlHeader.getJspWriter().println("function showSending() \n");
            htmlHeader.getJspWriter().println("{\n");
            htmlHeader.getJspWriter().println(" gnIsShowSending=1;\n");
            htmlHeader.getJspWriter().println("	sending.style.visibility=\"visible\";\n");
            htmlHeader.getJspWriter().println("	cover.style.visibility=\"visible\";\n");
            htmlHeader.getJspWriter().println("}\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</script>\n");
            htmlHeader
                    .getJspWriter()
                    .println(
                            "<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>����ִ����, ���Ժ�...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
            htmlHeader
                    .getJspWriter()
                    .println(
                            "<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
            htmlHeader.getJspWriter().println("\n");

            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println("    <td>\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"pagetop\">\n");
            htmlHeader.getJspWriter().println("      <tr>\n");
            htmlHeader
                    .getJspWriter()
                    .println(
                            "        <td width=\"400\" nowrap class=\"headerLogo\"><img src=\"/itreasury/graphics/logo.gif\"><img src=\"/itreasury/graphics/logo_zjjh.gif\"></td>\n");
            htmlHeader.getJspWriter().println("        <td class=\"welcome\">" + htmlHeader.getStatus() + "</td>\n");
            htmlHeader.getJspWriter().println(
                    "        <td width=\"187\" align=\"right\" nowrap class=\"headerHelp\"><table border=\"0\" cellpadding=\"4\" cellspacing=\"2\">\n");
            htmlHeader.getJspWriter().println("      <tr>\n");
            htmlHeader.getJspWriter().println("        <td align=\"center\" class=\"sys\">");
            if (htmlHeader.getSessionMng().m_lUserID > 0)
            {
                htmlHeader.getJspWriter().println(
                        "          <a href=\"" + Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID)
                                + "Logout.jsp?control=view\" class=\"syslink\">�˳���¼</a> <a href=\""
                                + Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID)
                                + "S520.jsp?control=view\" class=\"syslink\">�޸�����</a>");
            }
            htmlHeader.getJspWriter().println("        </td>\n");
            htmlHeader.getJspWriter().println("      </tr>\n");
            htmlHeader.getJspWriter().println("    </table></td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("</table>\n");
            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println("    <td height=\"24\" class=\"menuMain\">\n");
            htmlHeader.getJspWriter().println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            htmlHeader.getJspWriter().println("        <tr>\n");
            htmlHeader.getJspWriter().println("          <td>&nbsp;</td>\n");
            htmlHeader.getJspWriter().println("        </tr>\n");
            htmlHeader.getJspWriter().println("      </table>\n");
            htmlHeader.getJspWriter().println("    </td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println(
                    "    <td height=\"2\" class=\"menuBl1\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
            htmlHeader.getJspWriter().println("   </tr>\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println(
                    "    <td height=\"3\" class=\"menuBl2\"><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("</table>\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println("    <td><img src=\"/itreasury/graphics/space.gif\" width=\"778\" height=\"1\"></td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println("    <td height=\"20\">\n");
            htmlHeader.getJspWriter().println("      <iframe");
            htmlHeader.getJspWriter().println(" border=0 frameborder=0 framespacing=0");
            htmlHeader.getJspWriter().println(" marginheight=0");
            htmlHeader.getJspWriter().println(" marginwidth=0 name=new_date noResize scrolling=no height=\"20\"");
            htmlHeader.getJspWriter().println(" src=\"" + htmlHeader.getRemindURL() + "\" width=\"100%\" vspale=\"0\"></iframe>\n");

            htmlHeader.getJspWriter().println("    </td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("</table>\n");
            //
            htmlHeader.getJspWriter().println("<NOSCRIPT> \n");
            htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
            htmlHeader.getJspWriter().println("  <TBODY> \n");
            htmlHeader.getJspWriter().println("  <TR vAlign=center>\n");
            htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
            htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
            htmlHeader.getJspWriter().println("<table cellspacing=0 cellpadding=0 width=798 border=0>\n");
            htmlHeader.getJspWriter().println("  <tbody> \n");
            htmlHeader.getJspWriter().println("  <tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> \n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
            htmlHeader.getJspWriter().println(
                    "                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
            htmlHeader.getJspWriter().println("\n");
            htmlHeader.getJspWriter().println("</font></td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("  </tbody>\n");
            htmlHeader.getJspWriter().println("</table>\n");
            htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
            htmlHeader.getJspWriter().println("  <TBODY> \n");
            htmlHeader.getJspWriter().println("  <TR>\n");
            htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
            htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
            htmlHeader.getJspWriter().println("        </NOSCRIPT> \n");
            htmlHeader.getJspWriter().println("        \n");
            htmlHeader.getJspWriter().println("<p> \n");
            //
            if (htmlHeader.isShowMenu() == Constant.YesOrNo.YES)
            {
                htmlHeader.getJspWriter().println("  <script language=\"JavaScript1.2\">\n");
                htmlHeader.getJspWriter().println(
                        "      showMenu(\"" + htmlHeader.getSessionMng().m_strMenu + "\",\"" + Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID)
                                + "\");\n");
                htmlHeader.getJspWriter().println("  </script>\n");
            }
            htmlHeader.getJspWriter().println("      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#999999\">\n");
            htmlHeader.getJspWriter().println("        <tr>\n");
            htmlHeader.getJspWriter().println("          <td><img src=\"/itreasury/graphics/space.gif\" width=\"1\" height=\"1\"></td>\n");
            htmlHeader.getJspWriter().println("        </tr>\n");
            htmlHeader.getJspWriter().println("      </table>\n");
            htmlHeader.getJspWriter().println("    </td>\n");
            htmlHeader.getJspWriter().println("  </tr>\n");
            htmlHeader.getJspWriter().println("</table>\n");
            htmlHeader.getJspWriter().println("\n\n");
            htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
            htmlHeader.getJspWriter().println("  <tr>\n");
            htmlHeader.getJspWriter().println("    <td width=\"10\">\n");
            htmlHeader.getJspWriter().println("    </td>\n");
            htmlHeader.getJspWriter().println("    <td><br>\n");
        }
        else
        {
    		try
    		{
    			htmlHeader.getJspWriter().println("<html>\n");
    			htmlHeader.getJspWriter().println("<head>\n");
    			htmlHeader.getJspWriter().println("<title>" + htmlHeader.getTitle() + "</title>\n");
    			htmlHeader.getJspWriter().println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
    			htmlHeader.getJspWriter().println("<Script Language=\"Javascript\">\n");
    			htmlHeader.getJspWriter().println("<!--\n");
    			htmlHeader.getJspWriter().println("function sendandquit()\n");
    			htmlHeader.getJspWriter().println("{\n");
    			htmlHeader.getJspWriter().println("	window.open(\"list1.htm\",\"popup\", \"width=200,height=330,scrollbars=0,resizable=0,menubar=0,toolbar=0,locations=0,directories=0, screenX=0,screenY=0,left=600,top=0;\");\n");
    			htmlHeader.getJspWriter().println("}\n");
    			htmlHeader.getJspWriter().println("function MM_goToURL() { //v3.0\n");
    			htmlHeader.getJspWriter().println("  var i, args=MM_goToURL.arguments; document.MM_returnValue = false;\n");
    			htmlHeader.getJspWriter().println("  for (i=0; i<(args.length-1); i+=2) eval(args[i]+\".location='\"+args[i+1]+\"'\");\n");
    			htmlHeader.getJspWriter().println("}\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("function MM_jumpMenu(targ,selObj,restore){ //v3.0\n");
    			htmlHeader.getJspWriter().println("  eval(targ+\".location='\"+selObj.options[selObj.selectedIndex].value+\"'\");\n");
    			htmlHeader.getJspWriter().println("  if (restore) selObj.selectedIndex=0;\n");
    			htmlHeader.getJspWriter().println("}\n");
    			htmlHeader.getJspWriter().println("//-->\n");
    			htmlHeader.getJspWriter().println("</Script>\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("<link rel=\"stylesheet\" href=\"/webtp/template.css\" type=\"text/css\">\n");
    			htmlHeader.getJspWriter().println("</head>\n");
    			htmlHeader.getJspWriter().println("<body bgcolor=\"#f3f3f3\" text=\"#000000\">\n");
    			htmlHeader.getJspWriter().println("<script language=javascript>\n");
    			htmlHeader.getJspWriter().println("function showSending() \n");
    			htmlHeader.getJspWriter().println("{\n");
    			htmlHeader.getJspWriter().println(" gnIsShowSending=1;\n");
    			htmlHeader.getJspWriter().println("	sending.style.visibility=\"visible\";\n");
    			htmlHeader.getJspWriter().println("	cover.style.visibility=\"visible\";\n");
    			htmlHeader.getJspWriter().println("}\n");
    			htmlHeader.getJspWriter().println("function hideSending() \n");
    			htmlHeader.getJspWriter().println("{\n");
    			htmlHeader.getJspWriter().println(" gnIsShowSending=0;\n");
    			htmlHeader.getJspWriter().println("	sending.style.visibility=\"hidden\";\n");
    			htmlHeader.getJspWriter().println("	cover.style.visibility=\"hidden\";\n");
    			htmlHeader.getJspWriter().println("}\n");
    			htmlHeader.getJspWriter().println("</script>\n");
    			htmlHeader.getJspWriter()
    					.println("<div id=\"sending\" style=\"position:absolute; top:320; left:20; z-index:10; visibility:hidden\"><TABLE WIDTH=100% BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><td width=30%></td><TD bgcolor=#ff9900><TABLE WIDTH=100% height=70 BORDER=0 CELLSPACING=2 CELLPADDING=0><TR><td bgcolor=#eeeeee align=center>����ִ��, ���Ժ�...</td></tr></table></td><td width=30%></td></tr></table></div>\n");
    			htmlHeader.getJspWriter()
    					.println("<div id=\"cover\" style=\"position:absolute; top:0; left:0; z-index:9; visibility:hidden\"><TABLE WIDTH=100% height=900 BORDER=0 CELLSPACING=0 CELLPADDING=0><TR><TD align=center><br></td></tr></table></div>\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
    			htmlHeader.getJspWriter().println("  <tr>\n");
    			htmlHeader.getJspWriter().println("    <td width=\"24%\"><img src=\"/webtp/CNPC_Logo.jpg\" ></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=\"76%\" align=\"right\"><img src=\"/webtp/capital_logo.jpg\" ></td>\n");
    			htmlHeader.getJspWriter().println("  </tr>\n");
    			htmlHeader.getJspWriter().println("<tr>\n");
    			htmlHeader.getJspWriter().println("<td background=\"/webtp/topright.gif\" height=\"24\">&nbsp;</td>\n");
    			htmlHeader.getJspWriter().println("<td background=\"/webtp/topright.gif\">&nbsp;</td>\n");
    			htmlHeader.getJspWriter().println("</tr>\n");
    			htmlHeader.getJspWriter().println("</table>\n");
    			htmlHeader.getJspWriter().println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"20\">\n");
    			htmlHeader.getJspWriter().println("<tr>\n");
    			htmlHeader.getJspWriter().println("<td>\n");
    			htmlHeader.getJspWriter().println("<iframe \n");
    			htmlHeader.getJspWriter().println("      border=0 frameborder=0 framespacing=0 \n");
    			htmlHeader.getJspWriter().println(" marginheight=0 \n");
    			htmlHeader.getJspWriter().println("      marginwidth=0 name=new_date noResize scrolling=no height=\"20\"\n");
    			htmlHeader.getJspWriter().println("      src=\"" + htmlHeader.getRemindURL() + "\" width=\"100%\" vspale=\"0\"></iframe>\n");
    			htmlHeader.getJspWriter().println("</td>\n");
    			htmlHeader.getJspWriter().println("</tr>\n");
    			htmlHeader.getJspWriter().println("</table>\n");
    			//htmlHeader.getJspWriter().println("<hr size=\"2\" noshade color=\"#FF6633\">\n");
    			htmlHeader.getJspWriter().println("<NOSCRIPT> \n");
    			htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
    			htmlHeader.getJspWriter().println("  <TBODY> \n");
    			htmlHeader.getJspWriter().println("  <TR vAlign=center>\n");
    			htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
    			htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
    			htmlHeader.getJspWriter().println("<table cellspacing=0 cellpadding=0 width=798 border=0>\n");
    			htmlHeader.getJspWriter().println("  <tbody> \n");
    			htmlHeader.getJspWriter().println("  <tr valign=center align=middle width=\"718\" bordercolor=\"#666666\"> \n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("    <td width=90 bgcolor=white height=20><font \n");
    			htmlHeader.getJspWriter().println("                  style=\"FONT-SIZE: 11px; COLOR: #666666; FONT-FAMILY: Arial,Helvetica; TEXT-DECORATION: none\">??\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</font></td>\n");
    			htmlHeader.getJspWriter().println("  </tr>\n");
    			htmlHeader.getJspWriter().println("  </tbody>\n");
    			htmlHeader.getJspWriter().println("</table>\n");
    			htmlHeader.getJspWriter().println("<TABLE cellSpacing=0 cellPadding=0 width=798 border=0>\n");
    			htmlHeader.getJspWriter().println("  <TBODY> \n");
    			htmlHeader.getJspWriter().println("  <TR>\n");
    			htmlHeader.getJspWriter().println("          <TD bgColor=#cccccc height=1><SPACER height=\"1\" type=\"block\" \n");
    			htmlHeader.getJspWriter().println("            width=\"720\"></TD></TR></TBODY></TABLE>\n");
    			htmlHeader.getJspWriter().println("        </NOSCRIPT> \n");
    			htmlHeader.getJspWriter().println("        \n");
    			htmlHeader.getJspWriter().println("<p> \n");
    			htmlHeader.getJspWriter().println("  <script language=JavaScript1.2 src=\"/webtp/js/DropDownData.js\" \n");
    			htmlHeader.getJspWriter().println("      type=text/javascript></script>\n");
    			if (htmlHeader.isShowMenu() == Constant.YesOrNo.YES)
    			{
    				htmlHeader.getJspWriter().println("  <script language=\"JavaScript1.2\">\n");
    				htmlHeader.getJspWriter().println(" window.status=\"" + htmlHeader.getStatus() + "\"");
    				htmlHeader.getJspWriter().println("      showMenu(\"" + htmlHeader.getSessionMng().m_strMenu + "\",\"" + Env.getInstance().getURL(htmlHeader.getSessionMng().m_lModuleID) + "\");\n");
    				htmlHeader.getJspWriter().println("  </script>\n");
    			}
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</p>\n");
    			htmlHeader.getJspWriter().println("<table border=\"0\" width=\"100%\" height=\"20\">\n");
    			htmlHeader.getJspWriter().println(" \n");
    			htmlHeader.getJspWriter().println("	<tr>\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("     	\n");
    			htmlHeader.getJspWriter().println("    <td height=\"20\"></td>\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("	</tr>\n");
    			htmlHeader.getJspWriter().println("\n");
    			htmlHeader.getJspWriter().println("</table>\n");
    			htmlHeader.getJspWriter().println("\n");
    		}
    		catch (Exception e)
    		{
    			;
    		}            
        }

    	    
	}*/
}
