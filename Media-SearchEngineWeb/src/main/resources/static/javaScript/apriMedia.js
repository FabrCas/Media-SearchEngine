	function cambiaColoreSopra(id){
		console.log("cambio colore: " + id);
		  document.getElementById(id).style.color="Cyan"; 	  
		}
	
	function cambiaColorefuori(id){
		console.log("cambio colore: " + id);
		document.getElementById(id).style.color="black";
		}
	
	function gestisciClick(file, coordinate){
		apri(file);
		coloraSchermo(coordinate);
	}
	
	function apri(nomeFile){
		console.log(nomeFile);
		window.open("/documents/"+nomeFile , "_blank");
//		window.open("/documents/highlightYellow.jpg")
	}