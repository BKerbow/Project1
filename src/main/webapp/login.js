let url = "http://localhost:8080/Project1/FrontController"

function authorLogin(source){
    //Everything happening here (outside of onreadystatechange) prepares data and sends it to server
    console.log("logging in...");
    let flag;
    console.log(source)
    if (source == authorLogin){
        flag = "/author_login";
    }
    
    let authorLogIn = {
        username: document.getElementById("authorUsername").Value,
        password: document.getElementById("authorPassword"),value
    };

    console.log("login info:" + authorLogIn);
    let json = JSON.stringify(authorLogIn);
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

function editorLogin(source){
    //Everything happening here (outside of onreadystatechange) prepares data and sends it to server
    
    let editorLogIn = {
        username: document.getElementById("authorUsername").Value,
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