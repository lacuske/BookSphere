<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/admin/adminHeader.jsp" %>
<%@include file="include/admin/adminNavigator.jsp" %>

<title>新增</title>

<script>
    $(function () {
        $("#editForm").submit(function () {
            if (!checkEmpty("name", "产品名称"))
                return false;
//          if (!checkEmpty("subTitle", "小标题"))
//              return false;
            if (!checkNumber("originalPrice", "原价格"))
                return false;
            if (!checkNumber("promotePrice", "优惠价格"))
                return false;
            if (!checkInt("stock", "库存"))
                return false;
            return true;
        });
    });
</script>

<div class="workingArea">

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">add user</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="/users?action=create">
                <table class="editTable">
                    <tr>
                        <td>user firstName</td>
                        <td><input id="name" name="firstName" value="${user.firstName}"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user lastName</td>
                        <td><input id="lastName" name="lastName" value="${user.lastName}"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user age</td>
                        <td><input id="age" name="age" value="${user.age}"
                                   type="text"  class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user location</td>
                        <td><input id="location" name="location" value="${user.location}"
                                   type="text"  class="form-control"></td>
                    </tr>
                    <tr>
                        <td>user password</td>
                        <td><input id="password" value="${user.password}" name="password" type="text"
                                   class="form-control"></td>
                    </tr>

                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="userID" value="${user.userID}">
                            <button type="submit" class="btn btn-success">subumit</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>