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
    <title>bookAdmin</title>
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
                <h2 class="label label-info">booksAdmin</h2>
                <span class="label label-success"><a href="/books?action=createPage">addBook</a></span>
            </caption>
            <thead>
            <tr class="success">
                <th>bookID</th>
                <th>ISBN</th>
                <th>Title</th>
                <th>AuthorName</th>
                <th>BookYear</th>
                <th>PublisherName</th>
                <th>BookType</th>
                <th>Book operation</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${books}" var="book">
                <tr>
                    <td>${book.bookID}</td>
                    <td>${book.isbn}</td>
                    <td>${book.title}</td>
                    <td>${book.author.authorName}</td>
                    <td>${book.bookYear}</td>
                    <td>${book.publisher.publisherName}</td>
                    <td>${book.bookType}</td>
                    <td>
                        <span class="label label-info"><a href="/books?action=get&bookId=${book.bookID}">viewBook</a></span>
                        <span class="label label-success"><a href="/books?action=updatePage&bookId=${book.bookID}">editBook</a></span>
                        <span class="label label-danger"><a href="/books?action=delete&bookId=${book.bookID}">deleteBook</a></span>
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
