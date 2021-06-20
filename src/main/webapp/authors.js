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
                //window.location.assign('authors.html');
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
    window.location = "/Project1/src/main/webapp/editormessage.html";
}

function submitStory(){
    window.location = "/Project1/src/main/webapp/newwork.html";
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