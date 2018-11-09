package com.iss.itreasury.util;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.settlement.account.dataentity.SubAccountCurrentInfo;
/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RecordAccountInfo
{
	private final static String FILE_NAME = "account.dat";
	private File logFile = null;

	/**
	 * Constructor for RecordAccountInfo.
	 */
	public RecordAccountInfo()
	{
		logFile = new File(FILE_NAME);
	}

	public void recordAccountInfo(Collection accountInfos)
	{
		RandomAccessFile fileOutput = null;
		try
		{
			if (!logFile.exists())
			{
				logFile.createNewFile();
			}

			if (accountInfos != null && accountInfos.size() > 0)
			{
				Iterator itTemp = accountInfos.iterator();

				fileOutput = new RandomAccessFile(logFile, "rw");
				fileOutput.seek(fileOutput.length());

				while (itTemp.hasNext())
				{
					SubAccountCurrentInfo tempInfo = (SubAccountCurrentInfo) itTemp.next();

					fileOutput.writeBytes(
						"Account ID:"
							+ tempInfo.getAccountID()
							+ " SubAccount ID:"
							+ tempInfo.getID()
							+ " UncheckAmount:"
							+ tempInfo.getDailyUncheckAmount()
							+ " \n");
				}

			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (fileOutput != null)
				try
				{
					fileOutput.close();
				}
				catch (IOException e)
				{
				}
		}
	}
}
