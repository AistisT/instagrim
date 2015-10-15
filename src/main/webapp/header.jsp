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
                <div class="navbar-left">
                    <a class="navbar-brand" href="Index">InstaGrin</a>
                </div>
                <% String username = (String) session.getAttribute("Username");
                    if (username != null) {%>
                <div class="navbar-right">                    
                    <a class="navbar-brand" href="Home">Home</a>
                    <a class="navbar-brand" href="Feed">Feed</a>
                    <a class="navbar-brand" href="Settings">Settings</a>
                    <a class="navbar-brand" href="">                        </a>
                    <a class="navbar-brand" style="color: greenyellow"><%=username%></a>
                <form class="navbar-form navbar-right"  method="POST" action="Logout">
                    <input class="btn btn-warning" type="submit" value="Logout">
                </form>
                </div>
                <% } else { %>
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
                <%}%>
            </div>
        </nav>
    </body>
</html>
