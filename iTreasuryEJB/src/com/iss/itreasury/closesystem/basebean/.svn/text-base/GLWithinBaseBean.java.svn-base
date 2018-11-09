/*
 * Created on 2004-2-11
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
public class GLWithinBaseBean
{
	/**
	 * 
	 */
	public GLWithinBaseBean()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args)
	{
	}
	/*
					 * @author yychen
	?	FindGLVoucherBaseBean.findGLVoucherByCondition ():查询会计凭证
	参数：long lOfficeID:办事处；
	long lCurrencyID:币种；
	Timestamp tsStartDate:执行日；
	Timestamp tsEndDate:执行日；
	返回值：Collection collVoucher:凭证集合；
	功能描述：根据条件从业务系统中查询所需的会计凭证；
	流程描述：
	l	建立连接，开始事务；
	l	根据条件查询出所需的交易编号；
	l	分别根据交易编号查询出该交易的会计分录；
	l	关闭连接，提交事务；
	*/
	public Collection findGLVoucherByCondition(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
	{
		return null;
	}
	/*
						 * @author yychen
						参数：Collection collVoucher:解析从EAI返回信息所得到的信息集合；
		返回值：boolean bIsSuccess:是否成功；
		功能描述：修改业务系统中会计分录的状态；
		流程描述：
		l	建立连接，开始修改会计分录状态事务；
		l	根据参数，修改每笔会计分录的状态（数据库会计分录表Sett_GLEntry中nPostGLStatusID字段）；
		l	提交修改会计分录状态事务，返回是否成功；
		*/
	public boolean updatePostStatus(Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean updatePostStatus(long lOfficeID, long lCurrencyID) throws Exception
	{
		return true;
	}
	public boolean checkPostVoucher(long lOfficeID, long lCurrencyID, long lModelID, Timestamp tsStart, Timestamp tsEnd) throws Exception
	{
		return true;
	}
	public boolean addSubject(long lOfficeID, long lCurrencyID, Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean addSubjectBalance(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collVoucher) throws Exception
	{
		return true;
	}
	public boolean addSubjectAmount(long lOfficeID, long lCurrencyID, Timestamp tsDate, Collection collVoucher) throws Exception
	{
		return true;
	}
    public boolean addExternalAccount(long lOfficeID, long lCurrencyID, Collection collExternalAccount) throws Exception
    {
    	return true;
    }
    //add by xuteng
    public Collection getVouches(String strExecuteDate,long lOfficeID, long lCurrencyID)
    {
    	return null;
    }
}
