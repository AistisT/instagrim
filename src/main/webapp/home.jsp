<%-- 
    Document   : home
    Created on : 23-Sep-2015, 23-Sep-2015 21:29:25
    Author     : Smikis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-2.1.4.min"></script>
        <title>InstaGrin</title>
    </head>
    <body>
               <ul>
            
                <%
                    LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                    if (lg != null) {
                        String UserName = lg.getUsername();
                        if (lg.getlogedin()) {
                %>
            <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
            <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile"><br/>

                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form>

        </article>
                <%}
                } else {
                %>

                <%
            }%>
        </ul> 
    </body>
</html>
