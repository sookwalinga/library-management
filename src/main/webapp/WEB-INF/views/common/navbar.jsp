<nav class="navbar">
    <h1>📚 Library Management System</h1>
    <ul>
        <li><a href="${pageContext.request.contextPath}/" ${currentPage == 'home' ? 'class="active"' : ''}>Home</a></li>
        <li><a href="${pageContext.request.contextPath}/books" ${currentPage == 'books' ? 'class="active"' : ''}>Books</a></li>
        <li><a href="${pageContext.request.contextPath}/authors" ${currentPage == 'authors' ? 'class="active"' : ''}>Authors</a></li>
    </ul>
</nav>
