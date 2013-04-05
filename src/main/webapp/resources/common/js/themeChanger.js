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
    	this.initializeTooltip();
    	this.initializeThemeChanger();    	
    },   
    /**
     * Initializes the tooltip on the login page
     * */
    initializeTooltip : function(){
    	$('.tiptip button').tipTip();
    },
    /**
     * Saves the selected theme in Session Storage and retrieves it to show the pages accordingly
     * */
    initializeThemeChanger : function (){
    	if(window.sessionStorage.getItem('currentTheme')){    		
    		$('body').removeClass('ez base').addClass(window.sessionStorage.getItem('currentTheme'));	
    	}
    	$('#baseTheme').on('click', function(){
    		if(window.sessionStorage.getItem('currentTheme')!=="base"){
    			window.sessionStorage.setItem('currentTheme',"base");
    			$('body').removeClass('ez base').addClass('base');		
    		}
    	});
    	$('#ezTheme').on('click', function(){
    		if(window.sessionStorage.getItem('currentTheme')!=="ez"){
    			window.sessionStorage.setItem('currentTheme',"ez");
    			$('body').removeClass('ez base').addClass('ez');		
    		}
    	});
    }
  });	
  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","ThemeChanger"));