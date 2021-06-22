let url = "http://localhost:8080/Project1/FrontController"

function submit(){

    let newDraft = {
        id: document.getElementById("storyId").value,
        title: document.getElementById("title").value,
        genre: document.getElementById("genre").value,
        storyType: document.getElementById("story_type").value,
        description: document.getElementById("description").value,
        tagLine: document.getElementById("tag_line").value
    };

    console.log("login info:" + newDraft);
    let json = JSON.stringify(newDraft);
    console.log("json: " + json);

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + "/submit_draft_update", true);
    xhttp.send(json);

    xhttp.onreadystatechange = receiveData;

    function receiveData(){
        console.log("The xhttp ready state is: " + xhttp.readyState);
        console.log("The xhttp status is: " + xhttp.status);
        console.log(xhttp.responseText);
        //window.location.href = xhttp.responseText;
        console.log("Switching to login.html!");
        //window.location = "/Project1/authors.html";
    }
}

function goBack(){
    window.location = "/Project1/authors.html";
}