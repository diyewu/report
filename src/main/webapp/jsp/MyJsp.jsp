<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=gbk">  
<title>img usemap属性</title>  
</head>  
  
<body>  
    <img src="../images/map.jpg" usemap="#Map" border="1"/>  
       
    <map name="Map" class="maparea">  
        <area shape="rect" coords="73,87,103,104" href="javascript:alert('新疆')">  
        <area shape="rect" coords="144,125,173,142" href="javascript:alert('青海')">  
        <area shape="rect" coords="84,158,115,175" href="javascript:alert('西藏')">  
        <area shape="rect" coords="220,90,257,105" href="javascript:alert('内蒙古')">  
        <area shape="rect" coords="348,45,389,61" href="javascript:alert('黑龙江')">  
        <area shape="rect" coords="346,68,371,83" href="javascript:alert('吉林')">  
        <area shape="rect" coords="323,84,351,100" href="javascript:alert('辽宁')">  
        <area shape="rect" coords="298,100,326,114" href="javascript:alert('天津')">  
        <area shape="rect" coords="288,120,314,136" href="javascript:alert('山东')">  
        <area shape="rect" coords="268,107,295,121" href="javascript:alert('河北')">  
        <area shape="rect" coords="207,116,235,130" href="javascript:alert('宁夏')">  
        <area shape="rect" coords="186,162,214,179" href="javascript:alert('四川')">  
        <area shape="rect" coords="256,142,281,157" href="javascript:alert('河南')">  
        <area shape="rect" coords="305,144,332,159" href="javascript:alert('江苏')">  
        <area shape="rect" coords="320,162,342,177" href="javascript:alert('上海')">  
        <area shape="rect" coords="216,168,243,185" href="javascript:alert('重庆')">  
        <area shape="rect" coords="250,162,277,177" href="javascript:alert('湖北')">  
        <area shape="rect" coords="283,161,308,177" href="javascript:alert('安徽')">  
        <area shape="rect" coords="213,196,239,209" href="javascript:alert('贵州')">  
        <area shape="rect" coords="243,183,269,198" href="javascript:alert('湖南')">  
        <area shape="rect" coords="273,185,299,200" href="javascript:alert('江西')">  
        <area shape="rect" coords="308,177,334,193" href="javascript:alert('浙江')">  
        <area shape="rect" coords="179,210,204,225" href="javascript:alert('云南')">  
        <area shape="rect" coords="227,214,252,228" href="javascript:alert('广西')">  
        <area shape="rect" coords="231,248,257,263" href="javascript:alert('海南')">  
        <area shape="rect" coords="292,200,319,214" href="javascript:alert('福建')">  
        <area shape="rect" coords="259,219,287,231" href="javascript:alert('广东')">  
        <area shape="rect" coords="244,231,270,246" href="javascript:alert('澳门')">  
        <area shape="rect" coords="276,232,304,248" href="javascript:alert('香港')">  
        <area shape="rect" coords="311,228,338,243" href="javascript:alert('台湾')">  
        <area shape="rect" coords="272,90,302,107" href="javascript:alert('北京')">  
        <area shape="rect" coords="196,130,222,145" href="javascript:alert('甘肃')">  
        <area shape="rect" coords="246,116,272,130" href="javascript:alert('山西')">  
        <area shape="rect" coords="224,136,252,151" href="javascript:alert('陕西')">  
    </map>  
</body>  
</html>  

