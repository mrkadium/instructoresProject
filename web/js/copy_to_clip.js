/**
 * 
 * @param {HTMLInputElement} textField textfield with the text to copy
 * @param {Boolean} remove tells if textfield is going to be removed after the operation
 */
function copy(textField, remove = false){
    textField.select();
    textField.setSelectionRange(0, 99999);
    document.execCommand('copy');
    remove ? textField.remove() : '';
}