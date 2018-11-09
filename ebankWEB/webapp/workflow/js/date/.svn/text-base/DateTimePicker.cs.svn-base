using System;
using System.IO;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.ComponentModel;

namespace  ISS.IMP.Web
{
	/// <summary>
	/// Summary description for DateTimePicker.
	/// </summary>
	[DefaultProperty("Text"), 
	ToolboxData("<{0}:DateTimePicker runat=server></{0}:DateTimePicker>"),
	ValidationPropertyAttribute("Text")
	]
	public class DateTimePicker : TextBox
	{
		private bool g_bTimeAvailable;

		[Browsable(true)]
		public bool TimeAvailable		
		{
			get
			{
				return g_bTimeAvailable;
			}
			set
			{
				g_bTimeAvailable=value;
			}
		}

		protected override void OnInit( EventArgs e)
		{
			base.OnInit(e);

			//this.Width=Unit.Pixel(144);
			this.CssClass="textbox";
			//ReadOnly=True
			string strTimeAvailable="false";
			if(TimeAvailable)
			{
				strTimeAvailable="true";
			}
			this.Attributes.Add("onclick","Container_onclick(this,"+strTimeAvailable+")");
			//this.Attributes.CssStyle.Add("cursor","hand");
		}
		protected override void OnPreRender(System.EventArgs e)
		{
			base.OnPreRender(e);

			string strIncludeScript="<script language=javascript src=\"";
			string strJS=Context.Request.ApplicationPath+@"/Pub/WebControls/DateTimePicker/common.js";//注意修改文件路径
			if(!File.Exists(Context.Request.MapPath(strJS)))
			{
				throw new ApplicationException("缺少\""+strJS+"\"文件！");
			}
			else
			{
				strIncludeScript+=strJS;
				strIncludeScript+="\"></script>";
				Page.RegisterClientScriptBlock("Detials",strIncludeScript);
			}
		}

	}
}
