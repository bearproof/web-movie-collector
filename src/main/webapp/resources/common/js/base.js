(function($, NS) {
	/**
	 * Main Application Namespace.
	 */
	window[NS] = window[NS] || {};
	/**
	 * Dummy console implementation. Prevents code crashing when no Firebug (or another console implementer plug-in) is running in the browser.
	 */
	if (!window.console) {
		var i, noop = function() {
		}, fnc = [ 'log', 'debug', 'info', 'warn', 'error', 'assert', 'clear', 'dir', 'dirxml', 'trace', 'group', 'groupCollapsed', 'groupEnd', 'time', 'timeEnd', 'profile','profileEnd', 'count', 'exception', 'table' ];

		window.console = {};
		for (i = 0; i < fnc.length; i++) {
			window.console[fnc[i]] = noop;
		}
	}

	/**
	 * Global constants used across all page of application
	 */
	window[NS].Constants = window[NS].Constants || {};

	/**
	 * Helper method for extending native JavaScript objects with custom methods in a more compact and non-obtrusive way.
	 */
	Function.prototype.method = function(name, fnc) {
		if (this.prototype[name] === undefined) {
			this.prototype[name] = fnc;
		}
		return this;
	};
	Number.random=function(a,b){
		return Math.floor(a+ (b-a)*Math.random());
	};
	Array.method('choose',function(){
		return this[Number.random(0,this.length-1)];
	});
	/**
	 * Client-side templating method. Useful when generationg dynamic markup based on JSON objects received by Ajax. Eg.: '<span>{firstName}, {lastName}</span>'.tmpl({"firstName":
	 * "Elemér", "lastName" : "Zágoni"}) = '<span>Elemér, Zágoni</span>';
	 */
	String.method('tmpl', function(obj) {
		var prop, result = this;

		for (prop in obj) {
			if (obj.hasOwnProperty(prop)) {
				result = result.replace(new RegExp('{' + prop + '}', 'g'), obj[prop]);
			}
		}
		return result;
	});

	/**
	 * Interface BehaviorSupport. Helps to keep a good naming convention for commonly used methods. Eg.: Instead of using different names in each web application for the client-side
	 * behavior binding methods, we force using the same convention for the same thing.
	 */
	window[NS].BehaviorSupport = window[NS].BehaviorSupport || {
		toString : function() {
			return NS + '.BehaviorSupport';
		},
		bindBehavior : window[NS].Constants.noop
	};

	/**
	 * Base class for any UI support class.
	 */
	window[NS].Base = window[NS].Base || Klass.extend({
		/**
		 * Constructor. Here comes code to be executed on after page load regardless on which page we are.
		 * Eg.: - Marking required fields, Setting global Ajax Hooks etc.
		 */
		init : function() {
		},

		/**
		 * Should be overridden for debugging purposes.
		 */
		toString : function() {
			return NS + '.Base Class';
		},

		/**
		 * Returns the exact type of any JavaScript object.
		 * Note: the neither the typeof operator nor the .constructor method are not precise enough.
		 * They do NOT make difference between certain objects: Eg.: typeof {} == 'object'; typeof /\s+/ = 'object' 
		 * Inside a function: typeof arguments = 'object'.
		 */
		objType: function(obj) {
		  var t=Object.prototype.toString.call(obj).split(' ')[1];
		  return t.substring(0,t.length-1);
		}
	});
	/*
	 * DON'T ATTACH ONLOAD BEHAVIOUR, this is an ABSTRACT CLASS, extenders should bind their behaviour, constructor of Base class is AUTOMATICALLY called when the EXTENDER class is
	 * INSTANTIATED.
	 */

}(jQuery, "WMC"));
