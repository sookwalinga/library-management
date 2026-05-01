<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Books List</title>
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
        <h2>All Books with Authors</h2>
        <p style="color: #666; margin-bottom: 15px;">
            <em>Data shown via INNER JOIN between Books and Authors tables.</em>
        </p>

        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/books/new"
               class="btn btn-success">+ Add New Book</a>
        </div>

        <div class="filter-bar">
            <form action="${pageContext.request.contextPath}/books" method="get">
                <label for="genre"><strong>Filter by Genre:</strong></label>
                <input type="text" id="genre" name="genre"
                       value="${filterGenre}" placeholder="e.g. Fantasy"/>
                <button type="submit" class="btn">Filter</button>
                <c:if test="${not empty filterGenre}">
                    <a href="${pageContext.request.contextPath}/books"
                       class="btn btn-secondary">Clear</a>
                </c:if>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty books}">
                <p>No books found.</p>
            </c:when>
            <c:otherwise>
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
                                    <a href="${pageContext.request.contextPath}/books/edit/${book.bookId}"
                                       class="btn btn-warning">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
