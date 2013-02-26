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
    	this.initializeTooltip();
    	this.initializeThemeChanger();
    },   
    /**
     * Initializes the tooltip on the login page
     * */
    initializeTooltip : function(){
    	var that=this;
    	$('.tiptip button').tipTip();
    },
    /**
     * Saves the selected theme in Session Storage and retrieves it to show the pages accordingly
     * */
    initializeThemeChanger : function (){
    	$('body').removeClass('ez base').addClass(sessionStorage.getItem('currentTheme'));	
    	$('#baseTheme').on('click', function(){
    		if(sessionStorage.getItem('currentTheme')!=="base"){
    			sessionStorage.setItem('currentTheme',"base");
    			$('body').removeClass('ez base').addClass('base');		
    		}
    	});
    	$('#ezTheme').on('click', function(){
    		if(sessionStorage.getItem('currentTheme')!=="ez"){
    			sessionStorage.setItem('currentTheme',"ez");
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