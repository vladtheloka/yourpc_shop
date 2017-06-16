<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="fixed-header">
    <div class="container">
        <nav>
            <sec:authentication property="name"/>
            <sec:authorize access="hasRole('ROLE_USER')">
                <a href="<c:url value="/"/>">Home</a>
                <a href="<c:url value="/profile"/>">Profile</a>
                <a href="<c:url value="/cart"/>">Cart</a>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <a href="<c:url value="/signUp"/>">SignUp</a>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <a href="<c:url value="/listOfUsers"/>">Users</a>
                <a href="<c:url value="/listOfItems"/>">Items</a>
                <a href="<c:url value="/listOfCategories"/>">Categories</a>
                <a href="<c:url value="/listOfBillables"/>">Billables</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <form:form action="/logout" method="post">
                    <button class="btn btn-default">Logout</button>
                </form:form>
            </sec:authorize>
        </nav>
    </div>
</div>