import 'package:flutter/material.dart';

import 'login.dart';

class RegisterPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<RegisterPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomPadding: false,
      body: Stack(
        children: [
          Container(
            color: Colors.red,
          ),
          Container(
            height: 50,
            margin: EdgeInsets.only(top: 180,left: 15,right: 15),
            decoration: BoxDecoration(
              color: Colors.blue,
              border: Border.all(color: Colors.lightBlue,width: 1.5),
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(10),
                  topRight: Radius.circular(10)
              )
            ),
            child: Row(
              children: [
                Expanded(
                  flex: 1,
                  child: Container(
                    height: 50,
                    child: RaisedButton(
                      child: Text("Sign In"),
                      color: Colors.white,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.only(
                            topLeft: Radius.circular(10),
                          )
                      ),
                      onPressed: (){
                        Navigator.of(context).push(MaterialPageRoute(builder: (context)=>LoginPage()));
                      },
                    ),
                  ),
                ),
                Expanded(
                  flex: 1,
                  child: Container(
                    height: 50,
                    child: RaisedButton(
                      child: Text("Sign Up"),
                      color: Colors.blue,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.only(
                              topRight: Radius.circular(10)
                          )
                      ),
                      onPressed: (){},
                    ),
                  ),
                )
              ],
            ),
          ),
          Align(
            alignment: Alignment.topCenter,
            child: Container(
              height: 100,
              width: 100,
              margin: EdgeInsets.only(top: 130),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.blue,width: 1.5),
                shape: BoxShape.circle
              ),
              child: CircleAvatar(
                backgroundImage: AssetImage('images/sigup_boy_img.jpg'),
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(top: 20,left: 15,right: 15),
            margin: EdgeInsets.only(top: 230,left: 15,right: 15,bottom: 140),
            decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.only(bottomLeft: Radius.circular(10),bottomRight: Radius.circular(10))
            ),
            child: Column(
              children: [
                Container(
                  alignment: Alignment.center,
                  margin: EdgeInsets.only(top: 10),
                  child: Text("Sign Up",style: TextStyle(fontSize: 20,fontWeight:FontWeight.w700),),
                ),
                TextField(
                  keyboardType: TextInputType.emailAddress,
                  maxLines: 1,
                  decoration: InputDecoration(
                    prefixIcon: Icon(Icons.email),
                    hintText: "Email",
                    contentPadding: EdgeInsets.symmetric(vertical: 15),
                  ),
                ),
                SizedBox(height: 40,),
                TextField(
                  maxLines: 1,
                  obscureText: true,
                  keyboardType: TextInputType.visiblePassword,
                  decoration: InputDecoration(
                    prefixIcon: Icon(Icons.lock_outline),
                    hintText: "Password",
                    contentPadding: EdgeInsets.symmetric(vertical: 15),
                    suffixIcon: Icon(Icons.visibility_off)
                  ),
                ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Container(
                      height: 50,
                      width: 200,
                      margin: EdgeInsets.only(top: 40),
                      child: FloatingActionButton.extended(
                          onPressed: (){
                            // Navigator.pushAndRemoveUntil(
                            //     context,
                            //     MaterialPageRoute(builder: (context)=>),
                            //     (route) => false
                            // );
                          },
                          elevation: 10,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(10)
                          ),
                          icon: Icon(Icons.fingerprint),
                          label: Text("Sign Up"),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
