let url = "http://localhost:8080/Project1/FrontController"

function logout(){
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + "/logout", true);
    xhttp.send();

    xhttp.onreadystatechange = receiveData;

    function receiveData(){
        console.log("The xhttp ready state is: " + xhttp.readyState);
        console.log("The xhttp status is: " + xhttp.status);
        console.log(xhttp.responseText);
        window.location.href = xhttp.responseText;
        console.log("Switching to login.html!");
    }
}

function getAuthorMessages(){
    console.log("displaying author's messages");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/get_author_messages", true);
    xhttp.send();
    console.log(xhttp.responseText);

    let dataSection = document.getElementById('authorMessageData');
    dataSection.innerHTML = '';

    xhttp.onreadystatechange = () => {
        console.log(xhttp.readyState);
        console.log(xhttp.status);
        if(xhttp.readyState == 4){
            if(xhttp.status == 200){
                
                console.log(xhttp.responseText);

                let dataSection = document.getElementById('authorMessageData');
                dataSection.innerHTML = '';

                let r = xhttp.responseText;
                r = r.split("|");
                let messages = JSON.parse(r[1]);
                let otherMessage = JSON.parse(r[0]);

                //create table
                let messageTable = document.createElement('table');
                messageTable.id = 'messageTable';

                //create table header row
                let thRow = document.createElement('tr');
                let tHeaders = ['Title', 'From Author', 'Author Message'];
                for (let h of tHeaders){
                    let th = document.createElement('th');
                    th.innerHTML = h;
                    thRow.appendChild(th);
                }

                messageTable.append(thRow);

                //Iterate through the stories and create a table row with the date we want
                for (story of r){
                let tr = document.createElement('tr');

                //Title of Work
                let tdTitle = document.createElement('td');
                tdTitle.innerHTML = messages.title;
                tr.appendChild(tdTitle);

                //Author's Name
                let tdAuthor = document.createElement('td');
                tdAuthor.innerHTML = messages.fromAuthor;
                tr.appendChild(tdAuthor);

                //Author's Message
                let tdAuthorMessage = document.createElement('td');
                tdAuthorMessage.innerHTML = messages.authorMessage;
                tr.appendChild(tdEditorMessage);

                messageTable.appendChild(tr);
                }
                
                dataSection.appendChild(messageTable);
            }
        }
    }
}

function sendInfoRequest(){
    window.location = "/Project1/inforequest.html";
}

function rejectScript(){
    window.location = "/Project1/rejectscript.html";
}