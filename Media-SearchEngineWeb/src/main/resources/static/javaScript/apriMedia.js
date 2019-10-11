	function cambiaColoreSopra(id){
		console.log("cambio colore: " + id);
		  document.getElementById(id).style.color="Cyan"; 	  
		}
	
	function cambiaColorefuori(id){
		console.log("cambio colore: " + id);
		document.getElementById(id).style.color="black";
		}
	
	
	function apri(nomeFile){
		//console.log(nomeFile);
		//window.location = 'prova.html';
		//window.open("/documents/"+nomeFile+".jpg" , "_blank");
		//window.open("/resources/templates/prova.html");
		//let docPage = window.open("/toDoc");
//		let docPage = window.open("javaScript/prova.html");
//		console.log(docPage.location.href);
		//docPage.document.write("<html><head> <meta charset='utf-8'><title>prova</title></head><div>prova riuscita2</div><body></body></html>")
		//console.log(docPage.document);
//	    $.ajax({
//	      type: "POST",
//	      url : 'toDoc',
//	      data: 'file='+ nomeFile,
//	      success: function (data){
//	    	  var a= data;
//	    	  var page= window.open("");
//	    	  page.document.write(a);
//	    	  
//		      },
//		      error : function(){
//		    	  window.alert("Errore");  	
//		      }
//	    });
		
//		var url="javaScript/prova.html";
//		
//		let win= window.location.replace(url);
//		$(document).ready(function(){
//		$(this).load(function() {
//		  $('#id').append("<p>Result</p>");
//		});
//		});
//		console.log("eseguito");
		var popup = window.open('javaScript/prova.html');
		popup.document.$('body').html('test');
		
	}