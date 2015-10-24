<%@page import="java.util.Iterator"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.Pic"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="container-fluid">
    <div class="row">
        <div class="col-md-2 border1"><br>
            <%  java.util.LinkedList<Pic> pfPics = (java.util.LinkedList<Pic>) request.getAttribute("ProfilePics");
                if (pfPics == null) {%>
            <img style="max-width: 150px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif">
            <%} else {
                Iterator<Pic> iterator;
                iterator = pfPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();%>
            <a href="${pageContext.request.contextPath}/ProfilePic/<%=p.getSUUID()%>" ><img src="${pageContext.request.contextPath}/PThumb/<%=p.getSUUID()%>"></a><br/>
                <%}
                    } %>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Image">
                File to upload: <input type="file" name="upfile"><br/>
                <br/>
                <input type="submit" value="Press" onclick="<%session.setAttribute("origin", "settings");%>"> to upload the file!
            </form>
                <% if (session.getAttribute("typeFail") != null) {
                    if ((Boolean) session.getAttribute("typeFail") == true) {%>
                <h4 class="text-danger">Only images of a types: jpg, jpeg and png are allowed!</h4>
                <%}
                }%>
        </div>
        <form class="col-md-4 col-md-offset-2 " method="POST"  action="Settings">
            <div class="form-group">
                <div class="col-sm-4"></div>
                <div class="col-sm-8">
                    <h1 class="form-signin-heading">Update account info</h1><br>
                </div>
            </div>
            <div class="form-group" name="emailGroup">
                <label for="email" class="col-sm-4 control-label">Email Address  </label>
                <div class="col-sm-8">
                    <input type="text" name="email" class="form-control" value=<%=(String) request.getAttribute("email")%>>
                </div>
            </div>
            <div class="form-group" name="firstNameGroup">
                <label for="firstName" class="col-sm-4 control-label">First Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="firstName" class="form-control" value=<%=(String) request.getAttribute("firstName")%>>
                </div>
            </div>
            <div class="form-group" name="lastNameGroup">
                <label for="lastName" class="col-sm-4 control-label">Last Name  </label>
                <div class="col-sm-8"> 
                    <input type="text" name="lastName" class="form-control" value=<%=(String) request.getAttribute("lastName")%> >
                </div>
            </div>
            <div class="form-group" name="submitGroup">
                <label for="submit" class="col-sm-4 control-label"><h6><b></b></h6>  </label>
                <div class="col-sm-8"> 
                    <button class="btn btn-success btn-block" type="submit">Update</button>
                </div>
            </div>
        </form>
    </div>
    </div> 
</html>
