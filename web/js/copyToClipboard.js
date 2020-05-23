/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function copyToClipboard(e, column_index, link){
    let col = e.target.closest('tr').cells[column_index];
    col.innerHTML +=
    `<input type='text' style='margin:0;padding:0;border:0;width:0;font-size:1px;' value='${link}${col.textContent}' />`;            
    let text = col.firstElementChild;
    text.select();
    text.setSelectionRange(0, 99999);
    document.execCommand('copy');
    alert('Link copiado: '+text.value);
    text.remove();
}
