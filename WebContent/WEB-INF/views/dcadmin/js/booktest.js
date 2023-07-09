function downloadReceipt() {
	// Get the content of the modal body
	var receiptContent = document.getElementById('bookingDetails3').innerHTML;

	// Create a temporary <a> element and set the download attribute
	var link = document.createElement('a');
	link.href = 'data:text/html;charset=utf-8,' + encodeURIComponent(receiptContent);
	link.download = 'receipt.html';

	// Programmatically trigger the download
	link.click();
}

var tests;
$(document).ready(function() {
	var categorySelect = document.getElementById("category");
	var patientSelect = document.getElementById("patient");
	$.ajax({
		url: "./gettestcat",
		type: "GET",

		success: function(response) {

			var data = JSON.parse(response);

			for (var i = 0; i < data.length; i++) {
				var option = document.createElement("option");
				option.value = data[i].test_category;
				option.text = data[i].test_category;
				categorySelect.appendChild(option);
			}
		},
		error: function(xhr, status, error) {
			console.log("Error: " + error);
		}
	});
	
	
	$.ajax({
		url: "./getpatients",
		type: "GET",

		success: function(response) {

			var data1  = JSON.parse(response);
			console.log(data1);
			for (var i = 0; i < data1.length; i++) {
				var option = document.createElement("option");
				option.value = data1[i].patn_d;
				option.text = data1[i].patn_d+" "+data1[i].patn_name;
				patientSelect.appendChild(option);
				//console.log(data1[i].patn_id);
			}
		},
		error: function(xhr, status, error) {
			console.log("Error: " + error);
		}
	});
	
});

function getTest() {
	var category = document.getElementById("category").value;

	$.ajax({
		url: "./gettestbycat",
		type: "POST",
		data: { cat: category },
		success: function(response) {

			loadTests(response);
		},
		error: function(xhr, status, error) {
			console.log("Error: " + error);
		}
	});
}
var contact2;
function allTests() {
	var patient = document.getElementById("patient").value;
	$.ajax({
		url: "./displaytests",
		type: "POST",
		data: {
			
			patient:  patient
			
		},
		success: function(response) {
		

			if (response.length > 0) {
				// Data is available, populate the table
				var tableBody = document.getElementById("tableBody");
				tableBody.innerHTML = ""; // Clear existing rows

				for (var i = 0; i < response.length; i++) {

					var row = document.createElement("tr");

					var testNameCell = document.createElement("td");
					testNameCell.textContent =  response[i][0][0];
					row.appendChild(testNameCell);

					var priceCell = document.createElement("td");
					priceCell.textContent =  response[i][0][2];
					row.appendChild(priceCell);

					var bookedDateCell = document.createElement("td");
					bookedDateCell.textContent =  response[i][0][3];
					row.appendChild(bookedDateCell);

					var actionsCell = document.createElement("td");
					var cancelButton = document.createElement("button");
					cancelButton.className = "btn btn-danger";
					cancelButton.textContent = "Cancel";
					 cancelButton.addEventListener("click", function() {
                var rowIndex = this.closest("tr").rowIndex;
                tableBody.deleteRow(rowIndex - 1); // Subtract 1 to account for header row
                var recordId = response[rowIndex - 1][0][0]; // Assuming the record ID is in the fifth element
                deleteRecord(recordId,patient);
            });
					actionsCell.appendChild(cancelButton);

					row.appendChild(actionsCell);

					tableBody.appendChild(row);
				}
				
				var paymentRow = document.createElement("tr");
var paymentCell = document.createElement("td");
paymentCell.colSpan = 4; // Set the colspan to the number of columns in the table
paymentCell.style.textAlign = "center"; // Align the content to the center
var paymentButton = document.createElement("button");
paymentButton.className = "btn btn-primary";
paymentButton.textContent = "Make Payment";
// Add the event listener for the payment button here if needed
paymentCell.appendChild(paymentButton);
paymentRow.appendChild(paymentCell);
tableBody.appendChild(paymentRow);

paymentButton.addEventListener("click", function() {
  totalbills();
});
				// Show the table and hide the no data message
				document.getElementById("tableContainer").style.display = "block";
				
			} else {
				// No data available, hide the table and show the no data message
				document.getElementById("tableContainer").style.display = "none";
				
			}
			
		},
		error: function(xhr, status, error) {
			}
			
});
}
function deleteRecord(recordId,patient){
	console.log(recordId);
	console.log(patient);
	$.ajax({
		url: "./cancelTest",
		type: "POST",
		data: {
			test: recordId,
			patient: patient
			
		},
		success: function(response) {
			console.log(response);
			
		},
		error: function(xhr, status, error) {
			var bookingDetails8 = '<h3><strong>'+xhr.responseText+'</strong></h3>';
			}
			});
}
function booktest() {
	console.log("loog");
	var category = document.getElementById("category").value;
	var testSelect = document.getElementById("test").value;
	var priceField = document.getElementById("tprice").value;
	
	var patient = document.getElementById("patient").value;
	
	console.log(patient);
	console.log("in booktest");
	document.getElementById("category").selectedIndex = 0;
	document.getElementById("test").selectedIndex = 0;
	document.getElementById("tprice").value = "";



	$.ajax({
		url: "./bookdctest",
		type: "POST",
		data: {
			
			cat: category,
			test: testSelect,
			price: priceField,
			patient:  patient
			
		},
		success: function(response) {
			console.log(response);
			
		},
		error: function(xhr, status, error) {
			var bookingDetails8 = '<h3><strong>'+xhr.responseText+'</strong></h3>';


	bookingDetails8 += '<p><strong>Test:</strong> ' + testSelect + '</p>';
	bookingDetails8 += '<p><strong>Price:</strong> ' + priceField + '</p>';


	$('#bookingDetails8').html(bookingDetails8);
	$('#previewModal8').modal('show');

		}
	});
}

