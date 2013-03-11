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
    	this.initResetButton();
    	this.validateAndSubmitForm();
    	$('.container').css({'padding-top': function () {    			
					return ($('div.navbar-fixed-top').height());
				}
    	});  
    },   
    /**
     * Validates the Form before submitting it
     * */
    validateAndSubmitForm : function(){
    	var that=this;
    	$("#roleEditForm").validate({
    		rules: {
    			roleName: "required"
    		},
    		messages: {
    			roleName: $('body').data('enterrolename')
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
    				 	  //shows a confirmation message in a RED div if error===true, else shows it in a BLACK div
    					  $().message(response.message,response.error);
    					  document.location.href='/domain/roles/list';
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
    		$("input#roleName").val($("input#roleName").attr("data-reset"));
    		  return false;
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","RoleUpdate"));