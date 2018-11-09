/*
 * Created on 2004-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataentity;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.util.SessionMng;

/**
 * @author yiwang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HtmlHeaderInfo
{
    private String ProjectName = "";
    private String StyleName = "";
    private JspWriter jspWriter = null;
    private SessionMng sessionMng = null;
    private SessionOB EBank_SessionMng = null;
    private String RemindURL = "";
    private String Status = "";
    private String Title = "";
    private String SubTitle = "";
    private long ShowMenu = 1;
    private String year = "";
    
    public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	/**
     * @return Returns the jspWriter.
     */
    public JspWriter getJspWriter()
    {
        return jspWriter;
    }
    /**
     * @param jspWriter The jspWriter to set.
     */
    public void setJspWriter(JspWriter jspWriter)
    {
        this.jspWriter = jspWriter;
    }
    /**
     * @return Returns the projectName.
     */
    public String getProjectName()
    {
        return ProjectName;
    }
    /**
     * @param projectName The projectName to set.
     */
    public void setProjectName(String projectName)
    {
        ProjectName = projectName;
    }
    /**
     * @return Returns the remindURL.
     */
    public String getRemindURL()
    {
        return RemindURL;
    }
    /**
     * @param remindURL The remindURL to set.
     */
    public void setRemindURL(String remindURL)
    {
        RemindURL = remindURL;
    }
    /**
     * @return Returns the sessionMng.
     */
    public SessionMng getSessionMng()
    {
        return sessionMng;
    }
    /**
     * @param sessionMng The sessionMng to set.
     */
    public void setSessionMng(SessionMng sessionMng)
    {
        this.sessionMng = sessionMng;
    }
    /**
     * @return Returns the showMenu.
     */
    public long isShowMenu()
    {
        return ShowMenu;
    }
    /**
     * @param showMenu The showMenu to set.
     */
    public void setShowMenu(long showMenu)
    {
        ShowMenu = showMenu;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus()
    {
        return Status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status)
    {
        Status = status;
    }
    /**
     * @return Returns the styleName.
     */
    public String getStyleName()
    {
        return StyleName;
    }
    /**
     * @param styleName The styleName to set.
     */
    public void setStyleName(String styleName)
    {
        StyleName = styleName;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle()
    {
        return Title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title)
    {
        Title = title;
    }
    /**
     * @return Returns the eBank_SessionMng.
     */
    public SessionOB getEBank_SessionMng()
    {
        return EBank_SessionMng;
    }
    /**
     * @param bank_SessionMng The eBank_SessionMng to set.
     */
    public void setEBank_SessionMng(SessionOB bank_SessionMng)
    {
        EBank_SessionMng = bank_SessionMng;
    }
	/**
	 * @return Returns the subTitle.
	 */
	public String getSubTitle() {
		return SubTitle;
	}
	/**
	 * @param subTitle The subTitle to set.
	 */
	public void setSubTitle(String subTitle) {
		SubTitle = subTitle;
	}
	/**
	 * @return Returns the showMenu.
	 */
	public long getShowMenu() {
		return ShowMenu;
	}
}
