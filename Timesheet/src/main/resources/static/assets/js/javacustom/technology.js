$(document).ready(function() {

	$.ajax({
		url: "/fetchtechdata",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		console.log(result);
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
	Table = $('#techTable').DataTable({
		columns: [
			{ "data": "createDate" },
			{ "data": "name" },
		],
	});


	$("#techform").submit(function(e) {
		e.preventDefault();
		var techName = $("#techName").val();
		//alert("Hello");

		var json = {
			"techName": techName,

		};

		$.ajax({
			url: "technology",
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(json),
			dataType: 'json',
		}).done(function(result) {
			console.log("------------------result-------------------------");
			console.log(result);
			
			alert(result.name);
			location.reload();
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

});