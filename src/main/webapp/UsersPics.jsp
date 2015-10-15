<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*"%>
<!DOCTYPE html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/bootstrap-theme.css" />
    <script src="../js/bootstrap.js"></script>
    <script src="../js/jquery-2.1.4.min"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>InstaGrin</title>
</head>
<body> 
    <input type="hidden" id="refreshed" value="no">
    <script type="text/javascript">
        onload = function () {
            var e = document.getElementById("refreshed");
            if (e.value == "no")
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
                <a class="navbar-brand" href="../Index">InstaGrin</a>
            </div>
            <% String username = (String) session.getAttribute("Username");
                if (username != null) {%>

            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right"  method="POST" action="../Logout">
                    <div class="form-group">
                        <a class="navbar-brand"><%=username%></a>
                    </div>
                    <input class="btn btn-warning" type="submit" value="Logout">

                </form>
            </div><!--/.navbar-collapse -->



            <% } else { %>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right" method="POST" action="../Login">
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



    <div class="row">
        <div class="col-md-3 text-center" style="padding-right: 0px">
            <%
                java.util.LinkedList<Pic> pfPics = (java.util.LinkedList<Pic>) request.getAttribute("ProfilePics");
                if (pfPics == null) {
            %>
            <p>No profile picture. </p>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = pfPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();
            %>
            <a href="/Instagrim/ProfilePic/<%=p.getSUUID()%>" ><img src="/Instagrim/PThumb/<%=p.getSUUID()%>"></a><br/><br/>    
                <%
                        }
                    } %>
                <%
                    if (username != null) {
                        boolean following = (boolean) session.getAttribute("Following");

                        if (following) {
                            session.setAttribute("Follow", false); %>
            <form method="POST" action="../Following">            
                <button class="btn btn-success" type="submit" name="userToFollow">
                    <span class="glyphicon glyphicon-ok" style="vertical-align:middle">Un-follow</span> 
                </button>
            </form>

            <% } else {
                session.setAttribute("Follow", true); %>                
            <form method="POST" action="../Following">
                <button class="btn btn-primary" type="submit" name="userToFollow">
                    <span class="glyphicon glyphicon-user" style="vertical-align:middle">Follow</span> 
                </button>
            </form>
            <%}
                }%> 


        </div>
        <div class="col-md-9" style="padding-left: 0px; margin-left: -15px">
            <div class="container" style="padding-left: 0px">
                <h1 class="text-center text-primary">User Images</h1><br/>
                <ul class="row">
                    <%                java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                        if (lsPics == null) {
                    %>
                    <p>No pictures uploaded yet. </p>
                    <%
                    } else {
                        Iterator<Pic> iterator;
                        iterator = lsPics.iterator();
                        while (iterator.hasNext()) {
                            Pic p = (Pic) iterator.next();
                    %>
                    <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5"> <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a><br/></li>
                            <%
                                    }
                                }

                            %>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
