$(document).ready(function() {
	
});


function appendEmail(source, dest) {
	document.getElementById(dest).style.display = 'block';

	var enteredText = source.value;
	if (enteredText.split('@ojas-it.com').length == 1) {
		enteredText += "@ojas-it.com";
	}

	html = '<a href="#" class="list-group-item list-group-item-action" id="sbox" onclick="get_text(this)">'
		+ enteredText + '</a>';
	document.getElementById(dest).innerHTML = html;
}
function get_text(event) {
	var string = event.textContent;
	document.getElementById('emailId').value = string;
	document.getElementById('emailautofill').innerHTML = '';
	document.getElementById('emailautofill').style.display = 'none';
}

function validation() {
	event.preventDefault();
	$('#errorBoard').val('');
	var res = true;
	var ph = $('#phoneNumber').val();
	res = phonenumber(ph);
	console.log(res);
	return res;
}

function phonenumber(inputtxt) {
	console.log(inputtxt);
	var phoneno = /^\d{10}$/;
	if (inputtxt.match(phoneno)) {
		return true;
	} else {
		var existingErrorMsg = $('#errorBoard').text();
		$('#errorBoard').html(
			existingErrorMsg + "Not a valid Phone Number");
		return false;
	}
}