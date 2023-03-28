<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View The Questions</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
					<tr>
						<th>Question Id</th>
						<th>Question Context</th>
						<th>Question text</th>
						<th>Question Answer(s)</th>
					</tr>
            </thead>
            <tbody>
                <c:forEach items="${questionsList}" var="question">
                    <tr>
                        <td>${question.qId}</td>
                        <td>${question.qContext}</td>
                        <td>${question.qText}</td>
                        <td>${question.qAnswers}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>