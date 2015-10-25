<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true"%>
<!DOCTYPE html>
<html>
    <body>
        <div class="container-fluid text-center">
            <div class="col-md-6 col-md-offset-3">
                <h1 class="text-center text-success" >Oops, this page doesn't exist!
                    <br> <a href="${pageContext.request.contextPath}/">Go back to InstaGrin</a>
                </h1> 
                <img class="img-responsive" src="http://musiceon.com/wp-content/uploads/2014/11/vixx-error-logo.png">
            </div>
        </div>

    </body>
</html>