var rest = "/rest/";
var site = "/site/";

function createRefObject(formId, activity) {
	var formData = $(formId)
		.toObject({
		mode: 'first'
	});
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rest + activity,
		dataType: "json",
		data: JSON.stringify(formData, null, '\t'),
		success: function(data, textStatus, jqXHR) {
			alert('[[model.identifier]] created successfully');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('[[model.identifier]] creation error: ' + textStatus);
		}
	});
};

function findAll(templateId, activity,applyTo) {
	var uri = rest + activity + '/';
	$.getJSON(uri, function(data) {
		var user_data = {
			response: data
		};
		var template = jQuery(templateId)
			.html();
		var renderedData = Mustache.render(template, user_data);
		jQuery(applyTo)
			.html(renderedData);
	});
};

function synchronizeBookmaker(bookmaker) {
	var uri = rest +'admin/synchronize/?bookmaker='+bookmaker;
	$.getJSON(uri, function(data) {
		alert(JSON.stringify(data));
	});
};

function deleteRefObject(activity, id) {
	$.ajax({
		type: 'DELETE',
		url: rest + activity + '/' + id,
		success: function(data, textStatus, jqXHR) {
			alert('[[model.identifier]] created successfully');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert('[[model.identifier]] creation error: ' + textStatus);
		}
	});
};
