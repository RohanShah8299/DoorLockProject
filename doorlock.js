var Gpio = require('onoff').Gpio,
  sensor = new Gpio(27, 'in', 'both', {debounceTimeout: 1000});  
var LED = new Gpio(17, 'out');
var NodeWebcam = require( "node-webcam" );
emails_arr=[];
var opts = {
    width: 1280,
    height: 720,
    quality: 100,
    delay: 0,
    saveShots: true,
    output: "jpeg",
    device: false,
    callbackReturn: "location",
    verbose: false
};
var Webcam = NodeWebcam.create( opts );
Webcam.capture( "test_picture", function( err, data ) {} );
const firebaseConfig = {
  apiKey: "AIzaSyB9Iw7kp5gS9_YnM2MKxQNkmX9e-9vfGkk",
  authDomain: "beprojectdoorlock.firebaseapp.com",
  databaseURL: "https://beprojectdoorlock.firebaseio.com",
  projectId: "beprojectdoorlock",
  storageBucket: "beprojectdoorlock.appspot.com",
  messagingSenderId: "199064628837",
  appId: "1:199064628837:web:85f2be95901eea40f7208c",
  measurementId: "G-FV5EKGGVDF"
};
var nodemailer = require('nodemailer');
 
var mail = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: 'doorlockbeproject@gmail.com',
    pass: 'Djsce@123'
  }
});
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
var firebase=require('firebase/app');
var fs=require("fs");
var database=require('firebase/database');
firebase.initializeApp(firebaseConfig);
var database = firebase.database();
const rootRef=database.ref('users/abc456/unlock');
const rootRef2=database.ref('users/abc456/AtDoor');
var count=1;
const rootRef4=database.ref('users/abc456/emails');
rootRef4.on('child_added',function(snapshot){
emails_arr.push(snapshot.val());
//console.log(emails_arr);
});
function endBlink() { //function to stop blinking
LED.writeSync(0);
console.log('lock close');
}
  function blinkLED() { //function to start blinking
LED.writeSync(1);
}
rootRef.on('child_changed',function(snapshot){
    if(snapshot.val){
console.log('lock open');
blinkLED();
setTimeout(endBlink, 10000);
rootRef.set({unlock:false});
      }});
sensor.watch(function (err, value) { //#B
  count=1;
  if (err) exit(err);
  if(!value){
    console.log("person detected");
  var blinkInterval = setInterval(checkPerson, 3000);
  function checkPerson(){
      sensor.read(function (err, value) {
  if(!value) {
count++;
console.log("person there"); console.log(count);
if (count==5){
 for (a in emails_arr){
     console.log(a);
     var Webcam = NodeWebcam.create( opts );
Webcam.capture( "test_picture", function( err, data ) {} );
                                                 console.log("Initializing Emails"); console.log(count);
var mailOptions = {
from: '',
to: emails_arr[a],
subject: 'Sending Email using Node.js',
html: '<h1>Welcome</h1><p>That was easy!</p>' ,
attachments: {
filename: 'image1.jpg',
content: fs.readFileSync("test_picture.jpg")
}
}
mail.sendMail(mailOptions, function(error, info){
if(error){
console.log(error);
}else{
console.log('Email sent: ' + info.response);
}
});
   }

  rootRef2.set({
AtDoor:true
});
clearInterval(blinkInterval);
count=1;
}

}
  else {clearInterval(blinkInterval); count=1;}
  });
 
  }
  }
});
function exit(err) {
  if (err) console.log('An error occurred: ' + err);
  sensor.unexport();
  console.log('Bye, bye!')
  process.exit();
}
process.on('SIGINT', exit);