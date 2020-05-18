/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


const modal = document.querySelector(".modal");
const modal__msg = document.querySelector(".modal__msg");
const modal__btn = document.querySelector(".modal__btn");

function showMessage(msg, btn_msg){
    modal__msg.textContent = msg;
    modal__btn.textContent = btn_msg;
    modal.classList.remove("hide__modal");
}

modal__btn.addEventListener('click', function(e){
    modal.classList.add("hide__modal");
    e.preventDefault();
});