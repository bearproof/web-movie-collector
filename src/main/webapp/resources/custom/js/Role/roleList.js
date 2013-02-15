(function($, NS, SuperClass,SubClass) {
	window[NS][SubClass] = window[NS][SubClass] || window[NS][SuperClass].extend({
	toString:function(){return NS + '.' + SubClass;},
	oTable : null,
	selected_id : null,    
	/** Constructor. */
    init : function(cfg) {
      this.bindBehavior(this);
    },
    /**
     * Binds client-side behavior.
     */
    bindBehavior : function() {
    	this.initDataTable();
    	this.initCrudButtons();
    },
    /**
     * Creates the dataTable with the Roles List
     * */
    initDataTable : function(){
    	var that=this;
    	this.oTable = $('#roletable').dataTable({
    		"sDom": 'l<"toolbar">frtip',
    		"bFilter" : false,
    		"bStateSave" : false,
    		"aaSorting" : [ [ 1, "asc" ] ],
    		"sPaginationType" : "bootstrap",
    		"oLanguage" : {
    			"sLengthMenu" : "Display _MENU_ records per page",
    			"sZeroRecords" : "Nothing found - sorry",
    			"sInfo" : "Showing _START_ to _END_ of _TOTAL_ records",
    			"sInfoEmpty" : "Showing 0 to 0 of 0 records",
    		},
    		"bProcessing" : true,
    		"bServerSide" : true,
    		"sAjaxSource" : "/domain/roles/",
    		"aoColumns" : [ {"mData" : "id" },
    		                {"mData" : "roleName"}
    		               ],
    		"aoColumnDefs": [
    		                 { "sName": "id",   "aTargets": [ 0 ] },
    		                 { "sName": "roleName",  "aTargets": [ 1 ] },
    		                 { "sClass" : "hide",   "aTargets" : [ 0 ]}
    		               ],           
    	  "fnDrawCallback": function ( oSettings ) {
    		                   $('#roletable tbody tr').each( function () {
    		                	   that.selected_id = null;
    		                       $(this).click( function () {
    		                    	   that.oTable.$('tr.row_selected').removeClass('row_selected');
    		                           $(this).addClass('row_selected');
    		                           that.selected_id =  $(this).children(":first").html();
    		                       });
    		                       $(this).hover( function () {
    		                    	   $(this).addClass( 'row_highlighted' );
    		                       }, function(){
    		                    	   $(this).removeClass('row_highlighted');
    		                       });
    		                   });
    		               }
    	});
    	
    },
    /**
     * Initialize the Create, Edit and Delete Role buttons
     * */
    initCrudButtons : function (){
    	var that=this;
    	$("div.toolbar").html('<a href="/domain/roles/create" id="bt_add" class="btn btn-primary">Create</a> <a href="#" id="bt_update" class="btn btn-success">Edit</a> <button id="bt_delete" type="button" class="btn btn-danger">Delete</button>');
    	
    	$("#bt_update").click(function() {
    		if (that.selected_id == null) {
    	    	  $('#errorModalLabel').html('Warning:');
    	    	  $('#errorModalMsg').html('Please select a record first.');
    	    	  $('#errorModelBody').attr('class', 'modal-body alert alert-warning');
    	    	  $('#errorModal').modal();
    		} else {
    			$("#bt_update").attr("href", "/domain/roles/update/" + that.selected_id);
    		}
    	});
    	
    	$("#bt_delete").click(function() {
    		if (that.selected_id == null) {
    	    	  $('#errorModalLabel').html('Warning:');
    	    	  $('#errorModalMsg').html('Please select a record first.');
    	    	  $('#errorModelBody').attr('class', 'modal-body alert alert-warning');
    	    	  $('#errorModal').modal();
    		} else {
    			  /**Open a modal dialog to confirm the role delete operation*/	
    			  $('#confirmModalBody').attr('class', 'modal-body alert alert-warning');
    	    	  $('#confirmModal').modal();	
    	    	  $('#confirmModal button.btn-danger').off('click',$.proxy(that.deleteEntity,that));
    			  $('#confirmModal button.btn-danger').on('click',$.proxy(that.deleteEntity,that));	
    		}
    	});
    },
    /**
     * Deletes the selected record from the Roles Table
     * */
    deleteEntity : function (){
    	var that=this;
    	$.ajax({
            type: "DELETE",
            url: '/domain/roles/'+that.selected_id,
            success: function(response,status,xhr){
                	that.oTable.$('tr.row_selected').remove();
                	that.selected_id = null;
            },
    	      error: function (xhr, ajaxOptions, thrownError) {
    	    	  $('#errorModalLabel').html("Error Server "+xhr.status+":");
    	    	  $('#errorModalMsg').html(xhr.responseText);
    	    	  $('#errorModelBody').attr('class', 'modal-body error alert-error');
    	    	  $('#errorModal').modal();
    	        }
       });
    }
  });

  /* Attach page specific behavior on page load */
  $(function() {
	  return new window[NS][SubClass]();
  });
}(jQuery, "WMC","Base","RoleList"));