function cambiarColor(){
    var bodyColor = document.body.style.backgroundColor;
    if(bodyColor == ''){
        document.body.style.backgroundColor = 'black';
    }
    else{
        document.body.style.backgroundColor = '';
    }
}