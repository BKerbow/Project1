let url = "http://localhost:8080/Project1/FrontController"

function displayStories(){
    console.log("displaying author's stories");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/get_author_stories", true);
    xhttp.send();
    console.log(xhttp.responseText);

    let dataSection = document.getElementById('storyData');
    dataSection.innerHTML = '';

    xhttp.onreadystatechange = () => {
        console.log(xhttp.readyState);
        console.log(xhttp.status);
        if(xhttp.readyState == 4){
            if(xhttp.status == 200){
                
                console.log(xhttp.responseText);

                let dataSection = document.getElementById('storyData');
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
                                'Tag Line', 'Completion Date', 'Approval Status', 'Reason'];
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

                //Reason
                let tdReason = document.createElement('td');
                tdReason.innerHTML = story.reason;
                tr.appendChild(tdReason);

                authorTable.appendChild(tr);
                }
                
                dataSection.appendChild(authorTable);
            }
        }
    }
}

function getEditorMessages(){
    console.log("displaying editor's messages");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/get_editor_messages", true);
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
                let messages = JSON.parse(r);

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

                //Editor's Name
                let tdEditor = document.createElement('td');
                tdEditor.innerHTML = messages.fromEditor;
                tr.appendChild(tdEditor);

                //Editor's Message
                let tdEditorMessage = document.createElement('td');
                tdEditorMessage.innerHTML = messages.editorMessage;
                tr.appendChild(tdEditorMessage);

                messageTable.appendChild(tr);
                }
                
                dataSection.appendChild(messageTable);
            }
        }
    }
}

function submitStory(){
    window.location = "/Project1/newwork.html";
}

function updateStory(){
    window.location = "/Project1/newdraft.html";
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