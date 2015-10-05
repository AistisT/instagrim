<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <body>
        <div class="row">
            <div class="col-md-12">


                <%
                    java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                    if (lsPics == null) {
                %>
                <p>No pictures uploaded yet. </p>
                <%
                } else {
                    Iterator<Pic> iterator;
                    iterator = lsPics.iterator();
                    while (iterator.hasNext()) {
                        Pic p = (Pic) iterator.next();
                %>
                <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a><br/>
                    <%
                            }
                        }

                    %>


            </div>
        </div>
        <div class="row">
            <div class="col-md-12">


                <% 
                    java.util.LinkedList<String> userList = (java.util.LinkedList<String>) request.getAttribute("userList");
                    if (userList == null) {
                %>
                <p>No users registered. </p>
                <%
                } else {
                    Iterator<String> iterator;
                    iterator = userList.iterator();
                    while (iterator.hasNext()) {
                        String userl = (String) iterator.next();
                %>
                <a href="/Instagrim/Images/<%=userl%>"><%=userl%></a>
                <%
                        }
                    }

                %>

            </div>
        </div>
    </body>
</html>
