/**
 * ISDC's customized jQuery tooltip plugin.
 * version: 1.0
 */
(function($){
	$.extend($.fn, {

		tooltip : function(cfg) {
			var def = {
				arrowHPosX: 'dinamic',
				animate : 0, /* amount of pixels to animate the tooltip */
				displaytype : 'fade',
				displaytime : 100,
				arrowUp : "<div class='tooltip-arrow-up'></div>",
				arrowDown : "<div class='tooltip-arrow-down'></div>",
				footerTmpl : "<p class='tooltip-footer'>{footer}</p>",
				bodyTmpl : "<p>{msg}</p>",
				headTmpl : "<p class='tooltip-head'>{title}</p>",
				htmlTmpl : "<div class='tooltip hidden {id}' id='{id}'><div class='tooltip-content'>{head}<div class='tooltip-body'>{body}</div>{footer}</div></div>",
				title : '',					
				msg : null,
				footer: ''
			},
			configs = $.extend({}, def, cfg),
			template = function(string, obj) {
				var result = string, prop;
				for (prop in obj) {
					if (obj.hasOwnProperty(prop)) {
						result = result.replace(new RegExp('{' + prop + '}', 'g'), obj[prop]);
					}
				}
				return result;
			},
			generateTooltip = function(id, $trigger) {
				var strBody = "", message = configs.msg, body=[], $tooltip;
				if (!configs.msg) {
					message = $trigger.data('tooltipmsg');
				} else if ($.isFunction(configs.msg)) {
					message = configs.msg();
				}
				if ($.isArray(message)) {
					message = [];
					body = [];
					$.each(message, function(key, value){
						if ($.isPlainObject(value)) {
							body.push(template(configs.bodyTmpl,value));
						} else {
							body.push(template(configs.bodyTmpl, {'msg' : value}));					
						}
					});
					strBody = body.join("");
				} else {
					if ($.isPlainObject(message)) {
						strBody = template(configs.bodyTmpl, message);
					} else {
						strBody = template(configs.bodyTmpl, {'msg' : message});
					} 
				}
                
				$tooltip = $(template(configs.htmlTmpl, {'id' : id, 
														 'head' : (/^\s*$/.test(configs.title) ? '' : template(configs.headTmpl, {'title' : configs.title})), 
														 'body' : message, 
														 'footer' : (/^\s*$/.test(configs.footer) ? '' : template(configs.footerTmpl, {'footer' : configs.footer}))}));  
				
				return $tooltip;
			},
			appendArrow = function($tooltip, positions) {
				var html = $tooltip.html();
				if (positions.arrowPos === 'up') {
					$tooltip.html(configs.arrowUp + html);
				} else if (positions.arrowPos === 'down') {
					$tooltip.html(html + configs.arrowDown);						
				}		
				$tooltip.find('[class^=tooltip-arrow]').css({'background-position' : positions.arrowOffsetLeft + " " + positions.arrowOffsetTop});				
				return $tooltip;
			},			
			computePositions = function($this, $tooltip, e) {
				var offset = $this.offset(), 
					dinamicLeft = ((e.pageX - 40) < 0 ? 0 : (e.pageX - 40 + $tooltip.width()) > $(window).width() ? ($(window).width() - $tooltip.width()) : (e.pageX - 40)),
					dinamicArrowLeft = (e.pageX - dinamicLeft - 10),
					positions = {
						top :  ((offset.top - $tooltip.height() - 10) < $(document).scrollTop() ? (offset.top + $this.height() + 10 + configs.animate) : (offset.top - $tooltip.height() - 10 - configs.animate)),
						left : dinamicLeft,
						arrowPos : (offset.top - $tooltip.height() - 10) < $(document).scrollTop() ? 'up' : 'down',
						arrowOffsetLeft : (($tooltip.width() - dinamicArrowLeft - 10) < Math.round($tooltip.width() / 2) ? Math.round(dinamicArrowLeft/2)  : dinamicArrowLeft) + "px",
						arrowOffsetTop : (offset.top - $tooltip.height() - 10) < $(document).scrollTop() ? '-70px' : 'top'
					},
					left;
				if (configs.arrowHPosX === 'center' || (dinamicArrowLeft + 10) >= $tooltip.width()) {
					positions.arrowOffsetLeft = 'center';
					left = (e.pageX - Math.round($tooltip.width() / 2));
					positions.left = (left < 0 ? 0 : (left +  $tooltip.width()) > $(window).width() ? ($(window).width() - $tooltip.width()) : left);
				}
				return positions;
			},
			hideTooltip = function($tooltip, timouts){
				timouts.hideTimeOut = [];
				if (timouts.clearOutTime !== 0) {
					timouts.displayed = 0;
				}
				timouts.clearOutTime = 0;
				$tooltip.fadeOut("fast",function(){
					$(this).remove();
				});
			},
			onScroll = function() {
				$("[id^=tooltip]").remove();				
			};
			
			return this.each(function(){
				var $trigger = $(this),
				timeouts = {
					displayed: '',
					clearOutTime : 0,
					showTimeOut : {},
					hideTimeOut : {}
						
				};
				$trigger.data({'tooltipid' : "tooltip_" + Math.floor(Math.random()*10000)});
				$trigger.mouseover(function(e){
					var $this = $(this), tooltipId = $this.data('tooltipid');
					if (!$("." + tooltipId).is(":visible")) {
						timeouts.displayed = tooltipId;
						timeouts.showTimeOut[timeouts.displayed] = setTimeout(function(){
							var $tooltip = generateTooltip(tooltipId, $trigger),
								transform = {"tag":"ul","class":"tooltipContent","children":[
                                                  {'tag':'li','class':'delimiter', 'html':''},   							                                                                  
   								                  {'tag':'li', 'class':'tooltip-movie-info', 'html':'${title}'},
   								                  {'tag':'li', 'class':'tooltip-movie-info', 'html':'${year}'},
   								                  {'tag':'li', 'class':'tooltip-movie-info', 'html':'${director}'},
   								                  {'tag':'li', 'class':'tooltip-movie-info', 'html':'<a class="tooltip-movie-id" href="#">${id}</a>'}
   								                  ]
   							 				},
   							 	 tooltipMap = {},
   							 	 briefMovieInfo = $trigger.data('tooltipmsg'),
   							 	 item, site, moviesArrayPerSite,
   							 	 positions;
							$("body").append($tooltip);	

							console.log('tooltip added to body');
							
							 for (item in briefMovieInfo) {
								 if(briefMovieInfo.hasOwnProperty(item)){
									 site = briefMovieInfo[item].site;							 
									 if(!(site in tooltipMap)) {
										 tooltipMap[site] = [];
									 }	
									 moviesArrayPerSite = tooltipMap[site];
									 moviesArrayPerSite.push(briefMovieInfo[item]);
								 }
							 }
														 
							 for(item in tooltipMap){
								 if(tooltipMap.hasOwnProperty(item)){
									 $('.tooltip-body').append("<div class=\"" + item + " site"+"\"/>");
									 $('.'+item).append("<p class=\"tooltip-header\">" + item.toUpperCase() + "</p>");
									 $('.'+item).json2html(tooltipMap[item],transform);	 
								 }
							 }  	
							console.log('json2html done'); 
							
							positions = computePositions($this, $tooltip, e);
							$tooltip.css({'left' : positions.left, 'top' : positions.top});
							$tooltip = appendArrow($tooltip, positions);
							timeouts.hideTimeOut[timeouts.displayed] = true;
							timeouts.clearOutTime = 0;
							if (configs.displaytype === 'fade') {
								$tooltip.fadeIn(configs.displaytime).animate({
									'top' : (positions.arrowPos === 'up' ? '-='+ configs.animate + 'px' : '+=' + configs.animate + 'px')
								}, {duration : 'slow', queue : false}).mouseover(function(){
									clearTimeout(timeouts.clearOutTime);
								});
							} else {
								$tooltip.show(configs.displaytime);
							}
							
							$tooltip.mouseout(function(){
								timeouts.clearOutTime = setTimeout(function(){
									hideTooltip($tooltip, timeouts);
								}, 250);
							}).click(function(){
								hideTooltip($tooltip,  timeouts);
							});
						}, 200);							
					} else {
						clearTimeout(timeouts.clearOutTime);
						timeouts.clearOutTime = 0;
					}
				}).mouseout(function(e){
					var $this = $(this), tooltipId = $this.data('tooltipid'), $current = $("." + tooltipId);
						clearTimeout(timeouts.showTimeOut[tooltipId]);
						if(timeouts.hideTimeOut[timeouts.displayed]) {
							timeouts.clearOutTime = setTimeout(function(){
								hideTooltip($current, timeouts);								
							}, 200);
						} else {
							timeouts.clearOutTime = 0;
							timeouts.displayed = '';
						}	
				});
				// handle window scroll or scrollable sections
				$('.scrollable').scroll(function(e){onScroll();});
				$(window).scroll(function(e){onScroll();});
			});
		}
	});
	
	//$('.tooltip-aware').tooltip();
	
}(jQuery));
