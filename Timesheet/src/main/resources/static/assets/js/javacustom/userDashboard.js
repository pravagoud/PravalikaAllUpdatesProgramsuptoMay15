$(document).ready(function() {
	
$.ajax({
		url: "/user/approvedstatuscountuser",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		console.log(result);
		$('#uas').text(result);
		
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
		$('#uas').text('0');
		
	});
	
	
	$.ajax({
		url: "/user/pendingstatuscountuser",
		type: "get",
		contentType: "application/json",
		dataType: 'json',
	}).done(function(result) {
		console.log("------------------result----al---------------------");
		console.log(result);
		$('#ups').text(result);
		
	}).fail(function(jqXHR, textStatus, errorThrown) {
		// needs to implement if it fails
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
		$('#ups').text('0');
		
	});
	
});