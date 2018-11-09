/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   MemorySequenceNoSeeker.java

package com.iss.system.util;

import com.iss.system.action.ActionException;
import com.iss.system.dao.ISequenceNoSeeker;
import java.sql.Connection;
import java.text.MessageFormat;

public class MemorySequenceNoSeeker
    implements ISequenceNoSeeker
{

    public MemorySequenceNoSeeker()
    {
    }

    public Object nextSequenceNo(Connection conn, String strTable, Object objCurrentSequenceNo)
        throws ActionException
    {
        synchronized(this)
        {
            return new Long(m_lSequenceNo++);
        }
    }

    public static synchronized long nextSequenceNo()
    {
        return m_lSequenceNo++;
    }

    public static synchronized String nextSequenceNoS(String strFormat)
    {
        return MessageFormat.format(strFormat, new Object[] {
            new Long(m_lSequenceNo)
        });
    }

    private static long m_lSequenceNo = 1L;

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 16 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/