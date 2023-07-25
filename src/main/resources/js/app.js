
async function getRoles() {
    try {
        const resp = await fetch("api/role/all")
        return await resp.json()
    } catch (error) {
        console.log(error.message())
    }
}


async function fillPrincipalTable() {
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


async function getFormData(form) {
    let val = {}
    const roles = await getRoles()
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







