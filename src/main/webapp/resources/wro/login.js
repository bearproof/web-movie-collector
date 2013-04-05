(function($, NS, SuperClass,SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
	toString:function(){return NS + '.' + SubClass;},
	/** Constructor. */
    init : function(cfg) {
      this.bindBehavior();
    },
    /**
     * Binds client-side behavior.
     */
    bindBehavior : function() {
    	this.showInactiveMessage();
    },
    /**
     * Shows a corresponding message when an inactive user tries to log into the application,
     * before he/she activated the account via e-mail
     */
    showInactiveMessage : function(){
    	if(window.sessionStorage.getItem('inactive')==="true"){
    		$('#inactive').removeClass('hidden');
    		window.sessionStorage.setItem('inactive',"false");
    	}else{
    		$('#inactive').addClass('hidden');
    	}
    }
  });	
  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","ShowInactive"));