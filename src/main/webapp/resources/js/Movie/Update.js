$(document).ready(function() {	
	var validator = $("#movieEditForm").validate({
		rules: {
			title: "required",
			year: "required"			
		},
		messages: {
			title: {required : "Enter a movie title"},
			year:  {required  : "Enter the release year"}
		},
		errorPlacement: function(error, element) {
			error.appendTo( element.next() );
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
				ownMovieNotes : $('textarea#ownMovieNotes').val()
			};
			$.ajax({  
				  type: "PUT",  
				  url: "/domain/movies/"+$("input#id").val(),  
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
	// Handling reset button
	resetButtonHandler();
});


function resetButtonHandler(){
	$("#resetButton").click(function() {
		$("input#id").val($("input#id").attr("data-reset"));
		$("input#title").val($("input#title").attr("data-reset"));
		$("input#year").val($("input#year").attr("data-reset"));
		$("input#genre").val($("input#genre").attr("data-reset"));
		$("input#director").val($("input#director").attr("data-reset"));
		$("input#runtime").val($("input#runtime").attr("data-reset"));
		  return false;
	});	
}


