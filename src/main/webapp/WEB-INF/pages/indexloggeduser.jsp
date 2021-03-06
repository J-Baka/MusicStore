<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">
<title>Welcome Page</title>
<style>
img{
    height: 500px;
    }
.left-align-footer {
  text-align: right;
} 
</style>
</head>
<body>
	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<!-- brand header -->
			<a class="navbar-brand" href="/">Music.ly</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<!-- Home tab -->
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="/" name="target-top-home" >Home</a></li>
					<!-- Products -->
					<li class="nav-item"><a class="nav-link " href="/product">Products</a>
					</li>
					<!-- Welcome User -->
					<li class="nav-item"> <a class="nav-link">Welcome: ${ username}</a></li>
					<!-- Logout -->
					<li class="nav-item"><a class="nav-link" href="<c:url value="/logout"/>">Logout</a></li>
					<!-- Cart -->
					<li class="nav-item"><a class="nav-link" href="/cart">Cart</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<!--carousel  -->
	<div id="carouselExampleCaptions" class="carousel slide"
		data-bs-ride="carousel">
		<div class="carousel-indicators">
			<button type="button" data-bs-target="#carouselExampleCaptions"
				data-bs-slide-to="0" class="active" aria-current="true"
				aria-label="Slide 1"></button>
			<button type="button" data-bs-target="#carouselExampleCaptions"
				data-bs-slide-to="1" aria-label="Slide 2"></button>
			<button type="button" data-bs-target="#carouselExampleCaptions"
				data-bs-slide-to="2" aria-label="Slide 3"></button>
		</div>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img
					src="https://static.roland.com/assets/images/products/categories/rct_v-drums_acoustic_design.jpg"
					class="d-block w-100" alt="...">
				<div class="carousel-caption d-none d-md-block" >
					<h5>First slide label</h5>
					<p>Some representative placeholder content for the first slide.</p>
				</div>
			</div>
			<div class="carousel-item">
				<img
					src="https://upload.wikimedia.org/wikipedia/commons/8/8b/20131205_Istanbul_321_cropped.jpg"
					class="d-block w-100" alt="...">
				<div class="carousel-caption d-none d-md-block" >
					<h5>Second slide label</h5>
					<p>Some representative placeholder content for the second
						slide.</p>
				</div>
			</div>
			<div class="carousel-item">
				<img
					src="https://cdn4.eyeem.com/thumb/61e12700e42f3a252909be4e7765cbee61452c42-1583583725109/w/1300"
					class="d-block w-100" alt="...">
				<div class="carousel-caption d-none d-md-block">
					<h5>Third slide label</h5>
					<p>Some representative placeholder content for the third slide.</p>
				</div>
			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Next</span>
		</button>
	</div>

	<!-- Footer -->
	 <footer class="row">
    <div class="col"> @2016 Company.Inc
    <a href="#">Privacy </a> &#8226 <a href="#">Terms</a>
    </div>
    <div class="col left-align-footer">
    <a  href="#target-top-home">Back to top</a>
    </div>
     </footer>
	<!-- Bootstrap -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
		crossorigin="anonymous"></script>
</body>
</html>