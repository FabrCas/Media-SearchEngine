
function riempiCanvas(titolo, indice, box, testo, images){
	$(window).on("load", function() {
		//riferimento all'elemento html canvas
		var canvas= document.getElementById(indice+titolo); 

		canvas.width= canvas.scrollWidth;   
		canvas.height= canvas.scrollHeight; 
		// riferimento al contesto api 2D 
		var ctx = canvas.getContext('2d'); 

		var imageCor;
		var lunghezza= images.length;
		/*prendo l'immagine giusta per lo snippet*/
		function getImage(){
			for (i = 0; i < lunghezza; i++) {
				if(images[i].title== titolo){
					return images[i];
				}
			}
		}
		imageCor= getImage();
		imageCor.classList.add('mapHit');
		
		
		
		var y= box.y + testo.y;
		var x= testo.x; 
		//var larghezza= testo.width;
		//var altezza= box.height + 5 ; //margine per righe poco dritte

		/*la prima riga ha dei volori di x e y, poco centrati con la parola.
		 * quando ci saranno le righe: se riga è 1, sfasare la y*/
		if(box.y<70){
			y= y+35;
		}

		ctx.drawImage(imageCor, -x , -y);  //aggiungere 90 a x per box con margini ampi
		evidenzia(ctx, box, testo);
	});
}


function evidenzia(ctx,box, testo){
	ctx.fillStyle = 'rgba(225,255,0,0.5)';;
	ctx.fillRect(box.x, 0, box.width, 50);
}