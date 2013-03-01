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
		$signOutButton : $('#sign_out_button','#searchPage'),
		request: null,
		socket: $.atmosphere,
		subSocket: null,
		selectedMovieId : '',		
		targetLanguage : null,
		partialJSONString : '',
		prevRequest : null,
				
		/** Constructor. */
		init : function(cfg) {
			this.openChannel("streaming");	
			this.bindBehavior();
		},				
		
		/**Bind the events on the Add button and on enter keypress when the input is focused*/
		bindBehavior: function(){
			var that=this;
			//bind search page Behavior
			this.$addButton.on('click', $.proxy(this.processRequest, this));
			this.$searchTerm.on('keydown', $.proxy(this.processRequestOnEnter, this));
			$('.locale').on('click',$.proxy(this.confirmLanguageChange,this));
			$('#confirmLanguageChangeModal button.btn-danger').on('click',$.proxy(this.changeLanguage,this));	
			window.onbeforeunload = function() {
				that.onDisconnect();
			};
			
			$('.container').css({'padding-top': function () {    			
					return ($('div.navbar-fixed-top').height());
				}
			});    
		},
		
		/**Open a bi-directional communication channel between the browser and the specified server.*/
		openChannel: function (transport,connectionType) {
			var that=this;		
			this.request = new $.atmosphere.AtmosphereRequest();
			$.extend(this.request,{
				url:that.$ctx.data('search-url'),				
				contentType:"application/json;Charset=UTF-8",
				dataType:"json",
				transport:transport,
				fallbackTransport:"long-polling",
				onOpen:that.onChannelOpen,					
				onMessage:$.proxy(that.onMessageReceived,that),
				onMessagePublished: that.onMessagePublished,
				onError:that.onError,
				onReconnect: that.onReconnect,
				onDisconnect: that.onDisconnect
			});
			this.subSocket = this.socket.subscribe(this.request);
		},				
		
		/**On Channel open*/
		onChannelOpen: function(){
			$.atmosphere.log('info', ['onChannelOpen']);
		},
		
		/**On Message From server.*/
		onMessageReceived: function(response){			
			var MovieDataString = null;        		
			
			$.atmosphere.log('info', ['onMessageReceived']);	

			if((response.state === "messageReceived")&&(response.responseBody!=="")){					
				MovieDataString = response.responseBody;
				if(MovieDataString===null||MovieDataString==="&~$"){					
					console.log('Null or Empty:'+MovieDataString);	
					return false;
				}
				this.partialJSONString+=MovieDataString;				
				this.tryToShow();
			}// end if(response.state==="messageReceived")	    
		},
		
		/**Build a valid JSON String, then parse it and show it*/
		tryToShow : function(){
			var MovieDataAsJson = null,
				TrimmedStringArray = null;			
			
			if(this.partialJSONString.indexOf('&~$')!==-1){
				while(this.partialJSONString.indexOf('&~$')!==-1){		
					TrimmedStringArray = this.partialJSONString.substring(0,this.partialJSONString.indexOf('&~$'));
					console.log('while:' + TrimmedStringArray);
					try{
						MovieDataAsJson = $.parseJSON(TrimmedStringArray);
						this.showMovieInfo(MovieDataAsJson);
						this.partialJSONString = this.partialJSONString.replace(TrimmedStringArray+'&~$','');
					}catch(e){
						$.atmosphere.log('info', ['invalidJSON TrimmedStringArray']);
						$.atmosphere.log('info', ['TrimmedStringArray:'+TrimmedStringArray]);
						break;
					}
				}
			}else if(this.partialJSONString.indexOf('<!--')!==-1){				
				console.log('Header sent by atmosphere:'+this.partialJSONString);
				this.partialJSONString='';
			}
		},
		
		/**On Message Published*/
		onMessagePublished: function(response){
			var responseObj = null;
			$.atmosphere.log('info', ['onMessagePublished.', response]);
			try{
				responseObj = $.parseJSON(response.responseBody);
				//shows the message in a RED div if isError===true, else shows it in a BLACK div
				$().message(responseObj.message,responseObj.error);				
			}catch(e){
				$.atmosphere.log('info', ['invalidJSON:'+e.message]);
				$.atmosphere.log('info', ['responseBody:'+response.responseBody]);
			}
		},
		
		/**On Channel Error*/
		onError: function(){
			$.atmosphere.log('info', ['onError']);

		},
		
		/**On Channel Reconnected*/
		onReconnect: function(){
			$.atmosphere.log('info', ['onReconnect']);
		},
		
		/**On Channel Disconnect*/
		onDisconnect: function(){
			$.atmosphere.log('info', ['onDisconnect']);
			this.socket.unsubscribe();
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
				$('#errorModalLabel').html('Warning:');
		    	$('#errorModalMsg').html('No infosource selected!');
		    	$('#errorModalBody').attr('class', 'modal-body alert alert-warning');
		    	$('#errorModal').modal().css({'margin-top': function () {
		                        			return -($(this).height() / 2);
		                    				}
		    						});				
				return false;
			}
			if (movieData.searchTerms.length === 0) {	
				$('#errorModalLabel').html('Warning:');
		    	$('#errorModalMsg').html('Please type a movie title!');
		    	$('#errorModalBody').attr('class', 'modal-body alert alert-warning');
		    	$('#errorModal').modal().css({'margin-top': function () {
        										return -($(this).height() / 2);
    											}
		    							});						
				return false;
			}		
			
			/*if(this.prevRequest===JSON.stringify(movieData)){        		
        		$().message('Result already displayed',true);
        		return false;
        	}*/
			
			trimmedMovieTitle = movieTitle.replace(/[^a-zA-Z0-9_-]/g,'');
			//add a tree node with the search term only if it doesn't exist already
			if($('#'+trimmedMovieTitle).length<=0){
				$(searchItemTmpl.tmpl({
					"searchTerm" : trimmedMovieTitle,
					"movieTitle" : movieTitle
				})).appendTo(contentArea.children('ul'));								
			}
			
			//close the tree nodes of the existing items in the tree
    		$('#movieList').find('.searchTermTreeIcon').siblings('input[type=checkbox]').removeAttr('checked');	      
    		
    		//add a loading icon to the corresponding tree node
			$('#'+trimmedMovieTitle).siblings('label').addClass('loading');
					
			//bind the removeTreeNode() function to the spans having the class "removeTreeNode" only once
        	$('.removeTreeNode',that.$ctx).off('click',$.proxy(that.removeTreeNode,that));
        	$('.removeTreeNode', that.$ctx).on('click',$.proxy(that.removeTreeNode,that));
			
        	this.partialJSONString = '';
        	this.subSocket.response.request.method='POST';
        	this.subSocket.response.request.url=this.$ctx.data('search-url');
        	this.subSocket.push(JSON.stringify(movieData));        
        	//this.prevRequest = JSON.stringify(movieData);
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
			
			MovieData.title = this.unesco($.trim($($el).parents('div.tab-pane.active').children('ul').find('span.title').html()));
			MovieData.site = this.unesco($.trim($($el).parents('div.tab-pane.active').children('ul').find('span.movieSite').html().toLowerCase()));
			MovieData.year = this.unesco($($el).parents('div.tab-pane.active').children('table').find('td.year').html().replace(/\D+/gi,''));
			MovieData.director = this.unesco($.trim($($el).parents('div.tab-pane.active').children('table').find('td.director').html()));
			MovieData.description = this.unesco($.trim($($el).parents('div.tab-pane.active').children('table').find('td.description').html()));
			MovieData.cast = this.unesco($.trim($($el).parents('div.tab-pane.active').children('table').find('td.cast').html()));
			MovieData.genre = this.unesco($.trim($($el).parents('div.tab-pane.active').children('table').find('td.genre').html()));
			MovieData.rate = this.unesco($.trim($($el).parents('div.tab-pane.active').children('table').find('td.rate').html()));
			MovieData.runtime = this.unesco($($el).parents('div.tab-pane.active').children('table').find('td.runtime').html().replace(/\D+/gi,''));
    		MovieData.userRating = this.unesco($($el).parents('div.tab-pane.active').children('table').find('select.userRating :selected').val());
    		MovieData.movieStatus = this.unesco($($el).parents('div.tab-pane.active').children('table').find('select.movieStatus :selected').val());
    		MovieData.shelfLocation = this.unesco($($el).parents('div.tab-pane.active').children('table').find('input.shelfLocation').val());    		
    		MovieData.lentTo = this.unesco($($el).parents('div.tab-pane.active').children('table').find('input.lentTo').val());
    		MovieData.ownMovieNotes = this.unesco($($el).parents('div.tab-pane.active').children('table').find('textarea.ownMovieNotes').val());
    		MovieData.idOnSite = this.unesco($($el).parents('div.tab-pane.active').attr('id'));
    		MovieData.userId = '';
    		MovieData.loanDate = '';
    		MovieData.returnDate = '';
    		
    		this.subSocket.response.request.method='POST';
			this.subSocket.response.request.url=this.$msg.data('savemovieUrl');
        	this.subSocket.push(JSON.stringify(MovieData));	
		},
		
		/**Removes the selected tab from the DetailedMovieInfo section*/
		removeTab : function(e){
			var $el = e.target,			
				correspondingContentDiv = $($el).parents('a').attr('href'),
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
			var $el = e.target,		
				correspondingTree = $($el).closest('li');			
				$(correspondingTree).remove();
		},
		
		/**Unescapes the HTML encoded characters from the HTML string given as a parameter*/
		unesco : function (htmlString){
			  return $('<div/>').html(htmlString).text();
		},
		
		/**Lets the user choose if he wants to change the language, because the current content will be lost */
		confirmLanguageChange : function(e){
			var $el = e.target, 
				that=this;
			//show a warning dialog box only if there is movie information on the page
			if(($('#movieList ul').html()!=='')||($('#movieTabHeader').html()!=='')){
				$('#confirmLanguageChangeModalBody').attr('class', 'modal-body alert alert-warning');
		    	$('#confirmLanguageChangeModal').modal();
				//Set the URL for the target language that the user wants to see
		    	that.targetLanguage = $($el).attr('href');
				return false;	
			}
			
		},
		
		/**Reloads the page having the newly selected language*/
		changeLanguage : function(){			
			document.location.href = this.targetLanguage;
		},
		
		/**Shows the received movie info appropriately in the SearchPage*/
		showMovieInfo : function(MovieData){
			var contentArea = $('.search-results',this.$ctx),
    		searchItemTmpl = $('#searchItemTmpl').val(),		        
    		movieDataSourceTmpl = $('#movieDataSourceTmpl').val(),
    		movieItemTmpl = $('#movieItemTmpl').val(),
    		noMovieFoundTmpl = $('#noMovieFoundTmpl').val(),
    		oneMovieFoundTmpl = $('#oneMovieFoundTmpl').val(),
    		detailedMovieItemTabHeader = $('#detailedMovieItemTabHeader').val(),
    		detailedMovieItemTabContent = $('#detailedMovieItemTabContent').val(),
    		movieTitle = $('.movie-title',this.$ctx).val(),     	
    		site = null,
    		movieandsite = null,
    		that=this,
    		treeTitlesArray = [],
    		currentNode=null,
    		trimmedMovieTitle='';			
			
			if($.isArray(MovieData)&&(MovieData[0].title!==undefined)){//->we received Brief Movie Info for multiple movies from the selected infosources
        		trimmedMovieTitle = movieTitle.replace(/[^a-zA-Z0-9_-]/g,'');
        		movieandsite = trimmedMovieTitle+MovieData[0].site;
        		currentNode = $("#"+trimmedMovieTitle).siblings('.'+trimmedMovieTitle).find('.'+movieandsite);	        		

        		//remove the loading icon
        		$('.'+trimmedMovieTitle).siblings('label').removeClass('loading');
        		  	
        		//open the tree node of the current search term
        		$('#'+trimmedMovieTitle).attr('checked',true);
        		
        		// search if a node with the same info already exists in the tree 
        		//  and if the node doesn't already exist, add it
        		if(currentNode.length<=0){
        			$(movieDataSourceTmpl.tmpl({
						"site" : MovieData[0].site,
						"movieandsite" : movieandsite
					})).appendTo($('.'+trimmedMovieTitle));	
        			
        			//open the tree node of the current infosource
	        		$('#'+movieandsite).attr('checked',true);	
					
					$.each(MovieData,function(index, value){
		        		$(movieItemTmpl.tmpl({
							"title" : value.title,
							"year" : value.year,
							"director" : value.director,
							"uniqueid" : value.id.replace(/[^a-zA-Z0-9_-]/g,''),								
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
        		
        		//if the result came after a server Redirect(e.g.: one result found on Filmkatalogus when searching for a movie)
        		if(that.selectedMovieId===''){
        			trimmedMovieTitle = movieTitle.replace(/[^a-zA-Z0-9_-]/g,'');
        			movieandsite = trimmedMovieTitle+MovieData.site;	        			
        			//add the node in the tree only if it doesn't exist already
        			if($('#'+movieandsite).length<=0){
        				$(oneMovieFoundTmpl.tmpl({
							"site" : MovieData.site,
							"movieandsite" : movieandsite
						})).appendTo($('.'+trimmedMovieTitle));	
        				
        				//open the tree node of the current infosource
		        		$('#'+movieandsite).attr('checked',true);	
        			}	        				
        			that.selectedMovieId = trimmedMovieTitle;
        		}
        		
    			//remove the loading icon after the detailed movie info has been retrieved		        		
        		$('#'+that.selectedMovieId).siblings('label').removeClass('loading');	
        		
        		//only add a new tab with a certain id if it doesn't exist already
        		if($('#tab'+that.selectedMovieId).length<=0){
        			$(detailedMovieItemTabHeader.tmpl({	        			
	        			"movieTitle" : MovieData.title,
	        			"site" : MovieData.site.toUpperCase(),
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
        		
        		//open the tab corresponding to the movie received from the server
        		$('#movieTabHeader a[href="#tab'+that.selectedMovieId+'"]').tab('show');
        			        		
        		//bind the removeTab() function to the elements which have the class ".closeTab" only once
        		$('.closeTab',this.$ctx).off('click',$.proxy(this.removeTab,this));
	        	$('.closeTab', this.$ctx).on('click',$.proxy(this.removeTab,this));	 
	        	
        		//bind the saveMovieInDb() function to the elements which have the class ".addToDB" only once
	        	$('.addToDB', this.$ctx).off('click', $.proxy(this.saveMovieInDb,this));
	        	$('.addToDB', this.$ctx).on('click', $.proxy(this.saveMovieInDb,this));
	        			        	
	        	that.selectedMovieId = '';
        			        	
        	}else if((MovieData!==null)&&(MovieData.title===undefined)){//->We didn't receive any movie info from the selected infosource
        		trimmedMovieTitle = movieTitle.replace(/[^a-zA-Z0-9_-]/g,'');
    			movieandsite = trimmedMovieTitle+MovieData[0].site;
    			currentNode = $("#"+trimmedMovieTitle).siblings('.'+trimmedMovieTitle).find('.'+movieandsite);	        		

        		//remove the loading icon
        		$('.'+trimmedMovieTitle).siblings('label').removeClass('loading');	       
        		//open the tree node of the current search term
        		$('#'+trimmedMovieTitle).attr('checked',true);
        		// search if a node with the same info already exists in the tree 
        		// and if the node doesn't already exist, add it 
        		if(currentNode.length<=0){
        			$(noMovieFoundTmpl.tmpl({
						"site" : MovieData[0].site,
						"movieandsite" : movieandsite
					})).appendTo($('.'+trimmedMovieTitle));	
        			
        			//open the tree node of the current infosource
	        		$('#'+movieandsite).attr('checked',true);	
        		}	        		        		        	       		        	
        	}
		}
				
	});
	
	/* Attach page specific behavior on page load */
	$(function() {		
		return new window[NS][SubClass]();
	});
}(window.jQuery, "WMC", "Base", "SearchPage"));
