<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css" />
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-2.1.4.min"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>InstaGrin</title>
    </head>
    <body> 
        <input type="hidden" id="refreshed" value="no">
        <script type="text/javascript">
            onload = function () {
                var e = document.getElementById("refreshed");
                if (e.value === "no")
                    e.value = "yes";
                else {
                    e.value = "no";
                    location.reload();
                }
            }</script>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="Index">InstaGrin</a>
                </div>
                <% String username = (String) session.getAttribute("Username");
                    if (username != null) {%>

                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right"  method="POST" action="Logout">
                        <div class="form-group">
                            <a class="navbar-brand"><%=username%></a>
                        </div>
                        <input class="btn btn-warning" type="submit" value="Logout">

                    </form>
                </div><!--/.navbar-collapse -->



                <% } else { %>
                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right" method="POST" action="Login">
                        <div class="form-group">
                            <input type="text" placeholder="Username" name="username" class="form-control" required autofocus >
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" name="password" class="form-control" required autofocus>
                        </div>
                        <input class="btn btn-success" type="submit" value="Sign In">
                        <a href="Register" class="btn btn-info">Register</a>
                    </form>
                </div><!--/.navbar-collapse -->
                <%}%>
            </div>
        </nav>
    </body>
</html>
