<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% if (session.getAttribute("access_token") == null) { 
	response.sendRedirect("index.jsp");	 
}  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Quicksand" />
<link rel="stylesheet" type="text/css" href="css/pretty-json.css" />
<title>URL 단축 테스트</title>
</head>
<body>
	긴 URL : <input type="text" id="long" value="" /><br />
	<button id="shorten">URL 짧게!!</button>
	<hr />
	짧은 URL : <span id="short"></span><br />
	수신 데이터 : <br />
	<div style="border: solid 1px gray;" id="json">
		&nbsp;
	</div>
    <script type="text/javascript" src="js/jquery.min.js" ></script>
    <script type="text/javascript" src="js/underscore-min.js" ></script>
    <script type="text/javascript" src="js/backbone-min.js" ></script>
    <script type="text/javascript" src="js/pretty-json-min.js" ></script>
	<script type="text/javascript">
	var param = { longUrl : ""  };
	$("#shorten").click(function() {
		param.long_url = $("#long").val();
		$.get("shorten.jsp", param, function(response) {
            var node = new PrettyJSON.view.Node({ 
                el:$("#json"), 
                data: response
            });
            console.log(response);
			$("#short").html(response.link);
		});		
	})
	</script>
</body>
</html>
	