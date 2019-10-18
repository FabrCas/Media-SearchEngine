
function caricaMedia(documenti){
	let images= [];
	documenti.forEach(function(name){
		image = new Image();   // create image
		image.title= name;
		image.onload= function (){
			console.log("caricato"+ image.title);  
		}
		image.src = "/documents/" + name + ".jpg";
		images.push(image);    // push it onto the image array
	});	
	return images;
}