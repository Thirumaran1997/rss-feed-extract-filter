<html>

<head>
<title>RSS Feed Extract/Filter</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link href="css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
<style>

	table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(odd){background-color: #e67e22}

th {
/*   background-color: #4CAF50; */
  background-color: #e67e22;
  color: white;
}
</style>
</head>
<script src="https://code.jquery.com/jquery-3.4.1.js" integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" 
crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.7.9/angular.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" 
integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
<body ng-app="app" ng-controller="postController" style="background-image: url('css/login-background.png'); background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: 100% 100%;">
	<font color="red">${errorMessage}</font>
	<form method="post" >
	<div class="group" style="margin-left:16px; margin-top:16px">
      <label>City Name       :</label>
<!--       <input type="text" name="name" placeholder="City Name" ng-model="city.name" required="required"/><span class="highlight"></span><span class="bar"></span> -->
    <select name="name" id="cities" ng-model="city.name" ng-disabled=loading>
  		<option value="bangalore">Bengaluru</option>
  		<option value="chennai" selected>Chennai</option>
  		<option value="Coimbatore">Coimbatore</option>
  		<option value="Delhi">Delhi</option>
  		<option value="Hyderabad">Hyderabad</option>
  		<option value="Kochi">Kochi</option>
  		<option value="kolkata">Kolkata</option>
  		<option value="mumbai">Mumbai</option>
  		<option value="kozhikode">Kozhikode</option>
  		<option value="Madurai">Madurai</option>
  		<option value="Mangalore">Mangaluru</option>
  		<option value="puducherry">Puducherry</option>
  		<option value="Thiruvananthapuram">Thiruvananthapuram</option>
  		<option value="Tiruchirapalli">Tiruchirapalli</option>
  		<option value="Vijayawada">Vijayawada</option>
  		<option value="Visakhapatnam">Visakhapatnam</option>
	</select>
    </div>
    <div class="group" style="margin-left:16px;" >
      <label>Search Query :</label>
      <input type="text" name="password" placeholder="Search something" ng-disabled=loading ng-model="city.searchquery"/><span class="highlight"></span><span class="bar"></span>
    </div>
	<div class="group" style="margin-left:16px;">
		<input type="submit" id="submit" class="btn btn-success" ng-disabled=loading ng-click=postData() value="Push Records" />
	</div>
	<div class="group" style="margin-left:136px;margin-top:-30px" >
	 <button class="btn btn-success" ng-disabled=loading ng-click="filterData()">Filter Data</button>
	</div> 
<!-- 		<label>City Name :</label> <input type="text" name="name" ng-model="city.name" /> -->
<!-- 		Search Query : <input type="text" name="password" ng-model="city.searchquery"/>  -->
<!-- 		<input type="submit" /> -->
	</form>
	<table id="userTable" ng-show="showDetails">
	  <tr>
	 	 <th>City</th>
	  	<th>Author</th>
	    <th>Description</th>
	    <th>Link</th>
	    <th>Title</th>
	    <th>Date</th>
	  </tr>
	  <tr ng-repeat="data in recordDetails">
	  <td>{{data.city}}</td>
		<td>{{data.author}}</td>
	    <td>{{data.description}}</td>
	    <td>{{data.link}}</td>
	    <td>{{data.title}}</td>
	    <td>{{data.pubDate}}</td>
	</tr>
	</table>	
	<script>var App = angular.module('app',[]);</script>
	<script src="js/postController.js"></script>
	<script src="js/postService.js"></script>
</body>

</html>