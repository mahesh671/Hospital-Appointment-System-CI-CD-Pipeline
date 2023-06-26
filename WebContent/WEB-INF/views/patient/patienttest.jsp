<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="scripts.jsp" />
<title>DAS</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<script
	src="https://cdn.jsdelivr.net/npm/chart.js@4.3.0/dist/chart.umd.min.js"></script>
<script>
$(document).ready(function() {
	  // Get the hyperlinks elements
	  var displayLinks = $('.displayLink');

	  // Add click event listener to the parent element using event delegation
	  $('#testDetailsTableBody').on('click', '.displayLink', function(e) {
	    e.preventDefault();

	    // Get the image URL from the clicked hyperlink's data attribute
	    var imageUrl = $(this).data('image-url');
	    console.log("coming?");

	    // Create the popup window
	    var popupWindow = window.open('', 'Image Popup', 'width=500,height=500');

	    // Write the HTML content with Bootstrap classes to the popup window
	    popupWindow.document.write('<html><head><title>Image Popup</title><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"></head><body><div class="container"><div class="row"><div class="col-md-6"><img src="' + imageUrl + '" class="img-responsive"/><br/><button onclick="window.print()" class="btn btn-primary">Print</button> <button onclick="window.close()" class="btn btn-primary">Close</button></div></div></div></body></html>');
	  });

	  // Rest of your code...
	});

</script>
</head>



<body>
	<jsp:include page="nav.jsp" />
	<div align="center">
		<div class="container">
			<div class="row mt-4">
				<div class="col-md-12">
					<div class="filters">
						<label for="dateInput1">Select Date:</label> <input type="date"
							id="dateInput1"> </select> <label for="dateInput2">Select
							Date:</label> <input type="date" id="dateInput2">

						<button onclick="applyFilters()">Apply Filters</button>
					</div>
				</div>
			</div>

			<div class="row mt-4">
				<div class="col-md-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Recent Test</h5>
							<img id="testStatusImage"
								src="https://www.cdc.gov/fungal/diseases/histoplasmosis/images/318301-A_WEB_FungalLandingPages_Histo_Diagnosis.jpg"
								style="width: 100px; height: 50px;" alt="Test Status" />
						</div>
						<div class="card-body">
							<div class="test-details">
								<p id="testName" class="card-text">Test: MRI SCAN</p>
								<p id="testId" class="card-text">Test ID: 1</p>
								<p id="testDate" class="card-text">Date: 16-06-2023</p>
							</div>
							<div class="view-link">
								<a onclick="fun1()" id="viewLink">View Test</a>
							</div>
						</div>
					</div>
				</div>
			</div>


		</div>
		<div class="container">
			<div class="card-container" align="center">
				<div id="testDetailsTableContainer">
					<table id="testDetailsTable" class="table table-bordered">
						<thead>
							<tr>
								<th>s.no</th>
								<th>Report</th>
								<th>Date</th>
								<th>content</th>
							</tr>
						</thead>
						<tbody id="testDetailsTableBody">
							<c:forEach var="img" items="${reportUrls}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>Report ${status.index + 1}</td>
									<td>${img.getDgbl_date()}</td>
									<td><a href="#" class="displayLink"
										data-image-url="data:image/jpg;base64,${img.content}">Reports
											Image</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<style>
.filters {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-bottom: 20px;
}

.filters label {
	margin-right: 10px;
}

.filters select, .filters input[type="date"] {
	padding: 5px;
	border-radius: 5px;
	border: 1px solid #ccc;
}

.filters button {
	padding: 5px 10px;
	border-radius: 5px;
	background-color: #4CAF50;
	color: white;
	border: none;
	cursor: pointer;
}

.profile-pic {
	height: 100Px;
	width: 200px;
}
</style>




	<script>
		function applyFilters() {
			var selectedDate1 = document.getElementById("dateInput1").value;
			var selectedDate2 = document.getElementById("dateInput2").value;
			var testDetailsTableBody = document
					.getElementById("testDetailsTableBody");

			testDetailsTableBody.innerHTML = "";

			console.log(selectedDate1);
			console.log(selectedDate2);

			$.ajax({
				url : "./gettestbydate",
				type : "POST",
				data : {
					date1 : selectedDate1,
					date2 : selectedDate2
				},
				success : function(response) {

					console.log(response);
					// var filteredTestDetails = JSON.parse(response);
					var filteredTestDetails = response
					console.log("filteredtest: ", filteredTestDetails);

					// ...

					for (var i = 0; i < filteredTestDetails.length; i++) {
						var row = document.createElement("tr");

						var idColumn = document.createElement("td");
						idColumn.textContent = i + 1;

						var reportColumn = document.createElement("td");
						reportColumn.textContent = "Report " + (i + 1);
						
						var dateColumn = document.createElement("td");
						
						dateColumn.textContent = filteredTestDetails[i].dgbl_date.day+"/"+filteredTestDetails[i].dgbl_date.month+"/"+filteredTestDetails[i].dgbl_date.year;

						/* 
						<td><a href="#" class="displayLink"
					data-image-url="data:image/jpg;base64,${img.content}">Reports
								Image</a> */

						var imgColumn = document.createElement("td");
								imgColumn.innerHTML=`<a href="#" class="displayLink"
									data-image-url="data:image/jpg;base64,`+ filteredTestDetails[i].content+`">Reports
										Image</a>`
						/* var displayLink = document.createElement("a");
						displayLink.classList.add("displayLink");
						console.log(filteredTestDetails[i].content)
						displayLink.setAttribute("href", "#");
						displayLink.setAttribute("data-image-url",
								"data:image/jpg;base64,"
										+ filteredTestDetails[i].content);
						displayLink.textContent = "Reports Image"
						imgColumn.appendChild(displayLink) */

						row.appendChild(idColumn);
						row.appendChild(reportColumn);
						row.appendChild(dateColumn);
						row.appendChild(imgColumn);

						testDetailsTableBody.appendChild(row);
					}

					// ...
				},
				error : function(xhr, status, error) {
					console.log("Error: " + error);
				}
			});
		}

		// Update the UI to display the filtered test details
	</script>
</html>