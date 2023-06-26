<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*, spring.orm.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Specialization</title>
<jsp:include page="scripts.jsp" />


</head>
<body>

	<jsp:include page="nav.jsp" />
	<%
		List<Specialization> slist = (List<Specialization>) request.getAttribute("slist");
	%>
	<div align="center">
		<div class="card-container " align="center">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Total Specializations</h5>
					<p class="card-text"><%=slist.size()%></p>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Doctors Available</h5>
					<p class="card-text">15</p>
				</div>
			</div>
		</div>

		<div class="container">
			<h3 align="center">Specializations List</h3>

			<div class="col-md-12">
				<table class="table mt-4">
					<thead>
						<tr>
							<th>ID</th>
							<th>Title</th>
							<th>Description</th>
							<th>Actions</th>
						</tr>
					</thead>

					<%
						for (Specialization s : slist) {
					%>
					<tbody>
						<tr>
							<td><%=s.getId()%></td>
							<td><%=s.getTitle()%></td>
							<td><%=s.getDescription()%></td>
							<td>
								<button class="btn btn-primary"
									onclick="getspec('<%=s.getId()%>')">Edit</button>
								<button class="btn btn-danger"
									onclick="deletespec('<%=s.getId()%>')">Delete</button>
							</td>
						</tr>
						<%
							}
						%>
						
					</tbody>
				</table>
			</div>
		</div>


	
	<center>
		<button type="button" class="btn btn-primary" id="show-btn"
			onclick="onclickspec()">Add Specialization</button>

			<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6">
					<div class="shadow p-3 mb-5 bg-white rounded" id="specializationForm" style="display: none;">
						<form action="./savespec" , method="post" id="edit-spec-form">
							<div class="form-group">
								<label for="idInput">ID</label> <input type="text"
									class="form-control" name="idInput" id="idInput" required>
							</div>
							<div class="form-group">
								<label for="titleInput">Title</label> <input type="text"
									class="form-control" name="titleInput" id="titleInput" required>
							</div>
							<div class="form-group">
								<label for="descriptionInput">Description</label> <input
									type="text" class="form-control" id="descriptionInput" name="descriptionInput" required>
							</div>
							<button id="add-spec-btn" type="submit" class="btn btn-primary">Add</button>
						</form>
					</div>
				</div>
			</div>
		</div>
			


		</div>


	</center>



	</div>
	</div>
	</div>
	</div>

	<script>
/* 		const btn = document.getElementById('btn');

		btn.addEventListener('click', () => {
		  const form = document.getElementById('testform');

		  if (form.style.display === 'none') {
		   
		    form.style.display = 'block';
		  } else {
		  
		    form.style.display = 'none';
		  }
		}); */</script>

	<!--  <button type="button" class="btn btn-primary" id="show-btn"
		onclick="showForm()">Add Specialization</button>
	<div id="specializationForm" style="display: none;">
		<form action="updatetest" method="post">
			<div class="form-group">
				<label for="idInput">ID</label> <input type="text"
					class="form-control" name="idInput" id="idInput" required>
			</div>
			<div class="form-group">
				<label for="titleInput">Title</label> <input type="text"
					class="form-control" name="titleInput" id="titleInput" required>
			</div>
			<div class="form-group">
				<label for="descriptionInput">Category</label> <input type="text"
					class="form-control" name="descriptionInput" id="descriptionInput"
					required>
			
			<button type="submit" id="updatebtn" class="btn btn-primary">Add</button>
		</form>
	</div>
-->
</body>
<!-- <script>

	$('#show-btn').click(function()
			{
		$('#idInput').val('');
		$('#titleInput').val('');
		$('#descriptionInput').val('');
		

		$('#specializationForm').toggle();
			});
	
	function delspeci(specid)
	{
		$.ajax({
			url : "./deltest",
			method:"post",
			data:{
				id:specid
			},
			  success: function(response) {
			console.log(response); // You can log the response or perform further actions as needed
        },
        error: function(xhr, status, error) {
            // Handle any error that occurred during the AJAX request
            console.log("Error: " + error);
            }
		});
	}
	function getspeci(specid)
	{
		$.ajax({
			url : "./getspec",
			method:"post",
			data:{
				id:specid
			},
			success : function(spec) {
				//spec=JSON.parse(spec);
				console.log(typeof(spec));
				$('#idInput').val(spec.idInput);
				$('#titleInput').val(spec.titleInput);
				$('#descriptionInput').val(spec.descriptionInput);
				
			
					
					
			
				$('#specializationForm').show();
				$('#updatebtn').text('update');
			}
		});
	}
	</script> -->
</html>