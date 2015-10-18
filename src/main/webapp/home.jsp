
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <body>
        <h1 class="text-center text-success">Your Pictures</h1><br/>

        <div class="row">
            <div class="col-md-2 border1">
                <form id="uploadFile" method="POST" enctype="multipart/form-data" action="${pageContext.request.contextPath}/Image" >
                    <h3>Picture upload</h3>
                    Picture to upload: <input id="fileInput" type="file" accept=".jpg,.jpeg,.png" name="upfile"><br/>
                    <br/>
                    <input ID="upButton" type="submit"  onclick="<%session.setAttribute("origin", "home");%>" value="Press"> to upload the picture!
                    <div class="col-sm-12 text-center">
                        <% if (request.getAttribute("extensionFail") != null) { %>
                        <h4 class="text-danger">Only images (.jpg, .jpeg, .png) are allowed!</h4>
                        <%}%>
                    </div>
                </form>
            </div>
            <div class="col-md-10" style="margin-left: -100px">
                <div class="container">
                    <ul class="row">
                        <% java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                            if (lsPics == null) {%>
                        <p>No pictures uploaded yet. </p>
                        <%} else {
                            Iterator<Pic> iterator;
                            iterator = lsPics.iterator();
                            while (iterator.hasNext()) {
                                Pic p = (Pic) iterator.next();%>
                        <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5">  <a href="/Instagrin/Image/<%=p.getSUUID()%>" ><img src="/Instagrin/Thumb/<%=p.getSUUID()%>"></a><br/></li>
                                <%}
                                    }%>
                    </ul>           
                </div>
            </div>
        </div>

    </body>


</html>
