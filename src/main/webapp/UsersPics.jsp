<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*"%>
<!DOCTYPE html>
<body> 
    <h1 class="text-center text-primary"><%=request.getAttribute("user")%> Images</h1><br/>
    <div class="row">
        <div class="col-md-3 text-center" style="padding-right: 0px">
            <%java.util.LinkedList<Pic> pfPics = (java.util.LinkedList<Pic>) request.getAttribute("ProfilePics");
                if (pfPics == null) {%>
            <img style="max-width: 150px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif"><br><br>    
            <% } else {
                Iterator<Pic> iterator;
                iterator = pfPics.iterator();
                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();%>
            <a href="${pageContext.request.contextPath}/ProfilePic/<%=p.getSUUID()%>" ><img src="${pageContext.request.contextPath}/PThumb/<%=p.getSUUID()%>"></a><br><br>    
                <%}
                    }%>
            <div class="form-group" name="emailGroup">
                <div class="row">
                    <label  name="email" class="form-control-label"><%=(String) request.getAttribute("email")%></label>
                </div>
            </div>
            <div class="form-group" name="firstNameGroup">
                <div class="row"> 
                    <label  name="email" class="form-control-label"><%=(String) request.getAttribute("firstName")%> <%=(String) request.getAttribute("lastName")%></label>
                </div>
            </div>
            <%if ((String) session.getAttribute("Username") != null) {
                    if (!((String) session.getAttribute("Username")).equalsIgnoreCase((String) request.getAttribute("user"))) {
                        boolean following = (boolean) session.getAttribute("Following");
                        if (following) {
                            session.setAttribute("Follow", false); %>
            <form method="POST" action="${pageContext.request.contextPath}/Following">            
                <button class="btn btn-success" type="submit" name="userToFollow">Un-follow</button>
            </form>
            <% } else {
                session.setAttribute("Follow", true); %>                
            <form method="POST" action="${pageContext.request.contextPath}/Following">
                <button class="btn btn-primary" type="submit" name="userToFollow">Follow</button>
            </form>
            <%}
                    }
                }%> 
        </div>
        <div class="col-md-9" style="padding-left: 0px; margin-left: -15px">
            <div class="container" style="padding-left: 0px">
                <ul class="row">
                    <% java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                        if (lsPics == null) {%>
                    <p>No pictures uploaded yet.</p>
                    <%} else {
                        Iterator<Pic> iterator;
                        iterator = lsPics.iterator();
                        while (iterator.hasNext()) {
                            Pic p = (Pic) iterator.next();%>
                            <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5">  <figure><a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>" ><img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a><br/><br>
                            <figcaption> 
                                
                                <form class="btn-group btn-group-sm" role="group" aria-label="Like" method="POST" action="${pageContext.request.contextPath}/Like">
                                    <button type="submit" <% if (p.getLiked()||((String) session.getAttribute("Username") == null)){%> 
                                            disabled <%}%> class="btn btn-success">Like</button>
                                    <input type="hidden" name="userName" value="<%=(String)request.getAttribute("user")%>">
                                    <input type="hidden" name="picid" value="<%=p.getSUUID()%>">
                                    <input type="hidden" name="outcome" value="like">
                                    <button type="button" disabled="true" class="btn btn-default"><%=p.getLikes()%></button>
                                </form>
                                <form class="btn-group btn-group-sm" role="group" aria-label="Dislike"method="POST" action="${pageContext.request.contextPath}/Like">
                                    <button type="submit" <% if (p.getDisliked()){%> disabled <%}%>class="btn btn-primary">Dislike</button>
                                    <input type="hidden" name="userName" value="<%=(String)request.getAttribute("user")%>">
                                    <input type="hidden" name="picid" value="<%=p.getSUUID()%>">
                                    <input type="hidden" name="outcome" value="dislike">
                                    <button type="button" disabled="true" class="btn btn-default"><%=p.getDislikes()%></button>
                                </form>
                            </figcaption></figure></li>
                            <%}
                                }%>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
