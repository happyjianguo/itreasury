/*
 * Created on 2005-4-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;

import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.print.templateinfo.PrintOptionInfo;
import com.iss.itreasury.settlement.setting.dao.Sett_PrintTemplateDAO;

import com.iss.itreasury.settlement.print.dataentity.TemplateSettingXmlInfo;
import com.iss.itreasury.settlement.print.TemplateSettingXml;
import java.util.ArrayList;
/**
 * @author weilu
 *	
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrintTemplateBiz
{
	/**
	 * 
	 */
	Sett_PrintTemplateDAO dao = null;
	public PrintTemplateBiz()
	{
		super();
		dao = new Sett_PrintTemplateDAO();
		// TODO Auto-generated constructor stub
	}
	//根据办事处的ID号得到相应的所有模版信息的集合
	public Collection findPrintOption(long lOfficeID,long lCurrecnyID) throws SettlementException
	{
		return dao.findPrintOption(lOfficeID,lCurrecnyID);
	}
	//根据模板的ID及明细ID得和是否得模版或明细的INFO的参数判断      =0 得模版    ！=0 得明细
	public PrintOptionInfo findPrintOptionDetailsByTemplateIDOrderID(long lTemplateID, long lTemplateDetailsID, long lOrderID) throws SettlementException
	{
		return dao.findPrintOptionDetailsByTemplateIDOrderID( lTemplateID,  lTemplateDetailsID,  lOrderID);
	}
	
	//保存模版信息
	public long saveTemplate(long lTemplateID, String strDesc, String strPrintName, double dTop, double dLeft ,long nPrintTemplateType,long lCurrencyID) throws SettlementException
	{
		return dao.saveTemplate( lTemplateID,  strDesc,  strPrintName,  dTop,  dLeft ,nPrintTemplateType,lCurrencyID);
	}
	
	public long saveTemplateDetails(long lTemplateDetailsID, String strDesc, double dTop, double dLeft, double dSize, long lIsBold, long lIsItalic, String strFont, long lFileDwidth, String strData, long lTypeID) throws SettlementException
	{
		
		
		return dao.saveTemplateDetails( lTemplateDetailsID,  strDesc,  dTop,  dLeft,  dSize,  lIsBold,  lIsItalic,  strFont,  lFileDwidth,  strData,  lTypeID);
	}
	
	//根据模版的ID得到模版及明细的所有数据集合
	public Collection findPrintOptionDetailsByTemplateID(long lTemplateID) throws SettlementException
	{
		return dao.findPrintOptionDetailsByTemplateID(lTemplateID);
	}

	//添加打印模版
	public long addTemplate(long lOfficeID,long lCurrencyID,long nPrintTemplateType, String strName , String strDesc, String strPrintName, double dTop, double dLeft,long nStatusID) throws SettlementException
	{
		return dao.addTemplate(lOfficeID,lCurrencyID,nPrintTemplateType, strName , strDesc, strPrintName, dTop, dLeft, nStatusID);
	}
	//删除打印模版
	public long delTemplate(long lTemplateID) throws SettlementException
	{
		return dao.delTemplate(lTemplateID);
	}
	//添加打印模版明细
	public long addTemplateDetails(long lPrintTemplateID,String strCode,String strName,String strDesc,	String strData,double dTop,double dLeft,String strFont,long lTypeID,long lIsBold,long lIsItalic,double lSize,	long lFiledWidth) throws SettlementException
	{
		String strNameTmp="kkf";
		long lResult=-1;
		try{
			long TemplateTypeID=Long.parseLong(strName.trim());
			
			TemplateSettingXml tsXml=new TemplateSettingXml();
			ArrayList al=null;
			al=(ArrayList)tsXml.getTemplateSetting();

			System.out.println("	LOG:   得到的模版的类型为:"+TemplateTypeID);
			
			/*
			//读取XML文件到静态记录集
			if(TemplateSettingXml.getTemplateSettingDetails()!=null){
				System.out.println("XML的数据集合已经读出,放在静态属性里。现在可以直接用！");
				al=(ArrayList)TemplateSettingXml.getTemplateSettingDetails();
			}else{
				System.out.println("XML的数据集合没有读出,在静态属性为空。现在重新读入！");
				al=(ArrayList)tsXml.getTemplateSetting();
			}
			*/
		
			if(al!=null){
				System.out.println("===得到的数据集合的总数为:"+al.size());
				for(int i=0;i<al.size();i++){
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)al.get(i);
					if(info.getTemplateDetailCode().equals(strCode.trim()) && info.getTemplateType()==TemplateTypeID){
						strNameTmp=info.getTemplateDetailName();
						System.out.println("	LOG:   得到的模版的strNameTmp为:"+strNameTmp);
						break;
					}
				}
			}else{
				System.out.println("===得到的数据集合的总数为: 空,插入默认的属性名字");
			}
			
			lResult=dao.addTemplateDetails(lPrintTemplateID,strCode,strNameTmp,strDesc,	strData, dTop, dLeft,strFont,lTypeID, lIsBold,lIsItalic,lSize,lFiledWidth);
		}catch(Exception e){
			lResult=-1;
			e.printStackTrace();
		}
		return lResult;
	}
	//删除打印模版明细
	public long delTemplateDetails(long lTemplateDetailsID) throws SettlementException
	{
		return dao.delTemplateDetails(lTemplateDetailsID);
	}
}
