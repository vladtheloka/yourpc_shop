<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<div>
    <div class="container">

        <form action="<c:url value="/category"/>">
            <input type="submit" value="<spring:message code="label.AddCategory"/>" />
        </form>

        <table class="table table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th colspan=2>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="category">
                <tr>
                    <td><c:out value="${category.id}" /></td>
                    <td><c:out value="${category.name}" /></td>
                    <td><a href="/deleteCategory/${category.id}"><spring:message code="label.Delete"/></a></td>
                    <td><a href="/updateCategory/${category.id}"><spring:message code="label.Update"/></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>