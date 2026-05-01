<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Author</title>
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
        <h2>Add New Author</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                <strong>Error:</strong> <c:out value="${errorMessage}"/>
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/authors"
                   method="post" modelAttribute="author">

            <div class="form-group">
                <form:label path="name">Name *</form:label>
                <form:input path="name" placeholder="e.g. J.K. Rowling"/>
                <form:errors path="name" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="nationality">Nationality</form:label>
                <form:input path="nationality" placeholder="e.g. British"/>
                <form:errors path="nationality" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="birthYear">Birth Year</form:label>
                <form:input path="birthYear" type="number" placeholder="e.g. 1965"/>
                <form:errors path="birthYear" cssClass="field-error"/>
            </div>

            <div class="form-group">
                <form:label path="biography">Biography</form:label>
                <form:textarea path="biography" rows="4"
                               placeholder="Brief biography..."/>
                <form:errors path="biography" cssClass="field-error"/>
            </div>

            <div class="action-bar">
                <button type="submit" class="btn btn-success">Save Author</button>
                <a href="${pageContext.request.contextPath}/authors"
                   class="btn btn-secondary">Cancel</a>
            </div>
        </form:form>
    </div>
</body>
</html>
