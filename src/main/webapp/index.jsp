<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <body>
        <div class="container">
                <h1 class="text-center text-success">Newest Users</h1><br/>
                <ul class="row">
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
                             <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure> <a href="/Instagrin/Images/<%=userl%>" > <img style="max-width: 80px" src="http://paulskirbe.com/blog/wp-content/uploads/2012/12/empty_profile_picture_5.gif"></a>
                                <%} else {
                                    Iterator<Pic> iterator1;
                                    iterator1 = list.iterator();
                                    while (iterator1.hasNext()) {
                                        Pic p = (Pic) iterator1.next();%>
                                     <li class="col-lg-1 col-md-1 col-sm-2 col-xs-3">  <figure><a href="/Instagrin/Images/<%=userl%>" ><img style="max-width: 80px" src="/Instagrin/PThumb/<%=p.getSUUID()%>"></a>
                                        <%}
                                        }%>
                                    <figcaption><a href="/Instagrin/Images/<%=userl%>"><%=userl%></a></figcaption></figure></li>
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
                <li class="col-lg-3 col-md-3 col-sm-4 col-xs-5"> <a href="/Instagrin/Image/<%=p.getSUUID()%>"><img src="/Instagrin/Thumb/<%=p.getSUUID()%>"></a></li>
                        <%}
                            }%>
            </ul>
        </div>
    </body>
</html>
