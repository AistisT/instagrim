<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
     <div class="container">
         
         <div class="col-md-4">
                <h3>File Upload</h3>
                <form method="POST" enctype="multipart/form-data" action="Image">
                    File to upload: <input type="file" name="upfile"><br/>

                    <br/>
                    <input type="submit" value="Press"> to upload the file!
                </form>
            </div>
        <form class="col-sm-8 "  method="POST"  action="Settings">
            <div class="form-group">
                <div class="col-sm-4"></div>
                <div class="col-sm-6">
                    <h1 class="form-signin-heading">Update account info</h1><br>
                </div>
            </div>
            <div class="form-group" name="passwordGroup">
                <label for="password" class="col-sm-4 control-label">Password</label>
                <div class="col-sm-8">
                    <input type="password" name="password" class="form-control" placeholder="Password" autofocus>
                </div>
            </div>
            <div class="form-group" name="emailGroup">
                <label for="email" class="col-sm-4 control-label">Email Address  </label>
                <div class="col-sm-8">
                    <input type="text" name="email" class="form-control" value=<%=(String)session.getAttribute("email")%>>
                </div>
            </div>
            <div class="form-group" name="firstNameGroup">
                <label for="firstName" class="col-sm-4 control-label">First Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="firstName" class="form-control" value=<%=(String)session.getAttribute("firstName")%>>
                </div>
            </div>
            <div class="form-group" name="lastNameGroup">
                <label for="lastName" class="col-sm-4 control-label">Last Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="lastName" class="form-control" value=<%=(String)session.getAttribute("lastName")%>     >
                </div>
            </div>
            <div class="form-group" name="submitGroup">
                <label for="submit" class="col-sm-4 control-label"><h6><b></b></h6>  </label>
                <div class="col-sm-8"> 
                    <button class="btn btn-success btn-block" type="submit">Update</button>
                </div>
            </div>
        </form>
    </div> <!-- /container -->
    
</html>
