<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html>

                <head>
                    <title>Add New Book</title>
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
                </head>

                <body>
                    <c:set var="currentPage" value="books" />
                    <%@ include file="../common/navbar.jsp" %>

                        <div class="page-body">
                            <div class="container">
                                <h2>Add New Book</h2>
                                <hr class="section-divider">

                                <c:if test="${not empty errorMessage}">
                                    <div class="error-message">
                                        <strong>Error:</strong>
                                        <c:out value="${errorMessage}" />
                                    </div>
                                </c:if>

                                <div class="form-card">
                                    <form:form action="${pageContext.request.contextPath}/books" method="post"
                                        modelAttribute="book">

                                        <div class="form-grid">
                                            <div class="form-group">
                                                <form:label path="title">Title *</form:label>
                                                <form:input path="title" placeholder="e.g. The Great Gatsby" />
                                                <form:errors path="title" cssClass="field-error" />
                                            </div>

                                            <div class="form-group">
                                                <form:label path="isbn">ISBN *</form:label>
                                                <form:input path="isbn" placeholder="e.g. 978-0743273565" />
                                                <form:errors path="isbn" cssClass="field-error" />
                                            </div>

                                            <div class="form-group">
                                                <form:label path="publicationYear">Publication Year *</form:label>
                                                <form:input path="publicationYear" type="number"
                                                    placeholder="e.g. 1925" />
                                                <form:errors path="publicationYear" cssClass="field-error" />
                                            </div>

                                            <div class="form-group">
                                                <form:label path="genre">Genre</form:label>
                                                <form:input path="genre" placeholder="e.g. Fiction" />
                                                <form:errors path="genre" cssClass="field-error" />
                                            </div>

                                            <div class="form-group">
                                                <form:label path="price">Price</form:label>
                                                <form:input path="price" type="number" step="0.01"
                                                    placeholder="e.g. 14.99" />
                                                <form:errors path="price" cssClass="field-error" />
                                            </div>

                                            <div class="form-group">
                                                <label for="authorId">Author *</label>
                                                <select id="authorId" name="authorId" required>
                                                    <option value="">-- Select an author --</option>
                                                    <c:forEach var="auth" items="${authors}">
                                                        <option value="${auth.id}">
                                                            <c:out value="${auth.name}" />
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="action-bar">
                                            <button type="submit" class="btn btn-success">Save Book</button>
                                            <a href="${pageContext.request.contextPath}/books"
                                                class="btn btn-secondary">Cancel</a>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>

                        <%@ include file="../common/footer.jsp" %>
                </body>

            </html>