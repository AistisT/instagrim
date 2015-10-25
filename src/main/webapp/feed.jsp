<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <body>
        <div class="container-fluid">
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
                <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5 text-center">  <a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>" ><img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a>
                    <label>Uploaded by <a href="${pageContext.request.contextPath}/Images/<%=p.getOwner()%>"><%=p.getOwner()%></a> at <%=p.getDateFormated()%></label>
                <figcaption> 
                    <form class="btn-group btn-group-sm" role="group" aria-label="Like" method="POST" action="${pageContext.request.contextPath}/Like">
                        <button type="submit" <% if (p.getLiked()) {%>disabled <%}%> class="btn btn-success">Like</button>
                        <input type="hidden" name="userName" value="<%=p.getOwner()%>">
                        <input type="hidden" name="picid" value="<%=p.getSUUID()%>">
                        <input type="hidden" name="outcome" value="like">
                        <input type="hidden" name="source" value="feed">
                        <button type="button" disabled="true" class="btn btn-default"><%=p.getLikes()%></button>
                    </form>
                    <form class="btn-group btn-group-sm" role="group" aria-label="Dislike"method="POST" action="${pageContext.request.contextPath}/Like">
                        <button type="submit" <% if (p.getDisliked()) {%> disabled <%}%>class="btn btn-primary">Dislike</button>
                        <input type="hidden" name="userName" value="<%= p.getOwner()%>">
                        <input type="hidden" name="picid" value="<%=p.getSUUID()%>">
                        <input type="hidden" name="outcome" value="dislike">
                        <input type="hidden" name="source" value="feed">
                        <button type="button" disabled="true" class="btn btn-default"><%=p.getDislikes()%></button>
                    </form>
                </figcaption></figure></li>
                <%}
                    }%>
            </ul>
        </div>
    </div>
</body>
</html>