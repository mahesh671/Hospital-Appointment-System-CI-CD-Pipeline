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