/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function copyToClipboard(e, column_index, link){
    let col = e.target.closest('tr').cells[column_index];
    col.innerHTML +=
    `<input type='text' style='' value='${link}${col.textContent}' />`;    
    let text = col.firstElementChild;
    copy(text, true);
    buildToast('Link copiado a portapapeles', 3000);
}
