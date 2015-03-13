<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%! int id = 2; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hello jQuery</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			url : "http://<%=request.getServerName()%>:7001/rms/restaurant?id=<%=id%>"
		}).then(function(data) {
			if (data.availability == false)
				$('.status').append('<button type="button">Start Dispensing Tickets</button>');
			else
				$('.status').append('<button type="button">Stop Dispensing Tickets</button>');
		});
		$.ajax({
			url : "http://<%=request.getServerName()%>:7001/rms/tickettypes?restaurantId=<%=id%>"
		}).then(function(data) {
			for (i = 0; i < data.length; i++)
				$('.tickettypes').append('<button type="button">' 
					+ String.fromCharCode(data[i].type+64) + '</button>');
		});
	});
</script>
</head>

<body>
	<div id="menu">
		<span class="tickettypes"></span>
		<span class="status"></span>
		<button type="button">Restaurant Info</button>
		<button type="button">Account Info</button>
		<button type="button">Sign Out</button>
	</div>
	<div id="content">Content</div>
</body>
</html>