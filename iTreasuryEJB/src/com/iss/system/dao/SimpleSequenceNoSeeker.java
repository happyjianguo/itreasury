/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   SimpleSequenceNoSeeker.java

package com.iss.system.dao;

import java.sql.*;
import java.text.MessageFormat;

// Referenced classes of package com.iss.system.dao:
//            ISequenceNoSeeker

public class SimpleSequenceNoSeeker
    implements ISequenceNoSeeker
{

    public SimpleSequenceNoSeeker()
    {
    }

    public Object nextSequenceNo(Connection conn, String strTable, Object objCurrentSequenceNo)
    {
        PreparedStatement psSK = null;
        ResultSet rsSK = null;
        Object objResult = null;
        try
        {
            String strSequenceNoColName = findSequenceNoColumn(conn, strTable);
            if(strSequenceNoColName != null)
            {
                psSK = conn.prepareStatement(MessageFormat.format("SELECT MAX({0}) + 1  AS MaxSequenceNo FROM {1}", new Object[] {
                    strSequenceNoColName, strTable
                }));
                rsSK = psSK.executeQuery();
                long lResult = -1L;
                if(rsSK.next())
                    lResult = rsSK.getLong(1);
                objResult = new Long(lResult != 0L ? lResult : 1L);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(psSK != null)
                    psSK.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return objResult;
    }

    private String findSequenceNoColumn(Connection conn, String strTable)
        throws SQLException
    {
        PreparedStatement psFS = null;
        try
        {
            psFS = conn.prepareStatement(MessageFormat.format("SELECT * FROM {0} WHERE (0=1)", new Object[] {
                strTable
            }));
            ResultSet rsFS = psFS.executeQuery();
            ResultSetMetaData rsMeta = rsFS.getMetaData();
            String strColName = null;
            int nColCount = rsMeta.getColumnCount();
            for(int i = 1; i < nColCount + 1; i++)
            {
                strColName = rsMeta.getColumnName(i);
                if(strColName.toLowerCase().endsWith("_sequenceno"))
                {
                    String s = strColName;
                    return s;
                }
            }

            rsFS.close();
        }
        finally
        {
            if(psFS != null)
                psFS.close();
        }
        return null;
    }
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/