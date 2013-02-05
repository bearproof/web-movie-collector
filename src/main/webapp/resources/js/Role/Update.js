$(document).ready(function() {
	var validator = $("#roleEditForm").validate({
		rules: {
			roleName: "required"
		},
		messages: {
			roleName: "Enter a role name"
		},
		errorPlacement: function(error, element) {
			error.appendTo( element.next() );
		},
		submitHandler: function() {
			var jsonData = {
				id : $("input#id").val(),
				roleName : $("input#roleName").val()
			};
			$.ajax({  
				  type: "PUT",  
				  url: "/domain/roles/"+$("input#id").val(),  
				  data: JSON.stringify(jsonData),
				  contentType: "application/json; charset=utf-8",
				  success: function(response,status,xhr) { 
					  document.location.href='/domain/roles/list';
				  }
				});  
			return false; 
		},
		success: function(label) {
		}
	});
	// Handling reset button
	resetButtonHandler();
});


function resetButtonHandler(){
	$("#resetButton").click(function() {
		$("input#roleName").val($("input#roleName").attr("data-reset"));
		  return false;
	});	
}



