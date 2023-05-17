$(document).ready(function() {

	$("#dateform").submit(function(e) {
		e.preventDefault();
		dataFetch();
	});
	$("#approveddateform").submit(function(e) {
		e.preventDefault();
		approveddataFetch();
	});

	$("#dateform1").submit(function(e) {
		e.preventDefault();
		dataFetch1();
	});
	//	$("#closebox").close();
	
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
			apiUrl = "/admin/approval/" + timeSheetId + '/' + feedback;
		}
		if (option == 0) {
			apiUrl = "/admin/reject/" + timeSheetId + '/' + feedback;
		}

		$.ajax({
			url: apiUrl,
			type: "get",
			contentType: "application/json",
			dataType: 'json',
		}).done(function(result) {
	///		console.log("------------------result-------------------------");
	//		console.log(result.status);
			if (result.status === 'A') {
				$('#result').text(result.empName + ' Timesheet Approved');
			}
			if (result.status === 'R') {
				$('#result').text(result.empName + ' Timesheet Rejected');
			}
			dataFetch();
			openOutLook(result, option);
		}).fail(function(jqXHR, textStatus, errorThrown) {
			// needs to implement if it fails
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
			$('#result').text(jqXHR.responseText);
		})

	});


});

function dataFetch() {
	var start = $("#startdate").val();
	var end = $("#enddate").val();
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
//	console.log(json);
	$.ajax({
		url: "filterdate",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
//		console.log("------------------result-------------------------");
//		console.log(result);
		for (var i = 0; i < result.length; i++) {
			console.log(result[i]);
			props = getKeys(result[i]);
			if (props.length == 11) {
				props.push("shift");
				props.push("timing");
			}
			if (props.length == 13) {

			}
			for (var j = 0; j < props.length; j++) {
				try {
					var val = result[i][props[j]];
					if (typeof val == 'number') {
						if (val.toString().length == 0) {
							result[i][props[j]] = 'NA';
						}
					}
					if (typeof val == 'string') {
						if ("" + val.length == 0) {
							result[i][props[j]] = 'NA';
						}
					}
					//console.log(val + "----------" + ("" + val.length) + '===========' + (typeof val));
					console.log(val.length);

				} catch (err) {
					result[i][props[j]] = 'NA';
				}
			}
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
				if (result[i].status === 'P') {
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

	$("#datefilter").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#datefilter').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();


	fdTable = $('#datefilter').DataTable({
		dom: 'Bfrtip',
		buttons: [
			{
				extend: 'copy',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'csv',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'excel',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'pdf',
				filename: fn,
				messageTop: fn,
				title: null,

			},
			{
				extend: 'print',
				filename: fn,
				messageTop: fn,
				title: null,
			}
		],
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "createDate" },
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
		url: "filterapproveddate",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
//		console.log("------------------result-------------------------");
//		console.log(result);
		
		for (var i = 0; i < result.length; i++) {
			console.log(result[i].length);
			if (result[i].status === 'P') {
				result[i].status = 'Pending';
			}
			if (result[i].status === 'A') {
				result[i].status = 'Approved';
			}
		}

		for (var i = 0; i < result.length; i++) {
			console.log(result[i]);
			props = getKeys(result[i]);
			if (props.length == 11) {
				props.push("shift");
				props.push("timing");
			}
			if (props.length == 13) {

			}
			for (var j = 0; j < props.length; j++) {
				try {
					var val = result[i][props[j]];
					if (typeof val == 'number') {
						if (val.toString().length == 0) {
							result[i][props[j]] = 'NA';
						}
					}
					if (typeof val == 'string') {
						if ("" + val.length == 0) {
							result[i][props[j]] = 'NA';
						}
					}
					//console.log(val + "----------" + ("" + val.length) + '===========' + (typeof val));
					console.log(val.length);

				} catch (err) {
					result[i][props[j]] = 'NA';
				}
			}
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

	$("#datefilter2").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#datefilter2').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();


	fdTable = $('#datefilter2').DataTable({
		dom: 'Bfrtip',
		buttons: [
			{
				extend: 'copy',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'csv',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'excel',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'pdf',
				filename: fn,
				messageTop: fn,
				title: null,

			},
			{
				extend: 'print',
				filename: fn,
				messageTop: fn,
				title: null,
			}
		],
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


function processDownload(source) {
	var file_Id = source.id;
	$.ajax({
		url: "/getfile/" + file_Id,
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
	//	console.log("-----------------processDownload-------------------------");
//		console.log(result.status);
		if (result.status === 'A') {
			$('#result').text(result.empName + ' Timesheet Approved');
		}
		dataFetch();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
		$('#result').text(jqXHR.responseText);
	})

}

function processApproval(source, option) {
	var timeSheetId = source.id;
	$('#stid').val(timeSheetId);
	$('#actionOption').val(option);
	$('#email-modal').modal("show");
}



function openOutLook(to, option) {
	var username = sessionStorage.getItem("employeename");
//	var uname = $("#username").val();
//	console.log("uname"+uname);
	var comment = $("textarea#message").val();

//	console.log("outlook" + to);
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

	var to = to.email;
	var mailToLink = "mailto:" + to + "?subject=" + sub + "&body=" +
		encodeURIComponent(formattedBody);
	window.location.href = mailToLink;
}

var getKeys = function(obj) {
	var keys = [];
	for (var key in obj) {
		keys.push(key);
	}
	return keys;
}




function dataFetch1() {
	var start = $("#startdate").val();
	var end = $("#enddate").val();
	var json = {
		"start": start,
		"end": end,
	};
	$.ajax({
		url: "filterdate1",
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(json),
		dataType: 'json',
	}).done(function(result) {
//		console.log("------------------dataFetch1-------------------------");
		for (var i = 0; i < result.length; i++) {
			console.log(result[i].length);
			if (result[i].status === 'P') {
				result[i].status = 'Pending';
			}
			if (result[i].status === 'A') {
				result[i].status = 'Approved';
			}
		}
		fd1Table.clear().draw();
		fd1Table.rows.add(result).draw();

	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	})

	$("#datefilter1").dataTable().fnDestroy();

	var currentdate = new Date();
	var fn = $('#datefilter1').data('export-title') + " Generated on " + currentdate.getDate() + "-" + (currentdate.getMonth() + 1) + "-" + currentdate.getFullYear() + " " + currentdate.getHours() + "_" + currentdate.getMinutes() + "_" + currentdate.getSeconds();

	fd1Table = $('#datefilter1').DataTable({
		dom: 'Bfrtip',
		buttons: [
			{
				extend: 'copy',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'csv',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'excel',
				filename: fn,
				messageTop: fn,
				title: null,
			},
			{
				extend: 'pdf',
				filename: fn,
				messageTop: fn,
				title: null,

			},
			{
				extend: 'print',
				filename: fn,
				messageTop: fn,
				title: null,
			}
		],
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "startDate" },
			{ "data": "endDate" },
			{ "data": "shift" },
			{ "data": "timing" },
			{ "data": "phoneNo" },
			{ "data": "email" },
			{ "data": "status" },
		],

	});

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
function processLink(src) {
	var id = src.id;
	var fn = src.title;
	// Create anchor element.
	var a = document.createElement('a');
	a.setAttribute('target', '_blank');

	a.href = fn;
	a.click();
}

