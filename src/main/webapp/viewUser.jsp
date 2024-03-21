<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/admin/adminHeader.jsp" %>
<%@include file="include/admin/adminNavigator.jsp" %>

<title>iew user info</title>


<div class="workingArea">

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">view user info</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="/users?action=update">
                <table class="editTable">
                    <tr>
                        <td>user firstName</td>
                        <td><input id="name" name="firstName" value="${user.firstName}" readonly="readonly"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user lastName</td>
                        <td><input id="lastName" name="firstName" value="${user.lastName}" readonly="readonly"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user age</td>
                        <td><input id="age" name="firstName" value="${user.age}"
                                   type="text" readonly="readonly" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user location</td>
                        <td><input id="location" name="firstName" value="${user.location}"
                                   type="text" readonly="readonly" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user password</td>
                        <td><input id="stock" value="${user.password}" name="password" type="text" readonly="readonly"
                                   class="form-control"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>