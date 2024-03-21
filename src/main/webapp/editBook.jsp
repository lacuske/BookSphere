<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="include/admin/adminHeader.jsp" %>
<%@include file="include/admin/adminNavigator.jsp" %>

<title>edit book info</title>


<div class="workingArea">

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">edit book info</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="/books?action=update">
                <table class="editTable">

                    <tr>

                        <td>book title</td>
                        <td><input id="Title" name="title" value="${book.title}" 
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book ISBN</td>
                        <td><input id="isbn" name="isbn" value="${book.isbn}" 
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book bookYear</td>
                        <td><input id="bookYear" name="bookYear" value="${book.bookYear}"
                                   type="text"  class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book bookType</td>
                        <td><input id="bookType" name="bookType" value="${book.bookType}"
                                   type="text"  class="form-control"></td>
                    </tr>
                    <tr>
                        <td>book authorName</td>
                        <td><input id="authorName" value="${book.author.authorName}" name="authorName" type="text" 
                                   class="form-control"></td>
                    </tr>
                    <tr>

                        <td>book publisherName</td>
                        <td><input id="publisherName" value="${book.publisher.publisherName}" name="publisherName" type="text" 
                                   class="form-control"></td>
                    </tr>

                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="bookId" value="${book.bookID}">
                            <button type="submit" class="btn btn-success">subumit</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>