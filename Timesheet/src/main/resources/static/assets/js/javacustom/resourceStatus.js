$(document).ready(function() {
$('#progress').show();
	$.ajax({
		url: "/admin/resourcestatus",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		console.log(result);
		var rec = result.length;
		for (i = 0; i < rec; i++) {
			if (result[i].enabled) {
				result[i].enabled = "<a href='#'   class='active-btn' id='"+result[i].id+"' onclick='process(this,0);'>Active</a>";
			} else {
				result[i].enabled = "<a href='#' class='inactive-btn' id='"+result[i].id+"' onclick='process(this,1);'>In-active</a>";

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

	Table = $('#resourceStatus').DataTable({
		columns: [
			{ "data": "employeeId" },
			{ "data": "employeeName" },
			{ "data": "email" },
			{ "data": "enabled" },
		],
	});

});

function process(anc,opt){
	event.preventDefault();
	var api = '';
	if(opt == 0){
		api = '/admin/disableuser/'+anc.id;
	}
	if(opt == 1){
		api = '/admin/enableuser/'+anc.id;
	}
	console.log("api : " +api);
	$.ajax({
		url: api,
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		console.log(result);
		alert("sucessfully");
		location.reload();
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
		alert(textStatus);
	});
}
