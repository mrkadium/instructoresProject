const button_gen_pwd = document.querySelector("#gen_pwd");
const gen_pwd_out = document.querySelector('.gen_pwd_out');

// const randonFunc = {
//     lower: getRandomLower,
//     upper: getRandomUpper,
//     number: getRandomNumber,
//     symbol: getRandomSymbol
// };

function genRandomLower(){
    return String.fromCharCode(Math.floor(Math.random() * 26) + 97);
}
function genRandomUpper(){
    return String.fromCharCode(Math.floor(Math.random() * 26) + 65);
}
function genRandomNumber(){
    return String.fromCharCode(Math.floor(Math.random() * 10) + 48);
}
function genRandomSymbol(){
    var symbols = '$%&#[_?@';
    return symbols[Math.floor(Math.random() * symbols.length)];
}

function generatePassworwd(){
    let passLength = 10;
    let typesCount = 2;
    let pass = '';
    for(let i = 0; i < passLength; i += typesCount){
        pass += 
        genRandomLower() + 
        genRandomUpper()
    }
    return pass;
}

if(button_gen_pwd){    
    button_gen_pwd.addEventListener('click', (e) => {
        if(gen_pwd_out.tagName == 'INPUT'){
            gen_pwd_out.value = generatePassworwd();
        }else{
            gen_pwd_out.innerText = generatePassworwd();
        }    
        e.preventDefault();
    });
}

if(gen_pwd_out.tagName == 'INPUT'){
    gen_pwd_out.value = generatePassworwd();
}else{
    gen_pwd_out.innerText = generatePassworwd();
} 