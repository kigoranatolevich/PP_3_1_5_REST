const url = '/showUser'
const tbody = document.querySelector('tbody')

showUser(url)

async function showUser(url) {
    return await fetch(url).then(response => {
        const res = response.json()
        const div = document.querySelector('.navbar-brand')
        div.textContent = response.headers.get('navbar')
        return res
    }).then(data => showUserData(data)).catch(error => console.log(error))
}

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

    tbody.innerHTML = body
}