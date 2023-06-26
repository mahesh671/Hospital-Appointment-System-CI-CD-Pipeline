<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="spring.orm.model.output.*,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hospital Doctor Profit Information</title>
    <style>
        .container {
            display: flex;
            justify-content: center;
        }

        table {
            border-collapse: collapse;
            width: 30%;
            margin: 10px;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }
        
        #t1 {
         margin-left: 5%
        }
    </style>
</head>
<body>

<%

List<OutputDoctorProfit> d1 = (List<OutputDoctorProfit>)request.getAttribute("tndata");
List<OutputSpecializationProfit> d2 = (List<OutputSpecializationProfit>)request.getAttribute("tmdata");

%>
    

    <div class="container">
        <table id="t1">
            
            <tr>
                <th>Doctor Name</th>
                <th>Profit</th>
            </tr>
            <%for(int i=0;i<d1.size();i++){ %>
            <tr>
             <td><%=d1.get(i).getDoctorName() %></td>
            <td><%=d1.get(i).getTotalProfit() %></td>
            </tr>
            <%} %>
       </table>

       

        <table>
           
            <tr>
                <th>Specialization</th>
                <th>Profit</th>
            </tr>
            <%for(int i=0;i<d2.size();i++){ %>
            <tr>
            <td><%=d2.get(i).getSpecializationTitle()%></td>
            <td><%=d2.get(i).getTotalProfit()%></td>
            </tr>
            <%} %>
       </table>
    </div>
</body>
</html>
