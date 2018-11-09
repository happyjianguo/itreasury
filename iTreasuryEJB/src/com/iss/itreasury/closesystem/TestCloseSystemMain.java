/*
 * Created on 2004-2-26
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem;
import com.iss.itreasury.util.DataFormat;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestCloseSystemMain
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("111111dsdasdsad111111111");
			GLDealBean.addSubjectAmount(1, 1, 1, DataFormat.getDateTime("2004-08-01"), DataFormat.getDateTime("2004-08-25"));
			System.out.println("222222222222222");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
