<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.presentation eq 'newsList'}">
	<c:import url="/WEB-INF/view/tiles/newsList.jsp" />
</c:if>

<c:if test="${requestScope.presentation eq 'viewNews'}">
	<c:import url="/WEB-INF/view/tiles/viewNews.jsp" />
</c:if>

<c:if test="${requestScope.commandsName eq 'addNews'}">
	<c:import url="/WEB-INF/view/tiles/addNews.jsp" />
</c:if>

<c:if test="${requestScope.commandsName eq 'editNews'}">
	<c:import url="/WEB-INF/view/tiles/editNews.jsp" />
</c:if>
<c:if test="${requestScope.commandsName eq 'userAccount'}">
	<c:import url="/WEB-INF/view/tiles/userPersonalAccount.jsp" />
</c:if>