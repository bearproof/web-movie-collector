var oTable;
var selected_id;
$(document).ready(function() {
	
	/*Make the link to the corresponding page in the navbar active*/
	$('ul.nav li').each(function(){
		$(this).removeClass('active');
	});
	$('#domainPageLink').addClass('active');
	
	oTable = $('#movietable').dataTable({
		"sDom": 'l<"toolbar">frtip',
		"bFilter" : false,
		"bStateSave" : false,
		"aaSorting" : [ [ 1, "asc" ] ],
		"sPaginationType" : "bootstrap",
		"oLanguage" : {
			"sLengthMenu" : "Display _MENU_ records per page",
			"sZeroRecords" : "Nothing found - sorry",
			"sInfo" : "Showing _START_ to _END_ of _TOTAL_ records",
			"sInfoEmpty" : "Showing 0 to 0 of 0 records"
		},
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : "/domain/movies/",
		"aoColumns" : [ {"mData" : "id" },
		                {"mData" : "title"},
		                {"mData" : "year"}, 
		                {"mData" : "genre"},
		                {"mData" : "director"},
		                {"mData" : "runtime"}
		               ],
		"aoColumnDefs": [
		                 { "sName": "id",   "aTargets": [ 0 ] },
		                 { "sName": "title",  "aTargets": [ 1 ] },
		                 { "sName": "year", "aTargets": [ 2 ] },
		                 { "sName": "genre",  "aTargets": [ 3 ] },
		                 { "sName": "director",    "aTargets": [ 4 ] },
		                 { "sName": "runtime",    "aTargets": [ 5 ] },
		                 { "sClass" : "hide",   "aTargets" : [ 0 ]}
		               ],           
	  "fnDrawCallback": function ( oSettings ) {
		                   $('#movietable tbody tr').each( function () {
		                	   selected_id = null;
		                       $(this).click( function () {
		                    	   oTable.$('tr.row_selected').removeClass('row_selected');
		                           $(this).addClass('row_selected');
		                           selected_id =  $(this).children(":first").html();
		                       });
		                       $(this).hover( function () {
		                    	   $(this).addClass( 'row_highlighted' )
		                       }, function(){
		                    	   $(this).removeClass('row_highlighted');
		                       });
		                   });
		               }
	});
	initCrudButtons();
});

/**
 * Initializing crud buttons 
 */
function initCrudButtons(){
	$("div.toolbar").html('<a href="/domain/movies/create" id="bt_add" class="btn btn-primary">Create</a> <a href="#" id="bt_update" class="btn btn-success">Edit</a> <button id="bt_delete" type="button" class="btn btn-danger">Delete</button>');
	
	$("#bt_update").click(function() {
		if (selected_id == null) {
	    	  $('#errorModalLabel').html('Warning:');
	    	  $('#errorModalMsg').html('Please select a record first.');
	    	  $('#errorModelBody').attr('class', 'modal-body alert alert-warning');
	    	  $('#errorModal').modal();
		} else {
			$("#bt_update").attr("href", "/domain/movies/update/" + selected_id);
		}
	});
	
	$("#bt_delete").click(function() {
		if (selected_id == null) {
	    	  $('#errorModalLabel').html('Warning:');
	    	  $('#errorModalMsg').html('Please select a record first.');
	    	  $('#errorModelBody').attr('class', 'modal-body alert alert-warning');
	    	  $('#errorModal').modal();
		} else {
			if(confirm('Are you sure you want to delete the selected account?')){
				deleteAccount(selected_id);	
			}
		}
	});
	
}

/**
 * Delete button handler
 * @param type
 * @param id
 */
function deleteAccount(id){
	$.ajax({
        type: "DELETE",
        url: '/domain/movies/'+id,
        success: function(response,status,xhr){
            	oTable.$('tr.row_selected').remove();
            	selected_id = null;
        },
	    error: function (xhr, ajaxOptions, thrownError) {
	  	  $('#errorModalLabel').html("Error Server "+xhr.status+":");
	  	  $('#errorModalMsg').html(xhr.responseText);
	  	  $('#errorModelBody').attr('class', 'modal-body error alert-error');
	  	  $('#errorModal').modal();
	    }
   });
}


	

