let url = "http://localhost:8080/Project1/FrontController"

function submit(){
    let authorSubmit = {
        title: document.getElementById('title').value,
        genre: document.getElementById("genre").value,
        storyType: document.getElementById("story_type").value,
        description: document.getElementById("description").value,
        tagLine: document.getElementById("tag_line")
    };

    console.log("new story info: " + authorSubmit);
    let json = JSON.stringify(authorSubmit);
    console.log("json: " + json);

    let xhttp = new XMLHttpRequest();

    xhttp.open("POST", url + "/submit_new_work", true);
    xhttp.send(json);

    console.log(json);

    xhttp.onreadystatechange = receiveData;

    function receiveData(){
        console.log("the xhttp ready state is: " + xhttp.readyState);
        console.log("the xhttp status is: " + xhttp.status);
        if (xhttp.readyState == 4){
            if(xhttp.status == 200){
                console.log("Json is packed and ready to send.");
                console.log(xhttp.responseText);
                window.location.href = xhttp.responseText;
                console.log("Going back to main author menu!");
            }
        }
    }
}

function goBack(){
    window.location = "/Project1/authors.html";
}