var possibleRoles=[], existingRoles = {}, rolesToUpdate = [];
$(document).ready(function() {
	
	$.ajax({  
		  type: "GET",  
		  url: "/domain/roles/getAll",  
		  contentType: "application/json; charset=utf-8",
		  success: function(response) {
			  possibleRoles=response;
			  var i=0, values = $("#rolesList>option").map(function() { 
				  return $(this).val(); 
			  });
			  for(i=0;i<possibleRoles.length;i++){
					 if($.inArray(possibleRoles[i].id,values)===-1){
						 $('#rolesList').append("<option value="+possibleRoles[i].id+">"+possibleRoles[i].roleName+"</option>");	 
					 }else{
						 existingRoles[possibleRoles[i].id]=possibleRoles[i];
					 } 				  						  
			  }
			  
			  $('.multiselect').multiselect({
		      buttonClass: 'btn',
		      buttonWidth: 'auto',
		      buttonText: function(options) {
		        if (options.length == 0) {
		          return 'None selected <b class="caret"></b>';
		        }
		        else if (options.length > 4) {
		          return options.length + ' selected  <b class="caret"></b>';
		        }
		        else {
		          var selected = '';
		          options.each(function() {
		            selected += $(this).text() + ', ';
		          });
		          return selected.substr(0, selected.length -2) + ' <b class="caret"></b>';
		        }
		      }
			 });
			  $('ul li input[type="checkbox"]').on('change', function(e){
			   	var checked = $(e.target).prop('checked') || false, role={};			   	
					if(checked){
						  	role.id=$(this).val();
						  	role.roleName = $.trim($(this).parents('label').text());
						  	existingRoles[role.id]=role;
					 }else{
						 delete existingRoles[$(this).val()];						 
					 }					
			 });			  
		  }//success
	});  	
	var validator = $("#accountEditForm").validate({
		rules: {
			firstName: "required",
			lastName: "required",
			password: {
				required: true,
				minlength: 5,
				maxlength: 10
			},
			password_confirm: {
				required: true,
				equalTo: "#password"
			}
		},
		messages: {
			firstName: "Enter your firstname",
			lastName: "Enter your lastname",
			password: {
				required: "Provide a password",
				minlength: jQuery.format("Enter at least {0} characters"),
				maxlength: jQuery.format("Enter a maximum of {0} characters")
			},
			password_confirm: {
				required: "Repeat your password",
				equalTo: "Enter the same password as above"
			}
		},
		errorPlacement: function(error, element) {
			error.appendTo( element.next() );
		},
		submitHandler: function() {
			
			for(var index in existingRoles){
				rolesToUpdate.push(existingRoles[index]);	
			}
			
			var jsonData = {
				id : $("input#id").val(),
				firstName : $("input#firstName").val(),
				lastName : $("input#lastName").val(),
				password : $("input#password").val(),
				email : $("input#email").val(),
				isEnabled : $('input#isEnabled').is(':checked'),
				roles : rolesToUpdate
			};
			$.ajax({  
				  type: "PUT",  
				  url: "/domain/accounts/"+$("input#id").val(),  
				  data: JSON.stringify(jsonData),
				  contentType: "application/json; charset=utf-8",
				  success: function(response,status,xhr) { 
					  document.location.href='/domain/accounts/list';
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
		$("input#firstName").val($("input#firstName").attr("data-reset"));
		$("input#lastName").val($("input#lastName").attr("data-reset"));
		$("input#password").val($("input#password").attr("data-reset"));
		$("input#password_confirm").val($("input#password").attr("data-reset"));
		  return false;
	});	
}


