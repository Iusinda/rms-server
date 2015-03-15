<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!
	int id = 2;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title id="title">RMS</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	function changeStatus(availability){
		if (availability == false){
			$('#status').val('true');
			$('#status').text('Start');
			$('#statusSpan').attr({"title" : "Start Dispensing Tickets"});
		}
		else{
			$('#status').val('false');
			$('#status').text('Stop');
			$('#statusSpan').attr({"title" : "Stop Dispensing Tickets"});
		}
	}
	
	$(document).ready(function() {
		$.ajax({
			url: "http://<%=request.getServerName()%>:7001/rms/tickettypes",
			data: {id: <%=id%>},
			success: function(data) {
				for (i = 0; i < data.length; i++)
					$('#tickettypes').append('<button type="button" class="type" value="'
						+ data[i].type +'">' + String.fromCharCode(data[i].type+64) + '</button>');
			}
		});
		
		$.ajax({
			url: "http://<%=request.getServerName()%>:7001/rms/restaurant",
			data: {id: <%=id%>},
			cache: false,
			success: function(data) {
				changeStatus(data.availability);
				$('#content').append('Welcome to the Reservation Management System of ' + data.name + '.');
			}
		});
		
		$(".content").hide();
		
		$(".type").click(function(){
		alert();
			$('#waitinglist').show();
// 			$.ajax({
<%-- 				url : "http://<%=request.getServerName()%>:7001/rms/tickets", --%>
<%-- 				data: {id: <%=id%>, type: $(this).val()}, --%>
// 				cache: false,
// 				success: function(data) {
// 					//if (data == true) changeStatus($('#status').val() == 'true');
// 					for (i = 0; i < data.length; i++);
// 						//data[i].
// 				}
// 			});
		});
		
		$("#status").click(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant/waitinglist",
				data: {id: <%=id%>, status: $('#status').val()},
				cache: false,
				success: function(data) {
					if (data == true) changeStatus($('#status').val() == 'true');
				}
			});
		});
	});
</script>
</head>

<body>
	<div id="menu">
		<span id="statusSpan"><button type="button" id="status"></button></span>
		<span id="tickettypes"></span>
		<button type="button">+</button>
		<button type="button">Restaurant Info</button>
		<button type="button" id="accountButton">Account Info</button>
		<button type="button">Sign Out</button>
	</div>
	<div>
		
		<table class="content" id="waitinglist">
			<tr>
				<th>No.</th>
				<th>Size</th>
				<th>Get</th>
				<th>Call</th>
				<th></th>
			</tr>
		</table>
	</div>
</body>
</html>