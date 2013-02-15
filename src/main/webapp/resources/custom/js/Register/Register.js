(function($, NS, SuperClass,SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
	toString:function(){return NS + '.' + SubClass;},
	/** Constructor. */
    init : function(cfg) {
      this.bindBehavior(this);
    },
    /**
     * Binds client-side behavior.
     */
    bindBehavior : function() {
    	this.initCancelButton();
    	this.validateAndSubmitForm();
    },   
    /**
     * Validates the Form before submitting it
     * */
    validateAndSubmitForm : function(){
    	var that=this;
    	$("#registerForm").validate({
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
    				email: true,
    				remote: "/validator/checkemail"
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
    			
    			var jsonData = {
    				firstName : $("input#firstName").val(),
    				lastName : $("input#lastName").val(),
    				password : $("input#password").val(),
    				email : $("input#email").val()
    			};
    			$.ajax({  
    				  type: "POST",  
    				  url: "/register",  
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
    initCancelButton : function (){
    	var that=this;
    	$("#cancelButton").click(function() {
    		document.location.href='/login';
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","Register"));