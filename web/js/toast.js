let body = document.querySelector('body');
/**
 * 
 * @param {Text} message message to show on toast
 * @param {Number} seconds time before it dissapears
 */
function buildToast(message, seconds){
    body.innerHTML += 
    `<div class="toast">
        <p class="toast-message">${message}</p>
    </div>`;
    let toast = body.querySelector('.toast');
    setInterval(function(){
        toast.remove();
    }, seconds);
}