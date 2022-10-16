<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>



<!DOCTYPE html>
<html>
<head>
<title>News Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <style>
   body {
  background: #eeeeee url(resources/images/newsBackground.jpg) no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  -ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src= resources/images/newsBackground.jpg', sizingMethod='scale')";
  filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='. resources/images/newsBackground.jpg', sizingMethod='scale');
}
 </style>
<link rel="stylesheet" type="text/css" href=<c:url value="/resources/styles/newsStyle.css"/> />
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
<script type="text/javascript" src="scripts/validation.js"></script>
</head>
<body>
	<div class="page">	    
		<div class="header">
			<c:import url="/WEB-INF/view/tiles/header.jsp" />
		</div>				
		<div class="base-layout-wrapper">
			<div class="menu">
				<c:if test="${not (sessionScope.user_status eq 'active')}">				
				    <center><h1><spring:message code="local.loc.name.welcome"/>!</h1></center>
					<%-- <c:import url=""></c:import> --%>
				</c:if>
				<c:if test="${sessionScope.user_status eq 'active'}">
					<c:import url="/WEB-INF/view/tiles/menu.jsp" />
				</c:if>
			</div>			
		    <div class="content">
		        <c:if test="${not (sessionScope.user_status eq 'active')}"> 
				    <c:import url="/WEB-INF/view/tiles/guestInfo.jsp" />
				</c:if>				
				<c:if test="${sessionScope.user_status eq 'active'}">
					<c:import url="/WEB-INF/view/tiles/body.jsp" />
				</c:if>				
               <div class="registration">
		            <c:if test="${sessionScope.register_user eq 'not_registered'}">
		            <c:if test="${sessionScope.commandsName eq 'register'}">
				         <c:import url="/WEB-INF/view/tiles/registration.jsp" />
				    </c:if>
				    </c:if>				     
		      </div>
		      <div class="userPersonalAccount">		            
		            <c:if test="${sessionScope.commandsName eq 'userAccount'}">
				         <c:import url="/WEB-INF/view/tiles/userPersonalAccount.jsp" />
				    </c:if>				    	     
		      </div>				      
		      <div class="addNews">
		            <c:if test="${sessionScope.commandsName eq 'addNews'}">
		                <c:import url="/WEB-INF/view/tiles/addNews.jsp" />	                 
					</c:if>
		      </div>		      
		      <div class="editNews">
		            <c:if test="${sessionScope.commandsName eq 'editNews'}">
		                <c:import url="/WEB-INF/view/tiles/editNews.jsp" />
	                </c:if>			     
		      </div>		      
		     </div>				
		</div>
		<div class="footer">
			<c:import url="/WEB-INF/view/tiles/footer.jsp" />
		</div>		
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>