function loadTests(tests) {
	console.log(tests);
	var testSelect = document.getElementById("test");
	testSelect.innerHTML = "";

	var option = document.createElement("option");
	option.value = "";
	option.text = "Select Test";
	testSelect.appendChild(option);

	for (var i = 0; i < tests.length; i++) {
		var option = document.createElement("option");
		option.value = tests[i].test_id;
		option.text = tests[i].test_name;
		testSelect.appendChild(option);
	}

}



function updatePrice() {
	var testSelect = document.getElementById("test").value;
	var priceField = document.getElementById("tprice");
	console.log(testSelect);
	$.ajax({
		url: "./gettestprice",
		type: "POST",
		data: { test:  testSelect },
		success: function(response) {
			var data = JSON.parse(response);
			
			priceField.value = data;
			console.log(priceField);

		},
		error: function(xhr, status, error) {
			console.log("Error: " + error);
		}
	});
}






function preview() {
	var category = document.getElementById("category").value;
	
	var priceField = document.getElementById("tprice").value;
	
	var patient = document.getElementById("patient").value;
	
	
	var age=document.getElementById("page").value;
	
	
	var testSelect = document.getElementById("test");
	var selectedOptionText = testSelect.options[testSelect.selectedIndex].textContent;

	var priceField = $('#tprice').val();

if(category!="main"&& priceField!="" && patient!=""&&  selectedOptionText!="undefined"){
	var bookingDetails = '<h3><strong>Test Confirm</strong></h3>';


	bookingDetails += '<p><strong>Test:</strong> ' + selectedOptionText + '</p>';
	bookingDetails += '<p><strong>Price:</strong> ' + priceField + '</p>';


	$('#bookingDetails').html(bookingDetails);
	$('#previewModal').modal('show');
	}
	else {
 

  if (category == "main") {
    addErrorMessage('#category', 'Please select a category.');
  }

  if (priceField == "") {
    addErrorMessage('#tprice', 'Please enter a price.');
  }
  if (age == "") {
    addErrorMessage('#page', 'Please enter age.');
  }
 

  if (patient == "main") {
    addErrorMessage('#patient', 'Please select a patient .');
  }

 

  if (selectedOptionText==undefined) {
    addErrorMessage('#test', 'Please select a test.');
  }

  // Display the error message modal
  $('#errorModal').modal('show');
  setTimeout(function() {
    $('.error-message').remove(); // Assuming error messages have a common class "error-message"
  }, 6000);
  
  
 
}



}
function addErrorMessage(selector, message) {
  $(selector).addClass('error');
  $(selector).after('<span class="error-message">' + message + '</span>');
}


