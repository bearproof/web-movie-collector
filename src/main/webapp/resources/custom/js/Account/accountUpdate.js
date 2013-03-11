(function($, NS, SuperClass,SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
	toString:function(){return NS + '.' + SubClass;},
	rolesToUpdate : [],
	existingRoles : {},
	possibleRoles : [],
	/** Constructor. */
    init : function(cfg) {
      this.bindBehavior(this);
    },
    /**
     * Binds client-side behavior.
     */
    bindBehavior : function() {
    	if($('body').data('role')==="admin"){    		
    		this.getAllRoles();
    		this.initResetButton();
    		this.validateAndSubmitForm();
    	}else{
    		this.initResetButton();
        	this.validateUserDataAndSubmitForm();
    	}
    	$('.container').css({'padding-top': function () {    			
					return ($('div.navbar-fixed-top').height());
				}
    	});  
    },
    /**
     * Retrieves all existing roles from the server and populates a multiple-select list
     * */
    getAllRoles : function(){
    	var that=this;
    	$.ajax({  
  		  type: "GET",  
  		  url: "/domain/roles/getAll",  
  		  contentType: "application/json; charset=utf-8",
  		  success: function(response) {
  			  that.possibleRoles=response;
  			  var i=0, values = $("#rolesList>option").map(function() { 
  				  return $(this).val(); 
  			  });
  			  for(i=0;i<that.possibleRoles.length;i++){
  					 if($.inArray(that.possibleRoles[i].id,values)===-1){
  						 $('#rolesList').append("<option value="+that.possibleRoles[i].id+">"+that.possibleRoles[i].roleName+"</option>");	 
  					 }else{
  						 that.existingRoles[that.possibleRoles[i].id]=that.possibleRoles[i];
  					 } 				  						  
  			  }
  			  
  			  $('.multiselect').multiselect({
  		      buttonClass: 'btn',
  		      buttonWidth: 'auto',
  		      buttonText: function(options) {
  		        if (options.length === 0) {
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
  						  	that.existingRoles[role.id]=role;
  					 }else{
  						 delete that.existingRoles[$(this).val()];						 
  					 }					
  			 });			  
  		  }//success
  	});  	
    	
    },    
    /**
     * Validates the Form before submitting it for users having ROLE_ADMIN
     * */
    validateAndSubmitForm : function(){
    	var that=this;
    	$("#accountEditForm").validate({
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
    				minlength: 5,
    				maxlength: 10,
    				equalTo: "#password"
    			}
    		},
    		messages: {
    			firstName: {required : $('body').data('firstname')},
    			lastName:  {required  : $('body').data('lastname')},
    			password:  {
    				required: $('body').data('password'),
    				minlength: $.format($('body').data('minlength')),
    				maxlength: $.format($('body').data('maxlength'))
    			},
    			password_confirm: {
    				required: $('body').data('passwordconfirm'),
    				minlength: $.format($('body').data('minlength')),
    				maxlength: $.format($('body').data('maxlength')),
    				equalTo: $('body').data('equalto')
    			}
    		},
    		errorPlacement: function(error, element) {
    			error.appendTo( element.next() );
    		},
    		submitHandler: function() {
    			var jsonData = null, index = null;
    			
    			for(index in that.existingRoles){
    				if(that.existingRoles.hasOwnProperty(index)){
    					that.rolesToUpdate.push(that.existingRoles[index]);	
    				}    					
    			}
    			
    			jsonData = {
    				id : $("input#id").val(),
    				firstName : $("input#firstName").val(),
    				lastName : $("input#lastName").val(),
    				password : $("input#password").val(),
    				email : $("input#email").val(),
    				isEnabled : $('input#isEnabled').is(':checked'),
    				roles : that.rolesToUpdate
    			};
    			$.ajax({  
    				  type: "PUT",  
    				  url: "/domain/accounts/"+$("input#id").val(),  
    				  data: JSON.stringify(jsonData),
    				  contentType: "application/json; charset=utf-8",
    				  success: function(response,status,xhr) { 
    					  //shows a confirmation message in a RED div if error===true, else shows it in a BLACK div
    					  $().message(response.message,response.error);
    					  document.location.href='/domain/accounts/list/';
    				  }
    				});  
    			return false; 
    		},
    		success: function(label) {
    		}
    	});
    },
    /**
     * Validates the Form before submitting it for users having ROLE_USER
     * */
    validateUserDataAndSubmitForm : function(){
    	var that=this;
    	$("#accountEditForm").validate({
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
    			var jsonData = null, index = null;
    			
    			for(index in that.existingRoles){
    				if(that.existingRoles.hasOwnProperty(index)){
    					that.rolesToUpdate.push(that.existingRoles[index]);	   					
    				}
    			}
    			
    			jsonData = {
    				id : $("input#id").val(),
    				firstName : $("input#firstName").val(),
    				lastName : $("input#lastName").val(),
    				password : $("input#password").val(),
    				email : $("input#email").val(),
    				isEnabled : $('input#isEnabled').is(':checked'),
    				roles : that.rolesToUpdate
    			};
    			$.ajax({  
    				  type: "PUT",  
    				  url: "/changeDetails/updateUserAccount/",  
    				  data: JSON.stringify(jsonData),
    				  contentType: "application/json; charset=utf-8",
    				  success: function(response,status,xhr) { 
    					  document.location.href='/';
    				  }
    				});  
    			return false; 
    		},
    		success: function(label) {
    		}
    	});
    },
    /**
     * Binds the onClick event on the Cancel button
     * */
    initResetButton : function (){
    	var that=this;
    	$("#resetButton").click(function() {
    		$("input#firstName").val($("input#firstName").attr("data-reset"));
    		$("input#lastName").val($("input#lastName").attr("data-reset"));
    		$("input#password").val($("input#password").attr("data-reset"));
    		$("input#password_confirm").val($("input#password").attr("data-reset"));
    		  return false;
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","AccountUpdate"));