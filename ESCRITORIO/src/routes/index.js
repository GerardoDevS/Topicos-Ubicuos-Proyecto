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

router.post('/new-contact', (req,res)=>{
    const newContact = {
        firstName: req.body.firstName,
        lastName: req.body.lastName,
        email: req.body.email,
        phone: req.body.phone
    };
    db.ref('contactos').push(newContact);
    res.send('received');
})

module.exports = router;