var pdata;
function totalbills() {

	var patient = document.getElementById("patient").value;

	if (patient == "") {
		var bookingDetails = '<h3><strong>Enter patient Number</strong></h3>';
		$('#bookingDetails2').html(bookingDetails);
		$('#previewModal2').modal('show');
	}
	//console.log(contact);
	else {
		$.ajax({
			url: "./totalbills",
			type: "GET",
			data: {
				patient: patient
			},
			success: function(response) {

				pdata = JSON.parse(response);
				var pda = JSON.stringify(pdata).split(',');
				console.log(pda);
				console.log(pda[0]);
				console.log(pda[1]);
				var bookingDetails = '<h3><strong>Total Bill - $' + pdata[pdata.length - 1] + '</strong></h3>';
				

				$('#bookingDetails1').html(bookingDetails);
				$('#previewModal1').modal('show');
			},
			error: function(xhr, status, error) {
				console.log("Error: " + error);
			}
		});
	}
}

function payment() {

	var billid = pdata[pdata.length - 2];
	var amount = pdata[pdata.length - 1];
	var patient = document.getElementById("patient").value;

	var name = document.getElementById("pname").value;
	var email = document.getElementById("email").value;
	var gender = document.getElementById("gender").value;

	$.ajax({
		url: "./testpayment",
		type: "POST",
		data: {
			billid: billid,
			amount: amount,
			currency: "$"



		},

		success: function(response) {
		console.log(response);
			console.log("in pay");
			if (response.status == 'created') {
				var options = {
					"key": "rzp_test_wTvwL5iaSRljth",
					"amount": response.amount,
					"currency": "INR",
					"name": "RaphaHospital",
					"description": "Test Transaction",
					"image": "https://static.vecteezy.com/system/resources/thumbnails/006/817/240/small/creative-abstract-modern-clinic-hospital-logo-design-colorful-gradient-clinic-logo-design-free-vector.jpg",
					"order_id": response.id,
					"handler": function(response) {

						payment_id = response.razorpay_payment_id.toString();
						console.log(response.razorpay_payment_id);
						console.log(response.razorpay_order_id);
						console.log(response.razorpay_signature);
						storedb(payment_id);
						alert("success");
					},
					"prefill": {
						"name": "",
						"email": "",
						"contact": ""
					},
					"notes": {
						"address": "Razorpay Corporate Office"
					},
					"theme": {
						"color": "#3399cc"
					}
				};

				var rzp1 = new Razorpay(options);
				rzp1.on('payment.failed', function(response) {
					console.log(response.error.code);
					console.log(response.error.description);
					console.log(response.error.source);
					console.log(response.error.step);
					console.log(response.error.reason);
					console.log(response.error.metadata.order_id);
					console.log(response.error.metadata.payment_id);
					alert("Failed");

					window.location.href = "home";
				});

				rzp1.open();
			}




		},
		error: function(xhr, status, error) {
			// Handle the error response here
			console.log(xhr.responseText);
			var paymentError = '<h3><strong> Payment Error </strong></h3>';
			
		
			
			paymentError += '<p>Payment Failed : ' + xhr.responseText + '</p>'
			

			$('#bookingDetails4').html(paymentError);
			$('#previewModal4').modal('show');
		}
	});



}
function storedb(payment_id) {
	var contact = document.getElementById("contact").value;
	var patient = document.getElementById("patient").value
	var billid = pdata[pdata.length - 2];
	var amount = pdata[pdata.length - 1];
	var name = document.getElementById("pname").value;
	var email = document.getElementById("email").value;
	var gender = document.getElementById("gender").value;

	$.ajax({
		url: "./storedb",
		type: "POST",
		data: {
			patient: patient,

		},
		success: function(response) {
			console.log(response);
			var bookingDetails = '<h3><strong> Rapha Diagnostic Center </strong></h3>';
			bookingDetails += '<h4><strong>Total Bill - &#8377;' + pdata[pdata.length - 1] + '</strong></h4>';
			for (var i = 0; i < pdata.length - 1; i++) {

				bookingDetails += '<p><strong>Test - Method - Price</strong> ' + pdata[i][0] + '</p>';
			}
			bookingDetails += '<p>Bill Id: ' + response + '</p>'
			bookingDetails += '<p>Pay Reference Id: ' + payment_id + '</p>';
			bookingDetails += '<p>Contact: ' + contact + '</p>';
			bookingDetails += '<p>Name: ' + name + '</p>';
			bookingDetails += '<p>Email: ' + email + '</p>';
			bookingDetails += '<p>Gender: ' + gender + '</p>';

			$('#bookingDetails3').html(bookingDetails);
			$('#previewModal3').modal('show');

		},
		error: function(xhr, status, error) {
			// Handle the error response here
			console.log(xhr.responseText);
		}
	});
}
function sendReceiptByEmail() {
	//var receiptContent = document.getElementById('bookingDetails3').innerHTML;
	var receiptContent = document.getElementById('bookingDetails3');
	receiptContent = receiptContent.innerHTML.toString();

    // Perform further actions to send the email using the entered email value
    // You can make an AJAX request or call a backend API to send the email

	var email = document.getElementById("email").value;
	console.log(email);
	console.log(receiptContent);
	if (email) {

		$.ajax({
			url: "./mailsend2",
			method: "POST",
			data: {
				email: email,
				content: receiptContent

			},
			success: function(response) {
				console.log(response);
				
    // Close the popup after sending the email
    $('#emailPopup').remove();
				alert('Receipt sent successfully!');
			},
			error: function(xhr, status, error) {
				var mailError = '<p>Mail Sending Failed: ' + xhr.responseText + '</p>';

    // Create a new popup with a text field to enter an email
    mailError += '<div id="emailPopup">';
    mailError += '<label for="emailInput">Enter Email:</label>';
    mailError += '<input type="email" id="email">';
    mailError += '<button onclick="sendReceiptByEmail()">Send</button>';
    mailError += '</div>';

			$('#bookingDetails7').html(mailError);
			$('#previewModal7').modal('show');
			
			}
		});
	}
}

