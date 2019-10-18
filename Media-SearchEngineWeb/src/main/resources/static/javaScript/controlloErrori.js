
function stampaErrore(errore){
	if (errore==1){
		window.alert("ricerca non valida: nessun carattere digitato");
	}
	if (errore==2){
		window.alert("ricerca non valida: solo spazi");
	}
	if (errore==3){
		window.alert("ricerca non valida: sintassi wildcard non permessa");
	}
}

function controlloErrore(errorCode){
	$(document).ready(function(){
	if(!(errorCode==0)){
		stampaErrore(errorCode);
	}
	});
}