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
    	this.initResetButton();
    	this.validateUserDataAndSubmitForm();
    },    
    /**
     * Validates the Form before submitting it
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
}(jQuery, "WMC","Base","UserAccountUpdate"));