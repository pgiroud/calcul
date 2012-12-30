{
url_name = " "+window.document.location;
if ((parent.document.location==window.document.location) && (url_name.indexOf("?net") == -1) && !(document.layers))
{
document.write('<div align="center"><center>');
document.write('<link rel="stylesheet" href="/legislation/rsg/f/s/style/normal.css" type="text/css">');
document.write('<link rel="stylesheet" href="/legislation/chancellerie.css" type="text/css">');



//            Bandeau 

document.write('<table border="0" cellspacing="0" width="90%">');
document.write('  <tr> ');
document.write('  <td align="center" width="10%" background="/legislation/images/bandeau_fond.jpg"><a name="top"></a><a href="http://www.ge.ch"><img width="33" height="56" alt="Ecusson de l&quot;Etat de Gen&egrave;ve" border="0" src="/legislation/images/ecusson_fond.jpg"></a></td>');
document.write('    <td width="30%" background="/legislation/images/bandeau_fond.jpg">');
document.write("  <h1>Site officiel de l'Etat de Gen&egrave;ve</h1></td>");
document.write('    <td align="right" width="40%" background="/legislation/images/bandeau_fond.jpg" class="texte"> ');
document.write('        <a href="http://www.ge.ch" target="_top">Accueil</a> | ');
document.write('        <a href="http://www.ge.ch/rechercher/welcome.asp" target="_top">Recherche</a> |');
document.write('        <a href="http://www.ge.ch/annuaire/welcome.html" target="_top">Annuaire</a> |');
document.write('        <a href="http://www.ge.ch/organisation/welcome.asp" target="_top">D&eacute;partements</a>&nbsp;');
document.write('    <td align="right" width="1%" background="/legislation/images/bandeau_fond.jpg">&nbsp;</td>');
document.write('  </tr>');
document.write('</table>');

//             Fin bandeau
document.write('<table border="0" cellspacing="0" width="90%">');
document.write('<tr align="center">  <td valign="top" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">');


//document.write('<tr> <td width="300" class="titre"><h4><a href="/legislation/main/welcome.html">L&eacute;gislation genevoise</a></h4></td><td width="200" >');

document.write('<tr> <td width="300" class="titre"><h4><a href="/legislation" target="_top">L&eacute;gislation genevoise</a></h4></td><td width="200" >');


document.write('</tr> </table>');



document.write('<table border="0" cellspacing="0" cellpadding="0" width="100%">');


document.write('<td background="/chancellerie/images/spacer.gif" width="1" height="1"></td>');




//document.write('<a href="/chancellerie/welcome.html"><img src="/chancellerie/images/pt_logo.gif" width="38" height="22"alt="Vers la Chancellerie genevoise" border=0></a>');
document.write('</tr> </table> ');
document.write('<hr>');

document.write('<table border="0" cellspacing="0" cellpadding="0" width="100%"> <tr> ');
document.write('<td valign="top"> <br> <table border="0" width="100%"> <tr>  <td valign="top" align="left">');





}
}


function BandChange(form) 

{ 		
	
	var myindex=form.SelectedBand.selectedIndex;
	var myLocation =  form.SelectedBand.options[myindex].value;		
	window.location.href = myLocation; 	

}

