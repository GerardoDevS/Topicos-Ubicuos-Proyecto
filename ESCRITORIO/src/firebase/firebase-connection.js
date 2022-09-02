const { initializeApp } = require('firebase/app');
const { getDatabase , ref, onValue, get, child} = ("firebase/database");
//import { initializeApp } from '../../node_modules/firebase/app';

// Import the functions you need from the SDKs you need
//import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAoKpu5LSzG67_g9msI0jD_rWpCLHovTFE",
  authDomain: "aplicacionubicua.firebaseapp.com",
  databaseURL: "https://aplicacionubicua-default-rtdb.firebaseio.com",
  projectId: "aplicacionubicua",
  storageBucket: "aplicacionubicua.appspot.com",
  messagingSenderId: "260334690160",
  appId: "1:260334690160:web:75b596a66e1cdc51a0b9b5"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const database = getDatabase(app);

const dbref = ref(database);

function leer () {
    get(child(dbref, "Usuarios/LXYiRcUGc2bGrlNPZzGoweCHJnf2")).then((snapshot) =>{
    if(snapshot.exists()){
      console.log(snapshot.val().Nombre);
    }
  });
}