const urlAllUsers = '/showUsers'
const urlUpdate = '/updateUser'
const urlShowRoles = '/showRoles'
const urlSave = '/saveUser'
const urlDelete = '/deleteUser'
const urlUser = '/showUser'

const element = document.querySelector("tbody")
const currentUser = document.querySelector('.currentUser')
const modalEdit = new bootstrap.Modal(document.getElementById('modalEdit'))
const modalDelete = new bootstrap.Modal(document.getElementById('modalDelete'))
const id = document.getElementById('id')
const firstName = document.getElementById('firstName')
const lastName = document.getElementById('lastName')
const age = document.getElementById('age')
const email = document.getElementById('email')
const password = document.getElementById('password')
const role = document.getElementById('role')

const deleteId = document.getElementById('deleteId')
const deleteFirstName = document.getElementById('deleteFirstName')
const deleteLastName = document.getElementById('deleteLastName')
const deleteAge = document.getElementById('deleteAge')
const deleteEmail = document.getElementById('deleteEmail')

const newFirstName = document.getElementById('newFirstName')
const newLastName = document.getElementById('newLastName')
const newAge = document.getElementById('newAge')
const newEmail = document.getElementById('newEmail')
const newPassword = document.getElementById('newPassword')
const newRole = document.getElementById('newRole')

showAllUsers(urlAllUsers)

async function showAllUsers(urlAllUsers) {
    return await fetch(urlAllUsers).then(response => {
        const res = response.json()
        const div = document.querySelector('.navbar-brand')
        div.textContent = response.headers.get('navbar')
        return res
    }).then(data => showUsersData(data)).catch(error => console.log(error))
}

const showUsersData = (data) => {
    let body = ''

    if(data.length === 0) {
        element.innerHTML = body
    }

    for (var i = 0; i < data.length; i++) {
        let roleStr = ''
        for (const role of data[i].roles) {
            roleStr += `<h6>${role.role}</h6>`
        }

        body += `<tr class="${'user' + data[i].id}">
                       <td>${data[i].id}</td>
                       <td>${data[i].firstName}</td>
                       <td>${data[i].lastName}</td>
                       <td>${data[i].age}</td>
                       <td>${data[i].email}</td>
                       <td>${roleStr}</td>
                       <td><button id="buttonEdit" type="button" class="btn btn-primary" data-bs-toggle="modal">Edit</button></td>
                       <td><button id="buttonDelete" type="button" class="btn btn-danger" data-bs-toggle="modal">Delete</button></td>
                    </tr>`

        element.innerHTML = body

        callEditModalWindow(data)
        callDeleteModalWindow(data)
    }
}

showAllRoles(urlShowRoles)

async function showAllRoles(urlShowRoles) {
    return await fetch(urlShowRoles).then(response => response.json())
        .then(data => showRolesData(data))
        .catch(error => console.log(error))
}

const showRolesData = (data) => {
    let option = ""

    for (const role of data) {
        option += `<option value=${role.id}>${role.role}</option>`
    }

    document.getElementById('role').insertAdjacentHTML('afterbegin', option)
    document.getElementById('newRole').insertAdjacentHTML('afterbegin', option)
    document.getElementById('deleteRole').insertAdjacentHTML('afterbegin', option)
}

function selectRoles(selectedRoles) {
    const htmlOptionElements = Array.from(selectedRoles);
    return Array.prototype.map.call(htmlOptionElements,
        option => ({
            id: option.value,
            role: option.text
        })
    );
}

function callEditModalWindow(data) {
    Array.from(element.rows).forEach((row, index) => row.children[6].addEventListener('click', () => {
        id.value = data[index].id
        firstName.value = data[index].firstName
        lastName.value = data[index].lastName
        age.value = data[index].age
        email.value = data[index].email
        password.value = data[index].password
        modalEdit.show()
    }))
}

function callDeleteModalWindow(data) {
    Array.from(element.rows).forEach((row, index) => row.children[7].addEventListener('click', () => {
        deleteId.value = data[index].id
        deleteFirstName.value = data[index].firstName
        deleteLastName.value = data[index].lastName
        deleteAge.value = data[index].age
        deleteEmail.value = data[index].email
        modalDelete.show()
    }))
}

const triggerEl = document.querySelector('#myTab button[data-bs-target="#profile"]')

triggerEl.addEventListener('click', () => {
    newFirstName.value = ''
    newLastName.value = ''
    newAge.value = ''
    newEmail.value = ''
    newPassword.value = ''
})

const edit = document.getElementById('edit')

edit.addEventListener('submit', (event) => {
    if (!edit.checkValidity()) {
        event.preventDefault()
        return
    }
    event.preventDefault()

    edit.classList.add('was-validated')

    const body = {
        id: id.value,
        firstName: firstName.value,
        lastName: lastName.value,
        age: age.value,
        email: email.value,
        password: password.value,
        roles: selectRoles(role.selectedOptions)
    }

    httpRequest('PATCH', urlUpdate, body)
})

document.getElementById('confirmDelete').addEventListener('click', () => {
    const body = {
        id: deleteId.value,
        firstName: deleteFirstName.value,
        lastName: deleteLastName.value,
        age: deleteAge.value,
        email: deleteEmail.value
    }
    httpRequest('DELETE', urlDelete, body)
})


const add = document.getElementById('add')

add.addEventListener('submit', (event) => {
    if (!add.checkValidity()) {
        event.preventDefault()
        return
    }
    event.preventDefault()

    add.classList.add('was-validated')

    const body = {
        firstName: newFirstName.value,
        lastName: newLastName.value,
        age: newAge.value,
        email: newEmail.value,
        password: newPassword.value,
        roles: selectRoles(newRole.selectedOptions)
    }
    httpRequest('POST', urlSave, body)

    const triggerEl = document.querySelector('#myTab button[data-bs-target="#home"]')
    const bsTab = new bootstrap.Tab(triggerEl)
    bsTab.show()
})

async function httpRequest(method, url, body = null) {

    const headers = {
        'Content-Type': 'application/json'
    }

    await fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: headers
    }).then(response => response.json())
        .then(data => showAllUsers(urlAllUsers))
        .catch(error => console.log(error))
}

async function showUser(url) {
    return await fetch(url).then(response => response.json())
        .then(data => showUserData(data))
        .catch(error => console.log(error))
}

showUser(urlUser)

const showUserData = (data) => {
    let body = ''
    let roleStr = ''

    for (const role of data[0].roles) {
        roleStr += `<h6>${role.role}</h6>`
    }

    body = `<tr>
                    <td>${data[0].id}</td>
                    <td>${data[0].firstName}</td>
                    <td>${data[0].lastName}</td>
                    <td>${data[0].age}</td>
                    <td>${data[0].email}</td>
                    <td>${roleStr}</td>
                </tr>`

    currentUser.innerHTML = body
}
