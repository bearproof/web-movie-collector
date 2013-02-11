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
		selectedMovieId : '',
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
		
		/**Bind the events on the Add button and on enter keypress when the input is focused*/
		bindBehavior: function(){
			var that=this;
			//bind search page Behavior:
			this.$addButton.on('click', $.proxy(this.processRequest, this));
			this.$searchTerm.on('keydown', $.proxy(this.processRequestOnEnter, this));							
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
			detailedMovieItemTabHeader = $('#detailedMovieItemTabHeader').val(),
			detailedMovieItemTabContent = $('#detailedMovieItemTabContent').val(),
			movieTitle = $('.movie-title',this.$ctx).val(), 
			MovieData = null,
			site = null,
			movieandsite = null,
			that=this,
			treeTitlesArray = [],
			panelContent = "",
			$el = null,
			decodedResponse  = null;			
			
			$.atmosphere.log('info', ['onMessageReceived']);	

			if(response.state === "messageReceived"){								
					
				if((response.responseBody==="201")||((response.responseBody==="")&&(response.status===200))){
					alert('Movie saved successfully in DB');
					return;
				}else if((response.responseBody==="400")||(response.status===400)){
					alert('Could not save movie into DB');
					return;
				}
        		MovieData = $.parseJSON(decodeURIComponent(response.responseBody));	 
				$.atmosphere.log('info', [MovieData]);								
	        		
	        	if($.isArray(MovieData)&&(MovieData[0].title!==undefined)){
	        		var trimmedMovieTitle = movieTitle.replace(/\s+/g, ''),
	        			movieandsite = trimmedMovieTitle+MovieData[0].site,
	        			currentNode = $("#"+trimmedMovieTitle).siblings('.'+trimmedMovieTitle).find('.'+movieandsite);	        		

	        		//remove the loading icon
	        		$('.'+trimmedMovieTitle).siblings('label').removeClass('loading');
	        		
	        		/*search if a node with the same info already exists in the tree 
	        		  and if the node doesn't already exist, add it*/
	        		if(currentNode.length<=0){
	        			$(movieDataSourceTmpl.tmpl({
							"site" : MovieData[0].site,
							"movieandsite" : movieandsite
						})).appendTo($('.'+trimmedMovieTitle));	
	        			
						
						$.each(MovieData,function(index, value){
			        		$(movieItemTmpl.tmpl({
								"title" : value.title,
								"year" : value.year,
								"director" : value.director,
								"uniqueid" : value.id.replace(/\//g, ''),								
								"site" : value.site,
								"siteid" : value.id
							})).appendTo($('.'+movieandsite));				    			
		        		});		        		
	        		}
	        		
	        		//bind the getDetailedData() function to the elements which have the class "movie-id" only once
		        	$('.movie-id',this.$ctx).off('click',$.proxy(this.getDetailedData,this));
		        	$('.movie-id', this.$ctx).on('click',$.proxy(this.getDetailedData,this));	 
	        		
	        	}else if((MovieData!==null)&&(MovieData.title!==undefined)){// we received an object => Detailed Movie Info	   	        		
	        		site = MovieData.site;
	        		
	        		//remove the loading icon after the detailed movie info has been retrieved
	        		$('#'+that.selectedMovieId).siblings('label').removeClass('loading');
	        		//only add a new tab with a certain id if it doesn't exist already
	        		if($('#tab'+that.selectedMovieId).length<=0){
	        			$(detailedMovieItemTabHeader.tmpl({	        			
		        			"movieTitle" : MovieData.title,
		        			/*"site" : MovieData.site.toUpperCase(),*/
		        			"uniqueid" : '#tab'+that.selectedMovieId
		        		})).appendTo($('#movieTabHeader'));
		        		
		        		$(detailedMovieItemTabContent.tmpl({
	    					"title" : MovieData.title,
	    					"year" : MovieData.year,
	    					"director" : MovieData.director,
	    					"site" : MovieData.site.toUpperCase(),
							"description" : MovieData.description,
							"cast" : MovieData.cast,
							"genre" : MovieData.genre,
							"rate" :MovieData.rate,
							"runtime" : MovieData.runtime,
		        			"uniqueid" : 'tab'+that.selectedMovieId
						})).appendTo($('#movieTabContent'));
	        		}	        			        			        		
	        		
	        		$('#movieTabHeader a[href="#tab'+that.selectedMovieId+'"]').tab('show');
	        			        		
	        		//bind the removeTab() function to the elements which have the class ".closeTab" only once
	        		$('.closeTab',this.$ctx).off('click',$.proxy(this.removeTab,this));
		        	$('.closeTab', this.$ctx).on('click',$.proxy(this.removeTab,this));	 
		        	
		        	$('.addToDB', this.$ctx).off('click', $.proxy(this.saveMovieInDb,this));
		        	$('.addToDB', this.$ctx).on('click', $.proxy(this.saveMovieInDb,this));
	        			        	
	        	}	        		        		        	       		        	
	        	
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
			trimmedMovieTitle = '';						
	
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
				alert('No infosource selected!');
				return false;
			}
			if (movieData.searchTerms.length === 0) {				
				alert('Please type a movie title!');
				return false;
			}			
			
			trimmedMovieTitle = movieTitle.replace(/\s+/g, '');
			//add a tree node with the search term only if it doesn't exist already
			if($('#'+trimmedMovieTitle).length<=0){
				$(searchItemTmpl.tmpl({
					"searchTerm" : trimmedMovieTitle,
					"movieTitle" : movieTitle
				})).appendTo(contentArea.children('ul'));								
			}
			
			$('#'+trimmedMovieTitle).siblings('label').addClass('loading');
			
			//TODO:	open the existing tree node
			
			//bind the removeTreeNode() function to the spans having the class "removeTreeNode" only once
        	$('.removeTreeNode',that.$ctx).off('click',$.proxy(that.removeTreeNode,that));
        	$('.removeTreeNode', that.$ctx).on('click',$.proxy(that.removeTreeNode,that));
			
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
			
			this.selectedMovieId = $($el).data('uniqueid');
			$('#'+this.selectedMovieId).siblings('label').addClass('loading');
			detailedMovieData.infoSourceKeys.push($($el).data('site'));
			detailedMovieData.searchTerms.push($($el).data('siteid'));
			
			$.atmosphere.log('info', [detailedMovieData]);
				
			this.subSocket.response.request.method='POST';
			this.subSocket.response.request.url=this.$msg.data('detailedsearchUrl');
        	this.subSocket.push(JSON.stringify(detailedMovieData));	
		},
		
		/**Sends Movie Information to the server to be saved into the DB*/
		saveMovieInDb : function(e){
			var $el = e.target,
				MovieData = {};
			
			MovieData.title = $($el).siblings('ul').find('li.title').html();
			MovieData.year = $($el).siblings('ul').find('li.year').html();
			MovieData.director = $($el).siblings('ul').find('li.director').html();
			MovieData.site = $($el).siblings('ul').find('span.movieSite').html();
			MovieData.description = $($el).siblings('ul').find('li.description').html();
			MovieData.cast = $($el).siblings('ul').find('li.cast').html();
			MovieData.genre = $($el).siblings('ul').find('li.genre').html();
			MovieData.rate = $($el).siblings('ul').find('li.rate').html();
			MovieData.runtime = $($el).siblings('ul').find('li.runtime').html();
    		MovieData.userRating = $($el).siblings('select.userRating').find(":selected").text();
    		MovieData.movieStatus = $($el).siblings('select.movieStatus').find(":selected").text();
    		MovieData.shelfLocation = $($el).siblings('input.shelfLocation').val();    		
    		MovieData.lentTo = $($el).siblings('input.lentTo').val();
    		MovieData.ownMovieNotes = $($el).siblings('input.ownMovieNotes').val();
    		MovieData.idOnSite = $($el).parents('div.tab-pane').attr('id');
    		MovieData.userId = '';
    		MovieData.loanDate = '';
    		MovieData.returnDate = '';
    		
    		this.subSocket.response.request.method='POST';
			this.subSocket.response.request.url=this.$msg.data('savemovieUrl');
        	this.subSocket.push(JSON.stringify(MovieData));	
		},
		
		/**Removes the selected tab from the DetailedMovieInfo section*/
		removeTab : function(e){
			$el = e.target;			
			var correspondingContentDiv = $($el).parents('a').attr('href'),
				previousTab = $($el).closest('li').prev().children('a').attr('href'),
				nextTab = $($el).closest('li').next().children('a').attr('href');
			$(correspondingContentDiv).remove();
			$($el).parents('li.active').remove();
			if((previousTab!==null)&&(previousTab!==undefined)){
				$('#movieTabHeader a[href="'+previousTab+'"]').tab('show');				
			}else if((nextTab!==null)&&(nextTab!==undefined)){
				$('#movieTabHeader a[href="'+nextTab+'"]').tab('show');
			}
		},
		
		/**Removes the selected node from the BriefMovieInfo tree*/
		removeTreeNode : function(e){
			$el = e.target;		
			var correspondingTree = $($el).closest('li');			
			$(correspondingTree).remove();
		}
		
		/*createAutoClosingAlert : function(selector,delay){
			var alert = $(selector).alert();
			   window.setTimeout(function() { alert.alert('close'); }, delay);
		}*/
				
	});
	
	$(function(){		
		
		/*Make the link to the corresponding page in the navbar active*/
		$('ul.nav li').each(function(){
			$(this).removeClass('active');
		});
		$('#wmcPageLink').addClass('active');
			
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
	
	/*$(window).on("beforeunload", function() {
	    $(window).trigger("unload.atmosphere");
	});*/
	
	/* Attach page specific behavior on page load */
	$(function() {
		return new window[NS][SubClass]();
	});
}(window.jQuery, "WMC", "Base", "SearchPage"));
