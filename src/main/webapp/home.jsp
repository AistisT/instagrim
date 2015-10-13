
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>

    <body>
        <div class="row">
            <div class="col-md-4">
                <h3>File Upload</h3>
                <form id="uploadFile" method="POST" enctype="multipart/form-data" action="Image" >
                    File to upload: <input id="fileInput" type="file" accept=".jpg,.jpeg,.png" name="upfile"><br/>
                    <br/>
                    <input id="upButton" type="submit"  onclick="<%session.setAttribute("origin", "home");%>" value="Press"> to upload the file!
                </form>
            </div>
            <div class="col-md-8">
                <h1>Your Pics</h1>
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
    </body>

    <script>
        $("#uploadFile").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) === -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
            }
        });
    </script>

</html>
