
function caricaMedia(documenti){
//	$(document).ready(function(){
	let images= [];
	documenti.forEach(function(name){
		image = new Image();   // create image
		image.title= name;
		image.onload= function (){
			//per permettere il caricamento dei media 
			console.log("caricato");   // set the src
			//window.stop();
		}
		image.src = "/documents/" + name + ".jpg";
		images.push(image);    // push it onto the image array
	});
//	});
}