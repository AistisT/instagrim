
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <title>InstaGrin</title>
    </head>


    <body>
        <div class="row">
            <div class="col-md-4">
                <%
                    LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                    if (lg != null) {
                        String UserName = lg.getUsername();
                        if (lg.getlogedin()) {
                %>
                <a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a>

                <h3>File Upload</h3>
                <form method="POST" enctype="multipart/form-data" action="Image">
                    File to upload: <input type="file" name="upfile"><br/>

                    <br/>
                    <input type="submit" value="Press"> to upload the file!
                </form>
            </div>
            <div class="col-md-8">
                <h1>Your Pics</h1>
                <%
                    java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                    if (lsPics == null) {
                %>
                <p><%= UserName%> </p>
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
                            }
                        }%>
            </div>
        </div>
    </body>
</html>