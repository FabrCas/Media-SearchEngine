$(document).ready(function(){
	  $("#indexB").click(function(){
	    $.ajax({
	      url : 'Indexing',
	      beforeSend: function (){
	    	  $("#loading").html("Indicizzazione in corso...");
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