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
    				maxlength:15
    			},
    			password_confirm: {
    				required: true,
    				minlength: 5,
    				maxlength: 15,
    				equalTo: "#password"
    			},
    			email: {
    				required: true,
    				email: true,
    				remote: "/validator/checkemail"
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
    			},
    			email: {
    				required: $('body').data('email'),
    				email: $('body').data('email'),
    				remote: $.format($('body').data('remote'))
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

	$('#password').bind("click", function () {
		$(this).stop()
		.animate({ left: "-10px" }, 100).animate({ left: "10px" }, 100)
		.animate({ left: "-10px" }, 100).animate({ left: "10px" }, 100)
		.animate({ left: "0px" }, 100);		
		$(".password-meter").removeClass("hide").hide().slideDown('slow');
	});
	
	$('#password').bind("blur", function () {
		$(".password-meter").slideUp('slow').addClass("hide");
	});	
	
	/* Attach the password meter to the appropriate input*/
	$('#password').bind("keyup", function () {
		var nScore = $(this).chkPass(),
			sComplexity = "state-0",
			$mc = null, i = null;
		if (nScore > 100) { nScore = "state-7"; } else if (nScore <= 0) { nScore = "state-0"; }
		if (nScore > 0 && nScore < 10) { sComplexity = "state-1"; }
		else if (nScore >= 10 && nScore < 20) { sComplexity = "state-2"; }
		else if (nScore >= 20 && nScore < 30) { sComplexity = "state-3"; }
		else if (nScore >= 30 && nScore < 40) { sComplexity = "state-4"; }
		else if (nScore >= 40 && nScore <= 50) { sComplexity = "state-5"; }
		else if (nScore >= 50 && nScore <= 60) { sComplexity = "state-6"; }
		else if (nScore >= 60 && nScore <= 100) { sComplexity = "state-7"; }
		
		$mc = $(".meter-content");
		for(i=0; i<=7; i++) {
			$mc.removeClass("state-"+i);
		}
		$mc.addClass(sComplexity);
	});
	
  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","Register"));