$(document).ready(function() {
	// Retrieve the JSON data from the "tests" model attribute
	$.ajax({
		url: "./getapptestcards", // Specify the URL of the controller method
		type: "GET", // Use POST or GET depending on your server-side implementation
		// Pass the patientId as data to the server

		success  function(response) {
			var data = (response);
			console.log(response);
			// Iterate over the data and create table rows
			var toapp = document.getElementById("toapp");
			var totest = document.getElementById("totest");
			toapp.textContent = data[0];

			totest.textContent = data[1];


		},

		error: function(xhr, status, error) {
			console.error("Error: " + error);
		}
	});


	$.ajax({
		url: "./getapptests", // Specify the URL of the controller method
		type   "GET",
		success  function(response) {
			var data = JSON.parse(response);
			var tableBody2 = $('#teststable');



			for (var i = 0; i < data.length; i++) {

				var newRow1 = $('<tr>');
				for (var k = 0; k < 2; k++) {



					newRow1.append($('<td>').text(data[i][k]));

					tableBody2.append(newRow1);

				}
			}
		},
	});

	$.ajax({
		url: "./getapps", // Specify the URL of the controller method
		type: "GET",
		success  function(response) {
			var data = JSON.parse(response);
			var tableBody = $('#apptable');

			tableBody.empty();

			for (var i = 0; i < data.length; i++) {
				var newRow = $('<tr>');

				for (j = 0; j < 4; j++) {
					newRow.append($('<td>').text(data[i][j]));
					tableBody.append(newRow);
				}
			}
		},
	});

	
});