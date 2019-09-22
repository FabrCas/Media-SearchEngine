$(document).ready(function(){
	  $("#indexB").click(function(){
	    $.ajax({
	      url : 'Indexing',
	      success: function (data){
	        window.alert(data);
	      },
	      error : function(){
	    	  window.alert("Errore nell'indicizzazione");
	      }
	    });
	  });
	});