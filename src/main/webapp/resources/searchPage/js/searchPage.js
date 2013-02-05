(function($, NS, SuperClass, SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
		toString : function() {
			return NS + '.' + SubClass;
		},
		$ctx: $('#searchPage'),
		$msg: $('#searchPage .messages'),
		$contentArea:$('.search-results','#searchPage'),
		$accordion : null,
		$addButton : $("button.add",'#searchPage'),
		$searchTerm : $("input.movie-title",'#searchPage'),
		request: null,
		socket: $.atmosphere,
		subSocket: null,
		selectedMovieTitle : "",
		/*Test socket*/
		requestTest: null,
		socketTest: $.atmosphere,
		subSocketTest: null,
		isSending:false,
		stopSignaled:false,
				
		/** Constructor. */
		init : function(cfg) {
			this.openChannel("streaming");	
			this.bindBehavior();
			this.request.onMessage = $.proxy(this.messageReceived,this);
		},
		
		/**Renders the page's dynamic layout*/
		doLayout : function(e) {
			var cfgLayout = {
				spacing_open:3,
				spacing_closed:3,
				north : {
					resizable : false,
					closable : true
				},
				south : {
					resizable : false,
					closable : false
				}
			};
			this.$ctx.layout(cfgLayout);
			$('#header').layout($.extend({},cfgLayout,{
				east:{
					size:20 + $('span.locale-changer').width()
				}
			}));
			$('.layout-inner').layout($.extend({},cfgLayout,{
				east:{
					size:0.66
				}
			}));
			$('.user-input-zone').layout(cfgLayout);
		},
		
		bindBehavior: function(){
			var that=this;
			//bind search page Behavior:
			this.$addButton.on('click', $.proxy(this.processRequest, this));
			this.$searchTerm.on('keydown', $.proxy(this.processRequestOnEnter, this));	
			this.$accordion=this.$contentArea.accordion({heightStyle: "content", collapsible: true, active: false});			
		},
		/**Open a bi-directional communication channel between the browser and the specified server.*/
		openChannel: function (transport,connectionType) {
			var that=this;		
			this.request = new $.atmosphere.AtmosphereRequest();
			$.extend(this.request,{
				url:that.$ctx.data('search-url'),				
				contentType:"application/json",
				transport:transport,
				fallbackTransport:"long-polling",
				onOpen:that.onChannelOpen,					
				onMessage:$.proxy(that.onMessageReceived,that),
				onMessagePublished: that.onMessagePublished,
				onError:that.onError,
				onReconnect: that.onReconnect
			});
			this.subSocket = this.socket.subscribe(this.request);
		},				
		
		/**On Channel open*/
		onChannelOpen: function(){
			$.atmosphere.log('info', ['onChannelOpen']);
		},
		
		/**On Message From server.*/
		onMessageReceived: function(response){			
			var contentArea = $('.search-results',this.$ctx),
        	searchItemTmpl = $('#searchItemTmpl').val(),		        
			movieDataSourceTmpl = $('#movieDataSourceTmpl').val(),
			movieItemTmpl = $('#movieItemTmpl').val(),
			noMovieFoundTmpl = $('#noMovieFoundTmpl').val(),
			detailedMovieItemTmpl = $('#detailedMovieItemTmpl').val(),
			movieTitle = $('.movie-title',this.$ctx).val(), 
			MovieData = null,
			site = null,
			that=this,
			treeTitlesArray = [],
			panelContent = "",
			$el = null,
			decodedResponse  = null;			
			
			$.atmosphere.log('info', ['onMessageReceived']);	

			if(response.state === "messageReceived"){
					
        		MovieData = $.parseJSON(decodeURIComponent(response.responseBody));	 
				//$.atmosphere.log('info', [MovieData]);
	        		
	        	if($.isArray(MovieData)){	
	        		$(movieDataSourceTmpl.tmpl({
						"site" : MovieData[0].site						
					})).appendTo($('.'+movieTitle.replace(/\s+/g, '')));	
					
					$.each(MovieData,function(index, value){
		        		site = value.site;
		        		$(movieItemTmpl.tmpl({
							"title" : value.title,
							"year" : value.year,
							"director" : value.director,
							"id" : value.id,
							"site" : value.site
						})).appendTo($('.'+site));				    			
	        		});		        		
	        	}else{
	        		/*site = MovieData.site;
					$(movieDataSourceTmpl.tmpl({
						"site" : site					
					})).appendTo($('#accordion').accordion('getPanel',movieTitle));	
					$(noMovieFoundTmpl.tmpl({
						"noMovieFound" : this.$msg.data('searchpage.one.result.found')							
					})).appendTo($('#accordion').accordion('getPanel',movieTitle).find('.'+site));*/
	        	}
	        		
	        	/*  $el = $('#accordion').accordion('getPanel',movieTitle);
					$el.siblings('.panel-header').find('.panel-icon').removeClass('icon-loading').addClass('icon-cancel');
					
					$('.icon-cancel').on('click', function(e){					
						var p = $('#accordion').accordion('getPanel',$(this).prev().html()), index = null;
						if(p){
							 index = $('#accordion').accordion('getPanelIndex', p);
							 $('#accordion').accordion('remove',index);
						}
					});		
        			panelContent = $('#accordion').accordion('getPanel',movieTitle).panel('body');
        			$('.tree-title', panelContent).each(function(index){
        				treeTitlesArray.push($(this).html());
        			});						
					//only add content if it doesn't exist in the treeTitlesArray
					if(($.inArray(MovieData[0].site,treeTitlesArray)===-1)){
						
						if(MovieData[0].title===undefined){
							site = MovieData[0].site;
							$(movieDataSourceTmpl.tmpl({
								"site" : site					
							})).appendTo($('#accordion').accordion('getPanel',movieTitle));	
							$(noMovieFoundTmpl.tmpl({
								"noMovieFound" : this.$msg.data('searchpage.movie.not.found')							
							})).appendTo($('#accordion').accordion('getPanel',movieTitle).find('.'+site));
						}else{
							$(movieDataSourceTmpl.tmpl({
								"site" : MovieData[0].site						
							})).appendTo($('#accordion').accordion('getPanel',movieTitle));	
							
							$.each(MovieData,function(index, value){
				        		site = value.site;
				        		$(movieItemTmpl.tmpl({
									"title" : value.title,
									"year" : value.year,
									"director" : value.director,
									"id" : value.id,
									"site" : value.site
								})).appendTo($('#accordion').accordion('getPanel',movieTitle).find('.'+site));				    			
			        		});	
						}						
												
			        	//generate a tree from the content of the accordion
			        	$('#accordion').accordion('getPanel',movieTitle).find('.'+site).closest('.easyui-tree').tree({animate:true});
			        	//bind the getDetailedData() function to the elements which have the class "movie-id" only once
			        	$('.movie-id',this.$ctx).off('click',$.proxy(this.getDetailedData,this));
		        		$('.movie-id', this.$ctx).on('click',$.proxy(this.getDetailedData,this));
			        							
					}        		
        		}else{ we received the detailed movie info, we need to
        			   * overwrite the value of movieTitle variable 
        			   * with the name of the selected movie title from the tree
        			if(this.selectedMovieTitle!==""){
        				movieTitle = this.selectedMovieTitle;
            			Remove the loading icon from the selected movie after the requested data has arrived
        				$('.tree-loading').each(function(){
        					var selectedMovie = $(this).closest('ul').siblings('div.tree-node').children('.tree-title');
        					if(selectedMovie.html()===movieTitle){
        						$(this).removeClass('tree-loading').addClass('tree-file');
        					}
        				});            			
        			}else{
        				movieTitle = $('.movie-title',this.$ctx).val();
        				if($('#tabs').hasClass('display-none')){
        		 			$('#tabs').removeClass('display-none');	
        		 		}
        				site = MovieData.site;
						$(movieDataSourceTmpl.tmpl({
							"site" : site					
						})).appendTo($('#accordion').accordion('getPanel',movieTitle));	
						$(noMovieFoundTmpl.tmpl({
							"noMovieFound" : this.$msg.data('searchpage.one.result.found')							
						})).appendTo($('#accordion').accordion('getPanel',movieTitle).find('.'+site));
						
						$el = $('#accordion').accordion('getPanel',movieTitle);
			        	$el.find('.'+site).closest('.easyui-tree').tree({animate:true});			        	
						$el.siblings('.panel-header').find('.panel-icon').removeClass('icon-loading').addClass('icon-cancel');
						
						$('.icon-cancel').on('click', function(e){					
							var p = $('#accordion').accordion('getPanel',$(this).prev().html()), index = null;
							if(p){
								 index = $('#accordion').accordion('getPanelIndex', p);
								 $('#accordion').accordion('remove',index);
							}
						});														
        			}        	        			        			
        			
        			if($('#tabs').tabs('exists', movieTitle)){	        				  
        				$('#tabs').tabs('getTab',movieTitle).html($(detailedMovieItemTmpl.tmpl({
        					"title" : MovieData.title,
        					"year" : MovieData.year,
        					"director" : MovieData.director,
        					"site" : MovieData.site.toUpperCase(),
 							"description" : MovieData.description,
							"cast" : MovieData.cast,
							"genre" : MovieData.genre,
							"rate" :MovieData.rate,
							"runtime" : MovieData.runtime
						})));
        				$('#tabs').tabs('select',movieTitle);
        				this.selectedMovieTitle = "";
        			}else{
        				$('#tabs').tabs('add',{  
	        				 title: movieTitle,
	        				 content: $(detailedMovieItemTmpl.tmpl({
	        					 "title" : MovieData.title,
	         					"year" : MovieData.year,
	         					"director" : MovieData.director,
	        					"site" : MovieData.site.toUpperCase(),
	 							"description" : MovieData.description,
								"cast" : MovieData.cast,
								"genre" : MovieData.genre,
								"rate" :MovieData.rate,
								"runtime" : MovieData.runtime
							})),
	        				closable: true,
	        				selected: true,
	        				iconCls:'icon-movie'
	        				});	
        				this.selectedMovieTitle = "";
        			}	        			   	        				        				        			
        		}*/
	        	
		    }// end if(response.state==="messageReceived")	    
		},
		
		/**On Message Published*/
		onMessagePublished: function(response){
			$.atmosphere.log('info', ['onMessagePublished.', response]);
		},
		
		/**On Channel Error*/
		onError: function(){
			$.atmosphere.log('info', ['onError']);

		},
		/**On Channel Reconnected*/
		onReconnect: function(){
			$.atmosphere.log('info', ['onReconnect']);
		},
		
		/**Sends a request to server with the search term and the movie infosources*/
		processRequest: function(e){
			var that= this,
			movieData = {
			"searchTerms" : [],
			"infoSourceKeys" : []
			}, 
			movieTitle = $('.movie-title',this.$ctx).val(), 
			contentArea = $('#movieList',this.$ctx), 
			searchItemTmpl = $('#searchItemTmpl').val(),
			panelContent = "";						
	
			// map all the checked checkboxes' values into an array
			movieData.infoSourceKeys = $('.info-source :checked',this.$ctx).map(function() {
				return this.value;
			}).get();
			// put the search term into the movieData object
			if($('.movie-title').val()!==""){
				movieData.searchTerms.push($('.movie-title').val());	
			}		
			
			$.atmosphere.log('info', [movieData]);
	
			if (movieData.infoSourceKeys.length === 0) {
				/*$.messager.show({
						title : '',
						msg: this.$msg.data('searchpage.no.infosource.selected'),
						showType: 'slide',
						timeout:3000,
						style:{
							right:'',
							top:'',
							bottom:document.body.scrollTop+document.documentElement.scrollTop
						}
				});*/
				alert('No infosource selected!');
				return false;
			}
			if (movieData.searchTerms.length === 0) {
				/*$.messager.show({
					title:'',
					msg: this.$msg.data('searchpage.movie.required'),
					showType: 'slide',
					timeout:3000,
					style:{
						right:'',
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});*/
				alert('Please type a movie title!');
				return false;
			}			
			
			$(searchItemTmpl.tmpl({
				"searchTerm" : movieTitle.replace(/\s+/g, ''),
				"movieTitle" : movieTitle
			})).appendTo(contentArea.children('ul'));
			
			//add a new panel only if it doesn't exist already
			/*if(!$('#accordion').accordion('getPanel',movieTitle)){							
				$('#accordion').accordion('add', {
					title: movieTitle,
					content: $(searchItemTmpl.tmpl({
								"searchTerm" : movieTitle
							})),
					selected: true,
					iconCls: 'icon-loading',
					width:'100%'
				});												
				
			}else{
				//open the existing panel
				$('#accordion').accordion('select',movieTitle);					
			}	*/
			
			this.subSocket.response.request.method='POST';
			this.subSocket.response.request.url=this.$ctx.data('search-url');
        	this.subSocket.push(JSON.stringify(movieData));		
		},
		
		/**Process request on Enter keypress*/
		processRequestOnEnter: function(e){
			  if (e.keyCode === 13) {
				  $.proxy(this.processRequest(e), this);
			  }
		},
		
		/**Sends a request to server with a movie id to get detailed data about that movie*/
		getDetailedData: function(e){
			var that= this,						
			contentArea = $('.search-results',this.$ctx), 
			searchItemTmpl = $('#searchItemTmpl').val(),						
			detailedMovieData = {
				"searchTerms" : [],
				"infoSourceKeys" : []
			},
			$el = e.target;
			$($el).closest("div.tree-node").children("span.tree-icon").removeClass('tree-file').addClass('tree-loading');
			this.selectedMovieTitle = $($el).closest('ul').siblings('div.tree-node').children('.tree-title').html();
			
			if($('#tabs').hasClass('display-none')){
 			   $('#tabs').removeClass('display-none');	
 			}	
			
			detailedMovieData.infoSourceKeys.push($($el).data('site'));
			detailedMovieData.searchTerms.push($($el).attr('id'));
			$.atmosphere.log('info', [detailedMovieData]);
				
			this.subSocket.response.request.method='POST';
			this.subSocket.response.request.url=this.$msg.data('detailedsearchUrl');
        	this.subSocket.push(JSON.stringify(detailedMovieData));	
		}
				
	});
	
	$(function(){  			
			
	    $('.ez-template').bind('click', function(){  
	    	$('.panel-header').addClass("panel-header-ez");
	    	$('.layout-split-north').addClass("layout-split-north-ez");
	    	$('.layout-split-south').addClass("layout-split-south-ez");
	    	$('.layout-split-east').addClass("layout-split-east-ez");
	    	$('.layout-split-west').addClass("layout-split-west-ez");
	    	
	    	$('.layout-button-up').addClass("layout-button-up-ez");
	    	$('.layout-button-down').addClass("layout-button-down-ez");
	    	$('.layout-button-left').addClass("layout-button-left-ez");
	    	$('.layout-button-right').addClass("layout-button-right-ez");
	    	
	    	$('.layout-expand').addClass("layout-expand-ez");	    
	    	$('.panel-body').addClass("panel-body-ez");	  
	    	$('.header').addClass("header-ez");
	    });  
	    
	    $('.basic-template').bind('click', function(){  
	    	$('.panel-header-ez').removeClass("panel-header-ez");
	    	$('.layout-split-north-ez').removeClass("layout-split-north-ez");
	    	$('.layout-split-south-ez').removeClass("layout-split-south-ez");
	    	$('.layout-split-east-ez').removeClass("layout-split-east-ez");
	    	$('.layout-split-west-ez').removeClass("layout-split-west-ez");	  
	    	
	    	$('.layout-button-up-ez').removeClass("layout-button-up-ez");
	    	$('.layout-button-down-ez').removeClass("layout-button-down-ez");
	    	$('.layout-button-left-ez').removeClass("layout-button-left-ez");
	    	$('.layout-button-right-ez').removeClass("layout-button-right-ez");	    	
	    	
	    	$('.layout-expand-ez').removeClass("layout-expand-ez");	    
	    	$('.panel-body-ez').removeClass("panel-body-ez");	
	    	$('.header-ez').removeClass("header-ez");	    	
	    });  
	    
	    $('.layout-button-left').bind('click', function(){
	    	if($('.panel-header').hasClass("panel-header-ez")) {
		    	$('.layout-expand').addClass("layout-expand-ez");	    
		    	$('.panel-body').addClass("panel-body-ez");	    				    	
	    	}
	    });
	    
	    $('.layout-button-right').bind('click', function(){
	    	if(!$('.panel-header').hasClass("panel-header-ez")) {
		    	$('.layout-expand-ez').removeClass("layout-expand-ez");	    
		    	$('.panel-body-ez').removeClass("panel-body-ez");	    				    	
	    	}
	    });
	}); 		
	
	$(window).on("beforeunload", function() {
	    $(window).trigger("unload.atmosphere");
	});
	
	/* Attach page specific behavior on page load */
	$(function() {
		return new window[NS][SubClass]();
	});
}(window.jQuery, "WMC", "Base", "SearchPage"));
