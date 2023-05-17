$(document).ready(function() {

	$("#pmoapproved").submit(function(e) {
		e.preventDefault();
		approveddataFetch();
	});

	$("#financialpendingStatus").submit(function(e) {
		e.preventDefault();
		finacePendingDataFetch();
	});
	$("#allrequestapproved").submit(function(e) {
		e.preventDefault();
		finaceAprovedDataFetch();
	});
	$('#closebox').click(function(e) {
		e.preventDefault();
		$("#emailForm").trigger("reset");
		$("#email-modal").modal('hide');
		return false;
	});

	$("#emailForm").submit(function(e) {
		e.preventDefault();
		$('#email-modal').modal("hide");
		var apiUrl = '';
		let timeSheetId = $('#stid').val();
		let feedback = $('textarea#message').val();
		var option = parseInt($('#actionOption').val());
		//	console.log(option+"Akshaya"+feedback.trim().length);
		if (feedback.trim().length == 0) {
			if (option == 1) {
				feedback = "Approved Without Feedback.";
			}
			if (option == 0) {
				feedback = "Rejected Without Feedback. Contact PMO Team.";
			}
		}

		if (option == 1) {
			apiUrl = "/financialexecutive/fxapproval/" + timeSheetId + '/' + feedback;
		}
		if (option == 0) {
			apiUrl = "/financialexecutive/fxreject/" + timeSheetId + '/' + feedback;
		}

		$.ajax({
			url: apiUrl,
			type: "get",
			contentType: "application/json",
			dataType: 'json',
		}).done(function(result) {
			///		console.log("------------------result-------------------------");
			//		console.log(result.status);
			if (result.status === 'TA') {
				$('#result').text(result.empName + ' Timesheet Approved');
			}
			if (result.status === 'TR') {
				$('#result').text(result.empName + ' Timesheet Rejected');
			}
			approveddataFetch();
			finacePendingDataFetch();
			openOutLook(result, option);
			location.reload();
		}).fail(function(jqXHR, textStatus, errorThrown) {
			// needs to implement if it fails
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
			$('#result').text(jqXHR.responseText);
		})

	});


});

function approveddataFetch() {
	var start = $("#startdate").val();
	var end = $("#enddate").val();

	//	console.log(start);
	//	console.log(end);

	if (Date.parse(start) > Date.parse(end)) {
		alert("Invalid Date Range");
	}
	else {
		//alert("Valid date Range");
	}

	var json = {
		"start": start,
		"end": end,
	};
	console.log(json);
	$.ajax({
		url: "pmoapproved",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result-------------------------");
		for (var i = 0; i < result.length; i++) {
			console.log(result[i]);
			if (result[i].ttid.length > 0) {
				var fns = result[i].ttfn.split(',');
				var pp = result[i].ttid.split(',');
				var ttid_str = "";
				for (var k = 0; k < pp.length; k++) {
					if (fns[k].toLowerCase().endsWith(".pdf")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PDF ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".png")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PNG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpeg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPEG ' + (k + 1) + '</a><br/>';
					}
					else {
						ttid_str += '<a href="/getfile/' + pp[k] + '">XLS ' + (k + 1) + '</a><br/>';
					}
				}
				result[i].ttid = ttid_str;
			}
			if (result[i].status.length > 0) {
				if (result[i].status === 'PA') {
					result[i].status = '<a href="#" id="' + result[i].id + '" onclick="processApproval(this,1);"/>Approve</a>';
					result[i].status += '/';
					result[i].status += '<a href="#" id="' + result[i].id + '" onclick="processApproval(this,0);"/>Reject</a>';

				}
			}

			if (result[i].startDate.length > 0) {
				var int_sch_date = (result[i].startDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].startDate = formatDateAndTime(d);
			}
			if (result[i].endDate.length > 0) {
				var int_sch_date = (result[i].endDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].endDate = formatDateAndTime(d);
			}
			console.log(result[i]);

		}

		fdTable.clear().draw();
		fdTable.rows.add(result).draw();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	})

	$("#approveddatefilter").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#approveddatefilter').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();


	fdTable = $('#approveddatefilter').DataTable({
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "createDate" },
			{ "data": "modifyDate" },
			{ "data": "startDate" },
			{ "data": "endDate" },
			{ "data": "shift" },
			{ "data": "timing" },
			{ "data": "phoneNo" },
			{ "data": "email" },
			{ "data": "ttid" },
			{ "data": "status" },
		],
	});
}

