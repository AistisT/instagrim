<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <body>
        <h1 class="text-center text-success">Your Pictures</h1><br/>
        <div class="row">
            <div class="col-md-2 border1">
                <form method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Image" >
                    <h3>Picture upload</h3>
                    Picture to upload: <input id="fileInput" type="file" accept=".jpg,.jpeg,.png" name="upfile"><br/>
                    <br/>
                    <input ID="upButton" type="submit"  onclick="<%session.setAttribute("origin", "home");%>" value="Press"> to upload the picture!
                    <% if (session.getAttribute("typeFail") != null) {
                            if ((Boolean) session.getAttribute("typeFail") == true) {%>
                    <h4 class="text-danger">Only images of a types: jpg, jpeg and png are allowed!</h4>
                    <%}
                        }%>
                </form>
            </div>
            <div class="col-md-10" style="margin-left: -100px">
                <div class="container">
                    <ul class="row">
                        <% java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                            if (lsPics == null) {%>
                        <p>No pictures uploaded yet.</p>
                        <%} else {
                            Iterator<Pic> iterator;
                            iterator = lsPics.iterator();
                            while (iterator.hasNext()) {
                                Pic p = (Pic) iterator.next();%>
                        <form method="POST" action="${pageContext.request.contextPath}/Delete"> 
                            <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5"> 
                                <figure> <a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>"> <img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a>
                                    <figcaption> <input type="hidden" name="picId" value="<%=p.getSUUID()%>" >
                                        <Button class="btn icon-btn btn-warning" type="submit" onclick="javascript:return confirm('Are you sure you want to delete this picture?')">Delete</Button>
                                    </figcaption></figure></li></form>
                                    <% }
                                    }%>
                    </ul>           
                </div>
            </div>
        </div>
    </body>
</html>
