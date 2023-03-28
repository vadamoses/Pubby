<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
    <head>
    <style><%@include file="/WEB-INF/views/styles.css"%></style>
	<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Quiz Page</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
    <jsp:useBean id="qService" class="com.vada.interfaces.QuestionServiceImpl"/>
    
    <div class="body">
		<div class="quiz-question-heading">
			<h1 class="body-text-heading" id="">Quiz Question</h1>
		</div>
		<div id="quiz-question-List-Result" class="table-div-my">
		
            <form method ="post" action="/quiz/ask-question" modelAttribute="currentQuestion">
                ${currentQuestion.qText}<br/>
                ${currentQuestion.qAnswers}<br/>
            	Enter your Answer(s) : <input type="text" name="userAnswers"><br/><br/>
                <input type ="submit" value="SUBMIT">    
        	</form>
	        	
	    </div>
	        <h1 class="body-text-heading" id="">Score:</h1>
	        <br/>
        <p>score : <jsp:getProperty name="qService" property="score"/></p>
		
	</div>

	
		<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>