function processApproval(source, option) {
	var timeSheetId = source.id;
	$('#stid').val(timeSheetId);
	$('#actionOption').val(option);
	$('#email-modal').modal("show");
}
function formatDateAndTime(date) {
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var ampm = hours >= 12 ? 'pm' : 'am';
	hours = hours % 12;
	hours = hours ? hours : 12; // the hour '0' should be '12'
	minutes = minutes < 10 ? '0' + minutes : minutes;
	var strTime = ", " + hours + ':' + minutes + ' ' + ampm;
	//return [padTo2Digits(date.getMonth() + 1),padTo2Digits(date.getDate()),date.getFullYear(),].join('/') + strTime;

	return [
		padTo2Digits(date.getMonth() + 1),
		padTo2Digits(date.getDate()),
		date.getFullYear(),
	].join('/');
}
function padTo2Digits(num) {
	return num.toString().padStart(2, '0');
}

function formatDate(date) {
	return (
		[
			padTo2Digits(date.getMonth() + 1),
			padTo2Digits(date.getDate()),
			date.getFullYear(),
		].join('/') +
		' ' +
		[
			padTo2Digits(date.getHours()),
			padTo2Digits(date.getMinutes()),
			padTo2Digits(date.getSeconds()),
		].join(':')
	);
}

function openOutLook(to, option) {
	var username = sessionStorage.getItem("employeename");
	var uname = $("#username").val();
	console.log("uname" + uname);
	var comment = $("textarea#message").val();

	console.log("outlook" + to);
	var formattedBody = "";
	var sub = "";
	const sd = new Date(to.startDate);
	const ed = new Date(to.endDate);
	if (option == 1) {
		sub = "Your Timesheet request has been approved ";
		formattedBody = "Hi " + to.empName +
			",\n\n" + username +
			" has approved your timesheet request from " +
			formatDateAndTime(sd) +
			" to " + formatDateAndTime(ed) +
			"\n\n Comment : " + to.feedback +
			"\n\n\n\n Regards, \n Team Ojas Innovative Technologies ";
	}
	if (option == 0) {
		sub = "Your Timesheet request has been rejected ";
		formattedBody = "Hi " + to.empName +
			",\n\n" + username +
			" has rejected your timesheet request from " +
			formatDateAndTime(sd) +
			" to " + formatDateAndTime(ed) +
			"\n\n Comment: " + to.feedback +
			"\n\n\n\n Regards, \n Team Ojas Innovative Technologies ";
	}
	var domain = 'ojas-it.com';

	var to = 'pmo.support@ojas-it.com';
	var mailToLink = "mailto:" + to + "?subject=" + sub + "&body=" +
		encodeURIComponent(formattedBody);
	window.location.href = mailToLink;
}



