package com.iss.itreasury.evoucher.setting.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.AutoFileInfo;
import com.iss.itreasury.evoucher.base.VoucherDAO;
import com.iss.itreasury.evoucher.base.VoucherDAOException;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillSettingInfo;
import com.iss.itreasury.evoucher.setting.dataentity.PrintBillTemplateInfo;
import com.iss.itreasury.settlement.print.TemplateSettingXml;
import com.iss.itreasury.settlement.print.dataentity.TemplateSettingXmlInfo;
import com.iss.itreasury.util.AutoFileBean;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;

public class PrintBillSettingDao extends VoucherDAO {
	
	public static String TEMPLET_SEPERATOR = "<&&&>";
	public static String CONTENT_SEPERATOR = " ;; "; 

	public PrintBillSettingDao()
    {
        super("print_billsetting");
    }
    
    public PrintBillSettingDao(Connection con)
    {
        super("print_billsetting",con);
    }
    
    /**
     * 通过id查询单据信息
     * @param lID
     * @return
     * @throws VoucherDAOException
     */
    public PrintBillSettingInfo findByID ( long lID ) throws VoucherDAOException
    {
    	PrintBillSettingInfo resultInfo = new PrintBillSettingInfo();
    	PreparedStatement ps = null;
    	String strSQL = "";
    	
    	try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            strSQL +="select ID,nOfficeID,nCURRENCY,sName,nModuleID,nBillsegment,nPrintType,nStatusID from print_billsetting" +
            		" where id=" + lID;
            ps = prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	resultInfo.setId(rs.getLong("id"));
            	resultInfo.setLOfficeID(rs.getLong("nOfficeID"));
            	resultInfo.setLCurrencyID(rs.getLong("nCURRENCY"));
            	resultInfo.setSName(rs.getString("sName"));
            	resultInfo.setLModuleID(rs.getLong("nModuleID"));
            	resultInfo.setLBillsegment(rs.getLong("nBillsegment"));
            	resultInfo.setLPrintType(rs.getLong("nPrintType"));
            	resultInfo.setNStatusID(rs.getLong("nStatusID"));
            }
            if(rs != null)
            {
            	rs.close();
            	rs = null;
            }
            if(ps != null)
            {
            	ps.close();
            	ps = null;
            }
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        
    	return resultInfo;
    }
    
    /**
     * 根据模版id查询对应单据id
     * @param lTemplateID
     * @return
     * @throws Exception
     */
    public long findBillIDByTemplateID (long lTemplateID) throws Exception
    {
    	long lBillID = -1;
    	//PreparedStatement ps = null;
    	String strSQL = "";
    	
    	try {
    		try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
    		strSQL = " select nbillid from print_billtemplate where id=" + lTemplateID;
    		
    		System.out.println("------------------------查询billid sql："+strSQL);
    		transPS = prepareStatement(strSQL);
    		transRS = transPS.executeQuery();
    		
    		while (transRS.next())
    		{
    			lBillID = transRS.getLong("nbillid");
    		}
    		
    		
    		if(transRS!=null)
    		{
    			transRS.close();
    			transRS = null;
    		}
    		if(transPS!=null)
    		{
    			transPS.close();
    			transPS = null;
    		}
    	}
    	catch ( Exception ex ) {
			ex.printStackTrace();
			log.error(ex.getMessage());
			throw new IException("Gen_E001");
    	}
    	finally
		{
			try
			{
				finalizeDAO();
			}
			catch(Exception es)
			{
				es.printStackTrace();
				throw new IException("Gen_E001");
			}	
		}
    	return lBillID;
    }
    
    /**
     * 保存单据
     * @param info
     * @return
     * @throws VoucherDAOException
     */
    public long saveNewBillSetting(PrintBillSettingInfo info) throws VoucherDAOException
    {
    	long lID = -1;
    	PreparedStatement ps = null;
    	String strSQL = "";
    	try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }

            // 取得新纪录id
            strSQL +="select nvl(max(ID)+1,1) oID from print_billsetting";
            ps = prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            while(rs.next())
            {
            	lID = rs.getLong("oID");
            }
            if(rs != null)
            {
            	rs.close();
            	rs = null;
            }
            if(ps != null)
            {
            	ps.close();
            	ps = null;
            }
            
            strSQL = "";
            
            // 插入新记录
            strSQL += "insert into print_billsetting(id,nOfficeID,nCURRENCY,sName,nModuleID,nBillsegment,nPrintType,nStatusID) values(?,?,?,?,?,?,?,?)";
            log.print("------insert printbillsetting sql:"+strSQL);
            int index = 1;
            ps = prepareStatement(strSQL);
            ps.setLong(index++,lID);
            ps.setLong(index++ , info.getLOfficeID());
            ps.setLong(index++ , info.getLCurrencyID());
            ps.setString(index++, info.getSName());
            ps.setLong(index++ , info.getLModuleID());
            ps.setLong(index++ , info.getLBillsegment());
            ps.setLong(index++ , info.getLPrintType());            
            ps.setLong(index++ , 1);
            ps.executeUpdate();
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
        
    	return lID;
    }
    
    /**
     * 根据id查询模版信息
     * @param templateID
     * @return
     * @throws VoucherDAOException
     */
    public PrintBillTemplateInfo findTemplateInfoByID (long templateID) throws VoucherDAOException
    {
    	PrintBillTemplateInfo info = new PrintBillTemplateInfo();
    	PreparedStatement ps = null;
    	String strSQL = "";
    	String strContent = "";
    	
    	try {
    		try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            strSQL = "select id,nBillID,nIsNeedSeal,sDescription,nUrl,nCoupletNo from print_billtemplate" +
            		" where id=" + templateID;
            
            ps = prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	info.setId(rs.getLong("id"));
            	info.setNBillID(rs.getLong("nBillID"));
            	info.setNIsNeedSeal(rs.getLong("nIsNeedSeal"));
            	info.setSDescription(rs.getString("sDescription"));
            	info.setNUrl(rs.getString("nUrl"));
            	info.setLCoupletNo(rs.getLong("nCoupletNo"));
            	strContent = getTemplateContent(rs.getString("nUrl"));
            	info.setStrTemplateContent(strContent);
            }
            
            if(rs != null)
            {
            	rs.close();
            	rs = null;
            }
            if(ps != null)
            {
            	ps.close();
            	ps = null;
            }
            
            finalizeDAO();
    	} catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return info;
    }
    
    /**
     * 
     * @param lBillID
     * @param lCoupleno
     * @return
     * @throws VoucherDAOException
     */
    public PrintBillTemplateInfo findTemplateInfoByCouple (long lBillID,long lCoupleno) throws VoucherDAOException
    {
    	PrintBillTemplateInfo info = new PrintBillTemplateInfo();
    	
    	PreparedStatement ps = null;
    	String strSQL = "";
    	String strContent = "";
    	
    	try {
    		try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            strSQL = "select id,nBillID,nIsNeedSeal,sDescription,nUrl,nCoupletNo from print_billtemplate" +
            		" where nBillID=" + lBillID + " and nCoupletNo=" + lCoupleno;
            
            ps = prepareStatement(strSQL);
            ResultSet rs = executeQuery();
            
            while(rs.next())
            {
            	info.setId(rs.getLong("id"));
            	info.setNBillID(rs.getLong("nBillID"));
            	info.setNIsNeedSeal(rs.getLong("nIsNeedSeal"));
            	info.setSDescription(rs.getString("sDescription"));
            	info.setNUrl(rs.getString("nUrl"));
            	info.setLCoupletNo(rs.getLong("nCoupletNo"));
            	strContent = getTemplateContent(rs.getString("nUrl"));
            	info.setStrTemplateContent(strContent);
            }
            
            if(rs != null)
            {
            	rs.close();
            	rs = null;
            }
            if(ps != null)
            {
            	ps.close();
            	ps = null;
            }
            
            finalizeDAO();
    	} catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	
    	return info;
    }
    /**
     * 保存模版信息
     * @param info
     * @return
     * @throws VoucherDAOException
     */
    public long saveNewBillTemplate (PrintBillTemplateInfo info) throws VoucherDAOException
    {
    	long lID = -1;
    	PreparedStatement ps = null;
    	String strSQL = "";
    	String strURL = "";
    	
    	try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            // 取得模版存放路径
            strURL = saveTemplateContent(info.getNUrl(),info.getStrTemplateContent());
            if(info.getNUrl()!=null && info.getNUrl().length()>0) {
            	info.setNUrl(strURL);
            	updateBillTemplate(info);
            	lID = info.getId();
            }
            else {
	            // 取得新纪录id
	            strSQL ="select nvl(max(id)+1,1) oID from print_billtemplate";
	            ps = prepareStatement(strSQL);
	            ResultSet rs = executeQuery();
	            while(rs.next())
	            {
	            	lID = rs.getLong("oID");
	            }
	            if(rs != null)
	            {
	            	rs.close();
	            	rs = null;
	            }
	            if(ps != null)
	            {
	            	ps.close();
	            	ps = null;
	            }
	            
	            
	            // 插入新记录
	            strSQL = "insert into print_billtemplate(id,nBillID,nIsNeedSeal,sDescription,nUrl,nCoupletNo) values(?,?,?,?,?,?)";
	            int index = 1;
	            ps = prepareStatement(strSQL);
	            ps.setLong(index++,lID);
	            ps.setLong(index++,info.getNBillID());
	            ps.setLong(index++,info.getNIsNeedSeal());
	            ps.setString(index++,info.getSDescription());
	            ps.setString(index++,strURL);
	            ps.setLong(index++,info.getLCoupletNo());
	            ps.executeUpdate();
            }
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return lID;
    }
    
    /**
     * 更新模版信息
     * @param info
     * @return
     * @throws VoucherDAOException
     */
    public long updateBillTemplate (PrintBillTemplateInfo info) throws VoucherDAOException
    {
    	long lID = -1;
    	PreparedStatement ps = null;
    	String strSQL = "";
    	
    	try
        {
            try
            {
                initDAO();
            } 
            catch (ITreasuryDAOException e)
            {
                throw new VoucherDAOException(e);
            }
            
            strSQL = "update print_billtemplate set nBillID="+info.getNBillID() +
            " ,nIsNeedSeal="+info.getNIsNeedSeal()+" ,sDescription='"+info.getSDescription()+
            "' ,nUrl='"+info.getNUrl()+"'" + " ,nCoupletNo=" + info.getLCoupletNo();
            strSQL += " where id=" + info.getId();
            
            System.out.println("update Sql:"+strSQL);
            ps = prepareStatement(strSQL);
            
            ps.executeUpdate();
            
            finalizeDAO();
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return lID;
    }
    
    /**
     * 保存单据模版内容
     * @param strContent
     * @return
     * @throws VoucherDAOException
     */
    public String saveTemplateContent (String strName,String strContent) throws IException
    {
    	String strURL = "";
    	
    	try {
    		/*if(strName!=null && strName.length()>0) {
    			System.out.println("url xiangtong");
    			java.io.File f = new java.io.File(strName);
    			if(f.exists()) {
    				f.delete();
    			}
    			strURL = strName;
    		}
    		else {*/
	    		AutoFileInfo fileInfo = new AutoFileInfo();
	    		fileInfo.setFileType("txt");
	    		
	    		strURL = AutoFileBean.getDestPath(Constant.DocType.EVOUCHERBILLTEMPLATE);
	    		strURL += AutoFileBean.getFileName(fileInfo);
    		/*}*/	
    		//java.io.File f = new java.io.File(strURL);
    		FileWriter fw = new FileWriter(strURL);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(strContent);
			pw.close();
			fw.close();
    	}
    	catch ( Exception e ) {
    		e.printStackTrace();
    	}
    	
    	return strURL;
    }
    
    /*public String updateTemplateContent (String strURL , String strNewContent) throws IException 
    {
    	String strContent = "";
    	strContent = getTemplateContent(strURL);
    	return strContent;
    }*/
    /**
     * 取得单据模版文件
     * @param strURL
     * @return
     * @throws IException
     */
    public String getTemplateContent (String strURL) throws IException
    {
    	String strContent = "";
    	StringBuffer sbTmp = new StringBuffer();
    	BufferedReader br = null;
    	
    	try {
    		if(strURL!=null && strURL.length()>0) {
    			br = new BufferedReader( new InputStreamReader (new FileInputStream(strURL )) );
    			int i = 0;
    			while ((i = br.read ( )) != -1)
 				{
 				   sbTmp.append ( (char) i ) ;
 				}
    			strContent = sbTmp.toString();
 				br.close();
    		} 
    		else {
    			strContent = "";
    		}
    	} catch (Exception e)
		{
    		log.error(e.getMessage());
    		throw new IException("Loan_E106");
		}
    	return strContent;
    }
    
    /**
     * 遍历模版打印格式内容
     * 
     * @param strContent
     * @return int 模版格式中特殊字符的个数
     * @throws Exception
     */
    public int fileList (String strContent , String strSeparator) throws Exception
    {
    	int lEntryNum = 0;
    	int index = -1;
    	try {
	    	index = strContent.indexOf(strSeparator);
	    	while (index>=0) {
	    		lEntryNum ++;
	    		index = strContent.indexOf(strSeparator,index+strSeparator.length());
	    	}
    	} 
    	catch (Exception e) {
    		log.error(e.getMessage());
    	}
    	return lEntryNum;
    }
    
    /**
     * 将模版格式中的特殊字符转换为各个对应的条目名称，以便存入模版文本文件
     * @param strOldContent
     * @param values 各个特殊字符所对应的条目id
     * @return String 转换成功的模版内容
     * @throws Exception
     */
    public String updateTemplateContent (String strOldContent,String[] values) throws Exception
    {
    	int SIndex = 0;
    	int EIndex = 0;
    	int i = -1;
    	String[] detailName = null;
    	if(values.length > 0) {
    		detailName = new String[values.length];
    	}
    	
    	TemplateSettingXml tsXml=new TemplateSettingXml();
		ArrayList al=null;
		al=(ArrayList)tsXml.getTemplateSetting();
    	
		for(int k=0;k<values.length;k++) {
			if(al != null) {
				for(int m=0;m<al.size();m++){
					TemplateSettingXmlInfo info = (TemplateSettingXmlInfo)al.get(m);
					if(info.getTemplateDetailCode().equals(values[k]) && info.getTemplateType()==1) {
						detailName[k] = info.getTemplateDetailName();
					}
				}
			}
		}
    	
    	try {
    		EIndex = strOldContent.indexOf(TEMPLET_SEPERATOR);
    		while (EIndex>=0) {
    			SIndex = EIndex + TEMPLET_SEPERATOR.length();
    			
    			strOldContent = (strOldContent.substring(0,SIndex)).replaceAll(TEMPLET_SEPERATOR,detailName[++i]+"<%"+values[i]+"%>")
    				+ strOldContent.substring(SIndex);
    			
    			EIndex = strOldContent.indexOf(TEMPLET_SEPERATOR);    			
    		}
    	}
    	catch (Exception e) {
    		log.error(e.getMessage());
    	}
    	return strOldContent;
    }
    
    public long deleteTemplateInfo (PrintBillTemplateInfo info) throws VoucherDAOException
    {
    	long lReturn = -1;
    	String strSQL = "";
    	try
    	{
    		deleteBillRelation(info);
	    	try
	        {
	            initDAO();
	        } 
	        catch (ITreasuryDAOException e)
	        {
	            throw new VoucherDAOException(e);
	        }
	        
	        strSQL = "delete from print_billtemplate where id=" + info.getId();
	        
	        prepareStatement(strSQL);
	        transPS.executeUpdate();
    	}
    	catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } 
    	finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return lReturn;
    }
    public long deleteBillRelation (PrintBillTemplateInfo info) throws VoucherDAOException
    {
    	long lReturn = -1;
    	String strSQL = "";
    	try
    	{
	    	try
	        {
	            initDAO();
	        } 
	        catch (ITreasuryDAOException e)
	        {
	            throw new VoucherDAOException(e);
	        }
	        
	        strSQL = "delete from print_billrelation where NTEMPID=" + info.getId();
	        
	        prepareStatement(strSQL);
	        transPS.executeUpdate();
    	}
    	catch (Exception e)
        {
            e.printStackTrace();
            throw new VoucherDAOException("Gen_E001",e);
        } 
    	finally
        {
            try
            {
                finalizeDAO();
            } catch (ITreasuryDAOException e1)
            {
                e1.printStackTrace();
            }
        }
    	return lReturn;
    }
    
    public static void main (String[] args) {
    	PrintBillSettingDao dao = new PrintBillSettingDao();
    	//PrintBillTemplateInfo info = null;
    	try {
			//String str = dao.getTemplateContent("/upload/evoucher/template/1165296262381.txt");
			//System.out.println("content:"+str);
    		/*String[] s = {"01","02","03","04"}; 
    		String str1 = "<&&&><&&&><&&&><&&&>";
    		String str2 = dao.updateTemplateContent(str1,s);
    		System.out.println(str2);*/
    		long a = dao.findBillIDByTemplateID(1);
    		System.out.println(a);
			//info = new PrintBillTemplateInfo();			
			
			//System.out.println("sssss:"+dao.fileList(str1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
