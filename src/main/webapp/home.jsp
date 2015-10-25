<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <body>
        <h1 class="text-center text-success">Your Pictures</h1><br/>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 border1">
                    <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Image" >
                        <h3>Picture upload</h3>
                        <label>Picture to upload:</label> <input id="fileInput" type="file" accept=".jpg,.jpeg,.png" name="upfile"><br/>
                        <br/>
                        <input ID="upButton" type="submit"  onclick="<%session.setAttribute("origin", "home");%>" value="Press"> to upload the picture!
                        <% if (session.getAttribute("typeFail") != null) {
                                if ((Boolean) session.getAttribute("typeFail") == true) {%>
                        <h4 class="text-danger">Only images of a types: jpg, jpeg and png are allowed!</h4>
                        <%}
                            }%>
                    </form>
                </div>
                <div class="col-md-10">
                    <ul class="row">
                        <% java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                            if (lsPics == null) {%>
                        <p>No pictures uploaded yet.</p>
                        <%} else {
                            Iterator<Pic> iterator;
                            iterator = lsPics.iterator();
                            while (iterator.hasNext()) {
                                Pic p = (Pic) iterator.next();%>
                        <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5 text-center"> 
                            <figure> <a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>"> <img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a>
                                <figcaption> 
                                    <form method="POST" action="${pageContext.request.contextPath}/Delete"  class="btn-group btn-group-sm" role="group">
                                        <button type="button" disabled  class="btn btn-success">Likes</button>
                                        <button type="button" disabled="true" class="btn btn-default"><%=p.getLikes()%></button>
                                        <button type="button" disabled class="btn btn-primary">Dislikes</button>
                                        <button type="button" disabled="true" class="btn btn-default"><%=p.getDislikes()%></button>
                                        <input type="hidden" name="picId" value="<%=p.getSUUID()%>" >
                                        <button class="btn btn-warning " type="submit" onclick="javascript:return confirm('Are you sure you want to delete this picture?')">Delete</button>
                                    </form>
                                </figcaption></figure></li>
                                <% }
                                    }%>
                    </ul>           
                </div>
            </div>
        </div>
    </body>
</html>
