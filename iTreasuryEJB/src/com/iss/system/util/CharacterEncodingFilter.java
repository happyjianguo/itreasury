/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   CharacterEncodingFilter.java

package com.iss.system.util;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.*;

public class CharacterEncodingFilter
    implements Filter
{

    public CharacterEncodingFilter()
    {
        m_strRequestEncoding = null;
        m_strResponseLocale = null;
        m_bIgnore = true;
    }

    public void destroy()
    {
        m_strRequestEncoding = null;
        m_strResponseLocale = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        if(!m_bIgnore)
        {
            if(m_strRequestEncoding != null)
                request.setCharacterEncoding(m_strRequestEncoding);
            if(m_strResponseLocale != null)
                response.setLocale(new Locale(m_strResponseLocale));
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        m_strRequestEncoding = filterConfig.getInitParameter("request-encoding");
        m_strResponseLocale = filterConfig.getInitParameter("response-locale");
        String value = filterConfig.getInitParameter("ignore");
        if(value == null)
            m_bIgnore = true;
        else
        if(value.equalsIgnoreCase("true"))
            m_bIgnore = true;
        else
        if(value.equalsIgnoreCase("yes"))
            m_bIgnore = true;
        else
            m_bIgnore = false;
    }

    protected String m_strRequestEncoding;
    protected String m_strResponseLocale;
    protected boolean m_bIgnore;
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 16 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/