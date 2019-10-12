//differenza tra il mio codice e quello di partenza, inizialmente voglio creare aree
//SOLO per le parole trovate dalla ricerca e non tutte
function createAreas(div_id, image_path, map_name, page_name, coordD, coordP ) {
  var root_div = document.getElementById(div_id); //div della pagina documento.html,
                                                  //  dove verrà caricata l'immagine
  var map_ele = document.createElement("map");
  // image-map, ovvero un immagine con aree cliccabili
  var img_ele = document.createElement("img");
  //il documento

  map_ele.name = map_name;
  img_ele.src = image_path;
  img_ele.classList.add('map'); //utilizzo questa classe con maplight
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
  console.log(abs_x);
  console.log(abs_y);
 //ottengo oggetto contenente le coppie chiave valore dei box in cui c'è stato matching
  var page_bbxs = coordP;

/*sttuttura del file
"numPag_xAreaTesto_yAreaTesto_larghezzaAreaTesto_altezzaAreaTesto": {
    "xBoxParola_yBoxParola_larghezzaBoxParola_altezzaBoxParola": "trascrizione1\ntrascrizione2\ntrascrizione3",
*/

var i=0;
  coordP.forEach(
    function(){
      /*costruisco le coordinate*/
      var x1 = page_bbxs.x + abs_x; //xBox parola+ xAreaTesto
      var y1 = page_bbxs.y + abs_y;
      var x2 = x1 + page_bbxs.width;
      var y2 = y1 + page_bbxs.height ;  //angolo opposto all'origine

      /*creo un nuovo tag <area> con i valori trovati*/
      // il tag area permette di definire nuove aree per image map
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
      area_ele.title = coordP[i];

      map_ele.appendChild(area_ele);
      i= i+1;
  });

  root_div.appendChild(map_ele);
  root_div.appendChild(img_ele);
}

// $("area").filter( function(i){ return $(this).attr( "title" ).includes("latin") }).data('maphilight', {alwaysOn: false}).trigger('alwaysOn.maphilight');