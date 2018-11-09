/*
 * Created on 2004-2-6
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem.basebean;
import java.sql.Timestamp;
import java.util.Collection;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GLExtendBaseBean
{
	public static void main(String[] args)
	{
	}
	/*
	 * @author yychen  ----为了金算盘增加
	 	修改导出成功的会计分类状态
	*/
	public boolean updatePostedVoucherStatus(long lOfficeID, long lCurrencyID,long lModel,Timestamp tsStartdate,Timestamp tsEnddate) throws Exception
	{
		return false;
	}	
	/*
	 * @author yychen
	GLSystemBaseBean.postGLVoucher()：导出会计凭证
	参数：Collection collGlVoucher: FindGLVoucherBaseBean. findGLVoucherByCondition ()返回的凭证集合；
	返回值：boolean bIsSuccess:是否成功；
	功能描述：将业务系统的凭证信息生成XML文件，然后传出至EAI。
	流程描述：
	l	调用U850GLBean.buildGlVoucherXML(),将凭证信息集合转化成XML文件；
	l	调用U850GLBean.triggerPostGlVoucher()，将XML文件传至EAI系统；
	l	返回是否成功! 
	*/
	public Collection postGLVoucher(Collection collGlVoucher) throws Exception
	{
		return null;
	}	
	
	public Collection postGLVoucher(Collection collGlVoucher,long lOfiiceID,long lCurrencyID,Timestamp date) throws Exception
	{
		return null;
	}	
	/*?	U850GLBean.getGLSubject() :获取科目组合信息
	 * 返回值：科目组合信息集合；
	功能描述：从总账核算系统获取科目组合；
	流程描述：
	l	调用U850GLBean.buildGLSubjectXML()，将查询科目组合的指令生成EAI参数所需XML文件(具体见附件EAI中标准数据文件描述)；
	l	调用JavaBean.triggerQueryGLSubject()将XML发送到EAI，启动EAI进行科目组合查询；
	* */
	public Collection getGLSubject(long lOfficeID, long lCurrencyID) throws Exception
	{
		return null;
	}
	public Collection getGLSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return null;
	}
	public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, long lModelID,Timestamp tsDate) throws Exception
	{
		return null;
	}
	public Collection getGLSubjectAmount(long lOfficeID, long lCurrencyID, Timestamp tsDate) throws Exception
	{
		return null;
	}
    public Collection getGLExternalAccount(long lOfficeID, long lCurrencyID) throws Exception
    {
		return null;
    }
}
