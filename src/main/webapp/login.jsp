<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css" />
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-2.1.4.min"></script>

    </head>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">InstaGrin</a>
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

    <div class="container" >

        <div class="container">
            <form class="col-sm-6 col-sm-offset-3"  method="POST"  action="Login">
                <div class="form-group">
                    <div class="col-sm-4"></div>
                    <div class="col-sm-6">
                         <h1 class="form-signin-heading">Please sign in</h1><br>
                    </div>
                </div>
                <div class="form-group" id="usernameGroup">
                    <label for="username" class="col-sm-4 control-label">Username  </label>
                    <div class="col-sm-8">
                        <input type="text" name="username" class="form-control" placeholder="User name" required autofocus>
                    </div>
                </div>
                <div class="form-group" id="passwordGroup">
                    <label for="password" class="col-sm-4 control-label">Password  </label>
                    <div class="col-sm-8">
                        <input type="password" name="password" class="form-control" placeholder="Password" required autofocus>
                    </div>

                    <div class="checkbox col-sm-4">
                        <label>
                            <input type="checkbox" value="remember-me"> Remember me
                        </label>
                    </div>
                    <div class="col-sm-8">
                        <button class="btn btn-success btn-block" type="submit">Sign in</button>
                    </div>
                </div>
            </form>

        </div> <!-- /container -->

        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
    </body>
</html>
</html>
