

const URL_CHAT = "http://localhost:8080/ask/agent";
const HEADER = {'Content-Type': 'application/json'};

let rag_basic = false;

$(document).ready(function () {
	$('#inputTemp').val("1");

    $('#send').click(function (event) {
		let txt = "Subject:  " + $('#subject').val();
		txt = txt + " Message:  " + $('#message').val();
		$('#messageFeed').empty();
		console.log("Text: "+ txt);	
		if (txt.length == 0) {
			prompt = "Help";
		}
		else {
			prompt = txt;
		}
        get_chat(prompt, populateMessage);

	});

	$('#ticket-1').click(function (event) {
		ticket_1();
	});
	$('#ticket-2').click(function (event) {
		ticket_2();
	});
	$('#ticket-3').click(function (event) {
		ticket_3();
	});
});

const populateMessage = (data) => {
	console.log(data);
	var txt = "'"+ data.selection + "' chosen from " + data.routes + ". " + data.reasoning;
	addReasonPoint('#messageFeed', txt);
	addAnswerPoint('#messageFeed', data.response);
}

function addReasonPoint(tag, point) {
	$(tag).append('<li class = "list-group-item list-group-item-success"><b>Route Reasoning: </b>' + point + '</li>');
}

function addAnswerPoint(tag, point) {
	$(tag).append('<li class = "list-group-item list-group-item-light"><md-block>' + point + '</md-block></li>');
}

function ticket_1() {
	document.getElementById('subject').value = "How to export Excel data?";
	document.getElementById('message').value = 
	"I need to export all my project data to Excel. I've looked through the docs but can't " +
	"figure out how to do a bulk export. Is this possible? If so, could you walk me through the steps? " +
	"Best regards, Mike";
}
function ticket_2(tag, point) {
	document.getElementById('subject').value = 	"Unexpected charge on my card.";
	document.getElementById('message').value = 
	"Hello, I just noticed a charge of 2.99 on my credit card from your company, but I thought " +
	"I was on the 2.99 plan. Can you explain this charge and adjust it if it's a mistake? " +
	"Thanks, Sarah";
}
function ticket_3(tag, point) {
	document.getElementById('subject').value = "Can't access my account";
	document.getElementById('message').value =
	"Hi, I've been trying to log in for the past hour but keep getting an 'invalid password' error. " +
	"I'm sure I'm using the right password. Can you help me regain access? This is urgent as I need to" +
	"submit a report by end of day. - John"
}

// local chat API
function get_chat(question, callfunction) {
	fetch(URL_CHAT, {
		method: 'POST',
		headers: HEADER,
		body: JSON.stringify({
			"ticket": question,
		})
	})
	.then(response => response.json())
	.then(data => callfunction(data))
	.catch(error => console.error(error))
}
