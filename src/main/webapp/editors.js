let url = "http://localhost:8080/Project1/FrontController"

function getStories(){
    console.log("displaying your committee's stories");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/display_committee_table");
    xhttp.send();
    console.log(xhttp.responseText);

    let dataSection = document.getElementById("committeeData");
    dataSection.innerHTML = '';

    xhttp.onreadystatechange = () => {
        console.log(xhttp.readyState);
        console.log(xhttp.status);
        if(xhttp.readyState == 4){
            if(xhttp.status == 200){
                console.log(xhttp.responseText);

                let dataSection = document.getElementById('committeeData');
                dataSection.innerHTML = '';

                let r = xhttp.responseText;
                r = r.split("|");
                //r = JSON.parse(r);
                let story = JSON.parse(r[1]);
                let author = JSON.parse(r[0]);
                console.log(story.title);

                //create table
                let authorTable = document.createElement('table');
                authorTable.id = 'authorTable';

                //create table header row
                let thRow = document.createElement('tr');
                let tHeaders = ['Title', 'Genre', 'Story Type', 'Description',
                                'Tag Line', 'Completion Date', 'Approval Status',
                                'Approve'];
                for (let h of tHeaders){
                    let th = document.createElement('th');
                    th.innerHTML = h;
                    thRow.appendChild(th);
                }

                authorTable.append(thRow);

                //Iterate through the stories and create a table row with the date we want
                for (story of r){
                let tr = document.createElement('tr');

                //Title
                let tdTitle = document.createElement('td');
                tdTitle.innerHTML = story.title;
                tr.appendChild(tdTitle);

                //Genre
                let tdGenre = document.createElement('td');
                tdGenre.innerHTML = story.genre;
                tr.appendChild(tdGenre);

                //Story Type
                let tdStoryType = document.createElement('td');
                tdStoryType.innerHTML = story.type;
                tr.appendChild(tdStoryType);

                //Description
                let tdDescription = document.createElement('td');
                tdDescription.innerHTML = story.description;
                tr.appendChild(tdDescription);

                //Tag Line
                let tdTagLine = document.createElement('td');
                tdTagLine.innerHTML = story.tagLine;
                tr.appendChild(tdTagLine);

                //Completion Date
                let tdCompletionDate = document.createElement('td');
                tdCompletionDate.innerHTML = story.completionDate;
                tr.appendChild(tdCompletionDate);
                
                //Approval Status
                let tdApprovalStatus = document.createElement('td');
                tdApprovalStatus.innerHTML = story.approvalStatus;
                tr.appendChild(tdApprovalStatus);

                //Approve
                let tdButton = document.createElement("button");
                tdButton.innerHTML = "Approve";
                tr.appendChild(tdButton);

                tdButton.onclick = function sendApproval(){
                    let approveStory = {
                        approved: document.getElementById("Approve").value
                    };

                    console.log("button pressed" + approveStory);
                    let json = JSON.stringify(approveStory);
                    console.log("json: " + json);

                    let xhttp = new XMLHttpRequest();
                    xhttp.open("POST", url + "/approve_story", true);
                    xhttp.send(json);
                    
                    console.log("sent json");
                    xhttp.onreadystatechange = sendData;

                    function sendData(){
                        console.log(xhttp.readyState);
                        console.log(xhttp.status);
                        if(xhttp.readyState == 4){
                            if(xhttp.status == 200){
                                //log on screen 'approved story!'
                                //disable approve button somehow
                            }
                        }
                    }
                }

                authorTable.appendChild(tr);
                }
                
                dataSection.appendChild(authorTable);
            }
        }
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
                let messages = JSON.parse(r[0]);
                console.log(messages);
                console.log(r);
                let otherMessage = JSON.parse(r[1]);
                

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
                tr.appendChild(tdAuthorMessage);

                messageTable.appendChild(tr);
                }
                
                dataSection.appendChild(messageTable);
            }
        }
    }
}

function getEditorMessages(){
    console.log("displaying author's messages");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/get_other_editor_messages", true);
    xhttp.send();
    console.log(xhttp.responseText);

    let dataSection = document.getElementById('editorMessageData');
    dataSection.innerHTML = '';

    xhttp.onreadystatechange = () => {
        console.log(xhttp.readyState);
        console.log(xhttp.status);
        if(xhttp.readyState == 4){
            if(xhttp.status == 200){
                
                console.log(xhttp.responseText);

                let dataSection = document.getElementById('editorMessageData');
                dataSection.innerHTML = '';

                let r = xhttp.responseText;
                r = r.split("|");
                let messages = JSON.parse(r[0]);
                console.log(messages);
                console.log(r);
                let otherMessage = JSON.parse(r[1]);
                

                //create table
                let messageTable = document.createElement('table');
                messageTable.id = 'messageTable';

                //create table header row
                let thRow = document.createElement('tr');
                let tHeaders = ['Title', 'From Editor', 'Editor Message'];
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
                let tdEditor = document.createElement('td');
                tdEditor.innerHTML = messages.fromEditor;
                tr.appendChild(tdEditor);

                //Author's Message
                let tdEditorMessage = document.createElement('td');
                tdEditorMessage.innerHTML = messages.EditorMessage;
                tr.appendChild(tdEditorMessage);

                messageTable.appendChild(tr);
                }
                
                dataSection.appendChild(messageTable);
            }
        }
    }
}

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

function sendInfoRequest(){
    window.location = "/Project1/inforequest.html";
}

function rejectScript(){
    window.location = "/Project1/rejectscript.html";
}