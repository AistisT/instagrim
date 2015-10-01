<%-- 
    Document   : header
    Created on : 01-Oct-2015, 01-Oct-2015 10:50:35
    Author     : Aistis Taraskevicius
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css" />
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
                    <form class="navbar-form navbar-right" method="POST" action="Login">
                        <div class="form-group">
                            <input type="text" placeholder="Username" name="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" name="password" class="form-control">
                        </div>
                        <input class="btn btn-success" type="submit" value="Sign In"></a>
                        <a href="register.jsp" class="btn btn-info">Register</a>
                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </nav>
    </body>
</html>
