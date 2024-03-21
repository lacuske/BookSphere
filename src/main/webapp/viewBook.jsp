<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/admin/adminHeader.jsp" %>
<%@include file="include/admin/adminNavigator.jsp" %>

<title>view book info</title>


<div class="workingArea">

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">view book info</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="/users?action=update">
                <table class="editTable">

                    <tr>
                        <td>book bookID</td>
                        <td><input id="bookID" name="title" value="${book.bookID}" readonly="readonly"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book title</td>
                        <td><input id="Title" name="title" value="${book.title}" readonly="readonly"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book ISBN</td>
                        <td><input id="isbn" name="isbn" value="${book.isbn}" readonly="readonly"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book bookYear</td>
                        <td><input id="bookYear" name="bookYear" value="${book.bookYear}"
                                   type="text" readonly="readonly" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book bookType</td>
                        <td><input id="bookType" name="bookType" value="${book.bookType}"
                                   type="text" readonly="readonly" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book authorName</td>
                        <td><input id="authorName" value="${book.author.authorName}" name="authorName" type="text" readonly="readonly"
                                   class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book publisherName</td>
                        <td><input id="publisherName" value="${book.publisher.publisherName}" name="publisherName" type="text" readonly="readonly"
                                   class="form-control"></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>