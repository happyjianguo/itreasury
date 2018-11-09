package com.iss.sysframe.jasperPrint.element;

import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;

public class JasperStaticText {

	private String staticText="";
	
	private JasperElementConfigInfo elementInfo=new JasperElementConfigInfo();

	public String getStaticText() {
		return staticText;
	}

	public void setStaticText(String staticText) {
		this.staticText = staticText;
	}
	
	public JasperElementConfigInfo getElementInfo() {
		return elementInfo;
	}

	public void setElementInfo(JasperElementConfigInfo elementInfo) {
		this.elementInfo = elementInfo;
	}
	
	public void createStaticText(JasperElementConfigInfo info) 
	{
	StringBuffer txtStaticTextBuffer=null;
	try{
			
		txtStaticTextBuffer=new StringBuffer();

		//System.out.println(colTableTitleinfo.getTitleName());
			txtStaticTextBuffer.append(
	                 "			<staticText>\n" 
					+"				<reportElement x=\""+info.getXFlag()+"\" y=\""+info.getYFlag()+"\" width=\""+info.getElementWidth()+"\" height=\""+info.getElementHeight()+"\"/>\n");
		if(info.getBoxSetting()!=null){	
				txtStaticTextBuffer.append(      
						"				<box>\n");
			if(info.getBoxSetting().getPenLineWidth()!=0){
				txtStaticTextBuffer.append("				<pen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getBottomPenLineWidth()!=0){
				txtStaticTextBuffer.append("				<bottomPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getTopPenLineWidth()!=0){
				txtStaticTextBuffer.append("				<topPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getLeftPenLineWidth()!=0){
				txtStaticTextBuffer.append("				<leftPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getRightPenLineWidth()!=0){
				txtStaticTextBuffer.append("				<rightPen lineWidth=\"1.0\"/>\n"   );
			}
	                
		        txtStaticTextBuffer.append(       
		        		"				</box>\n");
		}
		
			txtStaticTextBuffer.append("				<textElement textAlignment=\""+info.getTextAlignment()+"\" verticalAlignment=\""+info.getVerticalAlignment()+"\">\n");
			
			
        if(info.getFontSetting()!=null){
        	txtStaticTextBuffer.append(	
        			"				<font fontName=\""+info.getFontSetting().getFrontName()+"\" size=\""+info.getFontSetting().getFrontSize()+"\"  isBold=\""+info.getFontSetting().getIsBold()+"\" />\n"   );
        }
            txtStaticTextBuffer.append(
	                "				</textElement>\n"
	                +"				<text><![CDATA["+info.getElementContext()+ "]]></text>\n"
	                +"			</staticText>\n" );
            staticText=txtStaticTextBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}


}
