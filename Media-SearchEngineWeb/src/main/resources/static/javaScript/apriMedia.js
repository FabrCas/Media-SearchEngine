	function cambiaColoreSopra(id){
		console.log("cambio colore: " + id);
		  document.getElementById(id).style.color="Cyan"; 	  
		}
	
	function cambiaColorefuori(id){
		console.log("cambio colore: " + id);
		document.getElementById(id).style.color="black";
		}
	
	
	function apri(nomeFile){
		var popup = window.open('javaScript/prova.html');
		popup.document.$('body').html('test');
		
	}