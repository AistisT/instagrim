<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
    <head>
        <title>Instagrin</title>
    </head>
    <div class="container">
        <form class="col-sm-6 col-sm-offset-3"  method="POST"  action="Register">
            <div class="form-group">
                <div class="col-sm-4"></div>
                <div class="col-sm-6">
                    <h1 class="form-signin-heading">Register</h1><br>
                </div>
            </div>
            <div class="form-group" name="usernameGroup">
                <label for="username" class="col-sm-4 control-label">Username  <b>*</b></label>
                <div class="col-sm-8">
                    <input type="text" name="username" class="form-control" placeholder="User name" required autofocus>
                </div>
            </div>
            <div class="form-group" name="passwordGroup">
                <label for="password" class="col-sm-4 control-label">Password  <b>*</b></label>
                <div class="col-sm-8">
                    <input type="password" name="password" class="form-control" placeholder="Password" required autofocus>
                </div>
            </div>
            <div class="form-group" name="emailGroup">
                <label for="email" class="col-sm-4 control-label">Email Address  </label>
                <div class="col-sm-8">
                    <input type="text" name="email" class="form-control" placeholder="aaa@dundee.ac.co.uk">
                </div>
            </div>
            <div class="form-group" name="firstNameGroup">
                <label for="firstName" class="col-sm-4 control-label">First Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="firstName" class="form-control" placeholder="First Name">
                </div>
            </div>
            <div class="form-group" name="lastNameGroup">
                <label for="lastName" class="col-sm-4 control-label">Last Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="lastName" class="form-control" placeholder="Last Name">
                </div>
            </div>
            <div class="form-group" name="streetGroup">
                <label for="street" class="col-sm-4 control-label">Address</label>
                <div class="col-sm-8"> 
                    <input type="text" name="street" class="form-control" placeholder="10 Dundee Street">
                </div>
            </div>
            <div class="form-group" name="cityGroup">
                <label for="city" class="col-sm-4 control-label">City  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="city" class="form-control" placeholder="Dundee">
                </div>
            </div>
            <div class="form-group" name="lastNameGroup">
                <label for="postcode" class="col-sm-4 control-label">Postcode  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="postcode" class="form-control" placeholder="DD1 1BB">
                </div>
            </div>
            <div class="form-group" name="submitGroup">
                <label for="submit" class="col-sm-4 control-label"><h6><b>* Required Fields</b></h6>  </label>
                <div class="col-sm-8"> 
                    <button class="btn btn-success btn-block" type="submit">Register</button>
                </div>
            </div>
        </form>

    </div> <!-- /container -->

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
