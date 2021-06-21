let url = "http://localhost:8080/Project1/FrontController"

function displayStories(){
    console.log("displaying author's stories");
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + "/get_author_stories", true);
    xhttp.send();
    console.log(xhttp.responseText);

    let dataSection = document.getElementById('data');
    dataSection.innerHTML = '';

    xhttp.onreadystatechange = () => {
        console.log(xhttp.readyState);
        console.log(xhttp.status);
        if(xhttp.readyState == 4){
            if(xhttp.status == 200){
                
                console.log(xhttp.responseText);

                let dataSection = document.getElementById('data');
                dataSection.innerHTML = '';

                let r = xhttp.responseText;
                r = JSON.parse(r);

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
                for (let story of r){
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
                tdStoryType = story.type;
                tr.appendChild(tdStory);

                //Description
                let tdDescription = document.createElement('td');
                tdDescription = story.description;
                tr.appendChild(tdDescription);

                //Tag Line
                let tdTagLine = document.createElement('td');
                tdTagLine = story.tagLine;
                tr.appendChild(tdTagLine);

                //Completion Date
                let tdCompletionDate = document.createElement('td');
                tdCompletionDate = story.completionDate;
                tr.appendChild(tdCompletionDate);
                
                //Approval Status
                let tdApprovalStatus = document.createElement('td');
                tdApprovalStatus = story.approvalStatus;
                tr.appendChild(tdApprovalStatus);

                //Reason
                let tdReason = document.createElement('td');
                tdReason = story.reason;
                tr.appendChild(reason);
                }
                
            }
        }
    }
}

function getStories(){
    //Everything happening here (outside of onreadystatechange) prepares data and sends it to server
    console.log("clicked Story Submit Button");

    let xhttp = new XMLHttpRequest();

    xhttp.open("POST", url + "/story_submit", true);
    xhttp.send();

    console.log("sent json");
    console.log("The xhttp ready state is: " + xhttp.readyState);
    console.log("The xhttp status is: " + xhttp.status);

    xhttp.onreadystatechange = receiveData;
    
    function receiveData(){
        console.log("The xhttp ready state is: " + xhttp.readyState);
        console.log("The xhttp status is: " + xhttp.status);
        if (xhttp.readyState == 4){
            if(xhttp.status == 200){
                //test to see if javascript is talking
                console.log("Request is ready and sending.");
                //load new html page sent from servlet
                console.log(xhttp.responseText);
                window.location.href = xhttp.responseText;
                console.log("Switching to newwork.html!");
            }
        }
    }
}

function getEditorMessages(){
    window.location = "/Project1/editormessage.html";
}

function submitStory(){
    window.location = "/Project1/newwork.html";
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