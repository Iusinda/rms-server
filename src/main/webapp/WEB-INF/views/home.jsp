<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!
	int id = 1;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
	* {
		text-align: center;
	}
	#add, #call{
		width: 120px;
	}
	th, td{
		width: 98px;
	}
	td.remove{
		width: 10px;
		text-align: center;
	}
	.fieldname{
  		display: inline-block;
		width: 120px;
		text-align: left;
	}
	.long.fieldname{
		width: 167px;
	}
	.field{
		width: 360px;
		text-align: left;
	}
	.short.field{
		width: 176px;
	}
	.long.field{
		height: 50px;
		width: 360px;
	}
</style>
<title>RMS</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	var id = <%=id%>, type, number, districtId, refresh = false;
	var date = new Date();
	function toTime(timestamp){
		date.setTime(timestamp);
		return ("00" + date.getHours()).slice(-2) + ":" + ("00" + date.getMinutes()).slice(-2);
	}
	
	function changeStatus(availability){ 
		if (availability == false){
			$("#distribute").val("true");
			$("#distribute").text("Start");
			$("#distribute").attr({"title" : "Start distributing tickets"});
		}
		else{
			$("#distribute").val("false");
			$("#distribute").text("Stop");
			$("#distribute").attr({"title" : "Stop distributing tickets"});
		}
	}
	
	function showTickets(){
		$("#tickettable").empty();
		$.ajax({
			url : "http://<%=request.getServerName()%>:7001/rms/tickets",
			data: {id: id, type: type},
			cache: false,
			success: function(data) {
				for (i = 0; i < data.length; i++){
					str = "<tr>"
						+ "<td>" + data[i].number + "</td>"
						+ "<td>" + data[i].size + "</td>"
						+ "<td>" + toTime(data[i].getTime) + "</td>"
						+ "<td>";
					if (data[i].callTime != null){
						str += toTime(data[i].callTime);
					}
					str += "</td><td class='remove'><button title='Remove ticket' value='"
						+ data[i].number + "'>X</button></td></tr>";
					$("#tickettable").append(str);
				}
			}
		});
	}
	
	function showDistricts(select){
		$.ajax({
			url : "http://<%=request.getServerName()%>:7001/rms/districts",
			data: {areaId: ($("#areaId")[0].selectedIndex + 1)},
			success: function(data) {
				$("#districtId").empty();
				for (i = 0; i < data.length; i++)
					$("#districtId").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
				if (select) $("#districtId option[value='" + districtId + "']").attr("selected", "selected");
			}
		});
	}
	
	function modifyRestaurant(){
		alert(name);
	}
	
	$(document).ready(function() {
		$.ajax({
			url: "http://<%=request.getServerName()%>:7001/rms/tickettypes",
			data: {id: id},
			success: function(data) {
				for (i = 0; i < data.length; i++)
					$("#show").append("<button value='"+ data[i].type + "' "
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
		
		// Show Tickets
		$("#show").on("click", "button", function(){
			type = $(this).val();
			showTickets();
			$("h2").text($(this).text() + " Tickets");
			$(".content").hide();
			$("#tickets").show();
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
			clearTimeout(refresh);
			refresh = setInterval(function(){showTickets()}, 5000);
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
		
		$("#call").click(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/ticket/call",
				data: {id: id, type: type},
				cache: false,
				success: function(data) {
					showTickets();
				}
			});
		});
		
		$("#tickettable").on("click", ".remove button", function(){
			number = $(this).val();
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/ticket/remove",
				data: {id: id, type:type, number:number},
				cache: false,
				success: function(data) {
					showTickets();
				}
			});
		});
		
		$("#distribute").click(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant/waitinglist",
				data: {id: id, status: $("#distribute").val()},
				cache: false,
				success: function(data) {
					if (data == true) changeStatus($("#distribute").val() == "true");
				}
			});
		});
		
		// Modify Info: Restaurant
		$("#modifyRestaurant").click(function(){
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/areas",
				success: function(data) {
					$("#areaId").empty();
					for (i = 0; i < data.length; i++)
						$("#areaId").append("<option>" + data[i].name + "</option>");
				}
			});		
		
			$.ajax({
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant",
				data: {id: id},
				cache: false,
				success: function(data) {
					$(".content").hide();
					$("h2").text("Restaurant Information");
					districtId = data.districtId;
					$.ajax({
						url : "http://<%=request.getServerName()%>:7001/rms/district",
						data: {districtId: data.districtId},
						success: function(data) {
							$("#areaId")[0].selectedIndex = data.areaId - 1;
							showDistricts(true);
						}
					});
					$("#name").val(data.name);
					$("#phoneNo").val(data.phoneNo);
					$("#openingHours").val(data.openingHours);
					$("#address").val(data.address);
					$("#description").val(data.description);
					$("#submitRestaurant").val("Save Changes");
					$("#submitRestaurant").attr("disabled",true);
					
					$("#restaurant").show();
					clearTimeout(refresh);
				}
			});
		});
		
		$(".field").change(function(){
			$("#submitRestaurant").val("Save Changes");
			$("#submitRestaurant").attr("disabled",false);
		});
		
		$("#areaId").change(function(){
			showDistricts(false);
		});
		
		$("#submitRestaurant").click(function(){
			$.ajax({
  				type: 'POST',
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant/edit",
				data: {
					id: id,
					name: $("#name").val(),
					phoneNo: $("#phoneNo").val(),
					openingHours: $("#openingHours").val(),
					districtId: $("#districtId").val(),
					address: $("#address").val(),
					description: $("#description").val()
				},
				cache: false,
				success: function(data) {
					if (data == true){
						$("#submitRestaurant").val("Saved Changes");
						$("#submitRestaurant").attr("disabled",true); 
					}
				}
			});
		});
		
		// Modify Info: Account
		$("#modifyAccount").click(function(){
			$("h2").text("Account Information");
			$(".content").hide();
			$("#account input").val("");
			$("#submitAccount").val("Change Password");
			$("#submitAccount").attr("disabled",true); 
			$("#account").show();
			clearTimeout(refresh);
		});
		
		$("#newPassword").change(function(){
			$("#submitAccount").val("Change Password");
			$("#submitAccount").attr("disabled",false); 
		});
		
		$("#submitAccount").click(function(){
			if ($("#newPassword").val() == "")
				alert("Password cannot be empty.");
			else if ($("#newPassword").val() != $("#newPassword2").val())
				alert("Passwords do not match.");
			else $.ajax({
  				type: 'POST',
				url : "http://<%=request.getServerName()%>:7001/rms/restaurant/password",
				data: {
					id: id,
					password: $("#password").val(),
					newPassword: $("#newPassword").val()
				},
				cache: false,
				success: function(data) {
					if (data == true){
						$("#submitAccount").val("Password Changed");
						$("#submitAccount").attr("disabled",true); 
					} else
						alert("Original password is incorrect.");
				}
			});
		});
		
		// Sign Out
	});
