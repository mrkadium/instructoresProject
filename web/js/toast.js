let body = document.querySelector('body');
/**
 * 
 * @param {Text} message message to show on toast
 * @param {Number} seconds time before it dissapears
 */
function buildToast(message, seconds){
    let toastContainer = document.querySelector('.toast-container');
    toastContainer.innerHTML = `<div class="toast"> <p class="toast-message">${message}</p> </div>`;
    let toast = toastContainer.querySelector('.toast');
    setInterval(function(){
        toast.remove();
    }, seconds);
}