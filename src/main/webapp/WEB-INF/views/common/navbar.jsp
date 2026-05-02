<nav class="navbar">
    <div class="navbar-brand">
        <img src="${pageContext.request.contextPath}/resources/images/logo.svg" alt="Library Logo" class="navbar-logo">
        <h1>Library Management System</h1>
    </div>
    <ul>
        <li><a href="${pageContext.request.contextPath}/" ${currentPage=='home' ? 'class="active"' : '' }>Home</a></li>
        <li><a href="${pageContext.request.contextPath}/books" ${currentPage=='books' ? 'class="active"' : ''
                }>Books</a></li>
        <li><a href="${pageContext.request.contextPath}/authors" ${currentPage=='authors' ? 'class="active"' : ''
                }>Authors</a></li>
    </ul>
</nav>