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
    	$("#movieEditForm").validate({
    		rules: {
    			title: "required",
    			year: "required"			
    		},
    		messages: {
    			title: {required : $('body').data('movietitle')},
    			year:  {required  : $('body').data('movieyear')}	
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
    				shelfLocation : $('input#shelfLocation').val(),
    				lentTo : $('input#lentTo').val(),
    				ownMovieNotes : $('textarea#ownMovieNotes').val()
    			};
    			$.ajax({  
    				  type: "PUT",  
    				  url: "/domain/movies/"+$("input#id").val(),  
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
    initResetButton : function (){
    	var that=this;
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
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","MovieUpdate"));