<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%
			//out.clear();//????resin???????tomacat??????
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/png");
			byte[] image = (byte[])request.getAttribute("image");

			response.getOutputStream().write(image);

%>

