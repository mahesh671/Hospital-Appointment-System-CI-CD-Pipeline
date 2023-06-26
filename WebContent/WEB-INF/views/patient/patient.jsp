<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>DAS</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<jsp:include page="scripts.jsp" />
</head>

<body>
	<jsp:include page="nav.jsp" />
	<div align="center">
		<div class="container">
			<div class="card-container" align="center">

				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Todays Appointments </h5>
						<p id="toapp" class="card-text"></p>
					</div>
				</div>
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">Total Tests</h5>
						<p id="totest" class="card-text"></p>
					</div>
				</div>
			</div>

			<h3>Appointments</h3>
			<div class="row mt-4">
				<div class="col-md-12">
					<table class="table table-bordered" >
						<thead>
							<tr>
								<th>Appointment</th>

								<th>Doctor</th>
								<th>Date</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody id="apptable">


						</tbody>
					</table>
				</div>
			</div>
		</div>
<div class="container">
			<div class="card-container" align="center">
		<h3>Today Diagnostic Tests</h3>
		<table class="table table-striped" >
			<thead>
				<tr>
					<th>Test ID</th>
					<th>Test Name</th>
					
				</tr>
			</thead>
			<tbody id="teststable">
				<!-- Dynamically populated with patient's diagnostic tests -->
				
			</tbody >
		</table >
	</div>
	</div>
	</div>
	<script>
		$(document).ready(function() {
			// Retrieve the JSON data from the "tests" model attribute
			$.ajax({
				url : "./getapptestcards", // Specify the URL of the controller method
				type : "GET", // Use POST or GET depending on your server-side implementation
				// Pass the patientId as data to the server
			
				success : function(response) {
					var data = (response);
					console.log(response);
					// Iterate over the data and create table rows
					var toapp = document.getElementById("toapp");
					var totest = document.getElementById("totest");
					toapp.textContent = data[0];

					totest.textContent = data[1];

					
				},
				
				error : function(xhr, status, error) {
					console.error("Error: " + error);
				}
			});
			$.ajax({
				url : "./getapptests", // Specify the URL of the controller method
				type : "GET",
				success : function(response) {
					var data = JSON.parse(response);
					var tableBody2 = $('#teststable');
					
					
					 
						for (var i =0; i < data.length; i++) {
							
							var newRow1 = $('<tr>');
						for (var k = 0; k < 2; k++) {
							
							
							
							newRow1.append($('<td>').text(data[i][k]));
							
							tableBody2.append(newRow1);	
						
					}
					} 
				},
			});
			$.ajax({
				url : "./getapps", // Specify the URL of the controller method
				type : "GET",
				success : function(response) {
					var data = JSON.parse(response);
			var tableBody = $('#apptable');
			
			tableBody.empty();
			
			for (var i = 0; i <data.length; i++) {
				var newRow = $('<tr>');
				
				for(j=0;j<4;j++){
						newRow.append($('<td>').text(data[i][j]));
						tableBody.append(newRow);
			}
						}
				},
			});
			  $.ajax({
			      url: './getOutParaGroup', // Specify the URL of the controller method to retrieve the test details
			      type: 'GET',
			  	success : function(response) {
					var data = JSON.parse(response);
				
					console.log(data);
					var labels=[];
					var cholesterolData =[];
					var sugarData =[];
					var bpData = [];
					for(var i=0;i<data.length;i++){
						var date=data[i].appn_sch_date.split(",");
						
				 labels.push(date[0]);
				
				//var dta=data[i][0].split(" ")
				if(data[i].patn_parameter=="Blood Pressure"){
					var dta=data[i].patn_value.split(" ")
				cholesterolData.push(dta[0]);
				}
				else if(data[i].patn_parameter=="Cholestrol"){	
				bpData.push(data[i].patn_value);				
			
				}	
				else {
					
					
				sugarData.push(data[i].patn_value);
			
				}	
		
					}
		  var ctx = document.getElementById("healthChart").getContext("2d");
		  var chart = new Chart(ctx, {
		    type: "line",
		    data: {
		      labels: labels,
		      datasets: [{
		        label: "Blood Pressure",
		        data: bpData,
		        borderColor: "rgb(255, 99, 132)",
		        backgroundColor: "rgba(255, 99, 132, 0.2)",
		        fill: false
		      }, {
		        label: "Cholesterol",
		        data: cholesterolData,
		        borderColor: "rgb(54, 162, 235)",
		        backgroundColor: "rgba(54, 162, 235, 0.2)",
		        fill: false
		      }, {
		        label: "Sugar Levels",
		        data: sugarData,
		        borderColor: "rgb(75, 192, 192)",
		        backgroundColor: "rgba(75, 192, 192, 0.2)",
		        fill: false
		      }]
		    },
		    options: {
		      responsive: true,
		      scales: {
		        x: {
		          display: true,
		          title: {
		            display: true,
		            text: "Date"
		          }
		        },
		        y: {
		          display: true,
		          title: {
		            display: true,
		            text: "Value"
		          }
		        }
		      }
		    }
		  });
		},
		error : function(xhr, status, error) {
			console.error("Error: " + error);
		}
	});
			   $.ajax({
				      url: './getPrescriptionView', // Specify the URL of the controller method to retrieve the test details
				      type: 'GET',
				  	success : function(response) {
						var data = (response);
						
						
						console.log(data);
						var doctorNameElement = document.getElementById('doctorName');
						var appointmentFeesElement = document.getElementById('appointmentFees');
						var lastVisitDateElement = document.getElementById('lastVisitDate');
						var nextVisitDateElement = document.getElementById('nextVisitDate');

						doctorNameElement.textContent = 'Doctor: '+data[0].doct_name;
						appointmentFeesElement.textContent = 'Appointment Fees: '+data[0].appn_payamount;
						lastVisitDateElement.textContent = 'Last Visit: '+data[0].patn_lastvisit.day+"-"+data[0].patn_lastvisit.month+"-"+data[0].patn_lastvisit.year;
						nextVisitDateElement.textContent = 'Next Appointment: '+data[0].appn_sch_date;
						},
						error : function(xhr, status, error) {
							console.error("Error: " + error);
						}
			   });
		});
	</script>
