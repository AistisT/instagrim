<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>InstaGrin</title>
        <!--<link rel="stylesheet" type="text/css" href="css/bootstrap.css.css" />-->
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.min.css" />
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-2.1.4.min"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">InstaGrin</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right">
                        <div class="form-group">
                            <input type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control">
                        </div>
                        <a href="login.jsp" class="btn btn-success" href="login.jsp">Sign in</a>
                        <a href="register.jsp" class="btn btn-info" href="login.jsp">Register</a>
                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </nav>

        <!--  <ul>
              <li><a href="upload.jsp">Upload</a></li>
        <%
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            if (lg != null) {
                String UserName = lg.getUsername();
                if (lg.getlogedin()) {
        %>

    <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
        <%}
        } else {
        %>
    <li><a href="register.jsp">Register</a></li>
    <li><a href="login.jsp">Login</a></li>
        <%
                        }%>
</ul> -->

        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <li>&COPY; Aistis Taraskevicius</li>
            </ul>
        </footer>
    </body>
</html>
