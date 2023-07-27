
async function getUser() {
    let temp = '';
    const table = document.getElementById('tableUser');
    const res = await fetch(`api/user`)
    const user = await res.json();
    temp = `
                <table class="usersTable table table-striped bg-white" >
                                <thead>
                                <tr class="border-top">
                                    <th scope="col">ID</th>
                                    <th scope="col">First Name</th>
                                    <th scope="col">Last Name</th>
                                    <th scope="col">Role</th>
                                </tr>
                                </thead>
                                <tbody>
                                <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.userLastName}</td>
                    <td>${user.roles.map(e => " " + e.userRole.substr(4))}</td>
                                </tbody>
                            </table>
            `;
    table.innerHTML = temp;
}

let roleList = [
    {id: 1, role: "ROLE_USER"},
    {id: 2, role: "ROLE_ADMIN"}
]
let isUser = true;



// async function getUser() {
//     let temp = '';
//     const table = document.querySelector('#tableUser tbody');
//     await userFetch.findUserByUsername()
//         .then(res => res.json())
//         .then(user => {
//             temp = `
//                 <tr>
//                     <td>${user.userId}</td>
//                     <td>${user.username}</td>
//                     <td>${user.name}</td>
//                     <td>${user.surname}</td>
//                     <td>${user.age}</td>
//                     <td>${user.email}</td>
//                     <td>${user.roles.map(e => " " + e.role.substr(5))}</td>
//                 </tr>
//             `;
//             table.innerHTML = temp;
//
//             $(function (){
//                 let role = ""
//                 for (let i = 0; i < user.roles.length; i++) {
//                     role = user.roles[i].role
//                     if (role === "ROLE_ADMIN") {
//                         isUser = false;
//                     }
//                 }
//                 if (isUser) {
//                     $("#userTable").addClass("show active");
//                     $("#userTab").addClass("show active");
//                 } else {
//                     $("#adminTable").addClass("show active");
//                     $("#adminTab").addClass("show active");
//                 }
//             })
//         })
// }
// async function getUsers() {
//     let temp = '';
//     const table = document.querySelector('#tableAllUsers tbody');
//     await userFetch.findAllUsers()
//         .then(res => res.json())
//         .then(users => {
//             users.forEach(user => {
//                 temp += `
//                 <tr>
//                     <td>${user.userId}</td>
//                     <td>${user.username}</td>
//                     <td>${user.name}</td>
//                     <td>${user.surname}</td>
//                     <td>${user.age}</td>
//                     <td>${user.email}</td>
//                     <td>${user.roles.map(e => " " + e.role.substr(5))}</td>
//                     <td>
//                         <button type="button" data-userid="${user.userId}" data-action="edit" class="btn btn-info"
//                             className data-toggle="modal" data-target="#editModal">Edit</button>
//                     </td>
//                     <td>
//                         <button type="button" data-userid="${user.userId}" data-action="delete" class="btn btn-danger"
//                             className data-toggle="modal" data-target="#deleteModal">Delete</button>
//                     </td>
//                 </tr>
//                `;
//             })
//             table.innerHTML = temp;