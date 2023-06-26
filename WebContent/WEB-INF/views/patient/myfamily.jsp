<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*, spring.orm.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DAS</title>
<jsp:include page="scripts.jsp" />
<style>
.popup-form {
	display: none;
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background-color: white;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
}

.popup-form input[type="text"], .popup-form input[type="number"] {
	margin-bottom: 10px;
}

.popup-form button {
	margin-top: 10px;
}
</style>

</head>
<body>
	<jsp:include page="nav.jsp" />

	<div align="center">
		<div class="container">
			<h1>My Profile</h1>

			<h2 class="mt-4">Family Members</h2>
			<table class="table mt-4">
				<thead>
					<tr>
						<th>Name</th>
						<th>Age</th>
						<th>Gender</th>
						<th>Blood Group</th>
						<th>Relation</th>
						<!-- 		<th>Action</th>
 -->
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${members}" var="member">
						<tr>
							<td>${member.pfmbName}</td>
							<td>${member.pfmbAge}</td>
							<td>${member.pfmbGender}</td>
							<td>${member.pfmbbgroup}</td>
							<td>${member.pfmbRelation}</td>
							<!-- Add your buttons or actions here -->
							<!-- <td><input type="button" value="Edit"
								class="btn btn-success edit-button"> <input
								type="button" value="Delete" class="btn btn-danger"></td>
						</tr> -->
					</c:forEach>
				</tbody>
			</table>


			<div class="container">
				<div class="row">
					<div class="col-md-6">
						<h4>Add Family Members</h4>
						<form id="addForm">
							<div class="form-group">
								<label for="pfmbName">Name:</label> <input type="text"
									id="pfmbName" name="pfmbName" class="form-control" required>
							</div>
							<div class="form-group">
								<label for="pfmbRelation">Relation:</label> <input type="text"
									id="pfmbRelation" name="pfmbRelation" class="form-control"
									required>
							</div>
							<div class="form-group">
								<label for="pfmbAge">Age:</label> <input type="number"
									id="pfmbAge" name="pfmbAge" class="form-control" required>
							</div>
							<div class="form-group">
								<label for="pfmbGender">Gender:</label> <select id="pfmbGender"
									name="pfmbGender" class="form-control" required>
									<option value="Male">Male</option>
									<option value="Female">Female</option>
									<option value="Other">Other</option>
								</select>
							</div>
							<div class="form-group">
								<label for="pfmbbgroup">Blood Group:</label> <select
									id="pfmbbgroup" name="pfmbbgroup" class="form-control" required>
									<option value="">Select</option>
									<option value="A+">A+</option>
									<option value="A-">A-</option>
									<option value="B+">B+</option>
									<option value="B-">B-</option>
									<option value="AB+">AB+</option>
									<option value="AB-">AB-</option>
									<option value="O+">O+</option>
									<option value="O-">O-</option>
								</select>
							</div>
							<button type="submit" class="btn btn-primary">Add Member</button>
						</form>
					</div>
				</div>
			</div>


			<div id="editFormSection" style="display: none;">
				<h4>Edit Family Member</h4>
				<form id="editForm" method="post">
					<input id="editId" name="editId" type="text" hidden> <label
						for="editName">Name:</label> <input type="text" id="editName"
						name="editName"><br> <label for="editRelation">Relation:</label>
					<input type="text" id="editRelation" name="editRelation"><br>
					<label for="editAge">Age:</label> <input type="number" id="editAge"
						name="editAge"><br>
					<button type="submit" id="update">Update</button>
				</form>
			</div>
		</div>
	</div>
	<div id="editFormSection" style="display: none;">
		<h4>Edit Family Member</h4>
		<form id="editForm" method="post">
			<!-- Form fields -->
			<button type="submit" id="update">Update</button>
		</form>
		<button id="closePopupButton">Close</button>
	</div>

</body>
<script>
	$(document).ready(function() {
		// Handle form submission for adding a family member
		$("#addForm").submit(function(e) {
			e.preventDefault();
			var formData = $(this).serialize();
			if (validateForm()) {
			$.ajax({
				type: "POST",
				url: "./savefm",
				data: formData,
				success: function(response) {
					// Handle success response
					// Update the table with the new family member dynamically
					window.location.reload();
				},
				error: function() {
					// Handle error response
				}
			});
			}
		});
	});

	function validateForm() {
	    var bloodGroup = $("#pfmbbgroup").val();
	    if (bloodGroup === "") {
	        alert("Please select a valid blood group.");
	        return false;
	    }
	    return true;
	}
		</script>
<script>
	document.addEventListener("DOMContentLoaded", function() {
		var editButtons = document.querySelectorAll(".edit-button");
		var popupForm = document.getElementById("editFormSection");
		var closePopupButton = document.getElementById("closePopupButton");

		editButtons.forEach(function(button) {
			button.addEventListener("click", function() {
				popupForm.style.display = "block";
			});
		});

		closePopupButton.addEventListener("click", function() {
			popupForm.style.display = "none";
		});
	});
</script>

<script type="text/javascript">
// Handle click event for deleting a family member
function deleteMember(memberId) {
    var confirmation = confirm("Are you sure you want to delete this family member?");
    if (confirmation) {
        $.ajax({
            type: "POST",
            url: "./deleteMember",
            data: {
                memberId: memberId
            },
            success: function(response) {
                // Handle success response
                alert("Family member deleted successfully.");
                // Remove the corresponding row from the table dynamically
                $("table tbody").find(`tr[data-memberid='${memberId}']`).remove();
            },
            error: function() {
                // Handle error response
            }
        });
    }
} 

   // Handle click event for editing a family member
function editMember(memberId) {
    $.ajax({
        type: "GET",
        url: "./getMember",
        data: {
            memberId: memberId
        },
        success: function(response) {
            // Handle success response
            // Populate the edit form with the retrieved data
            $("#editId").val(response.member.id);
            $("#editName").val(response.member.name);
            $("#editRelation").val(response.member.relation);
            $("#editAge").val(response.member.age);
            $("#editFormSection").show();
        },
        error: function() {
            // Handle error response
        }
    });
}

// Handle form submission for updating a family member
$("#editForm").submit(function(e) {
    e.preventDefault();
    var formData = $(this).serialize();
    $.ajax({
        type: "POST",
        url: "./updateMember",
        data: formData,
        success: function(response) {
            // Handle success response
            $("#editFormSection").hide();
            // Update the corresponding row in the table dynamically
            var member = response.member;
            var updatedRow = `
                <tr data-memberid="${member.id}">
                    <td>${member.name}</td>
                    <td>${member.age}</td>
                    <td>${member.gender}</td>
                    <td>${member.bloodGroup}</td>
                    <td>${member.relation}</td>
                    /* <td>
                        <button onclick="editMember(${member.id})">Edit</button>
                        <button onclick="deleteMember(${member.id})">Delete</button>
                    </td> */
                </tr>`;
            $("table tbody").find(`tr[data-memberid='${member.id}']`).replaceWith(updatedRow);
            // Reset the form
            $("#editForm")[0].reset();
        },
        error: function() {
            // Handle error response
        }
    });
}); 
</script>
</html>