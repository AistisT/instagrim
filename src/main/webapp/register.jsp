<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 
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
                    <input type="text" name="username" class="form-control"  placeholder="User name" required autofocus <% if (request.getAttribute("username") != null) {%> value="<%=request.getAttribute("username")%>" <%}%>>
                </div>
            </div>
            <div class="form-group" name="passwordGroup">
                <label for="password" class="col-sm-4 control-label">Password  <b>*</b></label>
                <div class="col-sm-8">
                    <input type="password" name="password" class="form-control" placeholder="Password" required >
                </div>
            </div>
            <div class="form-group" name="emailGroup">
                <label for="email" class="col-sm-4 control-label">Email Address  </label>
                <div class="col-sm-8">
                    <input type="text" name="email" class="form-control" placeholder="aaa@dundee.ac.co.uk" <% if (request.getAttribute("email") != null) {%> value="<%=request.getAttribute("email")%>" <%}%>>
                </div>
            </div>
            <div class="form-group" name="firstNameGroup">
                <label for="firstName" class="col-sm-4 control-label">First Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="firstName" class="form-control" placeholder="First Name" <% if (request.getAttribute("firstName") != null) {%> value="<%=request.getAttribute("firstName")%>" <%}%>>
                </div>
            </div>
            <div class="form-group" name="lastNameGroup">
                <label for="lastName" class="col-sm-4 control-label">Last Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="lastName" class="form-control" placeholder="Last Name" <% if (request.getAttribute("lastName") != null) {%> value="<%=request.getAttribute("lastName")%>" <%}%>>
                </div>
            </div>
            <div class="form-group" name="submitGroup">
                <label for="submit" class="col-sm-4 control-label"><h6><b>* Required Fields</b></h6>  </label>
                <div class="col-sm-8"> 
                    <button class="btn btn-success btn-block" type="submit">Register</button>
                </div>
            </div>
            <div class="col-sm-12">
                <% if (request.getAttribute("exists") != null) { %>
                <h4 class="text-danger">Username already exists, please choose another username.</h4>
                <%}%>
            </div>
        </form>
    </div>
</body>
</html>
