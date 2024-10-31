document.getElementById('registerForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const telefono = document.getElementById('telefono').value;
    const metodoPago = document.getElementById('metodoPago').value;
    const rol = document.getElementById('rol').value;

    const response = await fetch('/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            nombre,
            email,
            password,
            telefono,
            metodoPago,
            rol
        })
    });

    if (response.ok) {
        alert('Registro exitoso');
        window.location.href = '/login.html';
    } else {
        alert('Error en el registro');
    }
});
