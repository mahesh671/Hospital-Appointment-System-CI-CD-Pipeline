<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<jsp:include page="scripts.jsp"/>
 <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <script>
	$(document)
			.ready(
					function() {
						// Get the hyperlinks elements
						var displayLinks = $('.displayLink');

						// Add click event listener to each hyperlink
						displayLinks
								.on(
										'click',
										function(e) {
											e.preventDefault();

											// Get the image URL from the clicked hyperlink's data attribute
											var imageUrl = $(this).data(
													'image-url');

											// Create the popup window
											var popupWindow = window.open('',
													'Image Popup',
													'width=500,height=500');

											// Write the HTML content with Bootstrap classes to the popup window
											popupWindow.document
													.write('<html><head><title>Image Popup</title><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"></head><body><div class="container"><div class="row"><div class="col-md-6"><img src="' + imageUrl + '" class="img-responsive"/><br/><button onclick="window.print()" class="btn btn-primary">Print</button> <button onclick="window.close()" class="btn btn-primary">Close</button></div></div></div></body></html>');
										});
					});
</script>
 
</head>
<body>
    <jsp:include page="nav.jsp" />
    <div align="center">
		
		<div
			class=" custom-scroll overflow-auto shadow p-3 m-3  bg-white rounded"
			style="max-height: 700px;">
			<table class="table  table-bordered table-shadow mt-4 ">
				<thead>
					<tr>
						<th>ID</th>
						<th>Date</th>
						<th>Parameter </th>
						<th>Group</th>
						<th>Value</th>
						<th>Prescription</th>
						
					</tr>
				</thead>
				<c:forEach var="s" items="${pres}">
					<tbody>
						<tr>
							<td>${s.appn_id}</td>
							<td>${s.appn_sch_date}</td>
							<td>${s.patn_parameter}</td>
							<td>${s.patn_pargroup}</td>
							<td>${s.patn_value}</td>
							
							<td><a href="#" class="displayLink"
									data-image-url="data:image/jpg;base64,${s.patn_prescription}">Prescription 
			Image</a>
						</tr>
					</tbody>
				</c:forEach>




				</tbody>
			</table>
		</div>
   <!--  <table id="dataTable"></table>
    <script>
$(document).ready(function() {
	
		
		
		$.ajax({
			url : './getallprescriptions',
			type : 'GET',
			data: {
				id:1
				},
				
			
			success : function(response) {
			var data=response;
			
			    
			    
			},

		 error: function(xhr, status, error) {
                console.log("Error: " + error);
            }
			
		});
});
    </script> -->

</body>
</html>