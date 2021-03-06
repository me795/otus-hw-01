window.onload = function(){

}

function addButtonHandler(){
    const clientNameInput = document.getElementById("client_form_input_name");
    clientNameInput.classList.remove("error");
    const client = getClient();

    if (client.name.trim().length > 0){
            fetch('api/client', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(client)
                }).then(response => response.json())
                .then(client => addClientRecord(client));
        }else{
            clientNameInput.classList.add("error");
            alert("Не заполнено имя клиента!");
        }
}

function getClient(){
    const clientNameInput = document.getElementById("client_form_input_name");
    const clientAddressInput = document.getElementById("client_form_input_address");
    const clientPhoneInput = document.getElementById("client_form_input_phone");

    const client = {};
    const address = {};
    const phone = {};
    client.name = clientNameInput.value;
    if (clientAddressInput.value.trim().length > 0){
        address.street = clientAddressInput.value;
        client.address = address;
    }
    if (clientPhoneInput.value.trim().length > 0){
        phone.number = clientPhoneInput.value;
        client.phones = [];
        client.phones.push(phone);
    }

    return client;
}

function addClientRecord(client){
    console.log(client);

    const newClientRecord = document.createElement("tr");
    newClientRecord.classList.add("table_client_record");

    let cell = document.createElement("td");
    cell.innerHTML = client.id;
    newClientRecord.append(cell);

    cell = document.createElement("td");
    cell.innerHTML = client.name;
    newClientRecord.append(cell);

    cell = document.createElement("td");
    if (client.address != null)
        cell.innerHTML = client.address.street;
    newClientRecord.append(cell);

    cell = document.createElement("td");
    if (client.phones.length > 0){
        client.phones.forEach(phone => {
            cell.innerHTML += phone.number + "<br/>";
        });
    }
    newClientRecord.append(cell);

    const clientRecords = document.getElementsByClassName("table_client_record");

    if (clientRecords.length > 0){
            const lastClientRecord = clientRecords[clientRecords.length - 1];
            lastClientRecord.after(newClientRecord);
        }else{
            const tBody = document.getElementById("table_body");
            tBody.prepend(newClientRecord);
        }
}



