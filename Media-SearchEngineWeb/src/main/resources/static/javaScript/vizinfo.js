//funzione per evidenziare le hit della ricerca
function createAreasHit(div_id, image_path, map_name, page_name, coordD, coordP ) {
  var root_div = document.getElementById(div_id); //div della pagina documento.html,
                                                  //  dove verrà caricata l'immagine
  var map_ele = document.createElement("map");
  // image-map, ovvero un immagine con aree cliccabili
  var img_ele = document.createElement("img");
  //il documento

  map_ele.name = map_name;
  //map.ele.classList.add('mapHit');
  img_ele.id
  img_ele.src = image_path;
  img_ele.classList.add('mapHit'); //utilizzo questa classe con maplight
  img_ele.useMap = "#" + map_name;  //attributo necessario al plugin maphilight

  var pagename = page_name; /*prende page? */
  /*bbxs variabile in scripts/gen_transcriptions_nodup.js*/
  /*Il costruttore Object crea un oggetto avente il valore dato. Se il valore è
  null o undefined, verrà creato un oggetto vuoto; altrimenti un oggetto del tipo
  corrispondente al valore dato. Se il valore è già un oggetto, verra restituito
  senza alcuna modifica.
  /*Object.keys() Restituisce un array contenente i nomi di tutte le proprietà
   enumerabili dell'oggetto.*/
  var pagekey = coordD;
    /*ottengo la posizione x dell' area di testo*/
  var abs_x = pagekey.x;  //Number() costrutor, number-> numeric data typer 64 bit
  /*ottengo la posizione x dell' area di testo*/
  var abs_y = pagekey.y;
  console.log("x doc: "+abs_x);
  console.log("y doc: "+abs_y);
 //ottengo oggetto contenente le coppie chiave valore dei box in cui c'è stato matching
  var page_bbxs = coordP;
  
/*sttuttura del file
"numPag_xAreaTesto_yAreaTesto_larghezzaAreaTesto_altezzaAreaTesto": {
    "xBoxParola_yBoxParola_larghezzaBoxParola_altezzaBoxParola": "trascrizione1\ntrascrizione2\ntrascrizione3",
*/
var i;
var lunghezza= page_bbxs.length;
  for(i=0; i< lunghezza; i++){

    
      /*costruisco le coordinate*/
      var x1 = page_bbxs[i].x + abs_x; //xBox parola+ xAreaTesto
      var y1 = page_bbxs[i].y + abs_y;
      var x2 = x1 + page_bbxs[i].width;
      var y2 = y1 + page_bbxs[i].height ;  //angolo opposto all'origine
      
      /*creo un nuovo tag <area> con i valori trovati*/
      // il tag area permette di definire nuove aree per image map
      var area_ele = document.createElement("area");
      area_ele.shape = 'rect';
      area_ele.coords = x1+","+y1+","+x2+","+y2;
      area_ele.title = page_bbxs[i].trascriptions
      $(area_ele).data('maphilight', {alwaysOn: true})
      
/*The main use of anchor tags - <a></a> - is as hyperlinks. That basically means
 that they take you somewhere. Hyperlinks require the href property, because it
  specifies a location.
  href="#" doesn't specify an id name, but does have a corresponding location -
  the top of the page. Clicking an anchor with href="#" will move the scroll
  position to the top.
  #->primo elemento*/
      area_ele.href = "#";
      map_ele.appendChild(area_ele);
  }


  createAreas(root_div, image_path,img_ele, map_ele);
}

// $("area").filter( function(i){ return $(this).attr( "title" ).includes("latin") }).data('maphilight', {alwaysOn: false}).trigger('alwaysOn.maphilight');

//funzione per evidenziare e avere info delle trascrizioni passando il cursore sopra i boxes
function createAreas(root_div, image_path, img_ele, map_ele) {
	
	 //var map_ele2 = document.createElement("map");
	  //img_ele.classList.add('mapHit');

	  var pagename = image_path.split('/')[2].split('.')[0]; /*prende page? */
	  console.log(pagename);
	  /*bbxs variabile in scripts/gen_transcriptions_nodup.js*/
	  /*Il costruttore Object crea un oggetto avente il valore dato. Se il valore è
	  null o undefined, verrà creato un oggetto vuoto; altrimenti un oggetto del tipo
	  corrispondente al valore dato. Se il valore è già un oggetto, verra restituito
	  senza alcuna modifica.
	  /*Object.keys() Restituisce un array contenente i nomi di tutte le proprietà
	   enumerabili dell'oggetto.*/
	  var pagekey = Object.keys(bbxs).filter(
	    function(k, i){ return k.startsWith(pagename) })[0];
	    /*ottengo la posizione x dell' area di testo*/
	  var abs_x = Number(pagekey.split('_')[1]);  //Number() costrutor, number-> numeric data typer 64 bit
	  /*ottengo la posizione x dell' area di testo*/
	  var abs_y = Number(pagekey.split('_')[2]);
	  var page_bbxs = bbxs[pagekey]; //ottengo oggetto contenente le coppie chiave valore dei box

	/*sttuttura del file
	"numPag_xAreaTesto_yAreaTesto_larghezzaAreaTesto_altezzaAreaTesto": {
	    "xBoxParola_yBoxParola_larghezzaBoxParola_altezzaBoxParola": "trascrizione1\ntrascrizione2\ntrascrizione3",
	*/


	  Object.keys(page_bbxs).forEach(
	    function(k, i){
	      /*costruisco le coordinate*/
	      var coords = k.split('_');
	      var x1 = Number(coords[0]) + abs_x; //xBox parola+ xAreaTesto
	      var y1 = Number(coords[1]) + abs_y;
	      var x2 = x1 + Number(coords[2]);
	      var y2 = y1 + Number(coords[3]);  //angolo opposto all'origine

	      /*creo un nuovo tag <area> con i valori trovati*/
	      var area_ele = document.createElement("area");
	      area_ele.shape = 'rect';
	      area_ele.coords = x1+","+y1+","+x2+","+y2;

	/*The main use of anchor tags - <a></a> - is as hyperlinks. That basically means
	 that they take you somewhere. Hyperlinks require the href property, because it
	  specifies a location.
	  href="#" doesn't specify an id name, but does have a corresponding location -
	  the top of the page. Clicking an anchor with href="#" will move the scroll
	  position to the top.
	  #->primo elemento*/
	      area_ele.href = "#";
	      area_ele.title = page_bbxs[k];
	      console.log(area_ele);
	    //  $.maphilight.defaults;
	    $(area_ele).data('maphilight', {alwaysOn: false}).trigger('alwaysOn.maphilight');
	     map_ele.appendChild(area_ele);
	    }
	  );
	 

	  root_div.appendChild(map_ele);
	  root_div.appendChild(img_ele);
	}

	// $("area").filter( function(i){ return $(this).attr( "title" ).includes("latin") }).data('maphilight', {alwaysOn: false}).trigger('alwaysOn.maphilight');
