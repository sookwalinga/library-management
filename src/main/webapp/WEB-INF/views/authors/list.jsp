<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authors List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
    <c:set var="currentPage" value="authors" />
    <%@ include file="../common/navbar.jsp" %>

    <div class="page-body">
        <div class="container">
            <h2>All Authors</h2>
            <hr class="section-divider">

            <div class="action-bar">
                <a href="${pageContext.request.contextPath}/authors/new" class="btn btn-success">+ Add New Author</a>
            </div>

            <c:choose>
                <c:when test="${empty authors}">
                    <div class="empty-state">
                        <p>No authors found. Add some!</p>
                        <a href="${pageContext.request.contextPath}/authors/new" class="btn btn-success">+ Add New Author</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Nationality</th>
                                    <th>Birth Year</th>
                                    <th>Biography</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="author" items="${authors}">
                                    <tr>
                                        <td>${author.id}</td>
                                        <td><c:out value="${author.name}"/></td>
                                        <td><c:out value="${author.nationality}"/></td>
                                        <td>${author.birthYear}</td>
                                        <td><c:out value="${author.biography}"/></td>
                                        <td>
                                            <div class="td-actions">
                                                <a href="${pageContext.request.contextPath}/authors/edit/${author.id}"
                                                   class="btn btn-warning btn-sm">Edit</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>
