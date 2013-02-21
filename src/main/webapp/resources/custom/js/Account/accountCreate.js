(function($, NS, SuperClass,SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
	toString:function(){return NS + '.' + SubClass;},
	rolesToUpdate : [],
	selectedRoles : {},
	allExistingRoles : [],
	/** Constructor. */
    init : function(cfg) {
      this.bindBehavior(this);
    },
    /**
     * Binds client-side behavior.
     */
    bindBehavior : function() {
    	this.getAllRoles();
    	this.initCancelButton();
    	this.validateAndSubmitForm();
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
  			  that.allExistingRoles=response;
  			  for(var i=0;i<that.allExistingRoles.length;i++){
  				  $('#rolesList').append("<option value="+that.allExistingRoles[i].id+">"+that.allExistingRoles[i].roleName+"</option>");						  
  			  }
  			  
  			  $('.multiselect').multiselect({
  			      buttonClass: 'btn',
  			      buttonWidth: 'auto',
  			      buttonText: function(options) {
  			        if (options.length == 0) {
  			          return $('body').data('noneselected')+' <b class="caret"></b>';
  			        }
  			        else if (options.length > 4) {
  			          return options.length + $('body').data('selected')+'  <b class="caret"></b>';
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
  						  	that.selectedRoles[role.id]=role;
  					}else{
  						 delete that.selectedRoles[$(this).val()];						 
  					}					
  			  });		
  		  }//success
    	});  	
    	
    },    
    /**
     * Validates the Form before submitting it
     * */
    validateAndSubmitForm : function(){
    	var that=this;
    	$("#accountForm").validate({
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
    			
    			for(var index in that.selectedRoles){
    				that.rolesToUpdate.push(that.selectedRoles[index]);	
    			}
    			var jsonData = {
    				firstName : $("input#firstName").val(),
    				lastName : $("input#lastName").val(),
    				password : $("input#password").val(),
    				email : $("input#email").val(),
    				isEnabled : $('input#isEnabled').is(':checked'),
    				roles : that.rolesToUpdate
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
    },
    /**
     * Binds the onClick event on the Cancel button
     * */
    initCancelButton : function (){
    	var that=this;
    	$("#cancelButton").click(function() {
    		document.location.href='/domain/accounts/list';
    	});	
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","AccountCreate"));