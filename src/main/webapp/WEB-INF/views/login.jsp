<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	* {
		text-align: center;
	}
	.fieldname{
  		display: inline-block;
		width: 70px;
		text-align: left;
	}
	.field{
		width: 176px;
		text-align: left;
	}
	#submit{
		width: 80px;
	}
</style>
<title>RMS</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
</script>
</head>

<body>
        <%
        String myname =  (String)session.getAttribute("username");
        if(myname!=null)
            out.println("Welcome  "+myname+"  , <a href=\"logout.jsp\" >Logout</a>");
        else 
            {
            %>
            <br><h3>Sign in to your RMS</h3><br>
            
            <form action="rms/restaurant/login" method="post">
            	<div class="fieldname">User ID</div>
				<input type="text" class="field" name="id"><br><br>
	            <div class="fieldname">Password</div>
				<input type="password" class="field" name="password"><br><br><br>
                <input type="submit" id="submit" value="Sign in" /><br><br><br>
            </form>
            <% 
            }
            %>
    </body>
</html>