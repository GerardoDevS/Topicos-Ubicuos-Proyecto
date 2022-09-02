'use strict';

setInterval(datos, 3000);

function datos() {
    const host = 'https://502b-2806-108e-19-aa22-fd99-3450-c80f-e71.ngrok.io/lastUser';
    const cargarAlumnos = alumnos => {
            const nombre = document.getElementById("nombre");
            const apP = document.getElementById("apP");
            const apM = document.getElementById("apM");
            const ncontrol = document.getElementById("nc");
            const estado = document.getElementById("estado");

            nombre.innerHTML = "Nombre: " + alumnos.Nombre;
            apP.innerHTML = "Ap. Paterno: " + alumnos.ApellidoP;
            apM.innerHTML = "Ap. Materno: " + alumnos.ApellidoM;
            ncontrol.innerHTML = "N. Control: " + alumnos.NC;
            estado.innerHTML = "Estado: " + alumnos.EstadoActual;
    }
    fetch(`${host}`)
        .then(res => res.json())
        //.then(body => console.log(body))
        .then(alumnos => cargarAlumnos(alumnos))
}