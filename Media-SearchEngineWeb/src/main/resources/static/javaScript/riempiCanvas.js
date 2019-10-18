
function riempiCanvas(titolo, indice, box, testo, images){
	$(document).ready(function(){
		console.log("caricamento canvas");
		//riferimento all'elemento html canvas
		var canvas= document.getElementById(indice+titolo); 
		
		canvas.width= canvas.scrollWidth;   
		canvas.height= canvas.scrollHeight; 
		// riferimento al contesto api 2D 
		var ctx = canvas.getContext('2d'); 
		
		var imageCor;
		var imageSel;
		var lunghezza= images.length;
		
		function getImage(){
		for (i = 0; i < lunghezza; i++) {
		  if(images[i].title== titolo){
		  imageSel= images[i];
		  return imageSel;
		 // console.log(images[i].title);
		  }
		}
		}
			
		imageCor= getImage();
		var y= box.y + testo.y;
		var x= testo.x;
		var larghezza= testo.width;
		var altezza= box.height + 5 ; //margine per righe più storte
		//console.log("*******************************");
		//console.log(x);
		//console.log(y);
		//console.log(larghezza);
		//console.log(altezza);
		//console.log("*******************************");
		
		//quando ci saranno le righe: se riga è 1, sfasare la y
		if(box.y<70){
			y= y+35;
		}
		
		$(window).on("load", function() {
			ctx.drawImage(imageCor, -(x+90) , -y);
		    // weave your magic here.
		});
		});
	console.log("fine inserimento canvas");
}