const form = document.querySelector('form');
const nomeInput = document.querySelector('.nome');
const emailInput = document.querySelector('.email');
const senhaInput = document.querySelector('.senha');



function atualizar() {

    fetch('http://localhost:3000/cadastro',
        {
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify({
                nome: nomeInput.value,
                email: emailInput.value,
                senha: senhaInput.value,
    
            })
        })
        .then(function (response) { console.log(response) })
        .catch(function (response) { console.log(response) })
    
}

function limparCampos() {
    nomeInput.value = '';
    emailInput.value = '';
    senhaInput.value = '';
    
}

form.addEventListener('submit', function(event) {
    event.preventDefault();

    atualizar();
    limparCampos();
});

