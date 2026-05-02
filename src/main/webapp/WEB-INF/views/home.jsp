<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

            <head>
                <title>Library Management System</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
            </head>

            <body>
                <c:set var="currentPage" value="home" />
                <%@ include file="common/navbar.jsp" %>

                    <div class="page-body">
                        <div class="container">
                            <div class="welcome-banner">
                                <h1>Welcome to the Library</h1>
                                <p>Manage your books and authors efficiently with our system.</p>
                            </div>

                            <div class="dashboard">
                                <div class="card">
                                    <div class="card-icon">📖</div>
                                    <h3>Total Books</h3>
                                    <div class="number">${totalBooks}</div>
                                    <div class="card-actions">
                                        <a href="${pageContext.request.contextPath}/books" class="btn">View All
                                            Books</a>
                                        <a href="${pageContext.request.contextPath}/books/new" class="btn btn-success">+
                                            Add
                                            Book</a>
                                    </div>
                                </div>

                                <div class="card">
                                    <div class="card-icon">✍️</div>
                                    <h3>Total Authors</h3>
                                    <div class="number">${totalAuthors}</div>
                                    <div class="card-actions">
                                        <a href="${pageContext.request.contextPath}/authors" class="btn">View All
                                            Authors</a>
                                        <a href="${pageContext.request.contextPath}/authors/new"
                                            class="btn btn-success">+
                                            Add Author</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%@ include file="common/footer.jsp" %>
            </body>

        </html>