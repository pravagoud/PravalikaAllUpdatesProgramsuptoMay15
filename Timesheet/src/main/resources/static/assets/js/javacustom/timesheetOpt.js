$(document).ready(function() {
	$('#progress').show();
	process();
	Table = $('#userdetailstable').DataTable({
		columns: [
			{ "data": "empId" },
			{ "data": "empName" },
			{ "data": "clientName" },
			{ "data": "createDate" },
			{ "data": "modifyDate" },
			{ "data": "startDate" },
			{ "data": "endDate" },
			{ "data": "ttid" },
			{ "data": "status" }
		],
	});
	//  find by user details

	$.ajax({
		url: "/user/usertimesheet",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		//		console.log(result);
		for (var i = 0; i < result.length; i++) {
			console.log(result[i].length);

			if (result[i].status === 'P') {
				result[i].status = 'PMO-Pending';
			}
			else if (result[i].status === 'PA') {
				result[i].status = 'PMO_Approved';
			}
			else if (result[i].status === 'PR') {
				result[i].status = 'PMO-Rejected';
			}
			else if (result[i].status === 'FA') {
				result[i].status = 'Finance Approved';
			}
			else if (result[i].status === 'FR') {
				result[i].status = 'Finance Rejected';
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
		}

		Table.clear().draw();
		Table.rows.add(result).draw();
		$('#progress').hide();

	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
		$('#progress').hide();
	})





});
function processLink(src) {
	var id = src.id;
	var fn = src.title;
	// Create anchor element.
	var a = document.createElement('a');
	a.setAttribute('target', '_blank');

	a.href = fn;
	a.click();
}
function process() {
	var un = document.getElementById('un').value;
	//	console.log("user name is :" + un);

	$.ajax({
		url: "/user/getUserTimeSheetDetails/" + un,
		type: "GET",
		contentType: "application/json",
		dataType: 'json',
		success: function(data, state) {
			$('#progress').hide();
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

function processLink(src) {
	var id = src.id;
	var fn = src.title;
	// Create anchor element.
	var a = document.createElement('a');
	a.setAttribute('target', '_blank');

	a.href = fn;
	a.click();
}