<div align="center">
  <div class="container">
    <div class="card-container" style="display: flex; justify-content: space-between;">

      <div class="card">
      
  <div class="card-body">
    <div class="doctor-details">
    <strong> <p class="card-text">Last Appointment</p></strong>
      <p id="doctorName" class="card-text"></p>
      <p id="appointmentFees" class="card-text"></p>
      <p id="lastVisitDate" class="card-text"></p>
       <p id="nextVisitDate" class="card-text"></p>
      <a href="#" onclick="viewPrescription()" class="card-link">View Prescription</a>
    </div>
        </div>
      </div>

      <div class="card">
        <div class="card-body">
          <h5 class="card-title">Patient Health Overview</h5>
          <div class="chart-container">
            <canvas id="healthChart" style="width: 600px; height: 600px;"></canvas>
          </div>
        </div>
      </div>

    </div>
  </div>


	<div class="container">
			<div class="card-container" align="center">
<div id="testDetailsTableContainer" style="display: none;">
  <table id="testDetailsTable" class="table table-bordered">
    <thead>
      <tr>
        <th>Test ID</th>
        <th>Test Name</th>
        <th>Method</th>
        <th>Price</th>
        <th>Report</th>
      </tr>
    </thead>
    <tbody id="testDetailsTableBody"></tbody>
  </table>
</div>
</div>
</div>
</div>

	
	<style>
  /* Popup styling */
  .popup-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
  }

  .popup-content {
    max-width: 500px;
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
    position: relative;
  }

  .close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
    font-size: 18px;
    color: #555;
  }

  .popup-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .prescription-details {
    font-size: 14px;
    line-height: 1.5;
  }
</style>

<script>
  function viewPrescription() {
	  $.ajax({
	      url: './getPrescriptionView', // Specify the URL of the controller method to retrieve the test details
	      type: 'POST',
	      data:{
	    	id:1  
	      },
	  	success : function(response) {
			//var data = JSON.parse(response);
    // Retrieve the prescription details (sample data for demonstration)
    console.log(data);
   

    
  }
	  });
  }
</script>


	
	<script>
	
		  // ...

		  // Handle the click event of the "View Test" link
		 function fun() {
		    
		   // var testId = $(this).data('test-id');
		    var testId=3;
		    // Make an AJAX request to fetch the test details
		    $.ajax({
		      url: './getTestDetails', // Specify the URL of the controller method to retrieve the test details
		      type: 'POST',
		      data: { testId: testId }, // Pass the test ID as a parameter
		      success: function(response) {
		        // Handle the response and populate the test details in the table
		        var testDetails = JSON.parse(response);
		        console.log(testDetails);

		        // Clear any existing rows in the table
		        $('#testDetailsTableBody').empty();

		        // Iterate over the testDetails array and create table rows
		      
		          var row = $('<tr>');
		          row.append($('<td>').text(testDetails[0]));
		          row.append($('<td>').text(testDetails[1]));
		          row.append($('<td>').text(testDetails[2]));
		          row.append($('<td>').text(testDetails[3]));
		          var reportColumn = $('<td>');
		        //  if (testDetails[i].report) {
		          //  var downloadLink = $('<a>').attr('href', testDetails[i].report).text('Download PDF');
		            //reportColumn.append(downloadLink);
		          //}
		          //row.append(reportColumn);
		          $('#testDetailsTableBody').append(row);
		        

		        // Show the table
		        $('#testDetailsTableContainer').show();
		      },
		      error: function(xhr, status, error) {
		        console.error('Error: ' + error);
		      }
		    });
		  }
		
		 function fun1() {
			
			  var testDetails = [
			    { id: 1, name: "MRI", method: "Magnetic Resonance Imaging", price: "$345" ,report:"https://www.lalpathlabs.com/SampleReports/Z289.pdf"},
			    // Add more test details as needed
			  ];

			  // Clear any existing rows in the table
			  $('#testDetailsTableBody').empty();

			  // Iterate over the testDetails array and create table rows
			  testDetails.forEach(function(detail) {
			    var row = $('<tr>');
			    row.append($('<td>').text(detail.id));
			    row.append($('<td>').text(detail.name));
			    row.append($('<td>').text(detail.method));
			    row.append($('<td>').text(detail.price));
			    var reportColumn = $('<td>');
			    if (detail.report) {
			    	
			      var downloadLink = $('<a>').attr('https://www.lalpathlabs.com/SampleReports/Z289.pdf', detail.report).text('Download PDF');
			      reportColumn.append(downloadLink);
			    }
			    row.append(reportColumn);
			    row.append(reportColumn);
			    $('#testDetailsTableBody').append(row);
			  });

			  // Show the table
			  $('#testDetailsTableContainer').show();
			}

	</script>
</body>
</html>