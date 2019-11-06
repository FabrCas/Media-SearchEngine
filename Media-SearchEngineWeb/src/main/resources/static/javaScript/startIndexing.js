$(document).ready(function(){
	  $("#indexB").click(function(){
	    $.ajax({
	      url : 'Indexing',
	      beforeSend: function (){
	    	  $("#loading").html("Indexing in progress, please wait...");
	      },
	      success: function (data){
	        window.alert(data);
	        $("#loading").remove();
	      },
	      error : function(){
	    	  window.alert("Errore nell'indicizzazione");
	    	  $("#loading").remove();    	
	      }
	    });
	  });
	});