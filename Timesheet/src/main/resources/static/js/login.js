$(document).ready(function() {

//	$("#upload-file-input").on("change", uploadFile);

	$("#timesheetform").submit(function() {
		var empName = $("#empName").val();
		var empId = $("#empId").val();
		var clientName = $("#clientName").val();
		var startDate = $("#startDate").val();
		var email = $("#email").val();
		var endDate = $("#endDate").val();
		var phNo = $("#phNo").val();
		var domain = $("#domain").val();
		//	var uploadFile = $("#formFileLg").val();

		var json = {
			"empName": empName,
			"empId": empId,
			"clientName": clientName,
			"startDate": startDate,
			"email": email,
			"endDate": endDate,
			"phNo": phNo,
			"domain": domain,
			//		"uploadFile": uploadFile,
		};
		console.log("json---" + JSON.stringify(json));

		$.ajax({
			url: "timesheetdetails",
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(json),
			dataType: 'json',
		}).done(function(result) {
			console.log("-------------------------------------------");
			tsTable.clear().draw();
			tsTable.rows.add(result).draw();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			// needs to implement if it fails
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
		})
		
		$.ajax({
			url: "/uploadFile",
			type: "POST",
			data: new FormData(this),
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			cache: false,
			success: function() {
				// Handle upload success
				// ...
			},
			error: function() {
				// Handle upload error
				// ...
			}
		});

	});

	
});










