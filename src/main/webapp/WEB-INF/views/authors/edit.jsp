<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Author</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
    <nav class="navbar">
        <h1>📚 Library Management System</h1>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
            <li><a href="${pageContext.request.contextPath}/authors">Authors</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>Edit Author</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                <strong>Error:</strong> <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/authors/update/${author.id}"
                   method="post" modelAttribute="author">

            <form:hidden path="id"/>

            <div class="form-group">
                <form:label path="name">Name *</form:label>
                <form:input path="name"/>
                <form:errors path="name" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="nationality">Nationality</form:label>
                <form:input path="nationality"/>
                <form:errors path="nationality" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="birthYear">Birth Year</form:label>
                <form:input path="birthYear" type="number"/>
                <form:errors path="birthYear" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="biography">Biography</form:label>
                <form:textarea path="biography" rows="4"/>
                <form:errors path="biography" cssClass="field-error"/>
            </div>

            <div class="action-bar">
                <button type="submit" class="btn btn-success">Update Author</button>
                <a href="${pageContext.request.contextPath}/authors"
                   class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</body>
</html>
