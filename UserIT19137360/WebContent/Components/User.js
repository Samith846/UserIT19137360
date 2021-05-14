$(document).ready(function()
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT"; 
$.ajax( 
{ 
		url : "UserAPI", 
		type : type, 
		data : $("#formUser").serialize(), 
		dataType : "text", 
		complete : function(response, status) 
		{ 
			onUserSaveComplete(response.responseText, status); 
		} 
	}); 
});

function onUserSaveComplete(response, status)
{ 
if (status == "success") 
 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
		 $("#alertSuccess").text("Successfully saved."); 
		 $("#alertSuccess").show();
		 
		 $("#divItemsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
		 $("#alertError").text(resultSet.data); 
		 $("#alertError").show(); 
	 } 
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
 }

	$("#hidUserIDSave").val(""); 
	$("#formUser")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
 $("#hidUserIDSave").val($(this).closest("tr").find('#hidUserIDUpdate').val()); 
 $("#Email").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#Name").val($(this).closest("tr").find('td:eq(1)').text()); 
 $("#Address").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#Contact").val($(this).closest("tr").find('td:eq(3)').text()); 
}); 

//REMOVE=======================================================

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		 url : "UserAPI", 
		 type : "DELETE", 
		 data : "userID=" + $(this).data("userid"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
			 onUserDeleteComplete(response.responseText, status); 
		 } 
	}); 
});

function onUserDeleteComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response); 
		if (resultSet.status.trim() == "success") 
		{ 
		 $("#alertSuccess").text("Successfully deleted."); 
		 $("#alertSuccess").show(); 
		 
		 $("#divItemsGrid").html(resultSet.data); 
		} else if (resultSet.status.trim() == "error") 
		{ 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		} 
		
 } else if (status == "error") 
 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
 } else
 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
 } 

}

// CLIENT-MODEL================================================================
function validateUserForm() 
{ 
// CODE
if ($("#Email").val().trim() == "") 
 { 
 return "Insert Email."; 
 } 
// NAME
if ($("#Name").val().trim() == "") 
 { 
 return "Insert Name."; 
 }

//PRICE-------------------------------
if ($("#Address").val().trim() == "") 
 { 
 return "Insert Address."; 
 } 
// is numerical value
var tmpPrice = $("#Address").val().trim(); 
if (!$.isNumeric(tmpPrice)) 
 { 
 return "Insert Address."; 
 } 
// convert to decimal price
 $("#Address").val(parseFloat(tmpPrice).toFixed(2)); 
// DESCRIPTION------------------------
if ($("#Contact").val().trim() == "") 
 { 
 return "Insert Contact."; 
 } 
return true; 
}