</script>
</head>

<body>
	<div id="menu"><br>
		<span id="show"><button disabled>Show Tickets</button></span>&nbsp;&nbsp;&nbsp;
		<button disabled>Distribute Tickets</button><button id="distribute"></button>&nbsp;&nbsp;&nbsp;&nbsp;
		<button disabled>Modify Info</button><button id="modifyRestaurant" title="Modify restaurant information">Restaurant</button><button id="modifyAccount" title="Modify account information">Account</button>&nbsp;&nbsp;&nbsp;
		<button>Sign Out</button>
	</div>
	<div><br>
		<h2></h2>
		<div class="content" id="tickets">
			<select id="add" title="Add a new ticket"></select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="call" title="Call the next ticket">Call Ticket</button><br><br>
			<table align="center">
				<tr>
					<th>Ticket No.</th>
					<th>Party Size</th>
					<th>Get Time</th>
					<th>Call Time</th>
					<th></th>
				</tr>
				<tbody id="tickettable"></tbody>
			</table>
		</div>
		<div class="content" id="restaurant"><br>
			<div class="fieldname">Name</div>
			<input type="text" class="field" id="name"><br><br>
			<div class="fieldname">Phone Number</div>
			<input type="text" class="field" id="phoneNo"><br><br>
			<div class="fieldname">Opening Hours</div>
			<input type="text" class="field" id="openingHours"><br><br>
			<div class="fieldname">District</div>
			<select class="short field" id="areaId"></select>&nbsp;&nbsp;
			<select class="short field" id="districtId"></select><br><br>
			<div class="fieldname">Address</div>
			<input type="text" class="long field" id="address"><br><br>
			<div class="fieldname">Description</div>
			<input type="text" class="long field" id="description"><br><br><br>
			<input type="button" id="submitRestaurant"><br><br><br>
		</div>
		<div class="content" id="account"><br>
			<div class="long fieldname">Old Password</div>
			<input type="password" class="short field" id="password"><br><br>
			<div class="long fieldname">New Password</div>
			<input type="password" class="short field" id="newPassword"><br><br>
			<div class="long fieldname">Repeat New Password</div>
			<input type="password" class="short field" id="newPassword2"><br><br><br>
			<input type="button" id="submitAccount"><br><br><br>
		</div>
	</div>
</body>
</html>