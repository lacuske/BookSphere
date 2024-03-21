<%--
  Created by IntelliJ IDEA.
  User: czwbig
  Date: 2018/10/11
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" import="java.util.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="include/admin/adminHeader.jsp" %>
<%@include file="include/admin/adminNavigator.jsp" %>

<html>
<head>
    <title>userAdmin</title>
    <script>

    </script>
</head>
<body>
<div class="workingArea">

    <br>
    <br>


    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <caption>
                <h2 class="label label-info">userAdmin</h2>
                <span class="label label-success"><a href="/users?action=createPage">addUser</a></span>
            </caption>
            <thead>
            <tr class="success">
                <th>userID</th>
                <th>user firstName</th>
                <th>user lastName</th>
                <th>user age</th>
                <th>user password</th>
                <th>user location</th>
                <th>user operation</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.userID}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.password}</td>
                    <td>${user.location}</td>
                    <td>
                        <span class="label label-info"><a href="/users?action=get&userId=${user.userID}">viewUser</a></span>
                        <span class="label label-success"><a href="/users?action=updatePage&userId=${user.userID}">editUser</a></span>
                        <span class="label label-danger"><a href="/users?action=delete&userId=${user.userID}">deleteUser</a></span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<%@include file="include/admin/adminFooter.jsp" %>
</body>
</html>
