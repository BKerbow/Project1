console.log("page loaded!");

function getData(){

    let url = 'http://localhost:8080/Project1/FrontController/authors';
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = receiveData();
    xhttp.open('GET', url, true);
    xhttp.send();

        function receiveData() {
            let dataSection = document.getElementById('data');
            dataSection.innerHTML = '';
            if (xhttp.readyState == 4) {
                if (xhttp.status == 200) {

                    //window.location.href = xhttp.responseText;
                    let r = xhttp.responseText;
                    console.log(r);
    
                    r = JSON.parse(r);
                    console.log(r);
    
                    // Create a table
                    let authorTable = document.createElement('table');
                    authorTable.id = 'authorTable';
    
                    // we will need: <tr> table row, <td> for each piece of data in that row, <th> for the header
    
                    // Create table header row
                    let thRow = document.createElement('tr');
                    let tHeaders = ['First Name', 'Last Name', 'Bio', 'Points'];
                    for (let h of tHeaders) {
                        let th = document.createElement('th');
                        th.innerHTML = h;
                        thRow.appendChild(th);
                    }
    
                    authorTable.append(thRow);
    
                    // Iterate through the 'cats' and create a tr with td we want to show
                    for (let author of r) {
                        // Row for each cat
                        let tr = document.createElement('tr');
    
                        // Author's First Name
                        let tdName = document.createElement('td');
                        tdName.innerHTML = author.firstName;
                        tr.appendChild(tdName);
                        
                        // Author's Last Name
                        let tdAge = document.createElement('td');
                        tdAge.innerHTML = author.lastName;
                        tr.appendChild(tdAge);
    
                        // Author's Bio
                        let tdBreed = document.createElement('td');
                        tdBreed.innerHTML = author.bio;
                        tr.appendChild(tdBio);

                        //Author's Points
                        let tdPoitns = document.createElement('td');
                        tdPoitns.innerHTML = author.points;
                        tr.appendChild(tdPoints);
    
                        authorTable.appendChild(tr);
                    }

                    dataSection.appendChild(catTable);
                }
            }
        }
    }

    /**
        * In the final product, this page will:
        * 
        * 1. Display a Welcome Message
        * 2. Display a Story Submit Form
        * 3. Display all Pending Stories
        * 4. Display Stories that do not have points assigned
        * 5. Display Info Requests from Editors
        */