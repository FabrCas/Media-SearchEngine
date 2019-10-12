
function createAreas(div_id, image_path, map_name, page_name, coordD, coordP ) {
  var root_div = document.getElementById(div_id);
  var map_ele = document.createElement("map");
  var img_ele = document.createElement("img");

  map_ele.name = map_name;
  img_ele.src = image_path;
  img_ele.classList.add('map');
  img_ele.useMap = "#" + map_name;  //attributo necessario al plugin maphilight

  var pagename = image_path.split('/')[1].split('.')[0]; /*prende page? */
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
      area_ele.title = page_bbxs[k]

      map_ele.appendChild(area_ele);
    }
  );

  root_div.appendChild(map_ele);
  root_div.appendChild(img_ele);
}

// $("area").filter( function(i){ return $(this).attr( "title" ).includes("latin") }).data('maphilight', {alwaysOn: false}).trigger('alwaysOn.maphilight');
