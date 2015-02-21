<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Hello jQuery</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$.ajax({
			url : "http://localhost:7001/server/test"
		}).then(function(data) {
			$('.greeting-id').append(data.id);
			$('.greeting-content').append(data.text);
		});
	});
</script>
</head>

<body>
	<div>
		<p class="greeting-id">The ID is </p>
		<p class="greeting-content">The content is </p>
	</div>
</body>
</html>