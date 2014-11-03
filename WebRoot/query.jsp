<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>订购条件</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
<form action="/single/QueryServlet" method="get">
	
	<table>
		<tr>
			<td>出发地：</td>
			<td><input type="text" name="fromStation"></td>
		</tr>
		<tr>
			<td>目的地：</td>
			<td><input type="text" name="toStation"></td>
		</tr>
		<tr>
			<td>出发日期:</td>
			<td><input type="text" name="trainDate">(2014-20-06)</td>
		</tr>
		<tr>
			<td>发车时间：</td>
			<td><input type="text" name="startTime">(12:01)</td>
		</tr>
		<tr>
			<td>车次类型：</td>
			<td><input type="text"></td>
		</tr>
		<tr>
			<td>出发车站：</td>
			<td><input type="text"></td>
		</tr>
		<tr>
			<td>坐车类型，（软卧 硬卧 软座 硬座 无座）</td>
			<td><input type="checkbox"></td>
		</tr>
		<tr>
			<td>坐车类型，（商务座 特等座 一等座 二等座 高级软卧）</td>
			<td><input type="checkbox"></td>
		</tr>
	</table>
	<input type="submit" value="提交">
	</form>
</body>
</html>
