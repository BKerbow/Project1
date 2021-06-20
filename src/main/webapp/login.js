let url = 'http://localhost:8080/Project1/FrontController';

function login(source){
    //Everything happening here (outside of onreadystatechange) prepares data and sends it to server
    console.log("logging in...");
    let flag;

    if (source == 'authorLogin'){
        flag = "/author_login";
    }

    let authorLogIn = {
        username: document.getElementById("authorUsername").value,
        password: document.getElementById("authorPassword").value
    };

    console.log("login info:" + authorLogIn);
    let json = JSON.stringify(authorLogIn);
    console.log("json: " + json);

    let xhttp = new XMLHttpRequest();

    xhttp.open("POST", url, true);
    xhttp.send(json);

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
                console.log("Switching to authors.html!");
            }
        }
    }
}

function editorLogin(source){
    //Everything happening here (outside of onreadystatechange) prepares data and sends it to server
    
    console.log("logging in...");
    let flag;
    console.log(source)
    if (source == editorLogin){
        flag = "/editor_login";
    }

    let editorLogIn = {
        username: document.getElementById("authorUsername").value,
        password: document.getElementById("authorPassword"),value
    };

    console.log("login info:" + editorLogIn);
    let json = JSON.stringify(editorLogIn);
    console.log("json: " + json);

    let xhttp = new XMLHttpRequest();

    xhttp.open("POST", url, true);
    xhttp.send(json);

    xhttp.onReadyStateChange = () => {
        console.log(readyState);
        console.log(status);
        if (xhttp.readySate == 4){
            if(xhttp.status == 200){
                //test to see if javascript is talking
                console.log("Request is ready and sending.");
                //load new html page sent from servlet
                window.location.href = xhttp.responseText;
            }
        }
    }
}