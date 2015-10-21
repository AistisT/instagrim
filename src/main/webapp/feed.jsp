<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <body>
        <div class="container">
            <ul class="row">
                <h1 class="text-center text-success">Pictures from your friends</h1><br/>
                <%java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                    if (lsPics == null) {%>
                <p>No pictures uploaded yet.</p>
                <%} else {
                    Iterator<Pic> iterator;
                    iterator = lsPics.iterator();
                    while (iterator.hasNext()) {
                        Pic p = (Pic) iterator.next();%>
                <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5">  <a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>" ><img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a></li>     
                        <%}
                            }%>
            </ul>
        </div>
    </div>
</body>
</html>