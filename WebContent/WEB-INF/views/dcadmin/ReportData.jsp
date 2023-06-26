<%@ page import="java.util.List,spring.orm.model.output.OutputReportData,java.lang.*" %>
<!DOCTYPE html>
<html>
<head>
<title>Patients</title>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.18/dist/sweetalert2.all.min.js"></script>
<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
        text-align: center;
        padding: 15px;
    }

    th {
        background-color: #f2f2f2;
    }
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body id="con">
    <%
        List<OutputReportData> data = (List<OutputReportData>) request.getAttribute("ord");
    %>
    <table border="1" id="patientTable">
        <tr>
            <th>Patient ID</th>
            <th>DGBL ID</th>
            <th>Patient Name</th>
            <th>Patient Gender</th>
            <th>Amount</th>
            <th align="center">Action</th>
        </tr>
        <%
            for (int i = 0; i < data.size(); i++) {
        %>
        <tr>
            <td><%=data.get(i).getPatn_id()%></td>
            <td><%=data.get(i).getDgbl_id()%></td>
            <td><%=data.get(i).getPatn_name()%></td>
            <td><%=data.get(i).getPatn_gender()%></td>
            <td><%=data.get(i).getDgbl_amount()%></td>
            <td>
                <form class="uploadForm" enctype="multipart/form-data">
                    <input type="hidden" name="dgblId" value="<%=data.get(i).getDgbl_id()%>">
                    <input type="file" name="file" accept="image/*">
                    <input type="submit" value="Upload">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <script>
    $(document).ready(function() {
        // Attach event handler to the parent element and capture events from dynamically added forms
        $('#patientTable').on('submit', '.uploadForm', function(event) {
            event.preventDefault(); // Prevent form submission

            var form = $(this);
            var url = form.attr('action');
            var formData = new FormData(form[0]);

            $.ajax({
                type: 'POST',
                url: "./uploadfile",
                data: formData,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function(response) {
                    console.log(response);
                    
                    // Show success pop-up
                    Swal.fire({
                        icon: 'success',
                        title: 'File Uploaded',
                        text: 'The file has been successfully uploaded.',
                        timer: 3000, // Display the pop-up for 3 seconds
                        showConfirmButton: false
                    });
                    
                    setTimeout(function() {
                        window.opener.location.reload(); // Reload the parent page
                       window.close();
                    }, 3000);

                },
                error: function(xhr, status, error) {
                    console.log(error);
                    $('#uploadMessage').text('An error occurred during the file upload.');
                }
            });
        });
    });

 </script>
 
 
</body>
</html>