function fillPatientName() {
        var patientSelect = document.getElementById("patient");
        var pnameInput = document.getElementById("pname");
        var selectedPatient = patientSelect.options[patientSelect.selectedIndex].text;
var s=selectedPatient.split(" ");
        if (selectedPatient === "main") {
            pnameInput.value = "";
        } else {
			 pnameInput.value = "";
			for(var i=1;i<s.length;i++){
            pnameInput.value += s[i]+" "; 
            }// Replace with the actual value for the selected patient
        }
    }
function filterOptions() {
	// Get the search input element and select element
	const searchInput = document.getElementById('searchInput');
	const select = document.getElementById('patient');

	// Get the search term and convert it to lowercase
	const searchTerm = searchInput.value.toLowerCase();

	// Get all the options within the select element
	const options = select.options;

	// Loop through the options and hide/show based on the search term
	for (let i = 0; i < options.length; i++) {
		const option = options[i];
		const optionText = option.text.toLowerCase();

		// Check if the search term is found in the option text
		if (optionText.includes(searchTerm)) {
			option.style.display = '';  // Show the option
		} else {
			option.style.display = 'none';  // Hide the option
		}
		
	}
	
}

var contactInput = document.getElementById("contact");
    var emailInput = document.getElementById("email");

    contactInput.addEventListener("input", validateContact);
    emailInput.addEventListener("input", validateEmail);
     
    function validateContact() {
        var contactValue = contactInput.value;
        var contactError = document.getElementById("contactError");

        // Regex pattern for a valid contact number (example: 1234567890)
        var contactPattern = /^\d{10}$/;

        if (!contactPattern.test(contactValue)) {
            contactError.innerHTML = '<i class="error-icon fas fa-exclamation-circle"></i>Please enter a valid 10-digit contact number.';
            contactInput.classList.add("error-input");
        } else {
            contactError.textContent = "";
            contactInput.classList.remove("error-input");
        }
    }

    function validateEmail() {
        var emailValue = emailInput.value;
        var emailError = document.getElementById("emailError");

        // Regex pattern for a valid email address
        var emailPattern = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;

        if (!emailPattern.test(emailValue)) {
            emailError.innerHTML = '<i class="error-icon fas fa-exclamation-circle"></i>Make sure to include @mail.com';
            emailInput.classList.add("error-input");
        } else {
            emailError.textContent = "";
            emailInput.classList.remove("error-input");
        }
    }
    
 
    function validateAge() {
		 var ageInput = document.getElementById("page");

        var ageValue = ageInput.value;
        console.log(ageValue);
        var ageError = document.getElementById("ageError");

        if (ageValue === "" || ageValue < 0 || ageValue >= 150) {
            ageError.innerHTML = '<i class="error-icon fas fa-exclamation-circle"></i>Please enter a valid age less than 150.';
            ageInput.classList.add("error-input");
        } else {
            ageError.textContent = "";
            ageInput.classList.remove("error-input");
        }
    }