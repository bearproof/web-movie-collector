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
    	$("#movieForm").validate({
    		rules: {
    			title: "required",
    			year: "required"			
    		},
    		messages: {
    			title: {required : $('body').data('movietitle')},
    			year:  {required  : $('body').data('movieyear')}			
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
    				shelfLocation : $('input#shelfLocation').val(),
    				lentTo : $('input#lentTo').val(),
    				ownMovieNotes : $('textarea#ownMovieNotes').val()
    			};
    			$.ajax({  
    				  type: "POST",  
    				  url: "/domain/movies/",  
    				  data: JSON.stringify(jsonData),
    				  contentType: "application/json; charset=utf-8",
    				  success: function(response,status,xhr) {
    					  //shows a confirmation message in a RED div if error===true, else shows it in a BLACK div
    					  $().message(response.message,response.error);
    					  document.location.href='/domain/movies/list';
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
    		document.location.href='/domain/movies/list';
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","MovieCreate"));