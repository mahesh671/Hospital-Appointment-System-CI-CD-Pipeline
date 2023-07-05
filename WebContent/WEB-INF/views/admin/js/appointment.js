
		$(document)
				.ready(
						function() {
							$('#doctor')
									.change(
											function() {
												$('#docdetails').hide();
												var id = $('#doctor').val();
												$
														.ajax({
															url : './fetchdoctor',
															type : 'GET',
															data : {
																id : id
															},
															success : function(
																	doc) {
																/* doc = JSON
																		.parse(doc); */

																console
																		.log(doc);
																$('#doc_name')
																		.text(
																				"Name: "
																						+ doc.doctName);
																$("#doc_qual")
																		.text(
																				"Qualification: "
																						+ doc.doctQual);
																$("#doc_exp")
																		.text(
																				"Experience: "
																						+ doc.doctExp);
																$("#doc_fee")
																		.text(
																				"Base Fee: "
																						+ doc.doctCfee);
																$("#doc_img")
																		.attr(
																				"src",
																				"data:image/png;base64,"
																						+ doc.doctPhoto);
																$('#appnfee')
																		.val(
																				Number(doc.doctCfee));
																$('#docdetails')
																		.show();
															}
														});

											});
						});

		$(document).ready(function() {
			$('#doctor').change(function() {
				var id = $('#doctor').val();
				var appointmentDate = $('#appointmentDate').val();
				$.ajax({
					url : './fetchtimeSlots',
					type : 'GET',
					data : {
						id : id,
						date : appointmentDate
					},
					success : function(timeslots) {
						/* timeslots = JSON.parse(timeslots); */
						console.log(timeslots);
						$('#slots').empty();
						for (var i = 0; i < timeslots.length; i++) {
							var option = $('<option/>');
							option.attr({
								'value' : timeslots[i]
							}).text(timeslots[i]);
							$('#slots').append(option);
						}
					}
				});

			});
		});

		$(document)
				.ready(
						function() {
							$('#appointmentDate')
									.change(
											function() {
												var appointmentDate = $(this)
														.val();
												var specialization = $(
														'#specialization')
														.val();
												// Perform an AJAX request to fetch doctors based on specialization
												$
														.ajax({
															url : './fetchBySpecialization',
															type : 'GET',
															data : {
																specialization : specialization,
																appointmentDate : appointmentDate
															},
															success : function(
																	response) {
																// Clear previous options
																/* response = JSON
																		.parse(response); */

																$('#doctor')
																		.empty();
																if(response.length==0)
																{
																$('#doctor')
																		.append(
																				'<option value="">No Doctor Is Available</option>');
																}
																else
																{
																$('#doctor')
																		.append(
																				'<option value="">Select Doctor</option>');
																}
																// Append new options based on response
																$
																		.each(
																				response,
																				function(
																						index,
																						doctor) {
																					$(
																							'#doctor')
																							.append(
																									'<option value="' + doctor.doctId + '">'
																											+ doctor.doctName

																											+ '</option>');
																				});
															},
															error : function(
																	xhr,
																	status,
																	error) {
																console
																		.log(error);
															}
														});
											});
						});

		// Function to toggle display of family members dropdown based on booking type
		$(document).ready(function() {
			$('input[name="bookingType"]').change(function() {
				var bookingType = $(this).val();
				if (bookingType === 'family') {
					$('#familyMembersGroup').show();
				} else {
					$('#familyMembersGroup').hide();
				}
			});
		});

		// Function to preview the booking details in the modal
		function previewBooking() {
			var specialization = $('#specialization').val();
			var appointmentDate = $('#appointmentDate').val();
			var doctor = $('#doctor option:selected').text();
			var slot = $('#slots option:selected').text();
			var bookingType = $('input[name="bookingType"]:checked').val();
			var familyMembers = $('#familyMembers option:selected').text();

			var bookingDetails = '<p><strong>Specialization:</strong> '
					+ specialization + '</p>';
			bookingDetails += '<p><strong>Date:</strong> ' + appointmentDate
					+ '</p>';
			bookingDetails += '<p><strong>Doctor:</strong> ' + doctor + '</p>';
			bookingDetails += '<p><strong>Slot:</strong> ' + slot + '</p>';

			if (bookingType === 'NEW PATIENT') {
				bookingDetails += '<p><strong>Booking Type:</strong> NEW PATIENT</p>';
				bookingDetails += '<p><strong>Family Member:</strong> '
						+ familyMembers + '</p>';
			} else {
				bookingDetails += '<p><strong>Booking Type:</strong> Existing Patient</p>';
			}
			bookingDetails += '<p><strong>Booking Fee:</strong>'
					+ $('#appnfee').val() + '</p>';

			$('#bookingDetails').html(bookingDetails);
			$('#previewModal').modal('show');
		}

		// Function to confirm the booking and submit the form
		function confirmBooking() {

			console.log("called");
			$('#appointmentForm').submit();
		}
	
		// Get references to the radio buttons and form sections
		const existingBookingRadio = document.getElementById("existingBooking");
		const newBookingRadio = document.getElementById("newBooking");
		const existingPatientForm = document
				.getElementById("existingPatientForm");
		const newPatientForm = document.getElementById("newPatientForm");

		// Function to show the existing patient form
		function showExistingPatientForm() {
			existingPatientForm.style.display = "block";
			newPatientForm.style.display = "none";
		}

		// Function to show the new patient form
		function showNewPatientForm() {
			existingPatientForm.style.display = "none";
			newPatientForm.style.display = "block";
		}

		// Add event listeners to the radio buttons
		existingBookingRadio.addEventListener("click", showExistingPatientForm);
		newBookingRadio.addEventListener("click", showNewPatientForm);
		function payment() {
			var amount = $('#appnfee').val()

			$
					.ajax({
						url : "./appnpayment",
						type : "POST",
						data : {

							amount : amount,
							currency : "$"

						},

						success : function(response) {
							console.log("in pay");
							if (response.status == 'created') {
								var options = {
									"key" : "rzp_test_wTvwL5iaSRljth",
									"amount" : response.amount,
									"currency" : "INR",
									"name" : "RaphaHospital",
									"description" : "Test Transaction",
									"image" : "https://static.vecteezy.com/system/resources/thumbnails/006/817/240/small/creative-abstract-modern-clinic-hospital-logo-design-colorful-gradient-clinic-logo-design-free-vector.jpg",
									"order_id" : response.id,
									"handler" : function(response) {

										payment_id = response.razorpay_payment_id
												.toString();
										console
												.log(response.razorpay_payment_id);
										console.log(response.razorpay_order_id);
										console
												.log(response.razorpay_signature);

										$('#appnrefer').val(
												response.razorpay_payment_id);
										$('#appnmode').val("card");
										console.log($('#appnrefer').val());
										console.log($('#appnmode').val());
										confirmBooking();

										console.log("confirmed");
										alert("Success");

									},
									"prefill" : {
										"name" : "",
										"email" : "",
										"contact" : ""
									},
									"notes" : {
										"address" : "Razorpay Corporate Office"
									},
									"theme" : {
										"color" : "#3399cc"
									}
								};

								var rzp1 = new Razorpay(options);
								rzp1
										.on(
												'payment.failed',
												function(response) {
													console
															.log(response.error.code);
													console
															.log(response.error.description);
													console
															.log(response.error.source);
													console
															.log(response.error.step);
													console
															.log(response.error.reason);
													console
															.log(response.error.metadata.order_id);
													console
															.log(response.error.metadata.payment_id);
													alert("Failed");

													window.location.href = "./";
												});

								rzp1.open();
							}
						},
						error : function(xhr, status, error) {
								// Handle the error response here
			console.log(xhr.responseText);
			var paymentError = '<h3><strong> Payment Error </strong></h3>';
			
		
			
			paymentError += '<p>Payment Failed : ' + xhr.responseText + '</p>'
			

			$('#bookingDetails4').html(paymentError);
			$('#previewModal4').modal('show');
						}
					});
		}

		$(document).ready(function() {
			$('#appointmentForm').submit(function(event) {
				event.preventDefault(); // Prevent form submission

				var formData = $(this).serialize(); // Serialize the form data

				$.ajax({
					type : 'POST',
					url : './newappointment/create',
					data : formData,
					success : function(response) {
						// Handle successful response from the server
						console.log(response);
						$('#previewModal').modal('hide');
						window.location.reload();
					},
					error : function(xhr, status, error) {
					console.log("refund");
							console.log(xhr.responseText);
							
			var refundAmount = '<h3><strong> Amount Refunded </strong></h3>';
			
	
var trimmedString = xhr.responseText.trim().substr(29,12);
		
			//xhr.responseText.trim();
				refundAmount += '<p>Slot is already Booked  ' +""+ '</p>'
			
			refundAmount += '<p>Refund : ' + trimmedString + '</p>'
			$('#previewModal').modal('hide');

			$('#bookingDetails5').html(refundAmount);
			$('#previewModal5').modal('show');
				
						
					}
				});
			});
		});
	
