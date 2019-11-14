<%@include file="_head.jsp" %>
<%@include file="_nav.jsp" %>

<style>
    <c:forEach var="menu" items="${MenuPrincipal}">
        #${menu.idcss}{background-image: url("${pageContext.servletContext.contextPath}/img/${menu.img}");}
    </c:forEach>
</style>
<main>
    <div class="fondo">
        <c:forEach var="menu" items="${MenuPrincipal}">
            <c:if test="${menu.idmenu != 1}">
                <article id="${menu.idcss}" onclick="location.href = '${pageContext.servletContext.contextPath}${menu.url}'">
                    <div>
                        <h4>${menu.menu}</h4>
                        <p>${menu.descripcion}</p>
                    </div>
                </article>
            </c:if>
        </c:forEach>
    </div>
</main>
        
<%@include file="_footer.jsp" %>
