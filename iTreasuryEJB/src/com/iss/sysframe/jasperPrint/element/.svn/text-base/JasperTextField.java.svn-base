package com.iss.sysframe.jasperPrint.element;

import com.iss.sysframe.jasperPrint.dataentity.JasperElementConfigInfo;
import com.iss.sysframe.jasperPrint.dataentity.JasperFieldInfo;

public class JasperTextField {

	private String txtField="";
	private JasperElementConfigInfo elementInfo=new JasperElementConfigInfo();
	private JasperFieldInfo fieldInfo=new JasperFieldInfo();
	
	public String getTxtField() {
		return txtField;
	}

	public void setTxtField(String txtField) {
		this.txtField = txtField;
	}

	public JasperElementConfigInfo getElementInfo() {
		return elementInfo;
	}

	public void setElementInfo(JasperElementConfigInfo elementInfo) {
		this.elementInfo = elementInfo;
	}

	public JasperFieldInfo getFieldInfo() {
		return fieldInfo;
	}

	public void setFieldInfo(JasperFieldInfo fieldInfo) {
		this.fieldInfo = fieldInfo;
	}

	public void createTextField(JasperElementConfigInfo info) 
	{
	StringBuffer txtTextFieldBuffer=null;
	try{
			
		txtTextFieldBuffer=new StringBuffer();

		//System.out.println(colTableTitleinfo.getTitleName());
			txtTextFieldBuffer.append(
	                "			<textField isStretchWithOverflow=\""+info.getIsStretchWithOverflow()+"\" >\n"
	                +"				<reportElement " );
	                if(info.getStretchType()!=null && info.getStretchType().length()>0){
	                	txtTextFieldBuffer.append(	"stretchType=\""+info.getStretchType()+"\" ");
	                }
	        txtTextFieldBuffer.append(" x=\""+info.getXFlag()+"\" y=\""+info.getYFlag()+"\" width=\""+info.getElementWidth()+"\" height=\""+info.getElementHeight()+"\"/>\n");
        
		if(info.getBoxSetting()!=null){	
				txtTextFieldBuffer.append(      
						"				<box>\n");
			if(info.getBoxSetting().getPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<pen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getBottomPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<bottomPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getTopPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<topPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getLeftPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<leftPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getRightPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<rightPen lineWidth=\"1.0\"/>\n"   );
			}
	                
		        txtTextFieldBuffer.append(       
		        		"				</box>\n");
		}
		
			txtTextFieldBuffer.append("				<textElement textAlignment=\""+info.getTextAlignment()+"\" verticalAlignment=\""+info.getVerticalAlignment()+"\">\n");
			
			
			if(info.getFontSetting()!=null){
        	txtTextFieldBuffer.append(
        			"				<font fontName=\""+info.getFontSetting().getFrontName()+"\" size=\""+info.getFontSetting().getFrontSize()+"\" isBold=\""+info.getFontSetting().getIsBold()+"\" />\n"   );
        }    
            txtTextFieldBuffer.append(
	                "				</textElement>\n"
	                +"				<textFieldExpression class=\"java.lang.String\"><![CDATA[\""+info.getElementContext()+"\"]]></textFieldExpression>\n"
	                +"			</textField>\n"
					);
            txtField=txtTextFieldBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}

	public void createTextField(JasperElementConfigInfo info,JasperFieldInfo fieldInfo) 
	{
	StringBuffer txtTextFieldBuffer=null;
	try{
			
		txtTextFieldBuffer=new StringBuffer();

		//System.out.println(colTableTitleinfo.getTitleName());
		txtTextFieldBuffer.append(
                "			<textField isStretchWithOverflow=\""+info.getIsStretchWithOverflow()+"\" >\n"
                +"				<reportElement " );
                if(info.getStretchType()!=null && info.getStretchType().length()>0){
                	txtTextFieldBuffer.append(	"stretchType=\""+info.getStretchType()+"\" ");
                }
        txtTextFieldBuffer.append(" x=\""+info.getXFlag()+"\" y=\""+info.getYFlag()+"\" width=\""+info.getElementWidth()+"\" height=\""+info.getElementHeight()+"\"/>\n");
    
		if(info.getBoxSetting()!=null){	
				txtTextFieldBuffer.append(      
						"				<box>\n");
			if(info.getBoxSetting().getPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<pen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getBottomPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<bottomPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getTopPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<topPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getLeftPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<leftPen lineWidth=\"1.0\"/>\n"   );
			}
			if(info.getBoxSetting().getRightPenLineWidth()!=0){
				txtTextFieldBuffer.append("				<rightPen lineWidth=\"1.0\"/>\n"   );
			}
	                
		        txtTextFieldBuffer.append(       
		        		"				</box>\n");
		}
		
			txtTextFieldBuffer.append("				<textElement textAlignment=\""+info.getTextAlignment()+"\" verticalAlignment=\""+info.getVerticalAlignment()+"\">\n");
			
			
			if(info.getFontSetting()!=null){
        	txtTextFieldBuffer.append(
        			"				<font fontName=\""+info.getFontSetting().getFrontName()+"\" size=\""+info.getFontSetting().getFrontSize()+"\"/>\n"   );
        }    
			if(info.getElementContext()!=null && info.getElementContext().length()>0 )
			{
				if(fieldInfo.getFieldName()!=null && fieldInfo.getFieldName().length()>0 )
				{
		            txtTextFieldBuffer.append(
			                "				</textElement>\n"
			                +"				<textFieldExpression class=\"java.lang.String\"><![CDATA[\""+info.getElementContext()+"\"+$F{"+fieldInfo.getFieldName()+"}]]></textFieldExpression>\n"
			                +"			</textField>\n"
							);
				}else{
					
		            txtTextFieldBuffer.append(
			                "				</textElement>\n"
			                +"				<textFieldExpression class=\"java.lang.String\"><![CDATA[\""+info.getElementContext()+"\"]]></textFieldExpression>\n"
			                +"			</textField>\n"
							);
				}
			}
			else if(info.getElementContext()==null || info.getElementContext().length()==0)
			{
				
				if(fieldInfo.getFieldName()!=null && fieldInfo.getFieldName().length()>0 )
				{
		            txtTextFieldBuffer.append(
			                "				</textElement>\n"
			                +"				<textFieldExpression class=\""+fieldInfo.getFildType()+"\"><![CDATA[$F{"+fieldInfo.getFieldName()+"}]]></textFieldExpression>\n"
			                +"			</textField>\n"
							);
				}else{
					
		            txtTextFieldBuffer.append(
			                "				</textElement>\n"
			                +"				<textFieldExpression class=\"java.lang.String\"><![CDATA[]]></textFieldExpression>\n"
			                +"			</textField>\n"
							);
				}
			}

            txtField=txtTextFieldBuffer.toString();
	}
	catch(Exception ex){
		ex.getStackTrace();
	}
		
	}
}
