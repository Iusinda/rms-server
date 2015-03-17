<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!
	int id = 1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title id="title">RMS</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	var id = <%=id%>, type;
	function showTickets(){
		$("#waitinglistbody").empty();
		$.ajax({
			url : "http://<%=request.getServerName()%>:7001/rms/tickets",
			data: {id: <%=id%>, type: type},
			cache: false,
			success: function(data) {
				for (i = 0; i < data.length; i++){
					str = "<tr>"
						+ "<td>" + data[i].number + "<td>"
						+ "<td>" + data[i].size + "<td>"
						+ "<td>" + toTime(data[i].getTime) + "<td>"
						+ "<td>";
					if (data[i].callTime != null){
						str += toTime(data[i].callTime);
					}
					str += "</td><td><button>X</button></td></tr>";
					$("#waitinglistbody").append(str);
				}
			}
		});
	}
	
	function changeStatus(availability){ 
		if (availability == false){
			$("#status").val("true");
			$("#status").text("Start");
			$("#status").attr({"title" : "Start distributing tickets"});
		}
		else{
			$("#status").val("false");
			$("#status").text("Stop");
			$("#status").attr({"title" : "Stop distributing tickets"});
		}
	}
	
	var date = new Date();
	function toTime(timestamp){
		date.setTime(timestamp);
		return ("00" + date.getHours()).slice(-2) + ":" + ("00" + date.getMinutes()).slice(-2);
	}
	
	$(document).ready(function() {
		$.ajax({
			url: "http://<%=request.getServerName()%>:7001/rms/tickettypes",
			data: {id: <%=id%>},
			success: function(data) {
				for (i = 0; i < data.length; i++)
					$("#showtickets").append("<button value='"+ data[i].type + "' "
						+ "title='Show "+ String.fromCharCode(data[i].type+64) +" tickets'>"
						+ String.fromCharCode(data[i].type+64) + "</button>");
			}
		});
		
		$.ajax({
			url: "http://<%=request.getServerName()%>:7001/rms/restaurant",
			data: {id: id},
			cache: false,
			success: function(data) {
				changeStatus(data.availability);
				$("h2").append("Welcome to the Reservation Management System for " + data.name + ".");
			}
		});
		
		$(".content").hide();
		
		$("#showtickets").on("click", "button", function(){
			type = $(this).val();
			$("h2").text($(this).text() + " Tickets");
			$(".content").hide();
			$("#waitinglist").show();
			showTickets();
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/tickettypes",
				data: {id: id},
				cache: false,
				success: function(data) {
					if (type == 1) i = 1;
					else i = data[type-2].maxSize + 1;
					$("#add").empty();
					$("#add").append("<option>Add Ticket</option>")
					for (; i <= data[type-1].maxSize; i++)
						$("#add").append("<option>" + i + "</option>");
				}
			});
			$("#call").val($(this).val());
		});
		
		$("#add").hover(function(){
		    $("#add option:first-child").text("Select Size");
		}, function(){
		    $("#add option:first-child").text("Add Ticket");
		});
		
		$("#add").change(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/ticket/create",
				data: {id: id, type: type, size: $("#add").val()},
				cache: false,
				success: function(data) {
					showTickets();
				}
			});
			$("#add")[0].selectedIndex = 0;
		});
		
		$("#status").click(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant/waitinglist",
				data: {id: id, status: $("#status").val()},
				cache: false,
				success: function(data) {
					if (data == true) changeStatus($("#status").val() == "true");
				}
			});
		});
	});
</script>
</head>

<body>
	<div id="menu">
		<span id=showtickets><button disabled>Show Tickets</button></span>
		<button disabled>Distribute Tickets</button><button id="status"></button>
		<button disabled>Modify Info</button><button title="Modify restaurant information">Restaurant</button><button title="Modify account information">Account</button>
		<button>Sign Out</button>
	</div>
	<div>
		<h2></h2>
		<div class="content" id="waitinglist">
			<select id="add" title="Select party size to add a new ticket" style="width:92px"></select>
			<button id="call" title="Call the next ticket">Call Ticket</button>
			<table>
				<tr>
					<th>Ticket No.</th>
					<th>Party Size</th>
					<th>Get Time</th>
					<th>Call Time</th>
					<th><button>Clear All</button></th>
				</tr>
				<tbody id="waitinglistbody"></tbody>
			</table>
		</div>
	</div>
</body>
</html>