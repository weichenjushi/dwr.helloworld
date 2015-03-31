<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>DWR入门</title>
    <script type="text/javascript" src="js/jquery-1.6.2.js"></script>
    <script type='text/javascript' src='dwr/interface/Hello.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>
    <script type="text/javascript">
        function helloChinese(){
            var user=$('#user').val();
            /*alert(user);*/
            Hello.sayHelloChinese(user,callback);
            dwr.engine.setActiveReverseAjax(true);
        }
        function helloEnglish(){
            var user=$('#user').val();
//            alert(user);
            Hello.sayHelloEnglish(user,callback);
        }
        function callback(msg){
            dwr.util.setValue('result',msg);
        }
    </script>

</head>
<body>
<input id="user" type="text">
<input type="button" value="中文打招呼" onclick="helloChinese()">
<input type="button" value="英文打招呼" onclick="helloEnglish()">
<div id="result"></div>
</body>
</html>