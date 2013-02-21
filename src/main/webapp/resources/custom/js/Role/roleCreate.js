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
    	$("#roleForm").validate({
    		rules: {
    			roleName: {
    				required:true
    				,
    				remote: "/validator/checkrolename"
    			}
    		},
    		messages: {
    			roleName: {
    				required : $('body').data('enterrolename'),
    				remote: jQuery.format($('body').data('remote'))
    			}
    		},
    		errorPlacement: function(error, element) {
    			error.appendTo(element.next());
    		},
    		submitHandler: function() {
    			var jsonData = {
    				roleName : $("input#roleName").val()
    			};
    			$.ajax({  
    				  type: "POST",  
    				  url: "/domain/roles/",  
    				  data: JSON.stringify(jsonData),
    				  contentType: "application/json; charset=utf-8",
    				  success: function(response,status,xhr) {
    					  document.location.href='/domain/roles/list';
    				  },
    			      error: function (xhr, ajaxOptions, thrownError) {
    			          alert(thrownError);
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
    		document.location.href='/domain/roles/list';
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","RoleCreate"));