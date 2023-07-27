// get roles promise
async function getRoles() {
    try {
        const resp = await fetch("api/role/all")
        return await resp.json()
    } catch (error) {
        console.log(error.message())
    }
}

// output for user table
async function fillPrincipalTabl() {
    let user
    try {
        const res = await fetch(`api/user/${userId}`)
        user = await res.json()
    } catch (error) {
        console.log(error.message)
    }
    let elem = document.getElementById("user-id")
    elem.innerText = user.id
    elem = elem.nextElementSibling
    elem.innerHTML = user.username
    elem = elem.nextElementSibling
    elem.innerHTML = user.userLastName
    elem = elem.nextElementSibling
    elem.nextElementSibling.innerHTML = user.roles.map(role => role.roleName.substr(3)).join(" ")
}

// output for users table
async function fillUsersTable() {
    let users
    try {
        const res = await fetch("api/user/all")
        users = await res.json()
    } catch (error) {
        console.log(error.message)
    }
    let elem = document.getElementById("users-table")
    elem.innerHTML = ""
    for (const user of users) {
        let tr = document.createElement('tr')
        tr.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.userLastName}</td>
            <td>${user.roles.map(role => role.roleName.substr(3)).join(" ")}</td>
            <td>
                <button type="button" class="btn btn-info" data-bs-toggle="modal"
                        data-bs-target="#edit-modal" data-bs-updateUserId='${user.id}'>
                    Edit
                </button>
            </td>
            <td>
                <button type="button" class="btn btn-danger" data-bs-toggle="modal"  id = "delete-button"
                        data-bs-target="#delete-modal" data-bs-deleteUserId='${user.id}'>
                    Delete
                </button>
            </td>`
        elem.appendChild(tr)
    }
}

// get data from any form
async function getFormData(form) {
    let val = {}
    const roles = await getRoles() // getting roles as array of objects
    for (const field of form) {
        if (field.name) {
            if (field.type === "select-multiple") {
                val[field.name] = []
                for (const option of field.options) {
                    if (option.selected) {
                        roles.forEach(function (role) {
                            if (role.roleName.substr(5) === (option.value)) {
                                val[field.name].push(
                                    {
                                        id: role.id,
                                        roleName: role.roleName
                                    }
                                )
                            }
                        })
                    }
                }
            } else {
                if (field.value) {
                    val[field.name] = field.value
                }
            }
        }
    }
    return val
}

// save new user
async function saveNewUser() {
    const newUserForm = document.getElementById('newUserForm')
    const newUser = await getFormData(newUserForm)
    try {
        await fetch("/api/user", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(newUser)
        })
    } catch (error) {
        console.log(error.message)
    }
    newUserForm.reset()
    await fillUsersTable()
    switchToUsersTab()
}

// switch current tab to users tab
function switchToUsersTab() {
    const someTabTriggerEl = document.querySelector('#nav-tab button[data-bs-target="#nav-users"]');
    const tab = new bootstrap.Tab(someTabTriggerEl);
    tab.show()
}

// listen the delete modal to get user ID
async function listenDeleteModal() {
    const deleteModal = document.getElementById('delete-modal')
    deleteModal.addEventListener('show.bs.modal', await function (event) {
        const button = event.relatedTarget         // Button that triggered the modal
        const id = button.getAttribute('data-bs-deleteUserId'); //Extract info from data-bs-* attributes
        fillDeleteModal(id)
    })
}

// fill the delete user modal
async function fillDeleteModal(id) {
    let user
    try {
        const res = await fetch(`api/user/${id}`)
        user = await res.json()
    } catch (error) {
        console.log(error.message)
    }
    document.getElementById('idDelete').setAttribute("value", user.id)
    document.getElementById('firstNameDelete').setAttribute("value", user.username)
    document.getElementById('lastNameDelete').setAttribute("value", user.userLastName)
    const elem = document.getElementById('rolesDelete')
    elem.innerHTML = user.roles.map(role => "<option>" + role.roleName.substr(5) + "</option>").join(" ")
}

// delete user
async function deleteUser() {
    const id = document.getElementById('idDelete').value
    try {
        await fetch(`api/user/${id}`, {
            method: "DELETE", headers: {
                "Content-Type": "application/json"
            }
        })
    } catch (error) {
        console.log(error.message)
    }
}

// listen the update modal to get user ID
async function listenUpdateModal() {
    const updateModal = document.getElementById('edit-modal')
    updateModal.addEventListener('show.bs.modal', async function (event) {
        const button = event.relatedTarget         // Button that triggered the modal
        const id = button.getAttribute('data-bs-updateUserId'); //Extract info from data-bs-* attributes
        await fillUpdateModal(id)
    })
}

// fill the update user modal
async function fillUpdateModal(id) {
    let user
    try {
        const res = await fetch(`api/user/${id}`)
        user = await res.json()
    } catch (error) {
       console.log(error.message)
    }
    document.getElementById('idEdit').setAttribute("value", user.id)
    document.getElementById('firstNameEdit').setAttribute("value", user.firstName)
    document.getElementById('lastNameEdit').setAttribute("value", user.lastName)
    document.getElementById('passwordEdit').setAttribute("value", user.password)
    const elem = document.getElementById("rolesEdit")
    elem.innerHTML = ""
    const optionUser = document.createElement("option")
    optionUser.innerText = "USER"
    optionUser.setAttribute("selected", "true")
    elem.appendChild(optionUser)
    const optionAdmin = document.createElement("option")
    optionAdmin.innerText = "ADMIN"
    user.roles.map(role => {
        if (role.roleName.substr(5) === "ADMIN") {
            optionAdmin.setAttribute("selected", "true")
        }
    })
    elem.appendChild(optionAdmin)
}

// update user
async function updateUser() {
    const elem = document.getElementById('editFormBody')
    const user = await getFormData(elem)
    console.log(JSON.stringify(user))
    try {
        await fetch("api/user", {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        })
    } catch (error) {
        console.log(error.message)
    }
}





