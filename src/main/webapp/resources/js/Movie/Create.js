var rolesToUpdate = [], selectedRoles={}, allExistingRoles=[];

$(document).ready(function() {

	$.ajax({  
		  type: "GET",  
		  url: "/domain/roles/getAll",  
		  contentType: "application/json; charset=utf-8",
		  success: function(response) {
			  
			  allExistingRoles=response;
			  for(var i=0;i<allExistingRoles.length;i++){
				  $('#rolesList').append("<option value="+allExistingRoles[i].id+">"+allExistingRoles[i].roleName+"</option>");						  
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
						  	selectedRoles[role.id]=role;
					}else{
						 delete selectedRoles[$(this).val()];						 
					}					
			  });		
		  }//success
	});  	
	var validator = $("#accountForm").validate({
		rules: {
			firstName: "required",
			lastName: "required",
			password: {
				required: true,
				minlength: 5,
				maxlength:10
			},
			password_confirm: {
				required: true,
				minlength: 5,
				maxlength: 10,
				equalTo: "#password"
			},
			email: {
				required: true,
				email: true
				,remote: "/validator/checkemail"
			}
		},
		messages: {
			firstName: {required : "Enter your firstname"},
			lastName:  {required  : "Enter your lastname"},
			password:  {
				required: "Provide a password",
				minlength: jQuery.format("Enter at least {0} characters"),
				maxlength: jQuery.format("Enter a maximum of {0} characters")
			},
			password_confirm: {
				required: "Repeat your password",
				equalTo: "Enter the same password as above"
			},
			email: {
				required: "Please enter a valid email address",
				minlength: "Please enter a valid email address"
				,remote: jQuery.format("{0} is already in use")
			}
		},
		errorPlacement: function(error, element) {
			error.appendTo(element.next());
		},
		submitHandler: function() {
			
			for(var index in selectedRoles){
				rolesToUpdate.push(selectedRoles[index]);	
			}
			var jsonData = {
				firstName : $("input#firstName").val(),
				lastName : $("input#lastName").val(),
				password : $("input#password").val(),
				email : $("input#email").val(),
				isEnabled : $('input#isEnabled').is(':checked'),
				roles : rolesToUpdate
			};
			$.ajax({  
				  type: "POST",  
				  url: "/domain/accounts/",  
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


