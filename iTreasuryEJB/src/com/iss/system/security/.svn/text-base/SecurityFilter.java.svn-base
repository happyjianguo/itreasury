/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   SecurityFilter.java

package com.iss.system.security;

import com.iss.system.ISSAppConfig;
import com.iss.system.SecuritySettingConfig;
import com.iss.system.dataentity.ILogonUserBean;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

// Referenced classes of package com.iss.system.security:
//            SinatureUtil, Rights

public class SecurityFilter
    implements Filter
{

    public SecurityFilter()
    {
        m_strLogonURI = "/iampro/tiles/logon.jsp";
        m_strLogonActionURI = "/iampro/logon.do";
        m_strLogoffActionURI = "/iampro/logoff.do";
        m_strUncertificateInfoURI = "/iampro/tiles/common/showuncertificateinfo.jsp";
        m_strViewLicenceURI = "/iampro/sm/licence.do?operate=viewLience";
        m_strUploadLicenceURL = "/iampro/sm/licence.do";
        m_strRightConfig = "com.iss.iam.Rights_zh";
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpSession session = req.getSession();
        ILogonUserBean logonUserBean = (ILogonUserBean)session.getAttribute("__SESSION_LOGON_USER_KEY__");
        String strRequestURI = req.getRequestURI();
        HttpServletResponse resp = (HttpServletResponse)response;
        String strSystemPrefix = req.getContextPath();
        String strSystemCode = strSystemPrefix.substring(1);
        if(logonUserBean == null)
        {
            if(!strRequestURI.equals(m_strLogonActionURI) && !strRequestURI.equals(m_strLogonURI))
            {
                resp.sendRedirect(m_strLogonURI);
                return;
            }
        } else
        {
            String strPara = null;
            if(strRequestURI.endsWith(".do"))
                strPara = request.getParameter(SecuritySettingConfig.DISPATCH_PARAMETER);
            else
                strPara = req.getQueryString();
            String strStoragePath = session.getServletContext().getInitParameter("DocumentPath");
            if(ISSAppConfig.getLicence(strSystemCode, strStoragePath, SinatureUtil.LICENCE_FILE) == null)
            {
                if(logonUserBean.hasRight(Rights.getSysAdminRight(m_strRightConfig)))
                {
                    if(!strRequestURI.equals(m_strUploadLicenceURL) && !strRequestURI.equals(m_strLogoffActionURI))
                    {
                        resp.sendRedirect(m_strViewLicenceURI);
                        return;
                    }
                } else
                if(!strRequestURI.equals(m_strLogonURI))
                {
                    resp.sendRedirect(m_strLogonURI);
                    return;
                }
            } else
            if(!logonUserBean.hasRight(Rights.getSysAdminRight(m_strRightConfig)))
            {
                boolean bPermitted = true;
                strRequestURI = strRequestURI.substring(strSystemPrefix.length(), strRequestURI.length());
                if(strRequestURI.endsWith(".do"))
                    bPermitted = SecuritySettingConfig.checkPermission(strRequestURI, strPara, logonUserBean.getRight());
                else
                if(strRequestURI.endsWith(".jsp"))
                    bPermitted = SecuritySettingConfig.checkPermission(strRequestURI, strPara, logonUserBean.getRight());
                if(!bPermitted)
                {
                    resp.sendRedirect(m_strUncertificateInfoURI + "?ErrorMsg=" + SecuritySettingConfig.getRightsString(strRequestURI, strPara));
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        m_strLogonURI = filterConfig.getInitParameter("logon-uri");
        m_strLogonActionURI = filterConfig.getInitParameter("logon-action-uri");
        m_strLogoffActionURI = filterConfig.getInitParameter("logoff-action-uri");
        m_strUncertificateInfoURI = filterConfig.getInitParameter("uncerticate-info-uri");
        m_strViewLicenceURI = filterConfig.getInitParameter("view-licence-uri");
        m_strUploadLicenceURL = filterConfig.getInitParameter("upload-licence-uri");
        m_strRightConfig = filterConfig.getInitParameter("right-config");
    }

    private String m_strLogonURI;
    private String m_strLogonActionURI;
    private String m_strLogoffActionURI;
    private String m_strUncertificateInfoURI;
    private String m_strViewLicenceURI;
    private String m_strUploadLicenceURL;
    private String m_strRightConfig;
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/