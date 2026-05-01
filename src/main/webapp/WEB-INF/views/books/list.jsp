<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Books List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
    <c:set var="currentPage" value="books" />
    <%@ include file="../common/navbar.jsp" %>

    <div class="page-body">
        <div class="container">
            <h2>All Books</h2>
            <p class="page-subtitle">Data shown via INNER JOIN between Books and Authors tables.</p>
            <hr class="section-divider">

            <div class="action-bar">
                <a href="${pageContext.request.contextPath}/books/new" class="btn btn-success">+ Add New Book</a>
            </div>

            <div class="filter-bar">
                <form action="${pageContext.request.contextPath}/books" method="get">
                    <label for="genre">Filter by Genre:</label>
                    <input type="text" id="genre" name="genre"
                           value="${filterGenre}" placeholder="e.g. Fantasy"/>
                    <button type="submit" class="btn">Filter</button>
                    <c:if test="${not empty filterGenre}">
                        <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Clear</a>
                    </c:if>
                </form>
            </div>

            <c:choose>
                <c:when test="${empty books}">
                    <div class="empty-state">
                        <p>No books found. Try a different filter or add a new book.</p>
                        <a href="${pageContext.request.contextPath}/books/new" class="btn btn-success">+ Add New Book</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-wrapper">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>ISBN</th>
                                    <th>Author</th>
                                    <th>Nationality</th>
                                    <th>Year</th>
                                    <th>Genre</th>
                                    <th>Price</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="book" items="${books}">
                                    <tr>
                                        <td>${book.bookId}</td>
                                        <td><c:out value="${book.bookTitle}"/></td>
                                        <td><c:out value="${book.isbn}"/></td>
                                        <td><c:out value="${book.authorName}"/></td>
                                        <td><c:out value="${book.nationality}"/></td>
                                        <td>${book.publicationYear}</td>
                                        <td><c:out value="${book.genre}"/></td>
                                        <td>$<c:out value="${book.price}"/></td>
                                        <td>
                                            <div class="td-actions">
                                                <a href="${pageContext.request.contextPath}/books/edit/${book.bookId}"
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
