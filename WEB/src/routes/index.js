const {Router} = require('express');
const router = Router();

const admin = require('firebase-admin');

admin.initializeApp({
    credential: admin.credential.applicationDefault(),
    databaseURL: 'https://aplicacionubicua-default-rtdb.firebaseio.com/'
});

const db = admin.database();

router.get('/',(req,res)=>{
    db.ref('Usuarios').once('value',(snapshot)=>{
        const data = snapshot.val();
        res.render('layouts/index',{Usuarios: data});
    });
    //res.render('layouts/index');
});

router.get('/lastUser',(req,res) => {
    db.ref('UltimoUsuario').once('value',(snapshot)=>{
        let id = snapshot.val();
        ultUsuario = 'Usuarios/' + id;
    });

    db.ref(ultUsuario).once('value',(snapshot)=>{
        const data = snapshot.val();
        res.json(data);
        /* console.log(data); */
    });
});

module.exports = router;