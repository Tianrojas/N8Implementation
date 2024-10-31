// Función para decodificar el payload del JWT
function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

// Evento de envío de formulario
document.getElementById('loginForm').addEventListener('submit', async function(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const response = await fetch('/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({email, password})
    });

    if (response.ok) {
        const data = await response.json();
        const token = data.token;
        localStorage.setItem('token', token);

        // Decodificar el token para obtener información del usuario
        const decodedToken = parseJwt(token);
        localStorage.setItem('userEmail', decodedToken.sub);
        localStorage.setItem('userRole', decodedToken.role);

        // Redirigir según el rol
        if (decodedToken.role === 'Customer') {
            window.location.href = '/user/customer.html';
        } else if (decodedToken.role === 'Hoster') {
            window.location.href = '/user/hoster.html';
        } else {
            alert('Rol no reconocido');
        }
    } else {
        alert('Credenciales inválidas');
    }
});
