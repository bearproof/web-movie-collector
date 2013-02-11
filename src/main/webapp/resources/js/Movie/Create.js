var rolesToUpdate = [], selectedRoles={}, allExistingRoles=[];

$(document).ready(function() {
	
	var validator = $("#movieForm").validate({
		rules: {
			title: "required",
			year: "required"			
		},
		messages: {
			title: {required : "Enter a movie title"},
			year:  {required  : "Enter the release year"}			
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next());
		},
		submitHandler: function() {
						
			var jsonData = {
				title : $("input#title").val(),
				year : $("input#year").val(),
				genre : $("input#genre").val(),
				director : $("input#director").val(),
				cast : $('textarea#cast').val(),
				description : $('textarea#description').val(),
				runtime : $('input#runtime').val(),
				userRating : $('select#userRating option:selected').val(),
				movieStatus : $('select#movieStatus option:selected').val(),
				ownMovieNotes : $('textarea#ownMovieNotes').val(),
			};
			$.ajax({  
				  type: "POST",  
				  url: "/domain/movies/",  
				  data: JSON.stringify(jsonData),
				  contentType: "application/json; charset=utf-8",
				  success: function(response,status,xhr) {
					  document.location.href='/domain/movies/list';
				  }
				});  
			return false; 
		},
		success: function(label) {
		}
	});
	cancelButtonHandler();
		
});

/**
 * Cancel button handler
 */
function cancelButtonHandler(){
	$("#cancelButton").click(function() {
		document.location.href='/domain/accounts/list';
	});	
}


