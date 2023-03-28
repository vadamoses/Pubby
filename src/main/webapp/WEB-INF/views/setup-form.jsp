<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <style>
<%@include file ="/WEB-INF/views/styles.css"%>
</style>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Setup Form</title>
    </head>
    <body>
        <h1>Enter Quiz Details</h1>
        
        <form method ="get" action="/quiz/play-quiz">
            Enter number of quiz questions : <input type="number" name="quizSize"><br/><br/>
                <input type ="submit" value="SUBMIT">    
        </form>
    </body>
</html>