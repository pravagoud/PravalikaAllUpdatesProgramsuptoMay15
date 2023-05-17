$(document).ready(function() {
	$('#progress').show();
	getLocation();
	ipLookUp();
	process();
	$("#timesheetform").submit(function(e) {
		e.preventDefault();

		var element = document.getElementById("clientName");
		var selectedValue = element.value;
		var cName = null;
		if (selectedValue === 'Other') {
			var textField = document.getElementById("client");
			cName = textField.value;
		} else {
			cName = $("#clientName").val();

		}
		if (cName == null) {
			$('#msg').text("Client Name can not Empty...");
			return;
		}
		if (cName.trim().length == 0) {
			$('#msg').text("Client Name can not Empty...");
			return;
		}

		var element1 = document.getElementById("location");
		var selectedlocValue = element1.value;
		console.log("selected value :" + selectedlocValue);
		var lName = null;
		if (selectedlocValue === 'work_from_office') {
			var textField1 = document.getElementById("Officelocation");
			lName = textField1.value;
			console.log("officeLocation is :" + lName);
		} else {
			lName = $("#location").val();

		}
		if (lName == null) {
			$('#msg').text("Location can not Empty...");
			return;
		}
		if (lName.trim().length == 0) {
			$('#msg').text("Location can not Empty...");
			return;
		}

		var empName = $("#empName").val();
		var empId = $("#empId").val();
		var clientName = cName;
		var startDate = $("#startDate").val();
		var email = $("#email").val();
		var endDate = $("#endDate").val();
		var phNo = $("#phNo").val();
		var domain = $("#domain").val();
		var shift = $("#shift").val();
		var timings = "";
		var location = lName;
		var startTimings = $("#startTime").val();
		var endTimings = $("#endTime").val();
		
		var lat = $('#lat').val();
		var lon	= $('#long').val();
		var ip =$('#ip').val();	
		var locDetails = $("textarea#locDetails").val();
		console.log("locDetails : "+locDetails);

		timings = startTimings + " - " + endTimings;

		var json = {
			"empName": empName,
			"empId": empId,
			"clientName": clientName,
			"startDate": startDate,
			"email": email,
			"endDate": endDate,
			"phNo": phNo,
			"domain": domain,
			"shift": shift,
			"timings": timings,
			"location": location,
			"lat": lat,
			"lon": lon,
			"ip": ip,
			"locDetails": locDetails,
		};
		console.log("json---" + JSON.stringify(json));

		var fd = new FormData();


		$.ajax({
			url: "timesheetdetails",
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(json),
			dataType: 'json',
			success: function(data, state) {
				console.log(data);
				console.log(state);
				alert('Successfully Submitted');
				//formData.append('timesheet_Id', data.id);
				$('#timesheet_Id').val(data.id);
				fd.append("timesheet_Id", data.id);
				var file_data = $('input[type="file"]')[0].files; // for multiple files
				for (var i = 0; i < file_data.length; i++) {
					fd.append("uploadfile", file_data[i]);
				}
				var other_data = $('form').serializeArray();
				$.each(other_data, function(key, input) {
					fd.append(input.name, input.value);
				});
				secondCall(fd);

			},
			error: function(err) {
				console.log(err.responseText);   // <-- printing error message to console
			}
		});
	});



});




function secondCall(fd) {
	$.ajax({
		url: "/uploadFile",
		type: "POST",
		data: fd,
		enctype: 'multipart/form-data',
		processData: false,
		contentType: false,
		cache: false,
		success: function(data, state) {
			console.log(data);
			console.log(state);
			$("#timesheetform")[0].reset();
		},
		error: function(err) {
			console.log(err.responseText);   // <-- printing error message to console
		}
	});

}

function process() {
	var un = document.getElementById('un').value;
	console.log("user name is :" + un);

	$.ajax({
		url: "/user/getUserTimeSheetDetails/" + un,
		type: "GET",
		contentType: "application/json",
		dataType: 'json',
		success: function(data, state) {
			console.log("+++++++++++++++++++++++++++++++++++++++++++++++++++++" + (JSON.stringify(data)));
			$('#progress').hide();
			$('#empName').val(data.employeeName);
			$('#empId').val(data.employeeId);
			$('#email').val(data.email);
			$('#phNo').val(data.phoneNumber);
			$('#domain').val(data.domain);
			$('#username').text(data.employeeName);
			if (data.userAvtarPic != null) {
				var imgData = "data:image/jpg;base64," + encodeURI(data.userAvtarPic);
				document.getElementById('pp').src = '/user/imageDownloader';
			}
			console.log(state);
		},
		error: function(err) {
			console.log(err.responseText);   // <-- printing error message to console
		}
	});
}

function popClientNameTextBox() {
	var element = document.getElementById("clientName");
	var selectedValue = element.value;
	if (selectedValue === 'Other') {
		var textField = document.getElementById("client");
		textField.style.display = 'block';
		/*textField.classList.add('active')*/
		console.log("if block : " + textField);
	}
	else {
		var textField = document.getElementById("client");
		console.log("else block : " + textField);
		textField.style.display = 'none';
		/*textField.classList.remove('active')*/
	}
};

function popLocationTextBox() {
	var element = document.getElementById("location");
	var abcd = document.getElementById("abcd");
	var selectedValue = element.value;
	if (selectedValue === 'work_from_office') {
		var textField = document.getElementById("Officelocation");
		abcd.style.display = 'block';
		console.log("if block : " + textField);
	}
	else {
		var textField = document.getElementById("Officelocation");
		console.log("else block : " + textField);
		abcd.style.display = 'none';
	}
};
function getLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	} else {
		$('#msg').text("Geolocation is not supported by this browser.");
	}
}
function showPosition(position) {
	$('#lat').val(position.coords.latitude);
	$('#long').val(position.coords.longitude);	
}

function ipLookUp () {
  $.ajax('http://ip-api.com/json')
  .then(
      function success(response) {
          console.log('User\'s Location Data is ', response);
          console.log('User\'s Country', response.country);
		  console.log('User\'s ip', response.query);	
		  $("#ip").val(response.query) ;
		$("#locDetails").text(JSON.stringify(response));
      },

      function fail(data, status) {
          console.log('Request failed.  Returned status of',
                      status);
      }
  );
}



