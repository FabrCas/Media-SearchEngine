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
		//window.location = 'prova.html';
		//window.open("/documents/"+nomeFile+".jpg" , "_blank");
		//window.open("/resources/templates/prova.html");
		var docPage = window.open("");
		docPage.document.write("<html><head> <meta charset='utf-8'><title>prova</title></head><div>prova riuscita</div><body></body></html>")
	}