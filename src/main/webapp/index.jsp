<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <div class="container-fluid text-center">
            <h1 class="text-center text-success">Newest Users</h1><br/>
            <ul class="row center-block list-inline text-center ">
                <%  java.util.LinkedList<String> userList = (java.util.LinkedList<String>) request.getAttribute("userList");
                    java.util.LinkedList<LinkedList> pfPics = (java.util.LinkedList<LinkedList>) request.getAttribute("ProfilePicsList");
                    if (userList == null) {%>
                <p>No users registered. </p>
                <%} else {
                    Iterator<String> iterator;
                    iterator = userList.iterator();
                    Iterator<LinkedList> literator;
                    literator = pfPics.iterator();
                    while (iterator.hasNext()) {
                        String userl = (String) iterator.next();
                        LinkedList list = (LinkedList) literator.next();
                        if (list == null) {%>
                <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3 center-block">  <figure> <a href="${pageContext.request.contextPath}/Images/<%=userl%>" > <img style="max-width: 80px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif"></a>
                            <%} else {
                                Iterator<Pic> iterator1;
                                iterator1 = list.iterator();
                                while (iterator1.hasNext()) {
                                    Pic p = (Pic) iterator1.next();%>
                        <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3 center-block">  <figure><a href="${pageContext.request.contextPath}/Images/<%=userl%>" ><img style="max-width: 80px" src="${pageContext.request.contextPath}/PThumb/<%=p.getSUUID()%>"></a>
                                    <%}
                                            }%>
                                <figcaption><a href="${pageContext.request.contextPath}/Images/<%=userl%>"><%=userl%></a></figcaption></figure></li>
                                    <%}
                                            }%>
            </ul>           
            <h1 class="text-center text-primary">Latest Images</h1><br/>
            <ul class="row">
                <%java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                    if (lsPics == null) {%>
                <p>No pictures uploaded yet. </p>
                <%} else {
                    Iterator<Pic> iterator;
                    iterator = lsPics.iterator();
                    while (iterator.hasNext()) {
                        Pic p = (Pic) iterator.next();%>
                <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5"> <figure><a href="${pageContext.request.contextPath}/Image/<%=p.getSUUID()%>"><img src="${pageContext.request.contextPath}/Thumb/<%=p.getSUUID()%>"></a>
                        <figcaption> <label>Uploaded by <a href="${pageContext.request.contextPath}/Images/<%=p.getOwner()%>"><%=p.getOwner()%></a> at <%=p.getDateFormated()%></label></figcaption></figure></li>
                            <%}
                            }%>
            </ul>
        </div>
    </body>
</html>
