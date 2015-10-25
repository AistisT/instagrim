<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="container">
        <form class="col-sm-6 col-sm-offset-3"  method="POST"  action="${pageContext.request.contextPath}/Login">
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
            <div class="col-sm-12 text-center">
                <% if (request.getAttribute("fail") != null) { %>
                <h4 class="text-danger">Wrong username or password, please try again.</h4>
                <%}%>
            </div>
        </form>
    </div> 
</body>
</html>