function finacePendingDataFetch() {
	var start = $("#startdate").val();
	var end = $("#enddate").val();

	//	console.log(start);
	//	console.log(end);

	if (Date.parse(start) > Date.parse(end)) {
		alert("Invalid Date Range");
	}
	else {
		//alert("Valid date Range");
	}

	var json = {
		"start": start,
		"end": end,
	};
	console.log(json);
	$.ajax({
		url: "financialpendingStatus",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result-------------------------");
		for (var i = 0; i < result.length; i++) {
			console.log(result[i]);
			if (result[i].ttid.length > 0) {
				var fns = result[i].ttfn.split(',');
				var pp = result[i].ttid.split(',');
				var ttid_str = "";
				for (var k = 0; k < pp.length; k++) {
					if (fns[k].toLowerCase().endsWith(".pdf")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PDF ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".png")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PNG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpeg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPEG ' + (k + 1) + '</a><br/>';
					}
					else {
						ttid_str += '<a href="/getfile/' + pp[k] + '">XLS ' + (k + 1) + '</a><br/>';
					}
				}
				result[i].ttid = ttid_str;
			}
			if (result[i].status.length > 0) {
				if (result[i].status === 'FP') {
					result[i].status = '<a href="#" id="' + result[i].id + '" onclick="processApproval(this,1);"/>Approve</a>';
					result[i].status += '/';
					result[i].status += '<a href="#" id="' + result[i].id + '" onclick="processApproval(this,0);"/>Reject</a>';

				}
			}

			if (result[i].startDate.length > 0) {
				var int_sch_date = (result[i].startDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].startDate = formatDateAndTime(d);
			}
			if (result[i].endDate.length > 0) {
				var int_sch_date = (result[i].endDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].endDate = formatDateAndTime(d);
			}
			console.log(result[i]);

		}

		fdTable.clear().draw();
		fdTable.rows.add(result).draw();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	})

	$("#approveddatefilter").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#approveddatefilter').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();


	fdTable = $('#approveddatefilter').DataTable({
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "createDate" },
			{ "data": "modifyDate" },
			{ "data": "startDate" },
			{ "data": "endDate" },
			{ "data": "shift" },
			{ "data": "timing" },
			{ "data": "phoneNo" },
			{ "data": "email" },
			{ "data": "ttid" },
			{ "data": "status" },
		],
	});
}

function finaceAprovedDataFetch() {
	var start = $("#startdate").val();
	var end = $("#enddate").val();

	//	console.log(start);
	//	console.log(end);

	if (Date.parse(start) > Date.parse(end)) {
		alert("Invalid Date Range");
	}
	else {
		//alert("Valid date Range");
	}

	var json = {
		"start": start,
		"end": end,
	};
	console.log(json);
	$.ajax({
		url: "allrequestapproved",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result-------------------------");
		for (var i = 0; i < result.length; i++) {
			console.log(result[i]);
			if (result[i].ttid.length > 0) {
				var fns = result[i].ttfn.split(',');
				var pp = result[i].ttid.split(',');
				var ttid_str = "";
				for (var k = 0; k < pp.length; k++) {
					if (fns[k].toLowerCase().endsWith(".pdf")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PDF ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".png")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">PNG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPG ' + (k + 1) + '</a><br/>';
					}
					else if (fns[k].toLowerCase().endsWith(".jpeg")) {
						ttid_str += '<a href="#" id=a' + k + ' title="/getfile/' + pp[k] + '" onclick="processLink(this);">JPEG ' + (k + 1) + '</a><br/>';
					}
					else {
						ttid_str += '<a href="/getfile/' + pp[k] + '">XLS ' + (k + 1) + '</a><br/>';
					}
				}
				result[i].ttid = ttid_str;
			}
			if (result[i].status.length > 0) {
				if (result[i].status === 'FA') {
					result[i].status = 'Approve';
				}
			}

			if (result[i].startDate.length > 0) {
				var int_sch_date = (result[i].startDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].startDate = formatDateAndTime(d);
			}
			if (result[i].endDate.length > 0) {
				var int_sch_date = (result[i].endDate);
				int_sch_date = int_sch_date.split('.')[0];
				var withoutOffset = moment(int_sch_date).local().format("YYYY-MM-DD");
				const d = new Date(withoutOffset);
				result[i].endDate = formatDateAndTime(d);
			}
			console.log(result[i]);

		}

		fdTable.clear().draw();
		fdTable.rows.add(result).draw();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	})

	$("#approveddatefilter").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#approveddatefilter').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();


	fdTable = $('#approveddatefilter').DataTable({
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "createDate" },
			{ "data": "modifyDate" },
			{ "data": "startDate" },
			{ "data": "endDate" },
			{ "data": "shift" },
			{ "data": "timing" },
			{ "data": "phoneNo" },
			{ "data": "email" },
			{ "data": "ttid" },
			{ "data": "status" },
		],
	});
}