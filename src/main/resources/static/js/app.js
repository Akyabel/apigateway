const API = "/users/";

document.addEventListener("DOMContentLoaded", function() {
    loadUsers();
    loadTasks();

    document.getElementById("userform").addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = document.getElementById("userId").value;
        const name = document.getElementById("userName").value;
        const email = document.getElementById("userEmail").value;

        const method = id ? "PUT" : "POST";
        const url = id ? `${API}/${id}` : API;

        const response = await fetch(url, {
            method: method,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, tasks: [] })
        });

        if (response.ok) {
            alert("User saved successfully");
            document.getElementById("userform").reset();
            loadUsers();
        } else {
            console.error("Failed to save user");
        }
    });
});

async function loadUsers() {
    const res = await fetch(API);
    const users = await res.json();
    const userList = document.getElementById("userList");
    userList.innerHTML = "<table class='table' table-striped><thead><tr><th>ID</th><th>Nombre</th><th>Email</th><th>Acciones</th></tr></thead><tbody>";
    userList.innerHTML += users.map(user => `
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>
                <button class="btn btn-primary" onclick="editUser(${user.id})">Editar</button>
                <button class="btn btn-danger" onclick="deleteUser(${user.id})">Eliminar</button>
            </td>
        </tr>
    `).join("");
        userList.innerHTML += "</tbody></table>";
    }