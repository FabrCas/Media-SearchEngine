	function cambiaColoreSopra(id){
		console.log("cambio colore: " + id);
		  document.getElementById(id).style.color="Cyan"; 	  
		}
	
	function cambiaColorefuori(id){
		console.log("cambio colore: " + id);
		document.getElementById(id).style.color="black"; 	
		}
	
	function apri(nomeFile){
		console.log(nomeFile);
		window.open("/documents/"+nomeFile , "_blank");
	}