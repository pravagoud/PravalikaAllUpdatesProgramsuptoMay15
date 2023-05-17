$(document).ready(function() {
 var un = document.getElementById('un').value;
	console.log("user name is :" + un);

	$.ajax({
		url: "/admin/getUserDetails/" + un,
		type: "GET",
		contentType: "application/json",
		dataType: 'json',
		success: function(data, state) {
			console.log("+++++++++++++++++++++ " + data.employeeName);
			$('#username').text(data.employeeName);
			sessionStorage.setItem("employeename",data.employeeName);
			if (data.userAvtarPic != null) {
				var imgData = "data:image/jpg;base64," + encodeURI(data.userAvtarPic);
				document.getElementById('pp').src = '/admin/imageDownloader';
			}
		//	console.log(state);
		},
		error: function(err) {
		console.log(err.responseText);   // <-- printing error message to console
		}
	});
});
