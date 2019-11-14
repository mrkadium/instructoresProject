</head>
<body>
    <nav>
        <a href="${pageContext.servletContext.contextPath}/Principal"><img src="img/Logo-USO.png" alt=""></a>
        <div class="desk">
            <c:forEach var="menu" items="${MenuPrincipal}">
                <a href="${pageContext.servletContext.contextPath}${menu.url}">${menu.menu}</a>
            </c:forEach>
        </div>
        <div class="usuario">
            <img src="img/user.png" alt="">
            <div>
                <p>${Nombre}</p>
                <span>[${user}]</span>
                <a href="/InstructoresProject/Logout" class="cs">Cerrar sesión</a>
            </div>
        </div>
        <div class="burger">
            <i class="fas fa-times"></i>
            <i class="fas fa-bars"></i>
        </div>
        <div class="mob">
            <c:forEach var="menu" items="${MenuPrincipal}">
                <a href="${pageContext.servletContext.contextPath}${menu.url}">${menu.menu}</a>
            </c:forEach>
            <a href="/InstructoresProject/Logout" class="close">Cerrar sesión</a>
        </div>
    </nav>
    <a href="" class="regresar"><i class="fas fa-arrow-left"></i> Regresar</a>