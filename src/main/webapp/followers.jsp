<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.Pic"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <div class="container">
            <h1 class="text-center text-success">Users I am following</h1><br/>
            <ul class="row">
                <%  java.util.LinkedList<String> userList = (java.util.LinkedList<String>) request.getAttribute("followingList");
                    java.util.LinkedList<LinkedList> pfPics = (java.util.LinkedList<LinkedList>) request.getAttribute("ProfilePicsList");
                    if (userList.isEmpty()) {%>
                <p>Not following anyone yet. </p>
                <%} else {
                    Iterator<String> iterator;
                    iterator = userList.iterator();
                    Iterator<LinkedList> literator;
                    literator = pfPics.iterator();
                    while (iterator.hasNext()) {
                        String userl = (String) iterator.next();
                        LinkedList list = (LinkedList) literator.next();
                        if (list == null) {%>
                <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure> <a href="${pageContext.request.contextPath}/Images/<%=userl%>" > <img style="max-width: 80px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif"></a>
                            <%} else {
                                Iterator<Pic> iterator1;
                                iterator1 = list.iterator();
                                while (iterator1.hasNext()) {
                                    Pic p = (Pic) iterator1.next();%>
                        <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure><a href="${pageContext.request.contextPath}/Images/<%=userl%>" ><img style="max-width: 80px" src="${pageContext.request.contextPath}/PThumb/<%=p.getSUUID()%>"></a>
                                    <%}
                                        }%>
                                <figcaption><a href="${pageContext.request.contextPath}/Images/<%=userl%>"><%=userl%></a></figcaption></figure></li>
                                    <%}
                                        }%>
            </ul>
            <h1 class="text-center text-success">Users that are following you.</h1><br/>
            <ul class="row">
                <%  java.util.LinkedList<String> followers = (java.util.LinkedList<String>) request.getAttribute("followersList");
                    java.util.LinkedList<LinkedList> followersPf = (java.util.LinkedList<LinkedList>) request.getAttribute("followersProfilePics");
                    if (followers.isEmpty()) {%>
                <p>No one is following you yet. </p>
                <%} else {
                    Iterator<String> iterator;
                    iterator = followers.iterator();
                    Iterator<LinkedList> literator;
                    literator = followersPf.iterator();
                    while (iterator.hasNext()) {
                        String userl = (String) iterator.next();
                        LinkedList list = (LinkedList) literator.next();
                        if (list == null) {%>
                <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure> <a href="${pageContext.request.contextPath}/Images/<%=userl%>" > <img style="max-width: 80px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif"></a>
                            <%} else {
                                Iterator<Pic> iterator1;
                                iterator1 = list.iterator();
                                while (iterator1.hasNext()) {
                                    Pic p = (Pic) iterator1.next();%>
                        <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure><a href="${pageContext.request.contextPath}/Images/<%=userl%>" ><img style="max-width: 80px" src="${pageContext.request.contextPath}/PThumb/<%=p.getSUUID()%>"></a>
                                    <%}
                                        }%>
                                <figcaption><a href="${pageContext.request.contextPath}/Images/<%=userl%>"><%=userl%></a></figcaption></figure></li>
                                    <%}
                                        }%>
            </ul>  
        </div>
    </body>
</html>
