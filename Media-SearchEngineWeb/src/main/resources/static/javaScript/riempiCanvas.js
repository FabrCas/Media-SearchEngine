function riempiCanvas(titolo, indice, box, testo){
	//$(document).ready(function(){
//console.log(testo);
//console.log(box);

/*riferimento all'elemento html canvas*/
var canvas= document.getElementById(indice+titolo); 

canvas.width= canvas.scrollWidth;   
canvas.height= canvas.scrollHeight; 

/* riferimento al contesto api 2D */
var ctx = canvas.getContext('2d'); 

var imageCor;
var imageSel;
var lunghezza= images.length;

function getImage(){
	for (i = 0; i < lunghezza; i++) {
		if(images[i].title== titolo){
			imageSel= images[i];
			return imageSel;
		}
	}
}

imageCor= getImage();

var y= box.y + testo.y;
var x= testo.x;
var larghezza= testo.width;
var altezza= box.height + 5 ; //margine per righe poco dritte

//da sistuire il valore della y con il numero della riga
if(box.y < 80 ){
	y= y+40;  //centro lo snippet nel caso in cui il box è nella prima riga (elimino il margine aggiuntivo)
}

console.log("*******************************");
console.log(x);
console.log(y);
console.log(larghezza);
console.log(altezza);
console.log("*******************************");

//imageCor.onload = function (){
ctx.drawImage(imageCor, -(x+90) , -y);
//}
//ctx.drawImage(imageCor, x, y, larghezza/2, altezza/2, 0, 0, canvas.width, canvas.height ); //,larghezza, altezza );
	//});
}